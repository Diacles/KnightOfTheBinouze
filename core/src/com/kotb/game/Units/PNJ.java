package com.kotb.game.Units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.kotb.game.Items.Weapons.Weapon;

public abstract class PNJ extends Unit {
    //#region ATTRIBUTES
    private String dialogue;
    //#endregion

    //#region CONSTRUCTORS
    public PNJ(String name, int hp, int maxHP, float speed, Texture unitSheet, String unitFile, float stateTime, int saveDirection, Vector2 position) {
        super(name, hp, maxHP, speed, unitSheet, unitFile, stateTime, saveDirection, position);
    }
    //#endregion

    //#region GETTERS AND SETTERS
    public void setDialogue(String dialogue) {
        this.dialogue = dialogue;
    }
    public String getDialogue() {
        return dialogue;
    }
    //#endregion

    //#region METHODS
    @Override
    public boolean equip(Weapon weapon) {
        return false;
    }

    @Override
    public boolean attack(float delta) {
        return false;
    }

    @Override
    public boolean drop(Weapon weapon) {
        return false;
    }
    //#endregion

}

