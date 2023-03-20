package cz.cvut.fel.pjv;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    // screen settings
    final int originalTitleSize = 16;   // 16x16 tile
    final int scale = 3;
    final int tileSize = scale * originalTitleSize; // 48x48 tile, one tile
    final int maxScreenColumn = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenColumn; // 768 pixels
    final int screenHeight = tileSize * maxScreenRow;   // 576 pixels
    // FPS
    int FPS = 60;
    KeyHandler keyH = new KeyHandler(); // instance KeyHandler from KeyHandler class
    Thread gameThread;

    // set player's default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;    // speed of the player

    // constructor
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));    // size of the panel
        this.setBackground(Color.BLACK);    // background color of the panel
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);    // GamePanel can be focused to receive key input
    }
    public void startGameThread(){
        gameThread = new Thread(this);  // we are passing GamePanel to this constructor
        gameThread.start(); // it will call run method
    }

    // if we start some Thread, it will automatically call this method
    // using Delta/Accumulator method
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

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
                drawCount++;
            }
            if(timer >= 1000000000){    // it will print every second
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
    public void update(){

        if(keyH.upPressed){ // move up
            playerY -= playerSpeed;
        }else if(keyH.downPressed){ // move down
            playerY += playerSpeed;
        }else if(keyH.leftPressed){ // move left
            playerX -= playerSpeed;
        }else if(keyH.rightPressed){    //move right
            playerX += playerSpeed;
        }

    }
    public  void paintComponent(Graphics g){

        super.paintComponent(g);    // super means panel class of this method (JPanel)
        Graphics2D g2 = (Graphics2D) g; // convert g to 2D
        g2.setColor(Color.WHITE);
        g2.fillRect(playerX, playerY, tileSize, tileSize);
        g2.dispose();
    }
}
