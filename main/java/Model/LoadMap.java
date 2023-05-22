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

    /**
     * This method loads map from map file and saves it in mapTileNumber array.
     * @param file - map file. It is in resources folder.
     */
    public void loadMap(String file){

        mapTileNumber = new int[gp.getMaxScreenColumn()][gp.getMaxScreenRow()];

        try(InputStream is = getClass().getResourceAsStream(file)){

        if (is == null) throw new AssertionError();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        int column = 0;
        int row = 0;

        while(column < gp.getMaxScreenColumn() && row < gp.getMaxScreenRow()){
            String line = br.readLine();

            while(column < gp.getMaxScreenColumn()){
                String[] numbers = line.split(" ");
                int num = Integer.parseInt(numbers[column]);
                mapTileNumber[column][row] = num;
                column ++;

            }
            if(column == gp.getMaxScreenColumn()){
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
