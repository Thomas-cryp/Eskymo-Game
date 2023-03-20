package cz.cvut.fel.pjv;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // close the window when user click on close button
        window.setResizable(false); // set resizable of window on PC
        window.setTitle("First 2D Game");   // game name

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();  // to fit the size and styles from GamePanel

        window.setLocationRelativeTo(null); // window will be at the center of the screen
        window.setVisible(true);    // window will be visible

        gamePanel.startGameThread();
    }
}