package com.kotb.game.Items.Weapons;

import com.badlogic.gdx.math.Vector2;
import com.kotb.game.Items.Munitions.Munition;
import com.kotb.game.Items.Munitions.Rocket;
import com.kotb.game.Items.Munitions.Swoosh;

public class Lance_Roquette extends Weapon{

    public Lance_Roquette(){
        super("Lance roquette", "One shot", 7, 2, false, new Swoosh(), "Weapon/rpg.png");
    }

    @Override
    public Munition createMunition(float startX, float startY, Vector2 direction) {
        return new Rocket(startX, startY, direction, 2);
    }
}
