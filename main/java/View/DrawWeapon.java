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



    private int startX, startY;

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }
    private int counterForTrap;

    private int xBow, yBow, xArr, yArr;
    private int heightBow, widthBow, heightArr, widthArr;
    private BufferedImage arrUp, arrDown, arrLeft, arrRight, bowUp, bowDown, bowRight, bowLeft, bow, arrow;
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

    public void fireArrowDrawMethod(int x, int y, String direction, Player player) {
        if (directionOfArrow == null) {
            setStartX(xArr);
            setStartY(yArr);
            switch (direction) {
                case "up", "neutralUp" -> {
                    directionOfArrow = "up";
                }
                case "down", "neutralDown" -> {
                    directionOfArrow = "down";
                }
                case "left", "neutralLeft" -> {
                    directionOfArrow = "left";

                }
                default -> {    // like "right" and "neutralUp"
                    directionOfArrow = "right";

                }
            }
        }
        // Have to be condition
        switch (directionOfArrow) {

            case "up" -> {
                checkerForCollisionEnemyWithArray(directionOfArrow, player);
                arrow = arrUp;
                xArr = startX;
                yArr -= 4;

                if (yArr <= gp.tileSize) {
                    player.arrowIsFlying = false;
                    directionOfArrow = null;

                }
            }
            case "down" -> {
                checkerForCollisionEnemyWithArray(directionOfArrow, player);
                arrow = arrDown;
                xArr = startX;
                yArr += 4;

                if (yArr + heightArr >= gp.screenHeight - gp.tileSize) {
                    player.arrowIsFlying = false;
                    directionOfArrow = null;


                }
            }
            case "left" -> {
                checkerForCollisionEnemyWithArray(directionOfArrow, player);
                arrow = arrLeft;
                yArr = startY;
                xArr -= 4;

                if (xArr <= gp.tileSize) {
                    player.arrowIsFlying = false;
                    directionOfArrow = null;

                }
            }
            case "right" -> {
                checkerForCollisionEnemyWithArray(directionOfArrow, player);
                arrow = arrRight;
                yArr = startY;
                xArr += 4;

                if (xArr + widthArr >= gp.screenWidth - gp.tileSize) {
                    player.arrowIsFlying = false;
                    directionOfArrow = null;

                }
            }
        }
        switch (direction) {
            case "up", "neutralUp" -> {
                bow = bowUp;
                settingForUpAndDownBow();
                xBow = x + 2;
                yBow = y - 2 - heightBow;

            }
            case "down", "neutralDown" -> {
                bow = bowDown;
                settingForUpAndDownBow();
                xBow = x + 2;
                yBow = y + (gp.tileSize) + 2;

            }
            case "left", "neutralLeft" -> {
                bow = bowLeft;
                settingForLeftAndRightBow();
                yBow = y + 2;
                xBow = x - 2 - widthBow;
            }
            default -> {    // like "right" and "neutralUp"
                bow = bowRight;
                settingForLeftAndRightBow();
                yBow = y + 2;
                xBow = x + (gp.tileSize) + 2;
            }
        }
    }
    public void checkerForCollisionEnemyWithArray(String direction, Player player){
        ArrayList<Enemy> enemies = gp.getEnemies();
        for (Enemy enemy:
                enemies) {
            switch (direction) {
                case "up" -> {
                    if(yArr < (enemy.getY() + gp.tileSize) && yArr > enemy.getY()){
                        if(xArr > enemy.getX() && (xArr + widthArr) < (enemy.getX() + gp.tileSize)){
                            attackWasSuccessful(enemy, player);
                        }
                    }
                }
                case "down" -> {
                    if((yArr + heightArr) > enemy.getY() && (yArr + heightArr) < (enemy.getY() + gp.tileSize)){
                        if(xArr > enemy.getX() && (xArr + widthArr) < (enemy.getX() + gp.tileSize)){
                            attackWasSuccessful(enemy, player);
                        }
                    }
                }
                case "left" -> {
                    if((enemy.getX() + gp.tileSize) > xArr && enemy.getX() < xArr){
                        if((yArr + heightArr) < (enemy.getY() + gp.tileSize) && yArr > enemy.getY()){
                            attackWasSuccessful(enemy, player);
                        }
                    }
                }
                case "right" -> {
                    if(enemy.getX() < (xArr + widthArr) && enemy.getX() > xArr){
                        if((yArr + heightArr) < (enemy.getY() + gp.tileSize) && yArr > enemy.getY()){
                            attackWasSuccessful(enemy, player);
                        }
                    }
                }
            }
        }
    }
    public void attackWasSuccessful(Enemy enemy, Player player){
        increaseDamageAndSetFightBooleanValueForSpecifiedEnemy(enemy);
        player.arrowIsFlying = false;
        directionOfArrow = null;
    }
    public void drawBowAndArrowInStatic(int x, int y, String direction){
                switch (direction) {
                    case "up", "neutralUp" -> {
                        bow = bowUp;
                        arrow = arrUp;

                        settingForUpAndDownBow();
                        settingForUpAndDownArray();
                        xBow = x + 2;
                        xArr = xBow + ((widthBow - widthArr)/2);
                        yBow = y - 2 - heightBow;
                        yArr = (yBow + heightBow) - heightArr;
                    }
                    case "down", "neutralDown" -> {
                        bow = bowDown;
                        arrow = arrDown;

                        settingForUpAndDownBow();
                        settingForUpAndDownArray();
                        xBow = x + 2;
                        xArr = xBow + ((widthBow - widthArr)/2);
                        yBow = y + (gp.tileSize) + 2;
                        yArr = yBow;

                    }
                    case "left", "neutralLeft" -> {
                        bow = bowLeft;
                        arrow = arrLeft;

                        settingForLeftAndRightBow();
                        settingForLeftAndRightArray();
                        yBow = y + 2;
                        yArr = yBow + ((heightBow - heightArr)/2);
                        xBow = x - 2 - widthBow;
                        xArr = (xBow + widthBow) - widthArr;

                    }
                    default -> {    // like "right" and "neutralRight"
                        bow = bowRight;
                        arrow = arrRight;

                        settingForLeftAndRightBow();
                        settingForLeftAndRightArray();
                        yBow = y + 2;
                        yArr = yBow + ((heightBow - heightArr)/2);
                        xBow = x + (gp.tileSize) + 2;
                        xArr = xBow;
                    }
                }

            }
    public void drawTraps(int x, int y, String direction){
        switch (direction) {
            case "up", "neutralUp" -> {
                bow = bowUp;
                settingForUpAndDownBow();
                xBow = x + 2;
                yBow = y - 2 - heightBow;

            }
            case "down", "neutralDown" -> {
                bow = bowDown;
                settingForUpAndDownBow();
                xBow = x + 2;
                yBow = y + (gp.tileSize) + 2;

            }
            case "left", "neutralLeft" -> {
                bow = bowLeft;
                settingForLeftAndRightBow();
                yBow = y + 2;
                xBow = x - 2 - widthBow;
            }
            default -> {    // like "right" and "neutralUp"
                bow = bowRight;
                settingForLeftAndRightBow();
                yBow = y + 2;
                xBow = x + (gp.tileSize) + 2;

            }
        }
    }
    public void holdTheTrapsAndStartCounting(int x, int y, String direction, Player player){
        counterForTrap ++;
        drawTraps(x, y, direction);
        if(counterForTrap == 1){
            switch (direction) {
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
            player.arrowIsFlying = false;
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

    public void finalDraw(Graphics g2, int x, int y, String direction, Weapons weapons, boolean arrowFlight, Player player){
        if(gp.getGameState() == gp.getPlayState()){
            if(weapons.isBow()){
                if(!arrowFlight){
                    drawBowAndArrowInStatic(x, y, direction);
                }else{
                    fireArrowDrawMethod(x, y, direction, player);
                }
                g2.drawImage(bow, xBow, yBow, widthBow, heightBow, null);
                g2.drawImage(arrow, xArr, yArr, widthArr, heightArr, null);
            } else if (weapons.isTraps()) {
                // TODO
                if(!arrowFlight){
                    drawTraps(x, y, direction);
                    g2.drawImage(bow, xBow, yBow, widthBow, heightBow, null); // TODO for another image

                }else{
                    holdTheTrapsAndStartCounting(x, y, direction, player);
                    g2.drawImage(bow, xBow, yBow, widthBow, heightBow, null);
                    g2.drawImage(arrow, xArr, yArr, widthArr, heightArr, null);
                }
            }
        }else{

        }
    }
}
