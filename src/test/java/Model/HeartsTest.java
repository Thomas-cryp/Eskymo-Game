package Model;

import Controller.GamePanel;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;

public class HeartsTest extends TestCase {
    @Test
    public void testGetLengthOfHeartsAndSetArray(){
        GamePanel gp = new GamePanel();
        Hearts hearts = new Hearts(gp);
        hearts.getLengthOfHeartsAndSetArray(3);
        int expectedResult = 2;
        for (int i:
             hearts.getActualHeartsArray()) {
            Assert.assertEquals(expectedResult, i);
        }
    }
    @Test
    public void testDecreaseNumberOfHearts() {
        GamePanel gp = new GamePanel();
        Hearts hearts = new Hearts(gp);
        hearts.getLengthOfHeartsAndSetArray(3);
        hearts.decreaseNumberOfHearts();
        int expectedResultAfterDecreasing = 1;
        int expectedResultsAfterFilling = 2;
        int index = 0;
        for (int i:
                hearts.getActualHeartsArray()) {
            if(index == 0){
                Assert.assertEquals(expectedResultAfterDecreasing, i);
            }
            else{
                Assert.assertEquals(expectedResultsAfterFilling, i);
            }
            index++;
        }
    }

    public void testIncreaseNumberOfHeartsWithHalfHeart() {
        GamePanel gp = new GamePanel();
        Hearts hearts = new Hearts(gp);
        hearts.getLengthOfHeartsAndSetArray(3);
        hearts.decreaseNumberOfHearts();
        hearts.increaseNumberOfHeartsWithHalfHeart();
        int expectedResult = 2;
        for (int i:
                hearts.getActualHeartsArray()) {
            Assert.assertEquals(expectedResult, i);
        }
    }
}