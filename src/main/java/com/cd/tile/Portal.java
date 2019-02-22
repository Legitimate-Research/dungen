package com.cd.tile;

import com.cd.Images;
import com.cd.Launch;
import com.cd.World;
import com.cd.math.Vec2;
import org.json.simple.JSONObject;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * Created by PlanetCookieX on 5/21/2017.
 */
public class Portal extends Tile {
    public static BufferedImage portalOverlay = Images.load("Portal.png");
    public Portal(int x, int y, JSONObject data) {
        super(x, y, data);
    }

    @Override
    public void update(World world, float d) {
        super.update(world, d);
        boolean playerIsClose = new Vec2(getX() + 0.5, getY() + 0.5).sub(world.player.getPosition()).length() < 1 + world.player.getRadius();
        if (playerIsClose && Launch.justPressed(KeyEvent.VK_SPACE)) {
            world.goToBoss();
        }
    }
    @Override
    public void render(World world, Graphics2D g) {
        super.render(world, g);
        g.drawImage(Portal.portalOverlay, getX() * 32, getY() * 32, null);
        if (new Vec2(getX() + 0.5, getY() + 0.5).sub(world.player.getPosition()).length() < 1 + world.player.getRadius()) {
            String tip = "Space to enter";
            Font og = g.getFont();
            g.setFont(og.deriveFont(12f));

            int tipWidth = g.getFontMetrics().stringWidth(tip);
            int tipHeight = g.getFontMetrics().getHeight();

            g.setColor(Color.MAGENTA);
            g.drawString(tip, super.getX() * 32 + 16 - tipWidth / 2, super.getY() * 32);

            g.setFont(og);
        }
    }
}
