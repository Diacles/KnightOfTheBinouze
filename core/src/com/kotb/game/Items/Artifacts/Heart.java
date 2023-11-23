package com.kotb.game.Items.Artifacts;
import com.kotb.game.Global;
import com.kotb.game.Units.*;

public class Heart extends Artifact {

    public Heart() {
        super("Heart", "This magical heart allows you to regenerate your life a little", "HEals a Heart", 100, "ui_heart_full.png");
    }

    public void applyEffect() {
        Global.getPlayer().setHp(Global.getPlayer().getMaxHP());
    }
}
