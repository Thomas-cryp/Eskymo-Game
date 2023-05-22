package infoWidget;


import Controller.GamePanel;
import Model.KeyHandler;

public class Coins extends Items{
    private int counter;
    GamePanel gp;
    KeyHandler keyH;

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public Coins(GamePanel gp) {
        super(gp);
    }
}
