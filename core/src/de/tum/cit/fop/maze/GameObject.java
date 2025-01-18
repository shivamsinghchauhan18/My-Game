package de.tum.cit.fop.maze;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;


public class GameObject {
    private float x;
    private float y;
    private final Rectangle boundingBox;
    private Animation<TextureRegion> animation;
    private float animationTime = 0f;


    public GameObject(float x, float y, int rectWidth, int rectHeight) {
        this.x = x;
        this.y = y;
        this.boundingBox = new Rectangle(x, y, rectWidth, rectHeight);
    }

    public void loadHorizontalAnimation(String path, int startX, int startY, int frameWidth, int frameHeight, int frameCount, float duration) {
        animation = createAnimation(path, startX, startY, frameWidth, frameHeight, frameCount, duration, true);
    }


    public void loadVerticalAnimation(String path, int startX, int startY, int frameWidth, int frameHeight, int frameCount, float duration) {
        animation = createAnimation(path, startX, startY, frameWidth, frameHeight, frameCount, duration, false);
    }


    public void update(float delta) {
        if (!GameScreen.isResumed()) {
            animationTime += delta;
        }
    }

    public void draw(SpriteBatch spriteBatch, boolean shouldDraw) {
        if (shouldDraw && animation != null) {
            spriteBatch.draw(
                    animation.getKeyFrame(animationTime, true),
                    x,
                    y,
                    boundingBox.width,
                    boundingBox.height
            );
        }
    }


    public void dispose() {
        if (animation != null) {
            TextureRegion firstFrame = animation.getKeyFrame(0);
            if (firstFrame != null) {
                firstFrame.getTexture().dispose();
            }
        }
    }

    private Animation<TextureRegion> createAnimation(String path, int startX, int startY, int frameWidth, int frameHeight, int frameCount, float duration, boolean isHorizontal) {
        Texture texture = new Texture(Gdx.files.internal(path));
        Array<TextureRegion> frames = new Array<>(frameCount);

        for (int i = 0; i < frameCount; i++) {
            int offsetX = isHorizontal ? i * frameWidth : 0;
            int offsetY = isHorizontal ? 0 : i * frameHeight;
            frames.add(new TextureRegion(texture, startX + offsetX, startY + offsetY, frameWidth, frameHeight));
        }

        return new Animation<>(duration, frames);
    }

    // Getters and Setters
    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
        this.boundingBox.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
        this.boundingBox.y = y;
    }
}

