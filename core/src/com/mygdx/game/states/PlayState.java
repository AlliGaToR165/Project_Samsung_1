package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.InputHandler;
import com.mygdx.game.Score;
import com.mygdx.game.sprite.Duck;

public class PlayState extends AbstractState{
    private BitmapFont font;
    private Texture sight;
    private Texture background1, background2,background3,background4;
    float xMax, xCoordBg1, xCoordBg2,xCoordBg3,xCoordBg4;
    private Texture gras;
    private Texture ground;
    private int score;
    private final int DUCKS_COUNT = 3;
    private Duck[] ducks;
    final int BACKGROUND_MOVE_SPEED = 50;
    private int v, Number,isReversed;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        background1 = new Texture("sky.jpg");
        background2 = new Texture("sky.jpg");
        background3 = new Texture("sky.jpg");
        background4 = new Texture("sky.jpg");
        sight = new Texture("sight.png");
        font = new BitmapFont(Gdx.files.internal("myfont.fnt"),Gdx.files.internal("myfont.png"),false);
        gras = new Texture("gras.png");
        ground = new Texture("ground.png");
        score = 0;
        ducks = new Duck[DUCKS_COUNT];
        Number=0;

        for(int i=0;i<DUCKS_COUNT;i++) {
            isReversed = MathUtils.random(0,1);
            if(isReversed==1){
                ducks[i] = new Duck(new Vector2(Gdx.graphics.getWidth()+256, -252), new Vector2(-1.5f, 1.0f),isReversed);
            }
            else{
                ducks[i] = new Duck(new Vector2(-256, -252), new Vector2(1.5f, 1.0f),isReversed);
            }
        }
        xMax = background1.getWidth();
        xCoordBg1 = 0;
        xCoordBg2=xCoordBg1+xMax;
        xCoordBg3=xCoordBg2+xMax;
        xCoordBg4=xCoordBg3+xMax;
        v=1;
    }

    @Override
    public void update(float delta) {
        xCoordBg1 -= BACKGROUND_MOVE_SPEED * Gdx.graphics.getDeltaTime();
        xCoordBg2 -= BACKGROUND_MOVE_SPEED * Gdx.graphics.getDeltaTime();
        xCoordBg3 -= BACKGROUND_MOVE_SPEED * Gdx.graphics.getDeltaTime();
        xCoordBg4 -= BACKGROUND_MOVE_SPEED * Gdx.graphics.getDeltaTime();
        for(int i=0;i<Number+1;i++){
            ducks[i].update(delta);
        }
        switch (v) {
            case 2:
                if (xCoordBg2 + xMax <= 0) {
                    xCoordBg2 = xCoordBg1 + xMax;
                    v = 3;
                }
            case 3:
                if (xCoordBg3 + xMax <= 0) {
                    xCoordBg3 = xCoordBg2 + xMax;
                    v = 4;
                }
            case 4:
                if (xCoordBg4 + xMax <= 0) {
                    xCoordBg4 = xCoordBg3 + xMax;
                    v = 1;
                }
            case 1:
                if (xCoordBg1 + xMax <= 0) {
                    xCoordBg1 = xCoordBg4 + xMax;
                    v = 2;
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
        for(int i=0;i<DUCKS_COUNT;i++) {
            ducks[i].render(batch);
        }
        if (InputHandler.isClicked()) {
            if (ducks[Number].getBounds().contains(InputHandler.getMousePosition().x,InputHandler.getMousePosition().y))
            {
                ducks[Number].setKilled(true);
                score++;
                Score.setScores(score);
                Number++;
            }
        }
        font.draw(batch, "Scores: " + score, 10,Gdx.graphics.getHeight() - 20);
        int x = 0;
            do {
                batch.draw(ground, x, 0);
                batch.draw(gras, -30 + x, -80);
                x += ground.getWidth();
            } while (x <= Gdx.graphics.getWidth());
            batch.draw(sight, InputHandler.getMousePosition().x - sight.getWidth() / 2, InputHandler.getMousePosition().y - sight.getHeight() / 2);
            if (((ducks[DUCKS_COUNT - 1].getBounds().x - ducks[DUCKS_COUNT - 1].getBounds().width > Gdx.graphics.getWidth()) && ducks[DUCKS_COUNT - 1].getReversed() == 0)
                    || (ducks[DUCKS_COUNT - 1].getBounds().x + ducks[DUCKS_COUNT - 1].getBounds().width < 0 && ducks[DUCKS_COUNT - 1].getReversed() == 1)
                    || ducks[DUCKS_COUNT - 1].isKilled()) {
                gsm.set(new GameOverState(gsm));
            }
            else if((((ducks[Number].getBounds().x -ducks[Number].getBounds().width> Gdx.graphics.getWidth()) && ducks[Number].getReversed()==0)
                    ||( (ducks[Number].getBounds().x+ducks[Number].getBounds().width<0) && (ducks[Number].getReversed()==1)))){
                Number++;
            }
            batch.end();
        }


    @Override
    public void dispose() {
        sight.dispose();
        for (int i = 0; i < DUCKS_COUNT; i++) {
            ducks[i].dispose();
        }
        font.dispose();
        gras.dispose();
        background1.dispose();
        background2.dispose();
        background3.dispose();
        background4.dispose();
        ground.dispose();
    }
}
