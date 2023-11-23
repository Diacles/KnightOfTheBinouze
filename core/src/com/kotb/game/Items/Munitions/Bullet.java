package com.kotb.game.Items.Munitions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends Munition{
    public Bullet(float startX, float startY, Vector2 direction, float damages) {
        super("Bullet", 1000f, startX, startY, direction.x, direction.y, new Texture(Gdx.files.internal("Munition/bulletSprite.png")), "Munition/bulletSprite.png", 0f, damages);
    }
    public Bullet() {
        super("Bullet", 1000f, 0, 0, 0, 0,new Texture(Gdx.files.internal("Munition/bulletSprite.png")), "Munition/bulletSprite.png", 0f, 0.25f);
    }
}