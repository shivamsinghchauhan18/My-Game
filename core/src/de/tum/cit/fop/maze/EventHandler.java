package de.tum.cit.fop.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import de.tum.cit.fop.maze.GameElements.Characters.Player;

import java.util.Collections;
import java.util.List;

/**
 * Handles events and interactions in the MazeRunnerGame, including player movements,
 * interactions with objects, obstacles, enemies, hearts, and win/lose conditions.
 */
public class EventHandler {
    private static final float OBSTACLE_DAMAGE_INTERVAL = 3.0f;
    private static final float ENEMY_DAMAGE_INTERVAL = 3.0f;

    private final Player player;
    private final MapLoader mapLoader;
    private final MazeRunnerGame game;
    private final Music backgroundMusic;

    private float timeOnObstacle = 0f;
    private float timeOnEnemy = 0f;

    private boolean isOnObstacle = false;
    private boolean isOnEnemy = false;
    private boolean isOnHeart1 = false;
    private boolean isOnHeart2 = false;

    /**
     * Constructs the EventHandler with required game components.
     *
     * @param player          The player character of the game.
     * @param mapLoader       The object responsible for managing the game map.
     * @param game            The main game object for state transitions.
     * @param backgroundMusic The background music for the game.
     */
    public EventHandler(Player player, MapLoader mapLoader, MazeRunnerGame game, Music backgroundMusic) {
        this.player = player;
        this.mapLoader = mapLoader;
        this.game = game;
        this.backgroundMusic = backgroundMusic;
    }

    /**
     * Handles player movements based on input and updates the player's position and animation.
     */
    public void handlePlayerMovements() {
        float animationSpeed = 3f;
        float deltaTime = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            movePlayer(Direction.LEFT, deltaTime, animationSpeed);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            movePlayer(Direction.RIGHT, deltaTime, animationSpeed);
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            movePlayer(Direction.UP, deltaTime, animationSpeed);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            movePlayer(Direction.DOWN, deltaTime, animationSpeed);
        }
    }

    /**
     * Moves the player in the specified direction if movement is valid.
     *
     * @param direction      The direction of movement.
     * @param deltaTime      Time elapsed since the last frame.
     * @param animationSpeed The speed of the animation.
     */
    private void movePlayer(Direction direction, float deltaTime, float animationSpeed) {
        if (Utils.canCharacterMove(player.getX(), player.getY(), direction, mapLoader, player.getHasKey())) {
            switch (direction) {
                case LEFT -> player.setX(player.getX() - animationSpeed * deltaTime);
                case RIGHT -> player.setX(player.getX() + animationSpeed * deltaTime);
                case UP -> player.setY(player.getY() + animationSpeed * deltaTime);
                case DOWN -> player.setY(player.getY() - animationSpeed * deltaTime);
            }
            player.startAnimation(player.getAnimationForDirection(direction));
            player.setDefaultFrame(player.getFrameForDirection(direction));
        }
    }

    /**
     * Handles interactions with the key, updating the game state when the key is collected.
     *
     * @param hud The HUD to be updated.
     */
    public void handleKeyInteraction(HUD hud) {
        List<Integer> keyCoordinates = List.of(mapLoader.getKeyX(), mapLoader.getKeyY());
        if (Utils.isAtCoordinate(player.getX(), player.getY(), Collections.singletonList(keyCoordinates))) {
            player.setHasKey(true);
            mapLoader.setDisplayKey(false);
            hud.update();
            hud.animateKeyCollection();
        }
    }

    /**
     * Handles damage when the player interacts with obstacles.
     *
     * @param deltaTime The time elapsed since the last frame.
     * @param hud       The HUD to update the player's health.
     */
    public void handlePlayerObstacleInteraction(float deltaTime, HUD hud) {
        if (Utils.isAtCoordinate(player.getX(), player.getY(), mapLoader.getObstacleCoordinates())) {
            manageDamage(deltaTime, hud, true);
        } else {
            resetObstacleState();
        }
    }

    private void manageDamage(float deltaTime, HUD hud, boolean isObstacle) {
        if (isObstacle ? !isOnObstacle : !isOnEnemy) {
            applyDamage(hud, isObstacle);
        } else {
            float timer = isObstacle ? (timeOnObstacle += deltaTime) : (timeOnEnemy += deltaTime);
            if (timer >= (isObstacle ? OBSTACLE_DAMAGE_INTERVAL : ENEMY_DAMAGE_INTERVAL)) {
                applyDamage(hud, isObstacle);
                resetTimer(isObstacle);
            }
        }
    }

    private void applyDamage(HUD hud, boolean isObstacle) {
        player.setHeartCount(player.getHeartCount() - 1);
        hud.update();
        hud.animateHeartLoss(2 - player.getHeartCount());

        if (isObstacle) isOnObstacle = true;
        else isOnEnemy = true;
    }

    private void resetTimer(boolean isObstacle) {
        if (isObstacle) timeOnObstacle = 0f;
        else timeOnEnemy = 0f;
    }

    private void resetObstacleState() {
        isOnObstacle = false;
        timeOnObstacle = 0f;
    }

    /**
     * Handles damage when the player interacts with enemies.
     *
     * @param deltaTime The time elapsed since the last frame.
     * @param hud       The HUD to update the player's health.
     */
    public void handlePlayerEnemyInteraction(float deltaTime, HUD hud) {
        if (Utils.isEnemy(player.getX(), player.getY(), mapLoader.getEnemies())) {
            manageDamage(deltaTime, hud, false);
        } else {
            isOnEnemy = false;
            timeOnEnemy = 0f;
        }
    }

    /**
     * Handles interactions with hearts, restoring player health.
     *
     * @param hud The HUD to update the player's health.
     */
    public void handlePlayerHeartInteraction(HUD hud) {
        if (collectHeart(mapLoader.getHeartCoordinate1(), hud)) isOnHeart1 = true;
        if (collectHeart(mapLoader.getHeartCoordinate2(), hud)) isOnHeart2 = true;
    }

    private boolean collectHeart(int heartIndex, HUD hud) {
        List<Integer> heartCoordinate = mapLoader.getHeartCoordinates().get(heartIndex);
        if (Utils.isAtCoordinate(player.getX(), player.getY(), Collections.singletonList(heartCoordinate))) {
            if (player.getHeartCount() < 3) {
                player.setHeartCount(player.getHeartCount() + 1);
                hud.update();
                // Update display flags dynamically based on the heart index
                if (heartIndex == mapLoader.getHeartCoordinate1()) {
                    mapLoader.setDisplayHeart1(false);
                } else if (heartIndex == mapLoader.getHeartCoordinate2()) {
                    mapLoader.setDisplayHeart2(false);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Triggers the lose condition and transitions to the lose screen.
     */
    public void handleLoseCondition() {
        if (player.getHeartCount() == 0) {
            backgroundMusic.stop();
            game.goToLoseScreen();
        }
    }

    /**
     * Triggers the win condition and transitions to the win screen.
     */
    public void handleWinCondition() {
        if (Utils.isAtCoordinate(player.getX(), player.getY(), mapLoader.getDoorCoordinates()) && player.getHasKey()) {
            backgroundMusic.stop();
            game.goToWinScreen();
        }
    }
}