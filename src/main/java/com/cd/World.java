package com.cd;

import com.cd.entity.Entity;
import com.cd.entity.Player;
import com.cd.entity.enemy.BossEnemy;
import com.cd.entity.enemy.MeleeEnemy;
import com.cd.entity.enemy.ShootingEnemy;
import com.cd.math.Vec2;
import com.cd.tile.Tile;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by William on 5/20/2017.
 */
public class World {
    private static class RoomSwitchContext {
        public int x, y;
        public Direction from;

        public RoomSwitchContext(int x, int y, Direction from) {
            this.x = x;
            this.y = y;
            this.from = from;
        }
    }


    public static int WIDTH = 480;
    public static int HEIGHT = 288;

    public int level;

    public ArrayList<RoomTemplate> templates = new ArrayList<>();

    public Table<Room> rooms = new Table<>();
    private int roomX, roomY;
    public Player player;

    private RoomSwitchContext roomSwitchContext;

    public World(Player player, int level) {
        this.player = player;
        this.level = level;


        genTemplates();

        Room first = genRoom(templates.get(0), false);
        first.addExits(true, true, true, true);
        first.getEntities().add(player);
        rooms.put(0, 0, first);

    }

    public void renderRoom(Graphics2D g, int x, int y) {
        Room room = rooms.get(x, y);
        if (room != null) {
            room.render(this, g);
        }
    }
    public void render(Graphics2D g) {
        g.setColor(Color.gray);
        g.drawRect(0, 0, WIDTH, HEIGHT);

        if (Launch.isKeyPressed(KeyEvent.VK_P)) {
            AffineTransform transform = g.getTransform();

            g.translate(WIDTH / 2d, HEIGHT / 2d);
            g.scale(0.2, 0.2);
            g.translate(WIDTH / -2d, HEIGHT / -2d);

            for (int yi = -5; yi <= 5; yi++) {
                for (int xi = -5; xi <= 5; xi++) {
                    AffineTransform t = g.getTransform();
                    g.translate(xi * WIDTH, yi * HEIGHT);
                    renderRoom(g, roomX + xi, roomY + yi);
                    g.setTransform(t);
                }
            }


            for (IRenderable entity : getEntities()) {
                entity.render(this, g);
            }


            g.setTransform(transform);
        }else {
            renderRoom(g, roomX, roomY);

            for (IRenderable entity : getEntities()) {
                entity.render(this, g);
            }
        }

        g.setColor(Color.green);
        g.drawString("(" + roomX + ", " + roomY + ")", 50, 50);

    }

    public void update(float d) {

        if (Launch.justPressed(KeyEvent.VK_UP)) switchToRoom(Direction.UP);
        else if (Launch.justPressed(KeyEvent.VK_DOWN)) switchToRoom(Direction.DOWN);
        else if (Launch.justPressed(KeyEvent.VK_LEFT)) switchToRoom(Direction.LEFT);
        else if (Launch.justPressed(KeyEvent.VK_RIGHT)) switchToRoom(Direction.RIGHT);

        Room room = rooms.get(roomX, roomY);
        if (room != null) {
            room.update(this, d);
        }

        for (IRenderable entity : new ArrayList<>(getEntities())) {
            entity.update(this, d);
        }

        if (roomSwitchContext != null) {
            doSwitchToRoom(roomSwitchContext.x, roomSwitchContext.y, roomSwitchContext.from);
            roomSwitchContext = null;
        }

    }

    public void goToBoss(){
        level = 2;
        genTemplates();
        RoomTemplate rt = detRoom(1337, 1337);
        Room r = genRoom(rt, true);
        rooms.put(1337, 1337, r);
        switchToRoom(1337, 1337, Direction.UP);
        //getEntities().add(new BossEnemy(new Vec2(WIDTH/2-24, 24)));
    }

    public void switchToRoom(Direction toDir) {
        switchToRoom(roomX + toDir.x, roomY + toDir.y, toDir.opposite());
    }

    public void switchToRoom(int x, int y, Direction from) {
        roomSwitchContext = new RoomSwitchContext(x, y, from);
    }

    private void doSwitchToRoom(int x, int y, Direction from) {
        if (!rooms.exists(x, y)) {
            long start = System.currentTimeMillis();

            RoomTemplate nextTemplate = detRoom(x, y);
            Room nextRoom = genRoom(nextTemplate, x == 1337 && y == 1337);
            genExits(x, y, nextRoom);
            rooms.put(x, y, nextRoom);

            System.out.println("Time: " + (System.currentTimeMillis() - start));
        }

        if (!rooms.exists(x, y)) {
            throw new IllegalStateException("addRoom() did not add room at (" + x + ", " + y + ")");
        }

        roomX = x;
        roomY = y;

        if (!currentRoom().getEntities().contains(player)) {
            currentRoom().getEntities().add(player);
        }

        switch (from) {
            case UP:
                player.setPosition(player.getPosition().withY(0.5));
                break;
            case DOWN:
                player.setPosition(player.getPosition().withY(8.5));
                break;
            case LEFT:
                player.setPosition(player.getPosition().withX(0.5));
                break;
            case RIGHT:
                player.setPosition(player.getPosition().withX(14.5));
                break;
        }

    }

