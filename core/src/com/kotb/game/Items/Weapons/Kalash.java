package com.kotb.game.Items.Weapons;

import com.badlogic.gdx.math.Vector2;
import com.kotb.game.Items.Munitions.Bullet;
import com.kotb.game.Items.Munitions.Munition;
import com.kotb.game.Items.Munitions.Rocket;
import com.kotb.game.Items.Munitions.Swoosh;

public class Kalash extends Weapon{
    public Kalash() {
        super("Kalash", "Pan pan", 0.25f, 0.1f, false, new Bullet(), "Weapon/assault_rifle.png");
    }
    @Override
    public Munition createMunition(float startX, float startY, Vector2 direction) {
        return new Bullet(startX, startY, direction, 0.125f);
    }

}
