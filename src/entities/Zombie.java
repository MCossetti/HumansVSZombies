package entities;

import game.*;
import ui.GameFrame;

import java.util.Random;

public class Zombie extends Thread {
    private Position pos; // Current position of the zombie on the board
    private final Board board; // Reference to the shared game board
    private final GameFrame frame; // Reference to the UI, used for refreshing display
    private final Random rand = new Random(); // Random generator for movement and sleep timing

    // Constructor to initialize the zombie's position and board/UI references
    public Zombie(int row, int col, Board board, GameFrame frame) {
        this.pos = new Position(row, col);
        this.board = board;
        this.frame = frame; 
    }

    /* This method defines the behavior of the zombie thread.
     * It continues moving randomly while the game is running.
     */
    @Override
    public void run() {
        while (!board.isGameOver()) { // Continue moving while the game hasn't ended
            Position next = randomMove(pos); // Choose a random adjacent position to move to
            if (board.move(pos, next, 2)) { // Try to move as a zombie (type 2)
                pos = next; // Update the zombie's position if the move was successful
            }
            frame.refresh(); // Refresh the UI to reflect the board update
            sleepRandom(); // Pause for a short random interval before the next move
        }
    }

    /* Picks a random valid adjacent direction to move.
     * Possible directions: left, up, down, right.
     */
    private Position randomMove(Position p) {
        int[][] dirs = {
            {0, -1},  // left
            {-1, 0},  // up
            {1, 0},   // down
            {0, 1}    // right
        };
        int[] dir = dirs[rand.nextInt(dirs.length)]; // Choose a random direction
        return new Position(p.row + dir[0], p.col + dir[1]); // Return the resulting position
    }

    /* Causes the zombie thread to sleep for a random duration between 100 and 200 milliseconds.
     * Simulates delay between movements.
     */
    private void sleepRandom() {
        try {
            Thread.sleep(100 + rand.nextInt(100)); // Random sleep time between 100 and 200 ms
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupt status if interrupted
        } 
    }
}
