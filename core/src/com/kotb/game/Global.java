package com.kotb.game;

import com.badlogic.gdx.math.Vector2;
import com.kotb.game.Items.Artifacts.*;
import com.kotb.game.Items.Item;
import com.kotb.game.Items.Weapons.*;
import com.kotb.game.Screen.GameScreen;
import com.kotb.game.Units.Monster.*;
import com.kotb.game.Units.Monster.Demon.BigDemon;
import com.kotb.game.Units.Monster.Demon.ChortDemon;
import com.kotb.game.Units.Monster.Demon.ImpDemon;
import com.kotb.game.Units.Monster.Orc.Goblin;
import com.kotb.game.Units.Monster.Orc.Ogre;
import com.kotb.game.Units.Monster.Orc.OrcWarrior;
import com.kotb.game.Units.Monster.Other.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.kotb.game.Units.Monster.Monster;
import com.kotb.game.Items.Munitions.Munition;
import com.kotb.game.Units.Monster.Zombie.BigZombie;
import com.kotb.game.Units.Monster.Zombie.TinyZombie;
import com.kotb.game.Units.Monster.Zombie.Zombie;
import com.kotb.game.Wave.Wave;
import com.kotb.game.Units.Player;


public abstract class Global {
    private static Array<Munition> munitions = new Array<>();
    private static Array<Monster> monsters = new Array<>();
    private static Array<Monster> pnjs = new Array<>();
    private static Array<Item> loot = new Array<>();
    private static Array<Item> items = new Array<>();
    private static Player player = null;
    private static OrthographicCamera camera = new OrthographicCamera();
    private static TiledMapTileLayer dungeonLayer = null;
    private static Array<Array<Monster>> spawner = new Array<>();
    private static Array<Monster> weakSpawner = new Array<>();
    private static Array<Monster> midSpawner = new Array<>();
    private static Array<Monster> hardSpawner = new Array<>();
    private static Array<Monster> bossSpawner = new Array<>();private static Vector2 position ;
    private static float monsterX;
    private static float monsterY ;
    private static Vector2 targetPosition;
    public static void generateSpawner(){
        weakSpawner.add(
                new Slime(position,targetPosition),
                new Doc(position,targetPosition)
        );

        midSpawner.add(
                new ImpDemon(position, targetPosition),
                new TinyZombie(position, targetPosition),
                new Goblin(position, targetPosition)
        );

        hardSpawner.add(
                new ChortDemon(position, targetPosition),
                new Zombie(position, targetPosition),
                new OrcWarrior(position, targetPosition)
        );

        bossSpawner.add(
                new BigDemon(position, targetPosition),
                new BigZombie(position, targetPosition),
                new Ogre(position, targetPosition)
        );
        spawner.addAll(weakSpawner);
        spawner.addAll(midSpawner);
        spawner.addAll(hardSpawner);
        spawner.addAll(bossSpawner);
    }
    public static void generateLoot() {
        loot.add(new Axe());
        //loot.add(new Coussin_Peteur());
        loot.add(new Kronenbourg());
        loot.add(new Lance_Roquette());
        loot.add(new Kalash());
        //loot.add(new Heart());
        //loot.add(new Barbaria_Chest());
        //loot.add(new Devil_Hand());
        loot.add(new Hermes_shoes());
        //loot.add(new Lol_Player_Salt());
        //loot.add(new VampiricHand());
        //loot.add(new Warrior_Mask());
    }
    public static Array<Munition> getMunitions() {
        return munitions;
    }
    public static void setMunitions(Array<Munition> munitions) {
        Global.munitions = munitions;
    }
    public static Array<Monster> getMonsters() {
        return monsters;
    }
    public static void setMonsters(Array<Monster> monsters) {
        Global.monsters = monsters;
    }
    public static Array<Monster> getPnjs() {
        return pnjs;
    }
    public static void setPnjs(Array<Monster> pnjs) {
        Global.pnjs = pnjs;
    }
    public static Player getPlayer() {
        return player;
    }
    public static void setPlayer(Player player) {
        Global.player = player;
    }
    public static OrthographicCamera getCamera() {
        return camera;
    }
    public static void setCamera(OrthographicCamera camera) {
        Global.camera = camera;
    }
    public static TiledMapTileLayer getDungeonLayer() {
        return dungeonLayer;
    }
    public static void setDungeonLayer(TiledMapTileLayer dungeonLayer) {
        Global.dungeonLayer = dungeonLayer;
    }
    public static Array<Array<Monster>> getSpawner() {
        return spawner;
    }

    public static void setSpawner(Array<Array<Monster>> spawner) {
        Global.spawner = spawner;
    }

    public static Array<Monster> getWeakSpawner() {
        return weakSpawner;
    }

    public static void setWeakSpawner(Array<Monster> weakSpawner) {
        Global.weakSpawner = weakSpawner;
    }

    public static Array<Monster> getMidSpawner() {
        return midSpawner;
    }

    public static void setMidSpawner(Array<Monster> midSpawner) {
        Global.midSpawner = midSpawner;
    }

    public static Array<Monster> getHardSpawner() {
        return hardSpawner;
    }

    public static void setHardSpawner(Array<Monster> hardSpawner) {
        Global.hardSpawner = hardSpawner;
    }

    public static Array<Monster> getBossSpawner() {
        return bossSpawner;
    }

    public static void setBossSpawner(Array<Monster> bossSpawner) {
        Global.bossSpawner = bossSpawner;
    }

    public static void setMonsterX(float monsterX) {
        Global.monsterX = monsterX;
    }

    public static void setMonsterY(float monsterY) {
        Global.monsterY = monsterY;
    }

    public static Array<Item> getLoot() {
        return loot;
    }

    public static void setLoot(Array<Item> loot) {
        Global.loot = loot;
    }

    public static Array<Item> getItems() {
        return items;
    }

    public static void setItems(Array<Item> items) {
        Global.items = items;
    }
}
