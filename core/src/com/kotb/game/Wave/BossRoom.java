package com.kotb.game.Wave;

public class BossRoom extends Room {
    //#region ATTRIBUTES
    private Wave wave;
    private boolean generated;

    //#endregion

    //#region CONSTRUCTORS
    public BossRoom(int x, int y) {
        super(false, x, y, "maps/room-boss.tmx");
        this.wave = null;
        this.generated = false;
    }
    //#endregion

    //#region GET SET
    @Override
    public Wave getWave() {
        return wave;
    }
    //#endregion

    @Override
    public boolean isDone() {
        done = wave.isDone();
        return done;
    }
    public boolean isGenerated() {
        if(wave == null) {
            this.generated = false;
        } else {
            this.generated = true;
        }
        return this.generated;
    }

    //#region METHODS
    @Override
    public void startWave() {
        if (wave == null) {
            return;
        }
        wave.start();
    }
    public void generateWave() {
        // Create new wave
        wave = new Wave();
        // Add monster to wave according to the room's difficulty.
        wave.generateWave(x, y, width, height);
    }
    @Override
    public void generateWave(int difficulty) {
        // Create new wave
        wave = new Wave();
        // Add monster to wave according to the room's difficulty.
        wave.generateWave(x, y, width, height);    }
    //#endregion
}