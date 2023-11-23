package com.kotb.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.kotb.game.KnightOfTheBinouze;

public class LoseScreen implements Screen {
    //#region // -------- Attributes -------- //

    final KnightOfTheBinouze game;
    private OrthographicCamera camera;
    private Stage stage;
    private Music loser;
    private Sound menuClick;

    public LoseScreen(final KnightOfTheBinouze game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        loser = Gdx.audio.newMusic(Gdx.files.internal("Music/loser.mp3"));
        menuClick = Gdx.audio.newSound(Gdx.files.internal("Music/menuClick.mp3"));
        loser.play();


        // Crée la scène (Stage) pour les boutons
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // Crée les boutons avec des tailles spécifiques
        ImageButton gameOver = createButton("Screen/gameover.png", 500, 150);
        ImageButton menuButton = createButton("Screen/home_button.png", 50, 50);
        ImageButton exitButton = createButton("Screen/exit_button.png", 50, 50);



        // Ajoute les boutons à la scène
        Table table = new Table();
        table.setFillParent(true);
        table.add(gameOver).padBottom(20).row();
        table.add(menuButton).padBottom(20).row();
        table.add(exitButton);
        stage.addActor(table);
    }

    private ImageButton createButton(final String imagePath, float width, float height) {
        Texture buttonTexture = new Texture(Gdx.files.internal(imagePath));

        // Crée une région de texture à partir de la texture complète
        TextureRegionDrawable buttonDrawable = new TextureRegionDrawable(new TextureRegion(buttonTexture));

        // Redimensionne la région de texture à la taille souhaitée
        buttonDrawable.setMinWidth(width);
        buttonDrawable.setMinHeight(height);

        // Crée le style du bouton avec la région redimensionnée
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = buttonDrawable;

        // Crée le bouton avec le style
        ImageButton button = new ImageButton(style);

        // Ajoute un écouteur de clic au bouton
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleButtonClick(imagePath);
            }
        });

        return button;
    }

    private void handleButtonClick(String buttonType) {
        // Gérer les actions en fonction du bouton cliqué
        if (buttonType.equals("Screen/gameover.png")) {
            // Ajoutez ici le code pour le bouton "gameOver"
        } else if (buttonType.equals("Screen/home_button.png")) {
            game.setScreen(new MainMenuScreen(game));// Allez au Main Screen
            dispose();
        } else if (buttonType.equals("Screen/exit_button.png")) {
            Gdx.app.exit(); // Quitte l'application
        }
    }

    //#region // --------- Override --------- //
    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        SpriteBatch batch = game.getSpriteBatch();
        BitmapFont font = game.getFont();

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.end();

        // Dessine la scène des boutons
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
    //#endregion
    // ---------------------------- //
}
