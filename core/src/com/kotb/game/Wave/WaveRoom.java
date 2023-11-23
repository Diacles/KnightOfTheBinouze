package com.kotb.game.Wave;

public class WaveRoom extends Room {
    //#region ATTRIBUTES
    private final int difficulty;

    private Wave wave;
    private boolean generated;
    //#endregion

    //#region CONSTRUCTORS
    public WaveRoom(int x, int y, int width, int height, int difficulty) {
        //Call parent constructor
        super(false, x, y, width, height);
        //Increment difficulty.
        this.difficulty = difficulty;
        //Create waves
        this.wave = null;
        this.generated = false;
    }

    public WaveRoom(int x, int y, int width, int height) {
        //Call parent constructor
        super(false, x, y, width, height);
        //Increment difficulty.
        this.difficulty = 0;
        //Create waves
        this.wave = null;
    }
    //#endregion

    //#region GET SET
    @Override
    public Wave getWave() {
        return wave;
    }
    public int getDifficulty() {
        return difficulty;
    }
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

    public void setGenerated(boolean generated) {
        this.generated = generated;
    }
    //#endregion

    //#region METHODS
    @Override
    public void startWave() {
        if (wave == null) {
            return;
        }
        wave.start();
    }
    public void generateWave(int difficulty) {
        // Create new wave
        wave = new Wave();
        // Add monster to wave according to the room's difficulty.
        wave.generateWave(difficulty, x, y, width, height);
    }

    @Override
    public void generateWave() {
        return;
    }
    //#endregion
}