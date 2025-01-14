package de.tum.cit.fop.maze.GameElements.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Represents the Wall in the MazeRunnerGame.
 * This class includes different kinds of wall textures and decorative textures.
 */
public class Walls {

    // Wall textures and sprites
    private Sprite horizontalWall;
    private TextureRegion cornerWall;
    private TextureRegion stoneWall;

    // Updated to use Isometric Miniature Dungeon
    private TextureRegion dungeonWallCornerUpLeft;
    private TextureRegion dungeonWallCornerUpRight;
    private TextureRegion dungeonWallCornerDownLeft;
    private TextureRegion dungeonWallCornerDownRight;
    private TextureRegion dungeonWallUp;
    private TextureRegion dungeonWallDown;
    private TextureRegion dungeonWallRight;
    private TextureRegion dungeonWallLeft;

    private TextureRegion wood;

    // File paths for textures
    private static final String BASIC_TILES_PATH = "basictiles.png";
    private static final String DUNGEON_TEXTURE_PATH = "Isometric Miniature Dungeon.png";
    private static final String DECOR_TEXTURE_PATH = "decor.png";

    /**
     * Constructs a Wall object and initializes all wall textures and regions.
     */
    public Walls() {
        loadBasicWalls();
        loadDungeonWalls();
        loadWood();
    }

    /**
     * Loads basic wall textures from `basictiles.png`.
     */
    private void loadBasicWalls() {
        Texture wallTexture = new Texture(Gdx.files.internal(BASIC_TILES_PATH));

        horizontalWall = new Sprite(wallTexture, 16 * 2, 0, 16, 16); // Horizontal wall
        cornerWall = new TextureRegion(wallTexture, 16 * 3, 0, 16, 16); // Corner wall
        stoneWall = new TextureRegion(wallTexture, 16 * 2, 7 * 16, 16, 16); // Stone wall
    }

    /**
     * Loads dungeon wall textures from `Isometric Miniature Dungeon.png`.
     */
    private void loadDungeonWalls() {
        Texture dungeonTexture = new Texture(Gdx.files.internal(DUNGEON_TEXTURE_PATH));

        int tileWidth = 64; // Adjusted to match isometric tile dimensions
        int tileHeight = 64;

        dungeonWallCornerUpLeft = new TextureRegion(dungeonTexture, 0, 0, tileWidth, tileHeight);
        dungeonWallCornerUpRight = new TextureRegion(dungeonTexture, tileWidth, 0, tileWidth, tileHeight);
        dungeonWallCornerDownLeft = new TextureRegion(dungeonTexture, 0, tileHeight, tileWidth, tileHeight);
        dungeonWallCornerDownRight = new TextureRegion(dungeonTexture, tileWidth, tileHeight, tileWidth, tileHeight);

        dungeonWallUp = new TextureRegion(dungeonTexture, 2 * tileWidth, 0, tileWidth, tileHeight);
        dungeonWallDown = new TextureRegion(dungeonTexture, 2 * tileWidth, tileHeight, tileWidth, tileHeight);
        dungeonWallRight = new TextureRegion(dungeonTexture, 3 * tileWidth, 0, tileWidth, tileHeight);
        dungeonWallLeft = new TextureRegion(dungeonTexture, 3 * tileWidth, tileHeight, tileWidth, tileHeight);
    }

    /**
     * Loads decorative wood texture from `decor.png`.
     */
    private void loadWood() {
        Texture decorTexture = new Texture(Gdx.files.internal(DECOR_TEXTURE_PATH));
        wood = new TextureRegion(decorTexture, 24, 2 * 24, 24, 24); // Decorative wood texture
    }

    /**
     * Disposes of all textures to free up memory.
     * This method should be called when the game or level ends.
     */
    public void dispose() {
        if (horizontalWall != null) horizontalWall.getTexture().dispose();
        if (cornerWall != null) cornerWall.getTexture().dispose();
        if (stoneWall != null) stoneWall.getTexture().dispose();
        if (dungeonWallCornerUpLeft != null) dungeonWallCornerUpLeft.getTexture().dispose();
        if (wood != null) wood.getTexture().dispose();
    }

    // Getters for wall textures and regions

    public Sprite getHorizontalWall() {
        return horizontalWall;
    }

    public TextureRegion getCornerWall() {
        return cornerWall;
    }

    public TextureRegion getStoneWall() {
        return stoneWall;
    }

    public TextureRegion getDungeonWallCornerUpLeft() {
        return dungeonWallCornerUpLeft;
    }

    public TextureRegion getDungeonWallCornerUpRight() {
        return dungeonWallCornerUpRight;
    }

    public TextureRegion getDungeonWallCornerDownLeft() {
        return dungeonWallCornerDownLeft;
    }

    public TextureRegion getDungeonWallCornerDownRight() {
        return dungeonWallCornerDownRight;
    }

    public TextureRegion getDungeonWallUp() {
        return dungeonWallUp;
    }

    public TextureRegion getDungeonWallDown() {
        return dungeonWallDown;
    }

    public TextureRegion getDungeonWallRight() {
        return dungeonWallRight;
    }

    public TextureRegion getDungeonWallLeft() {
        return dungeonWallLeft;
    }

    public TextureRegion getWood() {
        return wood;
    }
}