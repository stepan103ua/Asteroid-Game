package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.guns.Gun;
import com.mygdx.game.ships.BasicShipG_1;
import com.mygdx.game.ships.Ship;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Vector;

public class Player implements Serializable {
    private Vector<String> purchasedShips;
    private Vector<String> purchasedGuns;
    public transient int currentScore = 0;
    public int maxScore = 0;
    private int money = 3000;
    public transient Array<Gun> currentGuns;
    public transient Ship currentShip;
    public String name;
    public Player(){
        purchasedShips = new Vector<>();
        purchasedGuns = new Vector<>();
        currentGuns = new Array<>();
        currentShip = new BasicShipG_1();
        currentShip.type = "player";
        name = + MathUtils.random(0,100) + " : name";
        purchasedGuns.add("Laser gun R1");
        purchasedShips.add("Red ship");

    }

    public int getMoney() {
        return money;
    }

    public void addMoney(int addition){
        money += addition;
    }

    public void minusMoney(int minus){
        money -= minus;
    }

    public Vector<String> getPurchasedShips() {
        return purchasedShips;
    }

    public HashSet<String> getUniquePurchasedShips(){
        HashSet<String> set = new HashSet<>();
        for(String gun : purchasedShips){
            set.add(gun);
        }
        return set;
    }

    public Vector<String> getPurchasedGuns() {
        return purchasedGuns;
    }

    public HashSet<String> getUniquePurchasedGuns(){
        HashSet<String> set = new HashSet<>();
        for(String gun : purchasedGuns){
            set.add(gun);
        }
        return set;
    }

    public void dispose(){
        this.currentShip.dispose();
    }


    public void update(float delta){
        currentShip.update(delta);
        if(currentScore < 0) currentScore = 0;

    }

    public void render(SpriteBatch batch){
        currentShip.render(batch);

    }

}
