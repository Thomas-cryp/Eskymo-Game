package infoWidget;

import Controller.GamePanel;
import Controller.KeyHandler;
import entity.Player;

public class InfoBar extends Player{
    Coins coins;
    Hearts hearts;
    int heartsStatus;
    int coinsStatus;

    public InfoBar(GamePanel gp, KeyHandler keyH) {
        super(gp, keyH);
    }


    public void update(){}
    public void addHearts(){}
    public void addCoins(){}
}
