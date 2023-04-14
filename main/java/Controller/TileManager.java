package Controller;


import Model.tile.LoadMap;
import Model.tile.Tile;
import Model.tile.TilesImport;
import View.DrawMap;

import java.awt.*;


public class TileManager extends GamePanel{
        TilesImport ti;
        DrawMap drawMap = new DrawMap();
        LoadMap lm;
        GamePanel gp;
        Tile[] tile;
        int[][] mapTileNumber;

        public TileManager(GamePanel gp) {
            this.gp = gp;
            ti = new TilesImport();
            lm = new LoadMap();
            lm.loadMap("/blocks/map01.txt", gp);
            tile = ti.getTileImage();
        }
        public void drawing(Graphics2D g2){
            mapTileNumber = lm.getMapTileNumber();
            drawMap.draw(g2, gp, mapTileNumber);
        }
    }


