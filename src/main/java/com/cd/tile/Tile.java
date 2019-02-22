package com.cd.tile;

import com.cd.IRenderable;
import com.cd.Images;
import com.cd.World;
import org.json.simple.JSONObject;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by PlanetCookieX on 5/20/2017.
 */
public class Tile implements IRenderable {

    public static Tile build(int x, int y, JSONObject data) {
        return new Tile(x, y, data);
    }

    private int x, y;

    private String type;
    private BufferedImage image;
    private boolean isCollidable;

    public Tile(int x, int y, JSONObject data) {
        this.x = x;
        this.y = y;

        type = ((String) data.get("name"));
        for (int i = 1; i < type.length(); i++) {
            if (Character.isUpperCase(type.charAt(i))) {
                type = type.substring(0, i);
                break;
            }
        }

        if (data.containsKey("image")) {
            image = Images.load((String) data.get("image"));
        }

        if (data.containsKey("collidable")) {
            isCollidable = (boolean) data.get("collidable");
        }

    }

    public boolean isCollidable() {
        return isCollidable;
    }

    @Override
    public void render(World world, Graphics2D g) {
        g.setColor(Color.gray);
        g.fillRect(x * 32, y * 32, 32, 32);

        g.drawImage(image, x * 32, y * 32, null);

    }

    @Override
    public void update(World world, float d) {
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getType() {
        return type;
    }
}
