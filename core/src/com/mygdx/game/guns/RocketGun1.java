package com.mygdx.game.guns;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.Constants;
import com.mygdx.game.bullets.LaserBulletR_1;
import com.mygdx.game.bullets.RocketBullet;
import com.mygdx.game.ships.Ship;

public class RocketGun1 extends Gun{

    private static final Texture TEXTURE = Constants.textures.get("rocket turret");
    private static final float COOLDOWN = 1f;
    private static final float BASE_DAMAGE = 250f;
    private static final float RANGE = 2000f;

    public RocketGun1(Ship ship) {
        super(TEXTURE, ship, COOLDOWN, BASE_DAMAGE, RANGE);

    }

    public RocketGun1(){
        super(TEXTURE,COOLDOWN,BASE_DAMAGE,RANGE);
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
    public void attack(float startX, float startY) {
        if (!isAbleToAttack()) return;
        this.bullets.add(new RocketBullet(startX, startY, ship.getRotation()));
        bullets.peek().type = ship.type;
        float damage = BASE_DAMAGE + MathUtils.random(-20f,40f);
        bullets.peek().setDamage(damage);
    }

    @Override
    public Object clone() {
        return new RocketGun1();
    }
}
