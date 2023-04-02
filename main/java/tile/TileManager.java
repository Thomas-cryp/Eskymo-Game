package tile;

import cz.cvut.fel.pjv.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    Tile[] tile;
    int[][] mapTileNumber;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];
        mapTileNumber = new int[gp.maxScreenColumn][gp.maxScreenRow];
        getTileImage();
        loadMap();
    }
    public void getTileImage(){
        try{
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/blocks/block_ice_picture.png"));
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/blocks/ezgif.com-crop.png"));

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void loadMap(){
        try{
            InputStream is = getClass().getResourceAsStream("/blocks/map01.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int column = 0;
            int row = 0;
            while(column < gp.maxScreenColumn && row < gp.maxScreenRow){
                String line = br.readLine();

                while(column < gp.maxScreenColumn){
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[column]);
                    mapTileNumber[column][row] = num;
                    column ++;

                    }
                if(column == gp.maxScreenColumn){
                        column = 0;
                        row ++;
                }
            }
            br.close();

        }catch(Exception e){

        }
    }
    public void draw(Graphics2D g2){
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
