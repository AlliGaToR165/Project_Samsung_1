package org.dhunt.game;

import org.dhunt.game.Background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Shared{
    public static BitmapFont hack = new BitmapFont(Gdx.files.internal("Hack.fnt"),Gdx.files.internal("Hack.png"),false),
                             font = new BitmapFont(Gdx.files.internal("myfont.fnt"),Gdx.files.internal("myfont.png"),false);
    public static Background bg = new Background();
    public static int ducks = 3;
    public static Stage stage = new Stage(new ScreenViewport());
}
