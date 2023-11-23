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

public class SettingsMenuScreen implements Screen {

    private final KnightOfTheBinouze game;
    private OrthographicCamera camera;
    private Stage stage;
    private boolean isSoundEnabled = true;
    private ImageButton soundButton;

    public SettingsMenuScreen(KnightOfTheBinouze game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        soundButton = createButton("play_button.png", 100, 100);

        Table table = new Table();
        table.setFillParent(true);
        table.add(soundButton).padBottom(20).row();
        stage.addActor(table);
    }

    private ImageButton createButton(final String imagePath, float width, float height) {
        Texture buttonTexture = new Texture(Gdx.files.internal(imagePath));
        TextureRegionDrawable buttonDrawable = new TextureRegionDrawable(new TextureRegion(buttonTexture));
        buttonDrawable.setMinWidth(width);
        buttonDrawable.setMinHeight(height);

        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = buttonDrawable;

        ImageButton button = new ImageButton(style);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                toggleSound();
            }
        });

        return button;
    }

    private void toggleSound() {
        isSoundEnabled = !isSoundEnabled;

        // Mettez à jour l'image du bouton en conséquence
        String imagePath = isSoundEnabled ? "play_button.png" : "exit_button.png";
        Texture buttonTexture = new Texture(Gdx.files.internal(imagePath));
        TextureRegionDrawable buttonDrawable = new TextureRegionDrawable(new TextureRegion(buttonTexture));

        // Mettez à jour le style du bouton
        soundButton.getStyle().imageUp = buttonDrawable;

        // Implémentez ici la logique pour activer/désactiver le son
        // Par exemple, utilisez isSoundEnabled pour ajuster le volume, etc.
        if (isSoundEnabled) {
            // Logique pour activer le son
            Gdx.app.log("Settings", "Sound enabled");
        } else {
            // Logique pour désactiver le son
            Gdx.app.log("Settings", "Sound disabled");
        }
    }

    @Override
    public void show() {
        // ...
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
        font.draw(batch, "Settings", 300, 400);
        batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // ...
    }

    @Override
    public void resume() {
        // ...
    }

    @Override
    public void hide() {
        // ...
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
