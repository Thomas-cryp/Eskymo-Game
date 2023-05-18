package entity;

import Controller.GamePanel;
import Controller.KeyHandler;
import Controller.Threat;
import View.DrawBoss;
import View.DrawEntity;
import View.DrawLiveBar;

import infoWidget.Weapons;

import javax.imageio.ImageIO;

import java.awt.*;
import java.io.IOException;

import java.util.Objects;


public class Enemy extends Entity{
    GamePanel gp;
    DrawEntity drawEntity;
    KeyHandler keyH;
    Player player;
    Fight fight;
    Weapons weapons;
    DrawLiveBar drawLiveBar;
    DrawBoss drawBoss;
    Threat threat;
    private int damage = 0;

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    private boolean death = false;

    public boolean isDeath() {
        return death;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private int x = 400;
    private int y = 400;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    private boolean drawing = true;

    public void setdrawing(boolean drawing) {
        this.drawing = drawing;
    }



    public void setDefaultX(int defaultX) {
        this.defaultX = defaultX;
    }


    public void setDefaultY(int defaultY) {
        this.defaultY = defaultY;
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
        this.drawBoss = new DrawBoss(gp);
        this.threat = gp.getThreat();

        if(gp.isBoss()){
            drawBoss.setBossPositionAndSize(this);
        }
        setDefaultValuesEnemy();
        getEnemyImage();

    }

    public void getEnemyImage(){
        try{
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/cyclope/up1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/cyclope/up2.png")));
            upNeutral = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/cyclope/upNeutral.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/cyclope/down1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/cyclope/down2.png")));
            downNeutral = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/cyclope/downNeutral.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/cyclope/left1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/cyclope/left2.png")));
            leftNeutral = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/cyclope/leftNeutral.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/cyclope/right1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/cyclope/right2.png")));
            rightNeutral = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/cyclope/rightNeutral.png")));
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

        if(!gp.isBoss()){
            if(damage == 1){
//            JOptionPane.showMessageDialog(null, "Death of first enemy");
                death = true;
                if(gp.checkIfIsTimeForBoss()){
                    gp.setBoss(true);
                    gp.createBoss();
                }
            }
        }else{

            if(damage == 2){
                death = true;
                gp.setBoss(false);
                threat.setStopGameLoop(true);
                gp.setGameState(gp.getNextLevelPage());
            }
        }


        if(!death) {
            if (checkFightKeyPressed()) {
                keyH.fight = false;
                if (timerToAttackKey() && !isFight) {
                    if(weapons.isSword()){
                        fight.swordFight();
                    } else if (weapons.isBow()) {
                        fight.bowFight();
                    }else{
                        fight.trapsFight();
                    }
                }
            } else if (calculateHypotenuse() < 30 && timerAfterAttackPlayerByEnemy()) {
                player.setAttackByEnemy(true);
                hittingByEnemy = true;
                if(gp.isBoss()){
                    player.callHeartsClassAndDecreaseNumberOfHearts();
                }
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
            } else if (calculateHypotenuse() < 220) {
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
        if(drawing && !gp.isBoss()){
            drawEntity.draw(g2, direction, spriteNum, up1, up2, down1, down2, left1, left2, right1, right2, upNeutral, downNeutral, leftNeutral, rightNeutral, iceAfterHit, isFight, death, x, y);
            if(death){
                if(!player.checkCollisionWithHearts(this)){
                    drawLiveBar.drawHeartAfterDeathOFEnemy(g2, this);
                }
            }
        }if(gp.isBoss()){
            drawBoss.draw(g2, this);
        }
    }
}
