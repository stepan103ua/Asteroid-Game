package com.mygdx.game.asteroids;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Circle;
import com.mygdx.game.GameObject;

public abstract class Enemy extends GameObject {
    public float health,maxHealth;
    public float size;
    public static final String TYPE = "enemy";
    protected BitmapFont font = new BitmapFont();
    public boolean destroy(){
        return health <= 0;
    }
    protected Enemy(Texture texture) {
        super(texture);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }


    public Enemy() {
        super();
    }

    public abstract Circle getBounds();

    public abstract void takeDamage(float damage);

    public abstract boolean isOutOfRange();
}
