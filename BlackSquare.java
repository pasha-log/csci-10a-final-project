import info.gridworld.actor.Actor; 
import java.awt.Color;

/**
 * One of three segments that make up a paddle.
 *
 * @author Pasha Loguinov
 * @version 10/25/2025
 */
public class BlackSquare extends Actor {
    private Color paddleColor;
    
    public BlackSquare (Color color) {
        this.paddleColor = color;
        setColor(paddleColor);
    }
}