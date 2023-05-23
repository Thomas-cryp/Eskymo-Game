package Controller;

import Model.Fight;
import Model.KeyHandler;
import Model.Moving;
import Model.Threat;
import View.DrawBoss;
import View.DrawEntity;
import View.DrawLiveBar;

import javax.imageio.ImageIO;

import java.awt.*;
import java.io.IOException;

import java.util.Objects;


public class Enemy extends Entity {
    GamePanel gp;
    DrawEntity drawEntity;
    KeyHandler keyH;
    Player player;
    Fight fight;
    Weapons weapons;
    DrawLiveBar drawLiveBar;
    DrawBoss drawBoss;
    Threat threat;
    Loader loader;
    Moving moving;
    private int damage = 0;
    private boolean death = false;
    private int x = 400;
    private int y = 400;
    private boolean drawing = false;
    private int defaultX;
    private int defaultY;
    private int playerX, playerY;
    int toleranceForEnemyDefaultBack = 3;
    private boolean isFight;
    private int counterOfFreezeTime = 0;
    private boolean hittingByEnemy = false;
    private int timerForStandingAfterHitPlayer;
    private String direction;
    private int spriteNum = 1;
    private int spriteCounter = 0;
    private int counterForLogger;
    private int distanceForClosingEnemyToPlayer;



    public Enemy(GamePanel gp, Player player) {
        this.player = player;
        this.gp = gp;
        this.drawEntity = new DrawEntity(gp);
        this.keyH = player.getKeyH();
        this.fight = new Fight(gp, player, this);
        this.weapons = player.getWeapons();
        this.drawLiveBar = new DrawLiveBar(gp);
        this.drawBoss = new DrawBoss(gp);
        this.threat = gp.getThreat();
        this.loader = gp.getLoader();
        this.moving = new Moving(gp, this);

        if(gp.isBoss()){
            drawBoss.setBossPositionAndSize(this);
        }
        setDefaultValuesEnemy();
        getEnemyImage();

    }

