package com.noname.mrch.gui;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.noname.mrch.GameWorld;

/**
 * Basic window used by gui. Interacts with a gui and gameworld.
 */

class GuiWindow extends Dialog {
    protected Gui gui;
    GameWorld gameWorld;

    GuiWindow(String title, Skin skin, Gui gui, GameWorld gameWorld) {
        super(title, skin);

        this.gui = gui;
        this.gameWorld = gameWorld;

        getTitleLabel().setAlignment(Align.center);
        setMovable(false);
        setModal(true);
    }
}
