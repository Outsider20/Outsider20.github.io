package rithmomachia;

import java.util.HashSet;
import java.util.Set;


public class Piece {
    protected final Color color;
    private int value;
    private int row, col;
    private int moveSpaces;
    private String shape;

    public Piece(Color color, int value, int row, int col, int moveSpaces, String shape) {
        this.color = color;
        this.value = value;
        this.row = row;
        this.col = col;
        this.moveSpaces = moveSpaces;
        this.shape = shape.toUpperCase();

        //Board.setPiece(row, col, this);
    }

    // What is row col? The piece's current row and col??? Do we need this??
    public Set<Move> findMoves(Board board) {
        Set<Move> mm = new HashSet<>();
        int distance = getMoveSpaces();

        // Horizontal and vertical moves (distance squares)
        if (board.isValidPos(row + distance, col) && board.pathIsClear(row, col, row + distance, col, board)) {
            mm.add(new Move(row, col, row + distance, col));
        }
        if (board.isValidPos(row - distance, col) && board.pathIsClear(row, col, row - distance, col, board)) {
            mm.add(new Move(row, col, row - distance, col));
        }
        if (board.isValidPos(row, col + distance) && board.pathIsClear(row, col, row, col + distance, board)) {
            mm.add(new Move(row, col, row, col + distance));
        }
        if (board.isValidPos(row, col - distance) && board.pathIsClear(row, col, row, col - distance, board)) {
            mm.add(new Move(row, col, row, col - distance));
        }

        // Diagonal moves (distance squares)
        if (board.isValidPos(row + distance, col + distance) && board.pathIsClear(row, col, row + distance, col + distance, board)) {
            mm.add(new Move(row, col, row + distance, col + distance));
        }
        if (board.isValidPos(row + distance, col - distance) && board.pathIsClear(row, col, row + distance, col - distance, board)) {
            mm.add(new Move(row, col, row + distance, col - distance));
        }
        if (board.isValidPos(row - distance, col + distance) && board.pathIsClear(row, col, row - distance, col + distance, board)) {
            mm.add(new Move(row, col, row - distance, col + distance));
        }
        if (board.isValidPos(row - distance, col - distance) && board.pathIsClear(row, col, row - distance, col - distance, board)) {
            mm.add(new Move(row, col, row - distance, col - distance));
        }

        // Knight-like moves

        if (getMoveSpaces() == 2) {

            if (board.isValidPos(row + 2, col + 1) && board.isEmpty(row + 2, col + 1)) {
                mm.add(new Move(row, col, row + 2, col + 1));
            }
            if (board.isValidPos(row + 2, col - 1) && board.isEmpty(row + 2, col - 1)) {
                mm.add(new Move(row, col, row + 2, col - 1));
            }
            if (board.isValidPos(row - 2, col + 1) && board.isEmpty(row - 2, col + 1)) {
                mm.add(new Move(row, col, row - 2, col + 1));
            }
            if (board.isValidPos(row - 2, col - 1) && board.isEmpty(row - 2, col - 1)) {
                mm.add(new Move(row, col, row - 2, col - 1));
            }

        }

        return mm;
    }


   /* public Set<Pos> captureBySiege(Board board) {
        Set<Pos> capturedPositions = new HashSet<>();
        boolean orthogonalBlocked = true;
        boolean diagonalBlocked = true;
        // Check all four orthogonal directions
        int[][] orthogonalMoves = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] move : orthogonalMoves) {
            int newRow = this.row + move[0];
            int newCol = this.col + move[1];
            // Check if the position is valid
            if (!board.isValidPos(newRow, newCol)) {
                orthogonalBlocked = false;  // If out of bounds, not blocked
                continue;
            }
            Piece neighbor = board.getPiece(newRow, newCol);
            if (neighbor == null || neighbor.getColor() == this.color) {
                orthogonalBlocked = false;  // If empty or friendly piece, not blocked
            }
        }
        // Check all four diagonal directions
        int[][] diagonalMoves = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        for (int[] move : diagonalMoves) {
            int newRow = this.row + move[0];
            int newCol = this.col + move[1];
            if (!board.isValidPos(newRow, newCol)) {
                diagonalBlocked = false;
                continue;
            }
            Piece neighbor = board.getPiece(newRow, newCol);
            if (neighbor == null || neighbor.getColor() == this.color) {
                diagonalBlocked = false;
            }
        }
        // Capture if surrounded in any one pattern (either all orthogonal or all diagonal)
        if (orthogonalBlocked || diagonalBlocked) {
            capturedPositions.add(new Pos(this.row, this.col));
        }
        return capturedPositions;
    }
*/

    public Set<Piece> captureBySiege(Board board) {
        Set<Piece> pieces = new HashSet<>();
        for (Piece neighbor : board.findClosestNeighbors(this.row, this.col)) {
            if(neighbor.color != this.color){
                if(board.isSurrounded(neighbor)){
                    pieces.add(neighbor);
                }
            }
        }
        return pieces;//Return Set of Pieces
    }

