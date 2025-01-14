package de.tum.cit.fop.maze;

import de.tum.cit.fop.maze.GameElements.Characters.Enemy;
import de.tum.cit.fop.maze.GameElements.Characters.Ghost;
import de.tum.cit.fop.maze.GameElements.Objects.*;
import java.util.*;

/**
 * Handles map data for the MazeRunnerGame, including game element placement and dynamic animations.
 */
public class MapLoader {
    private final Map<List<Integer>, Integer> map;
    private float sinusInput;
    private float maxX;
    private float maxY;
    private float playerX;
    private float playerY;

    private final MazeRunnerGame game;
    private final Walls wall;
    private final Obstacles obstacles;
    private final Ghost ghost;
    private final Door doors;
    private final Tile tiles;
    private final Decoration decoration;
    private final Heart heart;
    private final Key key;

    private int keyX, keyY;
    private boolean displayKey = true;
    private final List<List<Integer>> wallCoordinates;
    private final List<List<Integer>> doorCoordinates;
    private final List<List<Integer>> obstacleCoordinates;
    private final List<Enemy> enemies;
    private final List<List<Integer>> heartCoordinates;
    private final int heartCoordinate1;
    private final int heartCoordinate2;
    private boolean displayHeart1 = true;
    private boolean displayHeart2 = true;

    public Map<List<Integer>, Integer> getMap() {
        return map;
    }

    public float getSinusInput() {
        return sinusInput;
    }

    public void setMaxX(float maxX) {
        this.maxX = maxX;
    }

    public void setMaxY(float maxY) {
        this.maxY = maxY;
    }

    public void setPlayerX(float playerX) {
        this.playerX = playerX;
    }

    public void setPlayerY(float playerY) {
        this.playerY = playerY;
    }

    public MazeRunnerGame getGame() {
        return game;
    }

    public Walls getWall() {
        return wall;
    }

    public Obstacles getObstacles() {
        return obstacles;
    }

    public Ghost getGhost() {
        return ghost;
    }

    public Door getDoors() {
        return doors;
    }

    public Tile getTiles() {
        return tiles;
    }

    public Decoration getDecoration() {
        return decoration;
    }

    public Heart getHeart() {
        return heart;
    }

    public Key getKey() {
        return key;
    }

    public int getKeyX() {
        return keyX;
    }

    public void setKeyX(int keyX) {
        this.keyX = keyX;
    }

    public int getKeyY() {
        return keyY;
    }

    public void setKeyY(int keyY) {
        this.keyY = keyY;
    }

    public boolean isDisplayKey() {
        return displayKey;
    }

    public List<List<Integer>> getWallCoordinates() {
        return wallCoordinates;
    }

    public List<List<Integer>> getDoorCoordinates() {
        return doorCoordinates;
    }

    public List<List<Integer>> getObstacleCoordinates() {
        return obstacleCoordinates;
    }

    public List<List<Integer>> getHeartCoordinates() {
        return heartCoordinates;
    }

    public int getHeartCoordinate1() {
        return heartCoordinate1;
    }

    public int getHeartCoordinate2() {
        return heartCoordinate2;
    }

    public boolean isDisplayHeart1() {
        return displayHeart1;
    }

    public boolean isDisplayHeart2() {
        return displayHeart2;
    }

    /**
     * Initializes the MapLoader with game elements and level-specific data.
     */
    public MapLoader(MazeRunnerGame game, float sinusInput, String level) {
        this.game = game;
        this.sinusInput = sinusInput;
        this.map = Utils.readMap(level);
        this.wall = new Walls();
        this.obstacles = new Obstacles();
        this.ghost = new Ghost();
        this.doors = new Door();
        this.tiles = new Tile();
        this.key = new Key();
        this.decoration = new Decoration();
        this.heart = new Heart();

        this.wallCoordinates = new ArrayList<>();
        this.doorCoordinates = new ArrayList<>();
        this.obstacleCoordinates = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.heartCoordinates = new ArrayList<>();

        initializeMapData();
        calculateHeartCoordinates();

        Random random = new Random();
        heartCoordinate1 = random.nextInt(heartCoordinates.size());
        heartCoordinate2 = random.nextInt(heartCoordinates.size());
    }

    /**
     * Initializes and categorizes map data into game elements.
     */
    private void initializeMapData() {
        calculateMaxCoordinates(map);

        for (List<Integer> coordinates : map.keySet()) {
            int tileType = map.get(coordinates);
            switch (tileType) {
                case 0 -> wallCoordinates.add(new ArrayList<>(coordinates));
                case 1 -> {
                    playerX = coordinates.get(0);
                    playerY = coordinates.get(1);
                }
                case 2 -> doorCoordinates.add(new ArrayList<>(coordinates));
                case 3 -> obstacleCoordinates.add(new ArrayList<>(coordinates));
                case 4 -> enemies.add(new Enemy(coordinates.get(0), coordinates.get(1), 2f, Direction.getRandomDirection(), this));
                case 5 -> {
                    keyX = coordinates.get(0);
                    keyY = coordinates.get(1);
                }
            }
        }
    }

