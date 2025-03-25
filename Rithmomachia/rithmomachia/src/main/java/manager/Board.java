package manager;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private List<Piece> pieces = new ArrayList<>();

    public Board(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public void move(String from, String to) {
        Pos pos = Pos.fromString(from);
        for (Piece p : pieces) {
            if (pos.equals(p.getPos())) {
                Pos pos2 = Pos.fromString(to);
                p.setPos(pos2);
                return;
            }
        }
        throw new IllegalArgumentException("invalid position");
    }

}
