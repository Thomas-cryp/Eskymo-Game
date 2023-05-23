package Model;

import Controller.GamePanel;
import Controller.Enemy;

public class Moving {
    GamePanel gp;
    Enemy enemy;

    public Moving(GamePanel gp, Enemy enemy) {
        this.gp = gp;
        this.enemy = enemy;
    }

    /**
     * calculate direction of enemy. Depends on dx and dy. It changes spriteNumber and spriteCounter for change picture of enemy.
     * @param dx - difference between x position of enemy and x position of player.
     * @param dy - difference between y position of enemy and y position of player.
     */
    public void updateImageMoving(double dx, double dy){

        if (Math.abs(dx) > Math.abs(dy)) {
            // Moving horizontally
            if (dx > 0) {
                enemy.setDirection("right");
            } else {
                enemy.setDirection("left");
            }
        } else {
            // Moving vertically
            if (dy > 0) {
                enemy.setDirection("down");
            } else {
                enemy.setDirection("up");
            }
        }

        enemy.setSpriteCounter(enemy.getSpriteCounter() + 1);    // 60 times par second is called this method. Every 13 frames will be changed the picture
        if(enemy.getSpriteCounter() > 13){
            if(enemy.getSpriteNum() == 1){
                enemy.setSpriteNum(2);
            }
            else if(enemy.getSpriteNum() == 2) {
                enemy.setSpriteNum(1);
            }
            enemy.setSpriteCounter(0);
        }
    }
    /**
     * update image of enemy on default values in spawn space.
     */
    public void updateEnemyImageOnDefaultValues(){
        switch (enemy.getDirection()){
            case "up", "down", "right", "left" -> enemy.setDirection("neutralDown");
        }
    }
    /**
     * update image of enemy on default values.
     */
    public void updateImageStanding(){
        switch (enemy.getDirection()){
            case "up" -> enemy.setDirection("neutralUp");
            case "down" -> enemy.setDirection("neutralDown");
            case "right" -> enemy.setDirection("neutralRight");
            case "left" -> enemy.setDirection("neutralLeft");
        }
    }
    /**
     * move enemy on shortest path to player.
     * @param targetX - x position of player
     * @param targetY - y position of player
     * @param speed - speed of enemy
     */
    public void moveShortestPath(int targetX, int targetY, int speed){
        int xEnemy = enemy.getX();
        int yEnemy = enemy.getY();

        double dx = targetX - xEnemy;
        double dy = targetY - yEnemy;
        double angle = Math.atan2(dy, dx);

        xEnemy += speed * Math.cos(angle);
        yEnemy += speed * Math.sin(angle);

        enemy.setX(xEnemy);
        enemy.setY(yEnemy);
        updateImageMoving(dx, dy);
    }

}
