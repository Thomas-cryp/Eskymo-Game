package View;

import Controller.GamePanel;

import Model.LoadMap;
import Controller.Tile;
import Model.TilesImport;

import java.awt.*;
import java.util.Objects;

public class DrawMap{
    GamePanel gp;
    LoadMap lm;

    TilesImport ti;
    Tile[] tile;

    public void draw(Graphics2D g2, GamePanel gp, int[][] mapTileNumber){
        this.gp = Objects.requireNonNull(gp, "Game Panel cannot be null");
        lm = new LoadMap(gp);
        ti = new TilesImport();
        tile = ti.getTileImage();

        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;


        while(col < gp.maxScreenColumn && row < gp.maxScreenRow){

            int tileNumber = mapTileNumber[col][row];
            g2.drawImage(tile[tileNumber].image, x, y, gp.tileSize, gp.tileSize, null);
            x += gp.tileSize;
            col ++;

            if(col == gp.maxScreenColumn){
                col = 0;
                x = 0;
                y += gp.tileSize;
                row ++;
            }

        }
    }
}
