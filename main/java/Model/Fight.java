package Model;

import Controller.GamePanel;

import Controller.Weapons;
import Controller.Enemy;
import Controller.Player;

public class Fight{
    GamePanel gp;
    Player player;
    Enemy enemy;
    Weapons weapons;
    private int counterOfSlowestFrames = 30;
    private int playerX, playerY, enemyX, enemyY;



    public Fight(GamePanel gp, Player player, Enemy enemy) {
        this.gp = gp;
        this.player = player;
        this.enemy = enemy;
        this.weapons = player.getWeapons();
    }

    /**
     * timer for freeze enemy after attack player.
     * @return - true if counterOfSlowestFrames is in the end, false if not.
     */
    public boolean timerAfterAttackPlayerByEnemy(){
        counterOfSlowestFrames ++;
        if(counterOfSlowestFrames >= 30){
            counterOfSlowestFrames = 0;
            return true;
        }else {
            return false;
        }
    }
    private void setEnemyPosition(){
        enemyX = enemy.getX();
        enemyY = enemy.getY();
    }

    /**
     * calculate hypotenuse between trap and enemy for check if enemy is in range of trap.
     * @param xWeapon - x position of trap.
     * @param yWeapon - y position of trap.
     * @return - distance between trap and enemy.
     */
    public double calculateHypotenuseForTrap(int xWeapon, int yWeapon){
        setEnemyPosition();
        double distancePlayerAndEnemy;
        double b = enemyX - xWeapon;
        double a = enemyY - yWeapon;
        distancePlayerAndEnemy = Math.sqrt(b * b + a * a);
        return distancePlayerAndEnemy;
    }

    /**
     * calculate hypotenuse between player and enemy for check if enemy is in range of player.
     * @return - distance between player and enemy.
     */
    public double calculateHypotenuse(){

        setPlayerPosition();
        setEnemyPosition();
        double distancePlayerAndEnemy;
        double b = enemyX - playerX;
        double a = enemyY - playerY;
        distancePlayerAndEnemy = Math.sqrt(b * b + a * a);
        return distancePlayerAndEnemy;
    }

    /**
     * set player position to calculate hypotenuse.
     */
    public void setPlayerPosition(){
        playerX = player.getX();
        playerY = player.getY();
    }

    /**
     * timer for player when he is attacked by enemy. After 60 frames player can be attacked again.
     * @return - boolean value if is over 60 frames.
     */
    public boolean timerToAttackKey(){
        if(player.getTimerToAttackKey() >= 60){
            player.setTimerToAttackKey(0);
            return true;
        }else{
            return false;
        }

    }
    public void setWeaponsToFightState(){
        weapons.setWeaponToFightPosition(true);
    }public void setCounterOfSlowestFrames(int counterOfSlowestFrames) {
        this.counterOfSlowestFrames = counterOfSlowestFrames;
    }
}
