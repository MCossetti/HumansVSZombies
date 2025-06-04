package main;

import entities.Human;
import entities.Zombie;
import game.Board;
import ui.GameFrame;

public class Game {
    public static void main(String[] args) {
        final int SIZE = 50; 
        // Create a new game board with the specified size
        Board board = new Board(SIZE); 
        
        // Create the game window (UI) and pass the board to it so it can be rendered
        GameFrame frame = new GameFrame(board);  
        
        // Connect the frame to the board so they can interact (e.g., update display)
        board.setFrame(frame); 

        // Initialize and start threads for humans and zombies
        for (int i = 0; i < SIZE; i++) {
            // Create and start a human at column 'i' and row 0 (top of the board)
            new Human(i, 0, board, frame).start(); 
            
            // Create and start a zombie at column 'i' and bottom row (SIZE - 1)
            new Zombie(i, SIZE - 1, board, frame).start();  
        }
    }
}
