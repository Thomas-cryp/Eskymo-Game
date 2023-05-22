package Controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Loader {
    GamePanel gp;

    public Loader(GamePanel gp) {
        this.gp = gp;
    }
    public void updateLevelInJsonLoader(int newLevel) {
        try {
            // Read the JSON file
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader("JSONs/loader.json"));

            // Parse the JSON object
            JSONObject jsonObject = (JSONObject) obj;

            // Update the level value
            jsonObject.put("level", newLevel);

            // Save the updated JSON back to the file
            FileWriter fileWriter = new FileWriter("JSONs/loader.json");
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.flush();
            fileWriter.close();

            // Print a success message
//            System.out.println("Level value updated successfully to: " + newLevel);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }
    public int getLevelFromJson() {
        try {
            // Read the JSON file
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader("JSONs/loader.json"));

            // Parse the JSON object
            JSONObject jsonObject = (JSONObject) obj;

            // Get the level value
            return ((Long) jsonObject.get("level")).intValue();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }

        // Return a default value if the level value couldn't be retrieved
        return -1;
    }
}
