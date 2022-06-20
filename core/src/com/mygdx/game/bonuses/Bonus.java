package com.mygdx.game.bonuses;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.GameObject;
import com.mygdx.game.Player;

public abstract class Bonus extends GameObject {
    Bonus(Texture texture){
        super(texture);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(new TextureRegion(getTexture()),getX(),getY(),getWidth() / 2,
                getHeight()/2,
                getWidth(),
                getHeight(),0.5f,
                0.5f
                ,0);
    }

    public abstract void effect(Player player);
}
