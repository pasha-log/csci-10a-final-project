import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Location;
import info.gridworld.actor.Actor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent; 
import javax.swing.Timer;
import java.awt.Color;

/**
 * The grid where all the action happens.
 *
 * @author Pasha Loguinov
 * @version 10/25/2025
 */
public class PongWorld
{
    boolean paddleOnePressedUp = false;
    boolean paddleOnePressedDown = false;
    boolean paddleTwoPressedUp = false;
    boolean paddleTwoPressedDown = false;

    public static void main(String [] args) {
        int rows = 45, columns = 61; 
        BoundedGrid<Actor> grid = new BoundedGrid<>(rows, columns); 
        ActorWorld world = new ActorWorld(grid);
        Paddle paddleOne = new Paddle(world, grid, 1);
        Paddle paddleTwo = new Paddle(world, grid, 59);
        Ball ball = new Ball(world, grid, 20, 29, paddleOne, paddleTwo);
        PongWorld pongWorld = new PongWorld();

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

        ActionListener movePaddle = new ActionListener() {
                @Override 
                public void actionPerformed(ActionEvent event) {
                    if (pongWorld.paddleOnePressedUp) paddleOne.movePaddleSegmentsUpDown(true);
                    if (pongWorld.paddleOnePressedDown) paddleOne.movePaddleSegmentsUpDown(false);
                    if (pongWorld.paddleTwoPressedUp) paddleTwo.movePaddleSegmentsUpDown(true);
                    if (pongWorld.paddleTwoPressedDown) paddleTwo.movePaddleSegmentsUpDown(false);
                }
            };

        Timer timer = new Timer(1000/30, movePaddle);
        timer.start();
        world.show();    
    }
}