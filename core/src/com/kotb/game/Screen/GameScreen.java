package com.kotb.game.Screen;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.kotb.game.Global;
import com.kotb.game.Items.Item;
import com.kotb.game.Items.Weapons.Weapon;
import com.kotb.game.Units.Gandalf;
import com.kotb.game.Wave.BossRoom;
import com.kotb.game.Wave.Room;
import com.kotb.game.Wave.SpawnRoom;
import com.kotb.game.Wave.WaveRoom;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.ScreenUtils;
import static com.badlogic.gdx.math.MathUtils.random;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import com.kotb.game.KnightOfTheBinouze;
import com.kotb.game.Units.Player;
import com.kotb.game.Units.Monster.Monster;
import com.kotb.game.Units.PNJ;
import com.kotb.game.Items.Munitions.Munition;


public class GameScreen implements Screen {
    //#region ATTRIBUTES
    private final KnightOfTheBinouze game;
    private final TiledMap map;
    private final TiledMap tiledMap;
    private final TiledMapTileSet tileSet;
    private static OrthogonalTiledMapRenderer renderer = null;
    private final OrthographicCamera camera;
    private final Player player;
    private final Map<Integer, TiledMapTile> floorTiles;
    private final Map<Integer, TiledMapTile> wallTiles;
    private final int mapWidth;
    private final int mapHeight;
    private static TiledMapTileLayer dungeonLayer;
    private final Array<Point> doors;

    private final PNJ gandalf;
    private boolean displayDialogue;
    private final Array<Room> rooms;
    private final Array<Monster> monsters;
    private final Array<Item> items;
    private Room currentRoom;

    private Texture heartFull;
    private Texture heartHalf;
    private Texture heartEmpty;
    private Music dungeonMusic;
    private Sound InventoryClose;
    private Sound InventoryOpen;
    private Sound ItemDrop;
    private Sound ItemEquip;
    private Sound playerDamage;
    private Sound swordSlash;



    private int difficulty;
    //#endregion

