package com.kotb.game.Units.Monster;
import com.badlogic.gdx.math.Vector2;
import com.kotb.game.Items.Item;
import com.kotb.game.Items.Weapons.Weapon;
import com.kotb.game.Units.Unit;
import com.badlogic.gdx.graphics.Texture;
import com.kotb.game.Global;

import java.util.Random;

public abstract class Monster extends Unit {
    //#region ATTRIBUTES
    protected int damage;
    protected float rate;
    protected Vector2 targetPosition;
    protected float timeSinceLastHit = 0f;

    //#endregion

    //#region CONSTRUCTORS
    public Monster(String name, int hp, int maxHP, float speed, Texture unitSheet, String unitFile, float stateTime, int saveDirection, Vector2 position, int damage, float rate, Vector2 targetPosition) {
        super(name, hp, maxHP, speed, unitSheet, unitFile, stateTime, saveDirection, position);
        this.damage = damage;
        this.rate = rate;
        this.targetPosition = targetPosition;
    }
    //#endregion

    //#region GETTERS AND SETTERS
    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public Vector2 getTargetPosition() {
        return targetPosition;
    }

    public void setTargetPosition(Vector2 targetPosition) {
        this.targetPosition = targetPosition;
    }

    public float getTimeSinceLastHit() {
        return timeSinceLastHit;
    }

    public void setTimeSinceLastHit(float timeSinceLastHit) {
        this.timeSinceLastHit = timeSinceLastHit;
    }

    //#endregion

    //#region METHODS
    public void pattern(float deltaTime) {
        if (Global.getPlayer() != null) {
            targetPosition = Global.getPlayer().getPosition();
            Vector2 direction = targetPosition.cpy().sub(position).nor(); // Calculate normalized direction vector
            velocity.set(direction.x * speed, direction.y * speed);
        }
    }
    @Override
    public void update(float deltaTime) {
        pattern(deltaTime);
        //HITS A PLAYER
        if (position.x + velocity.x * deltaTime < Global.getPlayer().getPosition().x + width &&
                position.x + velocity.x * deltaTime + width > Global.getPlayer().getPosition().x &&
                position.y + velocity.y * deltaTime + height > Global.getPlayer().getPosition().y &&
                position.y + velocity.y * deltaTime < Global.getPlayer().getPosition().y + height )
        {
            if(canHit()) {
                Global.getPlayer().receiveDamage(damage);
                resetHitCooldown();
            }
            timeSinceLastHit += deltaTime;
            velocity.set(0,0);
        }
        super.update(deltaTime);
    }
    public boolean canHit() {
        return timeSinceLastHit >= rate;
    }
    public void resetHitCooldown() {
        timeSinceLastHit = 0f;
    }
    public void loot() {
        Random random = new Random();
        int probability = random.nextInt(100); // Génère un nombre aléatoire entre 0 et 99 inclus

        // Si la probabilité est inférieure ou égale à 5, alors le monstre loot
        if (probability <= 33 && Global.getLoot() != null) {
            Item loot = Global.getLoot().random();
            if (loot != null) {
                loot.setPosition(position);
                //Add the looted item to the list
                Global.getItems().add(loot);
                //Delete the looted item from the list, so we drop it only once
                Global.getLoot().removeValue(loot, true);
            }
        }
    }

    public abstract Monster createMonster(float startX, float startY);
    @Override
    public boolean attack(float delta) {
        return false;
    }

    @Override
    public boolean equip(Weapon weapon) {
        return false;
    }

    @Override
    public boolean drop(Weapon weapon) {
        return false;
    }
    //#endregion
}
