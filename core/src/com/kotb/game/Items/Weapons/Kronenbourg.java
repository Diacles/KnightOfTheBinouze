package com.kotb.game.Items.Weapons;

import com.badlogic.gdx.math.Vector2;
import com.kotb.game.Items.Munitions.Munition;
import com.kotb.game.Items.Munitions.Swoosh;

public class Kronenbourg extends  Weapon{

    public Kronenbourg(){
        super("kronenbourg", "removes 1 heart per move", 1, 0.25f, true, new Swoosh(), "Weapon/weapon_knife.png");
    }

    @Override
    public Munition createMunition(float startX, float startY, Vector2 direction) {
        return new Swoosh(startX, startY, direction, 1);
    }
}
