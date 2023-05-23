package Model;

import Controller.Enemy;
import Controller.GamePanel;
import Controller.Player;
import junit.framework.TestCase;

import java.util.ArrayList;

public class BossTest extends TestCase {

    public void testCheckIfIsTimeForBoss() {
        ArrayList<Enemy> enemies = new ArrayList<>();
        GamePanel gp = new GamePanel();
        Player player = new Player(gp);
        Boss boss = new Boss(gp);
        Enemy enemy1 = new Enemy(gp, player);
        enemies.add(enemy1);
        Enemy enemy2 = new Enemy(gp, player);
        enemies.add(enemy2);
        Enemy enemy3 = new Enemy(gp, player);
        enemies.add(enemy3);

        for (Enemy enemy:
             enemies) {
            enemy.setDeath(true);
        }
        boolean expectedResult = true;
        boolean actualResult = boss.checkIfIsTimeForBoss(enemies);
        assertEquals(expectedResult, actualResult);
    }
}