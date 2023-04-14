package infoWidget;

import Controller.GamePanel;
import Controller.KeyHandler;

public class Hearts extends Items{
    private int counter;

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public Hearts(GamePanel gp, KeyHandler keyH) {
        super(gp, keyH);
    }
}
