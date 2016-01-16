/**
 * Created by dwhip_000 on 1/16/2016.
 */
public class Move {
     // A move is a pair (piece, direction)

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
