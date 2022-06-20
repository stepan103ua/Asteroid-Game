package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DataProvider {

    public static void save(Player player){

        try {
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(new FileOutputStream(new File(Gdx.files.getLocalStoragePath()+"/data.bin")));
            objectOutputStream.writeObject(player);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Player loadPlayer(){

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File(Gdx.files.getLocalStoragePath()+"/data.bin")));
            Player loadedPlayer = (Player) objectInputStream.readObject();
            System.out.println(loadedPlayer.name);
            return loadedPlayer;
        } catch (IOException e) {
            System.out.println("File not found, created  new Player");
            System.out.println(e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            System.out.println("Not class found,new player created!");
            System.out.println(e.getMessage());
            return null;
        }
    }


    public static Array<ShopItem> allGuns(){
        Array<ShopItem> guns = new Array<>();

        guns.add(new ShopItem("Laser gun R0",25));
        guns.add(new ShopItem("Laser gun R1",75));
        guns.add(new ShopItem("Rocket gun",150));

        return guns;
    }

    public static Array<ShopItem> allShips(){
        Array<ShopItem> ships = new Array<>();

        ships.add(new ShopItem("Red ship",50));
        ships.add(new ShopItem("Green ship", 200));

        return ships;
    }
}
