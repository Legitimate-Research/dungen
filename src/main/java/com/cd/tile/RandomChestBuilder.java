package com.cd.tile;

import com.cd.Util;
import org.json.simple.JSONObject;

/**
 * Created by William on 5/21/2017.
 */
public class RandomChestBuilder {

    public static Tile build(int x, int y, JSONObject data) {
        if (x == 0 || y == 0) return new Tile(x, y, data);

        if (Util.rand.nextInt(50) == 0) return new ChestTile(x, y, data);
        else return new Tile(x, y, data);
    }

}