    public void genExits(int x, int y, Room r){
        Room[] adjRooms = new Room[4];
        adjRooms[0] = rooms.get(x, y - 1);
        adjRooms[1] = rooms.get(x, y + 1);
        adjRooms[2] = rooms.get(x - 1, y);
        adjRooms[3] = rooms.get(x + 1, y);

        boolean up = (adjRooms[0] == null?Math.random() > .5:adjRooms[0].isHasDown());
        boolean down = (adjRooms[1] == null?Math.random() > .5:adjRooms[1].isHasUp());
        boolean left = (adjRooms[2] == null?Math.random() > .5:adjRooms[2].isHasRight());
        boolean right = (adjRooms[3] == null?Math.random() > .5:adjRooms[3].isHasLeft());

        r.addExits(up, down, left, right);
    }

    public RoomTemplate detRoom(int x, int y) {
        if(x == 1337 && y == 1337)
            return templates.get(0);
        int totalWeight = 0;

        for(RoomTemplate t: templates){
            totalWeight += t.getWeight();
        }

        int rNum = ((int)(Math.random()*totalWeight));

        for(RoomTemplate t: templates){
            if(rNum < t.getWeight()){
                return t;
            } else{
                rNum -= t.getWeight();
            }
        }
        return null;
    }

    public Room genRoom(RoomTemplate temp, boolean isBossRoom) {
        Room room = new Room(temp);

        File roomsFolder = new File("res/Rooms/World" + level + "/");
        File roomFolder = new File(roomsFolder, temp.getRoomRef());
        File tileFile = new File(roomFolder, "tiles.txt");
        String fullContentsAsString = Util.readFile(tileFile);
        String[] fullContentsAsArray = fullContentsAsString.split("-");

        // REFS
        Map<String, String> refs;
        refs = genRefs(fullContentsAsArray[0]);
        // TILES
        String tilesAsString = fullContentsAsArray[1].trim();
        String[] tilesAsRowsArray = tilesAsString.split("\n");
        for (int cY = 0; cY < tilesAsRowsArray.length; cY++) {
            String[] tilesAsColsArray = tilesAsRowsArray[cY].split(" ");
            for (int cX = 0; cX < tilesAsColsArray.length; cX++) {
                room.addTile(cX, cY, Util.loadTileFromJson(refs.get(tilesAsColsArray[cX]), cX, cY));
            }
        }

        //ENEMIES

        if (isBossRoom) {
            room.getEntities().add(new BossEnemy(new Vec2(15 / 2.0, 9 / 2.0)));
        }else {
            for (int y = 0; y < 9; y++) {
                for (int x = 0; x < 15; x++) {
                    Tile tile = room.getTiles()[x][y];

                    if (!tile.isCollidable() && Util.rand.nextInt(50) == 0) {
                        room.getEntities().add(
                                Math.random() < 0.5 ? new MeleeEnemy(new Vec2(x + 0.5, y + 0.5)) :
                                        new ShootingEnemy(new Vec2(x + 0.5, y + 0.5)));
                    }

                }
            }
        }

        return room;
    }

    public Map<String, String> genRefs(String s) {
        Map<String, String> out = new HashMap<>();
        String[] refsAsStrings = s.split("\n");
        for (String refAsString : refsAsStrings) {
            String[] refAsArray = refAsString.split(":");
            out.put(refAsArray[0], refAsArray[1]);
        }
        return out;

    }

    public void genTemplates() {
        File folder = new File("res/Rooms/World" + level + "/");
        File[] listOfFiles = folder.listFiles();
        for (File f : listOfFiles) {
            File attr = new File(f, "attr.txt");
            String[] attrs = Util.readFile(attr).split("\n");
            templates.add(new RoomTemplate(Integer.parseInt(attrs[0]), attrs[1]));
        }
    }

    public void dropStack(PerkStack stack) {
        throw new UnsupportedOperationException();
    }

    public Room currentRoom() {
        return rooms.get(roomX, roomY);
    }

    public ArrayList<Entity> getEntities() {
        return currentRoom().getEntities();
    }

    public int getRoomX() {
        return roomX;
    }

    public int getRoomY() {
        return roomY;
    }
}
