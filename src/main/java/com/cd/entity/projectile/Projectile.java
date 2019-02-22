package com.cd.entity.projectile;

import com.cd.Direction;
import com.cd.World;
import com.cd.entity.CollidingEntity;
import com.cd.entity.Entity;
import com.cd.entity.IDamagable;
import com.cd.math.Vec2;
import java.util.Set;

/**
 * Created by William on 5/21/2017.
 */
public abstract class Projectile extends CollidingEntity {

    protected Entity shooter;
    protected Vec2 velocity;
    protected double duration;


    public Projectile(Vec2 position, Vec2 velocity, double duration) {
        this.position = position;
        this.velocity = velocity;
        this.radius = 0.2f;
        this.duration = duration;
    }

    public Projectile(Entity shooter, Vec2 velocity, double duration) {
        this.shooter = shooter;
        this.position = shooter.getPosition().add(velocity.normalized().mul(shooter.getRadius()));
        this.velocity = velocity;
        this.radius = 0.2f;
        this.duration = duration;
    }

    protected boolean doesCollide(World world, Entity entity) {
        return entity instanceof IDamagable;
    }

    protected Entity getCollidingEntity(World world){
        for (Entity entity : world.getEntities()) {
            if (entity != shooter && doesCollide(world, entity) && getPosition().sub(entity.getPosition()).length() < getRadius() + entity.getRadius()) {
                return entity;
            }
        }
        return null;
    }

    protected void handleCollision(World world, Entity target) {
        ((IDamagable) target).damage(10);
        world.getEntities().remove(this);
    }

    @Override
    public void update(World world, float d) {
        duration -= d;
        if (duration <= 0) {
            world.getEntities().remove(this);
        }

        position = position.add(velocity.mul(d));

        Entity target = getCollidingEntity(world);
        if (target != null) {
            handleCollision(world, target);
        }else {

            Set<Direction> collisions = calculateCollisions(world, position);
            if (!collisions.isEmpty()) {
                world.getEntities().remove(this);
                //velocity = getVelocity().withLength(1e-8);
            }

        }
    }

    @Override
    public boolean isCollidable() {
        return false;
    }

    @Override
    protected boolean collidesWithEntities() {
        return false;
    }

    public Entity getShooter() {
        return shooter;
    }

    public void setShooter(Entity shooter) {
        this.shooter = shooter;
    }

    public Vec2 getVelocity() {
        return velocity;
    }
}
