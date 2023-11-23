package com.kotb.game.Units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.kotb.game.Global;
import com.kotb.game.Items.Artifacts.*;
import com.kotb.game.Items.Item;
import com.kotb.game.Items.Weapons.*;

public class Player extends Unit {
    //#region ATTRIBUTES
    //Inventory
    private Array<Weapon> weapons;
    private Array<Artifact> artifacts;
    private Weapon weaponEquipped;
    private boolean inventoryOpen;
    private Item itemClose;
    // Définir une variable pour suivre le temps écoulé depuis le dernier ajout d'arme
    private float timeSinceSwitchWeapon = 0f;
    private static final float WEAPON_SWITCH_COOLDOWN = 1f; // Délai en secondes entre chaque ajout d'arme

    //#endregion

    //#region CONSTRUCTORS
    public Player(Vector2 position) {
        super("Bernard", 6, 6, 600f, new Texture(Gdx.files.internal("Player/knightidleleftsprite.png")), "Player/knightidleleftsprite.png", 0, 0, position);
        this.weapons = new Array<>();
        this.artifacts = new Array<>();
        this.weaponEquipped = null;
        this.inventoryOpen = false;
        pick(new Sword());
        equip(weapons.get(0));
    }
    //#endregion

    //#region GETTERS AND SETTERS
    public Array<Weapon> getWeapons() {
        return weapons;
    }
    public void setWeapons(Array<Weapon> weapons) {
        this.weapons = weapons;
    }
    public Array<Artifact> getArtifacts() {
        return artifacts;
    }
    public void setArtifacts(Array<Artifact> artifacts) {
        this.artifacts = artifacts;
    }
    public Weapon getWeaponEquipped() {
        return weaponEquipped;
    }
    public void setWeaponEquipped(Weapon weaponEquipped) {
        this.weaponEquipped = weaponEquipped;
    }
    public boolean isInventoryOpen() {
        return inventoryOpen;
    }
    public void setInventoryOpen(boolean inventoryOpen) {
        this.inventoryOpen = inventoryOpen;
    }
    public Array<Weapon> getAllWeapons() {
        Array<Weapon> allWeapons = new Array<>();
        if (weapons != null) {
            allWeapons.addAll(weapons);
        }
        if (weaponEquipped != null) {
            allWeapons.add(weaponEquipped);
        }
        return allWeapons;
    }
    //#endregion

    //#region METHODS
    //Handling player inputs
    public void action(float delta) {
        boolean right = false, left = false;
        timeSinceSwitchWeapon += delta;
        velocity.set(0, 0); // Reset velocity before processing input

        //COMBAT
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            attack(delta);
        }

        //ITEM
        if (Gdx.input.isKeyPressed(Input.Keys.E) && itemClose != null) {
            pick(itemClose);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && timeSinceSwitchWeapon >= WEAPON_SWITCH_COOLDOWN ) {
            switchWeapon();
            timeSinceSwitchWeapon = 0f; // Réinitialiser le temps écoulé
        }

