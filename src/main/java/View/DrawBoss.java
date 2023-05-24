package View;

import Controller.GamePanel;
import Controller.Enemy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class DrawBoss {
    GamePanel gp;
    BufferedImage boss1, boss2, boss3, boss4, bossNeutral;
    private int spriteNumber = 0;
    private final int bossWidth = 80;
    private final int bossHeight = 80;
    private BufferedImage image;
    public DrawBoss(GamePanel gp) {
        this.gp = gp;
        loadBossImages();
    }
    public void setBossPositionAndSize(Enemy enemy){
        int x = gp.getScreenWidth() - (gp.getTileSize() * 2) - bossWidth;
        int y = (gp.getScreenHeight() - bossHeight)/2;
        enemy.setX(x);
        enemy.setY(y);
        enemy.setDefaultX(x);
        enemy.setDefaultY(y);
    }
    private void loadBossImages(){
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
    /**
     * @param g2 is the graphics object
     * @param enemy is the enemy object
     * This method draws the boss pictures and change it for moving effect
     */
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
