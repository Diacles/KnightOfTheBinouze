package com.kotb.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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

public class SaveMenuScreen implements Screen {

    final KnightOfTheBinouze game;
    private OrthographicCamera camera;
    private Stage stage;

    public SaveMenuScreen(final KnightOfTheBinouze game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // Crée la scène (Stage) pour les boutons
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // Crée les boutons avec des tailles spécifiques
        ImageButton loadButton = createButton("play_button.png", 150, 150);
        ImageButton backButton = createButton("exit_button.png", 150, 150);

        // Ajoute les boutons à la scène
        Table table = new Table();
        table.setFillParent(true);
        table.add(loadButton).padBottom(20).row();
        table.add(backButton);
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
        if (buttonType.equals("load_button.png")) {
            // Ajouter le code pour charger la partie sauvegardée
            // game.loadSavedGame(); // À implémenter
            // Puis démarrer l'écran de jeu
            game.setScreen(new GameScreen(game));
            dispose();
        } else if (buttonType.equals("back_button.png")) {
            // Retourner au menu principal
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.18f, 0.18f, 0.18f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        SpriteBatch batch = game.getSpriteBatch();
        BitmapFont font = game.getFont();

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        font.draw(batch, "Save Menu", 300, 400);
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
}
