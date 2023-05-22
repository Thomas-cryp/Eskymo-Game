package Controller;


import Model.LoadMap;
import Model.TilesImport;
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
        /**
         * This method is called in GamePanel class.
         * It loads the map and tile image.
         */
        public void loadMapAndTileImage(){
            lm.loadMap("/blocks/map01.txt");
            tile = ti.getTileImage();
        }
        /**
         * This method is called in GamePanel class.
         * It draws the map.
         * @param g2
         */
        public void drawing(Graphics2D g2){
            mapTileNumber = lm.getMapTileNumber();
            drawMap.draw(g2, gp, mapTileNumber);
        }

}


