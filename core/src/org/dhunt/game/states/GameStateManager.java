package org.dhunt.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class GameStateManager {
    private final Stack<AbstractState> states;

    public GameStateManager() {
        this.states = new Stack<>();
    }

    public void push(AbstractState state) {
        this.states.push(state);
    }

    public void pop() {
        this.states.pop().dispose();
    }

    public void set(AbstractState state) {
        this.pop();
        this.states.push(state);
    }

    public void update(float delta) {
        this.states.peek().update(delta);
    }

    public void render(SpriteBatch batch) {
        this.states.peek().render(batch);
    }

}
