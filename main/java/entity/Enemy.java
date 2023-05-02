package entity;

import Controller.GamePanel;
import Controller.KeyHandler;
import View.DrawEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;


public class Enemy extends Entity{
    GamePanel gp;
    DrawEntity drawEntity;
    KeyHandler keyH;
    Player player;

    int damage;
    boolean death = false;
    private int x = 400;
    private int y = 400;

    private int defaultX;
    private int defaultY;
    private int playerX, playerY;
    int toleranceForEnemyDefaultBack = 3;
    private boolean isFight;
    private int counterOfFreezeTime = 0;


    public Enemy(GamePanel gp, Player player) {
        this.player = player;
        this.gp = gp;
        this.drawEntity = new DrawEntity(gp);
        this.keyH = player.keyH;
        setDefaultValuesEnemy();
        getEnemyImage();
    }
//    public void setKeyHandler(KeyHandler keyHandler) {
//        kh = keyHandler;
//    }

    public void getEnemyImage(){
        try{
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/eskimo/eskimo_up_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/eskimo/eskimo_up_2.png")));
            upNeutral = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/eskimo/eskimo_neutral_up.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/eskimo/eskimo_down_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/eskimo/eskimo_down_2.png")));
            downNeutral = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/eskimo/eskimo_neutral.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/eskimo/eskimo_left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/eskimo/eskimo_left_2.png")));
            leftNeutral = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/eskimo/eskimo_neutral_left.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/eskimo/eskimo_right_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/eskimo/eskimo_right_2.png")));
            rightNeutral = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/eskimo/eskimo_neutral_right.png")));
            iceAfterHit = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/iceAfterHit/ezgif.com-crop.png")));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void setDefaultValuesEnemy(){
        speed = 2;
        direction = "neutralDown";
    }

    public void setPlayerPosition(){
        playerX = player.getX();
        playerY = player.getY();

    }
    public void setPositionEnemy(int newX, int newY){
        x = newX;
        y = newY;
        defaultX = newX;
        defaultY = newY;
    }

    public double calculateHypotenuse(){
        setPlayerPosition();
        double distancePlayerAndEnemy;
        double b = x - playerX;
        double a = y - playerY;
        distancePlayerAndEnemy = Math.sqrt(b * b + a * a);
        return distancePlayerAndEnemy;

    }
//    public int addDamage(){ TODO
//        return 0;
//    }
    public boolean checkFightKeyPressed(){
        if(keyH.fight){
            return true;
        }else{
            return false;
        }
    }
    public boolean checkIfFightIsPossible(){
        if(calculateHypotenuse() < 30){
            return true;
        }else{
            return false;
        }
    }

    public void update(){
        if(checkFightKeyPressed() && checkIfFightIsPossible()){
        isFight = true;
        }
        if(isFight == true){
            counterOfFreezeTime ++;
            if(counterOfFreezeTime == 30){
                isFight = false;
                counterOfFreezeTime = 0;
            }

        } else if (calculateHypotenuse() < 150){
            setPlayerPosition();
            double dx = playerX - x;
            double dy = playerY - y;
            double angle = Math.atan2(dy, dx);

            x += speed * Math.cos(angle);
            y += speed * Math.sin(angle);
            updateImageMoving(dx, dy);
        } else if (Math.abs(x - defaultX) <= toleranceForEnemyDefaultBack && Math.abs(y - defaultY) <= toleranceForEnemyDefaultBack) {
            updateEnemyImageOnDefaultValues();
        }else if (x != defaultX || y != defaultY) {

            double dx = defaultX - x;
            double dy = defaultY - y;
            double angle = Math.atan2(dy, dx);

            x += speed * Math.cos(angle);
            y += speed * Math.sin(angle);
            updateImageMoving(dx, dy);
        }
        else{
            updateImageStanding();
        }
    }

    public void updateImageMoving(double dx, double dy){

        if (Math.abs(dx) > Math.abs(dy)) {
            // Moving horizontally
            if (dx > 0) {
                direction = "right";
            } else {
                direction = "left";
            }
        } else {
            // Moving vertically
            if (dy > 0) {
                direction = "down";
            } else {
                direction = "up";
            }
        }
        spriteCounter++;    // 60 times par second is called this method. Every 13 frames will be changed the picture
        if(spriteCounter > 13){
            if(spriteNum == 1){
                spriteNum = 2;
            }
            else if(spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }
    public void updateEnemyImageOnDefaultValues(){
        switch (direction){
            case "up" -> direction = "neutralDown";
            case "down" -> direction = "neutralDown";
            case "right" -> direction = "neutralDown";
            case "left" -> direction = "neutralDown";
        }
    }
    public void updateImageStanding(){
        switch (direction){
            case "up" -> direction = "neutralUp";
            case "down" -> direction = "neutralDown";
            case "right" -> direction = "neutralRight";
            case "left" -> direction = "neutralLeft";
        }
    }

    public void draw(Graphics g2) {
        drawEntity.draw(g2, direction, spriteNum, up1, up2, down1, down2, left1, left2, right1, right2, upNeutral, downNeutral, leftNeutral, rightNeutral, iceAfterHit, isFight, x, y);
    }
}
