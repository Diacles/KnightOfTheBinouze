package com.kotb.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class SaveManager {
    private static final String SAVE_FILE_PATH = "saveFile.json";

    public static void saveGame(GameData gameData) {
        Json json = new Json();
        String jsonData = json.toJson(gameData);

        // Sauvegarde des données dans le fichier
        FileHandle file = Gdx.files.local(SAVE_FILE_PATH);
        file.writeString(jsonData, false);
    }

    public static GameData loadGame() {
        GameData gameData = new GameData();

        // Charge les données à partir du fichier
        FileHandle file = Gdx.files.local(SAVE_FILE_PATH);
        if (file.exists()) {
            String jsonData = file.readString();
            Json json = new Json();
            gameData = json.fromJson(GameData.class, jsonData);
        }

        return gameData;
    }
}
