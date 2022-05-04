package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Background{
    private Texture background1, background2,background3,background4;
    private float xMax, xCoordBg1, xCoordBg2,xCoordBg3,xCoordBg4;
    final int BACKGROUND_MOVE_SPEED = 50;
    private int v;

    public Background() {
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

    public void update(float delta) {
        xCoordBg1 -= BACKGROUND_MOVE_SPEED * Gdx.graphics.getDeltaTime();
        xCoordBg2 -= BACKGROUND_MOVE_SPEED * Gdx.graphics.getDeltaTime();
        xCoordBg3 -= BACKGROUND_MOVE_SPEED * Gdx.graphics.getDeltaTime();
        xCoordBg4 -= BACKGROUND_MOVE_SPEED * Gdx.graphics.getDeltaTime();
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

    public void draw(SpriteBatch batch) {
        batch.begin();
        batch.draw(background1,xCoordBg1,-50);
        batch.draw(background2,xCoordBg2,-50);
        batch.draw(background3,xCoordBg3,-50);
        batch.draw(background4,xCoordBg4,-50);
        batch.end();
    }

    public void dispose() {
        background1.dispose();
        background2.dispose();
        background3.dispose();
        background4.dispose();
    }
}
