package rithmomachia;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public class Board {
    private int rows; //Number of Rows
    private int cols; //Number of Columns
    private int blackEnemyTerritoryStartColumn;
    private int whiteEnemyTerritoryStartColumn;
    private Piece[][] pieces;
    private final VictoryManager victoryManager;

    public Board(int numRows, int numCols, String[] str) {
        initBoard(numRows,numCols);
        //System.out.println("Start [0, 0]: " + pieces[0][0]);
        //System.out.println("After 'pieces' are added [0, 0]: " + pieces[0][0]);
        this.whiteEnemyTerritoryStartColumn = 1;
        this.blackEnemyTerritoryStartColumn = numCols-2;
        for (int i = 0; i < str.length; i++) {
            String[] t = str[i].split(" +");
            for (int j = 0; j < t.length; j++) {
                //System.out.println("Placing piece at [" + i + "][" + j + "]: " + t[j]);
                pieces[i][j] = fromString(t[j], i, j);
                //System.out.println("Piece at [0, 0]: " + pieces[0][0]);

            }
        }
        this.whiteEnemyTerritoryStartColumn = 0;
        this.blackEnemyTerritoryStartColumn = numCols-1;
        victoryManager = new VictoryManager(this, null, 0, 0, 0);
    }


    // A proper board must be presented with White on left side and Black on right side
    public Board(int numRows, int numCols, String[] str, Victory victory, int bodies, int digits, int values) {
        initBoard(numRows,numCols);
        //System.out.println("Start [0, 0]: " + pieces[0][0]);
        //System.out.println("After 'pieces' are added [0, 0]: " + pieces[0][0]);
        for (int i = 0; i < str.length; i++) {
            String[] t = str[i].split(" +");
            for (int j = 0; j < t.length; j++) {
                //System.out.println("Placing piece at [" + i + "][" + j + "]: " + t[j]);
                pieces[i][j] = fromString(t[j], i, j);
                //System.out.println("Piece at [0, 0]: " + pieces[0][0]);

            }
        }
        victoryManager = new VictoryManager(this, victory, bodies, digits, values);
    }

    private Piece fromString(String s, int row, int col) {
        if (s.charAt(0) == 'P') {
            Color pyramidColor = s.charAt(2) == 'W' ? Color.W : Color.B;
            return new Pyramid(pyramidColor, row, col, s);
        }

        if (s.charAt(0) == '-') {
            return null;
        }

        Color c = Color.W;
        switch (s.charAt(0)) {
            case 'B':
                c = Color.B;
                if (col <= this.blackEnemyTerritoryStartColumn) {
                    this.blackEnemyTerritoryStartColumn = col - 1;
                }
                break;
            case 'W':
                c = Color.W;
                if (row >= this.whiteEnemyTerritoryStartColumn) {
                    this.whiteEnemyTerritoryStartColumn = col + 1;
                }
                break;
            default:
                throw new AssertionError();
        }

        int value = Integer.valueOf(s.substring(2));
        // Need to build Pyramid too. Need to update enemy Territory start.
        switch (s.charAt(1)) {
            case 'C':
                return new Circle(c, value, row, col);
            case 'S':
                return new Square(c, value, row, col);
            case 'T':
                return new Triangle(c, value, row, col);
            default:
                throw new AssertionError();
        }
    }





    private void initBoard(int numRows, int numCols) {
        rows = numRows;
        cols = numCols;
        this.blackEnemyTerritoryStartColumn = 1;
        this.whiteEnemyTerritoryStartColumn = numCols-2;
        pieces = new Piece[rows][cols];
    }

    public void printBoard() {
        // Print the numbers at the top for the columns
        System.out.print("    ");
        for (int i = 1; i <= cols; i++) {
            System.out.printf("%9d", i);
        }
        System.out.println();

        // Horizontal separator line for dynamic column size
        System.out.print("    ");
        for (int i = 0; i < cols; i++) {
            System.out.print("----------");
        }
        System.out.println();

        // Letters on the sides for the rows
        char letter = 'a';
        for (int i = 0; i < rows; i++) {
            System.out.printf(" %c |", letter);  // Print the letter for the row
            for (int j = 0; j < cols; j++) {
                if (pieces[i][j] == null) {
                    System.out.print("         |");  // Empty space if no piece
                } else {
                    System.out.printf("%-8s|", pieces[i][j]);  // Piece name
                }
            }
            System.out.printf(" %c", letter);  // Print the letter at the end of the row
            letter++;
            System.out.println();

            // Horizontal separator line for dynamic column size
            System.out.print("    ");
            for (int k = 0; k < cols; k++) {
                System.out.print("----------");
            }
            System.out.println();
        }

        // Print the column numbers again at the bottom
        System.out.print("    ");
        for (int i = 1; i <= cols; i++) {
            System.out.printf("%9d", i);
        }
        System.out.println();
    }


   /* static void startGame() {
        // Initializes black pieces
        new Circle(Color.B, 3, 5, 2);
        new Circle(Color.B, 5, 5, 3);
        new Circle(Color.B, 7, 5, 4);
        new Circle(Color.B, 9, 5, 5);
        new Circle(Color.B, 9, 4, 2);
        new Circle(Color.B, 25, 4, 3);
        new Circle(Color.B, 49, 4, 4);
        new Circle(Color.B, 81, 4, 5);

        new Triangle(Color.B, 16, 4, 0);
        new Triangle(Color.B, 12, 4, 1);
        new Triangle(Color.B, 90, 4, 6);
        new Triangle(Color.B, 100, 4, 7);
        new Triangle(Color.B, 36, 3, 2);
        new Triangle(Color.B, 30, 3, 3);
        new Triangle(Color.B, 56, 3, 4);
        new Triangle(Color.B, 64, 3, 5);

        new Square(Color.B, 49, 2, 0);
        new Square(Color.B, 28, 3, 0);
        new Square(Color.B, 121, 2, 1);
        new Square(Color.B, 66, 3, 1);
        new Square(Color.B, 225, 2, 6);
        new Square(Color.B, 120, 3, 6);
        new Square(Color.B, 361, 2, 7);

        // Initializes white pieces
        new Circle(Color.W, 8, 10, 2);
        new Circle(Color.W, 6, 10, 3);
        new Circle(Color.W, 4, 10, 4);
        new Circle(Color.W, 2, 10, 5);
        new Circle(Color.W, 64, 11, 2);
        new Circle(Color.W, 36, 11, 3);
        new Circle(Color.W, 16, 11, 4);
        new Circle(Color.W, 4, 11, 5);

        new Triangle(Color.W, 81, 11, 0);
        new Triangle(Color.W, 72, 11, 1);
        new Triangle(Color.W, 6, 11, 6);
        new Triangle(Color.W, 9, 11, 7);
        new Triangle(Color.W, 49, 12, 2);
        new Triangle(Color.W, 42, 12, 3);
        new Triangle(Color.W, 20, 12, 4);
        new Triangle(Color.W, 25, 12, 5);

        new Square(Color.W, 153, 12, 0);
        new Square(Color.W, 289, 13, 0);
        new Square(Color.W, 169, 13, 1);
        new Square(Color.W, 45, 12, 6);
        new Square(Color.W, 81, 13, 6);
        new Square(Color.W, 15, 12, 7);
        new Square(Color.W, 25, 13, 7);
    }*/

    public Victory capture(Piece piece) {
        return victoryManager.capture(piece);
    }

    public boolean isValidPos(int row, int col) {
        //System.out.print("isValidPos called: ");
        return row >= 0 && row < rows && col >= 0 && col < cols;

    }

    // Gets all pieces of the specified color on the board
    public Set<Piece> getPiecesOfColor(Color color){
        Set<Piece> piecesOfColor = new HashSet<>();
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                if (this.pieces[i][j] != null && this.pieces[i][j].getColor() == color){
                    piecesOfColor.add(this.pieces[i][j]);
                }
            }
        }
        return piecesOfColor;
    }

    // Returns all pieces on the board as sets mapped to their color
    public Map<Color, Set<Piece>> getAllPieces(){
        Map<Color, Set<Piece>> allPieces = new HashMap<>();
        allPieces.put(Color.B, this.getPiecesOfColor(Color.B));
        allPieces.put(Color.W, this.getPiecesOfColor(Color.W));
        return allPieces;
    }

    // Rewrote this to remove double method calls. Basically, add everything to the set even if it is null.
    // null can only be added to the HashSet once. At the end, just call to remove null.
    // If null is in the set, it is now gone. Otherwise, it just ignores the remove command and proceeds anyways.
    public Set<Piece> findClosestNeighbors(int row, int col){
        Set<Piece> neighbors = new HashSet<>();
        neighbors.add(findClosestRight(row, col));
        neighbors.add(findClosestLeft(row, col));
        neighbors.add(findClosestUp(row, col));
        neighbors.add(findClosestDown(row, col));
        neighbors.add(findClosestUpRight(row, col));
        neighbors.add(findClosestUpLeft(row, col));
        neighbors.add(findClosestDownRight(row, col));
        neighbors.add(findClosestDownLeft(row, col));
        neighbors.remove(null);
        return neighbors;
    }

    public Piece findClosestRight(int row, int col) {
        for (int i = col+1; i<this.cols; i++){
            if (!this.isEmpty(row, i)) {
                return this.getPiece(row, i);
            }
        }
        return null;
    }

    public Piece findClosestLeft(int row, int col) {
        for (int i = col -1; i>=0; i--){
            if (!this.isEmpty(row, i)) {
                return this.getPiece(row, i);
            }
        }
        return null;
    }

    public Piece findClosestUp(int row, int col) {
        for (int i = row-1; i>=0; i--){
            if (!this.isEmpty(i, col)) {
                return this.getPiece(i, col);
            }
        }
        return null;
    }

    public Piece findClosestDown(int row, int col) {
        for (int i = row+1; i<this.rows; i++){
            if (!this.isEmpty(i, col)) {
                return this.getPiece(i, col);
            }
        }
        return null;
    }

    public Piece findClosestUpRight(int row, int col) {
        for (int i = row-1, j = col+1; i>=0 && j<this.cols; i--, j++){
            if (!this.isEmpty(i, j)) {
                return this.getPiece(i, j);
            }
        }
        return null;
    }

    public Piece findClosestDownRight(int row, int col) {
        for (int i = row+1, j = col+1; i<this.rows && j<this.cols; i++, j++){
            if (!this.isEmpty(i, j)) {
                return this.getPiece(i, j);
            }
        }
        return null;
    }

    public Piece findClosestUpLeft(int row, int col) {
        for (int i = row-1, j = col-1; i>=0 && j>=0; i--, j--){
            if (!this.isEmpty(i, j)) {
                return this.getPiece(i, j);
            }
        }
        return null;
    }

    public Piece findClosestDownLeft(int row, int col) {
        for (int i = row+1, j = col-1; i<this.rows && j>=0; i++, j--){
            if (!this.isEmpty(i, j)) {
                return this.getPiece(i, j);
            }
        }
        return null;
    }



    public boolean isEmpty(int row, int col) {
        if(!isValidPos(row,col)) {
            return false;
        }

        return pieces[row][col] == null;
    }

    // Helper method to check if the path is clear
    public boolean pathIsClear(int row1, int col1, int row2, int col2, Board board) {
        // Determine the direction of movement
        int dRow = Integer.signum(row2 - row1); // Direction along x-axis
        int dCol = Integer.signum(col2 - col1); // Direction along y-axis

        // Loop over each square between (row1, col1) and (row2, col2)
        for (int i = 1; i < Math.max(Math.abs(row2 - row1), Math.abs(col2 - col1)); i++) {
            int nextRow = row1 + i * dRow; // The x-coordinate of the next square along the path
            int nextCol = col1 + i * dCol; // The y-coordinSate of the next square along the path

            // If any square along the path is not empty, the path is blocked
            if (!board.isEmpty(nextRow, nextCol)) {
                return false; // Path is blocked by another piece
            }
        }

        // Check if the destination square (row2, col2) is empty
        // If it's occupied, the piece cannot move there
        if (!board.isEmpty(row2, col2)) {
            return false; // Target square is occupied
        }

        // If all squares are empty and the target square is not occupied, the path is clear
        return true;
    }

    public boolean capturePathIsClear(int rowStart, int colStart, int rowEnd, int colEnd, Board board) {
        // Determine the direction of movement
        int dRow = Integer.signum(rowEnd - rowStart); // Direction along rows
        int dCol = Integer.signum(colEnd - colStart); // Direction along cols

        // Loop over each square between (x1, y1) and (x2, col2)
        for (int i = 1; i < Math.max(Math.abs(rowEnd - rowStart), Math.abs(colEnd - colStart)); i++) {
            int nextRow = rowStart + i * dRow; // The row-coordinate of the next square along the path
            int nextCol = colStart + i * dCol; // The col-coordinate of the next square along the path

            // If any square along the path is not empty, the path is blocked
            if (!board.isEmpty(nextRow, nextCol)) {
                return false; // Path is blocked by another piece
            }
        }
        // If all squares are empty and the target square is not occupied, the path is clear
        return true;
    }

    public boolean containsPieceWithValue(int row, int col, int value) {
        //System.out.print("Contains function called");
        // Check if the position (x, y) contains a piece with the specific value.
        Piece piece = getPiece(row, col); // Retrieves the piece at position (x, y)
        if (piece != null && piece.getValue() == value) {
            return true; // Return true if the piece has the same value
        }
        return false;
    }

    public boolean containsPiece(int row, int col) {
        Piece piece = getPiece(row, col); // Retrieves the piece at position (x, y)
        if (piece != null) {
            return true; // Return true if the piece exists
        }
        return false;
    }


    //Check if a piece is the opposite color of another piece
    public boolean isOppositeColor(int row, int col, Color c) {
        Piece piece = getPiece(row,col);
        if (piece.getColor() == c) {
            return false;
        }
        return true;
    }

    // This returns the number of squares between two pieces INCLUSIVE.
    // For example, if "---" represents a blank square, P1 --- --- P2 returns 4.
    // Maybe need to consider if pieces are the same piece?
    public int distanceBetween(Piece piece1, Piece piece2) {
        // Always minimum of 2 squares
        int totalSquares = 2;
        // Determine how many rows of separation
        int numRows = Math.abs(piece1.getRow() - piece2.getRow());
        // Determine how many columns of separation
        int numCols = Math.abs(piece1.getCol() - piece2.getCol());
        if (numRows == 0)
            // If pieces are in same column, use the column separation
            totalSquares = numCols + 1;
        else
            // Otherwise, pieces are either horizontal or diagonal. May use row separator in each case
            totalSquares = numRows + 1;
        return totalSquares;
    }

    public boolean deceitCaptureHelper(Piece capturingPiece, Piece opponentPiece) {
        if(distanceBetween(capturingPiece, opponentPiece) > 2) {
            return false;
        }
        else{
            int capturingRow = capturingPiece.getRow();
            int capturingCol = capturingPiece.getCol();
            int opponentRow = opponentPiece.getRow();
            int opponentCol = opponentPiece.getCol();
            if(isValidPos(2 * opponentRow - capturingRow, 2 * opponentCol  - capturingCol)){
                Piece allyPiece = getPiece(2 * opponentRow - capturingRow, 2 * opponentCol  - capturingCol);
                if(allyPiece == null) {
                    return false;
                }
                if(allyPiece.getColor() != capturingPiece.getColor()) {
                    return false;
                }
                for(Piece piece: allyPiece.getPieceAsSet()){
                    if(capturingPiece.getValue() + piece.getValue() == opponentPiece.getValue()) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    public boolean isSurroundedOrthogonally(Piece capturedPiece) {
        int row = capturedPiece.getRow();
        int col = capturedPiece.getCol();
        Color color = capturedPiece.getColor();


        if (isValidPos(row - 1, col)) {
            Piece neighbor = getPiece(row - 1, col);
            if (neighbor == null || neighbor.getColor().equals(color)) return false;
        }


        if (isValidPos(row + 1, col)) {
            Piece neighbor = getPiece(row + 1, col);
            if (neighbor == null || neighbor.getColor().equals(color)) return false;
        }


        if (isValidPos(row, col - 1)) {
            Piece neighbor = getPiece(row, col - 1);
            if (neighbor == null || neighbor.getColor().equals(color)) return false;
        }


        if (isValidPos(row, col + 1)) {
            Piece neighbor = getPiece(row, col + 1);
            if (neighbor == null || neighbor.getColor().equals(color)) return false;
        }

        return true;
    }



    //This can be edited depending on whether we want to have sieges that occur on the 4 corners of the board or not
    public boolean isSurroundedDiagonally(Piece capturedPiece) {
        int row = capturedPiece.getRow();
        int col = capturedPiece.getCol();
        Color color = capturedPiece.getColor();
        //With these values we can dictate that a piece is "Surrounded" if there are two or more enemy pieces at the diagonals

        //If we want to make capture by siege work with only 1 attacking piece change siegeMinimumPiecesRequired to 1
        int siegeMinimumPiecesRequired = 2;
        int siegeCurrentPiecesUsed = 0;


        if (isValidPos(row - 1, col - 1)) {
            Piece neighbor = getPiece(row - 1, col - 1);
            siegeCurrentPiecesUsed++;
            if (neighbor == null || neighbor.getColor().equals(color)) return false;
        }


        if (isValidPos(row - 1, col + 1)) {
            Piece neighbor = getPiece(row - 1, col + 1);
            siegeCurrentPiecesUsed++;
            if (neighbor == null || neighbor.getColor().equals(color)) return false;
        }


        if (isValidPos(row + 1, col - 1)) {
            Piece neighbor = getPiece(row + 1, col - 1);
            siegeCurrentPiecesUsed++;
            if (neighbor == null || neighbor.getColor().equals(color)) return false;
        }


        if (isValidPos(row + 1, col + 1)) {
            Piece neighbor = getPiece(row + 1, col + 1);
            siegeCurrentPiecesUsed++;
            if (neighbor == null || neighbor.getColor().equals(color)) return false;
        }

        return siegeCurrentPiecesUsed >= siegeMinimumPiecesRequired;
    }



    public boolean isSurrounded(Piece capturedPiece) {
        return isSurroundedOrthogonally(capturedPiece) || isSurroundedDiagonally(capturedPiece);
    }




    public void setPiece(int row, int col, Piece piece) {
        if (piece != null) {
            piece.setRow(row);
            piece.setCol(col);
        }
        pieces[row][col] = piece;
    }

    // Need to handle Pyramid because it is multiple pieces. How to do this? Calls to this must run multiple times if encounter a pyramid?
    public Piece getPiece(int row, int col) {
        return pieces[row][col];
    }

    // This generates all triples
    // Note: OUTPUT MUST BE LIST TO MAINTAIN ORDER!
    // Valid shapes are straight orthogonal and diagonal lines or right angles oriented orthogonally
    public Map<List<Piece>, TripleShape> getTriplesForColor(Color color) {
        // Generate new set to return. Set contains lists of three pieces. Must return lists as order matters.
        Map<List<Piece>, TripleShape> triples = new HashMap<>();
        // Grab every piece of the color we are checking from the board.
        Set<Piece> piecesToCheck = this.getPiecesOfColor(color);
        // Pick a piece to be the first anchor
        for (Piece firstAnchor : piecesToCheck){
            // make a new list to hold the triple
            List<Piece> tripleCandidate1 = new ArrayList<>();
            tripleCandidate1.add(firstAnchor);
            int row1 = firstAnchor.getRow();
            int col1 = firstAnchor.getCol();
            // Find all of the first anchor's neighbors.
            Set<Piece> firstAnchorNeighbors = findClosestNeighbors(firstAnchor.getRow(), firstAnchor.getCol());
            // Pick second anchor piece
            for (Piece secondAnchor : firstAnchorNeighbors){
                tripleCandidate1.add(secondAnchor);
                int row2 = secondAnchor.getRow();
                int col2 = secondAnchor.getCol();
                // Case when first two pieces are vertically aligned.
                if (col1 == col2) {
                    boolean isUpward = row2 < row1;
                    // We will generate three candidates in this case. Here, we make 2 new lists that have the same first two pieces
                    List<Piece> tripleCandidate2 = new ArrayList<>(tripleCandidate1);
                    List<Piece> tripleCandidate3 = new ArrayList<>(tripleCandidate1);
                    // Find the piece on the left
                    tripleCandidate1.add(findClosestLeft(row2, col2));
                    // Find the piece on the right
                    tripleCandidate2.add(findClosestRight(row2, col2));
                    // Find the piece either on top or on the bottom based on orientation.
                    tripleCandidate3.add(isUpward ? findClosestUp(row2, col2) : findClosestDown(row2, col2));
                    if (isViableTriple(tripleCandidate1)){
                        triples.put(tripleCandidate1, isUpward ? TripleShape.UP_THEN_LEFT : TripleShape.DOWN_THEN_LEFT);
                    }
                    if (isViableTriple(tripleCandidate2)){
                        triples.put(tripleCandidate2, isUpward ? TripleShape.UP_THEN_RIGHT : TripleShape.DOWN_THEN_RIGHT);
                    }
                    if (isViableTriple(tripleCandidate3)){
                        triples.put(tripleCandidate3, isUpward ? TripleShape.UP_THEN_UP : TripleShape.DOWN_THEN_DOWN);
                    }
                // Case when pieces are horizontal
                } else if (row1 == row2) {
                    boolean isRightward = col2 > col1;
                    // Need three cases again
                    List<Piece> tripleCandidate2 = new ArrayList<>(tripleCandidate1);
                    List<Piece> tripleCandidate3 = new ArrayList<>(tripleCandidate1);
                    // Find the piece on top
                    tripleCandidate1.add(findClosestUp(row2, col2));
                    // Find the piece below
                    tripleCandidate2.add(findClosestDown(row2, col2));
                    // Find the piece on left or right based on orientation
                    tripleCandidate3.add(isRightward ? findClosestRight(row2, col2) : findClosestLeft(row2, col2));
                    if (isViableTriple(tripleCandidate1)){
                        triples.put(tripleCandidate1, isRightward ? TripleShape.RIGHT_THEN_UP : TripleShape.LEFT_THEN_UP);
                    }
                    if (isViableTriple(tripleCandidate2)){
                        triples.put(tripleCandidate2, isRightward ? TripleShape.RIGHT_THEN_DOWN : TripleShape.LEFT_THEN_DOWN);
                    }
                    if (isViableTriple(tripleCandidate3)){
                        triples.put(tripleCandidate3, isRightward ? TripleShape.RIGHT_THEN_RIGHT : TripleShape.LEFT_THEN_LEFT);
                    }
                } else if (row2 < row1){
                    // All that's left are diagonals. These are upwards.
                    boolean isRightward = col2 > col1;
                    tripleCandidate1.add(isRightward ? findClosestUpRight(row2, col2) : findClosestUpLeft(row2, col2));
                    if (isViableTriple(tripleCandidate1)){
                        triples.put(tripleCandidate1, isRightward ? TripleShape.UPRIGHT : TripleShape.UPLEFT);
                    }
                }else {
                    // Downwards diagonals
                    boolean isRightward = col2 < col1;
                    tripleCandidate1.add(isRightward ? findClosestDownRight(row2, col2) : findClosestDownLeft(row2, col2));
                    if (isViableTriple(tripleCandidate1)){
                        triples.put(tripleCandidate1, isRightward ? TripleShape.DOWNRIGHT : TripleShape.DOWNLEFT);
                    }
                }
            }
        }
        return triples;
    }

    // Check if candidate is viable.
    private boolean isViableTriple(List<Piece> tripleCandidate){
        // The only way we could have null is if no third piece is found, so I account for that here
        return (!tripleCandidate.contains(null)
                && isTripleCorrectPosition(tripleCandidate)
                && isTripleCorrectOrder(tripleCandidate)
                && isTripleCorrectProportion(tripleCandidate));
    }

    // Check if triple is in enemy territory. Note: this automatically extends to quadruples.
    private boolean isTripleCorrectPosition(List<Piece> piecesToCheck) {
        // The beginning of each enemy territory is determined when the board is built.
        int enemyStartColumn = 0;
        // initiate our variables
        boolean isCorrect = true;
        // A triple always starts with the current color's piece, so we grab and switch that here
        switch(piecesToCheck.get(0).getColor()){
            case W:
                enemyStartColumn = this.whiteEnemyTerritoryStartColumn;
                // Ensure each piece in the candidate is inside the enemy territory. Break loop early if not.
                for (Piece piece : piecesToCheck) {
                    if (piece.getCol() < enemyStartColumn){
                        isCorrect = false;
                        break;
                    }
                }
                break;
            case B:
                // Same exact thing if we're starting with black
                enemyStartColumn = this.blackEnemyTerritoryStartColumn;
                for (Piece piece : piecesToCheck) {
                    if (piece.getCol() > enemyStartColumn){
                        isCorrect = false;
                        break;
                    }
                }
                break;
            default:
                return false;
        }
        return isCorrect;
    }

    // This will calculate if the triple is correctly ordered, ie ascending or descending.
    private boolean isTripleCorrectOrder(List<Piece> piecesToCheck) {
        int a = piecesToCheck.get(0).getValue();
        int b = piecesToCheck.get(1).getValue();
        int c = piecesToCheck.get(2).getValue();
        // Only true if values are strictly increasing or strictly decreasing.
        return ((a < b && b < c)
                || (a > b && b > c));
    }

    // Calculate whether the triple is correctly proportioned.
    private boolean isTripleCorrectProportion(List<Piece> piecesToCheck) {
        // Calculate distance between 3 pieces.
        int distance1 = distanceBetween(piecesToCheck.get(0), piecesToCheck.get(1));
        int distance2 = distanceBetween(piecesToCheck.get(1), piecesToCheck.get(2));
        return distance1 == distance2;
    }


    // Current allowed quadruple shapes are straight orthogonal and diagonal lines or boxes oriented orthogonally.
    public Set<List<Piece>> getQuadruplesForColor(Color color) {
        // Now, we have mapped a triple to a shape, so we know which direction to get the forth piece.
        Set<List<Piece>> quadruples = new HashSet<>();
        Map<List<Piece>, TripleShape> triples = this.getTriplesForColor(color);
        for (List<Piece> triple : triples.keySet()) {
            int row = triple.get(2).getRow();
            int col = triple.get(2).getCol();
            // Build a new list off the current list of pieces
            List<Piece> quadrupleCandidate = new ArrayList<>(triple);
            switch (triples.get(triple)) {
                case UP_THEN_LEFT:
                case UP_THEN_RIGHT:
                case DOWN_THEN_DOWN:
                    quadrupleCandidate.add(findClosestDown(row, col));
                    break;
                case DOWN_THEN_LEFT:
                case DOWN_THEN_RIGHT:
                case UP_THEN_UP:
                    quadrupleCandidate.add(findClosestUp(row, col));
                    break;
                case RIGHT_THEN_UP:
                case RIGHT_THEN_DOWN:
                case LEFT_THEN_LEFT:
                    quadrupleCandidate.add(findClosestLeft(row, col));
                    break;
                case LEFT_THEN_UP:
                case LEFT_THEN_DOWN:
                case RIGHT_THEN_RIGHT:
                    quadrupleCandidate.add(findClosestRight(row, col));
                    break;
                case UPLEFT:
                    quadrupleCandidate.add(findClosestUpLeft(row, col));
                    break;
                case UPRIGHT:
                    quadrupleCandidate.add(findClosestUpRight(row, col));
                    break;
                case DOWNLEFT:
                    quadrupleCandidate.add(findClosestDownLeft(row, col));
                    break;
                case DOWNRIGHT:
                    quadrupleCandidate.add(findClosestDownRight(row, col));
                    break;
                default:
                    quadrupleCandidate.add(null);
                    break;
            }
            // This feeds a sublist consisting of the last three pieces in the new quadruple.
            // For a quadruple, the final three pieces must by definition also be a valid triple.
            // Since triples are pre-validated, we can validate the quadruple by checking only the final 3 pieces.
            if (isViableTriple(quadrupleCandidate.subList(1, quadrupleCandidate.size()))){
                quadruples.add(quadrupleCandidate);
            }
        }
        return quadruples;
    }
}