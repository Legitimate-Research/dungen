package com.cd;

import java.awt.*;

/**
 * Created by William on 5/21/2017.
 */
public class PerkStack {

    private Perk perk;
    private int count;

    public PerkStack(Perk perk, int count) {
        this.perk = perk;
        this.count = count;
    }

    public PerkStack(Perk perk) {
        this(perk, 1);
    }

    public void render(Graphics2D g) {
        perk.render(g);
    }

    public boolean canCombine(PerkStack other) {
        return perk.equals(other.perk);
    }

    public PerkStack combine(PerkStack other) {
        if (!canCombine(other)) {
            throw new IllegalArgumentException();
        }

        return new PerkStack(perk, count + other.count);
    }

    public Perk getPerk() {
        return perk;
    }

    public int getCount() {
        return count;
    }
}
