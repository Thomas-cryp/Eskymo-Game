package View;

import Controller.GamePanel;
import entity.Enemy;
import entity.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;



public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font font1, font2, font3;

    DecimalFormat dFormat = new DecimalFormat("#0.00");
    public UI(GamePanel gp) {
        this.gp = gp;

        font1 = new Font("Arial", Font.BOLD, 40);
        font2 = new Font("Arial", Font.BOLD, 80);
        font3 = new Font("Arial", Font.BOLD, 20);
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
        if(gp.getGameState() == gp.getInventoryState()){
            drawingInventorySite();
        }
    }
    public void drawingInventorySite() {
        String text = "INVENTORY";
        String text1 = "pressed 1 to change to sword";
        String text2 = "pressed 2 to change to bow and arrows";
        String text3 = "pressed 3 to change to traps";
        int x = halfWidthOfTheScreenWithText(text);
        int y = gp.screenHeight / 3;
        g2.setFont(font1);
        g2.setColor(Color.BLACK);
        g2.drawString(text, x, y);

        g2.setFont(font3);
        g2.setColor(Color.GRAY);
        int xFor1 = halfWidthOfTheScreenWithText(text1);
        int xFor2 = halfWidthOfTheScreenWithText(text2);
        int xFor3 = halfWidthOfTheScreenWithText(text3);
        g2.drawString(text1, xFor1, (y + 100));
        g2.drawString(text2, xFor2, (y + 150));
        g2.drawString(text3, xFor3, (y + 200));

    }

    public void drawingPauseStateScreen(){
        String text = "GAME IS PAUSED";
        g2.setFont(font1);
        g2.setColor(Color.BLACK);
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
