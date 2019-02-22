package com.cd.entity.enemy;

import com.cd.World;
import com.cd.entity.projectile.BasicProjectile;
import com.cd.math.Vec2;

/**
 * Created by William on 5/21/2017.
 */
public class ShootingEnemy extends EnemyBase {

    protected float cooldownTime = 1.5f;
    protected float cooldown = cooldownTime;

    public ShootingEnemy(Vec2 position) {
        super(20);
        this.position = position;
    }

    @Override
    public void update(World world, float d) {
        super.update(world, d);

        if (cooldown <= 0) {
            if (target != null) {
                world.getEntities().add(new BasicProjectile(this, target.getPosition().sub(getPosition()).rotate(Math.random() / 3 - 1 / 6d), 10));
                cooldown = cooldownTime;
            }
        } else {
            cooldown -= d;
        }


    }
}
