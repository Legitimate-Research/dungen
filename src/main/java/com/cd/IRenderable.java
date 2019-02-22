package com.cd;

import java.awt.*;

/**
 * Created by William on 5/20/2017.
 */
public interface IRenderable {

    void render(World world, Graphics2D g);

    void update(World world, float d);

}
