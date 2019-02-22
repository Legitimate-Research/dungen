package com.cd;

import com.cd.tile.Tile;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by PlanetCookieX on 5/20/2017.
 */
public class Util {

    public static final Random rand = new Random();

    public static String readFile(String path) {
        return readFile(new File(path));
    }

    public static String readFile(File file) {
        StringBuilder out = new StringBuilder();
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        while (sc.hasNextLine()) {
            String str = sc.nextLine();
            out.append(str).append("\n");
        }
        return out.toString();
    }

    public static <E> E random(List<E> list) {
        if (list.size() == 0) {
            throw new IllegalArgumentException("list contains zero elements!");
        }
        return list.get(rand.nextInt(list.size()));
    }

    public static Tile loadTileFromJson(String type, int x, int y) {
        File file = new File("res/tiles", type + ".json");

        JSONObject json;
        try {
            json = (JSONObject) new JSONParser().parse(new FileReader(file));
        } catch (IOException | ParseException e) {
            System.err.println("Cannot find file: " + file.getPath());
            e.printStackTrace();
            return null;
        }

        Class<? extends Tile> clazz = Tile.class;
        if (json.containsKey("class")) {
            try {
                //noinspection unchecked
                clazz = (Class<? extends Tile>) Class.forName(((String) json.get("class")));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        // try to construct using a static build() method or a constructor.

        try {
            Method method = clazz.getDeclaredMethod("build", int.class, int.class, JSONObject.class);
            return (Tile) method.invoke(null, x, y, json);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            try {
                Constructor<? extends Tile> constructor = clazz.getConstructor(int.class, int.class, JSONObject.class);
                return constructor.newInstance(x, y, json);
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e1) {
                e.printStackTrace();
                e1.printStackTrace();
                return null;
            }
        }
    }

}
