package infoWidget;

import Controller.GamePanel;
import Controller.KeyHandler;

public class Inventory extends Items{
    GamePanel gp;
    KeyHandler kh;
    Weapons weapons;
    Coins coins;
    Hearts hearts;

    public Inventory(GamePanel gp, KeyHandler keyH) {
        super(gp, keyH);
    }

    public void openInventory(){}
    public void showInventoryItems(){}

    public void draw(){}
}
