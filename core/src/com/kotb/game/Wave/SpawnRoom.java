package com.kotb.game.Wave;

public class SpawnRoom extends Room {
    //#region CONSTRUCTORS
    public SpawnRoom(int x, int y) {
        super(false, x, y, "maps/room-spawn.tmx");
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public boolean isGenerated() {
        return false;
    }

    @Override
    public void generateWave(int difficulty) {
        return;
    }

    @Override
    public void generateWave() {
        return;
    }
    //#endregion

    //#region GET SET
    //#endregion

    //#region METHODS
    //#endregion
}