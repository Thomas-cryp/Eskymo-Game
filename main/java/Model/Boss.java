package Model;

import Controller.GamePanel;
import Controller.Enemy;
import Controller.Entity;

import java.util.ArrayList;

public class Boss extends Entity {
    GamePanel gp;

    public Boss(GamePanel gp) {
        this.gp = gp;
    }
    /**
     * This method is called in GamePanel class.
     * It checks if all enemies are dead.
     * @param enemies - list of enemies in game.
     * @return - true if all enemies are dead, false if not.
     */
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
