package core.GameElements.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * This Class creates key for MazeRunnerGame.
 */
public class Key {
    private TextureRegion key;

    /**
     * Constructor initializes method which store Texture of key.
     */
    public Key(){
        loadKey();
    }

    /**
     * Method loads the key Texture.
     */
    public void loadKey(){
        Texture keyTexture = new Texture(Gdx.files.internal("keys_1_1.png"));
        key = new TextureRegion(keyTexture,16,16);
    }

    /**
     *Getter for key attribute.
     */
    public TextureRegion getKey() {
        return key;
    }
}