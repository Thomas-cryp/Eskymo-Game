package infoWidget;



import Controller.GamePanel;
import Controller.KeyHandler;
import entity.Player;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class Items extends Player {
    String name;
    int positionOfDrops;
    public BufferedImage picture;


    public Items(GamePanel gp, KeyHandler keyH) {
        super(gp, keyH);
    }
}