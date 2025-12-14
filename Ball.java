import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Actor; 
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

/** 
 * This is the Ball Actor class that controls the behavior of the pong ball.
 */
public class Ball extends Actor {
    private ActorWorld world;
    private Grid grid;
    private double rowFloat;
    private double columnFloat;
    private double startingAngle;
    private double speed = 3.0;
    private double horizontalVelocity;
    private double verticalVelocity;
    private int numberOfRows;
    private int numberOfColumns;
    private Paddle paddleOne;
    private Paddle paddleTwo;
    private boolean pointIsScored = false;
    private PongWorld pongWorld;

    public Ball (PongWorld pongWorld, ActorWorld world, Grid grid, double rowFloat, double columnFloat, double startingAngle, Paddle paddleOne, Paddle paddleTwo) {
        this.pongWorld = pongWorld;
        this.world = world;
        this.grid = grid;
        this.numberOfRows = grid.getNumRows();
        this.numberOfColumns = grid.getNumCols();
        this.rowFloat = rowFloat;
        this.columnFloat = columnFloat;
        this.startingAngle = startingAngle;
        computeVelocities(startingAngle, 1);
        this.paddleOne = paddleOne;
        this.paddleTwo = paddleTwo;
        world.add(this);
        this.moveTo(new Location((int)Math.round(rowFloat), (int)Math.round(columnFloat)));
    }
    
    // The act method that repeats every tick (determines next row/column in path, inverses the rise/run depending on the collision type, and updates score if ball passes paddle)
    public void act() {
        if (!pointIsScored) {
            double nextRowFloat = rowFloat + verticalVelocity;
            double nextColumnFloat = columnFloat + horizontalVelocity;
            if (willHitCorner(nextRowFloat, nextColumnFloat)) {
                verticalVelocity = -verticalVelocity;
                nextRowFloat = rowFloat + verticalVelocity;
                horizontalVelocity = -horizontalVelocity;
                nextColumnFloat = columnFloat + horizontalVelocity;
            } else if (willHitLeftOrRight(nextColumnFloat) || willHitLeftPaddle(nextRowFloat, nextColumnFloat) || willHitRightPaddle(nextRowFloat, nextColumnFloat)) {
                horizontalVelocity = -horizontalVelocity;

                if (willHitLeftPaddle(nextRowFloat, nextColumnFloat)) nextColumnFloat = 2;

                if (willHitRightPaddle(nextRowFloat, nextColumnFloat)) nextColumnFloat = 67;
                
                if (nextColumnFloat < 0 || nextColumnFloat > numberOfColumns - 1) pointIsScored = true;
            } else if (willHitTopOrBottom(nextRowFloat)) {
                verticalVelocity = -verticalVelocity;
                nextRowFloat = rowFloat + verticalVelocity;
            }

            rowFloat = nextRowFloat;
            columnFloat = nextColumnFloat;
            if (!pointIsScored) {
                this.moveTo(new Location((int)Math.round(rowFloat), (int)Math.round(columnFloat)));
            } else {
                if (nextColumnFloat < 0) pongWorld.updateScoreBoard(2); else pongWorld.updateScoreBoard(1);
                pointIsScored = false;
            }
        } 
    }
    
    // Checks whether the ball will hit the top or bottom of the game grid in its next position
    private boolean willHitTopOrBottom(double nextRowFloat) {
        return nextRowFloat < 5 || nextRowFloat > (numberOfRows - 1);
    }
    
    // Checks whether the ball will hit the right or left sides of the game grid in its next position
    private boolean willHitLeftOrRight(double nextColumnFloat) {
        return nextColumnFloat < 0 || nextColumnFloat > (numberOfColumns - 1);
    }

    // Checks whether the ball will hit both the top/bottom and left/right sides at the same time
    private boolean willHitCorner(double nextRowFloat, double nextColumnFloat) {
        return (nextRowFloat < 5 && nextColumnFloat < 0) 
        || (nextRowFloat < 5 && nextColumnFloat > (numberOfColumns - 1)) 
        || (nextRowFloat > (numberOfRows - 1) && nextColumnFloat > (numberOfColumns - 1)) 
        || (nextColumnFloat < 0 && nextRowFloat > (numberOfRows - 1));
    }

    // Checks whether the ball will hit the left paddle in its next position
    private boolean willHitLeftPaddle(double nextRowFloat, double nextColumnFloat) {
        return nextColumnFloat < 3 && (willCollideWithPaddle(nextRowFloat, nextColumnFloat, paddleOne));    
    }

    // Checks whether the ball will hit the right paddle in its next position
    private boolean willHitRightPaddle(double nextRowFloat, double nextColumnFloat) {
        return nextColumnFloat > 66 && (willCollideWithPaddle(nextRowFloat, nextColumnFloat, paddleTwo));
    }
    
    // Checks whether the ball will hit anywhere from the top or bottom corners of a paddle
    private boolean willCollideWithPaddle(double nextRowFloat, double nextColumnFloat, Paddle paddle) {
        return nextRowFloat >= paddle.getTopRow() - 1 && nextRowFloat <= paddle.getBottomRow() + 1;
    }
    
    // Move the ball to a new starting position with the randomly generated row number, launch with a random starting angle, and determine who receives the serve
    public void serveBall(int startingRow, double startingAngle, int player) {
        this.moveTo(new Location(startingRow, 35));
        this.rowFloat = startingRow;
        this.columnFloat = 35;
        this.startingAngle = startingAngle;
        computeVelocities(startingAngle, player);
    }
    
    // The trigonometric calculations for determining the rise and run of the ball's movement based on the starting angle
    public void computeVelocities(double startingAngle, int player) {
        double radians = Math.toRadians(startingAngle);
        this.horizontalVelocity = (player == 1) ? -speed*Math.cos(radians) : speed*Math.cos(radians);
        this.verticalVelocity = -speed*Math.sin(radians);
    }
}