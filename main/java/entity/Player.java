package entity;

import Controller.GamePanel;
import Controller.KeyHandler;
import View.DrawEntity;
import infoWidget.Hearts;

import javax.imageio.ImageIO;
import java.awt.*;

import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{
    GamePanel gp;

    KeyHandler keyH;
    DrawEntity drawEntity;
    Collision collisionChecker;
    Fight fight;
    Hearts hearts;
    private boolean attackByEnemy = false;
    private String fakeDirectionInAttack;   //TODO better in Entity class
    private int timerToAttackByEnemy = 0;

    public boolean isAttackByEnemy() {
        return attackByEnemy;
    }

    public void setAttackByEnemy(boolean attackByEnemy) {
        this.attackByEnemy = attackByEnemy;
    }

    private int x;
    private int y;
    private boolean isFight;

    public int getTimerToAttackKey() {
        return timerToAttackKey;
    }

    public void setTimerToAttackKey(int timerToAttackKey) {
        this.timerToAttackKey = timerToAttackKey;
    }

    private int timerToAttackKey = 60;


    public Player(GamePanel gp) {
        this.gp = gp;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;

        this.hearts = new Hearts(gp);
        this.collisionChecker = new Collision(gp, this);
        this.drawEntity = new DrawEntity(gp);

        setDefaultValues();
        getPlayerImage();

    }
    public void setKeyHandler(KeyHandler keyHandler) {
        keyH = keyHandler;
    }
    public void setDefaultValues(){ // set player's default position
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";

        actualHearts = new int[4];
        hearts.getLengthOfHeartsAndSetArray(actualHearts.length);
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
            iceAfterHit = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/iceAfterHit/ezgif.com-crop.png")));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void update(){
        timerToAttackKey ++;

        if(attackByEnemy){
            timerToAttackKey ++;
            if(timerToAttackKey == 30){
                attackByEnemy = false;
                timerToAttackKey = 0;
                fakeDirectionInAttack = null;
            }else{
                collision = false;
                collisionChecker.checkMapPosition(this, fakeDirectionInAttack);

                if(!collision){
                    switch (direction){
                        case "up", "neutralUp" -> {
                            y += speed;
                            fakeDirectionInAttack = "down";
                        }
                        case "down", "neutralDown" -> {
                            y -= speed;
                            fakeDirectionInAttack = "up";
                        }
                        case "left", "neutralLeft" -> {
                            x += speed;
                            fakeDirectionInAttack = "right";
                        }
                        case "right", "neutralRight" -> {
                            x -= speed;
                            fakeDirectionInAttack = "left";
                        }
                    }
                }
            }
        }
        else if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed){    // it pretends moving in stay position of character

            if(keyH.upPressed){// move up
                direction = "up";

            }if(keyH.downPressed){ // move down
                direction = "down";

            }if(keyH.leftPressed){ // move left
                direction = "left";

            }if(keyH.rightPressed){    //move right
                direction = "right";
            }

            // collision
            collision = false;
            collisionChecker.checkMapPosition(this, fakeDirectionInAttack);

            if(!collision){
                switch (direction) {
                    case "up" -> y -= speed;
                    case "down" -> y += speed;
                    case "left" -> x -= speed;
                    case "right" -> x += speed;
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
        drawEntity.draw(g2, direction, spriteNum, up1, up2, down1, down2, left1, left2, right1, right2, upNeutral, downNeutral, leftNeutral, rightNeutral, iceAfterHit, isFight, x, y);
        hearts.draw(g2);
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
