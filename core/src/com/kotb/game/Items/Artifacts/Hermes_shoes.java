package com.kotb.game.Items.Artifacts;
import com.kotb.game.Global;
import com.kotb.game.Units.*;


public class Hermes_shoes extends Artifact {

    // Constructeur
    public Hermes_shoes() {
        super("Hermes shoes", "adds 15% more speed", "Speed", 100, "artifact/hermesBoots.png");
    }

    // Applique l'effet des chaussures d'Hermès sur une unité
    public void applyEffect() {
        Global.getPlayer().setSpeed(Global.getPlayer().getSpeed() * 2);
    }
}
