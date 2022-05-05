package com.mygdx.game.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Duck {
    private Texture texture;
    private Vector2 position;
    private Vector2 velocity;
    private Rectangle bounds;
    private boolean isKilled;
    private boolean isReversed;
    private float time;
    private float angle = 0;
    private final int WIDTH = 252;
    private final int HEIGHT = 256;

    public Duck(Vector2 position, Vector2 velocity, boolean isReversed) {
        this.position = position;
        this.velocity = velocity;
        this.isReversed=isReversed;
        if(isReversed){
            this.texture = new Texture("duck_reversed.png");
        }
        else{
            this.texture = new Texture("duck.png");
        }
        bounds = new Rectangle(position.x,position.y, WIDTH,HEIGHT);
    }

    public void render(SpriteBatch batch) {
        int frame = (int) (time / 0.1f);
        if(this.isReversed){
            frame = 3 -(frame % 4);
        }
        else{
            frame = frame % 4;
        }
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
    public Vector2 getPosition() {
        return position;
    }
    public boolean getReversed(){
        return isReversed;
    }
    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
