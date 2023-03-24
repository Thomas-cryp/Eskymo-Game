package entity;

import java.awt.image.BufferedImage;

public class Entity {
    public int x,y;
    public int speed;
    public BufferedImage up1, up2, upNeutral, down1, down2, downNeutral, left1, left2, leftNeutral, right1, right2, rightNeutral;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

}
