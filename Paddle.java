import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

/** 
 * This is the Paddle class that creates the paddles of each player.
 */
public class Paddle {
    private ActorWorld world;
    private Grid grid;
    private BlackSquare paddleSegmentTop = new BlackSquare();
    private BlackSquare paddleSegmentCenter = new BlackSquare();
    private BlackSquare paddleSegmentBottom = new BlackSquare();
    private int topRow = 24;
    private int centerRow = 25;
    private int bottomRow = 26;
    private int column;
    
    public Paddle (ActorWorld world, Grid grid, int column) {
        this.world = world;
        this.grid = grid;
        this.column = column;
        world.add(paddleSegmentTop);
        world.add(paddleSegmentCenter);
        world.add(paddleSegmentBottom);
        paddleSegmentTop.moveTo(new Location(topRow, column));
        paddleSegmentCenter.moveTo(new Location(centerRow, column));
        paddleSegmentBottom.moveTo(new Location(bottomRow, column));
    }
    
    // Moves the paddle up or down depending on goUp using the pre-decrement/pre-increment operators
    public void movePaddleSegmentsUpDown(Boolean goUp) {
        if (goUp) {
            if (grid.isValid(new Location(topRow - 1, column)) && topRow > 5) {
                paddleSegmentTop.moveTo(new Location(--topRow, column));
                paddleSegmentCenter.moveTo(new Location(--centerRow, column));
                paddleSegmentBottom.moveTo(new Location(--bottomRow, column));
            }
        } else {
            if (grid.isValid(new Location(bottomRow + 1, column))) {
                paddleSegmentBottom.moveTo(new Location(++bottomRow, column));
                paddleSegmentCenter.moveTo(new Location(++centerRow, column));
                paddleSegmentTop.moveTo(new Location(++topRow, column));
            }
        }
    }

    public int getTopRow() {
        return topRow;
    }

    public int getCenterRow() {
        return centerRow;
    }

    public int getBottomRow() {
        return bottomRow;
    }

    public int getColumn() {
        return column;
    }
}