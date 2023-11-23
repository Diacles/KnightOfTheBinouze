import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SettingsMenuScreen implements Screen {
    private Stage stage;
    private Skin skin;
    private ImageButton soundButton;
    private boolean isSoundEnabled = true;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Créez une peau (Skin) pour votre interface graphique
        skin = new Skin();

        // Chargez vos images pour les états activé et désactivé
        Texture soundOnTexture = new Texture(Gdx.files.internal("sound_on_image.png"));
        Texture soundOffTexture = new Texture(Gdx.files.internal("sound_off_image.png"));

        // Créez un style pour l'image bouton
        ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.imageUp = skin.newDrawable(soundOnTexture); // Image quand le son est activé
        imageButtonStyle.imageDown = skin.newDrawable(soundOffTexture); // Image quand le son est désactivé

        // Créez le bouton avec le style défini
        soundButton = new ImageButton(imageButtonStyle);
        soundButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                toggleSound();
            }
        });

        // Ajoutez le bouton à la scène
        stage.addActor(soundButton);
    }

    @Override
    public void render(float delta) {
        // Effacez l'écran
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        // Dessinez la scène
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
        skin.dispose();
    }

    private void toggleSound() {
        // Inversez l'état du son (activé/désactivé)
        isSoundEnabled = !isSoundEnabled;

        // Mettez à jour l'image du bouton en conséquence
        if (isSoundEnabled) {
            soundButton.getStyle().imageUp = skin.newDrawable(new Texture(Gdx.files.internal("sound_on_image.png")));
        } else {
            soundButton.getStyle().imageUp = skin.newDrawable(new Texture(Gdx.files.internal("sound_off_image.png")));
        }

        // Implémentez ici la logique pour activer/désactiver le son
    }
}
