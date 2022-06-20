package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationPlayer {
    private TextureAtlas textureAtlas;
    private Animation<TextureRegion> animation;
    private float timePassed = 0;
    private float frameTime = 1/20f;
    private float x,y;
    private float size;

    public AnimationPlayer(String type,float x,float y,float frameTime,float size){
        textureAtlas = Constants.textureAtlases.get(type);
        animation = new Animation<TextureRegion>(frameTime,textureAtlas.getRegions());
        animation.setFrameDuration(frameTime);

        this.x = x;
        this.y = y;
        this.frameTime = frameTime;
        this.size = size;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void dispose(){
        textureAtlas.dispose();
    }

    public boolean isOver(){
        if (timePassed > frameTime * textureAtlas.getRegions().size) return true;
        return false;
    }

    public void render(SpriteBatch batch){
        TextureRegion currentFrame = animation.getKeyFrame(timePassed,false);
        batch.draw(currentFrame,x-size/2,y-size/2,size,size);
    }

    public void update(float delta){
        timePassed += delta;
    }
}
