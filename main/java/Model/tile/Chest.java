package Model.tile;

import Controller.GamePanel;
import Controller.KeyHandler;
import infoWidget.Coins;
import infoWidget.Weapons;

import java.util.ArrayList;

public class Chest extends Weapons {
    GamePanel gp;
    Igloo igloo;

    KeyHandler keyH;
    Coins coins;

   private ArrayList weaponsList;

    public ArrayList getWeaponsList() {
        return weaponsList;
    }

    public Chest(GamePanel gp) {
        super(gp);
    }

    public void openChest(){}

    public void importWeapons(){}
    public boolean checkPrice(){
        return false;
    }
    public void addWeaponToInventory(){}
}
