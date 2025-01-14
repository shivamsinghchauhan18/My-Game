package de.tum.cit.fop.maze;



import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.tum.cit.fop.maze.GameElements.Characters.Player;
import de.tum.cit.fop.maze.GameElements.Objects.Heart;
import de.tum.cit.fop.maze.GameElements.Objects.Key;

import java.util.ArrayList;
import java.util.List;

/**
 * The HUD (Heads-Up Display) class manages on-screen elements in MazeRunnerGame,
 * such as the player's health (hearts) and keys.
 */
public class HUD {

    private final Player player;
    private final Stage stage;
    private final Heart heart;
    private final Key key;

    private Image keyImage;
    private final List<Image> heartImages;

    /**
     * Constructs the HUD for the game.
     *
     * @param player   The player character whose status is displayed on the HUD.
     * @param viewport The viewport for rendering the HUD.
     */
    public HUD(Player player, Viewport viewport) {
        this.player = player;
        this.heart = new Heart();
        this.key = new Key();
        this.heartImages = new ArrayList<>();
        this.stage = new Stage(viewport);

        initializeHUD();
    }

    /**
     * Sets up the HUD elements on the game screen.
     * This includes creating and positioning heart and key icons.
     */
    private void initializeHUD() {
        Table table = new Table();
        table.top().right();
        table.setFillParent(true);

        // Add key image
        keyImage = new Image(key.getKey());
        keyImage.setScale(5f);
        keyImage.setVisible(false);
        table.add(keyImage).pad(10);

        // Add heart images based on initial player health
        for (int i = 0; i < player.getHeartCount(); i++) {
            Image heartImage = new Image(heart.getFullHeart());
            heartImage.setScale(5f);
            heartImages.add(heartImage);
            table.add(heartImage).pad(10);
        }

        // Add the table to the stage
        stage.addActor(table);
    }

    /**
     * Renders the HUD elements on the screen.
     * This should be called during the game's render loop.
     */
    public void draw() {
        stage.draw();
    }

    /**
     * Updates the HUD elements to reflect the player's current status.
     * Displays the key if collected and updates heart icons to match remaining health.
     */
    public void update() {
        // Update key visibility
        keyImage.setVisible(player.getHasKey());

        // Update heart icons
        for (int i = 0; i < heartImages.size(); i++) {
            Image heartImage = heartImages.get(i);
            if (i < player.getHeartCount()) {
                heartImage.setDrawable(new SpriteDrawable(new Sprite(heart.getFullHeart())));
            } else {
                heartImage.setDrawable(new SpriteDrawable(new Sprite(heart.getEmptyHeart())));
            }
        }
    }

    /**
     * Animates the key image when the player collects a key.
     * The animation highlights the key by scaling it up and back down.
     */
    public void animateKeyCollection() {
        keyImage.clearActions();
        keyImage.addAction(Actions.sequence(
                Actions.scaleTo(7f, 7f, 0.3f), // Scale up
                Actions.scaleTo(5f, 5f, 0.3f)  // Scale down
        ));
    }

    /**
     * Animates a heart icon when the player loses health.
     *
     * @param heartIndex The index of the heart to animate.
     */
    public void animateHeartLoss(int heartIndex) {
        if (heartIndex >= 0 && heartIndex < heartImages.size()) {
            Image heartImage = heartImages.get(heartIndex);
            heartImage.clearActions();
            heartImage.addAction(Actions.sequence(
                    Actions.scaleTo(7f, 7f, 0.3f), // Scale up
                    Actions.scaleTo(5f, 5f, 0.3f)  // Scale down
            ));
        }
    }

    /**
     * Gets the stage used for rendering the HUD elements.
     *
     * @return The Stage object associated with the HUD.
     */
    public Stage getStage() {
        return stage;
    }
}
