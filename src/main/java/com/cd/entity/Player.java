package com.cd.entity;

import com.cd.*;
import com.cd.entity.projectile.BasicProjectile;
import com.cd.math.Vec2;
import com.cd.menu.*;
import com.cd.menu.Menu;
import com.cd.tile.Item;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Set;

/**
 * Created by William on 5/20/2017.
 */
public class Player extends CollidingEntity implements IDamagable {

    public static double speed = System.getProperty("user.name").equalsIgnoreCase("PlanetCookieX") ? 10 : 5;

    public BufferedImage image;
    public Hud hud;
    public double currHealth, maxHealth, healthRegen, currManna, maxManna, mannaRegen, attackRegen, currAttackCoolddown, maxAttackCooldown;
    public Item weapon;
    public com.cd.menu.Menu menu;

    public Player() {
        image = Images.load("player.png");
        radius = 12 / 32f;
        this.hud = new Hud();

        maxHealth = 100.0;
        currHealth = maxHealth;
        healthRegen = 0.01;

        maxManna = 100.0;
        currManna = maxManna;
        mannaRegen = 0.1;

        attackRegen = 1.0;
        currAttackCoolddown = 0.0;
        maxAttackCooldown = 75.0;
        attackRegen = 1.0;
    }

    @Override
    public void render(World world, Graphics2D g) {

        g.setColor(Color.orange);

        AffineTransform transform = g.getTransform();
        g.translate(position.x * 32, position.y * 32);
        g.rotate(Launch.mousePos.sub(position).angle() - Math.PI / 2);
        g.translate(-12, -12);
        g.drawImage(image, 0, 0, 24, 24, null);
        g.setTransform(transform);
        hud.render(world, g);
    }

    private Vec2 getUserMovementVector() {
        Vec2 vec = Vec2.ZERO;
        if (Launch.isKeyPressed(KeyEvent.VK_W)) vec = vec.add(Direction.UP.vec);
        if (Launch.isKeyPressed(KeyEvent.VK_A)) vec = vec.add(Direction.LEFT.vec);
        if (Launch.isKeyPressed(KeyEvent.VK_S)) vec = vec.add(Direction.DOWN.vec);
        if (Launch.isKeyPressed(KeyEvent.VK_D)) vec = vec.add(Direction.RIGHT.vec);
        return vec;
    }

    private void trySwitchRoom(World world) {
        loop:
        for (Direction direction : lastCollisionDirections) {
            if (!world.currentRoom().hasExit(direction)) continue;

            switch (direction) {
                case UP:
                case DOWN:
                    if (getPosition().x - getRadius() < 5 || getPosition().x + getRadius() > 10)
                        continue loop;
                    break;
                case LEFT:
                case RIGHT:
                    if (getPosition().y - getRadius() < 3 || getPosition().y + getRadius() > 6)
                        continue loop;
                    break;
            }

            switch (direction) {
                case UP:
                    if (position.y > 1) continue loop;
                    break;
                case DOWN:
                    if (position.y < 8) continue loop;
                    break;
                case LEFT:
                    if (position.x > 1) continue loop;
                    break;
                case RIGHT:
                    if (position.x < 14) continue loop;
                    break;
            }

            world.switchToRoom(direction);
        }

    }

    @Override
    public void update(World world, float d) {
        collisionsEnabled = !Launch.isKeyPressed(KeyEvent.VK_C);

        if (Launch.justPressed(KeyEvent.VK_T)) {
            menu = new Menu(new MenuManager(this, world));
        }

        addPosition(getUserMovementVector().mul(d * speed));
        super.update(world, d);

        trySwitchRoom(world);

        if (Launch.mouseDown && currAttackCoolddown <= 0) {
            currAttackCoolddown = maxAttackCooldown;
            world.getEntities().add(new BasicProjectile(this, Launch.mousePos.sub(getPosition()), 5));
        }

        if (currHealth != maxHealth) {
            if (currHealth + healthRegen > maxHealth) {
                currHealth = maxHealth;
            } else {
                currHealth += healthRegen;
            }
        }

        if (currManna != maxManna) {
            if (currManna + mannaRegen > maxHealth) {
                currManna = maxManna;
            } else {
                currManna += mannaRegen;
            }
        }

        if (currAttackCoolddown > 0) {
            currAttackCoolddown -= attackRegen;
        }

    }

    @Override
    public void setPosition(Vec2 vec) {
        super.setPosition(vec);
    }


    @Override
    public void damage(float amount) {
        if (currHealth - amount < 0)
            currHealth = 0;
        else
            currHealth -= amount;
    }
}
