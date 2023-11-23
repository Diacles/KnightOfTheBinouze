package com.kotb.game.Items.Munitions;

import com.badlogic.gdx.Gdx;
import com.kotb.game.Global;
import com.kotb.game.Units.Monster.Monster;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public abstract class Munition {
    //#region ATTRIBUTES
    protected String name;
    protected float speed;
    private Vector2 position;
    private Vector2 velocity;
    protected int width;
    protected int height;
    //Animation
    protected static final int FRAME_COLS = 4, FRAME_ROWS = 1;// Constant rows and columns of the sprite sheet
    protected Texture munitionSheet;
    protected String munitionFile;
    protected Animation<TextureRegion> munitionAnimation; // Must declare frame type (TextureRegion)
    protected float stateTime;
    protected float damages;
    protected float distanceTraveled = 0f;
    protected float maxDistance = 500f; // Mettez la distance maximale souhaitée ici

    //#endregion

    //#region CONSTRUCTORS
    public Munition(String name, float speed, float startX, float startY, float targetX, float targetY, Texture munitionSheet, String munitionFile, float stateTime, float damages) {
        this.name = name;
        this.speed = speed;
        this.position = new Vector2(startX, startY);
        this.velocity = new Vector2(targetX, targetY).nor().scl(speed);
        this.munitionSheet = munitionSheet;
        this.munitionFile = munitionFile;
        this.stateTime = stateTime;
        this.damages = damages;

        // Use the split utility method to create a 2D array of TextureRegions. This is
        // possible because this sprite sheet contains frames of equal size and they are
        // all aligned.
        width = this.munitionSheet.getWidth() / FRAME_COLS;
        height = this.munitionSheet.getHeight() / FRAME_ROWS;

        TextureRegion[][] textureRegion = TextureRegion.split(this.munitionSheet,
                this.munitionSheet.getWidth() / FRAME_COLS,
                this.munitionSheet.getHeight() / FRAME_ROWS);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                frames[index++] = textureRegion[i][j];
            }
        }

        munitionAnimation = new Animation<TextureRegion>(0.175f, frames);
    }
    //#endregion

    public void update(float deltaTime) {
        TiledMapTileLayer dungeonLayer = Global.getDungeonLayer();
        // Mettez à jour la distance parcourue
        distanceTraveled += velocity.len() * deltaTime;

        // Vérifiez si la munition a dépassé la distance maximale
        if (distanceTraveled >= maxDistance) {
            Global.getMunitions().removeValue(this, false);
            return;
        }
        //HITS A WALL
        if(dungeonLayer.getCell(Math.round((position.x + velocity.x * deltaTime) / 64), Math.round((position.y + velocity.y * deltaTime) / 64)).getTile().getProperties().containsKey("blocked")) {
            Global.getMunitions().removeValue(this, false);
            return;
        }
        //HITS A MONSTER
        for (Monster monster : Global.getMonsters()) {
            if (position.x + velocity.x * deltaTime < monster.getPosition().x + 32 &&
                    position.x + velocity.x * deltaTime + width > monster.getPosition().x &&
                    position.y + velocity.y * deltaTime + height > monster.getPosition().y &&
                    position.y + velocity.y * deltaTime < monster.getPosition().y + 32 )
            {
                Global.getMunitions().removeValue(this, false);
                monster.receiveDamage(damages);
                return;
            }
        }
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
    }

    public void draw(Batch batch) {
        TextureRegion frame = munitionAnimation.getKeyFrame(stateTime, true);
        float drawX = position.x;
        float drawY = position.y;
        float drawWidth = width;
        float drawHeight = height;

        // Vérifiez la direction horizontale et verticale
        if (velocity.x < 0) {
            // Si la munition se déplace vers la gauche, utilisez l'échelle en miroir horizontal
            drawX = position.x + width;
            drawWidth = -width;
        }

        if (velocity.y > 0) {
            // Si la munition se déplace vers le haut, ajustez la texture en conséquence
            drawY = position.y + height;
            drawHeight = -height;
        } else if (velocity.y < 0) {
            // Si la munition se déplace vers le bas, ajustez la texture en conséquence
            drawHeight = height;
        }

        // Dessinez la munition avec les paramètres calculés
        batch.draw(frame, drawX, drawY, drawWidth, drawHeight);
    }
    //#endregion

    //#region GETTERS AND SETTERS
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public String getMunitionFile() {
        return munitionFile;
    }

    public void setMunitionFile(String munitionFile) {
        this.munitionFile = munitionFile;
    }

    public Texture getMunitionSheet() {
        return munitionSheet;
    }

    public void setMunitionSheet(Texture munitionSheet) {
        this.munitionSheet = munitionSheet;
    }

    public Animation<TextureRegion> getMunitionAnimation() {
        return munitionAnimation;
    }

    public void setMunitionAnimation(Animation<TextureRegion> munitionAnimation) {
        this.munitionAnimation = munitionAnimation;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }
    //#endregion
}
