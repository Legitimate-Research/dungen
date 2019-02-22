package com.cd.menu;

import com.cd.IInventory;
import com.cd.Images;
import com.cd.Launch;
import com.cd.PerkStack;
import com.cd.math.Vec2;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

/**
 * Created by William on 5/21/2017.
 */
public class Menu {

    protected MenuManager manager;
    protected java.util.List<Slot> slots = new ArrayList<>();
    protected PerkStack currentStack;
    protected int left, top;

    public Menu(MenuManager manager) {
        this.manager = manager;

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                Vec2 vec = new Vec2(41, 15).add(new Vec2(x, y).mul(50));
                addSlot(new Slot(this, new IInventory() {
                    @Override
                    public int getSize() {
                        return 1;
                    }

                    @Override
                    public void setStack(int index, PerkStack stack) {
                    }

                    @Override
                    public PerkStack getStack(int index) {
                        return null;
                    }
                }, 0, (int) vec.x, (int) vec.y));
            }
        }
    }

    public void render(Graphics2D g) {
        g.setColor(Color.cyan);
        g.drawImage(Images.load("menu.png"), 0, 0, null);

        for (Slot slot : slots) {
            AffineTransform t = g.getTransform();

            slot.render(g);

            g.setTransform(t);
        }

        g.drawString("" + Launch.rawMousePos, 50, 50);

    }

    public Dimension getSize() {
        return new Dimension(540, 400);
    }

    public void close() {
        if (currentStack != null) {
            manager.getWorld().dropStack(currentStack);
        }
        manager.getPlayer().menu = null;
    }

    public void addSlot(Slot slot) {
        slots.add(slot);
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }
}
