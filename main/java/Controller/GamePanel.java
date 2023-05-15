package Controller;

import View.UI;
import entity.Collision;
import entity.Enemy;
import entity.Entity;
import entity.Player;
import infoWidget.Weapons;

import javax.swing.*;

import java.awt.*;
import java.sql.Time;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable{

    // screen settings
    final int originalTitleSize = 16;   // 16x16 tile
    final int scale = 3;
    public final int tileSize = scale * originalTitleSize; // 48x48 tile, one tile
    public final int maxScreenColumn = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenColumn; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow;   // 576 pixels
    // FPS
    int FPS = 60;



    // SET ENEMY
    private int numberOfEnemyInLevel = 2;   // TODO getter and setter
    private ArrayList<Enemy> enemies = new ArrayList<>();

    private int[][] enemyPositions;


    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    private Player player;

    private TileManager tileM;
    private Collision collision;
    KeyHandler keyH = new KeyHandler(this); // instance KeyHandler from KeyHandler class
    Thread gameThread;





    UI ui;
    private int gameState;
    private int inventoryState = 3;
    private int pauseState = 2;
    private int playState = 1;
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

    //    StartAndUpdate startAndUpdate;
    // constructor
    public GamePanel(){

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));    // size of the panel
        this.setBackground(Color.BLACK);    // background color of the panel
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);// GamePanel can be focused to receive key input


        player = new Player(this);
        player.setKeyHandler(keyH);
        tileM = new TileManager(this);
        ui = new UI(this);
        setPositionOfEnemyToDoubleArray();

        collision = new Collision(this, player);
        // create the StartAndUpdate instance and pass the Player and TileManager instances to its constructor
//        startAndUpdate = new StartAndUpdate(player, tileM);
    }

    public void setPositionOfEnemyToDoubleArray(){
        enemyPositions = new int[numberOfEnemyInLevel][2];
        enemyPositions[0][0] = 400;
        enemyPositions[0][1] = 200;
        enemyPositions[1][0] = 300;
        enemyPositions[1][1] = 400;
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
    public void startGameThread(){
        gameState = playState;
        gameThread = new Thread(this);  // we are passing GamePanel to this constructor
        gameThread.start(); // it will call run method
    }

    // if we start some Thread, it will automatically call this method
    // using Delta/Accumulator method
    @Override
    public void run() {
        double drawInterval = (double) 1000000000 /FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;


        while(gameThread != null){  // it will repeat the process as long as this gameThread (method startGameThread) exists
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1){
                // Update info such as character position
                update();   // Draw the screen with the updated info
                repaint();  // it will call paintComponent method
                delta--;

            }
            if(timer >= 1000000000){    // it will print every second
//                System.out.println("FPS: " + drawCount);

                timer = 0;
            }
        }
    }

    public void update(){
        if(gameState == playState){
            player.update();    //call method for update game
            for (Enemy enemy : enemies) {
                enemy.update();
            }
        } else if (gameState == inventoryState) {
            player.getWeapons().listenerForChangeTheWeapon(keyH);
        } else{

        }

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);    // super means panel class of this method (JPanel)
        Graphics2D g2 = (Graphics2D) g; // convert g to 2D
        tileM.drawing(g2); // it has to be before player.draw
        for (Enemy enemy : enemies) {
            enemy.draw(g2);
        }
        ui.draw(g2);
        player.draw(g2);
        g2.dispose();
    }

}
