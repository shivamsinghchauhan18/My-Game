package core.GameElements.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Represents decorative objects in the MazeRunnerGame, such as boats, water, trees, and staircases.
 * This class handles the loading and management of textures and sprites used for decoration.
 */
public class Decoration {

    // Decorative elements
    private Sprite boat;
    private TextureRegion water;
    private TextureRegion tree;
    private TextureRegion staircase;

    // File paths for texture resources
    private static final String BOAT_TEXTURE_PATH = "Boat.png";
    private static final String DUNGEON_TEXTURE_PATH = "Isometric Miniature Dungeon.png";
    private static final String TREE_TEXTURE_PATH = "basictiles.png";
    private static final String STAIRCASE_TEXTURE_PATH = "basictiles.png";

    /**
     * Constructs a Decoration object and initializes all decorative textures.
     */
    public Decoration() {
        loadDecorations();
    }

    /**
     * Loads all decorative textures and sprites.
     * This method ensures that all textures are loaded and ready for use.
     */
    private void loadDecorations() {
        loadBoat();
        loadWater();
        loadTree();
        loadStaircase();
    }

    /**
     * Loads the boat sprite from the texture file.
     */
    private void loadBoat() {
        Texture boatTexture = new Texture(Gdx.files.internal(BOAT_TEXTURE_PATH));
        boat = new Sprite(boatTexture, 0, 0, 80, 32); // Sprite with specific region
    }

    /**
     * Loads the water texture region from the updated dungeon texture file.
     */
    private void loadWater() {
        Texture dungeonTexture = new Texture(Gdx.files.internal(DUNGEON_TEXTURE_PATH));
        water = new TextureRegion(dungeonTexture, 5 * 32, 4 * 32, 32, 32); // Extract region for water
    }

    /**
     * Loads the tree texture region from the texture file.
     */
    private void loadTree() {
        Texture treeTexture = new Texture(Gdx.files.internal(TREE_TEXTURE_PATH));
        tree = new TextureRegion(treeTexture, 16 * 4, 9 * 16, 16, 16); // Extract region for tree
    }

    /**
     * Loads the staircase texture region from the texture file.
     */
    private void loadStaircase() {
        Texture staircaseTexture = new Texture(Gdx.files.internal(STAIRCASE_TEXTURE_PATH));
        staircase = new TextureRegion(staircaseTexture, 16, 7 * 16, 16, 16); // Extract region for staircase
    }

    /**
     * Disposes of all textures used in decorations to free up memory.
     * This should be called when the game or level is finished.
     */
    public void dispose() {
        if (boat != null) boat.getTexture().dispose();
        if (water != null) water.getTexture().dispose();
        if (tree != null) tree.getTexture().dispose();
        if (staircase != null) staircase.getTexture().dispose();
    }

    // Getters for decorative elements

    /**
     * Gets the boat sprite.
     *
     * @return The boat sprite.
     */
    public Sprite getBoat() {
        return boat;
    }

    /**
     * Gets the water texture region.
     *
     * @return The water texture region.
     */
    public TextureRegion getWater() {
        return water;
    }

    /**
     * Gets the tree texture region.
     *
     * @return The tree texture region.
     */
    public TextureRegion getTree() {
        return tree;
    }

    /**
     * Gets the staircase texture region.
     *
     * @return The staircase texture region.
     */
    public TextureRegion getStaircase() {
        return staircase;
    }
}