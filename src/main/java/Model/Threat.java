package Model;

import Controller.GamePanel;

public class Threat implements Runnable{
    GamePanel gp;
    int FPS = 60;
    Thread gameThread;
    private boolean stopGameLoop = false;



    public Threat(GamePanel gp) {
        this.gp = gp;
    }
    public void setStopGameLoop(boolean stopGameLoop) {
        this.stopGameLoop = stopGameLoop;
    }

    /**
     * create new Thread and start it.
     */
    public void startGameThread(){
        gameThread = new Thread(this);  // we are passing GamePanel to this constructor
        gameThread.start(); // it will call run method
    }

    // if we start some Thread, it will automatically call this method
    // using Delta/Accumulator method
    /**
     * This method is called when the game is started. It is called from startGameThread method.
     */
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

            if(delta >= 1 && !stopGameLoop) {
                // Update info such as character position
                gp.update();   // Draw the screen with the updated info
                gp.repaint();  // it will call paintComponent method
                delta--;
            }
            if(stopGameLoop){
                delta--;
            }
            if(timer >= 1000000000){    // it will print every second
//                System.out.println("FPS: " + drawCount);

                timer = 0;
            }
        }
    }
}
