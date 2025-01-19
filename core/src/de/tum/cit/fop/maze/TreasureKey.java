package de.tum.cit.fop.maze;

public class TreasureKey extends GameObject {
    public TreasureKey(float x, float y) {
        super(x, y,45,45);
        loadVerticalAnimation("objects.png", 0, 64, 20, 20, 5, 0.15f);
    }


    public void collectKey() {
        System.out.println("Treasure Key Collected!");
    }
}
