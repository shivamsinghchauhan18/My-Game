package de.tum.cit.fop.maze;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Hero extends Character {


    private String direction;
    private int lives = 5;
    private boolean keyCollected;
    private boolean winner;
    private boolean dead;
    private float danceTimer;
    private final Animation<TextureRegion> danceAnimation;
    private final Animation<TextureRegion> cryAnimation;
    private int score;
    private MazeRunnerGame game;
    private float jumpStartY; // The Y-coordinate where the jump started
    private float jumpMaxY;   // The maximum Y-coordinate during the jump


    private boolean isJumping = false; // Indicates if the hero is jumping
    private boolean isFalling = false; // Indicates if the hero is falling
    private float jumpVelocity = 100;  // Initial velocity of the jump
    private float gravity = 200;       // Gravity acceleration
    private float groundLevel;         // The Y-coordinate of the ground
    private float maxJumpHeight;       // Maximum height the hero can reach during a jump


    /**
     * Constructs a Hero with the specified initial position (x, y).
     *
     * @param x The initial x-coordinate of the hero's position.
     * @param y The initial y-coordinate of the hero's position.
     */
    public Hero(float x, float y, MazeRunnerGame game) {
        super(x, y, 40, 40);
        this.game = game;
        this.groundLevel = y;          // Set the initial ground level to the starting Y-coordinate
        this.maxJumpHeight = groundLevel + 150; // Example max jump height

        try {
            // Load texture using ClassLoader
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("character.png");
            if (inputStream == null) {
                throw new FileNotFoundException("character.png not found in resources.");
            }

            // Load texture with AssetManager (preferred for libGDX)
            Texture texture = game.getAssetManager().get("character.png", Texture.class);

            // Initialize animations
            this.leftAnimation = createAnimation(texture, 0, 96, 16, 32, 4, 0.1f);
            this.downAnimation = createAnimation(texture, 0, 0, 16, 32, 4, 0.1f);
            this.rightAnimation = createAnimation(texture, 0, 32, 16, 32, 4, 0.1f);
            this.upAnimation = createAnimation(texture, 0, 64, 16, 32, 4, 0.1f);
            this.animation = createAnimation(texture, 0, 0, 16, 32, 1, 0.1f);
            this.cryAnimation = createAnimation(texture, 80, 0, 16, 32, 1, 0.25f);
            this.danceAnimation = createAnimation(texture, 96, 0, 16, 32, 2, 0.25f);

            // Initialize hero state
            this.keyCollected = false;
            this.dead = false;
            this.winner = false;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load texture: character.png");
        }
    }

    private Animation<TextureRegion> createAnimation(Texture texture, int startX, int startY, int frameWidth, int frameHeight, int frameCount, float frameDuration) {
        TextureRegion[] frames = new TextureRegion[frameCount];

        // Create frames by slicing the texture
        for (int i = 0; i < frameCount; i++) {
            frames[i] = new TextureRegion(texture, startX + (i * frameWidth), startY, frameWidth, frameHeight);
        }

        // Create and return an Animation
        return new Animation<>(frameDuration, frames);
    }

    /**
     * Updates the hero's state based on the elapsed time.
     *
     * @param delta The time in seconds since the last update.
     */
    @Override
    public void update(float delta) {
        setDirection(direction);
        sinusInput += delta;
    }

    /**
     * Draws the hero.
     * @param spriteBatch The SpriteBatch used for rendering graphics.
     */
    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(
                super.getCurrentAnimation().getKeyFrame(sinusInput, true),
                x,
                y,rect.width,rect.height*2
        );
    }

    /**
     * It enables hero to move left.
     * @param delta The distance to move the object.
     */
    public void moveLeft(float delta) {
        setPrevX(x);
        x -= delta;
    }

    /**
     * It enables hero to move right.
     * @param delta The distance to move the object.
     */
    public void moveRight(float delta) {
        setPrevX(x);
        x += delta;
    }

    /**
     * It enables hero to move up.
     * @param delta The distance to move the object.
     */
    public void moveUp(float delta) {
        setPrevY(y);
        y += delta;
    }

    /**
     * It enables hero to move down.
     * @param delta The distance to move the object.
     */
    public void moveDown(float delta) {
        setPrevY(y);
        y -= delta;
    }

    public void collectKey(Key key) {
        if (this.rect.overlaps(key.getRect())) { // Check collision
            this.keyCollected = true;
            System.out.println("Key collected!");
            increaseScore(100); // Add 100 points for collecting the key
        }
    }

    public void startJump() {
        if (!isJumping && !isFalling) {
            isJumping = true;
            jumpStartY = y; // Store the current Y-coordinate

            // Restrict max jump height near the top boundary
            float mazeTopBoundary = game.getMazeLoader().getTop().y;
            if (y + 2 > mazeTopBoundary - 100) {
                // Restrict to 2 units below the top boundary
                jumpMaxY = mazeTopBoundary - 100;
            } else {
                jumpMaxY = jumpStartY + 50; // Normal max height for jump
            }
        }
    }

    public void setScore(int score) {
        this.score = score;
    }

    public MazeRunnerGame getGame() {
        return game;
    }

    public void setGame(MazeRunnerGame game) {
        this.game = game;
    }

    public boolean isJumping() {
        return isJumping;
    }

    public void setJumping(boolean jumping) {
        isJumping = jumping;
    }

    public boolean isFalling() {
        return isFalling;
    }

    public void setFalling(boolean falling) {
        isFalling = falling;
    }

    public float getJumpVelocity() {
        return jumpVelocity;
    }

    public void setJumpVelocity(float jumpVelocity) {
        this.jumpVelocity = jumpVelocity;
    }

    public float getGravity() {
        return gravity;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public float getGroundLevel() {
        return groundLevel;
    }

    public void setGroundLevel(float groundLevel) {
        this.groundLevel = groundLevel;
    }

    public float getMaxJumpHeight() {
        return maxJumpHeight;
    }

    public void setMaxJumpHeight(float maxJumpHeight) {
        this.maxJumpHeight = maxJumpHeight;
    }

    public void updateJump(float delta) {
        if (isJumping) {
            // Move upward
            y += 50 * delta; // Adjust the speed of the ascent
            if (y >= jumpMaxY) {
                y = jumpMaxY;  // Snap to the peak
                isJumping = false;
                isFalling = true; // Start falling
            }
        } else if (isFalling) {
            // Move downward
            y -= 50 * delta; // Adjust the speed of the descent

            // Ensure the hero doesn't fall below the ground
            if (y <= jumpStartY) {
                y = jumpStartY; // Snap back to the starting position
                isFalling = false; // End the jump
            }
        }

        // Prevent the hero from exceeding maze boundaries
        if (y > game.getMazeLoader().getTop().y) {
            y = game.getMazeLoader().getTop().y; // Clamp to maze top boundary
            isJumping = false;
            isFalling = true;
        }
    }

    public void checkJumpCollision(MazeLoader mazeLoader) {
        for (Rectangle platform : mazeLoader.getPlatforms()) {
            if (getRect().overlaps(platform)) {
                // If jumping up, stop at the platform
                if (isJumping && y + rect.height <= platform.y) {
                    y = platform.y - rect.height; // Snap to the bottom of the platform
                    isJumping = false;
                    isFalling = true;
                    break;
                }
                // If falling, land on the platform
                if (isFalling && y >= platform.y) {
                    y = platform.y + platform.height; // Snap to the top of the platform
                    isFalling = false;
                    jumpVelocity = 300; // Reset velocity
                    groundLevel = platform.y + platform.height; // Update ground level
                    break;
                }
            }
        }
    }


    public void increaseScore(int points) {
        this.score += points;
    }

    public int getScore() {
        return score;
    }


    // Getters & Setters

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public boolean isKeyCollected() {
        return keyCollected;
    }

    public void setKeyCollected(boolean keyCollected) {
        this.keyCollected = keyCollected;
    }
    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }
    public float getDanceTimer() {
        return danceTimer;
    }
    public void setDanceTimer(float danceTimer) {
        this.danceTimer = danceTimer;
    }

    public Animation<TextureRegion> getDanceAnimation() {
        return danceAnimation;
    }
    public Animation<TextureRegion> getCryAnimation() {
        return cryAnimation;
    }
}














