package com.cd;

import com.cd.entity.Entity;
import com.cd.tile.Tile;
import org.json.simple.JSONObject;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by William on 5/20/2017.
 */
public class Room implements IRenderable {

    private Tile[][] tiles;
    private boolean hasUp;
    private boolean hasDown;
    private boolean hasLeft;
    private boolean hasRight;
    private int weight;

    private ArrayList<Entity> entities = new ArrayList<>();

    public Room(RoomTemplate template) {
        this.tiles = new Tile[15][9];
    }

    public void addTile(int x, int y, Tile t) {
        tiles[x][y] = t;
    }

    public void addExits(boolean up, boolean down, boolean left, boolean right) {
        this.hasUp = up;
        this.hasDown = down;
        this.hasLeft = left;
        this.hasRight = right;

        if (up) {
            for (int i = 5; i <= 9; i++) {
                tiles[i][0] = Util.loadTileFromJson(tiles[i][0].getType() + "ExitUp", i, 0);
            }
        }

        if (down) {
            for (int i = 5; i <= 9; i++) {
                tiles[i][8] = Util.loadTileFromJson(tiles[i][8].getType() + "ExitDown", i, 8);
            }
        }

        if (left) {
            for (int i = 3; i <= 5; i++) {
                tiles[0][i] = Util.loadTileFromJson(tiles[0][i].getType() + "ExitLeft", 0, i);
            }
        }

        if (right) {
            for (int i = 3; i <= 5; i++) {
                tiles[14][i] = Util.loadTileFromJson(tiles[14][i].getType() + "ExitRight", 14, i);
            }
        }

    }

    @Override
    public void render(World world, Graphics2D g) {

        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 15; x++) {
                Tile tile = tiles[x][y];
                if (tile != null) {
                    tile.render(world, g);
                }
            }
        }

    }

    @Override
    public void update(World world, float d) {

        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 15; x++) {
                Tile tile = tiles[x][y];
                if (tile != null) {
                    tile.update(world, d);
                }
            }
        }

    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public boolean isHasUp() {
        return hasUp;
    }

    public void setHasUp(boolean hasUp) {
        this.hasUp = hasUp;
    }

    public boolean isHasDown() {
        return hasDown;
    }

    public void setHasDown(boolean hasDown) {
        this.hasDown = hasDown;
    }

    public boolean isHasLeft() {
        return hasLeft;
    }

    public void setHasLeft(boolean hasLeft) {
        this.hasLeft = hasLeft;
    }

    public boolean isHasRight() {
        return hasRight;
    }

    public void setHasRight(boolean hasRight) {
        this.hasRight = hasRight;
    }

    public boolean hasExit(Direction direction) {
        switch (direction) {
            case UP:
                return isHasUp();
            case DOWN:
                return isHasDown();
            case LEFT:
                return isHasLeft();
            case RIGHT:
                return isHasRight();
            default:
                return false;
        }
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;

    }
}