package com.cd.entity;

import com.cd.IRenderable;
import com.cd.World;
import com.cd.math.Vec2;

import java.awt.*;

/**
 * Created by William on 5/20/2017.
 */
public abstract class Entity implements IRenderable {

    protected Vec2 position = Vec2.ZERO;
    protected float radius;

    public boolean isCollidable() {
        return true;
    }

    public Vec2 getPosition() {
        return position;
    }

    public float getRadius() {
        return radius;
    }
}
