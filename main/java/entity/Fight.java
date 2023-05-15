package entity;

import Controller.GamePanel;
import View.DrawWeapon;
import infoWidget.Weapons;

public class Fight{
    GamePanel gp;
    Player player;
    Enemy enemy;
    Weapons weapons;

    public Fight(GamePanel gp, Player player, Enemy enemy) {
        this.gp = gp;
        this.player = player;
        this.enemy = enemy;
        this.weapons = new Weapons(gp);
    }
    public boolean checkPlayerOrienting(){
        switch (player.direction){
            case "up":
            case "neutralUp":
                if(enemy.getY() < player.getY()){
                    return true;
                }
            case "down":
            case "neutralDown":
                if(enemy.getY() > (player.getY() + gp.tileSize/2)){
                    return true;
                }
            case "left":
            case "neutralLeft":
                if(enemy.getX() < player.getX()){
                    return true;
                }
            case "right":
            case "neutralRight":
                if(enemy.getX() > (player.getX() + gp.tileSize/2)){
                    return true;
                }
        }
        return false;
    }
    public void increaseDamageAndSetFightBooleanValue(Enemy enemy){
        int damage = enemy.getDamage();
        damage ++;
        enemy.setDamage(damage);
        enemy.setFight(true);
    }
    public void swordFight(Enemy enemy){
        if(checkPlayerOrienting()){
            increaseDamageAndSetFightBooleanValue(enemy);
        }
    }
    public void bowFight(){
        player.arrowIsFlying = true;
    }
    public void trapsFight(){
        player.arrowIsFlying = true;
    }


    public boolean checkEntityHearts(){
        return false;
    }
    public boolean spawnAlfaMonster(){  // it will be checked, if is time to spawn monster
        return false;
    }

    public boolean checkPlayerPosition(int X, int Y){
        return false;
    }


}
