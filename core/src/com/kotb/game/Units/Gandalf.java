package com.kotb.game.Units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Gandalf extends PNJ {
    public Gandalf(Vector2 position) {
        super("Gandalf", 1000, 1000, 800f, new Texture(Gdx.files.internal("Player/pnjIdleRightSprite.png")), "Player/pnjIdleRightSprite.png", 0, 0, position);
    }
}
