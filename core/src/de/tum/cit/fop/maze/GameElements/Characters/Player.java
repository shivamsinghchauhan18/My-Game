package de.tum.cit.fop.maze.GameElements.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import de.tum.cit.fop.maze.Direction;

/**
 * Represents the player character in the MazeRunnerGame.
 * Manages the player's animations, position, and state within the game.
 */
public class Player {

    // Animations for different movement directions
    private Animation<TextureRegion> characterDownAnimation;
    private Animation<TextureRegion> characterRightAnimation;
    private Animation<TextureRegion> characterUpAnimation;
    private Animation<TextureRegion> characterLeftAnimation;

    // Static textures for idle states
    private TextureRegion characterDown;
    private TextureRegion characterRight;
    private TextureRegion characterUp;
    private TextureRegion characterLeft;

    // Animation states
    private boolean isAnimating = false;
    private float animationTime = 0f;
    private Animation<TextureRegion> currentAnimation = null;

    // Player properties
    private float x;
    private float y;
    private boolean hasKey = false;
    private TextureRegion defaultFrame;
    private int heartCount = 3;

    // Constants for animation settings
    private static final String TEXTURE_PATH = "character.png";
    private static final int FRAME_WIDTH = 16;
    private static final int FRAME_HEIGHT = 32;
    private static final int FRAME_COUNT = 4;
    private static final float FRAME_DURATION = 0.08f;

    /**
     * Constructs a Player object with the specified initial position.
     * Loads various character animations and sets the default frame for the player.
     *
     * @param x The initial x-coordinate of the player.
     * @param y The initial y-coordinate of the player.
     */
    public Player(float x, float y) {
        this.x = x;
        this.y = y;
        loadAnimations();
        loadStaticFrames();
        defaultFrame = characterDown; // Default idle frame
    }

    /**
     * Loads animations for all movement directions.
     */
    private void loadAnimations() {
        characterDownAnimation = loadAnimation(0);
        characterRightAnimation = loadAnimation(1);
        characterUpAnimation = loadAnimation(2);
        characterLeftAnimation = loadAnimation(3);
    }

    /**
     * Loads a specific animation from the sprite sheet based on the row index.
     *
     * @param row The row in the sprite sheet representing the animation direction.
     * @return The Animation object for the specified direction.
     */
    private Animation<TextureRegion> loadAnimation(int row) {
        Texture spriteSheet = new Texture(Gdx.files.internal(TEXTURE_PATH));
        Array<TextureRegion> frames = new Array<>(FRAME_COUNT);

        for (int col = 0; col < FRAME_COUNT; col++) {
            frames.add(new TextureRegion(spriteSheet, col * FRAME_WIDTH, row * FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT));
        }

        return new Animation<>(FRAME_DURATION, frames, Animation.PlayMode.LOOP);
    }

    /**
     * Loads static textures for idle states in each direction.
     */
    private void loadStaticFrames() {
        Texture spriteSheet = new Texture(Gdx.files.internal(TEXTURE_PATH));

        characterDown = new TextureRegion(spriteSheet, 0, 0, FRAME_WIDTH, FRAME_HEIGHT);
        characterRight = new TextureRegion(spriteSheet, 0, FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT);
        characterUp = new TextureRegion(spriteSheet, 0, 2 * FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT);
        characterLeft = new TextureRegion(spriteSheet, 0, 3 * FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT);
    }

    /**
     * Starts the animation for the player character.
     *
     * @param animation The Animation object to start.
     */
    public void startAnimation(Animation<TextureRegion> animation) {
        this.currentAnimation = animation;
        this.animationTime = 0f;
        this.isAnimating = true;
    }

    /**
     * Updates the player's animation state.
     *
     * @param deltaTime The time elapsed since the last frame.
     */
    public void update(float deltaTime) {
        if (isAnimating && currentAnimation != null) {
            animationTime += deltaTime;

            if (animationTime >= currentAnimation.getAnimationDuration()) {
                isAnimating = false;
                animationTime = 0f;
            }
        }
    }

