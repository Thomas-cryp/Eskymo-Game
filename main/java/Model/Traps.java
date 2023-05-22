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

public class Traps {
    GamePanel gp;
    Player player;
    Weapons weapons;
    DrawWeapon drawWeapon;

    private int heightBow, widthBow, heightArr, widthArr;
    private int xBow, yBow, xArr, yArr;
    private BufferedImage arrUpTrap, arrDownTrap, arrLeftTrap, arrRightTrap, bowUp, bowDown, bowRight, bowLeft, bow, arrow;
    private int counterForTrap = 0;
    int playerX, playerY;

    public Traps(GamePanel gp, Player player, Weapons weapons) {
        this.gp = gp;
        this.player = player;
        this.weapons = weapons;
        this.drawWeapon = weapons.getDrawWeapon();
        loadImageFormResourcesArrowAndBow();
    }
    public void loadImageFormResourcesArrowAndBow() {
        try {
            arrUpTrap = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapons/arr_up.png")));
            arrDownTrap = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapons/arr_down.png")));
            arrLeftTrap = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapons/arr_left.png")));
            arrRightTrap = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapons/arr_right.png")));

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
    public void drawTraps(){    // TODO better picture
        playerX = player.getX();
        playerY = player.getY();
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
    public void holdTheTrapsAndStartCounting(){
        counterForTrap ++;
        drawTraps();
        if(counterForTrap == 1){
            switch (player.direction) {
                case "up", "neutralUp" -> {
                    arrow = arrUpTrap;
                    settingForUpAndDownArray();
                    xArr = xBow + ((widthBow - widthArr)/2);
                    yArr = (yBow + heightBow) - heightArr;
                }
                case "down", "neutralDown" -> {
                    arrow = arrDownTrap;
                    settingForUpAndDownArray();
                    xArr = xBow + ((widthBow - widthArr)/2);
                    yArr = yBow;
                }
                case "left", "neutralLeft" -> {
                    arrow = arrLeftTrap;
                    settingForLeftAndRightArray();
                    yArr = yBow + ((heightBow - heightArr)/2);
                    xArr = (xBow + widthBow) - widthArr;
                }
                default -> {    // like "right" and "neutralRight"
                    arrow = arrRightTrap;
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
        if(gp.isBoss()){
            weapons.increaseDamageAndSetFightBooleanValueForSpecifiedEnemy(gp.getBossEnemy());
        }else{
            for (Enemy enemy:
                    enemies) {
                Fight fight = new Fight(gp, player, enemy);
                if(fight.calculateHypotenuseForTrap(xArr, yArr) < 100){
                    weapons.increaseDamageAndSetFightBooleanValueForSpecifiedEnemy(enemy);
                }
            }
        }

    }public int getHeightBow() {
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
    }public int getxBow() {
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
