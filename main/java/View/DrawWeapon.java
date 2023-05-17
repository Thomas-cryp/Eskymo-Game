package View;

import Controller.GamePanel;
import entity.Enemy;

import entity.Player;
import infoWidget.Weapons;

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

    private int startX, startY;

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }
    int counterForSword = 0;
    private int counterForTrap = 0;
    private int playerX, playerY;
    private int xBow, yBow, xArr, yArr, xSword, ySword;
    private int heightBow, widthBow, heightArr, widthArr, widthSword, heightSword;
    private BufferedImage arrUp, arrDown, arrLeft, arrRight, bowUp, bowDown, bowRight, bowLeft, bow, arrow, sword, swordUp, swordRight, swordDown, swordLeft, sword45, sword90, sword135, sword180;
    private String directionOfArrow;


    public DrawWeapon(GamePanel gp) {
        this.gp = gp;
        loadImageFormResourcesArrowAndBow();
    }

    public void loadImageFormResourcesArrowAndBow() {
        try {
            arrUp = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapons/arr_up.png")));
            arrDown = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapons/arr_down.png")));
            arrLeft = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapons/arr_left.png")));
            arrRight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapons/arr_right.png")));

            bowUp = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapons/bow_up.png")));
            bowDown = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapons/bow_down.png")));
            bowLeft = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapons/bow_left.png")));
            bowRight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapons/bow_right.png")));

            swordUp = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapons/sword_up.png")));
            swordDown = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapons/sword_down.png")));
            swordLeft = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapons/sword_left.png")));
            swordRight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapons/sword_right.png")));

            sword45 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapons/sword_45.png")));
            sword90 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapons/sword_90.png")));
            sword135 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapons/sword_135.png")));
            sword180 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapons/sword_180.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void settingForUpAndDownBow() {
        widthBow = 45;
        heightBow = 21;
    }
    public void settingForUpAndDownArray(){
        widthArr = 15;
        heightArr = 39;
    }
    public void settingForLeftAndRightBow() {
        widthBow = 21;
        heightBow = 45;
    }
    public void settingForLeftAndRightArray(){
        widthArr = 39;
        heightArr = 15;
    }
    public void loadDirectionOfArrow(){
        switch (player.direction) {
            case "up", "neutralUp" -> directionOfArrow = "up";
            case "down", "neutralDown" -> directionOfArrow = "down";
            case "left", "neutralLeft" -> directionOfArrow = "left";
            default -> directionOfArrow = "right";
        }
    }

    public void fireArrowDrawMethod() {
        if (directionOfArrow == null) {
            setStartX(xArr);
            setStartY(yArr);
            loadDirectionOfArrow();
        }
        // Have to be condition
        switch (directionOfArrow) {
            case "up" -> {
                checkerForCollisionEnemyWithArray(directionOfArrow);
                arrow = arrUp;
                xArr = startX;
                yArr -= 4;

                if (yArr <= gp.tileSize) {
                    weapons.setWeaponToFightPosition(false);
                    directionOfArrow = null;
                }
            }
            case "down" -> {
                checkerForCollisionEnemyWithArray(directionOfArrow);
                arrow = arrDown;
                xArr = startX;
                yArr += 4;

                if (yArr + heightArr >= gp.screenHeight - gp.tileSize) {
                    weapons.setWeaponToFightPosition(false);
                    directionOfArrow = null;
                }
            }
            case "left" -> {
                checkerForCollisionEnemyWithArray(directionOfArrow);
                arrow = arrLeft;
                yArr = startY;
                xArr -= 4;

                if (xArr <= gp.tileSize) {
                    weapons.setWeaponToFightPosition(false);
                    directionOfArrow = null;
                }
            }
            case "right" -> {
                checkerForCollisionEnemyWithArray(directionOfArrow);
                arrow = arrRight;
                yArr = startY;
                xArr += 4;

                if (xArr + widthArr >= gp.screenWidth - gp.tileSize) {
                    weapons.setWeaponToFightPosition(false);
                    directionOfArrow = null;
                }
            }
        }
        switch (player.direction) {
            case "up", "neutralUp" -> {
                bow = bowUp;
                settingForUpAndDownBow();
                xBow = playerX + 2;
                yBow = playerY - 2 - heightBow;

            }
            case "down", "neutralDown" -> {
                bow = bowDown;
                settingForUpAndDownBow();
                xBow = playerX + 2;
                yBow = playerY + (gp.tileSize) + 2;

            }
            case "left", "neutralLeft" -> {
                bow = bowLeft;
                settingForLeftAndRightBow();
                yBow = playerY + 2;
                xBow = playerX - 2 - widthBow;
            }
            default -> {    // like "right" and "neutralUp"
                bow = bowRight;
                settingForLeftAndRightBow();
                yBow = playerY + 2;
                xBow = playerX + (gp.tileSize) + 2;
            }
        }
    }
    public void checkerForCollisionEnemyWithArray(String direction){

        ArrayList<Enemy> enemies = gp.getEnemies();
        for (Enemy enemy:
                enemies) {
            switch (direction) {
                case "up" -> {
                    if(yArr < (enemy.getY() + gp.tileSize) && yArr > enemy.getY()){
                        if(xArr > enemy.getX() && (xArr + widthArr) < (enemy.getX() + gp.tileSize)){
                            attackWasSuccessful(enemy);
                        }
                    }
                }
                case "down" -> {
                    if((yArr + heightArr) > enemy.getY() && (yArr + heightArr) < (enemy.getY() + gp.tileSize)){
                        if(xArr > enemy.getX() && (xArr + widthArr) < (enemy.getX() + gp.tileSize)){
                            attackWasSuccessful(enemy);
                        }
                    }
                }
                case "left" -> {
                    if((enemy.getX() + gp.tileSize) > xArr && enemy.getX() < xArr){
                        if((yArr + heightArr) < (enemy.getY() + gp.tileSize) && yArr > enemy.getY()){
                            attackWasSuccessful(enemy);
                        }
                    }
                }
                case "right" -> {
                    if(enemy.getX() < (xArr + widthArr) && enemy.getX() > xArr){
                        if((yArr + heightArr) < (enemy.getY() + gp.tileSize) && yArr > enemy.getY()){
                            attackWasSuccessful(enemy);
                        }
                    }
                }
            }
        }
    }
    public void attackWasSuccessful(Enemy enemy){
        increaseDamageAndSetFightBooleanValueForSpecifiedEnemy(enemy);
        weapons.setWeaponToFightPosition(false);
        directionOfArrow = null;
    }
    public void drawBowAndArrowInStatic(){
            switch (player.direction) {
                case "up", "neutralUp" -> {
                    bow = bowUp;
                    arrow = arrUp;
                    settingForUpAndDownBow();
                    settingForUpAndDownArray();
                    xBow = playerX + 2;
                    xArr = xBow + ((widthBow - widthArr)/2);
                    yBow = playerY - 2 - heightBow;
                    yArr = (yBow + heightBow) - heightArr;
                }
                case "down", "neutralDown" -> {
                    bow = bowDown;
                    arrow = arrDown;
                    settingForUpAndDownBow();
                    settingForUpAndDownArray();
                    xBow = playerX + 2;
                    xArr = xBow + ((widthBow - widthArr)/2);
                    yBow = playerY + (gp.tileSize) + 2;
                    yArr = yBow;

                }
                case "left", "neutralLeft" -> {
                    bow = bowLeft;
                    arrow = arrLeft;
                    settingForLeftAndRightBow();
                    settingForLeftAndRightArray();
                    yBow = playerY + 2;
                    yArr = yBow + ((heightBow - heightArr)/2);
                    xBow = playerX - 2 - widthBow;
                    xArr = (xBow + widthBow) - widthArr;

                }
                default -> {    // like "right" and "neutralRight"
                    bow = bowRight;
                    arrow = arrRight;
                    settingForLeftAndRightBow();
                    settingForLeftAndRightArray();
                    yBow = playerY + 2;
                    yArr = yBow + ((heightBow - heightArr)/2);
                    xBow = playerX + (gp.tileSize) + 2;
                    xArr = xBow;
                }
            }

        }
    public void drawTraps(){    // TODO better picture
        switch (player.direction) {
            case "up", "neutralUp" -> {
                bow = bowUp;
                settingForUpAndDownBow();
                xBow = playerX + 2;
                yBow = playerY - 2 - heightBow;

            }
            case "down", "neutralDown" -> {
                bow = bowDown;
                settingForUpAndDownBow();
                xBow = playerX + 2;
                yBow = playerY + (gp.tileSize) + 2;

            }
            case "left", "neutralLeft" -> {
                bow = bowLeft;
                settingForLeftAndRightBow();
                yBow = playerY + 2;
                xBow = playerX - 2 - widthBow;
            }
            default -> {    // like "right" and "neutralUp"
                bow = bowRight;
                settingForLeftAndRightBow();
                yBow = playerY + 2;
                xBow = playerX + (gp.tileSize) + 2;

            }
        }
    }
    public void holdTheTrapsAndStartCounting(){
        counterForTrap ++;
        drawTraps();
        if(counterForTrap == 1){
            switch (player.direction) {
                case "up", "neutralUp" -> {
                    arrow = arrUp;
                    settingForUpAndDownArray();
                    xArr = xBow + ((widthBow - widthArr)/2);
                    yArr = (yBow + heightBow) - heightArr;
                }
                case "down", "neutralDown" -> {
                    arrow = arrDown;
                    settingForUpAndDownArray();
                    xArr = xBow + ((widthBow - widthArr)/2);
                    yArr = yBow;
                }
                case "left", "neutralLeft" -> {
                    arrow = arrLeft;
                    settingForLeftAndRightArray();
                    yArr = yBow + ((heightBow - heightArr)/2);
                    xArr = (xBow + widthBow) - widthArr;
                }
                default -> {    // like "right" and "neutralRight"
                    arrow = arrRight;
                    settingForLeftAndRightArray();
                    yArr = yBow + ((heightBow - heightArr)/2);
                    xArr = xBow;
                }
            }

        }
        if(counterForTrap == 120){
            bombExplosion();
            weapons.setWeaponToFightPosition(false);
            counterForTrap = 0;
        }


    }
    public void bombExplosion(){
        ArrayList<Enemy> enemies = gp.getEnemies();
        for (Enemy enemy:
             enemies) {
            if(enemy.calculateHypotenuseForTrap(xArr, yArr) < 100){
                increaseDamageAndSetFightBooleanValueForSpecifiedEnemy(enemy);
            }
        }
    }
    public void increaseDamageAndSetFightBooleanValueForSpecifiedEnemy(Enemy enemy){
        int damage = enemy.getDamage();
        damage ++;
        enemy.setDamage(damage);
        enemy.setFight(true);
    }
    public void setSword(){
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
                xSword = (playerX + gp.tileSize) + 2;
            }
        }
    }
    public void setSwordInAttack(){
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
                xSword = playerX + (gp.tileSize - widthSword)/2;
                checkCollisionSwordWithEnemy();
            }
            case "down", "neutralDown" -> {
                sword = swordDown;
                heightSword = 40;
                widthSword = 17;
                ySword = (playerY + gp.tileSize) + 2;
                xSword = playerX + (gp.tileSize - widthSword)/2;
                checkCollisionSwordWithEnemy();
            }
            case "left", "neutralLeft" -> {
                sword = swordLeft;
                heightSword = 17;
                widthSword = 40;
                xSword = playerX - 2 - widthSword;
                ySword = playerY + (gp.tileSize - heightSword)/2;
                checkCollisionSwordWithEnemy();
            }
            default -> {    // like "right" and "neutralRight"
                sword = swordRight;
                heightSword = 17;
                widthSword = 40;
                xSword = playerX + gp.tileSize + 2;
                ySword = playerY + (gp.tileSize - heightSword)/2;
                checkCollisionSwordWithEnemy();
            }
        }
    }


    private void checkCollisionSwordWithEnemy(){
        ArrayList<Enemy> enemies = gp.getEnemies();
        int toleranceWidth = widthSword/2 + gp.tileSize/2;
        int toleranceHeight = heightSword/2 + gp.tileSize/2;
        int centerOfSwordX = xSword + widthSword/2;
        int centerOfSwordY = ySword + heightSword/2;
        for (Enemy enemy:
             enemies) {
            int centerOfEnemyX = enemy.getX() + gp.tileSize/2;
            int centerOfEnemyY = enemy.getY() + gp.tileSize/2;
            if(Math.abs(centerOfSwordX - centerOfEnemyX) <= toleranceWidth){
                if(Math.abs(centerOfSwordY - centerOfEnemyY) <= toleranceHeight){
                    weapons.setWeaponToFightPosition(false);

                    increaseDamageAndSetFightBooleanValueForSpecifiedEnemy(enemy);
                }
            }
        }
    }

    public void finalDraw(Graphics g2, Weapons weapons, Player player){
        this.weapons = weapons;
        this.player = player;
        this.playerX = player.getX();
        this.playerY = player.getY();

        if(gp.getGameState() == gp.getPlayState()){
            if(weapons.isBow()){
                if(!weapons.isWeaponToFightPosition()){
                    drawBowAndArrowInStatic();
                }else{
                    fireArrowDrawMethod();
                }
                g2.drawImage(bow, xBow, yBow, widthBow, heightBow, null);
                g2.drawImage(arrow, xArr, yArr, widthArr, heightArr, null);
            } else if (weapons.isTraps()) {
                if(!weapons.isWeaponToFightPosition()){
                    drawTraps();
                    g2.drawImage(bow, xBow, yBow, widthBow, heightBow, null); // TODO for another image

                }else{
                    holdTheTrapsAndStartCounting();
                    g2.drawImage(bow, xBow, yBow, widthBow, heightBow, null);
                    g2.drawImage(arrow, xArr, yArr, widthArr, heightArr, null);
                }
            }else{  // sword
                if(!weapons.isWeaponToFightPosition()){
                    setSword();
                }else{
                    setSwordInAttack();
                }
                g2.drawImage(sword, xSword, ySword, widthSword, heightSword, null);
            }
        }else{

        }
    }
}
