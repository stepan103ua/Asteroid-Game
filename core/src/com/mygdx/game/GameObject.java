package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameObject extends Sprite {

    Texture texture;
    protected GameObject(Texture texture){
        super(texture);
        this.texture = texture;
    }

    public GameObject() {

    }

    public void update(float delta){

    }
    public void render(SpriteBatch batch){
        batch.draw(this,
                this.getX() - getTexture().getWidth()/2,
                this.getY() - getTexture().getHeight()/2,
                getOriginX(),getOriginY(),
                getTexture().getWidth()
                ,getTexture().getHeight(),getScaleX(),getScaleY(),getRotation());
    }

}
