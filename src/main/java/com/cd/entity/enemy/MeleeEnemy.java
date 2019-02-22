package com.cd.entity.enemy;

import com.cd.World;
import com.cd.entity.IDamagable;
import com.cd.entity.projectile.BasicProjectile;
import com.cd.math.Vec2;

import java.awt.*;

/**
 * Created by William on 5/21/2017.
 */
public class MeleeEnemy extends EnemyBase {

    protected float cooldownTime = 1.5f;
    protected float cooldown = cooldownTime;

    public MeleeEnemy(Vec2 position) {
        super(20);
        this.position = position;
    }

    @Override
    public void update(World world, float d) {
        super.update(world, d);

        if (cooldown <= 0) {
            if (target != null) {
                Vec2 diff = target.getPosition().sub(getPosition());
                if (diff.length() < getRadius() + target.getRadius() + 0.5) {
                    if (target instanceof IDamagable) ((IDamagable) target).damage(5);

                    cooldown = cooldownTime;
                }
            }
        } else {
            cooldown -= d;
        }
    }

    @Override
    protected void renderEnemy(World world, Graphics2D g) {
        super.renderEnemy(world, g);
        if (cooldownTime - cooldown < 0.1f) {
            g.setColor(Color.red);
            g.fillArc(0, 0, 24, 24, 0, 360);
        }
    }
}
