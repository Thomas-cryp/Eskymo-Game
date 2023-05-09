package entity;

import Controller.GamePanel;

public class Fight{
    GamePanel gp;
    Player player;
    Enemy enemy;

//    int heartsOnStart;
//    int currentHearts;
//    int counterOfKills;
//    private double distancePlayerAndEnemy;
//    int playerX, playerY;
//    int enemyX, enemyY;

    public Fight(GamePanel gp, Player player, Enemy enemy) {
        this.gp = gp;
        this.player = player;
        this.enemy = enemy;


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
