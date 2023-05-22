package Model;

import Controller.GamePanel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class LoadMap{
    GamePanel gp;
    private int[][] mapTileNumber;
    public LoadMap(GamePanel gp) {
        this.gp = gp;

    }
    public void loadMap(String file){

        mapTileNumber = new int[gp.maxScreenColumn][gp.maxScreenRow];

        try(InputStream is = getClass().getResourceAsStream(file)){

        if (is == null) throw new AssertionError();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        int column = 0;
        int row = 0;

        while(column < gp.maxScreenColumn && row < gp.maxScreenRow){
            String line = br.readLine();

            while(column < gp.maxScreenColumn){
                String[] numbers = line.split(" ");
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

    }catch(IOException e){
        throw new RuntimeException(e);
    }
    }
    public int[][] getMapTileNumber() {
        return mapTileNumber;
    }

}
