package de.tum.cit.fop.maze;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import games.spooky.gdx.nativefilechooser.NativeFileChooser;
import games.spooky.gdx.nativefilechooser.NativeFileChooserCallback;
import games.spooky.gdx.nativefilechooser.NativeFileChooserConfiguration;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;


/**
 * The MazeRunnerGame class represents the core of the Maze Runner game.
 * It manages the screens and global resources like SpriteBatch and Skin.
 */
public class MazeRunnerGame extends Game{
    // Screens
    private MenuScreen menuScreen;
    private de.tum.cit.fop.maze.GameScreen gameScreen;
    // Sprite Batch for rendering
    private SpriteBatch spriteBatch;
    private double maxX;
    private double maxY;
    private double minX;
    private double minY;
    // UI Skin
    private Skin skin;
    private Hero hero;
    private final NativeFileChooser fileChooser;
    public static final Map<Point, Integer> mazeData = new HashMap<>();
    private de.tum.cit.fop.maze.Tiles allTiles;
    private Key key;
    private de.tum.cit.fop.maze.Entry entry;
    private de.tum.cit.fop.maze.MazeLoader mazeLoader;
    private de.tum.cit.fop.maze.Languages languages;
    private MusicLoader musicLoader;

    /**
     * Constructor for MazeRunnerGame.
     *
     * @param fileChooser The file chooser for the game, typically used in desktop environment.
     */
    public MazeRunnerGame(NativeFileChooser fileChooser) {
        super();
        this.fileChooser = fileChooser;
        this.maxX=0;
        this.maxY=0;
        this.mazeLoader = new de.tum.cit.fop.maze.MazeLoader(this);
        musicLoader = new MusicLoader();
    }

    /**
     * Shows the file chooser dialog to select a maze file.
     */
    public void showFileChooser() {
        NativeFileChooserConfiguration conf = new NativeFileChooserConfiguration();
        conf.directory = Gdx.files.internal("maps");
        fileChooser.chooseFile(conf, new NativeFileChooserCallback() {
            @Override
            public void onFileChosen(FileHandle file) {
                mazeLoader.loadMazeData(file.path());
                createMaze();
                renderMaze();
                goToGame();
            }

            @Override
            public void onCancellation() {
                // Handle cancellation if needed
            }

            @Override
            public void onError(Exception exception) {
                // Handle error (use exception type)
            }
        });
    }
    /**
     * Called when the game is created. Initializes the SpriteBatch, Skin and menu music. It also loads all the musics and sound effects..
     */
    @Override
    public void create() {
        spriteBatch = new SpriteBatch(); // Create SpriteBatch
        skin = new Skin(Gdx.files.internal("craft/craftacular-ui.json")); // Load UI skin
        this.allTiles = new Tiles();
        musicLoader.loadMusic(this);
        musicLoader.setVolumes();
        musicLoader.getCurrentMusic();
        if (!musicLoader.isForbiddenMenu()) {
            musicLoader.playMenuMusic();
        }
        createMaze();
        this.hero = new Hero(0,0);
        mazeLoader.createObjects();
        this.allTiles = new Tiles();
        this.languages = new de.tum.cit.fop.maze.Languages();
        goToMenu();
    }


    /**
     * Switches to the menu screen.
     */
    public void goToMenu() {
        this.setScreen(new MenuScreen(this)); // Set the current screen to MenuScreen
        if (gameScreen != null) {
            gameScreen.dispose(); // Dispose the game screen if it exists
            gameScreen = null;
        }
    }
    /**
     * Switches to the game screen.
     */
    public void goToGame() {
        this.setScreen(new de.tum.cit.fop.maze.GameScreen(this)); // Set the current screen to GameScreen
        if (menuScreen != null) {
            menuScreen.dispose(); // Dispose the menu screen if it exists
            menuScreen = null;
        }
        if (gameScreen != null) {
            gameScreen.setMazeLoader(mazeLoader);
        }
    }
    /**
     * Loads the character animation from the character.png file.
     */
    public void createMaze() {
        mazeLoader.calculateMaxCoordinates();
        mazeLoader.addGround();
    }

    public void renderMaze() {
        mazeLoader.renderMaze();
    }
    /**
     * Cleans up resources when the game is disposed.
     */
    @Override
    public void dispose() {
        if (getScreen() != null) {
            getScreen().hide();
            getScreen().dispose();
        }
        // Dispose SpriteBatch and Skin
        spriteBatch.dispose();
        skin.dispose();
    }

    // Getter methods
    public Skin getSkin() {
        return skin;
    }


    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public Map<Point, Integer> getMazeData() {
        return mazeData;
    }

    public Hero getHero() {
        return hero;
    }


    public double getMaxX() {
        return maxX;
    }

    public void setMaxX(double maxX) {
        this.maxX = maxX;
    }

    public double getMaxY() {
        return maxY;
    }

    public void setMaxY(double maxY) {
        this.maxY = maxY;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Tiles getAllTiles() {
        return allTiles;
    }

    public de.tum.cit.fop.maze.MazeLoader getMazeLoader() {
        return mazeLoader;
    }

    public Key getKey() {
        return key;
    }

    public de.tum.cit.fop.maze.Entry getEntry() {
        return entry;
    }

    public de.tum.cit.fop.maze.Languages getLanguages() {
        return languages;
    }

    public void setMinY(double minY) {
        this.minY = minY;
    }

    public double getMinX() {
        return minX;
    }

    public double getMinY() {
        return minY;
    }

    public void setMinX(double minX) {
        this.minX = minX;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public void setEntry(de.tum.cit.fop.maze.Entry entry) {
        this.entry = entry;
    }

    public MusicLoader getMusicLoader() {
        return musicLoader;
    }

}