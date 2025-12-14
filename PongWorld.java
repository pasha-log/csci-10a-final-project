import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.actor.Actor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent; 
import javax.swing.JOptionPane;
import javax.swing.Timer;

/** 
 * This is the main controller of the game.
 * 
 * TO-DO:
 * 1. Add sound effects.
 * 2. Deploy game online.
 * 3. Add middle border line.
 * 4. Allow the ball to be flush with the top bottom walls when it bounces.
 * 5. Some how manipulate speed and angle changes mid game to make it more dynamic.
 * 6. Prevent ball from being swallowed by the paddle.
 * 7. Create an AI paddle for single player mode.
 */
public class PongWorld {
    boolean paddleOnePressedUp = false;
    boolean paddleOnePressedDown = false;
    boolean paddleTwoPressedUp = false;
    boolean paddleTwoPressedDown = false;
    int playerOneScore = 0;
    int playerTwoScore = 0;
    int rows = 55, columns = 70; 
    BoundedGrid<Actor> grid = new BoundedGrid<>(rows, columns); 
    ActorWorld world = new ActorWorld(grid);
    Paddle paddleOne = new Paddle(world, grid, 1);
    Paddle paddleTwo = new Paddle(world, grid, 68);
    ScoreBoardDivider divider = new ScoreBoardDivider(world, grid, 35);
    ScoreBoard scoreBoard = new ScoreBoard(world, grid, playerOneScore, playerTwoScore);
    Ball ball;
    
    // Main method that contains input collection and a swing timer learned from GitHub Copilot 
    // (no transcript was available but I copied and pasted the conversation to here: https://docs.google.com/document/d/1ALapV3e6YSvQzSamk3b955Gp8QkTYG5_pViE5Vv1kXM/edit?usp=sharing)
    public static void main(String [] args) {
        PongWorld pongWorld = new PongWorld();
        pongWorld.ball = new Ball(pongWorld, pongWorld.world, pongWorld.grid, pongWorld.generateRandomRowNumber(), 35, pongWorld.generateRandomAngle(), pongWorld.paddleOne, pongWorld.paddleTwo);
        
        // Manages the detection of the up-down keys of each player (by KeyEvent object) and sets a boolean state to keep track of what is pressed down or released
        java.awt.KeyboardFocusManager.getCurrentKeyboardFocusManager()
        .addKeyEventDispatcher(new java.awt.KeyEventDispatcher(){
                public boolean dispatchKeyEvent(KeyEvent event){   
                    int keyCode = event.getKeyCode();
                    if(event.getID() == KeyEvent.KEY_PRESSED) {
                        if (keyCode == KeyEvent.VK_UP) pongWorld.paddleOnePressedUp = true;
                        if (keyCode == KeyEvent.VK_DOWN) pongWorld.paddleOnePressedDown = true;
                        if (keyCode == KeyEvent.VK_W) pongWorld.paddleTwoPressedUp = true;
                        if (keyCode == KeyEvent.VK_S) pongWorld.paddleTwoPressedDown = true;
                    } else if (event.getID() == KeyEvent.KEY_RELEASED) {
                        if (keyCode == KeyEvent.VK_UP) pongWorld.paddleOnePressedUp = false;
                        if (keyCode == KeyEvent.VK_DOWN) pongWorld.paddleOnePressedDown = false;
                        if (keyCode == KeyEvent.VK_W) pongWorld.paddleTwoPressedUp = false;
                        if (keyCode == KeyEvent.VK_S) pongWorld.paddleTwoPressedDown = false;
                    }
                    return false;
                }
            });
            
        // An ActionListener instance that defines method movePaddle to move the paddle if the boolean state allows it
        ActionListener movePaddle = new ActionListener() {
                @Override 
                public void actionPerformed(ActionEvent event) {
                    if (pongWorld.paddleOnePressedUp) pongWorld.paddleOne.movePaddleSegmentsUpDown(true);
                    if (pongWorld.paddleOnePressedDown) pongWorld.paddleOne.movePaddleSegmentsUpDown(false);
                    if (pongWorld.paddleTwoPressedUp) pongWorld.paddleTwo.movePaddleSegmentsUpDown(true);
                    if (pongWorld.paddleTwoPressedDown) pongWorld.paddleTwo.movePaddleSegmentsUpDown(false);
                }
            };
        
        // A swing timer instance which allows to call movePaddle 60 times per second (the purpose of this was to try and solve a keyboard 'ghosting' problem)
        Timer timer = new Timer(1000/60, movePaddle);
        timer.start();
        pongWorld.divider.show();
        pongWorld.scoreBoard.show();
        pongWorld.world.show();  
    }

    // Determine final game outcome, update score, or re-serve
    public void updateScoreBoard(int player) {
        if (player == 1) {
            playerOneScore++; 
            scoreBoard.updateScore(player, playerOneScore);
        } else {
            playerTwoScore++;
            scoreBoard.updateScore(player, playerTwoScore);
        }
        if (playerOneScore == 5 || playerTwoScore == 5) endGame(player);
        this.ball.serveBall(generateRandomRowNumber(), generateRandomAngle(), player);
    }
    
    // Generate random starting row number in between 20 and 40 inclusively
    public int generateRandomRowNumber() {
        return (int)(Math.random() * (40 - 20 + 1)) + 20;
    }
    
    // Generate random starting angle for the ball
    public double generateRandomAngle() {
        return Math.random() * 98.0 - 49.0;
    }
    
    // End the game with a pop-up modal presenting the option for restarting the game 
    // (Suggestion provided by GitHub Copilot, copy and pasted conversation to here: https://docs.google.com/document/d/1inb1F-ueTtJGfddRr6nj84BzPrjUg5TPAN6o_kGKciM/edit?usp=sharing)
    public void endGame(int player) {
        String winnerLabel = (player == 1) ? "Player 1" : "Player 2";
        int choice = JOptionPane.showConfirmDialog(
                null,
                winnerLabel + " wins!\nWanna play again?",
                "Game Over",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (choice == JOptionPane.YES_OPTION) resetGame(); else System.exit(0);
    }
    
    // Reset the game score board and serve the ball
    public void resetGame() {
        this.playerOneScore = 0;
        this.playerTwoScore = 0;
        scoreBoard.updateScore(1, playerOneScore);
        scoreBoard.updateScore(2, playerTwoScore);
        this.ball.serveBall(generateRandomRowNumber(), generateRandomAngle(), 1);
    }
}