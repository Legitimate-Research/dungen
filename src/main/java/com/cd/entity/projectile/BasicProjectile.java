package com.cd.entity.projectile;

import com.cd.World;
import com.cd.entity.Entity;
import com.cd.math.Vec2;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by William on 5/21/2017.
 */
public class BasicProjectile extends Projectile {

    public BasicProjectile(Vec2 position, Vec2 velocity, double duration) {
        super(position, velocity.withLength(5), duration);
    }

    public BasicProjectile(Entity shooter, Vec2 velocity, double duration) {
        super(shooter, velocity.withLength(5), duration);
    }

    @Override
    public void render(World world, Graphics2D g) {
        AffineTransform t = g.getTransform();
        g.translate(getPosition().x * 32, getPosition().y * 32);
        g.rotate(getVelocity().angle());
        g.setColor(Color.red);
        g.drawLine(0, 0, 5, 0);
        g.setTransform(t);
    }
}
