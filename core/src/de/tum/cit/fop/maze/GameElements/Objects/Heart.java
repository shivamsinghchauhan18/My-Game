package de.tum.cit.fop.maze.GameElements.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Represents animated and static heart elements for the MazeRunnerGame.
 * These hearts can be used for HUD (static) or as collectible life indicators (animated).
 */
public class Heart {

    // Texture regions for static hearts
    private TextureRegion fullHeart;
    private TextureRegion emptyHeart;

    // Animation for a dynamic heart (e.g., collectible life)
    private Animation<TextureRegion> animatedHeart;

    // Constants for file path and frame details
    private static final String HEART_TEXTURE_PATH = "objects.png";
    private static final int FRAME_WIDTH = 16;
    private static final int FRAME_HEIGHT = 16;
    private static final float ANIMATION_FRAME_DURATION = 0.2f; // Duration per frame
    private static final int ANIMATION_FRAME_COUNT = 4;

    /**
     * Constructs a Heart object and initializes textures and animations.
     */
    public Heart() {
        loadStaticHearts();
        loadHeartAnimation();
    }

    /**
     * Loads the static textures for full and empty hearts.
     * These textures are used for HUD elements like life indicators.
     */
    private void loadStaticHearts() {
        Texture heartSheet = new Texture(Gdx.files.internal(HEART_TEXTURE_PATH));
        fullHeart = new TextureRegion(heartSheet, 4 * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT);
        emptyHeart = new TextureRegion(heartSheet, 8 * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT);
    }

    /**
     * Loads the animation for a dynamic heart (e.g., collectible or life representation).
     */
    private void loadHeartAnimation() {
        Texture heartSheet = new Texture(Gdx.files.internal(HEART_TEXTURE_PATH));
        Array<TextureRegion> frames = new Array<>(ANIMATION_FRAME_COUNT);

        // Extract frames for animation
        for (int col = 0; col < ANIMATION_FRAME_COUNT; col++) {
            frames.add(new TextureRegion(heartSheet, col * FRAME_WIDTH, 3 * FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT));
        }

        animatedHeart = new Animation<>(ANIMATION_FRAME_DURATION, frames, Animation.PlayMode.LOOP);
    }

    /**
     * Disposes of the textures used by the heart object to free up memory resources.
     */
    public void dispose() {
        if (fullHeart != null) fullHeart.getTexture().dispose();
        if (emptyHeart != null) emptyHeart.getTexture().dispose();
        if (animatedHeart != null && animatedHeart.getKeyFrames().length > 0) {
            animatedHeart.getKeyFrames()[0].getTexture().dispose();
        }
    }

    // Getters for heart textures and animations

    /**
     * Gets the texture region for a full heart (HUD element).
     *
     * @return The full heart texture region.
     */
    public TextureRegion getFullHeart() {
        return fullHeart;
    }

    /**
     * Gets the texture region for an empty heart (HUD element).
     *
     * @return The empty heart texture region.
     */
    public TextureRegion getEmptyHeart() {
        return emptyHeart;
    }

    /**
     * Gets the animation for a dynamic heart (e.g., collectible life).
     *
     * @return The animated heart.
     */
    public Animation<TextureRegion> getAnimatedHeart() {
        return animatedHeart;
    }
}