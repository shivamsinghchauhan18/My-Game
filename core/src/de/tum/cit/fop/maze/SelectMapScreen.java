package de.tum.cit.fop.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
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

/**
 * SelectMapScreen gives the interface for selecting maps in the game.
 * Users can choose between predefined levels or upload custom maps.
 */
public class SelectMapScreen implements Screen {

    private final Stage stage;
    private final Texture backgroundImage;
    private final SpriteBatch spriteBatch;
    private final Sound laudaLassan;

    /**
     * @param game the MazeRunnerGame instance.
     */
    public SelectMapScreen(MazeRunnerGame game) {
        OrthographicCamera camera = new OrthographicCamera();
        spriteBatch = new SpriteBatch();

        // Load the background texture
        backgroundImage = new Texture(Gdx.files.internal("Maze1.jpeg"));
        backgroundImage.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        // Set up the stage and table
        stage = new Stage(new ScreenViewport(camera), game.getSpriteBatch());
        Table layoutTable = new Table();
        layoutTable.setFillParent(true);
        stage.addActor(layoutTable);

        laudaLassan = Gdx.audio.newSound(Gdx.files.internal("toy-button.mp3"));

        Label titleLabel = new Label(game.getLanguages().get("selectmap"), game.getSkin(), "title");
        layoutTable.add(titleLabel).padBottom(50).row();

        for (int level = 1; level <= 5; level++) {
            TextButton levelButton = new TextButton(game.getLanguages().get("level") + level, game.getSkin());
            layoutTable.add(levelButton).width(400).padBottom(15).row();


            int selectedLevel = level;
            levelButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    handleLevelSelection(game, selectedLevel);
                }
            });
        }

        // Add an "Upload Map" button
        TextButton uploadButton = new TextButton(game.getLanguages().get("uploadmap"), game.getSkin());
        layoutTable.add(uploadButton).width(350).padBottom(20).row();
        uploadButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.showFileChooser();
            }
        });

        // Add a "Back" button to return to the main menu
        TextButton backButton = new TextButton(game.getLanguages().get("back"), game.getSkin());
        layoutTable.add(backButton).width(350).padBottom(20).row();
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goToMenu();
            }
        });
    }

    /**
     * Handles the level selection
     *
     * @param game        The MazeRunnerGame instance.
     * @param levelNumber The selected level number.
     */
    public void handleLevelSelection(MazeRunnerGame game, int levelNumber) {
        String levelPath = "maps/level-" + levelNumber + ".properties";
        game.getMazeLoader().loadMazeData(Gdx.files.internal(levelPath).path());
        game.getMusicLoader().pauseMenuMusic();

        if (!game.getMusicLoader().isForbiddenGame()) {
            game.getMusicLoader().getCurrentMusic();
            game.getMusicLoader().playGameMusic();
        }
        game.createMaze();
        game.renderMaze();
        game.goToGame();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        spriteBatch.draw(backgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteBatch.end();
        stage.act(Math.min(delta, 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        backgroundImage.dispose();
        spriteBatch.dispose();
    }
}
