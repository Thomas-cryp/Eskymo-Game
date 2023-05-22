package View;

import Controller.GamePanel;
import Controller.Enemy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class DrawLiveBar {
    GamePanel gp;
    private BufferedImage full, half, empty;

    public DrawLiveBar(GamePanel gp) {
        this.gp = gp;
        setImageFromResources();
    }
    private void setImageFromResources(){
        try{
            full = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/liveBar/fullHeart.png")));
            half = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/liveBar/halfHeart.png")));
            empty = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/liveBar/emptyHeart.png")));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    /**
     * draw heart after death of enemy on same place where the enemy was death
     * @param g2 - graphics
     * @param enemy - enemy
     */
    public void drawHeartAfterDeathOFEnemy(Graphics g2, Enemy enemy){
        g2.drawImage(half, enemy.getX(), enemy.getY(), gp.getTileSize(), gp.getTileSize(), null);
    }

    /**
     * draw live bar at the top of the screen. it will read from array of hearts
     * @param g2 - graphics
     * @param actualArrayOfHearts - array of hearts for different type of hearts
     */
    public void draw(Graphics g2, int[] actualArrayOfHearts){
        BufferedImage image;
        int x = 0;
        int y = 0;

        for (int i:
             actualArrayOfHearts) {
            if(i == 2){
                image = full;
                g2.drawImage(image, x, y, gp.getTileSize(), gp.getTileSize(), null);
                x += gp.getTileSize();
            } else if ( i == 1){
                image = half;
                g2.drawImage(image, x, y, gp.getTileSize(), gp.getTileSize(), null);
                x += gp.getTileSize();
            }else{
                image = empty;
                g2.drawImage(image, x, y, gp.getTileSize(), gp.getTileSize(), null);
                x += gp.getTileSize();
            }
        }
    }
}
