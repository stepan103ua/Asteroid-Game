package com.mygdx.game.ships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GameObject;
import com.mygdx.game.Player;
import com.mygdx.game.Wave;
import com.mygdx.game.asteroids.Enemy;
import com.mygdx.game.bonuses.Bonus;
import com.mygdx.game.bullets.Bullet;
import com.mygdx.game.guns.Gun;

public abstract class Ship extends GameObject {
    private float health,maxHealth,rotationSpeed;
    public float centerWidth;
    public float centerHeight;
    public String type;
    private boolean autoMove = false;
    private boolean autoRotateLeft = false;
    private boolean autoRotateRight = false;
    private float speedPower;
    private float maxSpeedPower;
    private float angleOfMovement = 0;
    private boolean isNPC = false;
    private boolean holdAttack = false;
    private boolean hasTargetNPC = false;
    protected Player player;
    protected int maxAmountOfGuns;
    protected Array<Gun> guns;
    protected Array<Vector2> positionsForGuns;
    Ship(Texture texture,Array<Gun> guns,int maxAmountOfGuns,float health,
         float rotationSpeed){
        super(texture);
        baseInit(texture,maxAmountOfGuns,health,rotationSpeed);
    }
    Ship(Texture texture,int maxAmountOfGuns,float health,
         float rotationSpeed,float maxSpeedPower){
        super(texture);
        baseInit(texture,maxAmountOfGuns,health,rotationSpeed);
        this.maxSpeedPower = maxSpeedPower;
    }


    public void dispose(){

    }

    public void update(float delta){
        if (autoMove)addSpeed(delta);
        if (autoRotateLeft)rotate( rotationSpeed*delta);
        if (autoRotateRight)rotate(-rotationSpeed*delta);
        if (!isNPC){
            inputEvents(delta);
        }

        if (holdAttack){
            attack();
        }

        if (isNPC && !hasTargetNPC){
            speedPower += maxSpeedPower * 2 * delta;
        }
        move(delta);

        setRotation(getRotation()%360);
        centerWidth = getX() + getWidth()/2;
        centerHeight = getY() + getHeight()/2;

        updateGuns(delta);
    }

    public void setNPC(boolean isNPC){
        this.isNPC = isNPC;
    }

    public void setHoldAttack(boolean holdAttack){
        this.holdAttack = holdAttack;
    }

    public void setHasTargetNPC(boolean hasTargetNPC) {
        this.hasTargetNPC = hasTargetNPC;
    }

    public boolean hasTargetNPC() {
        return hasTargetNPC;
    }

    public int getMaxAmountOfGuns() {
        return maxAmountOfGuns;
    }

    public void render(SpriteBatch batch){
        batch.draw(
                this,
                getX(),
                getY(),
                getWidth()/2,
                getHeight()/2,
                getWidth(),
                getHeight(),
                1f,
                1f,
                getRotation());
        renderGuns(batch);
        //gun.render(batch);
    }


    private void move(float delta){
        speedPower = MathUtils.clamp(speedPower,0,maxSpeedPower);
        float dx = (float) (Math.cos((getRotation() + 90) * 0.017453f) * 6) * delta * speedPower;
        float dy = (float) (Math.sin((getRotation() + 90) * 0.017453f) * 6) * delta * speedPower;

        float x = getX() + dx;
        float y = getY() + dy;

        this.setPosition(x,y);
    }

    public void addSpeed(float delta){
        speedPower += maxSpeedPower * 2 * delta;
    }

