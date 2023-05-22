package Model;

import Controller.GamePanel;
import Controller.Weapons;
import View.DrawWeapon;
import Controller.Enemy;
import Controller.Player;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class BowAndArrow {
    GamePanel gp;
    Weapons weapons;
    Player player;
    DrawWeapon drawWeapon;
    private int playerX, playerY;

    private int xBow, yBow, xArr, yArr;
    private int heightBow, widthBow, heightArr, widthArr;
    private String directionOfArrow;
    private BufferedImage arrUp, arrDown, arrLeft, arrRight, bowUp, bowDown, bowRight, bowLeft, bow, arrow;

    public BowAndArrow(GamePanel gp, Player player, Weapons weapons) {
        this.gp = gp;
        this.player = player;
        this.weapons = weapons;
        this.drawWeapon = weapons.getDrawWeapon();
        loadImageFormResourcesArrowAndBow();
    }
    private void loadImageFormResourcesArrowAndBow() {
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
    private void settingForUpAndDownBow() {
        widthBow = 45;
        heightBow = 21;
    }
    private void settingForUpAndDownArray(){
        widthArr = 15;
        heightArr = 39;
    }
    private void settingForLeftAndRightBow() {
        widthBow = 21;
        heightBow = 45;
    }
    private void settingForLeftAndRightArray(){
        widthArr = 39;
        heightArr = 15;
    }
    public void drawBowAndArrowInStatic(){
        playerX = player.getX();
        playerY = player.getY();
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
                yBow = playerY + (gp.getTileSize()) + 2;
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
                xBow = playerX + (gp.getTileSize()) + 2;
                xArr = xBow;
            }
        }

    }
    private void calculateCollisionArrayWithEnemy(String direction, Enemy enemy){
        int largeOfEnemy;
        if(gp.isBoss()){
            largeOfEnemy = 80;
        }else{
            largeOfEnemy = gp.getTileSize();
        }
        switch (direction) {
            case "up" -> {
                if(yArr < (enemy.getY() + largeOfEnemy) && yArr > enemy.getY()){
                    if(xArr > enemy.getX() && (xArr + widthArr) < (enemy.getX() + largeOfEnemy)){
                        attackWasSuccessful(enemy);
                    }
                }
            }
            case "down" -> {
                if((yArr + heightArr) > enemy.getY() && (yArr + heightArr) < (enemy.getY() + largeOfEnemy)){
                    if(xArr > enemy.getX() && (xArr + widthArr) < (enemy.getX() + largeOfEnemy)){
                        attackWasSuccessful(enemy);
                    }
                }
            }
            case "left" -> {
                if((enemy.getX() + largeOfEnemy) > xArr && enemy.getX() < xArr){
                    if((yArr + heightArr) < (enemy.getY() + largeOfEnemy) && yArr > enemy.getY()){
                        attackWasSuccessful(enemy);
                    }
                }
            }
            case "right" -> {

                if(enemy.getX() < (xArr + widthArr) && enemy.getX() > xArr){
                    if((yArr + heightArr) < (enemy.getY() + largeOfEnemy) && yArr > enemy.getY()){
                        attackWasSuccessful(enemy);
                    }
                }
            }
        }
    }
    public void checkerForCollisionEnemyWithArray(String direction){
        if(gp.isBoss()){
            calculateCollisionArrayWithEnemy(direction, drawWeapon.getBossEnemy());
        }else{
            ArrayList<Enemy> enemies = gp.getEnemies();
            for (Enemy enemy:
                    enemies) {
                calculateCollisionArrayWithEnemy(direction, enemy);
            }
        }
    }
    public void attackWasSuccessful(Enemy enemy){
        if(gp.isBoss()){
            enemy = drawWeapon.getBossEnemy();
        }
        weapons.increaseDamageAndSetFightBooleanValueForSpecifiedEnemy(enemy);
        weapons.setWeaponToFightPosition(false);
        directionOfArrow = null;
    }
    public void fireArrowDrawMethod() {
        playerX = player.getX();
        playerY = player.getY();

        if (directionOfArrow == null) {
            drawWeapon.setStartX(xArr);
            drawWeapon.setStartY(yArr);
            loadDirectionOfArrow();
        }
        // Have to be condition
        switch (directionOfArrow) {
            case "up" -> {
                checkerForCollisionEnemyWithArray(directionOfArrow);
                arrow = arrUp;
                xArr = drawWeapon.getStartX();
                yArr -= 4;

                if (yArr <= gp.getTileSize()) {
                    weapons.setWeaponToFightPosition(false);
                    directionOfArrow = null;
                }
            }
            case "down" -> {
                checkerForCollisionEnemyWithArray(directionOfArrow);
                arrow = arrDown;
                xArr = drawWeapon.getStartX();
                yArr += 4;

                if (yArr + heightArr >= gp.getScreenHeight() - gp.getTileSize()) {
                    weapons.setWeaponToFightPosition(false);
                    directionOfArrow = null;
                }
            }
            case "left" -> {
                checkerForCollisionEnemyWithArray(directionOfArrow);
                arrow = arrLeft;
                yArr = drawWeapon.getStartY();
                xArr -= 4;

                if (xArr <= gp.getTileSize()) {
                    weapons.setWeaponToFightPosition(false);
                    directionOfArrow = null;
                }
            }
            case "right" -> {
                checkerForCollisionEnemyWithArray(directionOfArrow);
                arrow = arrRight;
                yArr = drawWeapon.getStartY();
                xArr += 4;

                if (xArr + widthArr >= gp.getScreenWidth() - gp.getTileSize()) {
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
                yBow = playerY + (gp.getTileSize()) + 2;

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
                xBow = playerX + (gp.getTileSize()) + 2;
            }
        }
    }
    public void loadDirectionOfArrow(){
        switch (player.direction) {
            case "up", "neutralUp" -> directionOfArrow = "up";
            case "down", "neutralDown" -> directionOfArrow = "down";
            case "left", "neutralLeft" -> directionOfArrow = "left";
            default -> directionOfArrow = "right";
        }
    }

    public int getHeightBow() {
        return heightBow;
    }

    public int getWidthBow() {
        return widthBow;
    }

    public int getHeightArr() {
        return heightArr;
    }

    public int getWidthArr() {
        return widthArr;
    }

    public int getxBow() {
        return xBow;
    }

    public int getyBow() {
        return yBow;
    }

    public int getxArr() {
        return xArr;
    }

    public int getyArr() {
        return yArr;
    }
    public BufferedImage getBow() {
        return bow;
    }
    public BufferedImage getArrow() {
        return arrow;
    }
}
