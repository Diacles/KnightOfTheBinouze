package com.kotb.game.Units.Monster.Orc;
import com.badlogic.gdx.maps.MapLayers;

import com.badlogic.gdx.math.Vector2;
import com.kotb.game.Global;
import com.kotb.game.Units.Monster.Monster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.kotb.game.Units.*;
import com.kotb.game.Units.Monster.Other.Slime;

public class OrcWarrior extends Monster {
    public OrcWarrior(Vector2 position, Vector2 targetPosition) {
        super("OrcWarrior", 10, 10, 100f, new Texture(Gdx.files.internal("Monster/Orc/OrcWarrior/orcWarriorIdleRightSprite.png")), "Monster/Orc/OrcWarrior/orcWarriorIdleRightSprite.png", 0,0,position, 2,  1, targetPosition);
    }

    @Override
    public Monster createMonster(float startX, float startY) {
        return new OrcWarrior(new Vector2(startX, startY), new Vector2(Global.getPlayer().getPosition().x, Global.getPlayer().getPosition().y));
    }
}

