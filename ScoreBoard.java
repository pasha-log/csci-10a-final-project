import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.actor.Actor;

/** 
 * This is the ScoreBoard class that updates the scores of the players.
 * Defines muli-nested arrays of coordinates that trace out numbers in 5 row by 3 column space.
 */
public class ScoreBoard {
    private ActorWorld world;
    private Grid<Actor> grid;
    private int playerOneScore = 0;
    private int playerTwoScore = 0;
    private int [][] zero = {{0, 0}, {0, 1}, {0, 2}, {1, 2}, {2, 2}, {3, 2}, {4, 2}, {4, 1}, {4, 0}, {3, 0}, {2, 0}, {1, 0}};
    private int [][] one = {{0, 0}, {0, 1}, {1, 1}, {2, 1}, {3, 1}, {4, 1}, {4, 2}, {4, 0}};
    private int [][] two = {{0, 0}, {0, 1}, {0, 2}, {1, 2}, {2, 2}, {2, 1}, {2, 0}, {3, 0}, {4, 0}, {4, 1}, {4, 2}};
    private int [][] three = {{0, 0}, {0, 1}, {0, 2}, {1, 2}, {2, 2}, {3, 2}, {4, 2}, {4, 1}, {4, 0}, {2, 1}, {2, 0}};
    private int [][] four = {{0, 0}, {1, 0}, {2, 0}, {2, 1}, {0, 2}, {1, 2}, {2, 2}, {3, 2}, {4, 2}};
    private int [][] five = {{0, 2}, {0, 1}, {0, 0}, {1, 0}, {2, 0}, {2, 1}, {2, 2}, {3, 2}, {4, 2}, {4, 1}, {4, 0}};
    private int [][][] scoreNumberTemplates = {zero, one, two, three, four, five};
    private int scoreOneStarterColumn = 31;
    private int scoreTwoStarterRow = 37;

    public ScoreBoard(ActorWorld world, Grid grid, int playerOneScore, int playerTwoScore) {
        this.world = world;
        this.grid = grid;
        this.playerOneScore = playerOneScore;
        this.playerTwoScore = playerTwoScore;
    }
    
    // Reveals the score numbers at game initiation within the PongWorld class
    public void show() {
        renderScoreNumber(1, playerOneScore);
        renderScoreNumber(2, playerTwoScore);
    }
    
    // Determines which player's score is to be rendered, and uses a loop to traverse scoreNumberTemplates array and the corresponding score point by point
    private void renderScoreNumber(int player, int score) {
        int column = player == 1 ? scoreOneStarterColumn : scoreTwoStarterRow;
        for (int i = 0; i < scoreNumberTemplates[score].length; i++) {
            world.add(new Location(scoreNumberTemplates[score][i][0], column + scoreNumberTemplates[score][i][1]), new BlackSquare());
        }
    }
    
    // Erases the old score and rerenders the new one based on score
    public void updateScore(int player, int score) {
        eraseObsoleteScore(player);
        if (player == 1) {
            this.playerOneScore = score;
            renderScoreNumber(1, playerOneScore);
        } else {
            this.playerTwoScore = score;
            renderScoreNumber(2, playerTwoScore);
        }
    }
    
    // Erases the old score from the top to the bottom rows, left to right columns
    private void eraseObsoleteScore(int player) {
        int column = player == 1 ? scoreOneStarterColumn : scoreTwoStarterRow;
        int columnNumber = 0;
        while (columnNumber < 4) {
            for (int rowNumber = 0; rowNumber < 5; rowNumber++) {
                Location scoreCell = new Location(rowNumber, column + columnNumber);
                if (grid.isValid(scoreCell)) {
                    Actor actor = grid.get(scoreCell);
                    if (actor != null) actor.removeSelfFromGrid();
                }
            }
            columnNumber++;
        }
    }
}