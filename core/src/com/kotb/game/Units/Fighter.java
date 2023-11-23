package com.kotb.game.Units;

import com.kotb.game.Items.Weapons.Weapon;

public interface Fighter {
    
    public boolean equip(Weapon weapon);
    public boolean attack(float delta);
    public boolean drop(Weapon weapon);
    public void receiveDamage(int damages);
    public String getName();
    public int getHp();
    public float getSpeed();



}
