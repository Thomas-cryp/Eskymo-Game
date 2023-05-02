package infoWidget;

import Controller.GamePanel;
import Controller.KeyHandler;

import java.util.ArrayList;

public class Weapons extends Items{
    ArrayList weapons;
    int damage;
    String type;
    GamePanel gp;
    KeyHandler keyH;

    public Weapons(GamePanel gp) {

        super(gp);
    }
    public int getActualWeapon(){
        return 0;
    }
    public void loadWeaponsFromResources(){}
}
