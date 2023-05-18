package entity;

import Controller.GamePanel;
import View.DrawBoss;
import View.DrawEntity;
import View.DrawLiveBar;
import infoWidget.Weapons;

public class Boss extends Entity{
    GamePanel gp;
    Player player;
    Fight fight;
    Weapons weapons;
    DrawLiveBar drawLiveBar;
    DrawBoss drawBoss;

    public Boss(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;
        this.drawBoss = new DrawBoss(gp);
        this.weapons = player.getWeapons();
    }
}
