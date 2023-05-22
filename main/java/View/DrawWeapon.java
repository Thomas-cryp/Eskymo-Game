package View;

import Controller.GamePanel;
import Model.BowAndArrow;
import Model.Traps;
import Controller.Enemy;

import Controller.Player;
import Controller.Weapons;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class DrawWeapon {

    GamePanel gp;
    Weapons weapons;
    Player player;
    Enemy bossEnemy;
    Traps traps;

    public Enemy getBossEnemy() {
        return bossEnemy;
    }



    BowAndArrow bowAndArrow;

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    private int startX, startY;

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }
    int counterForSword = 0;

    private int playerX, playerY;
    private int xSword, ySword;
    private int widthSword, heightSword;
    private BufferedImage sword, swordUp, swordRight, swordDown, swordLeft;


    public DrawWeapon(GamePanel gp) {
        this.gp = gp;
        loadImageFormResourcesArrowAndBow();

    }

    private void loadImageFormResourcesArrowAndBow() {
        try {

            swordUp = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapons/sword_up.png")));
            swordDown = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapons/sword_down.png")));
            swordLeft = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapons/sword_left.png")));
            swordRight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapons/sword_right.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setSword(){
        sword = swordUp;
        heightSword = 40;
        widthSword = 17;
        switch (player.direction) {
            case "down", "neutralDown", "left", "neutralLeft" -> {
                ySword = playerY + 8;
                xSword = playerX - 2 - widthSword;
            }
            default -> {    // like "right" and "neutralRight"
                ySword = playerY + 8;
                xSword = (playerX + gp.getTileSize()) + 2;
            }
        }
    }
    private void setSwordInAttack(){
        counterForSword ++;
        if(counterForSword == 10){
            weapons.setWeaponToFightPosition(false);
            counterForSword = 0;
        }
        switch (player.direction) {
            case "up", "neutralUp" -> {
                sword = swordUp;
                heightSword = 40;
                widthSword = 17;
                ySword = (playerY - heightSword - 2);
                xSword = playerX + (gp.getTileSize() - widthSword)/2;
                checkCollisionSwordWithEnemy();
            }
            case "down", "neutralDown" -> {
                sword = swordDown;
                heightSword = 40;
                widthSword = 17;
                ySword = (playerY + gp.getTileSize()) + 2;
                xSword = playerX + (gp.getTileSize() - widthSword)/2;
                checkCollisionSwordWithEnemy();
            }
            case "left", "neutralLeft" -> {
                sword = swordLeft;
                heightSword = 17;
                widthSword = 40;
                xSword = playerX - 2 - widthSword;
                ySword = playerY + (gp.getTileSize() - heightSword)/2;
                checkCollisionSwordWithEnemy();
            }
            default -> {    // like "right" and "neutralRight"
                sword = swordRight;
                heightSword = 17;
                widthSword = 40;
                xSword = playerX + gp.getTileSize() + 2;
                ySword = playerY + (gp.getTileSize() - heightSword)/2;
                checkCollisionSwordWithEnemy();
            }
        }
    }
    private void calculateCollisions(Enemy enemy, int toleranceWidth, int toleranceHeight, int centerOfSwordX, int centerOfSwordY){
        int centerOfEnemyX = enemy.getX() + gp.getTileSize()/2;
        int centerOfEnemyY = enemy.getY() + gp.getTileSize()/2;
        if(Math.abs(centerOfSwordX - centerOfEnemyX) <= toleranceWidth){
            if(Math.abs(centerOfSwordY - centerOfEnemyY) <= toleranceHeight){
                weapons.setWeaponToFightPosition(false);
                weapons.increaseDamageAndSetFightBooleanValueForSpecifiedEnemy(enemy);
            }
        }
    }


    private void checkCollisionSwordWithEnemy(){
        ArrayList<Enemy> enemies = gp.getEnemies();
        int toleranceWidth = widthSword/2 + gp.getTileSize()/2;
        int toleranceHeight = heightSword/2 + gp.getTileSize()/2;
        int centerOfSwordX = xSword + widthSword/2;
        int centerOfSwordY = ySword + heightSword/2;
        if(gp.isBoss()){
            calculateCollisions(bossEnemy, toleranceWidth, toleranceHeight, centerOfSwordX, centerOfSwordY);
        }else{
            for (Enemy enemy:
                    enemies) {
                calculateCollisions(enemy, toleranceWidth, toleranceHeight, centerOfSwordX, centerOfSwordY);
            }
        }

    }

    public void finalDraw(Graphics g2, Weapons weapons, Player player){
        if(gp.isBoss()){
            this.bossEnemy = gp.getBossEnemy();
        }

        this.weapons = weapons;
        this.player = player;
        this.playerX = player.getX();
        this.playerY = player.getY();
        this.bowAndArrow = weapons.getBowAndArrow();
        this.traps = weapons.getTraps();

        if(gp.getGameState() == gp.getPlayState()){
            if(weapons.isBow()){
                if(!weapons.isWeaponToFightPosition()){
                    bowAndArrow.drawBowAndArrowInStatic();
                }else{
                    bowAndArrow.fireArrowDrawMethod();
                }
                g2.drawImage(bowAndArrow.getBow(), bowAndArrow.getxBow(), bowAndArrow.getyBow(), bowAndArrow.getWidthBow(), bowAndArrow.getHeightBow(), null);
                g2.drawImage(bowAndArrow.getArrow(), bowAndArrow.getxArr(), bowAndArrow.getyArr(), bowAndArrow.getWidthArr(), bowAndArrow.getHeightArr(), null);

            }else if (weapons.isTraps()) {
                if(!weapons.isWeaponToFightPosition()){
                    traps.drawTraps();
                    g2.drawImage(traps.getBow(), traps.getxBow(), traps.getyBow(), traps.getWidthBow(), traps.getHeightBow(), null); // TODO for another image

                }else{
                    traps.holdTheTrapsAndStartCounting();
                    g2.drawImage(traps.getBow(), traps.getxBow(), traps.getyBow(), traps.getWidthBow(), traps.getHeightBow(), null);
                    g2.drawImage(traps.getArrow(), traps.getxArr(), traps.getyArr(), traps.getWidthArr(), traps.getHeightArr(), null);
                }
            }else{  // sword
                if(!weapons.isWeaponToFightPosition()){
                    setSword();
                }else{
                    setSwordInAttack();
                }
                g2.drawImage(sword, xSword, ySword, widthSword, heightSword, null);
            }
        }
    }
}
