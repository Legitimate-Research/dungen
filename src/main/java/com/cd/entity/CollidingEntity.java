package com.cd.entity;

import com.cd.Direction;
import com.cd.World;
import com.cd.math.Vec2;
import com.cd.tile.Tile;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by William on 5/20/2017.
 */
public abstract class CollidingEntity extends Entity {

    private Vec2 positionDelta = Vec2.ZERO;

    /**
     * Set by CollidingEntity.update()
     */
    protected Set<Direction> lastCollisionDirections = EnumSet.noneOf(Direction.class);
    protected boolean collisionsEnabled = true;

    protected boolean collidesWithWalls() {
        return true;
    }

    protected boolean collidesWithTiles() {
        return true;
    }

    protected boolean collidesWithEntities() {
        return true;
    }

    protected Set<Direction> calculateCollisions(World world, Vec2 position) {
        Set<Direction> set = new HashSet<>();
        if (!collisionsEnabled) return set;

        if (collidesWithWalls()) {
            if (position.y < getRadius()) {
                set.add(Direction.UP);
            }

            if (position.y >= 9 - getRadius()) {
                set.add(Direction.DOWN);
            }

            if (position.x < getRadius()) {
                set.add(Direction.LEFT);
            }

            if (position.x >= 15 - getRadius()) {
                set.add(Direction.RIGHT);
            }
        }

        if (collidesWithTiles()) {
            for (Tile[] tileArray : world.currentRoom().getTiles()) {
                for (Tile tile : tileArray) {
                    if (!tile.isCollidable()) {
                        continue;
                    }

                    // From: https://yal.cc/rectangle-circle-intersection-test/

                    double dX = Math.max(tile.getX(), Math.min(position.x, tile.getX() + 1)) - position.x;
                    double dY = Math.max(tile.getY(), Math.min(position.y, tile.getY() + 1)) - position.y;
                    if (dX * dX + dY * dY < getRadius() * getRadius()) {

                        double buffer = 0.2;

                        if (tile.getX() <= position.x + buffer && position.x <= tile.getX() + 1 + buffer) {
                            set.add(dY > 0 ? Direction.DOWN : Direction.UP);
                        } else if (tile.getY() <= position.y + buffer && position.y <= tile.getY() + 1 + buffer) {
                            set.add(dX > 0 ? Direction.RIGHT : Direction.LEFT);
                        } else {
                            set.add(dY > 0 ? Direction.DOWN : Direction.UP);
                            set.add(dX > 0 ? Direction.RIGHT : Direction.LEFT);
                        }

                    }
                }
            }
        }

        if (collidesWithEntities()) {
            for (Entity entity : world.getEntities()) {
                if (entity == this || !entity.isCollidable())
                    continue;

                Vec2 diff = entity.getPosition().sub(position);
                if (diff.length() > entity.getRadius() + getRadius())
                    continue;

                for (Direction dir : EnumSet.of(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT)) {
                    if (dir.vec.angleBetween(diff) < Math.PI / 3) {
                        set.add(dir);
                    }
                }
            }
        }

        return set;
    }

    @Override
    public void update(World world, float d) {

        Set<Direction> collisions = calculateCollisions(world, position.add(positionDelta));
        lastCollisionDirections = collisions;

        double dX = positionDelta.x;
        double dY = positionDelta.y;

        if (collisions.contains(Direction.UP) && dY < 0) dY = 0;
        if (collisions.contains(Direction.DOWN) && dY > 0) dY = 0;

        if (collisions.contains(Direction.LEFT) && dX < 0) dX = 0;
        if (collisions.contains(Direction.RIGHT) && dX > 0) dX = 0;

        positionDelta = Vec2.ZERO;
        position = position.add(new Vec2(dX, dY));

    }

    public void addPosition(Vec2 vec) {
        positionDelta = positionDelta.add(vec);
    }

    protected void setPosition(Vec2 vec) {
        position = vec;
    }

}
