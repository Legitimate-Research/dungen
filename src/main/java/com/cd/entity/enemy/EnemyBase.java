package com.cd.entity.enemy;

import com.cd.Images;
import com.cd.Perk;
import com.cd.World;
import com.cd.entity.CollidingEntity;
import com.cd.entity.Entity;
import com.cd.entity.IDamagable;
import com.cd.entity.Player;
import com.cd.math.Vec2;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Created by William on 5/21/2017.
 */
public abstract class EnemyBase extends CollidingEntity implements IDamagable {

    protected double direction = 0;

    protected float health;
    protected Entity target;
    protected Vec2 velocity = Vec2.ZERO;

    public EnemyBase(float health) {
        this.health = health;
        this.radius = 12 / 32f;
    }

    @Override
    public void damage(float amount) {
        health -= amount;
    }

    public BufferedImage getImage() {
        return Images.load("enemy.png");
    }

    @Override
    public void update(World world, float d) {
        if (health <= 0) {
            Perk.genPerks(.5, world);
            world.getEntities().remove(this);
            return;
        }

        if (target != null) {

            Vec2 diff = target.getPosition().sub(getPosition());

            if (diff.length() > getRadius() + target.getRadius() + 0.4) {
                velocity = diff.withLength(3);
            } else {
                velocity = Vec2.ZERO;
            }

        }

        addPosition(velocity.mul(d));

        super.update(world, d);

        for (Entity e : world.getEntities()) {
            if (e instanceof Player && e.getPosition().sub(getPosition()).length() < 3) {
                target = e;
            }
        }

        if (target != null) {
            direction = target.getPosition().sub(getPosition()).angle();
        }



    }

    protected void renderEnemy(World world, Graphics2D g) {
        g.drawImage(getImage(), 0, 0, 24, 24, null);
    }

    @Override
    public void render(World world, Graphics2D g) {
        AffineTransform t = g.getTransform();
        g.translate(getPosition().x * 32, getPosition().y * 32);
        g.rotate(direction + Math.PI /2);
        g.translate(-12, -12);
        renderEnemy(world, g);

        g.setTransform(t);
    }



}
