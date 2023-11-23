package com.kotb.game.Units.Monster.Other;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.math.Vector2;
import com.kotb.game.Global;
import com.kotb.game.Units.Monster.Monster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Doc extends Monster {
    public Doc(Vector2 position, Vector2 targetPosition) {
        super("Doc", 4, 4, 100f, new Texture(Gdx.files.internal("Monster/Other/Doc/docIdleRightSprite.png")), "Monster/Other/Doc/docIdleRightSprite.png", 0,0,position, 1,  1f, targetPosition);
    }

    @Override
    public Monster createMonster(float startX, float startY) {
        return new Doc(new Vector2(startX, startY), new Vector2(Global.getPlayer().getPosition().x, Global.getPlayer().getPosition().y));
    }
}

