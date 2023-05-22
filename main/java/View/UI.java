package View;

import Controller.GamePanel;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;




public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font font1, font2, font3;
    int buttonWidth, buttonHeight, buttonSpacing, totalHeight, startX, startY;

    public UI(GamePanel gp) {
        this.gp = gp;

        font1 = new Font("Arial", Font.BOLD, 40);
        font2 = new Font("Arial", Font.BOLD, 80);
        font3 = new Font("Arial", Font.BOLD, 20);

        buttonWidth = 200;
        buttonHeight = 50;
    }

    /**
     * draw the pause state screen. when the pause button is pressed
     * draw the inventory screen. when the inventory button is pressed
     * @param g2 - The graphics object
     */
    public void draw(Graphics2D g2){
        this.g2 = g2;
        g2.setFont(font1);
        g2.setColor(Color.BLACK);

        if(gp.getGameState() == gp.getPauseState()){
            drawingPauseStateScreen();
        }
        if(gp.getGameState() == gp.getInventoryState()){
            drawingInventorySite();
        }
    }

    /**
     * draw the pause state screen. when the inventory button is pressed
     */
    public void drawingInventorySite() {
        String text = "INVENTORY";
        String text1 = "press 1 to change to sword";
        String text2 = "press 2 to change to bow and arrows";
        String text3 = "press 3 to change to traps";
        int x = halfWidthOfTheScreenWithText(text);
        int y = gp.getScreenHeight() / 3;
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

    private void drawingPauseStateScreen(){
        String text = "GAME IS PAUSED";
        g2.setFont(font1);
        g2.setColor(Color.BLACK);
        int x = halfWidthOfTheScreenWithText(text);
        int y = gp.getScreenHeight()/2;
        g2.drawString(text, x, y);

    }

    private int halfWidthOfTheScreenWithText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.getScreenWidth()/2 - length/2;
    }

    /**
     * Paints the start of game page with two buttons with given texts.
     * @param g2 - The graphics object
     * @param text1 - The text to be displayed on the first button
     * @param text2 - The text to be displayed on the second button
     */
    public void paintPageWithTwoButtons(Graphics2D g2, String text1, String text2){
        setAndShowFirstButton(g2, text1);

        // Draw the second button
        RoundRectangle2D button2 = new RoundRectangle2D.Double(startX, startY + buttonHeight + buttonSpacing, buttonWidth, buttonHeight, 20, 20);
        g2.setColor(Color.BLACK);
        g2.fill(button2);
        g2.setColor(Color.WHITE);
        FontMetrics fm2 = g2.getFontMetrics();
        int textWidth2 = fm2.stringWidth(text2);
        int textHeight2 = fm2.getHeight();
        int textX2 = startX + buttonWidth / 2 - textWidth2 / 2;
        int textY2 = startY + buttonHeight + buttonSpacing + buttonHeight / 2 + textHeight2 / 2;
        g2.drawString(text2, textX2, textY2);
    }
    public void paintPageWithOneButton(Graphics2D g2, String text){
        setAndShowFirstButton(g2, text);
    }
    /**
     * Paints the end of game page with the given text in the center of the screen
     * @param g2   The graphics object
     * @param text The text to be displayed
     */
    public void paintEndOfGamePage(Graphics2D g2, String text) {
        // Set the background color to white
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());

        // Calculate the position for the text
        FontMetrics fm = g2.getFontMetrics();
        int textHeight = fm.getHeight();

        int textY = (gp.getScreenHeight() - textHeight) / 2;

        // Draw the text
        g2.setColor(Color.BLACK);

        // Set the font to bold with a bigger size
        Font boldFont = new Font(g2.getFont().getName(), Font.BOLD, 24);
        g2.setFont(boldFont);

        // Draw the bold text
        FontMetrics boldFm = g2.getFontMetrics();
        int boldTextWidth = boldFm.stringWidth(text);
        int boldTextX = (gp.getScreenWidth() - boldTextWidth) / 2;
        g2.drawString(text, boldTextX, textY);

    }
    private void setAndShowFirstButton(Graphics2D g2, String text){
        // Set the background color to white
        g2.setColor(Color.WHITE);

        g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());

        // Calculate the positions for the buttons
        buttonWidth = 200;
        buttonHeight = 50;
        buttonSpacing = 20;
        totalHeight = (buttonHeight + buttonSpacing) * 2 - buttonSpacing;
        startX = (gp.getScreenWidth() - buttonWidth) / 2;
        startY = (gp.getScreenHeight() - totalHeight) / 2;

        // Draw the first button
        RoundRectangle2D button1 = new RoundRectangle2D.Double(startX, startY, buttonWidth, buttonHeight, 20, 20);
        g2.setColor(Color.BLACK);
        g2.fill(button1);
        g2.setColor(Color.WHITE);
        FontMetrics fm1 = g2.getFontMetrics();
        int textWidth1 = fm1.stringWidth(text);
        int textHeight1 = fm1.getHeight();
        int textX1 = startX + buttonWidth / 2 - textWidth1 / 2;
        int textY1 = startY + buttonHeight / 2 + textHeight1 / 2;
        g2.drawString(text, textX1, textY1);

    }
}
