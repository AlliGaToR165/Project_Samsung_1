package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class BaseMenuState extends AbstractState{
    private class DuckCountIL implements TextInputListener {
       @Override
       public void input (String text) {
           ducks = Integer.parseInt(text);
           if(ducks == 0) ducks = 3;
           else if(ducks < 0) ducks = -ducks;
           setCount.setText("Hunting until "+Integer.toString(ducks)+" ducks");
       }
       @Override
       public void canceled () {}
    }

    private DuckCountIL duckCountInput;
    protected BitmapFont defaultFont, hack;
    private Texture background1, background2,background3,background4;
    protected TextButton mainButton, setCount;
    private float xMax, xCoordBg1, xCoordBg2,xCoordBg3,xCoordBg4;
    final int BACKGROUND_MOVE_SPEED = 50;
    private int v;
    protected int ducks=3;
    protected Stage stage;

    public BaseMenuState(GameStateManager gsm, String buttonText, String buttonImgPath) {
        super(gsm);
        stage = new Stage(new ScreenViewport());
	    Gdx.input.setInputProcessor(stage);

        defaultFont = new BitmapFont(Gdx.files.internal("myfont.fnt"),Gdx.files.internal("myfont.png"),false);
        hack = new BitmapFont(Gdx.files.internal("Hack.fnt"),Gdx.files.internal("Hack.png"),false);
        mainButton = new TextButton(buttonText, new TextButton.TextButtonStyle(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal(buttonImgPath)))), null,
                                                                               new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal(buttonImgPath)))), defaultFont));
        setCount = new TextButton("Hunting until "+Integer.toString(ducks)+" ducks",
                                  new TextButton.TextButtonStyle(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("set_count.png")))), null,
                                                                 new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("set_count.png")))), hack));
        stage.addActor(mainButton);
        stage.addActor(setCount);

        duckCountInput = new DuckCountIL();

        background1 = new Texture("sky.jpg");
        background2 = new Texture("sky.jpg");
        background3 = new Texture("sky.jpg");
        background4 = new Texture("sky.jpg");

        xMax = background1.getWidth();
        xCoordBg1 = 0;
        xCoordBg2=xCoordBg1+xMax;
        xCoordBg3=xCoordBg2+xMax;
        xCoordBg4=xCoordBg3+xMax;
        v=1;
    }

    public BaseMenuState(GameStateManager gsm, String buttonText, String buttonImgPath, int ducks) {
        this(gsm, buttonText, buttonImgPath);
        this.ducks = ducks;
        setCount.setText("Hunting until "+Integer.toString(ducks)+" ducks");
    }

    protected void handleInput() {
        if(setCount.isPressed()) {
            Gdx.input.getTextInput(duckCountInput, "Playing before:", "3", "");
            try{ Thread.sleep(1000);} catch(InterruptedException e){}
        }
    }

    @Override
    public void update(float delta) {
        xCoordBg1 -= BACKGROUND_MOVE_SPEED * Gdx.graphics.getDeltaTime();
        xCoordBg2 -= BACKGROUND_MOVE_SPEED * Gdx.graphics.getDeltaTime();
        xCoordBg3 -= BACKGROUND_MOVE_SPEED * Gdx.graphics.getDeltaTime();
        xCoordBg4 -= BACKGROUND_MOVE_SPEED * Gdx.graphics.getDeltaTime();
        handleInput();

        switch (v){
            case 2:
                if (xCoordBg2+xMax<=0){
                    xCoordBg2=xCoordBg1+xMax;
                    v=3;
                }
            case 3:
                if (xCoordBg3+xMax<=0){
                    xCoordBg3=xCoordBg2+xMax;
                    v=4;            }
            case 4:
                if (xCoordBg4+xMax<=0){
                    xCoordBg4=xCoordBg3+xMax;
                    v=1;
                }
            case 1:
                if (xCoordBg1+xMax<=0){
                    xCoordBg1=xCoordBg4+xMax;
                    v=2;
                }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(background1,xCoordBg1,-50);
        batch.draw(background2,xCoordBg2,-50);
        batch.draw(background3,xCoordBg3,-50);
        batch.draw(background4,xCoordBg4,-50);
        batch.end();

        float delta = Gdx.graphics.getDeltaTime();
        stage.act(delta);
	    stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        background1.dispose();
        background2.dispose();
        background3.dispose();
        background4.dispose();
        defaultFont.dispose();
        hack.dispose();
    }
}