    // What is row col? The piece's current row and col??? Do we need this??
    // Have to rework this so it can capture pyramids as either a whole or a part.
    // Thought:
    //  Set<Pos> pp = new HashSet<>();
    //      for (Piece neighbor : board.findClosestNeighbors(this.row, this.col)){
    //          for(Piece pieceInSet : neighbor.getPieceAsSet){
    //              int neighborRow = pieceInSet.getRow();
    //              int neighborCol = pieceInSet.getCol();
    //              if (board.distanceBetween(this, pieceInSet) == this.moveSpaces + 1
    //                  && this.color != pieceInSet.getColor()
    //                  && this.value == pieceInSet.getValue()){
    //                      pp.add(new Pos(pieceInSet.getRow(), pieceInSet.getCol()));
    //              }
    //          }
    //      }
    //  return pp;
    // That's it. That's the function.
    public Set<Piece> captureByEncounter(Board board) {
        Set<Piece> pp = new HashSet<>();
        for (Piece neighbor : board.findClosestNeighbors(this.row, this.col)) {
            for (Piece pieceInSet : neighbor.getPieceAsSet()) {
                if (board.distanceBetween(this, pieceInSet) == this.moveSpaces + 1
                        && this.color != pieceInSet.getColor()
                        && this.value == pieceInSet.getValue()) {
                    pp.add(new Piece(pieceInSet.getColor(),pieceInSet.getValue(), pieceInSet.getRow(),pieceInSet.getCol(),pieceInSet.getMoveSpaces(),pieceInSet.getShape()));
                }
            }
        }
        return pp;
    }

    // This checks for all possible eruption captures and returns as a Set of Pos.
    // If there are no captures, it will return an empty set.
    public Set<Piece> captureByEruption(Board board) {
        Set<Piece> posECaps = new HashSet<>();
        Set<Piece> neighbors = board.findClosestNeighbors(this.row, this.col);
        if (!neighbors.isEmpty()) {
            for (Piece piece : neighbors) {
                if (piece.color != this.color) {
                    Set<Piece> pieceAsSet = piece.getPieceAsSet();
                    for (Piece setPiece : pieceAsSet) {
                        int totalSquares = 2;
                        int numRows = Math.abs(setPiece.row - this.row);
                        int numCols = Math.abs(setPiece.col - this.col);
                        if (numRows == 0)
                            totalSquares = numCols + 1;
                        else
                            totalSquares = numRows + 1;
                        int divValue = 0;
                        if (this.value % totalSquares == 0) {
                            divValue = this.value / totalSquares;
                        }
                        int multValue = this.value * totalSquares;
                        if (multValue == setPiece.value || divValue == setPiece.value) {
                            posECaps.add(new Piece(setPiece.color, setPiece.value, setPiece.row, setPiece.col, setPiece.moveSpaces, setPiece.shape));
                        }
                    }
                }
            }
        }
        return posECaps;
    }

    public Set<Piece> captureByDeceit(Board board) {
        Set<Piece> pieces = new HashSet<>();
        for (Piece neighbor : board.findClosestNeighbors(this.row, this.col)) {
            for (Piece pieceInSet : neighbor.getPieceAsSet()) {
                for(Piece piece : board.getPiece(this.row,this.col).getPieceAsSet()){
                    System.out.println(piece);
                    if(board.deceitCaptureHelper(piece, pieceInSet)){
                        pieces.add(pieceInSet);
                    }
                }
            }
        }
        return pieces;//Return Set of Pieces
    }


    public Color getColor() {
        return this.color;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getRow() {
        return this.row;
    }

    public int getMoveSpaces() {
        return this.moveSpaces;
    }

    private void setMoveSpaces(int moveSpaces) {
        this.moveSpaces = moveSpaces;
    }

    public String getShape() {
        return this.shape;
    }

    protected void setShape(String shape) {
        if (shape.equalsIgnoreCase(this.getShape())) {
            return;
        }
        switch (shape.toLowerCase()) {
            case "c":
                this.setMoveSpaces(1);
                break;
            case "t":
                this.setMoveSpaces(2);
                break;
            case "s":
                this.setMoveSpaces(3);
                break;
            default:
                return;
        }
        this.shape = shape.toUpperCase();
    }

    void setRow(int newRow) {
        this.row = newRow;
    }

    public int getCol() {
        return this.col;
    }

    void setCol(int newCol) {
        this.col = newCol;
    }

    public Set<Piece> getPieceAsSet(){
        Set<Piece> pieceAsSet = new HashSet<>();
        pieceAsSet.add(this);
        return pieceAsSet;
    }

    public String nullToString() {
        return "   ";
    }

    public String toString() {
        return String.format("%s%s%d", color, shape, value);
    }
}