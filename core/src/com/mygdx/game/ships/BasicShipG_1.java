package com.mygdx.game.ships;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.guns.Gun;

public class BasicShipG_1 extends Ship {
    private final static Texture TEXTURE = new Texture("green_ship.jpg");
    private final static float HEALTH = 500f;
    private final static float ROTATION_SPEED = 140f;
    private final static float MAX_SPEED_POWER = 30f;
    private final static int MAX_AMOUNT_OF_GUNS = 3;
    public BasicShipG_1(Array<Gun> guns) {
        super(TEXTURE,guns,MAX_AMOUNT_OF_GUNS, HEALTH, ROTATION_SPEED);
    }
    public BasicShipG_1(){
        super(TEXTURE,MAX_AMOUNT_OF_GUNS,HEALTH,ROTATION_SPEED,MAX_SPEED_POWER);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
    }

    @Override
    protected void initPointsForGuns() {
        super.initPointsForGuns();
        positionsForGuns.add(new Vector2(15,getHeight()/2));
        positionsForGuns.add(new Vector2(getWidth()-15,
                getHeight()/2));
        positionsForGuns.add(new Vector2(getWidth()/2,
                getHeight()/2 + 10));
    }

    @Override
    public Object clone() {
        return new BasicShipG_1();
    }
}
