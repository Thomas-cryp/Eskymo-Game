package Controller;

import Model.BowAndArrow;
import Model.KeyHandler;
import Model.Traps;
import View.DrawWeapon;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Weapons{

    GamePanel gp;
    File jsonFile;
    BowAndArrow bowAndArrow;
    Traps trapsClass;
    JSONObject data;
    DrawWeapon drawWeapon;
    boolean traps, bow;
    private boolean weaponToFightPosition;

    public Weapons(GamePanel gp, Player player) {
        this.gp = gp;
        this.drawWeapon = new DrawWeapon(gp);
        this.jsonFile = new File("JSONs/actualWeapons.json");
        this.bowAndArrow = new BowAndArrow(gp, player, this);
        this.trapsClass = new Traps(gp, player, this);
        fileReader();
    }

    /**
     * increase damage and set fight boolean value for specified enemy in parameter.
     * @param enemy - specified enemy for which we want to increase damage and set fight boolean value.
     */
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

    private void setSword() {
        setWeapon(true, false, false);
    }

    /**
     * set weapon value in json file.
     * @param value1 - sword
     * @param value2 - bow
     * @param value3 - traps
     */
    public void setWeapon(boolean value1, boolean value2, boolean value3){
        data.put("sword", value1);
        data.put("bow", value2);
        data.put("traps", value3);
        writeFile();
    }
    /**
     * get boolean value from json file for bow.
     * @return - boolean value for bow. if true - bow is active, if false - bow is not active.
     */
    public boolean isBow() {
        bow = (boolean) data.getOrDefault("bow", false);
        return bow;
    }
    private void setBow() {
        setWeapon(false, true, false);
    }
    /**
     * get boolean value from json file for traps.
     * @return - boolean value for traps. if true - traps is active, if false - traps is not active.
     */
    public boolean isTraps() {
        traps = (boolean) data.getOrDefault("traps", false);
        return traps;
    }
    private void setTraps() {
        setWeapon(false, false, true);
    }

    /**
     * listener for change the weapon in inventory stateGame. if is called when the inventory stateGame is active.
     * @param keyH - keyHandler object.
     */
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
    /**
     * set default values of weapons.
     */
    public void setDefaultValuesOfWeapons(){
        setSword();
    }

    /**
     * draw weapon in stateGame. it is called when the stateGame is active.
     * @param g2 - graphics object.
     * @param player - player object.
     */
    public void draw(Graphics g2, Player player){
        drawWeapon.finalDraw(g2, this, player);
    }





    public boolean isWeaponToFightPosition() {
        return weaponToFightPosition;
    }

    public void setWeaponToFightPosition(boolean weaponToFightPosition) {
        this.weaponToFightPosition = weaponToFightPosition;
    }
    public Traps getTraps() {
        return trapsClass;
    }

    public BowAndArrow getBowAndArrow() {
        return bowAndArrow;
    }
    public DrawWeapon getDrawWeapon() {
        return drawWeapon;
    }

}
