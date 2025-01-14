package de.tum.cit.fop.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    public GameObject(float x, float y, int rectWidth, int rectHeight) {
        this.x = x;
        this.y = y;
        this.rect = new Rectangle(x, y, rectWidth, rectHeight);
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

    public Animation<TextureRegion> loadVerticalAnimation(String path, int imageX, int imageY, int frameWidth, int frameHeight, int frames, float duration) {
        // Load the texture once
        this.texture = new Texture(Gdx.files.internal(path));

        // Split the texture into a 2D array of frames
        TextureRegion[][] tempFrames = TextureRegion.split(texture, frameWidth, frameHeight);

        // Create a libGDX Array to hold the vertical frames
        Array<TextureRegion> framesArray = new Array<>(frames);

        // Calculate starting row and column indices based on offsets
        int startRow = imageY / frameHeight;
        int startCol = imageX / frameWidth;

        // Collect the required vertical frames
        for (int row = startRow; row < startRow + frames; row++) {
            framesArray.add(tempFrames[row][startCol]);
        }

        // Create and return the animation
        return new Animation<>(duration, framesArray);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }

    public void setAnimation(Animation<TextureRegion> animation) {
        this.animation = animation;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    public float getAnimationTime() {
        return animationTime;
    }

    public void setAnimationTime(float animationTime) {
        this.animationTime = animationTime;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void draw(SpriteBatch spriteBatch, boolean toDraw) {
        if (toDraw){
            spriteBatch.draw(
                    animation.getKeyFrame(animationTime, true),
                    x,
                    y,
                    rect.width,
                    rect.height
            );
        }
    }
}
