package entity;

import Controller.GamePanel;

public class Fight{
    GamePanel gp;
    Player player;
    Enemy enemy;
    int heartsOnStart;
    int currentHearts;
    int counterOfKills;
    private double distancePlayerAndEnemy;
    int playerX, playerY;
    int enemyX, enemyY;

    public Fight(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;
        this.enemy = new Enemy(gp, player);
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
