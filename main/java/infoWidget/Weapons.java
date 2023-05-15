package infoWidget;

import Controller.GamePanel;
import Controller.KeyHandler;
import View.DrawWeapon;
import entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Weapons{   // TODO extends on ITEM class

    GamePanel gp;
    File jsonFile;

    JSONObject data;
    DrawWeapon drawWeapon;
    boolean sword;
    boolean bow;
    boolean traps;
    private boolean endOfArrow = false;

    public boolean isEndOfArrow() {
        return endOfArrow;
    }

    public Weapons(GamePanel gp) {
        this.gp = gp;
        this.drawWeapon = new DrawWeapon(gp);
        this.jsonFile = new File("/Users/tomasjelsik/Desktop/Škola II.sem./PJV/semestrálka/PJV_semestralka/src/main/java/infoWidget/actualWeapons.json");   // TODO absolut path
        fileReader();
    }
    public void getActualWeapon() {
        fileReader();
    }
    public void fileReader(){
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(jsonFile)) {
            data = (JSONObject) parser.parse(reader);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
    private void writeFile() {
        try (FileWriter writer = new FileWriter(jsonFile)) {
            writer.write(data.toJSONString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean isSword() {
        sword = (boolean) data.getOrDefault("sword", false);
        return sword;
    }

    public void setSword() {
        data.put("sword", true);
        data.put("bow", false);
        data.put("traps", false);
        writeFile();
    }

    public boolean isBow() {
        bow = (boolean) data.getOrDefault("bow", false);
        return bow;
    }

    public void setBow() {

        data.put("sword", false);
        data.put("bow", true);
        data.put("traps", false);
        writeFile();

    }
    public boolean isTraps() {
        traps = (boolean) data.getOrDefault("traps", false);
        return traps;
    }
    public void setTraps() {
        data.put("sword", false);
        data.put("bow", false);
        data.put("traps", true);
        writeFile();
    }
    public void listenerForChangeTheWeapon(KeyHandler keyH){
        if(keyH.number1){
            setSword();
        }
        if(keyH.number2){
            setBow();
        }
        if(keyH.number3){
            setTraps();
        }
    }
    public void setDefaultValuesOfWeapons(){
        setSword();
    }

    public void draw(Graphics g2, int x, int y, String direction, boolean arrowFlight, Player player){
        drawWeapon.finalDraw(g2, x, y, direction, this, arrowFlight, player);
    }
}
