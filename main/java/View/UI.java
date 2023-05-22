package View;

import Controller.GamePanel;
import entity.Enemy;
import entity.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
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
        String text1 = "press 1 to change to sword";
        String text2 = "press 2 to change to bow and arrows";
        String text3 = "press 3 to change to traps";
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
    public void paintPageWithTwoButtons(Graphics2D g2, String text1, String text2){
        // Set the background color to white
        g2.setColor(Color.WHITE);

        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Calculate the positions for the buttons
        int buttonWidth = 200;
        int buttonHeight = 50;
        int buttonSpacing = 20;
        int totalHeight = (buttonHeight + buttonSpacing) * 2 - buttonSpacing;
        int startX = (gp.screenWidth - buttonWidth) / 2;
        int startY = (gp.screenHeight - totalHeight) / 2;

        // Draw the first button

        RoundRectangle2D button1 = new RoundRectangle2D.Double(startX, startY, buttonWidth, buttonHeight, 20, 20);
        g2.setColor(Color.BLACK);
        g2.fill(button1);
        g2.setColor(Color.WHITE);
        FontMetrics fm1 = g2.getFontMetrics();
        int textWidth1 = fm1.stringWidth(text1);
        int textHeight1 = fm1.getHeight();
        int textX1 = (int) (startX + buttonWidth / 2 - textWidth1 / 2);
        int textY1 = (int) (startY + buttonHeight / 2 + textHeight1 / 2);
        g2.drawString(text1, textX1, textY1);

        // Draw the second button

        RoundRectangle2D button2 = new RoundRectangle2D.Double(startX, startY + buttonHeight + buttonSpacing, buttonWidth, buttonHeight, 20, 20);
        g2.setColor(Color.BLACK);
        g2.fill(button2);
        g2.setColor(Color.WHITE);
        FontMetrics fm2 = g2.getFontMetrics();
        int textWidth2 = fm2.stringWidth(text2);
        int textHeight2 = fm2.getHeight();
        int textX2 = (int) (startX + buttonWidth / 2 - textWidth2 / 2);
        int textY2 = (int) (startY + buttonHeight + buttonSpacing + buttonHeight / 2 + textHeight2 / 2);
        g2.drawString(text2, textX2, textY2);
    }
    public void paintEndOfGamePage(Graphics2D g2, String text, String buttonText) {
        // Set the background color to white
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Calculate the position for the text
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        int textX = (gp.screenWidth - textWidth) / 2;
        int textY = (gp.screenHeight - textHeight) / 2;

        // Draw the text
        g2.setColor(Color.BLACK);

        // Set the font to bold with a bigger size
        Font boldFont = new Font(g2.getFont().getName(), Font.BOLD, 24);
        g2.setFont(boldFont);

        // Draw the bold text
        FontMetrics boldFm = g2.getFontMetrics();
        int boldTextWidth = boldFm.stringWidth(text);
        int boldTextX = (gp.screenWidth - boldTextWidth) / 2;
        g2.drawString(text, boldTextX, textY);

        // Calculate the position for the button
        int buttonWidth = 200;
        int buttonHeight = 50;
        int buttonX = (gp.screenWidth - buttonWidth) / 2;
        int buttonY = textY + textHeight + 20;

        // Draw the button
        RoundRectangle2D button = new RoundRectangle2D.Double(buttonX, buttonY, buttonWidth, buttonHeight, 20, 20);
        g2.setColor(Color.BLACK);
        g2.fill(button);
        g2.setColor(Color.WHITE);
        g2.draw(button);
        g2.setFont(new Font("Arial", Font.PLAIN, 18));
        g2.drawString(buttonText, buttonX + 50, buttonY + 30);
    }

}
