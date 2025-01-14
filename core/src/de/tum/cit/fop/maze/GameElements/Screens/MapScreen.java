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

/**
 * The SelectMapScreen class provides a screen where players can select a map to play.
 * It displays a list of available maps and a back button to return to the main menu.
 */
public class MapScreen implements Screen {

    private final MazeRunnerGame game;
    private final Stage stage;

    /**
     * Constructor for SelectMapScreen. Sets up the camera, viewport, stage, and UI elements.
     *
     * @param game The main game instance.
     */
    public MapScreen(MazeRunnerGame game) {
        this.game = game;

        // Initialize the camera and viewport
        OrthographicCamera camera = new OrthographicCamera();
        Viewport viewport = new ScreenViewport(camera);
        stage = new Stage(viewport, game.getSpriteBatch());

        // Set up UI components
        setupUI();
    }

    /**
     * Sets up the UI components for the Select Map screen.
     */
    private void setupUI() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Add background image
        Image backgroundImage = new Image(new Texture(Gdx.files.internal("MainMenuImage.jpg")));
        table.setBackground(backgroundImage.getDrawable());

        // Add title
        Label title = new Label("Select a Map", game.getSkin(), "title");
        title.setFontScale(1.5f);
        table.add(title).padBottom(50).row();

        // Add buttons for each map
        addMapButton(table, "Level 1", "maps//level-1.properties");
        addMapButton(table, "Level 2", "maps//level-2.properties");
        addMapButton(table, "Level 3", "maps//level-3.properties");
        addMapButton(table, "Level 4", "maps//level-4.properties");
        addMapButton(table, "Level 5", "maps//level-5.properties");

        // Add back button
        TextButton backButton = new TextButton("Back", game.getSkin());
        table.add(backButton).width(300).pad(20).row();
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goToMenu();
            }
        });
    }

    /**
     * Adds a button for a specific map to the given table.
     *
     * @param table The table to which the button is added.
     * @param mapName The display name of the map.
     * @param mapPath The file path to the map.
     */
    private void addMapButton(Table table, String mapName, String mapPath) {
        TextButton mapButton = new TextButton(mapName, game.getSkin());
        table.add(mapButton).width(300).pad(10).row();
        mapButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goToGame();
            }
        });
    }

    @Override
    public void show() {
        // Set the input processor to the stage
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        // Clear the screen with a dark gray background
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
    public void hide() {
        // Remove the input processor when the screen is hidden
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        // Dispose of the stage when the screen is disposed
        stage.dispose();
    }

    // Unused methods in this screen
    @Override
    public void pause() {}

    @Override
    public void resume() {}
}