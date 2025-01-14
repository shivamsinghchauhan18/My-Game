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
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.tum.cit.fop.maze.MazeRunnerGame;

/**
 * The WinScreen class displays the winning screen after the player reaches the exit successfully.
 * It provides navigation back to the main menu and includes background music and visual elements.
 */
public class WinScreen implements Screen {

    private final MazeRunnerGame game;
    private final Stage stage;
    private final Music backgroundMusic;

    /**
     * Constructor for WinScreen. Sets up the camera, viewport, stage, background music, and UI elements.
     *
     * @param game The main game instance.
     */
    public WinScreen(MazeRunnerGame game) {
        this.game = game;

        // Initialize camera and viewport
        OrthographicCamera camera = new OrthographicCamera();
        Viewport viewport = new ScreenViewport(camera);
        stage = new Stage(viewport, game.getSpriteBatch());

        // Set up UI components
        setupUI();

        // Load and play background music
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("LooseWinMusic.wav"));
        backgroundMusic.setVolume(0.8f);
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
    }

    /**
     * Sets up the UI components for the Win screen.
     */
    private void setupUI() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Add background image
        Image backgroundImage = new Image(new Texture(Gdx.files.internal("WinImage.png")));
        table.setBackground(backgroundImage.getDrawable());

        // Add instruction label
        Label instructionLabel = new Label("You Win! Press SPACE to return to Main Menu", game.getSkin(), "title");
        instructionLabel.setFontScale(1.5f);
        table.bottom().add(instructionLabel).padBottom(30);
    }

    @Override
    public void show() {
        // Set the input processor to the stage
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        // Handle input for navigating back to the main menu
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            backgroundMusic.stop();
            game.goToMenu();
        }

        // Clear the screen with a dark background
        ScreenUtils.clear(0, 0, 0, 1);

        // Update and draw the stage
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Update the viewport on screen resize
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