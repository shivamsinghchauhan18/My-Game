package core.GameElements.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Represents static and animated obstacles in the MazeRunnerGame.
 * This class manages animations for obstacles such as spikes, fire, flames, and poison.
 */
public class Obstacles {

    // Animations for different obstacles
    private Animation<TextureRegion> spikeAnimation;
    private Animation<TextureRegion> fireAnimation;
    private Animation<TextureRegion> flameAnimation;
    private Animation<TextureRegion> poisonAnimation;

    // Constants for texture file paths
    private static final String SPIKE_TEXTURE_PATH = "things.png";
    private static final String OBJECT_TEXTURE_PATH = "objects.png";

    /**
     * Constructs an Obstacle object and initializes all obstacle animations.
     */
    public Obstacle() {
        loadAnimations();
    }

    /**
     * Loads all obstacle animations by calling individual methods for each obstacle type.
     */
    private void loadAnimations() {
        loadSpikeAnimation();
        loadFireAnimation();
        loadFlameAnimation();
        loadPoisonAnimation();
    }

    /**
     * Loads the animation for spikes.
     * The animation cycles through frames and reverses to create a dynamic effect.
     */
    private void loadSpikeAnimation() {
        Texture spikeSheet = new Texture(Gdx.files.internal(SPIKE_TEXTURE_PATH));
        Array<TextureRegion> spikeFrames = new Array<>(6);

        int frameSize = 16;
        int startCol = 6;
        int row = 6;

        // Forward frames
        for (int col = startCol; col < startCol + 3; col++) {
            spikeFrames.add(new TextureRegion(spikeSheet, col * frameSize, row * frameSize, frameSize, frameSize));
        }

        // Reverse frames
        for (int col = startCol + 2; col >= startCol; col--) {
            spikeFrames.add(new TextureRegion(spikeSheet, col * frameSize, row * frameSize, frameSize, frameSize));
        }

        spikeAnimation = new Animation<>(0.4f, spikeFrames, Animation.PlayMode.LOOP);
    }

    /**
     * Loads the animation for fire.
     */
    private void loadFireAnimation() {
        Texture fireSheet = new Texture(Gdx.files.internal(OBJECT_TEXTURE_PATH));
        Array<TextureRegion> fireFrames = new Array<>(7);

        int frameSize = 16;
        int startCol = 5;
        int row = 3;

        for (int col = startCol; col < startCol + 7; col++) {
            fireFrames.add(new TextureRegion(fireSheet, col * frameSize, row * frameSize, frameSize, frameSize));
        }

        fireAnimation = new Animation<>(0.1f, fireFrames, Animation.PlayMode.LOOP);
    }

    /**
     * Loads the animation for flames.
     */
    private void loadFlameAnimation() {
        Texture flameSheet = new Texture(Gdx.files.internal(OBJECT_TEXTURE_PATH));
        Array<TextureRegion> flameFrames = new Array<>(9);

        int frameWidth = 32;
        int frameHeight = 32;
        int startCol = 8;
        int row = 1;

        for (int col = startCol; col < startCol + 9; col++) {
            flameFrames.add(new TextureRegion(flameSheet, col * frameWidth, row * frameHeight, frameWidth, frameHeight));
        }

        flameAnimation = new Animation<>(0.1f, flameFrames, Animation.PlayMode.LOOP);
    }

    /**
     * Loads the animation for poison.
     */
    private void loadPoisonAnimation() {
        Texture poisonSheet = new Texture(Gdx.files.internal(OBJECT_TEXTURE_PATH));
        Array<TextureRegion> poisonFrames = new Array<>(8);

        int frameWidth = 32;
        int frameHeight = 32;
        int startCol = 2;
        int row = 2;

        for (int col = startCol; col < startCol + 8; col++) {
            poisonFrames.add(new TextureRegion(poisonSheet, col * frameWidth, row * frameHeight, frameWidth, frameHeight));
        }

        poisonAnimation = new Animation<>(0.2f, poisonFrames, Animation.PlayMode.LOOP);
    }

    /**
     * Disposes of all textures used by the obstacle animations to free up memory.
     */
    public void dispose() {
        if (spikeAnimation != null) {
            spikeAnimation.getKeyFrames()[0].getTexture().dispose();
        }
        if (fireAnimation != null) {
            fireAnimation.getKeyFrames()[0].getTexture().dispose();
        }
        if (flameAnimation != null) {
            flameAnimation.getKeyFrames()[0].getTexture().dispose();
        }
        if (poisonAnimation != null) {
            poisonAnimation.getKeyFrames()[0].getTexture().dispose();
        }
    }

    // Getters for obstacle animations

    /**
     * Gets the spike animation.
     *
     * @return The spike animation.
     */
    public Animation<TextureRegion> getSpikeAnimation() {
        return spikeAnimation;
    }

    /**
     * Gets the fire animation.
     *
     * @return The fire animation.
     */
    public Animation<TextureRegion> getFireAnimation() {
        return fireAnimation;
    }

    /**
     * Gets the flame animation.
     *
     * @return The flame animation.
     */
    public Animation<TextureRegion> getFlameAnimation() {
        return flameAnimation;
    }

    /**
     * Gets the poison animation.
     *
     * @return The poison animation.
     */
    public Animation<TextureRegion> getPoisonAnimation() {
        return poisonAnimation;
    }
}