    /**
     * Calculates maximum map coordinates (maxX, maxY) from the map data.
     */
    private void calculateMaxCoordinates(Map<List<Integer>, Integer> map) {
        maxX = 0;
        maxY = 0;

        for (List<Integer> key : map.keySet()) {
            maxX = Math.max(maxX, key.get(0));
            maxY = Math.max(maxY, key.get(1));
        }
    }

    /**
     * Calculates available coordinates for hearts, excluding walls and obstacles.
     */
    private void calculateHeartCoordinates() {
        for (int x = 0; x <= maxX; x++) {
            for (int y = 0; y <= maxY; y++) {
                List<Integer> position = Arrays.asList(x, y);
                heartCoordinates.add(position);
            }
        }

        heartCoordinates.removeIf(pos ->
                wallCoordinates.contains(pos) || obstacleCoordinates.contains(pos)
        );
    }

    /**
     * Renders the map, including all static and dynamic elements.
     */
    public void renderMap() {
        drawTiles();

        if (displayHeart1) drawHeart(heartCoordinate1);
        if (displayHeart2) drawHeart(heartCoordinate2);

        for (List<Integer> coordinates : map.keySet()) {
            renderMapElement(coordinates);
        }

        renderEnemies();
    }

    /**
     * Draws the background tiles for the map.
     */
    private void drawTiles() {
        for (int x = 0; x <= maxX; x++) {
            for (int y = 0; y <= maxY; y++) {
                game.getSpriteBatch().draw(tiles.getTile(), x * 32, y * 32, 32, 32);
            }
        }
    }

    /**
     * Draws a heart animation at the specified coordinate index.
     */
    private void drawHeart(int heartIndex) {
        List<Integer> position = heartCoordinates.get(heartIndex);
        game.getSpriteBatch().draw(
                heart.getAnimatedHeart().getKeyFrame(sinusInput, true),
                position.get(0) * 32, position.get(1) * 32, 32, 32
        );
    }

    /**
     * Renders a specific map element based on its type and position.
     */
    private void renderMapElement(List<Integer> coordinates) {
        int type = map.get(coordinates);

        switch (type) {
            case 0 -> renderWall(coordinates);
            case 1 -> renderPlayerSpawn(coordinates);
            case 2 -> renderDoor(coordinates);
            case 3 -> renderObstacle(coordinates);
            case 5 -> renderKey(coordinates);
        }
    }

    private void renderWall(List<Integer> coordinates) {
        game.getSpriteBatch().draw(wall.getHorizontalWall(), coordinates.get(0) * 32, coordinates.get(1) * 32, 32, 32);
    }

    private void renderPlayerSpawn(List<Integer> coordinates) {
        game.getSpriteBatch().draw(decoration.getStaircase(), coordinates.get(0) * 32, coordinates.get(1) * 32, 32, 32);
    }

    private void renderDoor(List<Integer> coordinates) {
        game.getSpriteBatch().draw(doors.getHorizontalDoor(), coordinates.get(0) * 32, coordinates.get(1) * 32, 32, 32);
    }

    private void renderObstacle(List<Integer> coordinates) {
        game.getSpriteBatch().draw(obstacles.getSpikeAnimation().getKeyFrame(sinusInput, true), coordinates.get(0) * 32, coordinates.get(1) * 32, 32, 32);
    }

    private void renderKey(List<Integer> coordinates) {
        if (displayKey) {
            game.getSpriteBatch().draw(key.getKey(), coordinates.get(0) * 32, coordinates.get(1) * 32, 32, 32);
        }
    }

    /**
     * Renders all enemies on the map using their `render` method.
     */
    private void renderEnemies() {
        for (Enemy enemy : enemies) {
            enemy.render(game.getSpriteBatch(), ghost.getGhostDownAnimation(), sinusInput);
        }
    }

    /**
     * Setter for sinus input value.
     */
    public void setSinusInput(float sinusInput) {
        this.sinusInput = sinusInput;
    }

    /**
     * Getters for map-related attributes.
     */
    public float getMaxX() {
        return maxX;
    }

    public float getMaxY() {
        return maxY;
    }

    public float getPlayerX() {
        return playerX;
    }

    public float getPlayerY() {
        return playerY;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void setDisplayKey(boolean displayKey) {
        this.displayKey = displayKey;
    }

    public void setDisplayHeart1(boolean displayHeart1) {
        this.displayHeart1 = displayHeart1;
    }

    public void setDisplayHeart2(boolean displayHeart2) {
        this.displayHeart2 = displayHeart2;
    }
}