    //#region CONSTRUCTORS
    public GameScreen(KnightOfTheBinouze game) {
        this.game = game;
        this.map = new TiledMap();
        this.tiledMap = new TmxMapLoader().load("maps/tileset.tmx");
        this.tileSet = tiledMap.getTileSets().getTileSet("Tiles");
        this.camera = Global.getCamera();

        floorTiles = new HashMap<>();
        wallTiles = new HashMap<>();
        doors = new Array<>();
        monsters = Global.getMonsters();
        heartFull = new Texture(Gdx.files.internal("ui_heart_full.png"));
        heartHalf = new Texture(Gdx.files.internal("ui_heart_half.png"));
        heartEmpty = new Texture(Gdx.files.internal("ui_heart_empty.png"));

        dungeonMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/dungeonMusic.mp3"));
        dungeonMusic.setLooping(true);
        dungeonMusic.play();
        InventoryClose = Gdx.audio.newSound(Gdx.files.internal("Music/InventoryClose.mp3"));
        InventoryOpen = Gdx.audio.newSound(Gdx.files.internal("Music/InventoryOpen.mp3"));
        ItemDrop = Gdx.audio.newSound(Gdx.files.internal("Music/ItemDrop.mp3"));
        ItemEquip = Gdx.audio.newSound(Gdx.files.internal("Music/ItemEquip.mp3"));
        playerDamage = Gdx.audio.newSound(Gdx.files.internal("Music/playerDamage.mp3"));
        swordSlash = Gdx.audio.newSound(Gdx.files.internal("Music/swordSlash.mp3"));

        Global.generateSpawner();
        Global.generateLoot();
        items = Global.getItems();



            for (TiledMapTile tile : tileSet) {
            Object floorProperty = tile.getProperties().get("floor");
            Object topWallProperty = tile.getProperties().get("top-wall");

            if (floorProperty != null) {
                floorTiles.put((Integer) floorProperty, tile);
            }

            if (topWallProperty != null) {
                wallTiles.put((Integer) topWallProperty, tile);
            }
        }

        Random random = new Random();
        //#region MAP GENERATION
        rooms = new Array<>();
        //The map will be minimum 80 (height/width) and can go up to 150.
        this.mapWidth = random.nextInt(70)+80;
        this.mapHeight = random.nextInt(70)+80;
        this.difficulty = 1;

        //FILL LAYER WITH WALLS
        Global.setDungeonLayer(new TiledMapTileLayer(mapWidth, mapHeight, 64, 64));
        MapLayers layers = map.getLayers();
        dungeonLayer = Global.getDungeonLayer();
        dungeonLayer.setParallaxX(1f);
        dungeonLayer.setParallaxY(1f);
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                int bound = wallTiles.size();
                int index = random.nextInt(bound);
                if (index == 0)
                    index++;
                TiledMapTile blockedTile = wallTiles.get(index);

                TiledMapTileLayer.Cell blockedCell = new TiledMapTileLayer.Cell();
                blockedCell.setTile(blockedTile);

                dungeonLayer.setCell(x, y, blockedCell);
            }
        }

        //#region SPAWN ROOM
        //Add spawn room to array.
        int spawnX, spawnY;
        boolean spawnIntersects;
        do {
            // Generate random coordinates for the spawn room
            spawnX = random.nextInt(mapWidth-mapWidth/4)+5;
            spawnY = random.nextInt(mapWidth-mapWidth/4)+5;

            // Check if the spawn room intersects with any existing rooms
            spawnIntersects = false;
            for (Room existingRoom : rooms) {
                if (existingRoom.intersects(new SpawnRoom(spawnX, spawnY))) {
                    spawnIntersects = true;
                }
            }
            if (spawnX + mapWidth/4 > mapWidth || spawnY + mapWidth/4 > mapHeight) {
                spawnIntersects = true;
            }
        } while (spawnIntersects);
        // Create and add the spawn room to the list
        SpawnRoom spawnRoom = new SpawnRoom(spawnX, spawnY);
        rooms.add(spawnRoom);
        //#endregion

        //#region WAVE ROOMS
        //Add 5 to 10 wave rooms to array.
        int difficulty = 0;
        for (int i = 0; i < random.nextInt(5)+5; i++) {
            boolean intersects;
            int x, y, width, height;
            do {
                // Generate random coordinates
                x = random.nextInt(mapWidth-mapWidth/4)+5;
                y = random.nextInt(mapHeight-mapHeight/4)+5;
                width = random.nextInt(8)+8;
                height = random.nextInt(8)+8;

                // Check if the new room intersects with any existing rooms
                intersects = false;
                for (Room existingRoom : rooms) {
                    //If intersect with a room or
                    if (existingRoom.intersects(new WaveRoom(x, y, width, height))) {
                        intersects = true;
                    }
                }
                //map limits
                if (x + width > mapWidth || y + height > mapHeight) {
                    intersects = true;
                }
            } while (intersects);

            // Create and add the room to the list
            difficulty++;
            WaveRoom room = new WaveRoom(x, y, width, height, difficulty);
            rooms.add(room);
        }
        //#endregion

        //#region BOSS ROOM
        int bossX, bossY;
        boolean bossIntersects;
        do {
            // Generate random coordinates for the boss room
            bossX = random.nextInt(mapWidth-mapWidth/4)+5;
            bossY = random.nextInt(mapWidth-mapWidth/4)+5;

            // Check if the boss room intersects with any existing rooms
            bossIntersects = false;
            for (Room existingRoom : rooms) {
                if (existingRoom.intersects(new BossRoom(bossX, bossY))) {
                    bossIntersects = true;
                    break;
                }
                if (bossX + 16 > mapWidth || bossY + 16 > mapHeight) {
                    bossIntersects = true;
                    break;
                }
            }
        } while (bossIntersects);
        // Create and add the boss room to the list
        BossRoom bossRoom = new BossRoom(bossX, bossY);
        rooms.add(bossRoom);
        //#endregion

        //Now that every room are created and added to the array.
        //Let's browse through the array and add the rooms to the map.
        for (Room room : rooms) {
            for (int x = room.getX(); x < room.getX() + room.getWidth(); x++) {
                for (int y = room.getY(); y < room.getY() + room.getHeight(); y++) {
                    int bound = wallTiles.size();
                    int index = random.nextInt(bound);
                    if (index == 0)
                        index++;
                    TiledMapTile floorTile = floorTiles.get(index);

                    TiledMapTileLayer.Cell floorCell = new TiledMapTileLayer.Cell();
                    floorCell.setTile(floorTile);

                    dungeonLayer.setCell(x, y, floorCell);
                }
            }

            if (rooms.size > 1) {
                // store center of the previous room
                Room prevRoom = rooms.get(rooms.size - 1);
                Point prevCenter = new Point(prevRoom.getX() + prevRoom.getWidth() / 2, prevRoom.getY() + prevRoom.getHeight() / 2);
                Point newCenter = new Point(room.getX() + room.getWidth() / 2, room.getY() + room.getHeight() / 2);
                // carve out corridors between rooms based on centers
                // randomly start with horizontal or vertical corridors
                if (Math.random() < 0.5) {
                    hCorridor(prevCenter.x, newCenter.x, prevCenter.y);
                    vCorridor(prevCenter.y, newCenter.y, newCenter.x);
                } else {
                    vCorridor(prevCenter.y, newCenter.y, prevCenter.x);
                    hCorridor(prevCenter.x, newCenter.x, newCenter.y);
                }
            }
        }
        layers.add(dungeonLayer);
        renderer = new OrthogonalTiledMapRenderer(map);
        this.currentRoom = null;
        //#endregion

        Global.setPlayer(new Player(new Vector2((spawnRoom.getX() + (float) spawnRoom.getWidth() / 2) * 64 , (spawnRoom.getY() + (float) spawnRoom.getHeight() / 2) * 64)));
        this.player = Global.getPlayer();
        this.gandalf = new Gandalf(new Vector2((spawnRoom.getX() + (float) spawnRoom.getWidth() / 2) * 64, (spawnRoom.getY() + (float) spawnRoom.getHeight() / 2) * 64 ));

        this.displayDialogue = false;

        this.heartFull = new Texture(Gdx.files.internal("ui_heart_full.png"));
        this.heartHalf = new Texture(Gdx.files.internal("ui_heart_half.png"));
        this.heartEmpty = new Texture(Gdx.files.internal("ui_heart_empty.png"));

        //#region CURSOR
        Pixmap pixmap = new Pixmap(Gdx.files.internal("Utility/cursor64x64.png"));
        int xHotspot = 32, yHotspot = 32;
        Cursor cursor = Gdx.graphics.newCursor(pixmap, xHotspot, yHotspot);
        Gdx.graphics.setCursor(cursor);
        pixmap.dispose(); // We don't need the pixmap anymore
        //#endregion

        //#region INVENTORY
        Sprite inventorySprite = new Sprite(new Texture(Gdx.files.internal("inventory.png")));
        inventorySprite.setPosition(Gdx.graphics.getWidth() / 2f - inventorySprite.getWidth() / 2f, Gdx.graphics.getHeight() / 2f - inventorySprite.getHeight() / 2f);
        //#endregion
    }

    //#endregion

    //#region METHODS
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.1450980392156863f, 0.07450980392156863f, 0.10196078431372549f, 1);

        currentRoom = getCurrentRoom(player);
        // Set the camera's position to the player's position
        camera.position.x = player.getPosition().x;
        camera.position.y = player.getPosition().y;

        TextureRegion WeaponFrame = player.getWeaponEquipped().getWeaponSprite();

        // Check if the player is next to the PNJ
        float distance = calculateDistance(player.getPosition().x, player.getPosition().y, gandalf.getPosition().x, gandalf.getPosition().y);
        if (distance < 100) { // Adjust the threshold as needed
            // Set the dialogue for the PNJ
            gandalf.setDialogue("Hello! Welcome to the Beergeon.");

            // Display the dialogue on the screen
            displayDialogue = true;
        } else {
            // Reset the dialogue when the player is not close to the PNJ
            displayDialogue = false;
        }

        // Gère l'ouverture/fermeture de l'inventaire
        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            player.toggleInventory();
        }

        camera.update();
        renderer.setView(camera);
        renderer.render();

        //START DRAWING
        renderer.getBatch().begin();
        if(player.getSaveDirection()==0){
            //Left
            renderer.getBatch().draw(WeaponFrame, player.getPosition().x-3, player.getPosition().y+20,32,64);
        } else {
            //Right
            renderer.getBatch().draw(WeaponFrame, player.getPosition().x+45, player.getPosition().y+20,32,64);
        }
        player.getWeaponEquipped().draw(renderer.getBatch(), player.getPosition().x+500, player.getPosition().y-300,96,192);
        //#region BULLETS DRAWING / UPDATING
        if (Global.getMunitions() != null) {
            // update bullets
            for (Munition munition : Global.getMunitions()) {
                munition.setStateTime(munition.getStateTime() + delta);
                munition.update(Gdx.graphics.getDeltaTime());
            }

            // draw bullets
            for (Munition munition : Global.getMunitions()) {
                swordSlash.play();
                munition.draw(renderer.getBatch());

            }
        }
        //#endregion

        //#region MONSTERS DRAWING / UPDATING
        if(monsters != null) {
            for (Monster monster : monsters) {
                if(!monster.isAlive() && currentRoom != null) {
                    monster.loot();
                    ItemDrop.play();
                    currentRoom.getWave().getMonsters().removeValue(monster, true);
                    monsters.removeValue(monster, true);
                }

                monster.setStateTime(monster.getStateTime() + delta);
                monster.update(delta);
                monster.draw(renderer.getBatch());
            }
        }
        //#endregion

        //#region ITEMS DRAWING / UPDATING
        if(items != null) {
            boolean close;
            float itemDistance;
            for (Item item : items) {
                item.draw(renderer.getBatch());
                itemDistance = calculateDistance(player.getPosition().x, player.getPosition().y, item.getPosition().x, item.getPosition().y);
                if (itemDistance < 100) { // Adjust the threshold as needed
                    close = true;
                } else {
                    close = false;
                }
                if(close) {
                    ItemEquip.play();
                    player.setItemClose(item);
                    game.getFont().draw(renderer.getBatch(), "E", item.getPosition().x, item.getPosition().y + 50);
                } else {
                    player.setItemClose(null);
                }
            }
        }
        //#endregion

        //PLAYER UPDATING / DRAWING
        player.setStateTime(player.getStateTime() + delta);
        player.update(delta);
        player.draw(renderer.getBatch());

        //GANDALF UPDATING / DRAWING
        gandalf.setStateTime(gandalf.getStateTime() + delta);
        gandalf.update(delta);
        gandalf.draw(renderer.getBatch());

        //#region WAVE HANDLING
        // If the player is in a wave or boss room
        if(currentRoom != null && currentRoom.getClass() != SpawnRoom.class) {
            if(!currentRoom.isGenerated()) {
                currentRoom.generateWave(difficulty);
            }
            //Check if the room isn't done (meaning the wave of monsters associated to the room isn't finished).
            //And isn't already locked.
            if(!currentRoom.isDone() && !currentRoom.isLocked()) {
                lock(currentRoom);
                currentRoom.setLocked(true);
                currentRoom.startWave();
                for (Monster monster : currentRoom.getWave().getMonsters()) {
                    monsters.add(monster);
                }
            }
            //If the room is done, unlock it.
            else if(currentRoom.isDone() && currentRoom.isLocked()) {
                if(currentRoom instanceof BossRoom) {
                    game.setScreen(new WinScreen(game));
                } else {
                    unlock(currentRoom);
                    difficulty++;
                    currentRoom.setLocked(false);
                }
            }
        }
        //#endregion

        // Display the dialogue if set
        if (displayDialogue) {
            // Customize this logic to fit your game's UI
            // For simplicity, this example just draws the dialogue at a fixed position
            game.getFont().draw(renderer.getBatch(), gandalf.getDialogue(), gandalf.getPosition().x - 60, gandalf.getPosition().y + 100);
            game.getFont().draw(renderer.getBatch(), gandalf.getDialogue(), gandalf.getPosition().x - 60, gandalf.getPosition().y + 100);
        }

        // Dessine le sprite de l'inventaire à la position de la caméra
        if (player.isInventoryOpen()) {
            Sprite inventorySprite = new Sprite(new Texture(Gdx.files.internal("inventory.png")));
            float inventoryX = camera.position.x - inventorySprite.getWidth() / 2f;
            float inventoryY = camera.position.y - inventorySprite.getHeight() / 2f;
            inventorySprite.setPosition(inventoryX, inventoryY);
            inventorySprite.draw(renderer.getBatch());


            // Dessine les sprites des armes du joueur
            float offsetX = 170;
            float weaponX = inventoryX + offsetX;
            float offsetY = 450;
            float weaponY = inventoryY + offsetY;


            for (Weapon weapon : player.getAllWeapons()) {
                // Dessine chaque arme à sa position dans le menu
                float itemWidth = 30F;
                float itemHeight = 50F;

                renderer.getBatch().draw(weapon.getWeaponSprite(), weaponX, weaponY, itemWidth, itemHeight);

                // Ajuste les positions pour la prochaine arme
                float itemSpacingX = 100;
                float itemSpacingY = 100;

                weaponX += itemSpacingX;
                if (weaponX > inventoryX + inventorySprite.getWidth() - itemWidth) {
                    weaponX = inventoryX + offsetX;
                    weaponY -= itemSpacingY;
                }
            }
        }

        // HEALTH BAR
        int hp = player.getHp();
        float heartSize = 60f;
        float heartSpacing = 5f;
        float heartsX = camera.position.x - camera.viewportWidth / 2 + 10f;
        float heartsY = camera.position.y + camera.viewportHeight / 2 - heartSize - 10f;

        switch (hp) {
            case 6:
                renderer.getBatch().draw(heartFull, heartsX, heartsY, heartSize, heartSize);
                heartsX += heartSize + heartSpacing;
                renderer.getBatch().draw(heartFull, heartsX, heartsY, heartSize, heartSize);
                heartsX += heartSize + heartSpacing;
                renderer.getBatch().draw(heartFull, heartsX, heartsY, heartSize, heartSize);
                heartsX += heartSize + heartSpacing;
                break;
            case 5:
                renderer.getBatch().draw(heartFull, heartsX, heartsY, heartSize, heartSize);
                heartsX += heartSize + heartSpacing;
                renderer.getBatch().draw(heartFull, heartsX, heartsY, heartSize, heartSize);
                heartsX += heartSize + heartSpacing;
                renderer.getBatch().draw(heartHalf, heartsX, heartsY, heartSize, heartSize);
                heartsX += heartSize + heartSpacing;
                break;
            // pas de break ici pour passer au cas suivant
            case 4:
                renderer.getBatch().draw(heartFull, heartsX, heartsY, heartSize, heartSize);
                heartsX += heartSize + heartSpacing;
                renderer.getBatch().draw(heartFull, heartsX, heartsY, heartSize, heartSize);
                heartsX += heartSize + heartSpacing;
                renderer.getBatch().draw(heartEmpty, heartsX, heartsY, heartSize, heartSize);
                heartsX += heartSize + heartSpacing;
                break;
            // pas de break ici pour passer au cas suivant
            case 3:
                renderer.getBatch().draw(heartFull, heartsX, heartsY, heartSize, heartSize);
                heartsX += heartSize + heartSpacing;
                renderer.getBatch().draw(heartHalf, heartsX, heartsY, heartSize, heartSize);
                heartsX += heartSize + heartSpacing;
                renderer.getBatch().draw(heartEmpty, heartsX, heartsY, heartSize, heartSize);
                heartsX += heartSize + heartSpacing;
                break;
            case 2:
                renderer.getBatch().draw(heartFull, heartsX, heartsY, heartSize, heartSize);
                heartsX += heartSize + heartSpacing;
                renderer.getBatch().draw(heartEmpty, heartsX, heartsY, heartSize, heartSize);
                heartsX += heartSize + heartSpacing;
                renderer.getBatch().draw(heartEmpty, heartsX, heartsY, heartSize, heartSize);
                heartsX += heartSize + heartSpacing;
                break;
            case 1:
                renderer.getBatch().draw(heartHalf, heartsX, heartsY, heartSize, heartSize);
                heartsX += heartSize + heartSpacing;
                renderer.getBatch().draw(heartEmpty, heartsX, heartsY, heartSize, heartSize);
                heartsX += heartSize + heartSpacing;
                renderer.getBatch().draw(heartEmpty, heartsX, heartsY, heartSize, heartSize);
                heartsX += heartSize + heartSpacing;
                break;
            case 0:
                renderer.getBatch().draw(heartEmpty, heartsX, heartsY, heartSize, heartSize);
                heartsX += heartSize + heartSpacing;
                renderer.getBatch().draw(heartEmpty, heartsX, heartsY, heartSize, heartSize);
                heartsX += heartSize + heartSpacing;
                renderer.getBatch().draw(heartEmpty, heartsX, heartsY, heartSize, heartSize);

                game.setScreen(new LoseScreen(game));
                dispose();

                break;
            default:
        }

        renderer.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        map.dispose();
        heartFull.dispose();
        heartHalf.dispose();
        heartEmpty.dispose();
    }

    // create horizontal corridor to connect rooms
    public void hCorridor(int x1, int x2, int y) {
        for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
            TiledMapTileLayer.Cell floorCell = new TiledMapTileLayer.Cell();
            int bound = floorTiles.size();
            int index = random.nextInt(bound);
            if (index == 0)
                index++;

            floorCell.setTile(floorTiles.get(index));

            dungeonLayer.setCell(x, y, floorCell);
            dungeonLayer.setCell(x, y - 1, floorCell);
        }
    }

    public void vCorridor(int y1, int y2, int x) {
        for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
            placeBlock(x, y, floorTiles);
            placeBlock(x - 1, y, floorTiles);
        }
    }

    public Room getCurrentRoom(Player player) {
        float playerX = player.getPosition().x / 64;
        float playerY = player.getPosition().y / 64;
        int roomX;
        int roomY;
        int roomWidth;
        int roomHeight;

        for(Room room : rooms) {
            roomX = room.getX();
            roomY = room.getY();
            roomWidth = room.getWidth();
            roomHeight = room.getHeight();

            if(playerX >= roomX && playerX <= roomX + roomWidth && playerY >= roomY && playerY <= roomY + roomHeight) {
                return room;
            }
        }
        return null;
    }

    public void lock(Room aRoom) {
        //Store the rooms contours
        int roomX = aRoom.getX() - 1;
        int roomY = aRoom.getY() - 1 ;
        int roomWidth = aRoom.getWidth() + 2;
        int roomHeight = aRoom.getHeight() + 2;
        TiledMapTileLayer.Cell currentCell;

        //We'll browse through the limits of the room and replace floor tiles with walls.
        // X
        for (int x = roomX; x <= roomX + roomWidth; x++) {
            currentCell = dungeonLayer.getCell(x, roomY);
            if(currentCell.getTile().getProperties().containsKey("floor")) {
                placeBlock(x, roomY, wallTiles);
                doors.add(new Point(x, roomY));
            }
            currentCell = dungeonLayer.getCell(x, roomY + roomHeight);
            if(currentCell.getTile().getProperties().containsKey("floor")) {
                placeBlock(x, roomY + roomHeight, wallTiles);
                doors.add(new Point(x, roomY + roomHeight));
            }
        }
        // Y
        for (int y = roomY; y <= roomY + roomHeight; y++) {
            currentCell = dungeonLayer.getCell(roomX, y);
            if(currentCell.getTile().getProperties().containsKey("floor")) {
                placeBlock(roomX, y, wallTiles);
                doors.add(new Point(roomX, y));
            }
            currentCell = dungeonLayer.getCell(roomX + roomWidth, y);
            if(currentCell.getTile().getProperties().containsKey("floor")) {
                placeBlock(roomX + roomWidth, y, wallTiles);
                doors.add(new Point(roomX + roomWidth, y));
            }
        }
    }

    private void unlock(Room room) {
        for(Point door : doors) {
            placeBlock((int) door.getX(), (int) door.getY(), floorTiles);
        }
    }

    public void placeBlock(int X, int Y, Map<Integer, TiledMapTile> tiles) {
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        int bound = tiles.size();
        int index = random.nextInt(bound);
        if (index == 0)
            index++;

        cell.setTile(tiles.get(index));
        dungeonLayer.setCell(X, Y, cell);
    }
    private float calculateDistance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
    //#endregion

    //#region GETTERS AND SETTERS
    public static TiledMapTileLayer getDungeonLayer() {
        return dungeonLayer;
    }
    public static void setDungeonLayer(TiledMapTileLayer dungeonLayer) {
        GameScreen.dungeonLayer = dungeonLayer;
    }
    public static OrthogonalTiledMapRenderer getRenderer() {
        return renderer;
    }
    public static void setRenderer(OrthogonalTiledMapRenderer renderer) {
        GameScreen.renderer = renderer;
    }
    public Array<Monster> getMonsters() {
        return monsters;
    }
    //#endregion
}
