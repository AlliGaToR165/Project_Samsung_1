package org.dhunt.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Background{
    private Texture texture;
    private int out=0, length, height;
    private final int BG_MOVE_SPEED=1;

    public Background(){
        texture = new Texture("sky.jpg");
        length = texture.getWidth();
        height = texture.getHeight();
    }

    public void update(float delta){
        out -= BG_MOVE_SPEED;
        while((-out) >= length) out += length;
    }

    public void draw(SpriteBatch batch) {
        batch.begin();

        int x, y = -50;
        do{
            x = out;
            do {
                batch.draw(texture, x, y);
                x += length;
            } while (x <= Gdx.graphics.getWidth());
            y += height;
        } while (y <= Gdx.graphics.getHeight());

        batch.end();
    }

    public void dispose(){
        texture.dispose();
    }
}
