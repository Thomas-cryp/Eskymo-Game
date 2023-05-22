package Model;

import Controller.GamePanel;
import entity.Enemy;
import Controller.Entity;

import java.util.ArrayList;

public class Boss extends Entity {
    GamePanel gp;

    public Boss(GamePanel gp) {
        this.gp = gp;
    }
    public boolean checkIfIsTimeForBoss(ArrayList<Enemy> enemies){
        for (Enemy enemy:
                enemies) {
            if(!enemy.isDeath()){
                return false;
            }
        }
        return true;
    }
}
