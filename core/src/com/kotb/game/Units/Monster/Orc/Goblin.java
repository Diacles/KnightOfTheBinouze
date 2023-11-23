package com.kotb.game.Units.Monster.Orc;
import com.badlogic.gdx.maps.MapLayers;

import com.kotb.game.Global;
import com.kotb.game.Units.Monster.Monster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.kotb.game.Units.Monster.Other.Slime;

public class Goblin extends Monster {
    //Default Constructor
    public Goblin(Vector2 position, Vector2 targetPosition) {
        super("Goblin", 4, 4, 150f, new Texture(Gdx.files.internal("Monster/Orc/Goblin/goblinIdleRightSprite.png")), "Monster/Orc/Goblin/goblinIdleRightSprite.png", 0,0,position, 2,  1, targetPosition);
    }

    @Override
    public Monster createMonster(float startX, float startY) {
        return new Goblin(new Vector2(startX, startY), new Vector2(Global.getPlayer().getPosition().x, Global.getPlayer().getPosition().y));

    }
}