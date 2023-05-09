package entity;



import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity{

    public int speed;
    public int[] actualHearts;
    public BufferedImage up1, up2, upNeutral, down1, down2, downNeutral, left1, left2, leftNeutral, right1, right2, rightNeutral, iceAfterHit;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea;
    public boolean collision = false;



}
