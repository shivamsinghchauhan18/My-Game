package de.tum.cit.fop.maze.GameElements.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.tum.cit.fop.maze.MazeRunnerGame;

/**
 * The CreditScreen displays the credits for the game in an immersive and animated way.
 */
public class Credits implements Screen {

    private final MazeRunnerGame game;
    private final Stage stage;
    private final BitmapFont font;

    private final Music backgroundMusic;
    private Texture backgroundTexture;

    /**
     * Constructor initializes the credits screen and its visual elements.
     *
     * @param game The main game class.
     */
    public Credits(MazeRunnerGame game) {
        this.game = game;

        // Initialize camera and viewport
        OrthographicCamera camera = new OrthographicCamera();
        camera.zoom = 1.0f;
        Viewport viewport = new ScreenViewport(camera);
        stage = new Stage(viewport, game.getSpriteBatch());

        // Load font and background music
        font = game.getSkin().getFont("font");
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("credits_music.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);

        // Load background texture
        backgroundTexture = new Texture(Gdx.files.internal("credits_background.png"));

        // Set up the UI table layout
        setupTable();
    }

    public Credits(MazeRunnerGame game, Stage stage, BitmapFont font, Music backgroundMusic) {
        this.game = game;
        this.stage = stage;
        this.font = font;
        this.backgroundMusic = backgroundMusic;
    }

    /**
     * Sets up the table layout for the credits screen.
     */
    private void setupTable() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Title
        Label.LabelStyle titleStyle = new Label.LabelStyle(font, null);
        Label title = new Label("CREDITS", titleStyle);
        title.setFontScale(1.5f);

        // Credits list
        Label.LabelStyle nameStyle = new Label.LabelStyle(font, null);
        Label createdBy = new Label("Created By", nameStyle);
        Label developer1 = new Label("Shivam Singh Chauhan", nameStyle);
        Label developer2 = new Label("Jeet Upadhya", nameStyle);
        Label developer3 = new Label("Janmdeepsinh Vala", nameStyle);
        Label thanks = new Label("Special Thanks to Open Source Contributors", nameStyle);

        // Add elements to the table
        table.add(title).padBottom(50).row();
        table.add(createdBy).padBottom(30).row();
        table.add(developer1).padBottom(20).row();
        table.add(developer2).padBottom(50).row();
        table.add(thanks).padTop(30);
    }

    @Override
    public void show() {
        // Start background music when the screen is shown
        backgroundMusic.play();
    }

    @Override
    public void render(float delta) {
        // Handle escape key input to return to the main menu
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            backgroundMusic.stop();
            game.goToMenu();
        }

        // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(0, 0, 0.1f, 1); // Slightly dark background

        // Draw background
        SpriteBatch spriteBatch = game.getSpriteBatch();
        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteBatch.end();

        // Update and draw stage
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        // Stop the background music when the screen is hidden
        backgroundMusic.stop();
    }

    @Override
    public void dispose() {
        // Dispose resources
        stage.dispose();
        backgroundMusic.dispose();
        backgroundTexture.dispose();
    }
}