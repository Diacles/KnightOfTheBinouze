package com.kotb.game.Items.Weapons;

import com.badlogic.gdx.math.Vector2;
import com.kotb.game.Items.Munitions.Munition;
import com.kotb.game.Items.Munitions.Swoosh;

public class Coussin_Peteur extends Weapon {
    public Coussin_Peteur() {
        super("coussin_peteur", "Gas removes half a heart per second from enemies 5 seconds left", 4, 2, false, new Swoosh(), "Weapon/weapon_rusty_sword.png");
    }

    @Override
    public Munition createMunition(float startX, float startY, Vector2 direction) {
        return null;
    }
}
