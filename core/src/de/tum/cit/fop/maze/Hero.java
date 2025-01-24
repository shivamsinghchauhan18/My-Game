package de.tum.cit.fop.maze;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.InputStream;

public class Hero extends Character {


    private String direction;
    private int lives = 5;
    private boolean keyCollected;
    private boolean winner;
    private boolean dead;
    private float danceTimer;
    private final Animation<TextureRegion> danceAnimation;
    private final Animation<TextureRegion> cryAnimation;
    private int score;


    /**
     * Constructs a Hero with the specified initial position (x, y).
     *
     * @param x The initial x-coordinate of the hero's position.
     * @param y The initial y-coordinate of the hero's position.
     */
    public Hero(float x, float y) {
        super(x, y, 40, 40);
        try {
            // Load the texture using a FileInputStream
            Texture texture = new Texture(String.valueOf(new FileInputStream("character.png")));

            // Create animations using the helper method
            this.leftAnimation = createAnimation(texture, 0, 96, 16, 32, 4, 0.1f);
            this.downAnimation = createAnimation(texture, 0, 0, 16, 32, 4, 0.1f);
            this.rightAnimation = createAnimation(texture, 0, 32, 16, 32, 4, 0.1f);
            this.upAnimation = createAnimation(texture, 0, 64, 16, 32, 4, 0.1f);
            this.animation = createAnimation(texture, 0, 0, 16, 32, 1, 0.1f);
            this.cryAnimation = createAnimation(texture, 80, 0, 16, 32, 1, 0.25f);
            this.danceAnimation = createAnimation(texture, 96, 0, 16, 32, 2, 0.25f);

            // Initialize hero state
            this.keyCollected = false;
            this.dead = false;
            this.winner = false;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load texture: character.png");
        }
    }

    private Animation<TextureRegion> createAnimation(Texture texture, int startX, int startY, int frameWidth, int frameHeight, int frameCount, float frameDuration) {
        TextureRegion[] frames = new TextureRegion[frameCount];

        // Create frames by slicing the texture
        for (int i = 0; i < frameCount; i++) {
            frames[i] = new TextureRegion(texture, startX + (i * frameWidth), startY, frameWidth, frameHeight);
        }

        // Create and return an Animation
        return new Animation<>(frameDuration, frames);
    }

    /**
     * Updates the hero's state based on the elapsed time.
     *
     * @param delta The time in seconds since the last update.
     */
    @Override
    public void update(float delta) {
        setDirection(direction);
        sinusInput += delta;
    }

    /**
     * Draws the hero.
     * @param spriteBatch The SpriteBatch used for rendering graphics.
     */
    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(
                super.getCurrentAnimation().getKeyFrame(sinusInput, true),
                x,
                y,rect.width,rect.height*2
        );
    }

    /**
     * It enables hero to move left.
     * @param delta The distance to move the object.
     */
    public void moveLeft(float delta) {
        setPrevX(x);
        x -= delta;
    }

    /**
     * It enables hero to move right.
     * @param delta The distance to move the object.
     */
    public void moveRight(float delta) {
        setPrevX(x);
        x += delta;
    }

    /**
     * It enables hero to move up.
     * @param delta The distance to move the object.
     */
    public void moveUp(float delta) {
        setPrevY(y);
        y += delta;
    }

    /**
     * It enables hero to move down.
     * @param delta The distance to move the object.
     */
    public void moveDown(float delta) {
        setPrevY(y);
        y -= delta;
    }

    public void collectKey(Key key) {
        if (this.rect.overlaps(key.getRect())) { // Check collision
            this.keyCollected = true;
            System.out.println("Key collected!");
            increaseScore(100); // Add 100 points for collecting the key
        }
    }


    public void increaseScore(int points) {
        this.score += points;
    }

    public int getScore() {
        return score;
    }


    // Getters & Setters

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public boolean isKeyCollected() {
        return keyCollected;
    }

    public void setKeyCollected(boolean keyCollected) {
        this.keyCollected = keyCollected;
    }
    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }
    public float getDanceTimer() {
        return danceTimer;
    }
    public void setDanceTimer(float danceTimer) {
        this.danceTimer = danceTimer;
    }

    public Animation<TextureRegion> getDanceAnimation() {
        return danceAnimation;
    }
    public Animation<TextureRegion> getCryAnimation() {
        return cryAnimation;
    }
}














