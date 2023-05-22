package Model;

import Controller.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed, fight, number1, number2, number3;

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
            upPressed = true;
        }
        if(code == KeyEvent.VK_S){
            downPressed = true;
        }
        if(code == KeyEvent.VK_A){

            leftPressed = true;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = true;
        }
        if(code == KeyEvent.VK_K){
            gp.getLogWriter().println("K key pressed"); // Log the message to the file
            gp.getLogWriter().flush(); // Flush the PrintWriter to ensure the message is written immediately
            fight = true;
        }
        if(code == KeyEvent.VK_1){

            number1 = true;
        }
        if(code == KeyEvent.VK_2){
            number2 = true;
        }
        if(code == KeyEvent.VK_3){
            number3 = true;
        }
        if(code == KeyEvent.VK_P){
            if(gp.getGameState() == gp.getPlayState()){
                gp.setGameState(gp.getPauseState());
            } else if (gp.getGameState() == gp.getPauseState()) {
                gp.setGameState(gp.getPlayState());
            }
        }
        if(code == KeyEvent.VK_I){
            if(gp.getGameState() == gp.getPlayState()){
                gp.setGameState(gp.getInventoryState());
            } else if (gp.getGameState() == gp.getInventoryState()) {
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
        if(code == KeyEvent.VK_K){
            fight = false;
        }
        if(code == KeyEvent.VK_1){
            number1 = false;
        }if(code == KeyEvent.VK_2){
            number2 = false;
        }if(code == KeyEvent.VK_3){
            number3 = false;
        }
    }
}
