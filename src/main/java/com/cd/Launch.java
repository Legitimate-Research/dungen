package com.cd;

import com.cd.entity.Player;
import com.cd.math.Vec2;
import com.cd.menu.MenuManager;
import com.cd.menu.WelcomeMenu;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by William on 5/20/2017.
 */
public class Launch {

    private static final boolean[] volatileKeysPressed = new boolean[65536];
    private static final boolean[] keysPressed = new boolean[65536];
    private static final boolean[] lastKeysPressed = new boolean[65536];
    public static boolean mouseDown = false;

    public static World world;
    public static ArrayList<Perk> perks = new ArrayList<>();

    public static Vec2 mousePos = new Vec2(0, 0);
    public static Vec2 rawMousePos = new Vec2(0, 0);

    public static boolean isKeyPressed(int key) {
        synchronized (keysPressed) {
            return keysPressed[key];
        }
    }

    public static boolean justPressed(int key) {
        synchronized (keysPressed) {
            return keysPressed[key] && !lastKeysPressed[key];
        }
    }

    public static void main(String[] args) {


        JFrame frame = new JFrame();

        GameCanvas canvas = new GameCanvas();
        canvas.setSize(480 * 2, 288 * 2);
        canvas.requestFocus();
        frame.add(canvas);

        frame.setResizable(false);
        frame.pack();

        Player player = new Player();

        world = new World(player, 1);

        player.menu = new WelcomeMenu(new MenuManager(player, world));

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setTitle("DunGen");

        canvas.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                mouseDown = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mouseDown = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        canvas.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                rawMousePos = new Vec2(e.getX(), e.getY());
                mousePos = new Vec2(e.getX() / 64d, e.getY() / 64d);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                rawMousePos = new Vec2(e.getX(), e.getY());
                mousePos = new Vec2(e.getX() / 64d, e.getY() / 64d);
            }
        });
        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                volatileKeysPressed[e.getKeyCode()] = true;
            }

            @Override
            public void keyReleased(KeyEvent e) {
                volatileKeysPressed[e.getKeyCode()] = false;
            }
        });


        frame.setVisible(true);
        frame.setFocusable(false);
        canvas.requestFocusInWindow();
        canvas.requestFocus();

        genPerks(world);

        long lastStartTime = System.currentTimeMillis();

        while (true) {

            long startTime = System.currentTimeMillis();

            float d = (startTime - lastStartTime) / 1000f;

            frame.setTitle("DunGen | d: \t" + d);

            if (player.menu != null) {
                canvas.draw(world, player.menu);
            }else {
                world.update(d);

                canvas.draw(world, null);
            }

            synchronized (keysPressed) {
                System.arraycopy(keysPressed, 0, lastKeysPressed, 0, keysPressed.length);
                System.arraycopy(volatileKeysPressed, 0, keysPressed, 0, keysPressed.length);
            }

            lastStartTime = startTime;

            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }

    public static void genPerks(World w){
        Perk maxHealthUp = new Perk();
        maxHealthUp.setName("Max Health Up");
        maxHealthUp.setFunc(new Runnable() {
            @Override
            public void run() {
                w.player.maxHealth += maxHealthUp.getValue();
            }
        });
        perks.add(maxHealthUp);


        Perk currHealthUp = new Perk();
        currHealthUp.setName("Current Health Up");
        currHealthUp.setFunc(new Runnable() {
            @Override
            public void run() {
                w.player.currHealth += currHealthUp.getValue();
            }
        });
        perks.add(currHealthUp);


        Perk regenHealthUp = new Perk();
        regenHealthUp.setName("Regen Health Up");
        regenHealthUp.setFunc(new Runnable() {
            @Override
            public void run() {
                w.player.healthRegen += regenHealthUp.getValue();
            }
        });
        perks.add(regenHealthUp);


        Perk maxMannaUp = new Perk();
        maxMannaUp.setName("Max Manna Up");
        maxMannaUp.setFunc(new Runnable() {
            @Override
            public void run() {
                w.player.maxManna += maxMannaUp.getValue();
            }
        });
        perks.add(maxMannaUp);


        Perk currMannaUp = new Perk();
        currMannaUp.setName("Current Manna Up");
        currMannaUp.setFunc(new Runnable() {
            @Override
            public void run() {
                w.player.currManna += currMannaUp.getValue();
            }
        });
        perks.add(currMannaUp);


        Perk regenMannaUp = new Perk();
        regenMannaUp.setName("Regen Manna Up");
        regenMannaUp.setFunc(new Runnable() {
            @Override
            public void run() {
                w.player.mannaRegen += regenMannaUp.getValue();
            }
        });
        perks.add(regenMannaUp);


        Perk attackSpeedUp = new Perk();
        attackSpeedUp.setName("Attack Speed Up");
        attackSpeedUp.setFunc(new Runnable() {
            @Override
            public void run() {
                w.player.attackRegen += attackSpeedUp.getValue();
            }
        });
        perks.add(attackSpeedUp);
    }

}
