package com.kotb.game.Items.Weapons;

import com.badlogic.gdx.math.Vector2;

import com.kotb.game.Items.Munitions.Munition;
import com.kotb.game.Items.Munitions.Swoosh;

public class Sword extends Weapon {

    // constructor

    public Sword() {
        super("sword", "removes 1 half heart per move", 2, 0.5f, true, new Swoosh(), "Weapon/weapon_anime_sword.png");
    }

    @Override
    public Munition createMunition(float startX, float startY, Vector2 direction) {
        return new Swoosh(startX, startY, direction, 2);
    }
}
