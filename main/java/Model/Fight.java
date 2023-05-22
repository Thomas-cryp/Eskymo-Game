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

    public void setCounterOfSlowestFrames(int counterOfSlowestFrames) {
        this.counterOfSlowestFrames = counterOfSlowestFrames;
    }

    public Fight(GamePanel gp, Player player, Enemy enemy) {
        this.gp = gp;
        this.player = player;
        this.enemy = enemy;
        this.weapons = player.getWeapons();
    }
    public void increaseDamageAndSetFightBooleanValue(Enemy enemy){
        int damage = enemy.getDamage();
        damage ++;
        enemy.setDamage(damage);
        enemy.setFight(true);
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
    private void setEnemyPosition(){
        enemyX = enemy.getX();
        enemyY = enemy.getY();
    }
    public double calculateHypotenuseForTrap(int xWeapon, int yWeapon){
        setEnemyPosition();
        double distancePlayerAndEnemy;
        double b = enemyX - xWeapon;
        double a = enemyY - yWeapon;
        distancePlayerAndEnemy = Math.sqrt(b * b + a * a);
        return distancePlayerAndEnemy;
    }
    public double calculateHypotenuse(){

        setPlayerPosition();
        setEnemyPosition();
        double distancePlayerAndEnemy;
        double b = enemyX - playerX;
        double a = enemyY - playerY;
        distancePlayerAndEnemy = Math.sqrt(b * b + a * a);
        return distancePlayerAndEnemy;
    }
    public void setPlayerPosition(){
        playerX = player.getX();
        playerY = player.getY();
    }
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
    }
}
