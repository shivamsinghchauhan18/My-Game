package de.tum.cit.fop.maze.GameElements.Characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.tum.cit.fop.maze.Direction;
import de.tum.cit.fop.maze.MapLoader;
import de.tum.cit.fop.maze.Utils;

/**
 * Represents an enemy character in the MazeRunnerGame.
 * The Enemy class manages the position, movement, rendering, and behavior of enemies.
 */
public class Enemy {

    // Position and movement properties
    private float x, y; // Current position of the enemy
    private final float speed; // Movement speed of the enemy
    private Direction direction; // Current direction of movement

    // Dependencies
    private final MapLoader mapLoader; // Map context to determine valid movement

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getSpeed() {
        return speed;
    }

    public MapLoader getMapLoader() {
        return mapLoader;
    }

    /**
     * Constructs an Enemy object with specified position, speed, direction, and map context.
     *
     * @param x          The initial x-coordinate of the enemy.
     * @param y          The initial y-coordinate of the enemy.
     * @param speed      The movement speed of the enemy.
     * @param direction  The initial direction of movement for the enemy.
     * @param mapLoader  The MapLoader instance, used for determining valid movement within the game map.
     */
    public Enemy(float x, float y, float speed, Direction direction, MapLoader mapLoader) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.direction = direction;
        this.mapLoader = mapLoader;
    }

    /**
     * Renders the enemy character using the provided sprite batch and animation.
     *
     * @param spriteBatch The SpriteBatch used for rendering.
     * @param animation   The animation object containing the enemy's sprite frames.
     * @param elapsedTime Time elapsed, used to determine the current frame of the animation.
     */
    public void render(SpriteBatch spriteBatch, Animation<TextureRegion> animation, float elapsedTime) {
        spriteBatch.draw(
                animation.getKeyFrame(elapsedTime, true),
                x * 32, y * 32, // Position (scaled by tile size)
                32, 32          // Size of the sprite
        );
    }

    /**
     * Updates the enemy's position and behavior. The enemy moves based on its current direction,
     * changing direction when encountering obstacles.
     *
     * @param deltaTime Time elapsed since the last frame, used for smooth movement calculations.
     */
    public void update(float deltaTime) {
        if (Utils.canCharacterMove(x, y, direction, mapLoader, false)) {
            move(deltaTime);
        } else {
            changeDirection();
        }
    }

    /**
     * Moves the enemy in its current direction based on speed and elapsed time.
     *
     * @param deltaTime Time elapsed since the last frame.
     */
    private void move(float deltaTime) {
        switch (direction) {
            case RIGHT -> x += speed * deltaTime;
            case LEFT -> x -= speed * deltaTime;
            case UP -> y += speed * deltaTime;
            case DOWN -> y -= speed * deltaTime;
        }
    }

    /**
     * Changes the enemy's direction to a new random direction, avoiding backtracking.
     * This method is invoked when the enemy encounters an obstacle.
     */
    private void changeDirection() {
        direction = Direction.getRandomDirectionExcept(direction);
    }

    /**
     * Gets the x-coordinate of the enemy's position.
     *
     * @return The current x-coordinate of the enemy.
     */
    public float getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the enemy's position.
     *
     * @return The current y-coordinate of the enemy.
     */
    public float getY() {
        return y;
    }

    /**
     * Gets the current direction of the enemy.
     *
     * @return The current direction the enemy is facing.
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Sets a new direction for the enemy.
     *
     * @param direction The new direction to set.
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}