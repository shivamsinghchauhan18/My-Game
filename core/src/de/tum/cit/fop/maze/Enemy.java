package de.tum.cit.fop.maze;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Enemy class represents an enemy character in the game. It extends the Character class
 * and includes functionalities for managing enemy movement and animations.
 */
public class Enemy extends Character {
    private String direction;
    private int prevIndex ;
    public static List<Enemy> enemyList= new ArrayList<>();
    private final List<String> directionList;
    private final float speed;
    private static final float STEP_DISTANCE = 300;
    private float stepsRemaining;

    /**
     * Constructs an Enemy object with the specified position.
     *
     * @param x The x-coordinate of the enemy.
     * @param y The y-coordinate of the enemy.
     */
    public Enemy(float x, float y) {
        super(x,y,60,60);
        this.directionList = new ArrayList<>(Arrays.asList("up","right","down","left"));
        this.prevIndex = -1;
        this.speed = 100f;
        this.stepsRemaining = STEP_DISTANCE;
        this.animation = loadHorizontalAnimation("mobs.png",64,64,16,16,1,0.1f);
        this.leftAnimation = loadHorizontalAnimation("mobs.png",48,80,16,16,3,0.1f);
        this.rightAnimation = loadHorizontalAnimation("mobs.png",48,96,16,16,3,0.1f);
        this.downAnimation = loadHorizontalAnimation("mobs.png",48,64,16,16,3,0.1f);
        this.upAnimation = loadHorizontalAnimation("mobs.png",48,112,16,16,3,0.1f);
        this.direction = getDirection();
    }

    /**
     * Updates the enemy's state based on the elapsed time.
     *
     * @param delta The time in seconds since the last update.
     */
    @Override
    public void update(float delta) {
        float movement = speed * delta;
        // Check if there are steps remaining
        if (stepsRemaining > 0) {
            move(movement, delta);
            stepsRemaining -= movement;
            if (stepsRemaining <= 0) {
                stepsRemaining = STEP_DISTANCE;
                this.direction = getDirection();
            }
        }
        rect.setPosition(x, y);
    }

    /**
     * It detects the enemies direction.
     * @param distance The distance to move the object.
     * @param delta The time in seconds since the last update. It is used for frame-rate independent movement.
     */
    public void move(float distance, float delta) {
        if (!GameScreen.isResumed()) {
            // Implement movement logic based on the current direction
            switch (direction) {
                case "left":
                    if (checkEnemyMovement(x-distance,y+10)&&checkEnemyMovement(x-distance,y+50)) {
                        moveLeft(distance);
                        sinusInput += delta;
                    }
                    break;
                case "right":
                    if (checkEnemyMovement(x+60+distance,y+10)&&checkEnemyMovement(x+60+distance,y+50)){
                        moveRight(distance);
                        sinusInput += delta;
                    }
                    break;
                case "up":
                    if (checkEnemyMovement(x+10,y+60+distance)&&checkEnemyMovement(x+50,y+60+distance)){
                        moveUp(distance);
                        sinusInput += delta;
                    }
                    break;
                case "down":
                    if (checkEnemyMovement(x+10,y-distance)&&checkEnemyMovement(x+50,y-distance)){
                        moveDown(distance);
                        sinusInput += delta;
                    }
                    break;
            }
        }
    }

    /**
     * Generates a random movement direction from a predefined list, ensuring it is different from the previously generated direction.
     * @return A randomly chosen movement direction from the available direction list.
     */
    public String getDirection() {
        int index = MathUtils.random(0,3);
        while(index == prevIndex){
            index = MathUtils.random(0,3);
        }
        prevIndex=index;
        return directionList.get(index);
    }

    /**
     * @param direction It gets the direction from getDirection() method, and then sets it accordingly.
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }
    public boolean checkEnemyMovement(float x, float y){
        int nx = (int) (x/60);
        int ny = (int) (y/60);
        if (MazeRunnerGame.mazeData.get(new Point(nx,ny))==null){
            return true;
        }else {
            return switch (MazeRunnerGame.mazeData.get(new Point(nx, ny))) {
                case 0, 1, 2,3 -> {
                    setDirection(getDirection());
                    yield false;
                }
                default -> true;
            };
        }
    }

    /**
     * It draws the enemy.
     * @param spriteBatch The SpriteBatch used for rendering graphics.
     */
    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(
                getCurrentFrame().getKeyFrame(sinusInput, true),
                x,
                y, 60, 60
        );
    }

    /**
     * Retrieves the current frame animation based on the current movement direction. If the game is resumed, it returns a default animation.
     * @return The Animation<TextureRegion> corresponding to the current movement direction, or a default animation if the game is currently resumed.
     */
    private Animation<TextureRegion> getCurrentFrame() {
        if (!GameScreen.isResumed()){
            return switch (direction) {
                case "left" -> leftAnimation;
                case "right" -> rightAnimation;
                case "up" -> upAnimation;
                case "down" -> downAnimation;
                default -> animation;
            };
        }
        else {
            return animation;
        }
    }

    /**
     * It makes enemies to move left.
     * @param distance The distance to move the object.
     */
    public void moveLeft(float distance) {
        setPrevX(x);
        x -= distance;
        rect.setX(x);
    }

    /**
     * It makes enemies to move right.
     * @param distance The distance to move the object.
     */
    public void moveRight(float distance) {
        setPrevX(x);
        x +=distance;
        rect.setX(x);
    }

    /**
     * It makes enemies to move up.
     * @param distance The distance to move the object.
     */
    public void moveUp(float distance) {
        setPrevY(y);
        y += distance;
        rect.setY(y);
    }

    /**
     * It makes enemies to move down.
     * @param distance The distance to move the object.
     */
    public void moveDown(float distance) {
        setPrevY(y);
        y -= distance;
        rect.setY(y);
    }
}