package com.cd.menu;

import com.cd.IInventory;
import com.cd.Launch;
import com.cd.PerkStack;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.security.AlgorithmParameterGenerator;

/**
 * Created by William on 5/21/2017.
 */
public class Slot {

    private static int SLOT_FULL_WIDTH = 50;
    private static int SLOT_ITEM_WIDTH = 42;

    private Menu menu;
    private IInventory inventory;
    private int index;
    private int x, y;

    public Slot(Menu menu, IInventory inventory, int index, int x, int y) {
        this.menu = menu;
        this.inventory = inventory;
        this.index = index;
        this.x = x;
        this.y = y;
    }

    protected boolean isMouseHovering() {
        int gX = menu.getLeft() + x;
        int gY = menu.getTop() + y;
        return gX < Launch.rawMousePos.x && Launch.rawMousePos.x < gX + SLOT_FULL_WIDTH &&
                gY < Launch.rawMousePos.y && Launch.rawMousePos.y < gY + SLOT_FULL_WIDTH;
    }

    public void render(Graphics2D g) {
        AffineTransform t = g.getTransform();
        g.translate(x, y);

        if (isMouseHovering()) {
            Composite c = g.getComposite();
            g.setColor(new Color(192, 192, 192, 100));
            //noinspection SuspiciousNameCombination
            g.fillRect(0, 0, SLOT_FULL_WIDTH, SLOT_FULL_WIDTH);

            g.setComposite(c);
        }

        int offset = (SLOT_FULL_WIDTH - SLOT_ITEM_WIDTH) / 2;
        g.translate(offset, offset);
        PerkStack stack = inventory.getStack(index);
        if (stack != null) {
            stack.render(g);
        }
        g.setTransform(t);
    }

    public Menu getMenu() {
        return menu;
    }

    public IInventory getInventory() {
        return inventory;
    }

    public int getIndex() {
        return index;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
