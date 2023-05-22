package Model;

import Controller.GamePanel;
import Controller.Loader;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseListener {
    GamePanel gp;
    Loader loader;
    Threat threat;

    public MouseListener(GamePanel gp, Loader loader, Threat threat) {
        this.gp = gp;
        this.loader = loader;
        this.threat = threat;
    }
    public void calculateButtonPositions(MouseEvent e){
        if (gp.getGameState() == gp.getMainMenu()) {
            int buttonWidth = 200;
            int buttonHeight = 50;
            int buttonSpacing = 20;
            int totalHeight = (buttonHeight + buttonSpacing) * 2 - buttonSpacing;
            int startX = (gp.screenWidth - buttonWidth) / 2;
            int startY = (gp.screenHeight - totalHeight) / 2;

            // Check if the "Start Game" button is clicked
            if (e.getX() >= startX && e.getX() <= startX + buttonWidth &&
                    e.getY() >= startY && e.getY() <= startY + buttonHeight) {
                loader.updateLevelInJsonLoader(1);
                gp.setGameState(gp.getPlayState());    // Change the game state to playState
                gp.startGameWithGameState();
            }
            // Check if the "Login Game" button is clicked
//                    if (// TODO) {
//                        startGameWithGameState();
//                    }
        }
        if (gp.getGameState() == gp.getNextLevelPage()) {
            int buttonWidth = 200;
            int buttonHeight = 50;
            int buttonSpacing = 20;
            int totalHeight = (buttonHeight + buttonSpacing) * 2 - buttonSpacing;
            int startX = (gp.screenWidth - buttonWidth) / 2;
            int startY = (gp.screenHeight - totalHeight) / 2;

            // Check if the "Next Level" button is clicked
            if (e.getX() >= startX && e.getX() <= startX + buttonWidth &&
                    e.getY() >= startY && e.getY() <= startY + buttonHeight) {

                gp.setGameState(gp.getPlayState());    // Change the game state to playState
                gp.startGameWithGameState();
                threat.setStopGameLoop(false);
            }
            // Check if the "Main menu" button is clicked
//                    if (// TODO) {
//                        setGameState(mainMenu);    // Change the game state to mainMenu
//                        startGameWithGameState();
//                    }
        }
        if (gp.getGameState() == gp.getEndOfGamePage()) {
            int buttonWidth = 200;
            int buttonHeight = 50;
            int buttonSpacing = 20;
            int startX = (gp.screenWidth - buttonWidth) / 2;
            int startY = (gp.screenHeight - buttonHeight) / 2;

            // Check if the "Main menu" button is clicked
            if (e.getX() >= startX && e.getX() <= startX + buttonWidth &&
                    e.getY() >= startY && e.getY() <= startY + buttonHeight) {
                gp.setGameState(gp.getMainMenu());    // Change the game state to mainMenu
                loader.updateLevelInJsonLoader(1);
                gp.startGameWithGameState();
            }
        }
    }

}
