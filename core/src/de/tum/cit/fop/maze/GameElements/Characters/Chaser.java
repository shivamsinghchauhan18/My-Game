package de.tum.cit.fop.maze.GameElements.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import de.tum.cit.fop.maze.Direction;
import de.tum.cit.fop.maze.MapLoader;
import de.tum.cit.fop.maze.Utils;
//import de.tum.cit.fop.maze.Characters.Player;

import java.util.Random;

/**
 * Represents the Chaser character in the MazeRunnerGame.
 * The Chaser actively pursues the player using a combination of random movement and pathfinding.
 */
public class Chaser {

    // Position and movement properties
    private float x, y; // Current position of the Chaser
    private final float speed; // Movement speed of the Chaser
    private Direction direction; // Current direction of movement
    private final MapLoader mapLoader; // Reference to the game map for movement validation
    private final Random random; // For random movement behavior

    // Animation properties
    private Animation<TextureRegion> chaserAnimation; // Animation for the Chaser
    private float animationTime = 0f; // Keeps track of animation progress

    // Targeting behavior
    private static final float CHASE_RADIUS = 5.0f; // Distance within which the Chaser targets the player
    private final Player player; // Reference to the player object

    /**
     * Constructs a Chaser object with specified initial position, speed, map context, and player reference.
     *
     * @param x         The initial x-coordinate of the Chaser.
     * @param y         The initial y-coordinate of the Chaser.
     * @param speed     The movement speed of the Chaser.
     * @param mapLoader The MapLoader instance for validating movement within the map.
     * @param player    The Player instance to target during gameplay.
     */
    public Chaser(float x, float y, float speed, MapLoader mapLoader, Player player) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.mapLoader = mapLoader;
        this.player = player;
        this.random = new Random();
        this.direction = Direction.getRandomDirection(); // Start with a random direction
        loadChaserAnimation(); // Initialize the animation
    }

    /**
     * Loads the Chaser's animation from a sprite sheet.
     */
    private void loadChaserAnimation() {
        Texture chaserSheet = new Texture(Gdx.files.internal("assets/character-male-d.png"));
        Array<TextureRegion> frames = new Array<>();

        // Assuming the frames are in the first row of the sprite sheet
        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(chaserSheet, i * 32, 0, 32, 32)); // Adjust frame size as per sprite sheet
        }

        chaserAnimation = new Animation<>(0.1f, frames, Animation.PlayMode.LOOP);
    }

    /**
     * Updates the Chaser's position and behavior.
     * The Chaser switches between random movement and targeting the player based on proximity.
     *
     * @param deltaTime The time elapsed since the last frame, used for smooth movement calculations.
     */
    public void update(float deltaTime) {
        Vector2 playerPosition = new Vector2(player.getX(), player.getY());
        Vector2 chaserPosition = new Vector2(x, y);

        if (chaserPosition.dst(playerPosition) <= CHASE_RADIUS) {
            // Pathfinding behavior: Move toward the player
            moveToPlayer(deltaTime, playerPosition);
        } else {
            // Random movement behavior
            randomMovement(deltaTime);
        }
    }

    /**
     * Handles the random movement of the Chaser.
     * The Chaser changes direction randomly when encountering obstacles.
     *
     * @param deltaTime The time elapsed since the last frame.
     */
    private void randomMovement(float deltaTime) {
        if (Utils.canCharacterMove(x, y, direction, mapLoader, false)) { // Enemies don't have keys
            moveStraight(deltaTime);
        } else {
            direction = Direction.getRandomDirection();
        }
    }

    /**
     * Moves the Chaser toward the player using simple targeting logic.
     *
     * @param deltaTime     The time elapsed since the last frame.
     * @param playerPosition The player's current position.
     */
    private void moveToPlayer(float deltaTime, Vector2 playerPosition) {
        if (playerPosition.x > x) {
            direction = Direction.RIGHT;
        } else if (playerPosition.x < x) {
            direction = Direction.LEFT;
        } else if (playerPosition.y > y) {
            direction = Direction.UP;
        } else if (playerPosition.y < y) {
            direction = Direction.DOWN;
        }

        if (Utils.canCharacterMove(x, y, direction, mapLoader,false)) {
            moveStraight(deltaTime);
        } else {
            direction = Direction.getRandomDirection(); // Switch to a random direction if blocked
        }
    }

    /**
     * Moves the Chaser in its current direction.
     *
     * @param deltaTime The time elapsed since the last frame.
     */
    private void moveStraight(float deltaTime) {
        switch (direction) {
            case RIGHT -> x += speed * deltaTime;
            case LEFT -> x -= speed * deltaTime;
            case UP -> y += speed * deltaTime;
            case DOWN -> y -= speed * deltaTime;
        }
    }

    /**
     * Renders the Chaser on the screen using the specified SpriteBatch.
     *
     * @param spriteBatch The SpriteBatch used for rendering.
     */
    public void render(SpriteBatch spriteBatch) {
        animationTime += Gdx.graphics.getDeltaTime();
        spriteBatch.draw(
                chaserAnimation.getKeyFrame(animationTime, true),
                x * 32, y * 32, 32, 32 // Render at the scaled position
        );
    }

    /**
     * Gets the current x-coordinate of the Chaser.
     *
     * @return The current x-coordinate.
     */
    public float getX() {
        return x;
    }

    /**
     * Gets the current y-coordinate of the Chaser.
     *
     * @return The current y-coordinate.
     */
    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getSpeed() {
        return speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public MapLoader getMapLoader() {
        return mapLoader;
    }

    public Random getRandom() {
        return random;
    }

    public Animation<TextureRegion> getChaserAnimation() {
        return chaserAnimation;
    }

    public void setChaserAnimation(Animation<TextureRegion> chaserAnimation) {
        this.chaserAnimation = chaserAnimation;
    }

    public float getAnimationTime() {
        return animationTime;
    }

    public void setAnimationTime(float animationTime) {
        this.animationTime = animationTime;
    }

    public Player getPlayer() {
        return player;
    }
}