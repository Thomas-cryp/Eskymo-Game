package entity;

import Controller.GamePanel;
import Controller.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;

    ArrayList inventory;


    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues(){ // set player's default position
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }
    public void getPlayerImage(){
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
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void update(){
        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed){    // it pretends moving in stay position of character
            if(keyH.upPressed){// move up
                direction = "up";
                y -= speed;

            }if(keyH.downPressed){ // move down
                direction = "down";
                y += speed;
            }if(keyH.leftPressed){ // move left
                direction = "left";
                x -= speed;
            }if(keyH.rightPressed){    //move right
                direction = "right";
                x += speed;
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
        }else{
            switch (direction){
                case "up" -> direction = "neutralUp";
                case "down" -> direction = "neutralDown";
                case "right" -> direction = "neutralRight";
                case "left" -> direction = "neutralLeft";
            }
        }


    }
    public void draw(Graphics g2){
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
            case "neutralUp" -> image = upNeutral;
            case "neutralDown" -> image = downNeutral;
            case "neutralRight" -> image = rightNeutral;
            case "neutralLeft" -> image = leftNeutral;


        }
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);

    }

}
