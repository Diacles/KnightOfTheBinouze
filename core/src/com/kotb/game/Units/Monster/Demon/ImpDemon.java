package com.kotb.game.Units.Monster.Demon;

import com.badlogic.gdx.math.Vector2;
import com.kotb.game.Global;
import com.kotb.game.Units.Monster.Monster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.kotb.game.Units.Monster.Other.Slime;


public class ImpDemon extends Monster {
    public ImpDemon(Vector2 position, Vector2 targetPosition) {
        super("ImpDemon", 4, 4, 150f, new Texture(Gdx.files.internal("Monster/Demon/Imp/impIdleRightSprite.png")), "Monster/Demon/Imp/impIdleRightSprite.png", 0,0,position, 2,  1, targetPosition);

    }

    @Override
    public Monster createMonster(float startX, float startY) {
        return new ImpDemon(new Vector2(startX, startY), new Vector2(Global.getPlayer().getPosition().x, Global.getPlayer().getPosition().y));

    }
}

