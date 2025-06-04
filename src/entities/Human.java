package entities;

import game.*;
import ui.GameFrame;

import java.util.Random;

public class Human extends Thread {
    private Position pos; // Current position of the human on the board
    private final Board board; // Reference to the shared game board
    private final GameFrame frame; // Reference to the UI, used for refreshing display
    private final Random rand = new Random(); // Random generator for movement and sleep timing

    // Constructor to initialize human position and references
    public Human(int row, int col, Board board, GameFrame frame) {
        this.pos = new Position(row, col);
        this.board = board; 
        this.frame = frame;
    }

    /* This method defines the behavior of the human thread.
     * It keeps running until the game is over or the human is converted to a zombie.
     */
    @Override
    public void run() {
        while (!board.isGameOver()) {
            // Take a snapshot of the board to read current positions
            int[][] snapshot = board.getGridSnapshot(); 

            // If there's a zombie directly in front, convert to a zombie and terminate thread
            if (isZombieNearby(snapshot, pos)) {
                board.transformHumanToZombie(pos); // Notify board to convert this human to a zombie
                return; // Human is now a zombie, so this thread ends
            }

            // Otherwise, attempt a random move
            Position next = randomMove(pos);
            if (board.move(pos, next, 1)) { // Try to move as a human (type 1)
                pos = next; // Update position on success
            }

            frame.refresh(); // Refresh the UI to reflect any updates on the board
            sleepRandom(); // Pause for a random amount of time before next move
        }
    }

    //Checks whether a zombie is directly in front (to the right) of the human.
    private boolean isZombieNearby(int[][] grid, Position p) {
        int newCol = p.col + 1; // Position to the right
        // Check if the column is within bounds and contains a zombie
        if (newCol < grid[0].length) {
            return grid[p.row][newCol] == 2; // 2 = zombie
        }
        return false;
    }

    /* Generates a random adjacent position for the human to attempt moving into.
     * Possible directions: right, down, up, left.
     */
    private Position randomMove(Position p) {
        int[][] dirs = {
            {0, 1},  // right
            {1, 0},  // down
            {-1, 0}, // up
            {0, -1}  // left
        };
        int[] dir = dirs[rand.nextInt(dirs.length)]; // Choose a random direction
        return new Position(p.row + dir[0], p.col + dir[1]); // Return the resulting position
    }

    /* Causes the human thread to sleep for a short random duration,
     * simulating a delay between moves.
     */
    private void sleepRandom() {
        try {
            Thread.sleep(100 + rand.nextInt(100)); // Sleep between 100 and 200 ms
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupt status if interrupted
        }
    }
}
