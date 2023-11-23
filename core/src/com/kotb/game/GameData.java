package com.kotb.game;

import java.util.ArrayList;
import java.util.List;

public class GameData {
    private int playerLife;
    private int playerPositionX;
    private int playerPositionY;
    private List<String> collectedItems;
    private List<String> collectedArtifacts;

    public GameData() {
        // Initialisez les valeurs par défaut ou chargez-les depuis la sauvegarde précédente
        playerLife = 100;
        playerPositionX = 0;
        playerPositionY = 0;
        collectedItems = new ArrayList<>();
        collectedArtifacts = new ArrayList<>();
    }

    // Ajoutez les getters et setters nécessaires

    public int getPlayerLife() {
        return playerLife;
    }

    public void setPlayerLife(int playerLife) {
        this.playerLife = playerLife;
    }

    public int getPlayerPositionX() {
        return playerPositionX;
    }

    public void setPlayerPositionX(int playerPositionX) {
        this.playerPositionX = playerPositionX;
    }

    public int getPlayerPositionY() {
        return playerPositionY;
    }

    public void setPlayerPositionY(int playerPositionY) {
        this.playerPositionY = playerPositionY;
    }

    public List<String> getCollectedItems() {
        return collectedItems;
    }

    public void setCollectedItems(List<String> collectedItems) {
        this.collectedItems = collectedItems;
    }

    public List<String> getCollectedArtifacts() {
        return collectedArtifacts;
    }

    public void setCollectedArtifacts(List<String> collectedArtifacts) {
        this.collectedArtifacts = collectedArtifacts;
    }
}