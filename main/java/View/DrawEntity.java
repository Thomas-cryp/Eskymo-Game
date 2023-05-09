package View;

import Controller.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawEntity {
    GamePanel gp;

    public DrawEntity(GamePanel gp) {
        this.gp = gp;
    }

    public void draw(Graphics g2, String direction, int spriteNum, BufferedImage up1, BufferedImage up2, BufferedImage down1, BufferedImage down2, BufferedImage left1, BufferedImage left2, BufferedImage right1, BufferedImage right2, BufferedImage upNeutral, BufferedImage downNeutral, BufferedImage leftNeutral, BufferedImage rightNeutral, BufferedImage iceAfterHit, Boolean isFight, int x, int y){

        BufferedImage image = null;
            switch (direction) {
            case "up" -> {
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
            }
            case "down" -> {
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
            }
            case "left" -> {
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
            }
            case "right" -> {
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
            }
            case "death" -> {
                image = iceAfterHit;
            }
            case "neutralUp" -> image = upNeutral;
            case "neutralDown" -> image = downNeutral;
            case "neutralRight" -> image = rightNeutral;
            case "neutralLeft" -> image = leftNeutral;

        }


            g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
            if(isFight){
                g2.drawImage(iceAfterHit, x, y, gp.tileSize, gp.tileSize, null);
            }
    }
}
