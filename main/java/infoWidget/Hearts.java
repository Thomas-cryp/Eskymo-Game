package infoWidget;

import Controller.GamePanel;
import Controller.KeyHandler;
import View.DrawLiveBar;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;

public class Hearts{ // TODO extends ITEM
    private int[] actualHeartsArray;
    private int counter;
    private int defaultHearts;
    GamePanel gp;
    KeyHandler keyH;
    DrawLiveBar drawLiveBar;

    public Hearts(GamePanel gp) {
        drawLiveBar = new DrawLiveBar(gp);
    }
    public void getLengthOfHeartsAndSetArray(int defaultLengthOfArray) {
        defaultHearts = defaultLengthOfArray;
        actualHeartsArray = new int[defaultLengthOfArray];
        Arrays.fill(actualHeartsArray, 2);
    }
    public void decreaseNumberOfHearts(){
        int indexInArray = -1;
//        for (int i:
//             actualHeartsArray) {
//            System.out.println(i);
//        }
        for (int i:
             actualHeartsArray) {
            indexInArray ++;
            if(i == 1){
                actualHeartsArray[indexInArray] = 0;
                break;
            }
            if(i == 2){
                actualHeartsArray[indexInArray] = 1;
                break;
            }
        }
    }

    public void draw(Graphics g2){
        drawLiveBar.draw(g2, actualHeartsArray);
    }
    public void setLiveBarFromInput(int actualHearts){
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
