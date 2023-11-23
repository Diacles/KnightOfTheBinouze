package com.kotb.game.Units.Monster.Demon;
import com.badlogic.gdx.maps.MapLayers;

import com.badlogic.gdx.math.Vector2;
import com.kotb.game.Global;
import com.kotb.game.Units.Monster.Monster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.kotb.game.Units.Monster.Other.Slime;

public class ChortDemon extends Monster {
    public ChortDemon(Vector2 position, Vector2 targetPosition) {
        super("ChortDemon", 10, 10, 100f, new Texture(Gdx.files.internal("Monster/Demon/ChortDemon/chortDemonIdleRightSprite.png")), "Monster/Demon/ChortDemon/chortDemonIdleRightSprite.png", 0,0,position, 2,  1, targetPosition);

    }

    @Override
    public Monster createMonster(float startX, float startY) {
        return new ChortDemon(new Vector2(startX, startY), new Vector2(Global.getPlayer().getPosition().x, Global.getPlayer().getPosition().y));

    }
}