        // MOVEMENT
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            unitFile =  "Player/knightrunrightsprite.png";
            right = true;
            left = false;
            saveDirection = 1;
            velocity.x = speed; // Move right
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            unitFile = "Player/knightrunleftsprite.png";
            right = false;
            left = true;
            saveDirection = 0;
            velocity.x = -speed; // Move left
        } else if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            if (right)
                unitFile = "Player/knightrunrightsprite.png";
            else
                unitFile = "Player/knightrunleftsprite.png";
            velocity.y = speed; // Move up
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            if (right)
                unitFile = "Player/knightrunrightsprite.png";
            else
                unitFile = "Player/knightrunleftsprite.png";
            velocity.y = -speed; // Move down
        } else {
            if (saveDirection == 1){
                unitFile = "Player/knightidlerightsprite.png";
            }else {
                unitFile = "Player/knightidleleftsprite.png";
            }
        }
        recreateAnimation();
    }
    @Override
    public void update(float deltaTime) {
        action(deltaTime); // Handle player input
        //HITS A MONSTER
        for (int i = 0; i < Global.getMonsters().size; i++) {
            if (position.x + velocity.x * deltaTime < Global.getMonsters().get(i).position.x + width &&
                    position.x + velocity.x * deltaTime + width > Global.getMonsters().get(i).position.x &&
                    position.y + velocity.y * deltaTime + height > Global.getMonsters().get(i).position.y &&
                    position.y + velocity.y * deltaTime < Global.getMonsters().get(i).position.y + height )
            {
                if(Global.getMonsters().get(i).canHit()) {
                    this.receiveDamage(Global.getMonsters().get(i).getDamage());
                    Global.getMonsters().get(i).resetHitCooldown();
                }
                Global.getMonsters().get(i).setTimeSinceLastHit(Global.getMonsters().get(i).getTimeSinceLastHit() + deltaTime);
                velocity.set(0,0);
            }
        }


        super.update(deltaTime);
    }
    public void loot() {

        if (Math.random() < 0.9){ System.out.println("no loot"); }
        //Weapons
        else if (Math.random() < 0.5) { addWeapon(new Sword()); System.out.println("Weapon Sword loot");}//50%
        else if (Math.random() < 0.4) { addWeapon(new Axe()); System.out.println("Weapon Axe loot"); }//40%
        else if (Math.random() < 0.3) { addWeapon(new Coussin_Peteur()); System.out.println("Weapon Coussin_Peteur loot"); }//30%
        else if (Math.random() < 0.2) { addWeapon(new Kronenbourg()); System.out.println("Weapon Kronenbourg loot"); }//20%
        else if (Math.random() < 0.1) { addWeapon(new Lance_Roquette()); System.out.println("Weapon Lance_Roquette loot"); }//10%
        //Artifacts
        else if (Math.random() < 0.5) { addArtifact(new Heart()); System.out.println("Artifact Heart loot"); }//50%
        else if (Math.random() < 0.1) { addArtifact(new Hermes_shoes());System.out.println("Artifact Hermes_shoes loot"); }//10%
    }
    private void addWeapon(Weapon weapon) {
        if (!weapons.contains(weapon, true) && weapons.size<10) {
            weapons.add(weapon);
            System.out.println(weapons);
        }
    }
    private void addArtifact(Artifact artifact) {
        if (!artifacts.contains(artifact, true)) {
            artifacts.add(artifact);
            System.out.println(artifacts);
        }
    }
    public boolean attack(float delta) {
        // CHECK ARTIFACTS
        if (artifacts != null) {
            for (Artifact artifact : artifacts) {
                // Vérifie si l'artefact a un effet sur l'attaque
                if (artifact.getEffectName().equals("AttackBoost")) {
                    // Applique l'effet de l'artefact
                    boolean effectActivated = artifact.applyEffect(this);

                    // Si l'effet de l'artefact est activé, augmenter les dégâts
                    if (effectActivated) {
                        if (weaponEquipped != null) {
                            // Ajoutez ici le code pour augmenter les dégâts, par exemple :
                            artifact.applyEffect(this);
                        }
                    }
                }
            }
        }

        // THEN ATTACK
        if (weaponEquipped != null) {
            weaponEquipped.shoot(delta, position.x, position.y);
            return true;
        } else {
            return false;
        }
    }
    public void toggleInventory() {
        // Si l'inventaire est ouvert, ferme-le ; sinon, ouvre-le
        if (this.isInventoryOpen()) {
            this.setInventoryOpen(false);
        } else {
            this.setInventoryOpen(true);
        }
    }
    public void receiveDamage(int damages) {
        // Vérifie si le joueur a des artefacts qui réduisent les dégâts
        if (artifacts != null) {
            for (Artifact artifact : artifacts) {
                // Vérifie si l'artefact a un effet sur la réception de dégâts
                if (artifact.getEffectName().equals("Protection")) {
                    // Applique l'effet de l'artefact
                    boolean effectActivated = artifact.applyEffect(this);

                    // Si l'effet de l'artefact est activé, ne pas appliquer les dégâts
                    if (effectActivated) {
                        return;
                    }
                }
            }
        }

        // Si aucun artefact n'a empêché les dégâts, applique les dégâts au joueur
        super.receiveDamage(damages);
    }
    public boolean equip(Weapon weapon){
        if (weapons.contains(weapon, true)){
            weaponEquipped = null;
            weaponEquipped = weapon;
            return true;
        } else{
            return false;
        }
    }
    public void pick(Item item) {
        if (item instanceof Weapon) {
            pick((Weapon) item);
        } else if (item instanceof Artifact) {
            pick((Artifact) item);
        }
    }


    public void pick(Weapon weapon) {
        // Add the new weapon to the inventory and set it as equipped
        if(!weapons.contains(weapon, true)) {
            weapons.add(weapon);
        }
        Global.getItems().removeValue(weapon, true);
    }
    public boolean pick(Artifact artifact) {
        if(!artifacts.contains(artifact, true)) {
            artifacts.add(artifact);
            artifact.applyEffect();
        }
        Global.getItems().removeValue(artifact, true);
        return true;
    }
    @Override
    public boolean drop(Weapon weapon) {
        if (this.weapons.contains(weapon, true)){
            this.weapons.removeValue(weapon, true);
            return true;
        }
        if(this.weaponEquipped==weapon){
            this.weaponEquipped=null;
        }
        return false;
    }

    public void setItemClose(Item item) {
        itemClose = item;
    }
    public void switchWeapon() {
        int index = weapons.indexOf(weaponEquipped, true) + 1;
        System.out.println(index);
        System.out.println(weapons.size);

        if(index >= weapons.size)
            index = 0;
        weaponEquipped = weapons.get(index);
    }

    //#endregion
}