package Model.tile;

import Controller.GamePanel;
import Controller.KeyHandler;
import infoWidget.Coins;
import infoWidget.Weapons;

public class Chest extends Weapons {
    GamePanel gp;
    Igloo igloo;

    KeyHandler kh;
    Coins coins;

    public Chest(GamePanel gp, KeyHandler keyH) {
        super(gp, keyH);
    }

    public void openChest(){}

    public void checkPrice(){}
}
