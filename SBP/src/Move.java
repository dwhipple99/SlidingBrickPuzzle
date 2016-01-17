/**
 * Created by dwhipple_000 on 1/10/2016.
 *
 * The is class is designed to represent a move in the Sliding Brick Puzzle game.
 *
 */
public class Move {

    // Enumeration of the possible moves
    public enum Direction {
        UP("UP"), DOWN("DOWN"), LEFT("LEFT"), RIGHT("RIGHT");

        private String name;

        Direction(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    // A move is a pair (piece, direction)
    int piece;
    Direction direction;

    // Constructor
    public Move(int p, Direction d) {
        this.piece=p;
        this.direction=d;
    }

    public int getPiece() { return piece;}
    public Direction getDirection() { return direction;}

}
