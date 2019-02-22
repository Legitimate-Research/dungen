package com.cd;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by PlanetCookieX on 5/21/2017.
 */
public class Perk {
    private Runnable func;
    private double value;
    private String name;

    public Perk() {

    }

    public Perk(Runnable func, double value, String name) {
        this.func = func;
        this.value = value;
        this.name = name;
    }

    public Perk clone(){
        return new Perk(this.func, this.value, this.name);
    }

    public void render(Graphics2D g) {
        g.setColor(Color.orange);
        g.fillRect(0, 0, 42, 42);
    }

    public static void genPerks(double prob, World w){
        ArrayList<Perk> perks = new ArrayList<>();
        genPerks(prob, perks, w);
        for(Perk p: perks){
            p.apply();
            System.out.println("+" +p.getValue() +" " +p.getName());
        }
    }

    private static void genPerks(double prob, ArrayList<Perk> p, World w) {
        double rand = Math.random();
        if (rand > prob || prob <= 0) {
            return;
        } else {
            Perk r = Util.random(Launch.perks).clone();

            if (r.getName().contains("Attack")) {
                r.setValue(Math.pow(Math.random(), 4));
            } else if (r.getName().contains("Current Health")) {
                r.setValue(Math.random() * (w.player.maxHealth - w.player.currHealth));
            } else if (r.getName().contains("Max Health")){
                r.setValue(Math.random()*25);
            } else if (r.getName().contains("Regen Health")){
                r.setValue(Math.pow(Math.random(), 4));
            } else if (r.getName().contains("Current Manna")) {
                r.setValue(Math.random() * (w.player.maxManna - w.player.currManna));
            } else if (r.getName().contains("Max Manna")){
                r.setValue(Math.random()*25);
            } else if (r.getName().contains("Regen Manna")){
                r.setValue(Math.pow(Math.random(), 4));
            }

            p.add(r);
            genPerks(prob-0.1, p, w);
        }
    }

    public void apply() {
        func.run();
    }

    public Runnable getFunc() {
        return func;
    }

    public void setFunc(Runnable func) {
        this.func = func;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
