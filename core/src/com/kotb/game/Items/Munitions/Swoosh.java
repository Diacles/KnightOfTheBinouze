package com.kotb.game.Items.Munitions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class Swoosh extends Munition {
    public Swoosh(float startX, float startY, Vector2 direction, int damages) {
        super("Swoosh", 200f, startX, startY, direction.x, direction.y, new Texture(Gdx.files.internal("Munition/swooshSprite.png")), "Munition/swooshSprite.png", 0f, damages);
    }
    public Swoosh() {
        super("Swoosh", 200f, 0, 0, 0, 0,new Texture(Gdx.files.internal("Munition/swooshSprite.png")), "Munition/swooshSprite.png", 0f, 0);
    }
}
