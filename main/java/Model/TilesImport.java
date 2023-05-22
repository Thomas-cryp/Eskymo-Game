package Model;

import Controller.Tile;

import javax.imageio.ImageIO;
import java.util.Objects;

public class TilesImport extends Tile {
    public TilesImport() {
        getTileImage();
    }

    public Tile[] getTileImage(){
        Tile[] tile = new Tile[10];
        try{
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/blocks/block_ice_picture.png")));
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/blocks/block_stone_picture.png")));
            tile[1].collision = true;

        }catch(Exception e){
            e.printStackTrace();
        }
        return tile;
    }
}
