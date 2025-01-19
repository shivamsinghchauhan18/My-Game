package de.tum.cit.fop.maze;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Character extends GameObject {

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
    protected int rectWidth;
    protected int rectHeight;



    private Direction direction;
    protected Animation<TextureRegion> leftAnimation;
    protected Animation<TextureRegion> rightAnimation;
    protected Animation<TextureRegion> upAnimation;
    protected Animation<TextureRegion> downAnimation;

    protected float prevX;
    protected float prevY;
    protected float sinusInput;

    public Character(float x, float y, int rectWidth, int rectHeight) {
        super(x, y, rectWidth, rectHeight);
        this.prevX = x;
        this.prevY = y;
        this.direction = Direction.DOWN; // Default direction
    }

    public abstract void update(float delta);


    public abstract void draw(SpriteBatch spriteBatch);

    public Animation<TextureRegion> getCurrentAnimation() {
        return switch (direction) {
            case LEFT -> leftAnimation;
            case RIGHT -> rightAnimation;
            case UP -> upAnimation;
            case DOWN -> downAnimation;
        };
    }

    public void setDirection(Direction direction) {
        if (direction == null) {
            throw new IllegalArgumentException("Direction cannot be null");
        }
        this.direction = direction;
    }

    public void setPrevX(float prevX) {
        this.prevX = prevX;
    }

    public void setPrevY(float prevY) {
        this.prevY = prevY;
    }

    public Direction getDirection() {
        return direction;
    }

    public float getPrevX() {
        return prevX;
    }

    public float getPrevY() {
        return prevY;
    }

    public void setAnimations(Animation<TextureRegion> leftAnimation, Animation<TextureRegion> rightAnimation,
                              Animation<TextureRegion> upAnimation, Animation<TextureRegion> downAnimation) {
        this.leftAnimation = leftAnimation;
        this.rightAnimation = rightAnimation;
        this.upAnimation = upAnimation;
        this.downAnimation = downAnimation;
    }
}