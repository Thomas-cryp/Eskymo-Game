package Controller;

import Model.*;
import View.UI;
import entity.Enemy;

import entity.Player;


import javax.swing.*;

import java.awt.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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


    public int getNumberOfEnemyInLevel() {
        return numberOfEnemyInLevel;
    }

    public void setNumberOfEnemyInLevel(int numberOfEnemyInLevel1) {
        this.numberOfEnemyInLevel = numberOfEnemyInLevel1;
    }

    // SET ENEMY
    private int numberOfEnemyInLevel;  // TODO getter and setter
    private ArrayList<Enemy> enemies = new ArrayList<>();

    private int[][] enemyPositions;
    private int actualLevel;
    private boolean endOfGame;

    public boolean isEndOfGame() {
        return endOfGame;
    }

    public void setEndOfGame(boolean endOfGame) {
        if(endOfGame){
            gameState = endOfGamePage;
        }
        this.endOfGame = endOfGame;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    private final Player player;
    private MouseListener mouseListener;

    private final TileManager tileM;

    public TileManager getTileM() {
        return tileM;
    }

    private Collision collision;
    KeyHandler keyH = new KeyHandler(this); // instance KeyHandler from KeyHandler class

    Threat threat;
    Loader loader;
    Boss bossClass;

    public Loader getLoader() {
        return loader;
    }

    UI ui;
    private int gameState;
    private final int inventoryState = 3;
    private final int pauseState = 2;
    private final int playState = 1;
    private final int mainMenu = 4;
    private final int nextLevelPage = 5;
    private final int endOfGamePage = 6;

    public int getEndOfGamePage() {
        return endOfGamePage;
    }

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
        loader = new Loader(this);
        bossClass = new Boss(this);
        mouseListener = new MouseListener(this, loader, threat);

        ui = new UI(this);
        collision = new Collision(this, player);

        gameState = mainMenu;
        addMouseListener();

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

    public void setBossEnemyOnNull() {
        this.bossEnemy = null;
    }

    public void setPositionOfEnemyToDoubleArray(){
        actualLevel = loader.getLevelFromJson();
        if(actualLevel == 1){
            numberOfEnemyInLevel = 1;
            enemyPositions = new int[numberOfEnemyInLevel][2];
            enemyPositions[0][0] = 400;
            enemyPositions[0][1] = 400;
//            enemyPositions[1][0] = 500;
//            enemyPositions[1][1] = 400;
//            enemyPositions[2][0] = 600;
//            enemyPositions[2][1] = 500;
            setEnemyList();
        }
        if(actualLevel == 2){
            enemies.clear();
            setBoss(false);
            numberOfEnemyInLevel = 2;
            enemyPositions = new int[numberOfEnemyInLevel][2];
            enemyPositions[0][0] = 400;
            enemyPositions[0][1] = 400;
            enemyPositions[1][0] = 500;
            enemyPositions[1][1] = 400;
//            enemyPositions[2][0] = 600;
//            enemyPositions[2][1] = 500;
//            enemyPositions[3][0] = 500;
//            enemyPositions[3][1] = 500;
//            enemyPositions[4][0] = 500;
//            enemyPositions[4][1] = 400;
            setEnemyList();
        }

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
        return bossClass.checkIfIsTimeForBoss(enemies);
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


    public void paintComponent(Graphics g){
        super.paintComponent(g);    // super means panel class of this method (JPanel)
        Graphics2D g2 = (Graphics2D) g; // convert g to 2D
        if(gameState == mainMenu){
            ui.paintPageWithTwoButtons(g2, "Start New Game", "Load Game");
        } else if (gameState == nextLevelPage) {
            ui.paintPageWithTwoButtons(g2, "Next Level", "Main menu");
        } else if (gameState == endOfGamePage) {
            ui.paintEndOfGamePage(g2, "END OF GAME", "Main Menu");
        } else{
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
                mouseListener.calculateButtonPositions(e);
            }
        });
    }
}
