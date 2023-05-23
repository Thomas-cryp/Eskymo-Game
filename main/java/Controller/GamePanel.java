package Controller;
import Model.*;
import View.UI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class GamePanel extends JPanel{

    // screen settings
    final int originalTitleSize = 16;   // 16x16 tile
    final int scale = 3;
    private final int tileSize = scale * originalTitleSize; // 48x48 tile, one tile
    private final int maxScreenColumn = 16;
    private final int maxScreenRow = 12;
    private final int screenWidth = tileSize * maxScreenColumn; // 768 pixels
    private final int screenHeight = tileSize * maxScreenRow;   // 576 pixels
    private int numberOfEnemyInLevel;
    private final ArrayList<Enemy> enemies = new ArrayList<>();
    private int[][] enemyPositions;


    int actualLevel;
    boolean endOfGame;





    Player player;
    MouseListener mouseListener;
    TileManager tileM;
    KeyHandler keyH = new KeyHandler(this); // instance KeyHandler from KeyHandler class
    Threat threat;
    Loader loader;
    Boss bossClass;
    Enemy bossEnemy;
    UI ui;
    private PrintWriter logWriter;
    private int gameState;
    private final int inventoryState = 3;
    int pauseState = 2;
    private final int playState = 1;
    private final int mainMenu = 4;
    private final int nextLevelPage = 5;
    private final int endOfGamePage = 6;
    private final int deathOfPlayerPage = 7;



    private boolean boss;

    public GamePanel(){

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));    // size of the panel
        this.setBackground(Color.BLACK);    // background color of the panel
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);// GamePanel can be focused to receive key input

        threat = new Threat(this);
        tileM = new TileManager(this);
        player = new Player(this);
        player.setKeyHandler(keyH);
        loader = new Loader(this);
        bossClass = new Boss(this);
        mouseListener = new MouseListener(this, loader, threat);
        ui = new UI(this);

        gameState = mainMenu;
        addMouseListener();
        initializeNewPlayerAndLogger();

    }
    private void initializeNewPlayerAndLogger(){
        // Initialize the log file
        try {
            logWriter = new PrintWriter(new FileWriter("JSONs/game.log", true));
            logWriter.println(" ");
            logWriter.println("New Player: "); // Add "New Player" message when a new game is started
            logWriter.flush(); // Flush the PrintWriter to ensure the message is written immediately
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Starts the game based on the current game state.
     * If the game state is playState, it loads the map, sets default player values,
     * retrieves the player image, and starts the game thread.
     */
    public void startGameWithGameState(){
        if(gameState == playState){
            tileM.loadMapAndTileImage();
            player.setDefaultValues();
            player.getPlayerImage();
            setPositionOfEnemyToDoubleArray();
            threat.startGameThread();

        }
    }
    /**
     * Creates a boss enemy for the game.
     */
    public void createBoss(){
        bossEnemy = new Enemy(this, player);
    }
    /**
     * Sets the boss enemy to null.
     */
    public void setBossEnemyOnNull() {
        boss = false;
        this.bossEnemy = null;
    }

    private void setPositionOfEnemyToDoubleArray(){
        actualLevel = loader.getLevelFromJson();
        if(actualLevel == 1){
            numberOfEnemyInLevel = 3;
            enemyPositions = new int[numberOfEnemyInLevel][2];
            enemyPositions[0][0] = 400;
            enemyPositions[0][1] = 400;
            enemyPositions[1][0] = 400;
            enemyPositions[1][1] = 300;
            enemyPositions[2][0] = 500;
            enemyPositions[2][1] = 100;
            setEnemyList();
        }
        if(actualLevel == 2){
            enemies.clear();
            setBoss(false);
            numberOfEnemyInLevel = 2;
            enemyPositions = new int[numberOfEnemyInLevel][2];
            enemyPositions[0][0] = 400;
            enemyPositions[0][1] = 400;
            enemyPositions[1][0] = 500;
            enemyPositions[1][1] = 400;
//            enemyPositions[2][0] = 600;
//            enemyPositions[2][1] = 500;
//            enemyPositions[3][0] = 500;
//            enemyPositions[3][1] = 500;
//            enemyPositions[4][0] = 500;
//            enemyPositions[4][1] = 400;
            setEnemyList();
        }

    }
    private void setEnemyList(){
        int row = 0;
        int counter = 1;
        for (int i = 0; i < numberOfEnemyInLevel; i++) {
            int x = enemyPositions[row][0];
            int y = enemyPositions[row][1];
            Enemy enemy = new Enemy(this, player);
            enemy.setPositionEnemy(x, y);
            enemies.add(enemy);
            if(counter == 1){
                counter = 2;
            }else{
                counter = 1;
            }
            row ++;

        }
    }

    /**
     * Call another method in the boss class to check if it is time for the boss to appear.
     * @return boolean value if it is time for the boss to appear.
     */
    public boolean checkIfIsTimeForBoss(){
        return bossClass.checkIfIsTimeForBoss(enemies);
    }

    /**
     * Updates the game state by calling the update methods of player and enemies.
     * If the game state is inventoryState, it handles weapon changes. It is called by the game thread.
     */
    public void update(){
        if(gameState == playState){
            player.update();    //call method for update game
            if(!boss){
                for (Enemy enemy : enemies) {
                    enemy.update();
                }
            }else{
                bossEnemy.update();
            }

        }if (gameState == inventoryState) {
            player.getWeapons().listenerForChangeTheWeapon(keyH);
        }

    }

    /**
     * Draws the game based on the current game state. It checked the game state and call the right method.
     * If is boss state, it draws the boss enemy.
     * @param g the graphics object
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);    // super means panel class of this method (JPanel)
        Graphics2D g2 = (Graphics2D) g; // convert g to 2D
        if(gameState == mainMenu){
            ui.paintPageWithTwoButtons(g2, "Start New Game", "Load Game");
        } else if (gameState == nextLevelPage) {
            ui.paintPageWithOneButton(g2, "Next Level");
        } else if (gameState == endOfGamePage || gameState == deathOfPlayerPage) {
            if(gameState == endOfGamePage){
                ui.paintEndOfGamePage(g2, "END OF GAME");
            }if(gameState == deathOfPlayerPage){
                ui.paintEndOfGamePage(g2, "YOU DIED");
            }
        }
        else{
            tileM.drawing(g2); // it has to be before player.draw

            for (Enemy enemy : enemies) {
                enemy.drawHeartAfterDeathOFEnemy(g2);
                if(!boss){
                    enemy.draw(g2);
                }
            }
            if(boss){
                bossEnemy.draw(g2);
            }
            ui.draw(g2);
            player.draw(g2);
        }
        g2.dispose();
    }
    private void addMouseListener() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mouseListener.calculateButtonPositions(e);
            }
        });
    }

    /**
     * setter for the end of game state. If the end of game state is true, it sets the game state to end of game page.
     * @param endOfGame boolean value
     */
    public void setEndOfGame(boolean endOfGame) {
        if(endOfGame){
            gameState = endOfGamePage;
        }
        this.endOfGame = endOfGame;
    }





    public int getDeathOfPlayerPage() {
        return deathOfPlayerPage;
    }
    public Enemy getBossEnemy(){
        return bossEnemy;
    }
    public boolean isBoss() {
        return boss;
    }
    public void setBoss(boolean boss) {
        this.boss = boss;
    }
    public Threat getThreat() {
        return threat;
    }
    public int getNextLevelPage() {
        return nextLevelPage;
    }
    public int getMainMenu() {
        return mainMenu;
    }
    public int getInventoryState() {
        return inventoryState;
    }
    public int getGameState() {
        return gameState;
    }
    public void setGameState(int gameState) {
        this.gameState = gameState;
    }
    public int getPauseState() {
        return pauseState;
    }
    public int getPlayState() {
        return playState;
    }
    public Loader getLoader() {
        return loader;
    }
    public PrintWriter getLogWriter() {
        return logWriter;
    }
    public TileManager getTileM() {
        return tileM;
    }
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
    public int getTileSize() {
        return tileSize;
    }
    public int getMaxScreenColumn() {
        return maxScreenColumn;
    }
    public int getMaxScreenRow() {
        return maxScreenRow;
    }
    public int getScreenWidth() {
        return screenWidth;
    }
    public int getScreenHeight() {
        return screenHeight;
    }
}