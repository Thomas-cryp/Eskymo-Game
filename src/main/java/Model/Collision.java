package Model;

import Controller.GamePanel;
import Controller.TileManager;
import Controller.Enemy;
import Controller.Entity;
import Controller.Player;

public class Collision {
    GamePanel gp;
    TileManager tileM;
    Player player;
    LoadMap lm;
    int[][] mapTileNumber;
    String switchDirection;

    public Collision(GamePanel gp, Player player) {
        this.gp = gp;
        this.tileM = gp.getTileM();
        this.player = player;
        this.lm = new LoadMap(gp);
        lm.loadMap("/blocks/map01.txt");
        this.mapTileNumber = lm.getMapTileNumber();
    }
    /**
     * This method is called in Player class.
     * It checks if player is colliding with map.
     * @param entity - player or enemy object.
     */
    public void checkMapPosition(Entity entity, String fakeEntityDirection){
        int playerX = player.getX();
        int playerY = player.getY();
        int leftColumn = (playerX + entity.solidArea.x) / gp.getTileSize();
        int rightColumn = (playerX + entity.solidArea.x + entity.solidArea.width) / gp.getTileSize();
        int topRow = (playerY + entity.solidArea.y) / gp.getTileSize();
        int bottomRow = (playerY + entity.solidArea.y + entity.solidArea.height) / gp.getTileSize();
        int tileNum1, tileNum2;

        if (fakeEntityDirection == null){
            switchDirection = entity.direction;
        }else{
            switchDirection = fakeEntityDirection;
        }

        switch (switchDirection) {
            case "up" -> {
                topRow = (playerY + entity.solidArea.y - entity.speed) / gp.getTileSize();
                tileNum1 = mapTileNumber[leftColumn][topRow];
                tileNum2 = mapTileNumber[rightColumn][topRow];
                if (tileM.tile[tileNum1].collision || tileM.tile[tileNum2].collision) {
                    entity.collision = true;
                }
            }
            case "down" -> {
                bottomRow = (playerY + entity.solidArea.y + entity.solidArea.height + entity.speed) / gp.getTileSize();
                tileNum1 = mapTileNumber[leftColumn][bottomRow];
                tileNum2 = mapTileNumber[rightColumn][bottomRow];
                if (tileM.tile[tileNum1].collision || tileM.tile[tileNum2].collision) {
                    entity.collision = true;
                }
            }
            case "left" -> {
                leftColumn = (playerX + entity.solidArea.x - entity.speed) / gp.getTileSize();
                tileNum1 = mapTileNumber[leftColumn][topRow];
                tileNum2 = mapTileNumber[leftColumn][bottomRow];
                if (tileM.tile[tileNum1].collision || tileM.tile[tileNum2].collision) {
                    entity.collision = true;
                }
            }
            case "right" -> {
                rightColumn = (playerX + entity.solidArea.x + entity.solidArea.width + entity.speed) / gp.getTileSize();
                tileNum1 = mapTileNumber[rightColumn][topRow];
                tileNum2 = mapTileNumber[rightColumn][bottomRow];
                if (tileM.tile[tileNum1].collision || tileM.tile[tileNum2].collision) {
                    entity.collision = true;
                }
            }
        }
    }
    /**
     * This method is called in Player class.
     * It checks if player is colliding with heart after death of enemy.
     * @param player - player object.
     * @param enemy - enemy object. enemy after death has the same x and y value as heart.
     */
    public boolean compareXAndYValueForCollisionBetweenPlayerAndHeart(Player player, Enemy enemy) {
        int centerXOfPlayer = player.getX() + (gp.getTileSize()/2);
        int centerYOfPlayer = player.getY() + (gp.getTileSize()/2);
        int centerXOfHeart = enemy.getX() + (gp.getTileSize()/2);
        int centerYOfHeart = enemy.getY() + (gp.getTileSize()/2);

        if(Math.abs(centerXOfPlayer - centerXOfHeart) <= (gp.getTileSize()/2) && Math.abs(centerYOfPlayer - centerYOfHeart) <= (gp.getTileSize()/2)){
            return true;
        }
        return false;
    }
}
