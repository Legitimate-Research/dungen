package com.cd.menu;

import java.awt.*;

/**
 * Created by William on 5/21/2017.
 */
public class WelcomeMenu extends Menu {

    private long startTime = -1;

    public WelcomeMenu(MenuManager manager) {
        super(manager);
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.cyan);
        Font f = g.getFont();
        g.setFont(g.getFont().deriveFont(50f));
        g.drawString("Welcome!", 150, 200);
        g.setFont(f);

        if (startTime == -1) {
            startTime = System.currentTimeMillis();
        }

        if (System.currentTimeMillis() - startTime > 1000) {
            close();
        }


    }
}
