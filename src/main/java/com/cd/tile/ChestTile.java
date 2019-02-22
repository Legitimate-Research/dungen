package com.cd.tile;

import com.cd.*;
import com.cd.entity.enemy.ShootingEnemy;
import com.cd.math.Vec2;
import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by William on 5/21/2017.
 */
public class ChestTile extends Tile {

    public static BufferedImage chestOverlay = Images.load("chest.png");

    public ChestTile(int x, int y, JSONObject data) {
        super(x, y, data);
    }

    @Override
    public void update(World world, float d) {
        super.update(world, d);
        boolean playerIsClose = new Vec2(getX() + 0.5, getY() + 0.5).sub(world.player.getPosition()).length() < 1 + world.player.getRadius();
        if (playerIsClose && Launch.justPressed(KeyEvent.VK_SPACE)) {
            open(world);
        }
    }

    private void open(World w) {

        if (Math.random() < .05) {
            w.getEntities().add(new ShootingEnemy(new Vec2(getX(), getY() )));
        } else {
           Perk.genPerks(.9, w);
        }
        w.currentRoom().addTile(getX(), getY(), Util.loadTileFromJson(getType() + "Floor", getX(), getY()));
    }



    @Override
    public void render(World world, Graphics2D g) {
        super.render(world, g);
        g.drawImage(chestOverlay, getX() * 32, getY() * 32, null);
        if (new Vec2(getX() + 0.5, getY() + 0.5).sub(world.player.getPosition()).length() < 1 + world.player.getRadius()) {
            String tip = "Space to open";
            Font og = g.getFont();
            g.setFont(og.deriveFont(8f));

            int tipWidth = g.getFontMetrics().stringWidth(tip);
            int tipHeight = g.getFontMetrics().getHeight();

            g.setColor(Color.MAGENTA);
            g.drawString(tip, super.getX() * 32 + 16 - tipWidth / 2, super.getY() * 32);

            g.setFont(og);
        }
    }

    @Override
    public boolean isCollidable() {
        return true;
    }
}
