package de.tum.cit.fop.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class GameObject {

    protected float x;
    protected float y;
    protected Animation<TextureRegion> animation;
    protected Rectangle rect;
    protected float animationTime;
    protected Texture texture;

    public GameObject(float x, float y,int rectWidth,int rectHeight) {
        this.x = x;
        this.y = y;
        this.rect = new Rectangle(x, y, rectWidth,rectHeight);
    }

    public Animation<TextureRegion> loadHorizontalAnimation(String path, int imageX, int imageY, int frameWidth, int frameHeight, int frames, float duration) {
        // Load the texture only once
        this.texture = new Texture(Gdx.files.internal(path));

        // Split the texture into regions based on frame dimensions
        TextureRegion[][] tempFrames = TextureRegion.split(texture, frameWidth, frameHeight);

        // Use the Array from libGDX directly for performance
        Array<TextureRegion> framesArray = new Array<>(frames);

        // Add only the required frames, starting from imageX and imageY
        for (int col = 0; col < frames; col++) {
            framesArray.add(tempFrames[imageY / frameHeight][(imageX / frameWidth) + col]);
        }

        // Create and return the animation
        return new Animation<>(duration, framesArray);
    }
}
