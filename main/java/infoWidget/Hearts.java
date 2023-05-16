package infoWidget;

import Controller.GamePanel;

import View.DrawLiveBar;
import java.awt.*;
import java.util.Arrays;


public class Hearts{ // TODO extends ITEM
    private int[] actualHeartsArray;

    int defaultHearts;
    GamePanel gp;
    DrawLiveBar drawLiveBar;

    public Hearts(GamePanel gp) {
        this.gp = gp;
        drawLiveBar = new DrawLiveBar(gp);
    }
    public void getLengthOfHeartsAndSetArray(int defaultLengthOfArray) {
        defaultHearts = defaultLengthOfArray;
        actualHeartsArray = new int[defaultLengthOfArray];
        Arrays.fill(actualHeartsArray, 2);
    }
    public void decreaseNumberOfHearts(){
        int indexInArray = -1;
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
    public void increaseNumberOfHeartsWithHalfHeart(){
        boolean fullHeart = false;
        int actualIndex = -1;
        for (int i:
             actualHeartsArray) {
            actualIndex ++;
            if(i == 2){
                fullHeart = true;
            }
            if(fullHeart){
                int potentialIndex = actualIndex - 1;
                if(potentialIndex < 0){
                    System.out.println("I cannot increase number of hearts");
                }else{
                    if(actualHeartsArray[potentialIndex] == 1){
                        actualHeartsArray[potentialIndex] = 2;
                    }
                    if(actualHeartsArray[potentialIndex] == 0){
                        actualHeartsArray[potentialIndex] = 1;
                    }
                }
                break;

            }
        }
    }

    public void draw(Graphics g2){
        drawLiveBar.draw(g2, actualHeartsArray);
    }
}
