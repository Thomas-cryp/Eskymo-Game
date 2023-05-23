package View;

import Controller.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawEntity {
    GamePanel gp;

    public DrawEntity(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * This method draws the entity on the screen. it takes the direction, sprite number, and the images of the entity
     * @param g2 - the graphics object
     * @param direction - the direction of the entity
     * @param spriteNum - the number of the sprite for the moving effect
     * @param up1 - the images of the entity when it is moving to the up1
     * @param up2 - the images of the entity when it is moving to the up2
     * @param down1 - the images of the entity when it is moving to the down1
     * @param down2 - the images of the entity when it is moving to the down2
     * @param left1 - the images of the entity when it is moving to the left1
     * @param left2 - the images of the entity when it is moving to the left2
     * @param right1 - the images of the entity when it is moving to the right1
     * @param right2 - the images of the entity when it is moving to the right2
     * @param upNeutral - the image of the entity when it is not moving to the up
     * @param downNeutral - the image of the entity when it is not moving to the down
     * @param leftNeutral - the image of the entity when it is not moving to the left
     * @param rightNeutral - the image of the entity when it is not moving to the right
     * @param iceAfterHit - the image of the entity after it gets hit
     * @param isFight - boolean to check if the entity is fighting
     * @param death - boolean to check if the entity is dead
     * @param x - the x and y coordinates of the entity
     * @param y - the x and y coordinates of the entity
     */
    public void draw(Graphics g2, String direction, int spriteNum, BufferedImage up1, BufferedImage up2, BufferedImage down1, BufferedImage down2, BufferedImage left1, BufferedImage left2, BufferedImage right1, BufferedImage right2, BufferedImage upNeutral, BufferedImage downNeutral, BufferedImage leftNeutral, BufferedImage rightNeutral, BufferedImage iceAfterHit, Boolean isFight, Boolean death, int x, int y){

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
            case "death" -> image = iceAfterHit;
            case "neutralUp" -> image = upNeutral;
            case "neutralDown" -> image = downNeutral;
            case "neutralRight" -> image = rightNeutral;
            case "neutralLeft" -> image = leftNeutral;

        }


            g2.drawImage(image, x, y, gp.getTileSize(), gp.getTileSize(), null);
            if(isFight && !death){
                g2.drawImage(iceAfterHit, x, y, gp.getTileSize(), gp.getTileSize(), null);
            }
    }
}
