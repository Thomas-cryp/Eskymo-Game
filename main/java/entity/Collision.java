package entity;

import Controller.GamePanel;
import Controller.TileManager;
import Model.tile.LoadMap;

public class Collision {
    GamePanel gp;
    TileManager tileM;
    Player player;
    LoadMap lm;
    int mapTileNumber[][];

    public Collision(GamePanel gp, Player player) {
        this.gp = gp;
        this.tileM = new TileManager(gp);
        this.player = player;
        this.lm = new LoadMap(gp);
        lm.loadMap("/blocks/map01.txt"); // TODO better
        this.mapTileNumber = lm.getMapTileNumber();
    }
    public void checkMapPosition(Entity entity){
        int playerX = player.getX();
        int playerY = player.getY();
        int leftColumn = (playerX + entity.solidArea.x) / gp.tileSize;
        int rightColumn = (playerX + entity.solidArea.x + entity.solidArea.width) / gp.tileSize;
        int topRow = (playerY + entity.solidArea.y) / gp.tileSize;
        int bottomRow = (playerY + entity.solidArea.y + entity.solidArea.height) / gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction){
            case "up":
                topRow = (playerY + entity.solidArea.y - entity.speed)/gp.tileSize;

                tileNum1 = mapTileNumber[leftColumn][topRow];
                tileNum2 = mapTileNumber[rightColumn][topRow];
                if(tileM.tile[tileNum1].collision || tileM.tile[tileNum2].collision){
                    entity.collision = true;
                }
                break;
            case "down":
                bottomRow = (playerY + entity.solidArea.y + entity.solidArea.height + entity.speed)/gp.tileSize;

                tileNum1 = mapTileNumber[leftColumn][bottomRow];
                tileNum2 = mapTileNumber[rightColumn][bottomRow];
                if(tileM.tile[tileNum1].collision || tileM.tile[tileNum2].collision){
                    entity.collision = true;
                }
                break;
            case "left":
                leftColumn = (playerX + entity.solidArea.x - entity.speed)/gp.tileSize;

                tileNum1 = mapTileNumber[leftColumn][topRow];
                tileNum2 = mapTileNumber[leftColumn][bottomRow];
                if(tileM.tile[tileNum1].collision || tileM.tile[tileNum2].collision){
                    entity.collision = true;
                }
                break;
            case "right":
                rightColumn = (playerX + entity.solidArea.x + entity.solidArea.width + entity.speed)/gp.tileSize;

                tileNum1 = mapTileNumber[rightColumn][topRow];
                tileNum2 = mapTileNumber[rightColumn][bottomRow];
                if(tileM.tile[tileNum1].collision || tileM.tile[tileNum2].collision){
                    entity.collision = true;
                }
                break;


        }
    }
}
