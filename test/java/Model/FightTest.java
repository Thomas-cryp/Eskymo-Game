package Model;

import Controller.GamePanel;
import Controller.Enemy;
import Controller.Player;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;

public class FightTest extends TestCase {

    @Test
    public void testTimerAfterAttackPlayerByEnemy() {
        GamePanel gp = new GamePanel();
        Player player = new Player(gp);
        Enemy enemy = new Enemy(gp, player);
        Fight fight = new Fight(gp, player, enemy);
        fight.setCounterOfSlowestFrames(0);
        for (int i = 1; i < 30; i++) {
            assertFalse(fight.timerAfterAttackPlayerByEnemy());
        }
        assertTrue(fight.timerAfterAttackPlayerByEnemy());
    }

    @Test
    public void testCalculateHypotenuseForTrap() {
        GamePanel gp = new GamePanel();
        Player player = new Player(gp);
        Enemy enemy = new Enemy(gp, player);
        Fight fight = new Fight(gp, player, enemy);
        enemy.setX(3);
        enemy.setY(0);
        int xWeapon = 3;
        int yWeapon = 5;
        double expectedResult = 5;
        double actualResult = fight.calculateHypotenuseForTrap(xWeapon, yWeapon);
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testCalculateHypotenuse() {
        GamePanel gp = new GamePanel();
        Player player = new Player(gp);
        Enemy enemy = new Enemy(gp, player);
        Fight fight = new Fight(gp, player, enemy);
        player.setX(3);
        player.setY(5);
        enemy.setX(3);
        enemy.setY(0);
        double expectedResult = 5;
        double actualResult = fight.calculateHypotenuse();
        Assert.assertEquals(expectedResult, actualResult);
    }
}