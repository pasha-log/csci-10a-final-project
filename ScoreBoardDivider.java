import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

/** 
 * The divider between the two players' score numbers.
 */
public class ScoreBoardDivider {
    private ActorWorld world;
    private Grid grid;
    private int startingColumn;
    private BlackSquare [] dividerSquares = new BlackSquare[5];

    public ScoreBoardDivider(ActorWorld world, Grid grid, int startingColumn) {
        this.world = world;
        this.grid = grid;
        this.startingColumn = startingColumn;
    }
    
    // Renders the divider at intiation within the PongWorld class
    public void show() {
        for (int i = 0; i < dividerSquares.length; i++) {
            if (grid.isValid(new Location(0, startingColumn))) {
                if (i % 2 == 0 || i == 0) {
                    dividerSquares[i] = new BlackSquare();
                    world.add(new Location(i, startingColumn), dividerSquares[i]);
                }
            }
        }
    }
}