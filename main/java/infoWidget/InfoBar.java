package infoWidget;

import Controller.GamePanel;
import Controller.KeyHandler;
import entity.Player;

public class InfoBar extends Player{
    Coins coins;
    Hearts hearts;
    int heartsStatus;
    int coinsStatus;

    GamePanel gp;
    KeyHandler keyH;

    public InfoBar(GamePanel gp) {
        super(gp);
    }


    public void update(){}
    public void addHearts(){}
    public void addCoins(){}
}
