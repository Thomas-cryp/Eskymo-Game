//package Controller;
//
//import Model.tile.TilesImport;
//import entity.Player;
//
//import java.awt.*;
//import javax.swing.*;
//
//public class StartAndUpdate extends JPanel{
//    Player player;
//    TileManager tileM;
//    public StartAndUpdate(Player player, TileManager tileM) {
//        this.tileM = tileM;
//        this.player = player;
//    }
//
//    public void update(){
//        player.update();    //call method for update game
//    }
//    public void paintComponent(Graphics g){
//        System.out.println("test");
//        super.paintComponent(g);    // super means panel class of this method (JPanel)
//        Graphics2D g2 = (Graphics2D) g; // convert g to 2D
//        tileM.drawing(g2); // it has to be before player.draw
//
//        player.draw(g2);
//        g2.dispose();
//    }
//}
