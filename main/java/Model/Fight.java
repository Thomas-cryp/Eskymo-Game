package Model;

import Controller.GamePanel;

import Controller.Weapons;
import entity.Enemy;
import entity.Player;

public class Fight{
    GamePanel gp;
    Player player;
    Enemy enemy;
    Weapons weapons;

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
    public void setWeaponsToFightState(){
        weapons.setWeaponToFightPosition(true);
    }
}
