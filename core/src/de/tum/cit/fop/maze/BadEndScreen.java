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

    /**
     * Constructs a BadEndScreen with the specified MazeRunnerGame instance.
     *
     * @param game The MazeRunnerGame instance.
     */
    public BadEndScreen(MazeRunnerGame game) {
        this.game = game;
        var camera = new OrthographicCamera();
        backgroundTexture = new Texture(Gdx.files.internal("assets/Gameover.jpeg"));
        backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        batch = new SpriteBatch();
        Viewport viewport = new ScreenViewport(camera);
        stage = new Stage(viewport, game.getSpriteBatch());
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        hero = game.getHero();
        table.add(new Label(game.getLanguages().get("youlost"), game.getSkin(), "title")).padBottom(400).row();

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
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        batch.setProjectionMatrix(stage.getCamera().combined);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        game.getSpriteBatch().begin();
        game.getSpriteBatch().draw(hero.getCryAnimation().getKeyFrame(delta, true), (stage.getWidth()/2) - 70, (stage.getHeight()/2) - 130, 150,300);
        game.getSpriteBatch().end();
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
