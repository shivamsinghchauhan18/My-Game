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
import de.tum.cit.fop.maze.Languages;

public class BadEndScreen implements Screen {
    private final Stage stage;
    private final Texture backgroundTexture;
    private final SpriteBatch batch;
    private final MazeRunnerGame game;
    private final Hero hero;
    private final int finalScore;

    /**
     * Constructs a BadEndScreen with the specified MazeRunnerGame instance.
     *
     * @param game The MazeRunnerGame instance.
     */
    public BadEndScreen(MazeRunnerGame game, int finalScore) {
        this.game = game;
        this.batch = game.getSpriteBatch();
        var camera = game.getCamera();
        this.hero = game.getHero(); // Properly retrieve the hero from the game instance
        this.finalScore = finalScore;

        // Load assets from AssetManager
        this.backgroundTexture = game.getAssetManager().get("assets/Gameover.jpeg", Texture.class);

        // Initialize stage and table
        Viewport viewport = new ScreenViewport(camera);
        this.stage = new Stage(viewport, batch);
        setupTable();
    }

    private void setupTable() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.add(new Label(game.getLanguages().get("youlost"), game.getSkin(), "title"))
                .padBottom(50)
                .row();

        // Add score label
        table.add(new Label("Your Score: " + finalScore, game.getSkin(), "default"))
                .padBottom(200)
                .row();

        // Example: Display hero-related information (if needed)
//        if (hero != null) {
//            table.add(new Label("Final Score: " + hero.getScore(), game.getSkin())).padBottom(20).row();
//        }

        TextButton goToMenu = new TextButton(game.getLanguages().get("gomenu"), game.getSkin());
        table.add(goToMenu).width(400).padBottom(15).row();

        goToMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MenuScreen(game));
                game.getMusicLoader().stopLosingMusic();
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

    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update stage logic
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));

        // Set the projection matrix for the batch
        batch.setProjectionMatrix(stage.getCamera().combined);

        // Draw the background
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        // Track animation state time
        hero.setDanceTimer(hero.getDanceTimer() + delta);

        // Draw hero's cry animation
        batch.begin();
        batch.draw(
                hero.getCryAnimation().getKeyFrame(hero.getDanceTimer(), true), // Use cumulative timer for animation
                (stage.getWidth() / 2) - 70,
                (stage.getHeight() / 2) - 130,
                150,
                300
        );
        batch.end();

        // Draw the stage UI
        stage.draw();
    }

    /**
     * Called when the screen is resized, such as when the window is resized or the orientation changes.
     *
     * @param width  The new width of the screen.
     * @param height The new height of the screen.
     */

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        stage.getCamera().position.set(stage.getCamera().viewportWidth / 2f, stage.getCamera().viewportHeight / 2f, 0);
    }

    /**
     * Disposes of resources and performs cleanup when the screen is no longer in use.
     * It is called when the game switches to another screen or exits.
     */

    public void dispose() {
        stage.dispose();
    }

    /**
     * Called when the screen is set as the current screen in the game.
     * This method is typically used to initialize resources or set up the initial state of the screen.
     */

    public void show() {
        Gdx.input.setInputProcessor(stage);
    }


    public void pause() {
    }


    public void resume() {
    }


    public void hide() {
    }

}