    private void inputEvents(float delta){
        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            addSpeed(delta);
        }
        else speedPower -= maxSpeedPower / 2 * delta;


        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.X) || Gdx.input.isKeyPressed(Input.Keys.D)){
            rotate(- rotationSpeed*delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.Z) || Gdx.input.isKeyPressed(Input.Keys.A)){
            rotate(rotationSpeed*delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            attack();
        }
    }

    private void baseInit(Texture texture,int maxAmountOfGuns,float health,
                          float rotationSpeed){
        this.maxAmountOfGuns = maxAmountOfGuns;
        this.maxHealth = health;
        this.health = health;
        this.rotationSpeed = rotationSpeed;
        this.positionsForGuns = new Array<>();
        this.initPointsForGuns();
        this.guns = new Array<>();
    }

    private void updateGuns(float delta){
        for (int i = 0; i < Math.min(maxAmountOfGuns,guns.size); i++){
            try{
                Vector2 updatedGunPosition = updatePointsForGuns(positionsForGuns.get(i).x,
                        positionsForGuns.get(i).y);

                guns.get(i).setPosition(getX() + updatedGunPosition.x,
                        getY() + updatedGunPosition.y);
                guns.get(i).update(delta);
            }
            catch (Exception e){
                System.out.println("Error in guns update: "+e.getMessage());
                e.printStackTrace();
            }
        }
    }

    protected void initPointsForGuns(){
    }

    private Vector2 updatePointsForGuns(float positionX,float positionY){
        float r = (Math.abs(getRotation())%90)/90;
        float x = 0;
        float y = 0;
        float rotation = getRotation()%360;
        if (rotation < 0){
            rotation = Math.abs(rotation);
            if(rotation >= 0f && rotation <= 90f){
                x = MathUtils.lerp(positionX,positionY,r);
                y = MathUtils.lerp(positionY,getHeight()-positionX
                        ,r);
            }
            else if (rotation >= 180f && rotation <= 270f){
                x = MathUtils.lerp(getWidth() - positionX,getWidth() - positionY,r);
                y = MathUtils.lerp(getWidth() - positionY,positionX,r);
            }
            else if (rotation > 90 && rotation < 180){

                x = MathUtils.lerp(positionY,
                        getHeight()-positionX,r);
                y = MathUtils.lerp(getHeight()-positionX,
                        getHeight() - positionY,r);

            }
            else if (rotation >270 && rotation < 360){
                x = MathUtils.lerp(getHeight() - positionY,positionX,r);
                y = MathUtils.lerp(positionX,positionY,r);
            }
        }
        else {
            if(rotation >= 0f && rotation <= 90f){
                x = MathUtils.lerp(positionX,getWidth() - positionY,r);
                y = MathUtils.lerp(positionY,positionX,r);
            }
            else if(rotation >= 180f && rotation <=270f){
                x = MathUtils.lerp(getWidth()-positionX,positionY,r);
                y = MathUtils.lerp(getHeight() - positionY,getHeight() - positionX,r);
            }
            else if (rotation > 90 && rotation < 180)
            {
                x = MathUtils.lerp(getWidth() - positionY,
                        getWidth() - positionX,r);
                y = MathUtils.lerp(positionX,getHeight() - positionY,r);
            }
            else if (rotation >270 && rotation < 360){
                x = MathUtils.lerp(positionY,positionX,r);
                y = MathUtils.lerp(getHeight() - positionX,positionY,r);
            }
        }
        return new Vector2(x,y);
    }

    public boolean checkCollision(Enemy asteroid){
        if (Intersector.overlaps(asteroid.getBounds(),getBoundingRectangle())){
            takeDamage(asteroid.size + MathUtils.random(-asteroid.size/3,asteroid.size/3));
            return true;
        }
        return false;
    }

    public boolean checkCollision(Bullet bullet){
        if(Intersector.overlaps(bullet.getBoundingRectangle(),getBoundingRectangle()) && type != bullet.type){
            takeDamage(bullet.getDamage());
            Wave.animations.add(bullet.getAnimationPlayer());
            return true;
        }
        return false;
    }

    public void takeDamage(float damage){
        health -= damage;
    }

    private void renderGuns(SpriteBatch batch){
        for (int i = 0; i < Math.min(maxAmountOfGuns,guns.size);i++){
            try{
                guns.get(i).render(batch);
            }
            catch (Exception e){
                System.out.println("Error in guns render: "+e.getMessage());
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private void attack(){
        for (int i = 0; i < Math.min(maxAmountOfGuns,guns.size);i++){
            try{
                Vector2 updatedGunPosition = updatePointsForGuns(positionsForGuns.get(i).x,
                        positionsForGuns.get(i).y);

                guns.get(i).attack(getX() + updatedGunPosition.x,
                        getY() + updatedGunPosition.y);
            }
            catch (Exception e){
                System.out.println("Error in guns attack: "+e.getMessage());
            }
        }
    }

    public boolean takeBonus(Bonus bonus){
        if(Intersector.overlaps(bonus.getBoundingRectangle(),getBoundingRectangle())){
            bonus.effect(player);
            return true;
        }
        return false;
    }

    public abstract Object clone();

    public float getHealth(){
        return health;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public void setGuns(Array<Gun> guns){
        this.guns = guns;
    }
    public Array<Gun> getGuns(){
        return guns;
    }

    public void setAutoMove(boolean autoMove) {
        this.autoMove = autoMove;
    }

    public void setAutoRotateLeft(boolean autoRotateLeft) {
        this.autoRotateLeft = autoRotateLeft;
    }

    public void setAutoRotateRight(boolean autoRotateRight) {
        this.autoRotateRight = autoRotateRight;
    }

    public boolean isAutoMove() {
        return autoMove;
    }

    public boolean isAutoRotateLeft() {
        return autoRotateLeft;
    }

    public boolean isAutoRotateRight() {
        return autoRotateRight;
    }

    public boolean isHoldAttack() {
        return holdAttack;
    }

    public void addHealth(float bonusHP){
        health += bonusHP;
    }

    public void setHealth(float health) {
        this.health = health;
    }
}
