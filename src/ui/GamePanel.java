package ui;

import javax.swing.*;
import java.awt.*;
import game.Board;

public class GamePanel extends JPanel {
    private final Board board;                // Reference to the game board (model)
    private static final int CELL_SIZE = 15;  // Size in pixels of each cell in the grid

    //* Constructs the game panel, which is responsible for rendering the board.

    public GamePanel(Board board) {
        this.board = board;
        int size = board.getSize() * CELL_SIZE; // Calculate panel dimensions based on board size
        setPreferredSize(new Dimension(size, size)); // Set panel size
        setBackground(Color.WHITE); // Background color of the panel
    }

    /* Paints the current game state onto the panel.
     * This method is automatically called when the panel needs to be redrawn. 
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Clear the panel before drawing

        int[][] grid = board.getGridSnapshot(); // Get a snapshot of the board's current state

        // Loop through all cells in the grid
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                // Set color based on the cell's content
                switch (grid[i][j]) {
                    case 1 -> g.setColor(Color.BLUE);        // Blue represents a human
                    case 2 -> g.setColor(Color.RED);         // Red represents a zombie
                    default -> g.setColor(Color.LIGHT_GRAY); // Light gray represents an empty cell
                }

                // Fill the cell with the selected color
                g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);

                // Draw a gray border around each cell
                g.setColor(Color.GRAY);
                g.drawRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }
}
