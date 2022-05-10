package com.mygdx.game.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Duck {
    private Texture texture;
    private Vector2 position, velocity;
    private Rectangle bounds;
    private boolean isKilled, isReversed;
    private float time, angle = 0;
    private final int WIDTH = 252, HEIGHT = 256;

    public Duck(Vector2 position, Vector2 velocity, boolean isReversed) {
        this.position = position;
        this.velocity = velocity;
        this.isReversed=isReversed;
        loadTexture();
        bounds = new Rectangle(position.x,position.y, WIDTH,HEIGHT);
    }

    public void render(SpriteBatch batch) {
        int frame = (int) (time / 0.1f);
        frame = (this.isReversed ? 3 -(frame % 4) : frame % 4);
        batch.draw(texture, position.x, position.y, frame * WIDTH, 0, WIDTH, HEIGHT);
        batch.draw(texture, position.x, position.y, WIDTH/2, HEIGHT/2, WIDTH, HEIGHT,1,1,angle,frame * WIDTH,0, WIDTH,HEIGHT,false,false);
    }

    public void dispose() {
        texture.dispose();
    }

    public void update(float delta) {
        time+= delta;
        position.add(velocity);
        if(isReversed){
            velocity.x -= 0.5f * delta;
        }
        else{
            velocity.x += 0.5f * delta;
        }
        bounds.setPosition(position.x,position.y);
        if (isKilled) {
            angle+=10;
            velocity.y--;
        }
    }

    public void kill(){
        isKilled = true;
    }
    public boolean isDead(){
        return ((position.y + HEIGHT < 0) && isKilled);
    }

    public boolean getReversed(){return isReversed;}
    public Rectangle getBounds() {return bounds;}
    public void loadTexture(){
        this.texture = new Texture(isReversed ? "duck_reversed.png" : "duck.png");
    }
}
