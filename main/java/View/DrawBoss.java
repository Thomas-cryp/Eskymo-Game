package View;

import Controller.GamePanel;
import entity.Enemy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class DrawBoss {
    GamePanel gp;
    private BufferedImage boss1, boss2, boss3, boss4, bossNeutral;
    private int spriteNumber = 0;
    private int bossWidth = 80;
    private int bossHeight = 80;
    private BufferedImage image;
    public DrawBoss(GamePanel gp) {
        this.gp = gp;
        loadBossImages();
    }
    public void setBossPositionAndSize(Enemy enemy){
        int x = gp.screenWidth - (gp.tileSize * 2) - bossWidth;
        int y = (gp.screenHeight - bossHeight)/2;
        enemy.setX(x);
        enemy.setY(y);
        enemy.setDefaultX(x);
        enemy.setDefaultY(y);
    }
    public void loadBossImages(){
        try {
            bossNeutral = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/boss/bossNeutral.png")));
            boss1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/boss/boss1.png")));
            boss2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/boss/boss2.png")));
            boss3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/boss/boss3.png")));
            boss4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/boss/boss4.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void draw(Graphics g2, Enemy enemy){
        int x = enemy.getX();
        int y = enemy.getY();
        spriteNumber ++;
        switch (spriteNumber){
            case 1 -> image = bossNeutral;
            case 5 -> image = boss1;
            case 10 -> {
                image = boss4;
                spriteNumber = 0;
            }
        }
        g2.drawImage(image, x, y, bossWidth, bossHeight, null);
    }


}
