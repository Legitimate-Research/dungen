package com.cd;

/**
 * Created by PlanetCookieX on 5/20/2017.
 */
public class RoomTemplate {
    private int weight;
    private String roomRef;

    public RoomTemplate(int weight, String roomRef) {
        this.weight = weight;
        this.roomRef = roomRef;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getRoomRef() {
        return this.roomRef;
    }

    public void setRoomRef(String roomRef) {
        this.roomRef = roomRef;
    }
}
