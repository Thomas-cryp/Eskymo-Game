package infoWidget;

import Controller.GamePanel;
import Model.KeyHandler;
import Controller.Weapons;

public class Inventory extends Items{
    GamePanel gp;
    KeyHandler kh;
    Weapons weapons;
    Coins coins;
    Hearts hearts;


    public Inventory(GamePanel gp) {
        super(gp);


    }

    public void openInventory(){}
    public void showInventoryItems(){}

    public void draw(){}
}
