package de.tum.cit.fop.maze.GameElements.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Represents a door object in the MazeRunnerGame.
 * This class manages the texture and sprite for a horizontal door, typically used at exit coordinates.
 */
public class Door {

    // Door sprite
    private Sprite horizontalDoor;

    // File path for the door texture
    private static final String DOOR_TEXTURE_PATH = "basictiles.png";

    /**
     * Constructs a Door object and initializes its texture and sprite.
     */
    public Door() {
        loadHorizontalDoor();
    }

    /**
     * Loads the horizontal door texture and initializes the door sprite.
     */
    private void loadHorizontalDoor() {
        Texture doorTexture = new Texture(Gdx.files.internal(DOOR_TEXTURE_PATH));
        horizontalDoor = new Sprite(doorTexture, 2 * 16, 6 * 16, 16, 16); // Extracts specific tile
    }

    /**
     * Disposes of the door texture to free memory resources.
     * This should be called when the door object is no longer needed.
     */
    public void dispose() {
        if (horizontalDoor != null) {
            horizontalDoor.getTexture().dispose();
        }
    }

    /**
     * Gets the sprite representing the horizontal door.
     *
     * @return The horizontal door sprite.
     */
    public Sprite getHorizontalDoor() {
        return horizontalDoor;
    }
}