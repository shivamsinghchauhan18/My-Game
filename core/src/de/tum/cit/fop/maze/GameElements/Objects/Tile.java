package de.tum.cit.fop.maze.GameElements.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Represents the floor of the game in MazeRunnerGame.
 * This class includes textures for tiles and grass.
 */
public class Tile {

    // Texture regions for tiles
    private TextureRegion tile;
    private TextureRegion dungeonFloor;

    // File paths for textures
    private static final String BASIC_TILES_PATH = "basictiles.png";
    private static final String DUNGEON_TEXTURE_PATH = "Isometric Miniature Dungeon.png";

    /**
     * Constructs a Tile object and initializes all textures and regions.
     */
    public Tile() {
        loadTile();
        loadDungeonFloor();
    }

    /**
     * Loads the main tile texture (e.g., basic tile for floors).
     */
    private void loadTile() {
        Texture tileTexture = new Texture(Gdx.files.internal(BASIC_TILES_PATH));
        tile = new TextureRegion(tileTexture, 16, 8 * 16, 16, 16); // Extract a specific tile region
    }

    /**
     * Loads the dungeon floor texture from the Isometric Miniature Dungeon sprite sheet.
     */
    private void loadDungeonFloor() {
        Texture dungeonTexture = new Texture(Gdx.files.internal(DUNGEON_TEXTURE_PATH));
        dungeonFloor = new TextureRegion(dungeonTexture, 0, 0, 64, 64); // Adjusted to match the dungeon tile size
    }

    /**
     * Disposes of all textures used by this class to free memory resources.
     */
    public void dispose() {
        if (tile != null) tile.getTexture().dispose();
        if (dungeonFloor != null) dungeonFloor.getTexture().dispose();
    }

    // Getters for tile textures

    /**
     * Gets the basic tile texture region.
     *
     * @return The basic tile texture region.
     */
    public TextureRegion getTile() {
        return tile;
    }

    /**
     * Gets the dungeon floor texture region.
     *
     * @return The dungeon floor texture region.
     */
    public TextureRegion getDungeonFloor() {
        return dungeonFloor;
    }
}