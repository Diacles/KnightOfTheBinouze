package com.kotb.game.Units.Monster.Orc;
import com.badlogic.gdx.maps.MapLayers;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.kotb.game.Global;
import com.kotb.game.Items.Munitions.Bullet;
import com.kotb.game.Items.Munitions.Swoosh;
import com.kotb.game.Units.Monster.Monster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.kotb.game.Units.*;
import com.kotb.game.Units.Monster.Other.Slime;

public class Ogre extends Monster {
    private float shootTimer;
    private float shootInterval = 1f; // Intervalle entre chaque tir en secondes

    public Ogre(Vector2 position, Vector2 targetPosition) {
        super("Ogre", 30, 30, 350f, new Texture(Gdx.files.internal("Monster/Orc/Ogre/ogreIdleRightSprite.png")), "Monster/Orc/Ogre/ogreIdleRightSprite.png", 0,0,position, 3,  1, targetPosition);
    }

    @Override
    public Monster createMonster(float startX, float startY) {
        return new Ogre(new Vector2(startX, startY), new Vector2(Global.getPlayer().getPosition().x, Global.getPlayer().getPosition().y));
    }
    @Override
    public void pattern(float deltaTime) {
        super.pattern(deltaTime);

        // Mettez à jour le chronomètre de tir
        shootTimer += deltaTime;

        // Si le chronomètre atteint l'intervalle de tir, effectuez le tir
        if (shootTimer >= shootInterval) {
            shoot();
            shootTimer = 0; // Réinitialisez le chronomètre après chaque tir
        }
    }

    private void shoot() {
        // Créez et ajoutez des balles dans toutes les directions
        for (float angle = 0; angle < 360; angle += 45) {
            float radians = (float) Math.toRadians(angle);
            float bulletSpeed = 1000f; // Vitesse de la balle

            // Créez une balle avec la direction en fonction de l'angle
            //Bullet bullet = new Bullet(getPosition().x, getPosition().y, new Vector2(MathUtils.cos(radians) * bulletSpeed, MathUtils.sin(radians) * bulletSpeed), 1);

            // Ajoutez la balle à la liste des balles (assurez-vous d'avoir une liste dans votre jeu pour les balles)
            //Global.getMunitions().add(bullet);
        }
    }

}

