package com.kotb.game.Items.Weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.kotb.game.Global;
import com.kotb.game.Items.Munitions.Munition;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.kotb.game.Items.Item;
import com.kotb.game.Screen.GameScreen;

public abstract class Weapon extends Item {

    // attribut
    protected float damage;
    protected float rate;
    protected boolean melee;
    protected Munition ammo;
    protected GameScreen gameScreen;
    protected Sprite weaponSprite;
    protected float timeSinceLastShot = 0f;


    // Constructeur
    public Weapon(String name, String description, float damage, float rate, boolean melee, Munition ammo, String texturePath) {
        super(name, description, texturePath);
        this.damage = damage;
        this.rate = rate;
        this.melee = melee;
        this.ammo = ammo;

        // Initialise le sprite avec la texture spécifiée
        this.weaponSprite = new Sprite(new Texture(texturePath));
    }

    // GET et SET
    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public boolean isMelee() {
        return melee;
    }

    public void setMelee(boolean melee) {
        this.melee = melee;
    }

    public Munition getAmmo() {
        return ammo;
    }

    public void setAmmo(Munition ammo) {
        this.ammo = ammo;
    }

    public Sprite getWeaponSprite() {
        return weaponSprite;
    }
    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    // methode
    public abstract Munition createMunition(float startX, float startY, Vector2 direction);

    public void shoot(float delta, float shooterX, float shooterY) {
        if (canShoot()) {
            float playerX = shooterX; // Player
            float playerY = shooterY;

            float targetX = Gdx.input.getX(); // Cursor
            float targetY = Gdx.input.getY();

            // Convertir les coordonnées d'écran en coordonnées de jeu
            Vector3 unprojected = Global.getCamera().unproject(new Vector3(targetX, targetY, 0));
            targetX = unprojected.x;
            targetY = unprojected.y;

            // Calculer la direction du tir
            Vector2 direction = new Vector2(targetX - playerX, targetY - playerY).nor();

            Munition bullet = createMunition(playerX, playerY, direction);
            Global.getMunitions().add(bullet); // Ajouter la munition à la liste des munitions en cours

            resetShotCooldown(); // Réinitialiser le temps écoulé depuis le dernier tir
        }

        // Mettre à jour le temps écoulé depuis le dernier tir
        timeSinceLastShot += delta;
    }

    public boolean canShoot() {
        return timeSinceLastShot >= rate;
    }

    public void resetShotCooldown() {
        timeSinceLastShot = 0f;
    }
}
