package com.kotb.game.Items.Artifacts;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.kotb.game.Items.Item;
import com.kotb.game.Units.Unit;

public abstract class Artifact extends Item {

    //#region // ---- Attribute ---- //

    protected String effectName;
    protected int probability;

    protected Sprite artifactSprite;
    //#endregion

    // Constructor
    public Artifact(String name, String description, String effectName, int probability, String texturePath) {
        super(name, description, texturePath);
        this.effectName = effectName;
        this.probability = probability;

        // Initialise le sprite avec la texture spécifiée
        this.artifactSprite = new Sprite(new Texture(texturePath));
    }

    //#region // ----- Getter and Setter ----- //

    public String getEffectName() {
        return effectName;
    }

    public void setEffectName(String effectName) {
        this.effectName = effectName;
    }

    public int getProbability() {
        return probability;
    }

    public void setProbability(int probability) {
        this.probability = probability;
    }

    // methode
    public boolean applyEffect(Unit unit) {

        return false;
    }
    public abstract void applyEffect();
    //#endregion

}