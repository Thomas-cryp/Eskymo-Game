package View;

import Controller.GamePanel;
import Model.BowAndArrow;
import Model.Sword;
import Model.Traps;
import Controller.Enemy;

import Controller.Player;
import Controller.Weapons;
import java.awt.*;


public class DrawWeapon {
    GamePanel gp;
    Weapons weapons;
    Player player;
    Enemy bossEnemy;
    Traps traps;
    Sword sword;
    BowAndArrow bowAndArrow;
    private int startX, startY;
    public DrawWeapon(GamePanel gp) {
        this.gp = gp;
    }


    /**
     * draw weapon in play state. it depends on the weapon that player has active. if the boss is active it will create the bossEnemy object
     * @param g2 - graphics2D
     * @param weapons - weapons object
     * @param player - player object
     */
    public void finalDraw(Graphics g2, Weapons weapons, Player player){
        if(gp.isBoss()){
            this.bossEnemy = gp.getBossEnemy();
        }

        this.weapons = weapons;
        this.player = player;
        this.bowAndArrow = weapons.getBowAndArrow();
        this.traps = weapons.getTraps();
        this.sword = weapons.getSword();

        if(gp.getGameState() == gp.getPlayState()){
            if(weapons.isBow()){
                if(!weapons.isWeaponToFightPosition()){
                    bowAndArrow.drawBowAndArrowInStatic();
                }else{
                    bowAndArrow.fireArrowDrawMethod();
                }
                g2.drawImage(bowAndArrow.getBow(), bowAndArrow.getxBow(), bowAndArrow.getyBow(), bowAndArrow.getWidthBow(), bowAndArrow.getHeightBow(), null);
                g2.drawImage(bowAndArrow.getArrow(), bowAndArrow.getxArr(), bowAndArrow.getyArr(), bowAndArrow.getWidthArr(), bowAndArrow.getHeightArr(), null);

            }else if (weapons.isTraps()) {
                if(!weapons.isWeaponToFightPosition()){
                    traps.drawTraps();
                    g2.drawImage(traps.getBow(), traps.getxBow(), traps.getyBow(), traps.getWidthBow(), traps.getHeightBow(), null); // TODO for another image

                }else{
                    traps.holdTheTrapsAndStartCounting();
                    g2.drawImage(traps.getBow(), traps.getxBow(), traps.getyBow(), traps.getWidthBow(), traps.getHeightBow(), null);
                    g2.drawImage(traps.getArrow(), traps.getxArr(), traps.getyArr(), traps.getWidthArr(), traps.getHeightArr(), null);
                }
            }else{  // sword
                if(!weapons.isWeaponToFightPosition()){
                    sword.setSword();
                }else{
                    sword.setSwordInAttack();
                }
                g2.drawImage(sword.getSword(), sword.getxSword(), sword.getySword(), sword.getWidthSword(), sword.getHeightSword(), null);
            }
        }
    }
    public void setStartX(int startX) {
        this.startX = startX;
    }
    public void setStartY(int startY) {
        this.startY = startY;
    }
    public int getStartX() {
        return startX;
    }
    public int getStartY() {
        return startY;
    }
    public Enemy getBossEnemy() {
        return bossEnemy;
    }


}
