package infoWidget;



import Controller.GamePanel;
import Model.KeyHandler;
import entity.Player;

import java.awt.image.BufferedImage;

public class Items extends Player {
    String name;
    int positionOfDrops;
    public BufferedImage picture;
    GamePanel gp;
    KeyHandler keyH;


    public Items(GamePanel gp) {
        super(gp);
    }
}
