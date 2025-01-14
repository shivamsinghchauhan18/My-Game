package de.tum.cit.fop.maze;

import de.tum.cit.fop.maze.GameElements.Characters.Enemy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Utility class providing static methods for various operations in MazeRunnerGame.
 * Includes map reading, movement validation, and interaction with game elements.
 */
public class Utils {

    /**
     * Reads a map file and constructs a representation as a map of coordinates and their values.
     *
     * @param filePath Path to the map file.
     * @return A map where the key is a list of two integers (x, y coordinates),
     *         and the value is an integer associated with these coordinates.
     */
    public static Map<List<Integer>, Integer> readMap(String filePath) {
        Map<List<Integer>, Integer> map = new HashMap<>();
        Path path = Paths.get(filePath);

        try {
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                String[] parts = line.split("=");

                if (parts.length == 2) {
                    String[] xy = parts[0].split(",");
                    if (xy.length == 2) {
                        int x = Integer.parseInt(xy[0].trim());
                        int y = Integer.parseInt(xy[1].trim());
                        int value = Integer.parseInt(parts[1].trim());
                        map.put(Arrays.asList(x, y), value);
                    } else {
                        System.err.println("Invalid coordinates format in map file: " + Arrays.toString(xy));
                    }
                } else {
                    System.err.println("Invalid line format in map file: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading map file: " + e.getMessage());
            e.printStackTrace();
        }

        return map;
    }

    /**
     * Validates if a character can move in the specified direction based on the map state.
     *
     * @param characterX The character's current x-coordinate.
     * @param characterY The character's current y-coordinate.
     * @param direction  The direction of intended movement.
     * @param mapLoader  The MapLoader instance containing the map layout.
     * @param hasKey     Whether the character possesses a key.
     * @return True if the character can move; false otherwise.
     */
    public static boolean canCharacterMove(float characterX, float characterY, Direction direction, MapLoader mapLoader, boolean hasKey) {
        List<List<Integer>> walls = mapLoader.getWallCoordinates();
        List<List<Integer>> doors = mapLoader.getDoorCoordinates();

        switch (direction) {
            case RIGHT:
                return !isAtCoordinate(characterX + 0.2f, characterY, walls) &&
                        (!isAtCoordinate(characterX + 0.2f, characterY, doors) || hasKey);
            case UP:
                return !isAtCoordinate(characterX, characterY + 0.2f, walls) &&
                        (!isAtCoordinate(characterX, characterY + 0.2f, doors) || hasKey);
            case LEFT:
                return !isAtCoordinate(characterX - 0.3f, characterY, walls) &&
                        (!isAtCoordinate(characterX - 0.3f, characterY, doors) || hasKey);
            case DOWN:
                return !isAtCoordinate(characterX, characterY - 0.3f, walls) &&
                        (!isAtCoordinate(characterX, characterY - 0.3f, doors) || hasKey);
            default:
                return false;
        }
    }

    /**
     * Checks if given coordinates are at any specified location from a list.
     *
     * @param x           The x-coordinate to check.
     * @param y           The y-coordinate to check.
     * @param coordinates A list of coordinates to compare against.
     * @return True if the coordinates match; false otherwise.
     */
    public static boolean isAtCoordinate(float x, float y, List<List<Integer>> coordinates) {
        final float tolerance = 0.5f;

        for (List<Integer> coordinate : coordinates) {
            if (coordinate.size() == 2) {
                float coordX = coordinate.get(0);
                float coordY = coordinate.get(1);
                if (Math.abs(x - coordX) < tolerance && Math.abs(y - coordY) < tolerance) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if given coordinates correspond to a heart's position.
     *
     * @param x             The x-coordinate to check.
     * @param y             The y-coordinate to check.
     * @param heartPosition Coordinates of the heart.
     * @return True if the coordinates match the heart's position; false otherwise.
     */
    public static boolean isHeart(float x, float y, List<Integer> heartPosition) {
        final float tolerance = 0.5f;

        if (heartPosition.size() == 2) {
            float heartX = heartPosition.get(0);
            float heartY = heartPosition.get(1);
            return Math.abs(x - heartX) < tolerance && Math.abs(y - heartY) < tolerance;
        }
        return false;
    }

    /**
     * Checks if given coordinates correspond to an enemy's position.
     *
     * @param x       The x-coordinate to check.
     * @param y       The y-coordinate to check.
     * @param enemies List of Enemy objects.
     * @return True if the coordinates match any enemy's position; false otherwise.
     */
    public static boolean isEnemy(float x, float y, List<Enemy> enemies) {
        final float tolerance = 0.5f;

        for (Enemy enemy : enemies) {
            float enemyX = enemy.getX();
            float enemyY = enemy.getY();
            if (Math.abs(x - enemyX) < tolerance && Math.abs(y - enemyY) < tolerance) {
                return true;
            }
        }
        return false;
    }
}