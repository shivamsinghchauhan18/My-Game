package de.tum.cit.fop.maze.GameElements.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.tum.cit.fop.maze.MazeRunnerGame;
import de.tum.cit.fop.maze.GameScreen;

/**
 * Represents the Escape Menu Screen in the MazeRunnerGame.
 * Provides options to continue the game, select a new map, or exit to the main menu.
 */
public class EscapeMenuScreen implements Screen {

    private final MazeRunnerGame game;
    private final Stage stage;
    private final GameScreen gameScreen;

    /**
     * Constructor for EscMenuScreen. Sets up the camera, viewport, stage, and UI elements.
     *
     * @param game       The main game class, used to access global resources and methods.
     * @param gameScreen The active GameScreen instance for resuming the game.
     */
    public EscapeMenuScreen(MazeRunnerGame game, GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;

        // Set up the camera and viewport
        OrthographicCamera camera = new OrthographicCamera();
        camera.zoom = 1.0f;
        Viewport viewport = new ScreenViewport(camera);
        stage = new Stage(viewport, game.getSpriteBatch());

        // Set up the UI layout
        setupUI();
    }

    public EscapeMenuScreen(MazeRunnerGame game, Stage stage, GameScreen gameScreen) {
        this.game = game;
        this.stage = stage;
        this.gameScreen = gameScreen;
    }

    /**
     * Sets up the UI elements and layout for the Escape Menu Screen.
     */
    private void setupUI() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Set up the background image
        Image backgroundImage = new Image(new Texture(Gdx.files.internal("MainMenuImage.jpg")));
        table.setBackground(backgroundImage.getDrawable());

        // Title label
        Label title = new Label("Pause Menu", game.getSkin(), "title");
        title.setFontScale(1.5f); // Scale the title for emphasis
        table.add(title).padBottom(50).row();

        // "Continue Game" button
        TextButton continueGameButton = new TextButton("Continue", game.getSkin());
        table.add(continueGameButton).width(300).pad(10).row();
        continueGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event

                    , Actor actor) {
                gameScreen.resume(); // Resume the game when the button is pressed
            }
        });

        // "Select New Map" button
        TextButton selectMapButton = new TextButton("Play New Map", game.getSkin());
        table.add(selectMapButton).width(300).pad(10).row();
        selectMapButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goToSelectMap(); // Navigate to the map selection screen
            }
        });

        // "Exit to Menu" button
        TextButton exitGameButton = new TextButton("Exit to Main Menu", game.getSkin());
        table.add(exitGameButton).width(300).pad(10).row();
        exitGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goToMenu(); // Navigate back to the main menu
            }
        });
    }

    @Override
    public void render(float delta) {
        // Clear the screen with a slightly transparent dark overlay
        Gdx.gl.glClearColor(0, 0, 0, 0.8f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update and draw the stage
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true); // Update viewport when resized
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage); // Set the input processor to the stage
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null); // Remove input processor when hidden
    }

    @Override
    public void dispose() {
        stage.dispose(); // Dispose of stage resources
    }
}