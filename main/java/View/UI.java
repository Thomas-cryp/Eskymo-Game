package View;

import Controller.GamePanel;
import entity.Enemy;
import entity.Player;

import java.awt.*;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font font1, font2;

    DecimalFormat dFormat = new DecimalFormat("#0.00");
    public UI(GamePanel gp) {
        this.gp = gp;

        font1 = new Font("Arial", Font.PLAIN, 40);
        font2 = new Font("Arial", Font.BOLD, 80);
    }
    public void draw(Graphics2D g2){
        this.g2 = g2;
        g2.setFont(font1);
        g2.setColor(Color.BLACK);

        if(gp.getGameState() == gp.getPlayState()){

        }
        if(gp.getGameState() == gp.getPauseState()){
            drawingPauseStateScreen();
        }
    }
    public void drawingPauseStateScreen(){
        String text = "GAME IS PAUSED";
        int x = halfWidthOfTheScreenWithText(text);
        int y = gp.screenHeight/2;
        g2.drawString(text, x, y);
    }
    public int halfWidthOfTheScreenWithText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }
}
