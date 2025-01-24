package de.tum.cit.fop.maze;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Key extends GameObject {

    private boolean collected = false;


    public Key(float x,float y) {
        super(x,y,40,40);
        this.animation = loadHorizontalAnimation("objects.png",0,64,16,16,4,0.1f);
    }

    public boolean isCollected() {
        return collected;
    }

    public void collect() {
        this.collected = true;
    }

    public void draw(SpriteBatch spriteBatch) {
        if (!collected) {
            spriteBatch.draw(animation.getKeyFrame(0, true), x, y, rect.width, rect.height);
        }
    }
}
