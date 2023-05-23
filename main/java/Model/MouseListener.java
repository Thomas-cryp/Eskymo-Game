package Model;
import Controller.GamePanel;
import Controller.Loader;
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
    /**
     * This method is called in GamePanel class.
     * It checks if the mouse is clicked on the button.
     * @param e - MouseEvent object.
     */
    public void calculateButtonPositions(MouseEvent e){
        int buttonWidth = 200;
        int buttonHeight = 50;
        int buttonSpacing = 20;
        int totalHeight = (buttonHeight + buttonSpacing) * 2 - buttonSpacing;
        int startX = (gp.getScreenWidth() - buttonWidth) / 2;
        int startY = (gp.getScreenHeight() - totalHeight) / 2;
        if (gp.getGameState() == gp.getMainMenu()) {

            // Check if the "Start Game" button is clicked
            if (e.getX() >= startX && e.getX() <= startX + buttonWidth &&
                    e.getY() >= startY && e.getY() <= startY + buttonHeight) {
                loader.updateLevelInJsonLoader(1);
                gp.setGameState(gp.getPlayState());    // Change the game state to playState
                gp.startGameWithGameState();
            }
            // Check if the "Login Game" button is clicked
            if (e.getX() >= startX && e.getX() <= startX + buttonWidth &&
                    e.getY() >= startY + buttonHeight + buttonSpacing &&
                    e.getY() <= startY + 2 * (buttonHeight + buttonSpacing)) {
                gp.setGameState(gp.getPlayState());    // Change the game state to playState
                gp.startGameWithGameState();
            }
        }
        if (gp.getGameState() == gp.getNextLevelPage()) {

            // Check if the "Next Level" button is clicked
            if (e.getX() >= startX && e.getX() <= startX + buttonWidth &&
                    e.getY() >= startY && e.getY() <= startY + buttonHeight) {
                gp.setGameState(gp.getPlayState());    // Change the game state to playState
                gp.startGameWithGameState();
                threat.setStopGameLoop(false);
            }

        }
    }

}
