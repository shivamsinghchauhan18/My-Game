package de.tum.cit.fop.maze;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SuperHero extends Character {

    private String direction;
    private int lives = 5;
    private boolean keyCollected;
    private boolean winner;
    private boolean dead;
    private float danceTimer;
    private final Animation<TextureRegion> danceAnimation;
    private final Animation<TextureRegion> cryAnimation;
    private TextureRegion texture;


    public SuperHero(float x, float y) {
        super(x, y, 40, 40); // Call the super class constructor
        this.keyCollected = false;
        this.dead = false;
        this.winner = false;

        // Set animations for different directions
        this.leftAnimation = loadHorizontalAnimation("character.png", 0, 96, 16, 32, 4, 0.1f);
        this.downAnimation = loadHorizontalAnimation("character.png", 0, 0, 16, 32, 4, 0.1f);
        this.rightAnimation = loadHorizontalAnimation("character.png", 0, 32, 16, 32, 4, 0.1f);
        this.upAnimation = loadHorizontalAnimation("character.png", 0, 64, 16, 32, 4, 0.1f);
        this.animation = loadHorizontalAnimation("character.png", 0, 0, 16, 32, 1, 0.1f);
        this.cryAnimation = loadHorizontalAnimation("character.png", 80, 0, 16, 32, 1, 0.25f);
        this.danceAnimation = loadHorizontalAnimation("character.png", 96, 0, 16, 32, 2, 0.25f);
    }

    @Override
    public void update(float delta) {
        // Update the position based on some logic (e.g., movement).
        prevX = x;
        prevY = y;

        // Example logic for sinusoidal movement (optional based on your requirements).
        sinusInput += delta;
        x += Math.sin(sinusInput) * 10;

        // Update other properties or animations if needed.
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        Animation<TextureRegion> currentAnimation = getCurrentAnimation();
        if (currentAnimation != null) {
            // Get the current frame of the animation.
            TextureRegion currentFrame = currentAnimation.getKeyFrame(sinusInput, true);
            spriteBatch.draw(currentFrame, x, y);
        } else {

            if (texture != null) {
                spriteBatch.draw(texture, x, y, rectWidth, rectHeight);
            }
        }
    }

    public void setSuperHeroAnimations(Animation<TextureRegion> left, Animation<TextureRegion> right,
                                       Animation<TextureRegion> up, Animation<TextureRegion> down) {
        this.leftAnimation = left;
        this.rightAnimation = right;
        this.upAnimation = up;
        this.downAnimation = down;
    }

}
