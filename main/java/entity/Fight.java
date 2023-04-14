package entity;

import Controller.GamePanel;

public class Fight extends GamePanel {
    GamePanel gp;
    Player player;
    Enemy enemy;
    int heartsOnStart;
    int currentHearts;
    int counterOfKills;

    public boolean checkEntityHearts(){
        return false;
    }
    public boolean spawnAlfaMonster(){  // it will be checked, if is time to spawn monster
        return false;
    }
}
