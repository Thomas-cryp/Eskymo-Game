package Controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Logger;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed, inventory, fight, esc, pause;

//    private static final Logger LOGGER = Logger.getLogger(KeyHandler.class.getName());  TODO
    GamePanel gp;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W){
//            LOGGER.info("Pressed W");
            upPressed = true;
        }
        if(code == KeyEvent.VK_S){
//            LOGGER.info("Pressed S");
            downPressed = true;
        }
        if(code == KeyEvent.VK_A){
//            LOGGER.info("Pressed A");
            leftPressed = true;
        }
        if(code == KeyEvent.VK_D){
//            LOGGER.info("Pressed D");
            rightPressed = true;
        }
        if(code == KeyEvent.VK_K){
//            LOGGER.info("Pressed K");
            fight = true;
        }
        if(code == KeyEvent.VK_P){
//            LOGGER.info("Pressed P");
            if(gp.getGameState() == gp.getPlayState()){
                gp.setGameState(gp.getPauseState());
            } else if (gp.getGameState() == gp.getPauseState()) {
                gp.setGameState(gp.getPlayState());
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W){
            upPressed = false;
        }
        if(code == KeyEvent.VK_S){
            downPressed = false;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }
    }
}
