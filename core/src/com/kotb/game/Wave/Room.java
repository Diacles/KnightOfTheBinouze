package com.kotb.game.Wave;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public abstract class Room {
    protected int x;
    protected int y;
    protected TiledMap room;
    protected int width;
    protected int height;
    protected boolean done;
    protected boolean locked;

    public Room(boolean locked, int x, int y, String fileName) {
        this.x = x;
        this.y = y;
        this.room = new TmxMapLoader().load(fileName);
        this.width = (int) room.getProperties().get("width");
        this.height = (int) room.getProperties().get("height");
        this.done = false;
        this.locked = locked;
    }
    public Room(boolean locked, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.done = false;
        this.locked = locked;
    }


    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public TiledMap getMap() {
        return room;
    }
    public void setMap(TiledMap room) {
        this.room = room;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public abstract boolean isDone();
    public void setDone(boolean done) { this.done = done; }
    public boolean isLocked() { return this.locked; }
    public void setLocked(boolean locked) { this.locked = locked; }
    public abstract boolean isGenerated();


    // return true if this room intersects provided room
    public boolean intersects(Room room) {
        return this.x -1 < room.getX() + room.getWidth() &&
                this.x + width + 1  > room.getX() &&
                this.y - 1 < room.getY() + getHeight() &&
                this.y + height + 1 > room.getY();
    }
    public void startWave() {}
    public Wave getWave() {
        return null;
    };
    public abstract void generateWave(int difficulty);
    public abstract void generateWave();

    }