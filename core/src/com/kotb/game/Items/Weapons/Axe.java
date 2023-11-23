package com.kotb.game.Items.Weapons;

import com.badlogic.gdx.math.Vector2;
import com.kotb.game.Items.Munitions.Munition;
import com.kotb.game.Items.Munitions.Swoosh;

public class Axe extends Weapon{
    public Axe(){
        super("axe", "removes 1 heart per move", 4, 1.5f, true, new Swoosh(), "Weapon/weapon_double_axe.png");
    }

    @Override
    public Munition createMunition(float startX, float startY, Vector2 direction) {
        return new Swoosh(startX, startY, direction, 4);
    }
}
