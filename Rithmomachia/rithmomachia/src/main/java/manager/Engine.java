package manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Engine {

    private final Board board;
    private final Color onMove;

    public Engine(Board board, Color onMove) {
        this.board = board;
        this.onMove = onMove;
    }

    public Move play() {
        Piece p = findPiece(onMove);
        Pos from = p.getPos();
        Pos to = findEmptySquare();
        return new Move(from.getRow(), from.getCol(), to.getRow(), to.getCol());
    }

    private Piece findPiece(Color color) {
        List<Piece> pp = new ArrayList<>();
        pp.addAll(board.getPieces());
        Collections.shuffle(pp);
        for (Piece p : pp) {
            if (p.getColor() == color) {
                return p;
            }
        }
        return null;
    }

    private Pos findEmptySquare() {
        boolean[][] sq = new boolean[16][8];
        for (Piece p : board.getPieces()) {
            Pos pos = p.getPos();
            int row = pos.getRow();
            int col = pos.getCol();
            sq[row][col] = true;
        }
        Random rand = new Random();
        for (int i = 0; i < 100000; i++) {
            int row = rand.nextInt(16);
            int col = rand.nextInt(8);
            if (!sq[row][col]) {
                return new Pos(row, col);
            }
        }
        throw new AssertionError();
    }

}
