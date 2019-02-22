package com.cd;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by PlanetCookieX on 5/21/2017.
 */
public class Hud implements IRenderable {
    public Hud() {
    }

    @Override
    public void render(World world, Graphics2D g) {
        // Health
        Color gray = new Color(10, 10, 10);
        //g.setColor(gray);
        //g.fillRect(World.WIDTH-20*2, World.HEIGHT-80, 20, 80);

        Composite ac = g.getComposite();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));

        Color red = new Color(255, 0, 0);
        g.setColor(red);
        g.fillRect(World.WIDTH-20*2, World.HEIGHT-(int)(80*(world.player.currHealth/world.player.maxHealth)), 20, (int)(80*(world.player.currHealth/world.player.maxHealth)));

        g.translate(World.WIDTH-20*2+5, World.HEIGHT);
        g.rotate(Math.toRadians(90));
        g.setXORMode(red);
        g.drawString(world.player.currHealth +"/" +world.player.maxHealth, 0, 0);
        g.setPaintMode();
        g.rotate(Math.toRadians(-90));
        g.translate(-(World.WIDTH-20*2+5), -(World.HEIGHT));
        // Manna
        g.setColor(gray);
        g.fillRect(World.WIDTH-20, World.HEIGHT-80, 20, 80);

        Color cyan = new Color(0, 103, 255);
        g.setColor(cyan);
        g.fillRect(World.WIDTH-20, World.HEIGHT-(int)(80*(world.player.currManna/world.player.maxManna)), 20, (int)(80*(world.player.currManna/world.player.maxManna)));
        // Weapon
        g.setColor(gray);

        g.setComposite(ac);
    }

    @Override
    public void update(World world, float d) {

    }
}
