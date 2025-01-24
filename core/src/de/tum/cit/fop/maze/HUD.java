package de.tum.cit.fop.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * The HUD class represents the Heads-Up Display in the MazeRunnerGame.
 */
public class HUD extends Stage{
    private final Label livesLabel;
    private final Label keyStatusLabel;
    private final MazeRunnerGame game;
    private final Hero hero;
    private final TextureRegion livesTextures;
    private final Label vulnerability;

    /**
     * Constructs a HUD with the specified viewport, hero, and MazeRunnerGame.
     *
     * @param viewport The viewport to use for the HUD stage.
     * @param hero     The hero instance of the game.
     * @param game     The MazeRunnerGame instance.
     */
    public HUD(Viewport viewport, Hero hero, MazeRunnerGame game) {
        super(viewport);

        // Cache font and texture resources from the game
        BitmapFont customFont = game.getSkin().getFont("default-font"); // Use a preloaded font
        Label.LabelStyle labelStyle = new Label.LabelStyle(customFont, Color.WHITE); // Reuse LabelStyle

        // Create labels
        livesLabel = createLabel(game.getLanguages().get("lives"), labelStyle, 10, viewport.getWorldHeight() - 50);
        keyStatusLabel = createLabel(game.getLanguages().get("keystatusnotok"), labelStyle, 10, viewport.getWorldHeight() - 110);
        vulnerability = createLabel(game.getLanguages().get("shieldno"), labelStyle, 10, viewport.getWorldHeight() - 170);

        // Add UI elements to the stage
        addActor(livesLabel);
        addActor(keyStatusLabel);
        addActor(vulnerability);

        // Assign Hero and Game references
        this.hero = hero;
        this.game = game;

        // Reuse cached texture from the game
        Texture heartTexture = game.getAssetManager().get("objects.png", Texture.class); // Use preloaded texture
        this.livesTextures = new TextureRegion(heartTexture, 64, 0, 16, 16);
    }


    private Label createLabel(String text, Label.LabelStyle style, float x, float y) {
        Label label = new Label(text, style);
        label.setPosition(x, y);
        return label;
    }

    /**
     * Draws the heart icons representing the number of lives the hero has.
     */
    public void drawLives() {
        // Cache reusable values
        float baseX = livesLabel.getWidth() + 15; // Starting X position
        float baseY = livesLabel.getY() - 10;    // Y position for all icons
        int spacing = 60;                        // Spacing between icons
        float textureSize = 64;                  // Size of each heart texture

        // Draw lives
        for (int i = 0; i < hero.getLives(); i++) {
            float currentX = baseX + (i * spacing); // Compute X position for the current heart
            game.getSpriteBatch().draw(livesTextures, currentX, baseY, textureSize, textureSize);
        }
    }

    /**
     *  If hero collected the key, it changes the keystatus label to "keystatusok" key in the language bundles.
     */
    public void setKeyStatus() {
        if (hero.isKeyCollected()) {
            keyStatusLabel.setText(game.getLanguages().get("keystatusok"));
        }
    }

    /**
     * According to the state of the shield it changes vulnerability text to "shieldyes" or "shieldno" key in the language bundles.
     * @param shield It is a boolean which states that shield is enabled or not.
     */
    public void setShield(boolean shield) {
        if (shield){
            vulnerability.setText(game.getLanguages().get("shieldyes"));
        }else{
            vulnerability.setText(game.getLanguages().get("shieldno"));
        }
    }

}