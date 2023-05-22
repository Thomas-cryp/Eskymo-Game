package Controller;

import Model.BowAndArrow;
import Model.KeyHandler;
import Model.Traps;
import View.DrawWeapon;
import entity.Enemy;
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

    public DrawWeapon getDrawWeapon() {
        return drawWeapon;
    }
    BowAndArrow bowAndArrow;
    Traps trapsClass;

    public Traps getTraps() {
        return trapsClass;
    }

    public BowAndArrow getBowAndArrow() {
        return bowAndArrow;
    }

    JSONObject data;
    DrawWeapon drawWeapon;
    Player player;
    boolean sword;
    boolean bow;
    boolean traps;
    private boolean endOfArrow = false;
    private boolean weaponToFightPosition = false;

    public boolean isWeaponToFightPosition() {
        return weaponToFightPosition;
    }

    public void setWeaponToFightPosition(boolean weaponToFightPosition) {
        this.weaponToFightPosition = weaponToFightPosition;
    }


    public Weapons(GamePanel gp, Player player) {
        this.gp = gp;
        this.drawWeapon = new DrawWeapon(gp);
        this.jsonFile = new File("JSONs/actualWeapons.json");   // TODO absolut path
        this.bowAndArrow = new BowAndArrow(gp, player, this);
        this.trapsClass = new Traps(gp, player, this);
        fileReader();
    }
    public void increaseDamageAndSetFightBooleanValueForSpecifiedEnemy(Enemy enemy){
        int damage = enemy.getDamage();
        damage ++;
        enemy.setDamage(damage);
        enemy.setFight(true);
    }
    private void fileReader(){
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

    private void setSword() {
        setWeapon(true, false, false);
    }
    public void setWeapon(boolean value1, boolean value2, boolean value3){
        data.put("sword", value1);
        data.put("bow", value2);
        data.put("traps", value3);
        writeFile();
    }

    public boolean isBow() {
        bow = (boolean) data.getOrDefault("bow", false);
        return bow;
    }

    private void setBow() {
        setWeapon(false, true, false);
    }
    public boolean isTraps() {
        traps = (boolean) data.getOrDefault("traps", false);
        return traps;
    }
    private void setTraps() {
        setWeapon(false, false, true);
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
    public void draw(Graphics g2, Player player){
        drawWeapon.finalDraw(g2, this, player);
    }

}