    /**
     * Retrieves the current frame of the player's animation.
     *
     * @return The current TextureRegion frame of the animation, or the default frame if idle.
     */
    public TextureRegion getCurrentFrame() {
        if (isAnimating && currentAnimation != null) {
            return currentAnimation.getKeyFrame(animationTime, true);
        }
        return defaultFrame;
    }

    // Getters and setters for player properties

    public boolean isAnimating() {
        return isAnimating;
    }

    public Animation<TextureRegion> getCharacterDownAnimation() {
        return characterDownAnimation;
    }

    public Animation<TextureRegion> getCharacterRightAnimation() {
        return characterRightAnimation;
    }

    public Animation<TextureRegion> getCharacterUpAnimation() {
        return characterUpAnimation;
    }

    public Animation<TextureRegion> getCharacterLeftAnimation() {
        return characterLeftAnimation;
    }

    public TextureRegion getCharacterDown() {
        return characterDown;
    }

    public TextureRegion getCharacterRight() {
        return characterRight;
    }

    public TextureRegion getCharacterUp() {
        return characterUp;
    }

    public TextureRegion getCharacterLeft() {
        return characterLeft;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean getHasKey() {
        return hasKey;
    }

    public void setHasKey(boolean hasKey) {
        this.hasKey = hasKey;
    }

    public TextureRegion getDefaultFrame() {
        return defaultFrame;
    }

    public void setDefaultFrame(TextureRegion defaultFrame) {
        this.defaultFrame = defaultFrame;
    }

    public int getHeartCount() {
        return heartCount;
    }

    public void setHeartCount(int heartCount) {
        this.heartCount = heartCount;
    }

    public void setCharacterDownAnimation(Animation<TextureRegion> characterDownAnimation) {
        this.characterDownAnimation = characterDownAnimation;
    }

    public void setCharacterRightAnimation(Animation<TextureRegion> characterRightAnimation) {
        this.characterRightAnimation = characterRightAnimation;
    }

    public void setCharacterUpAnimation(Animation<TextureRegion> characterUpAnimation) {
        this.characterUpAnimation = characterUpAnimation;
    }

    public void setCharacterLeftAnimation(Animation<TextureRegion> characterLeftAnimation) {
        this.characterLeftAnimation = characterLeftAnimation;
    }

    public void setCharacterDown(TextureRegion characterDown) {
        this.characterDown = characterDown;
    }

    public void setCharacterRight(TextureRegion characterRight) {
        this.characterRight = characterRight;
    }

    public void setCharacterUp(TextureRegion characterUp) {
        this.characterUp = characterUp;
    }

    public void setCharacterLeft(TextureRegion characterLeft) {
        this.characterLeft = characterLeft;
    }

    public void setAnimating(boolean animating) {
        isAnimating = animating;
    }

    public float getAnimationTime() {
        return animationTime;
    }

    public void setAnimationTime(float animationTime) {
        this.animationTime = animationTime;
    }

    public Animation<TextureRegion> getCurrentAnimation() {
        return currentAnimation;
    }

    public void setCurrentAnimation(Animation<TextureRegion> currentAnimation) {
        this.currentAnimation = currentAnimation;
    }

    public boolean isHasKey() {
        return hasKey;
    }

    public Animation<TextureRegion> getAnimationForDirection(Direction direction) {
        return switch (direction) {
            case UP -> getCharacterUpAnimation();
            case DOWN -> getCharacterDownAnimation();
            case LEFT -> getCharacterLeftAnimation();
            case RIGHT -> getCharacterRightAnimation();
        };
    }

    public TextureRegion getFrameForDirection(Direction direction) {
        return switch (direction) {
            case UP -> getCharacterUp();
            case DOWN -> getCharacterDown();
            case LEFT -> getCharacterLeft();
            case RIGHT -> getCharacterRight();
        };
    }
}