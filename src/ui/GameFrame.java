package ui;

import javax.swing.*;
import game.Board;

public class GameFrame extends JFrame {
    private final GamePanel panel; // The panel that displays the game board

    // Constructs the main game window.

    public GameFrame(Board board) {
        setTitle("Humans vs Zombies"); // Set the window title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the application when window is closed
        setResizable(false); // Disable window resizing

        panel = new GamePanel(board); // Create the game panel using the board reference
        add(panel); // Add the panel to the frame

        pack(); // Resize the frame to fit the preferred size of its components (panel)
        setLocationRelativeTo(null); // Center the window on the screen
        setVisible(true); // Make the window visible
    }

    /* Refreshes the game panel, triggering a repaint to reflect updates.
     * Called after each move to update the visual state.
     */
    public void refresh() {
        panel.repaint(); // Repaint the panel to reflect the latest board state
    }

    /* Closes the game by disposing the window and exiting the application.
     * This is called when a win/lose condition is met.
     */
    public void closeGame() {
        SwingUtilities.invokeLater(() -> {
            dispose();      // Close the window
            System.exit(0); // Terminate the application
        });
    }
}