    private void getEnemyImage(){
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

    private void setDefaultValuesEnemy(){
        if(gp.isBoss()) {
            distanceForClosingEnemyToPlayer = 700;
        }else{
            distanceForClosingEnemyToPlayer = 220;
        }
        speed = 2;
        direction = "neutralDown";
        counterForLogger = 1;
    }

    private void setPlayerPosition(){
        playerX = player.getX();
        playerY = player.getY();
    }
    /**
     * set the position of the enemy on the X and Y axis. It also set default position of the enemy, which is used when the enemy has to go back to spawn place.
     * @param newX - new position of the enemy on the X axis.
     * @param newY - new position of the enemy on the Y axis.
     */
    public void setPositionEnemy(int newX, int newY){
        x = newX;
        y = newY;
        defaultX = newX;
        defaultY = newY;
    }

    private boolean checkFightKeyPressed(){
        return keyH.fight;
    }

    private void writeLoggerInfo(String text){
        if(counterForLogger == 1){
            gp.getLogWriter().println(text);
            gp.getLogWriter().flush();
            counterForLogger = 0;
        }
    }
    private void loaderInfoAfterDeathOfBoss(){
        if(loader.getLevelFromJson() == 2){
            gp.setEndOfGame(true);
        }else{
            loader.updateLevelInJsonLoader((loader.getLevelFromJson() + 1));
            gp.setGameState(gp.getNextLevelPage());
        }
    }
    private void checkerIfIsAttackByEnemyOrToEnemy(){
        if(checkFightKeyPressed()) {
            keyH.fight = false;
            if (fight.timerToAttackKey() && !isFight) {
                fight.setWeaponsToFightState();
            }
        }else if (fight.calculateHypotenuse() < 30 && fight.timerAfterAttackPlayerByEnemy()) {

            hittingByEnemy = true;
            if(gp.isBoss()){
                player.callHeartsClassAndDecreaseNumberOfHearts();
            }
            player.callHeartsClassAndDecreaseNumberOfHearts();
        }
    }

    private void updateMovingEnemy(){
        if (isFight) {
            counterOfFreezeTime++;
            if (counterOfFreezeTime >= 120) {

                isFight = false;
                counterOfFreezeTime = 0;
            }
        } else if (hittingByEnemy) {
            if (timerForStandingAfterHitPlayer == 40) {
                hittingByEnemy = false;
                timerForStandingAfterHitPlayer = 0;
            }
            player.setAttackByEnemy(true, this);
            timerForStandingAfterHitPlayer++;
            moving.updateImageStanding();
        } else if (fight.calculateHypotenuse() < distanceForClosingEnemyToPlayer) {
            setPlayerPosition();
            moving.moveShortestPath(playerX, playerY, speed);

        } else if (Math.abs(x - defaultX) <= toleranceForEnemyDefaultBack && Math.abs(y - defaultY) <= toleranceForEnemyDefaultBack) {
            moving.updateEnemyImageOnDefaultValues();

        } else if (x != defaultX || y != defaultY) {
            moving.moveShortestPath(defaultX, defaultY, speed);
        } else {
            moving.updateImageStanding();
        }
    }
    private void checkerOnBossAndDamage(){
        if(!gp.isBoss()){
            if(damage == 1){
                if(!death){
                    drawing = true;
                }
                death = true;
                writeLoggerInfo("Death of Enemy");
                if(gp.checkIfIsTimeForBoss()){
                    gp.setBoss(true);
                    gp.createBoss();
                }
            }
        }else{
            if(damage == 3){
                death = true;
                writeLoggerInfo("Death of Boss");
                loaderInfoAfterDeathOfBoss();
                gp.setBossEnemyOnNull();
                threat.setStopGameLoop(true);

            }
        }
    }
    /**
     * update the enemy. It checks if the enemy is fight with the player, if the enemy is hitting the player, if the enemy is on the default position, if the enemy is on the boss level and if the enemy is death.
     */
    public void update(){
        checkerOnBossAndDamage();
        if(!death) {
            checkerIfIsAttackByEnemyOrToEnemy();
            updateMovingEnemy();
        }else{
            direction = "death";
        }
    }

    /**
     * draw the enemy. It checks if the enemy is fight with the player, if the enemy is hitting the player, if the enemy is on the default position, if the enemy is on the boss level and if the enemy is death.
     * @param g2 - Graphics2D
     */
    public void draw(Graphics g2) {

        if(!gp.isBoss() && !drawing) {
            drawEntity.draw(g2, direction, spriteNum, up1, up2, down1, down2, left1, left2, right1, right2, upNeutral, downNeutral, leftNeutral, rightNeutral, iceAfterHit, isFight, death, x, y);
        }
        if(gp.isBoss()){
            drawBoss.draw(g2, this);
        }
    }
    /**
     * draw the heart after death of the enemy. It is separate for possible to call it if enemy is death.
     * @param g2 - Graphics2D
     */
    public void drawHeartAfterDeathOFEnemy(Graphics g2){
        if(drawing){
            if(!player.checkCollisionWithHearts(this)){
                drawLiveBar.drawHeartAfterDeathOFEnemy(g2, this);
            }else{
                drawing = false;
            }
        }
    }





    public void setFight(boolean fight) {
        isFight = fight;
    }
    public int getSpriteNum() {
        return spriteNum;
    }
    public void setSpriteNum(int spriteNum) {
        this.spriteNum = spriteNum;
    }
    public int getSpriteCounter() {
        return spriteCounter;
    }
    public void setSpriteCounter(int spriteCounter) {
        this.spriteCounter = spriteCounter;
    }
    public String getDirection() {
        return direction;
    }
    public void setDirection(String direction) {
        this.direction = direction;
    }
    public int getDamage() {
        return damage;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
    public boolean isDeath() {
        return death;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setDeath(boolean death) {
        this.death = death;
    }
    public void setDefaultX(int defaultX) {
        this.defaultX = defaultX;
    }
    public void setDefaultY(int defaultY) {
        this.defaultY = defaultY;
    }
}
