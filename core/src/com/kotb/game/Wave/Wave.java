package com.kotb.game.Wave;
import com.badlogic.gdx.math.Vector2;
import com.kotb.game.Global;
import com.kotb.game.Screen.GameScreen;
import com.kotb.game.Units.Monster.*;
import com.kotb.game.Units.Monster.Demon.BigDemon;
import com.kotb.game.Units.Monster.Demon.ChortDemon;
import com.kotb.game.Units.Monster.Orc.Goblin;
import com.kotb.game.Units.Monster.Orc.Ogre;
import com.kotb.game.Units.Monster.Orc.OrcWarrior;
import com.kotb.game.Units.Monster.Other.*;
import com.badlogic.gdx.utils.Array;
import com.kotb.game.Units.Monster.Zombie.BigZombie;
import com.kotb.game.Units.Monster.Zombie.TinyZombie;
import com.kotb.game.Units.Monster.Zombie.Zombie;

import java.util.Random;

import static com.kotb.game.Global.*;

public class Wave {
    //#region ATTRIBUTES
    private Array<Monster> monsters;
    private boolean done;
    private boolean started;
    private int nbRoom;

    //#endregion

    //#region CONSTRUCTORS
    public Wave() {
        this.monsters = new Array<>();
        this.done = false;
        this.started = false;
    }
    //#endregion

    //#region GET SET
    public Array<Monster> getMonsters() {
        return this.monsters;
    }
    public void setMonsters(Array<Monster> monsterList) {
        this.monsters = monsterList;
    }
    public boolean isDone() {
        done = monsters.isEmpty();
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
    //#endregion

    //#region METHODS

    //Add a number of monster based on the difficulty. 
    //A difficulty of 10 should add 10 monsters. 
    public void generateWave(int aDifficulty, int x, int y, int width, int height) {
        Random random = new Random();
        float monsterX, monsterY;

        Array<Monster> spawner = new Array<>();
        if (aDifficulty <= 2) {
            // Ajouter le spawner faible
            spawner=Global.getSpawner().get(0);
        } else if (aDifficulty > 2 && aDifficulty <= 5) {
            // Ajouter le spawner moyen
            spawner=Global.getSpawner().get(1);
        } else {
            // Ajouter le spawner difficile
            spawner=Global.getSpawner().get(2);
        }

        for(int i = 0; i < aDifficulty; i++) {
            monsterX = (random.nextInt(width) + x) * 64;
            monsterY = (random.nextInt(height) + y) * 64;
           monsters.add(spawner.random().createMonster(monsterX,monsterY));
        }
    }
    public void generateWave(int x, int y, int width, int height) {
        Random random = new Random();
        float monsterX, monsterY;
        new Array<>();
        Array<Monster> spawner;
        spawner=Global.getSpawner().get(3);

        monsterX = (random.nextInt(width) + x) * 64;
        monsterY = (random.nextInt(height) + y) * 64;

        monsters.add(spawner.random().createMonster(monsterX,monsterY));
    }

    public void start() {
        for(Monster monster : monsters) {
            monster.draw(GameScreen.getRenderer().getBatch());
        }
    }
    //#endregion
}