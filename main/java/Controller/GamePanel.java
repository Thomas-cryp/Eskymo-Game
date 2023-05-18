package Controller;

import View.UI;
import entity.Collision;
import entity.Enemy;

import entity.Player;


import javax.swing.*;

import java.awt.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public class GamePanel extends JPanel{

    // screen settings
    final int originalTitleSize = 16;   // 16x16 tile
    final int scale = 3;
    public final int tileSize = scale * originalTitleSize; // 48x48 tile, one tile
    public final int maxScreenColumn = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenColumn; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow;   // 576 pixels





    // SET ENEMY
    private final int numberOfEnemyInLevel = 1;   // TODO getter and setter
    private ArrayList<Enemy> enemies = new ArrayList<>();

    private int[][] enemyPositions;


    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    private final Player player;

    private final TileManager tileM;

    public TileManager getTileM() {
        return tileM;
    }

    private Collision collision;
    KeyHandler keyH = new KeyHandler(this); // instance KeyHandler from KeyHandler class

    Threat threat;




    UI ui;
    private int gameState;
    private final int inventoryState = 3;
    private final int pauseState = 2;
    private final int playState = 1;
    private final int mainMenu = 4;
    private final int nextLevelPage = 5;

    public int getNextLevelPage() {
        return nextLevelPage;
    }

    public int getMainMenu() {
        return mainMenu;
    }

    public int getInventoryState() {
        return inventoryState;
    }

    public int getGameState() {
        return gameState;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    public int getPauseState() {
        return pauseState;
    }
    public int getPlayState() {
        return playState;
    }
    Enemy bossEnemy;

    private boolean boss;

    public boolean isBoss() {
        return boss;
    }

    public void setBoss(boolean boss) {
        this.boss = boss;
    }

    public Threat getThreat() {
        return threat;
    }

    //    StartAndUpdate startAndUpdate;
    // constructor
    public GamePanel(){

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));    // size of the panel
        this.setBackground(Color.BLACK);    // background color of the panel
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);// GamePanel can be focused to receive key input

        threat = new Threat(this);
        tileM = new TileManager(this);
        player = new Player(this);
        player.setKeyHandler(keyH);

        ui = new UI(this);
        collision = new Collision(this, player);

        gameState = mainMenu;
        addMouseListener();

        // create the StartAndUpdate instance and pass the Player and TileManager instances to its constructor
//        startAndUpdate = new StartAndUpdate(player, tileM);
    }
    public void startGameWithGameState(){
        if(gameState == playState){
            tileM.loadMapAndTileImage();
            player.setDefaultValues();
            player.getPlayerImage();
            setPositionOfEnemyToDoubleArray();
            threat.startGameThread();

        }else{
            // TODO
        }
    }

    public void createBoss(){
        bossEnemy = new Enemy(this, player);
    }
    public Enemy getBossEnemy(){
        return bossEnemy;
    }

    public void setPositionOfEnemyToDoubleArray(){
        enemyPositions = new int[numberOfEnemyInLevel][2];
        enemyPositions[0][0] = 400;
        enemyPositions[0][1] = 200;
//        enemyPositions[1][0] = 300;
//        enemyPositions[1][1] = 400;
        setEnemyList();
    }
    public void setEnemyList(){
        int row = 0;
        int counter = 1;
        for (int i = 0; i < numberOfEnemyInLevel; i++) {
            if(counter == 1){
                int x = enemyPositions[row][0];
                int y = enemyPositions[row][1];
                Enemy enemy = new Enemy(this, player);
                enemy.setPositionEnemy(x, y);
//                enemy.setKeyHandler(keyH);
                enemies.add(enemy);
                counter = 2;
            }else{
                int x = enemyPositions[row][0];
                int y = enemyPositions[row][1];
                Enemy enemy = new Enemy(this, player);
                enemy.setPositionEnemy(x, y);
                enemies.add(enemy);
                counter = 1;
            }
            row ++;

        }
    }
    public boolean checkIfIsTimeForBoss(){
        for (Enemy enemy:
             enemies) {
            if(!enemy.isDeath()){
                return false;
            }
        }
        return true;
    }

    public void update(){
        if(gameState == playState){
            player.update();    //call method for update game
            if(!boss){
                for (Enemy enemy : enemies) {
                    enemy.update();
                }
            }else{
                bossEnemy.update();
            }

        } else if (gameState == inventoryState) {
            player.getWeapons().listenerForChangeTheWeapon(keyH);
        } else{

        }

    }
    private void paintPageWithTwoButtons(Graphics2D g2, String text1, String text2){
        // Set the background color to white
        g2.setColor(Color.WHITE);

        g2.fillRect(0, 0, screenWidth, screenHeight);

        // Calculate the positions for the buttons
        int buttonWidth = 200;
        int buttonHeight = 50;
        int buttonSpacing = 20;
        int totalHeight = (buttonHeight + buttonSpacing) * 2 - buttonSpacing;
        int startX = (screenWidth - buttonWidth) / 2;
        int startY = (screenHeight - totalHeight) / 2;

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

    public void paintComponent(Graphics g){
        super.paintComponent(g);    // super means panel class of this method (JPanel)
        Graphics2D g2 = (Graphics2D) g; // convert g to 2D
        if(gameState == mainMenu){
            paintPageWithTwoButtons(g2, "Start New Game", "Load Game");
        } else if (gameState == nextLevelPage) {
            paintPageWithTwoButtons(g2, "Next Level", "Main menu");
        }
        else{

            tileM.drawing(g2); // it has to be before player.draw
            if(!boss){
                for (Enemy enemy : enemies) {
                    enemy.draw(g2);
                }
            }else{
                bossEnemy.draw(g2);
            }
            ui.draw(g2);
            player.draw(g2);
        }
        g2.dispose();
    }
    public void addMouseListener() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (gameState == mainMenu) {

                    int buttonWidth = 200;
                    int buttonHeight = 50;
                    int buttonSpacing = 20;
                    int totalHeight = (buttonHeight + buttonSpacing) * 2 - buttonSpacing;
                    int startX = (screenWidth - buttonWidth) / 2;
                    int startY = (screenHeight - totalHeight) / 2;

                    // Check if the "Start Game" button is clicked
                    if (e.getX() >= startX && e.getX() <= startX + buttonWidth &&
                            e.getY() >= startY && e.getY() <= startY + buttonHeight) {
                        setGameState(playState);    // Change the game state to playState
                        startGameWithGameState();
                    }
                }
                if(gameState == nextLevelPage){
                    System.out.println("clicked to next level button");
                }
            }
        });
    }
}
