package Model.tile;

import Controller.GamePanel;

import java.awt.image.BufferedImage;

public class Tile extends GamePanel {
    GamePanel gp;
    public BufferedImage image;
    public boolean collision;
    {
        collision = false;
    }
}
