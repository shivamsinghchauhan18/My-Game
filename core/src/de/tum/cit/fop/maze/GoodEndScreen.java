package de.tum.cit.fop.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * The GoodEndScreen class represents the screen displayed when the player wins the game.
 */
public class GoodEndScreen implements Screen {
    private final Stage stage;
    private final Texture backgroundTexture;
    private final SpriteBatch batch;
    private final de.tum.cit.fop.maze.MazeRunnerGame game;
    private final Hero hero;
    private final int finalScore;

    /**
     * Constructs a GoodEndScreen with the specified MazeRunnerGame instance.
     *
     * @param game The MazeRunnerGame instance.
     */
    public GoodEndScreen(MazeRunnerGame game, int finalScore) {
        this.game = game;
        this.batch = game.getSpriteBatch(); // Reuse the game's shared SpriteBatch
        var camera = game.getCamera();// Reuse the game's shared camera
        this.finalScore = finalScore;

        // Load background texture from AssetManager
        this.backgroundTexture = game.getAssetManager().get("Winner.jpeg", Texture.class);

        // Initialize stage with the camera and shared SpriteBatch
        Viewport viewport = new ScreenViewport(camera);
        this.stage = new Stage(viewport, batch);

        this.hero = game.getHero(); // Retrieve the hero instance

        setupTable(); // Delegate UI table creation to a helper method
    }

    private void setupTable() {
        // Create a table for UI elements
        Table table = new Table();
        table.setFillParent(true); // Ensure the table covers the screen
        stage.addActor(table);

        // Add "You Won" label
        table.add(new Label(game.getLanguages().get("youwon"), game.getSkin(), "title"))
                .padBottom(50)
                .row();

        // Add score label
        table.add(new Label("Your Score: " + finalScore, game.getSkin(), "default"))
                .padBottom(200)
                .row();

        // Add "Go to Menu" button
        TextButton goToMenu = new TextButton(game.getLanguages().get("gomenu"), game.getSkin());
        table.add(goToMenu).width(400).padBottom(15).row();

        // Add listener to navigate back to the menu
        goToMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MenuScreen(game)); // Navigate to the menu
                game.getMusicLoader().stopWinningMusic();
                if (!game.getMusicLoader().isForbiddenMenu()) {
                    game.getMusicLoader().playMenuMusic();
                }
            }
        });
    }

    /**
     * Renders the visual elements on the screen.
     *
     * @param delta The time in seconds since the last render call. It is used for frame-rate independent animation.
     */
    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update stage logic
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));

        // Draw the background
        batch.setProjectionMatrix(stage.getCamera().combined);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        // Draw the stage
        stage.draw();
    }


    /**
     * Called when the screen is resized, such as when the window is resized or the orientation changes.
     *
     * @param width  The new width of the screen.
     * @param height The new height of the screen.
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        stage.getCamera().position.set(stage.getCamera().viewportWidth / 2f, stage.getCamera().viewportHeight / 2f, 0);
    }

    /**
     * Disposes of resources and performs cleanup when the screen is no longer in use.
     * It is called when the game switches to another screen or exits.
     */
    @Override
    public void dispose() {
        stage.dispose(); // Dispose of the stage resources
    }

    /**
     * Called when the screen is set as the current screen in the game.
     * This method is typically used to initialize resources or set up the initial state of the screen.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }
}
