package de.tum.cit.fop.maze.GameElements.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.tum.cit.fop.maze.MazeRunnerGame;

/**
 * The LoseScreen class displays a screen when the player loses the game.
 * It includes background music, a background image, and navigation instructions.
 */
public class LoseScreen implements Screen {

    private final MazeRunnerGame game;
    private final Stage stage;
    private final Music backgroundMusic;

    /**
     * Constructor for LoseScreen. Sets up the camera, viewport, stage, background music, and UI elements.
     *
     * @param game The main game instance.
     */
    public LoseScreen(MazeRunnerGame game) {
        this.game = game;

        // Initialize the camera and viewport
        OrthographicCamera camera = new OrthographicCamera();
        Viewport viewport = new ScreenViewport(camera);
        stage = new Stage(viewport, game.getSpriteBatch());

        // Set up UI components
        setupUI();

        // Load and configure background music
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("LooseWinMusic.wav"));
        backgroundMusic.setVolume(0.8f);
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
    }

    /**
     * Sets up the UI components for the Lose screen.
     */
    private void setupUI() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Add a background image
        Image backgroundImage = new Image(new Texture(Gdx.files.internal("LooseImage.png")));
        table.setBackground(backgroundImage.getDrawable());

        // Add a label with instructions
        Label instructions = new Label("Press SPACE to return to Main Menu!", game.getSkin(), "title");
        instructions.setFontScale(1.5f); // Make the text more prominent
        table.bottom().add(instructions).padBottom(30); // Place the label at the bottom with padding
    }

    @Override
    public void show() {
        // Set the input processor to the stage
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        // Handle input for returning to the main menu
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            backgroundMusic.stop();
            backgroundMusic.dispose();
            game.goToMenu();
        }

        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1); // Dark background
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update and draw the stage
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        // Dispose of resources
        stage.dispose();
        if (backgroundMusic != null) {
            backgroundMusic.dispose();
        }
    }

    // Unused methods in this screen
    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
}