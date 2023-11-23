package com.kotb.game.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.kotb.game.Screen.GameScreen;

public abstract class Item {

    // attribut

    protected String name;
    protected String description;
    protected String texturePath;
    protected Vector2 position;
    // constructor

    public Item(String name, String description, String texturePath) {
        this.name = name;
        this.description = description;
        this.texturePath = texturePath;
        this.position = new Vector2(0, 0);
    }

    // GET et SET

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTexturePath() {
        return texturePath;
    }

    public void setTexturePath(String texturePath) {
        this.texturePath = texturePath;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void draw(Batch batch) {
        batch.draw(new Texture(texturePath), position.x, position.y);
    }
    public void draw(Batch batch, float x, float y, int width, int height) {
        batch.draw(new Texture(texturePath), x, y, width, height);
    }
}