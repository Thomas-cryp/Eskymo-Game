package Model;

import Controller.GamePanel;

import View.DrawLiveBar;
import java.awt.*;
import java.util.Arrays;


public class Hearts{
    private int[] actualHeartsArray;



    int defaultHearts;
    GamePanel gp;
    DrawLiveBar drawLiveBar;

    public Hearts(GamePanel gp) {
        this.gp = gp;
        drawLiveBar = new DrawLiveBar(gp);
    }
    /**
     * This method is called in Player class.
     * It checks if player is death. Iterate through array of hearts and check if all hearts are empty.
     * @return - true if player is death, false if not.
     */
    public boolean checkIfPlayerIsDeath(){
        int counter = 0;
        for (int i:
             actualHeartsArray) {
            if(i == 0){
                counter++;
            }
        }
        return counter == defaultHearts;
    }

    /**
     * set array of hearts with default length. It is called from Player class when are default values set.
     * @param defaultLengthOfArray - default length of array.
     */
    public void getLengthOfHeartsAndSetArray(int defaultLengthOfArray) {
        defaultHearts = defaultLengthOfArray;
        actualHeartsArray = new int[defaultLengthOfArray];
        Arrays.fill(actualHeartsArray, 2);
    }

    /**
     * decrease number of hearts when player is picked heart after death of enemy.
     */
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
    /**
     * increase number of hearts when player is attacked by enemy.
     */
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
                if(potentialIndex >= 0){
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

    /**
     * call drawing method from DrawLiveBar class. This method draw actual number of hearts.
     * @param g2 - Graphics2D
     */
    public void draw(Graphics g2){
        drawLiveBar.draw(g2, actualHeartsArray);
    }
    public int[] getActualHeartsArray() {
        return actualHeartsArray;
    }
}
