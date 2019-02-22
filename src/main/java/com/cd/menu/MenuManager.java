package com.cd.menu;

import com.cd.World;
import com.cd.entity.Player;

/**
 * Created by William on 5/21/2017.
 */
public class MenuManager {

    private Player player;
    private World world;

    public MenuManager(Player player, World world) {
        this.player = player;
        this.world = world;
    }

    public Player getPlayer() {
        return player;
    }

    public World getWorld() {
        return world;
    }

}
