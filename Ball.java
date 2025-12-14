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

        int nextRow = row + verticalDirection;
        int nextColumn = column + horizontalDirection;
        boolean willHitVerticalWall = willHitVerticalWall(nextRow);
        boolean willHitLeftPaddle = willHitLeftPaddle(nextRow, nextColumn);
        boolean willHitRightPaddle = willHitRightPaddle(nextRow, nextColumn);

        if (willHitVerticalWall && (willHitLeftPaddle || willHitRightPaddle)) { // Hitting a corner
            System.out.println("We hit a corner!");
            horizontalDirection = -horizontalDirection;
            verticalDirection = (Math.random() < .5) ? 0 : -verticalDirection;
            nextRow = row + verticalDirection;
            nextColumn = column + horizontalDirection;
        } else if (willHitVerticalWall) { // Hitting a wall
            System.out.println("We hit a wall!");
            verticalDirection = (verticalDirection < 0) ? 1 : -1; 
            nextRow = row + verticalDirection;
            nextColumn = column + horizontalDirection;
        } else if (willHitLeftPaddle || willHitRightPaddle) { // Hitting one of the paddles
            System.out.println("We hit a paddle!");
            if (willHitLeftPaddle) {
                System.out.println("We hit the left paddle!");
                changeVerticalDirectionPolarity(nextRow, leftPaddle);
                horizontalDirection = 1;
                nextRow = row + verticalDirection;
                nextColumn = column + horizontalDirection;
            } else if (willHitRightPaddle) {
                System.out.println("We hit the right paddle!");
                changeVerticalDirectionPolarity(nextRow, rightPaddle);
                horizontalDirection = -1;
                nextRow = row + verticalDirection;
                nextColumn = column + horizontalDirection;
            }
        }

        column = nextColumn;
        row = nextRow;
        this.moveTo(new Location(row, column));
    }

    private void changeVerticalDirectionPolarity(int nextRow, Paddle paddle) {
        if (paddle.getTopRow() == nextRow) verticalDirection = -1;
        if (paddle.getCenterRow() == nextRow) verticalDirection = 0;
        if (paddle.getBottomRow() == nextRow) verticalDirection = 1;
        System.out.println("Changing vertical polarity to: " + verticalDirection);
    }

    private boolean willHitVerticalWall(int nextRow) {
        return nextRow < 0 || nextRow > grid.getNumRows() - 1;
    }

    private boolean willHitLeftPaddle(int nextRow, int nextColumn) {
        return horizontalDirection < 0 && nextColumn == leftPaddle.getColumn() && (nextRow >= leftPaddle.getTopRow() - 1 && nextRow <= leftPaddle.getBottomRow() + 1 );
    }

    private boolean willHitRightPaddle(int nextRow, int nextColumn) {
        return horizontalDirection > 0 && nextColumn == rightPaddle.getColumn() && (nextRow >= rightPaddle.getTopRow() - 1 && nextRow <= rightPaddle.getBottomRow() + 1 );
    }
}