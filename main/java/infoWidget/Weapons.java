package infoWidget;

import Controller.GamePanel;
import Controller.KeyHandler;

import java.util.ArrayList;

public class Weapons extends Items{
    ArrayList weapons;
    int damage;
    String type;

    public Weapons(GamePanel gp, KeyHandler keyH) {
        super(gp, keyH);
    }
    public int getActualWeapon(){
        return 0;
    }
    public void loadWeaponsFromResources(){}
}
