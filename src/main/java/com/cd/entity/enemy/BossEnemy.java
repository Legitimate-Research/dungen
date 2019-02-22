package com.cd.entity.enemy;

import com.cd.BossAttack;
import com.cd.Images;
import com.cd.Launch;
import com.cd.World;
import com.cd.math.Mat3;
import com.cd.math.Vec2;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by William on 5/21/2017.
 */
public class BossEnemy extends MeleeEnemy {

    private static BufferedImage bossImage = Images.load("bossImage.png");
    private static BufferedImage bossOverlay = Images.load("bossOverlay.png");

    private float spinnerRotation;
    private float rotation;

    private ArrayList<BossAttack> attackPattern = new ArrayList<>();

    public BossEnemy(Vec2 position) {
        super(position);
        cooldownTime = 3;
        try {
            health = 50 * Launch.world.rooms.size();
        }catch (NullPointerException e) {
            health = 50;
        }
    }

    @Override
    public void render(World world, Graphics2D g) {
        AffineTransform t = g.getTransform();
        g.translate(getPosition().x * 32, getPosition().y * 32);
        g.drawImage(bossImage, 0, 0, 48, 48, null);
        g.rotate(spinnerRotation, 24, 24);
        for (int i = 0; i < 3; i++) {
            AffineTransform t2 = g.getTransform();
            g.rotate(2 * Math.PI * i / 3, 24, 24);
            g.rotate(rotation);
            g.drawImage(bossOverlay, 0, 0, 48, 48, null);
            g.setTransform(t2);
        }


        g.setTransform(t);
        g.translate(getPosition().x * 32, getPosition().y * 32);
        g.translate(24, 24);

        g.translate(0, 1.5 * Math.cos(System.currentTimeMillis() / 300d));

        g.translate(-3, -3);
        g.setColor(Color.orange);
        g.fillArc(0, 0, 6, 6, 0, 360);

        g.setTransform(t);

        for (int i = 0; i < 3; i++) {
            Mat3 transform = Mat3.translate(getPosition().mul(32).add(new Vec2(48, 48)))
                    .mul(Mat3.rotate(2 * Math.PI * i / 3 + -spinnerRotation, new Vec2(24, 24)))
                    .mul(Mat3.rotate(-rotation));

            Vec2 vec = transform.mul(new Vec2(24, 24));

            g.fillRect((int) vec.x, (int) vec.y, 5, 5);
        }

    }

    @Override
    public void update(World world, float d) {
        super.update(world, d);
        rotation += (cooldownTime - cooldown) * 2 * d;
        spinnerRotation += 2 * d;





    }
}
