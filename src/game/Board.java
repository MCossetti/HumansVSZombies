package game;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import entities.Zombie;
import ui.GameFrame;

public class Board {
    private final int SIZE; // Size of the board (NxN)
    private final int[][] grid; // Matrix representing the state of each cell: 0 = empty, 1 = human, 2 = zombie
    private final Semaphore[][] semaphores; // Matrix of semaphores to ensure exclusive access to each cell
    private final AtomicBoolean gameOver = new AtomicBoolean(false); // Flag to indicate whether the game is over
    private final AtomicInteger humans = new AtomicInteger(50); // Number of active humans
    private final AtomicInteger zombies = new AtomicInteger(50); // Number of active zombies
    private GameFrame frame; // Reference to the game UI

    // Constructor: Initializes the board, placing humans and zombies at opposite sides
    public Board(int size) {
        this.SIZE = size;
        this.grid = new int[size][size];
        this.semaphores = new Semaphore[size][size];

        for (int i = 0; i < size; i++) {
            grid[i][0] = 1; // Place humans at the first column
            grid[i][size - 1] = 2; // Place zombies at the last column

            for (int j = 0; j < size; j++) {
                // Initialize a semaphore for each cell, with one permit (mutual exclusion)
                semaphores[i][j] = new Semaphore(1);
            }
        }
    }

    // Link the graphical user interface to the board
    public void setFrame(GameFrame frame) {
        this.frame = frame;
    }

    //Moves an entity (human or zombie) from one position to another
    public boolean move(Position from, Position to, int type) {
        // Check if destination is within bounds
        if (!isValid(to)) return false;

        try {
            // Try to acquire semaphore for the destination cell
            if (!semaphores[to.row][to.col].tryAcquire()) return false;

            synchronized (this) {
                int target = grid[to.row][to.col]; // What's currently in the destination cell?

                // If a human is trying to move to an empty cell
                if (type == 1 && target == 0) {
                    grid[from.row][from.col] = 0; // Clear the previous cell
                    grid[to.row][to.col] = 1; // Mark the destination with a human
                    semaphores[from.row][from.col].release(); // Release semaphore of old cell

                    // If the human reached the rightmost column, they win
                    if (to.col == SIZE - 1) {
                        if (!gameOver.getAndSet(true)) {
                            if (frame != null) {
                                frame.closeGame(); // Notify UI to close the game
                            }
                        }
                    }
                    return true;

                // If a zombie is trying to move to an empty cell
                } else if (type == 2 && target == 0) {
                    grid[from.row][from.col] = 0; // Clear the previous cell
                    grid[to.row][to.col] = 2; // Mark the destination with a zombie
                    semaphores[from.row][from.col].release(); // Release semaphore of old cell
                    return true;
                }
            }
        } catch (Exception ignored) {
            // Catch and ignore any exceptions (e.g., thread interruption)
        }
        return false; // Move failed
    }

    // Check if the game is over
    public boolean isGameOver() {
        return gameOver.get();
    }

    // Decrease the number of humans alive and check for game over condition
    private void decrementHumans() {
        humans.decrementAndGet();
        checkGameOver();
    }

    // Check if all humans have been eliminated and end the game if so
    private synchronized void checkGameOver() {
        if (humans.get() == 0 && !gameOver.get()) {
            gameOver.set(true);
            if (frame != null) {
                frame.closeGame(); // Inform UI that the game should be closed
            }
        }
    }

    // Get the board size
    public int getSize() {
        return SIZE;
    }

    /* Returns a snapshot of the current board state
     * Used for rendering the board in the UI
     */
    public int[][] getGridSnapshot() {
        int[][] snapshot = new int[SIZE][SIZE];
        synchronized (this) {
            for (int i = 0; i < SIZE; i++) {
                System.arraycopy(grid[i], 0, snapshot[i], 0, SIZE);
            }
        }
        return snapshot;
    }

    // Check if a position is within the bounds of the board
    private boolean isValid(Position p) {
        return p.row >= 0 && p.row < SIZE && p.col >= 0 && p.col < SIZE;
    }

    /* Converts a human into a zombie at the given position
     * Also spawns a new zombie thread at that location
     */
    public void transformHumanToZombie(Position p) {
        synchronized (this) {
            grid[p.row][p.col] = 2; // Mark the cell as occupied by a zombie
            humans.decrementAndGet(); // One less human
            zombies.incrementAndGet(); // One more zombie

            // Create and start a new zombie at the specified position
            Zombie newZombie = new Zombie(p.row, p.col, this, frame);
            newZombie.start();

            // If no humans are left, the game is over
            if (humans.get() == 0) {
                gameOver.set(true);
            }
        }
    }
}
