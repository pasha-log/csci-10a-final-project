import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Actor; 
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.awt.Color;

/**
 * The ball that is bounced back and forth between paddles.
 *
 * @author Pasha Loguinov
 * @version 10/25/2025
 * 
 * Has a starting point. 
 * Gets shot towards player one's direction dead-on to start the game.
 * Define the out of bounds coordinates on each side to determine who loses the round.
 * If the ball comes in "contact" with with a paddle perpendicularly, the ball gets returned perpedicularly.
 * If the ball comes in contact diagonally,  
 * 
 * Add continuous position fields (x,y,vx,vy) and a simple act() that moves the ball and updates its grid cell—no collisions.
 * Add top/bottom wall collision.
 * Implement paddle collision with simple vx inversion only.
 * Add offset shaping of vy.
 * Add spin from paddle movement delta.
 * Add speed increases, min horizontal enforcement.
 * Add scoring and reset logic.
 * Add sub-step refinement for higher speeds.
 * Add small vertical perturbation to avoid flat rallies.
 */
public class Ball extends Actor
{
    private ActorWorld world;
    private Grid grid;
    private int row;
    private int column;
    private int horizontalDirection = -1;
    private int verticalDirection = 0;
    private Paddle leftPaddle;
    private Paddle rightPaddle;

    public Ball (ActorWorld world, Grid grid, int row, int column, Paddle leftPaddle, Paddle rightPaddle) {
        this.world = world;
        this.grid = grid;
        this.row = row;
        this.column = column;
        this.leftPaddle = leftPaddle;
        this.rightPaddle = rightPaddle;
        world.add(this);
        this.moveTo(new Location(row, column));
    }

    public void act() {
        if (column < 2 || column > 58) return;

        // if (grid.get(new Location(row, column - 1)) instanceof BlackSquare) {
        // changeVerticalDirectionPolarity(leftPaddle);
        // horizontalDirection = 1;
        // } else if (grid.get(new Location(row, column + 1)) instanceof BlackSquare) {
        // changeVerticalDirectionPolarity(rightPaddle);
        // horizontalDirection = -1;
        // } 

        // if ((grid.get(new Location(row, column - 1)) instanceof BlackSquare && grid.isValid(new Location(row, column - 1))) || (grid.get(new Location(row + 1, column - 1)) instanceof BlackSquare && grid.isValid(new Location(row + 1, column - 1))) || (grid.get(new Location(row - 1, column - 1)) instanceof BlackSquare && grid.isValid(new Location(row - 1, column - 1)))) {
        // System.out.println("We hit the left paddle!");
        // changeVerticalDirectionPolarity(leftPaddle);
        // horizontalDirection = 1;
        // } else if ((grid.get(new Location(row, column + 1)) instanceof BlackSquare && grid.isValid(new Location(row, column + 1))) || (grid.get(new Location(row + 1, column + 1)) instanceof BlackSquare && grid.isValid(new Location(row + 1, column + 1))) || (grid.get(new Location(row - 1, column + 1)) instanceof BlackSquare && grid.isValid(new Location(row - 1, column + 1)))) {
        // System.out.println("We hit the right paddle!");
        // changeVerticalDirectionPolarity(rightPaddle);
        // horizontalDirection = -1;
        // } 

        // } else if (wasVerticalWallHit() && (wasTheLeftPaddleHit() || wasTheRightPaddleHit())) {
        // System.out.println("We hit both the wall and a paddle!");

        if (wasVerticalWallHit()) {
            System.out.println("We have hit a wall!");
            verticalDirection = (verticalDirection < 0) ? 1 : -1;
        } else {
            if (wasTheLeftPaddleHit()) {
                System.out.println("We hit the left paddle!");
                changeVerticalDirectionPolarity(leftPaddle);
                horizontalDirection = 1;
            } else if (wasTheRightPaddleHit()) {
                System.out.println("We hit the right paddle!");
                changeVerticalDirectionPolarity(rightPaddle);
                horizontalDirection = -1;
            } 
        }

        column += horizontalDirection;
        row += verticalDirection;
        this.moveTo(new Location(row, column));
    }

    private void changeVerticalDirectionPolarity(Paddle paddle) {
        if (paddle.getTopRow() == row || paddle.getTopRow() - 1 == row) verticalDirection = -1;
        if (paddle.getCenterRow() == row) verticalDirection = 0;
        if (paddle.getBottomRow() == row || paddle.getBottomRow() + 1 == row) verticalDirection = 1;
        System.out.println("Changing vertical polarity to: " + verticalDirection);
    }

    private boolean wasTheLeftPaddleHit() {
        return grid.get(new Location(row, column - 1)) instanceof BlackSquare || grid.get(new Location(row + 1, column - 1)) instanceof BlackSquare || grid.get(new Location(row - 1, column - 1)) instanceof BlackSquare; 
    }

    private boolean wasTheRightPaddleHit() {
        return grid.get(new Location(row, column + 1)) instanceof BlackSquare || grid.get(new Location(row + 1, column + 1)) instanceof BlackSquare || grid.get(new Location(row - 1, column + 1)) instanceof BlackSquare; 
    }

    private boolean wasVerticalWallHit() {
        return row + 1 > grid.getNumRows() - 1 || row - 1 < 0;
    }
}