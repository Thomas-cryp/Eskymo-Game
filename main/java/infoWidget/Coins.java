package infoWidget;


import Controller.GamePanel;
import Controller.KeyHandler;

public class Coins extends Items{
    private int counter;

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public Coins(GamePanel gp, KeyHandler keyH) {
        super(gp, keyH);
    }
}
