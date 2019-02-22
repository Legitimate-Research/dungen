package com.cd;

import java.util.HashMap;

/**
 * Created by PlanetCookieX on 5/20/2017.
 */
public class Table<E> {

    private static class Location {
        public final int x, y;

        public Location(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Location location = (Location) o;
            return x == location.x && y == location.y;
        }

        @Override
        public int hashCode() {
            return x * 100_005 + y;
        }
    }

    private HashMap<Location, E> map = new HashMap<>();

    public void put(int x, int y, E item) {
        map.put(new Location(x, y), item);
    }

    public boolean exists(int x, int y) {
        return get(x, y) != null;
    }

    public E get(int x, int y) {
        return map.get(new Location(x, y));
    }

    public int size() {
        return map.size();
    }

}
