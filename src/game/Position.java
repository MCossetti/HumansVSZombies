package game;

import java.util.Objects;

/* Represents a position (row and column) on the game board.
 * Used by humans and zombies to track their current location.
 */
public class Position {
    public int row, col; // Public fields representing the row and column coordinates

    //Constructs a position with the given row and column.
    public Position(int r, int c) {
        this.row = r;
        this.col = c;
    }

    //Overrides the equals method to compare two Position objects by their coordinates.
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Position)) return false; // Return false if object is not a Position
        Position p = (Position) o; // Cast the object to Position
        return row == p.row && col == p.col; // Compare row and column values
    }

    //Generates a hash code based on row and column, for use in hash-based collections.
    @Override
    public int hashCode() {
        return Objects.hash(row, col); // Combine row and column into a single hash value
    }
}
