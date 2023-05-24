package Model;

import Controller.Enemy;
import Controller.GamePanel;
import Controller.Player;
import Controller.Weapons;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Sword {
    GamePanel gp;
    Player player;
    Weapons weapons;
    private BufferedImage sword, swordUp, swordRight, swordDown, swordLeft;
    private int xSword, ySword;

    public BufferedImage getSword() {
        return sword;
    }

    public int getxSword() {
        return xSword;
    }

    public int getySword() {
        return ySword;
    }

    public int getWidthSword() {
        return widthSword;
    }

    public int getHeightSword() {
        return heightSword;
    }

    private int widthSword, heightSword;
    int counterForSword = 0;
    public Sword(GamePanel gp, Player player, Weapons weapons) {
        this.gp = gp;
        this.player = player;
        this.weapons = weapons;
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
    private void checkCollisionSwordWithEnemy(){
        ArrayList<Enemy> enemies = gp.getEnemies();
        int toleranceWidth = widthSword/2 + gp.getTileSize()/2;
        int toleranceHeight = heightSword/2 + gp.getTileSize()/2;
        int centerOfSwordX = xSword + widthSword/2;
        int centerOfSwordY = ySword + heightSword/2;
        if(gp.isBoss()){
            calculateCollisions(gp.getBossEnemy(), toleranceWidth, toleranceHeight, centerOfSwordX, centerOfSwordY);
        }else{
            for (Enemy enemy:
                    enemies) {
                calculateCollisions(enemy, toleranceWidth, toleranceHeight, centerOfSwordX, centerOfSwordY);
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

    /**
     * This method is called when the player is attacking with a sword.
     * It sets the sword in the correct position and checks if the sword collides with an enemy.
     */
    public void setSwordInAttack(){
        int playerX = player.getX();
        int playerY = player.getY();
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

    /**
     * This method sets the sword in the position of the player
     * Default position is up. It changes direction.
     */
    public void setSword(){
        int playerX = player.getX();
        int playerY = player.getY();
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
}
