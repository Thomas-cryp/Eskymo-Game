package Controller;


import Model.tile.LoadMap;
import Model.tile.Tile;
import Model.tile.TilesImport;
import View.DrawMap;

import java.awt.*;


public class TileManager{
        TilesImport ti;
        DrawMap drawMap = new DrawMap();
        LoadMap lm;
        GamePanel gp;

        public Tile[] tile;
        int[][] mapTileNumber;



    public TileManager(GamePanel gp) {
            this.gp = gp;
            ti = new TilesImport();
            lm = new LoadMap(gp);
        }
        public void loadMapAndTileImage(){
            lm.loadMap("/blocks/map01.txt");
            tile = ti.getTileImage();
        }
        public void drawing(Graphics2D g2){
            mapTileNumber = lm.getMapTileNumber();
            drawMap.draw(g2, gp, mapTileNumber);
        }

}


