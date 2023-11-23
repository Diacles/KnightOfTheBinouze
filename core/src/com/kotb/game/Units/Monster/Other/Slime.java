package com.kotb.game.Units.Monster.Other;

import com.badlogic.gdx.math.Vector2;
import com.kotb.game.Global;
import com.kotb.game.Units.Monster.Monster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Slime extends Monster {
    public Slime(Vector2 position, Vector2 targetPosition) {
        super("Slime", 4, 4, 100f, new Texture(Gdx.files.internal("Monster/Other/Slime/slimeIdleRightSprite.png")), "Monster/Other/Slime/slimeIdleRightSprite.png", 0,0,position, 1,  1f, targetPosition);
    }

    @Override
    public Monster createMonster(float startX, float startY) {
        return new Slime(new Vector2(startX, startY), new Vector2(Global.getPlayer().getPosition().x, Global.getPlayer().getPosition().y));
    }
}

