package com.kotb.game.Units.Monster.Zombie;

import com.kotb.game.Global;
import com.kotb.game.Units.Monster.Monster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.kotb.game.Units.Monster.Other.Slime;

public class Zombie extends Monster {
    public Zombie(Vector2 position, Vector2 targetPosition) {
        super("Zombie", 10, 10, 1, new Texture(Gdx.files.internal("Monster/Zombie/Zombie/zombieIdleRightSprite.png")), "Monster/Zombie/Zombie/zombieIdleRightSprite.png", 0,0,position, 2,  1, targetPosition);
    }

    @Override
    public Monster createMonster(float startX, float startY) {
        return new Zombie(new Vector2(startX, startY), new Vector2(Global.getPlayer().getPosition().x, Global.getPlayer().getPosition().y));
    }
}

