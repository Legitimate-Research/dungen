package com.cd;

import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * Created by William on 5/20/2017.
 */
public class GameCanvas extends Canvas {

    public void draw(World world, com.cd.menu.Menu menu) {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            bs = getBufferStrategy();
        }

        Graphics2D g = (Graphics2D) bs.getDrawGraphics();
        g.clearRect(0, 0, getWidth(), getHeight());

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g.scale(2, 2);

        world.render(g);

        g.scale(0.5, 0.5);

        if (menu != null) {
            menu.setLeft((int) (getWidth() / 2 - menu.getSize().getWidth() / 2));
            menu.setTop((int) (getHeight() / 2 - menu.getSize().getHeight() / 2d));

            g.translate(menu.getLeft(), menu.getTop());

            menu.render(g);
        }

        g.dispose();
        bs.show();
    }

}
