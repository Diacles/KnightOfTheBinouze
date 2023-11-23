package com.kotb.game.Units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.kotb.game.Global;
import com.kotb.game.Units.Monster.Monster;

public abstract class Unit implements Fighter {
    //#region ATTRIBUTES
    //Gameplay
    protected String name;
    protected int hp;
    protected int maxHP;
    protected float speed;
    protected boolean alive;
    //Animation
    protected static final int FRAME_COLS = 4, FRAME_ROWS = 1;// Constant rows and columns of the sprite sheet
    protected Texture unitSheet;
    protected String unitFile;
    protected Animation<TextureRegion> unitAnimation; // Must declare frame type (TextureRegion)
    protected float stateTime;
    protected int saveDirection;
    protected int width;
    protected int height;
    //Movement
    protected Vector2 position;
    protected Vector2 velocity;
    //#endregion

    //#region CONSTRUCTORS
    public Unit(String name, int hp, int maxHP, float speed, Texture unitSheet, String unitFile, float stateTime, int saveDirection, Vector2 position) {
        this.name = name;
        this.hp = hp;
        this.maxHP = maxHP;
        this.speed = speed;
        this.alive = true;
        this.unitSheet = unitSheet;
        this.unitFile = unitFile;
        this.stateTime = stateTime;
        this.saveDirection = saveDirection;
        this.position = position;
        this.velocity = new Vector2(0f, 0f);
        this.width = unitSheet.getWidth() / FRAME_COLS;
        this.height = unitSheet.getHeight() / FRAME_ROWS;
        recreateAnimation();
    }
    //#endregion

    //#region GETTERS AND SETTERS
    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public boolean isAlive() {
        alive = hp > 0;
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Texture getUnitSheet() {
        return unitSheet;
    }

    public void setUnitSheet(Texture unitSheet) {
        this.unitSheet = unitSheet;
    }

    public String getUnitFile() {
        return unitFile;
    }

    public void setUnitFile(String unitFile) {
        this.unitFile = unitFile;
    }

    public Animation<TextureRegion> getUnitAnimation() {
        return unitAnimation;
    }

    public void setUnitAnimation(Animation<TextureRegion> unitAnimation) {
        this.unitAnimation = unitAnimation;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public int getSaveDirection() {
        return saveDirection;
    }

    public void setSaveDirection(int saveDirection) {
        this.saveDirection = saveDirection;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }
    //#endregion

    //#region METHODS
    public void receiveDamage ( int damages){
        this.hp = this.hp - damages;
        if (this.hp < 0) {
            this.hp = 0;
        }
    }
    public void receiveDamage (float damages){
        this.hp = (int) (this.hp - damages);
        if (this.hp < 0) {
            this.hp = 0;
        }
    }
    public void update(float deltaTime) {
        TiledMapTileLayer dungeonLayer = Global.getDungeonLayer();
        //HITS A WALL
        if(dungeonLayer.getCell(Math.round((position.x + velocity.x * deltaTime) / 64), Math.round((position.y + velocity.y * deltaTime) / 64)).getTile().getProperties().containsKey("blocked")) {
            velocity.set(0, 0);
        }
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
    }
    public void render(SpriteBatch batch) {
        batch.draw(unitAnimation.getKeyFrame(stateTime, true), position.x, position.y, width, height);
    }
    public void draw(Batch batch) {
        TextureRegion frame = unitAnimation.getKeyFrame(stateTime, true);
        batch.draw(frame, position.x, position.y);
    }
    public void recreateAnimation() {
        this.unitSheet = new Texture(Gdx.files.internal(this.unitFile));

        TextureRegion[][] tmp = TextureRegion.split(this.unitSheet,
                this.unitSheet.getWidth() / FRAME_COLS,
                this.unitSheet.getHeight() / FRAME_ROWS);

        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }

        //Animation
        // Must declare frame type (TextureRegion)
        this.unitAnimation = new Animation<TextureRegion>(0.175f, walkFrames);
    }
    //#endregion
}