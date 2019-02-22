package com.cd;

import com.cd.math.Vec2;

/**
 * Created by William on 5/20/2017.
 */
public enum Direction {
    NONE(0, 0),
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    public final int x, y;
    public final Vec2 vec;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
        this.vec = new Vec2(x, y);
    }

    public Direction opposite() {
        switch (this) {
            case UP: return DOWN;
            case DOWN: return UP;
            case LEFT: return RIGHT;
            case RIGHT: return LEFT;
            default:
                return NONE;
        }
    }




}
