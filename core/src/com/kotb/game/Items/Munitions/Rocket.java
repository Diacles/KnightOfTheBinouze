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

public class Rocket extends Munition{
        public Rocket(float startX, float startY, Vector2 direction, int damages) {
            super("Rocket", 800f, startX, startY, direction.x, direction.y, new Texture(Gdx.files.internal("Munition/rocketSprite.png")), "Munition/rocketSprite.png", 0f, damages);
        }
        public Rocket() {
            super("Rocket", 800f, 0, 0, 0, 0,new Texture(Gdx.files.internal("Munition/rocketSprite.png")), "Munition/rocketSprite.png", 0f, 0);
        }
    }