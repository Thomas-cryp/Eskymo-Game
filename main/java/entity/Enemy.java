package entity;

import Controller.GamePanel;
import Controller.KeyHandler;
import View.DrawEntity;
import View.DrawLiveBar;
import View.UI;
import infoWidget.Weapons;

import javax.imageio.ImageIO;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Objects;


public class Enemy extends Entity{
    GamePanel gp;
    DrawEntity drawEntity;
    KeyHandler keyH;
    Player player;
    Fight fight;
    Weapons weapons;
    DrawLiveBar drawLiveBar;

    private int damage = 0;

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    private boolean death = false;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private int x = 400;
    private int y = 400;
    private boolean drawing = true;

    public void setdrawing(boolean drawing) {
        this.drawing = drawing;
    }

    private int defaultX;
    private int defaultY;
    private int playerX, playerY;
    int toleranceForEnemyDefaultBack = 3;
    private boolean isFight;

    public void setFight(boolean fight) {
        isFight = fight;
    }

    private int counterOfFreezeTime = 0;
    private boolean hittingByEnemy = false;

    private int timerForStandingAfterHitPlayer;
    private int counterOfSlowestFrames = 30;


    public Enemy(GamePanel gp, Player player) {
        this.player = player;
        this.gp = gp;
        this.drawEntity = new DrawEntity(gp);
        this.keyH = player.keyH;
        this.fight = new Fight(gp, player, this);
        this.weapons = player.getWeapons();
        this.drawLiveBar = new DrawLiveBar(gp);
        setDefaultValuesEnemy();
        getEnemyImage();
    }

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
            iceAfterHit = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/iceAfterHit/iceAfterHitPicture.png")));
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
    public double calculateHypotenuseForTrap(int xWeapon, int yWeapon){
        double distancePlayerAndEnemy;
        double b = x - xWeapon;
        double a = y - yWeapon;
        distancePlayerAndEnemy = Math.sqrt(b * b + a * a);
        return distancePlayerAndEnemy;
    }

    public boolean timerToAttackKey(){
        if(player.getTimerToAttackKey() >= 60){
            player.setTimerToAttackKey(0);
            return true;
        }else{
            return false;
        }

    }

//    public int addDamage(){ TODO
//        return 0;
//    }
    public boolean checkFightKeyPressed(){
        return keyH.fight;
    }
    public boolean timerAfterAttackPlayerByEnemy(){
        counterOfSlowestFrames ++;
        if(counterOfSlowestFrames >= 30){
            counterOfSlowestFrames = 0;
            return true;
        }else {
            return false;
        }
    }

    public void update(){

        if(damage == 3){
//            JOptionPane.showMessageDialog(null, "Death of first enemy");
            death = true;

        }

        if(!death) {
            if (calculateHypotenuse() < 300 && checkFightKeyPressed()) {
                keyH.fight = false;
                if (timerToAttackKey() && !isFight) {
                    if(weapons.isSword()){
                        fight.swordFight(this);
                    } else if (weapons.isBow()) {
                        fight.bowFight();
                    }else{
                        System.out.println(weapons.isTraps());
                        fight.trapsFight();
                    }
                }
            } else if (calculateHypotenuse() < 20 && timerAfterAttackPlayerByEnemy()) {
                player.setAttackByEnemy(true);
                hittingByEnemy = true;
                player.callHeartsClassAndDecreaseNumberOfHearts();
            }


            if (isFight) {
                counterOfFreezeTime++;
                if (counterOfFreezeTime >= 120) {

                    isFight = false;
                    counterOfFreezeTime = 0;
                }
            } else if (hittingByEnemy) {
                if (timerForStandingAfterHitPlayer == 30) {
                    hittingByEnemy = false;
                    timerForStandingAfterHitPlayer = 0;
                }
                timerForStandingAfterHitPlayer++;
                updateImageStanding();
            } else if (calculateHypotenuse() < 150) {
                setPlayerPosition();
                moveShortestPath(playerX, playerY);

            } else if (Math.abs(x - defaultX) <= toleranceForEnemyDefaultBack && Math.abs(y - defaultY) <= toleranceForEnemyDefaultBack) {
                updateEnemyImageOnDefaultValues();

            } else if (x != defaultX || y != defaultY) {
                moveShortestPath(defaultX, defaultY);
            } else {
                updateImageStanding();
            }
        }else{
            direction = "death";
        }

    }
    public void moveShortestPath(int targetX, int targetY){
        double dx = targetX - x;
        double dy = targetY - y;
        double angle = Math.atan2(dy, dx);

        x += speed * Math.cos(angle);
        y += speed * Math.sin(angle);
        updateImageMoving(dx, dy);
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
            case "up", "down", "right", "left" -> direction = "neutralDown";
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
        if(drawing){
            drawEntity.draw(g2, direction, spriteNum, up1, up2, down1, down2, left1, left2, right1, right2, upNeutral, downNeutral, leftNeutral, rightNeutral, iceAfterHit, isFight, death, x, y);
            if(death){
                if(!player.checkCollisionWithHearts(this)){
                    drawLiveBar.drawHeartAfterDeathOFEnemy(g2, this);
                }
            }
        }
    }
}
