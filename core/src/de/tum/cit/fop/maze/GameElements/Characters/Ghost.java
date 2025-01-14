package de.tum.cit.fop.maze.GameElements.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Represents a Ghost character in the MazeRunnerGame.
 * The Ghost class handles the animations and rendering of the ghost character.
 */
public class Ghost {

    // Animation for ghost movement
    private Animation<TextureRegion> ghostDownAnimation;

    // Constants for animation settings
    private static final String TEXTURE_PATH = "mobs.png"; // Path to the sprite sheet
    private static final int FRAME_WIDTH = 16;            // Width of each animation frame
    private static final int FRAME_HEIGHT = 16;           // Height of each animation frame
    private static final int START_COLUMN = 6;            // Starting column in the sprite sheet
    private static final int ROW = 4;                     // Row index in the sprite sheet
    private static final int FRAME_COUNT = 3;             // Total frames in the animation
    private static final float FRAME_DURATION = 0.08f;    // Duration of each frame in seconds

    /**
     * Constructor initializes and loads the ghost animations.
     */
    public Ghost() {
        loadGhostDownAnimation();
    }

    /**
     * Loads the downward movement animation for the ghost from the sprite sheet.
     */
    private void loadGhostDownAnimation() {
        Texture walkSheet = new Texture(Gdx.files.internal(TEXTURE_PATH));
        Array<TextureRegion> walkFrames = new Array<>(FRAME_COUNT);

        // Extract animation frames from the sprite sheet
        for (int col = START_COLUMN; col < START_COLUMN + FRAME_COUNT; col++) {
            walkFrames.add(new TextureRegion(walkSheet, col * FRAME_WIDTH, ROW * FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT));
        }

        ghostDownAnimation = new Animation<>(FRAME_DURATION, walkFrames, Animation.PlayMode.LOOP);
    }

    /**
     * Gets the animation for the ghost's downward movement.
     *
     * @return The Animation object for the ghost's downward movement.
     */
    public Animation<TextureRegion> getGhostDownAnimation() {
        return ghostDownAnimation;
    }

    public void setGhostDownAnimation(Animation<TextureRegion> ghostDownAnimation) {
        this.ghostDownAnimation = ghostDownAnimation;
    }
}