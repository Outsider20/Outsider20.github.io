package rithmomachia;

import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

public class Pyramid extends Piece {

    private int currentValue;
    private int completeValue;
    private int remainingTotalValue;
    private String currentShape;
    private final HashMap<String, HashSet<Piece>> pieces;
    private final boolean isComplete;
    // var remainingPieces = Hash map<String, Set<Piece>> in form {"S": Set<Squares>, "C": Set<Circles>, "T": Set<Triangle>}
    // why are we using sets instead of lists?
    // A: set prevents duplicates.


    // Add CSV string to constructor. For example "P,W,T1,T5,T7,C2,S9"
    // change this to exclude shape and color. Pointless
    public Pyramid(Color color, int row, int col, String pyramidPieces) {
        super(color, 0, row, col, 0, "P");
        this.remainingTotalValue = 0;
        this.currentShape = "P";
        this.isComplete = true;
        // Crunch CSV string to build all pieces here
        this.pieces = buildPyramid(pyramidPieces);
        this.completeValue = this.remainingTotalValue;
    }

    private HashMap<String, HashSet<Piece>> buildPyramid(String pyramidString) {
        String[] pyramidPieces = pyramidString.split(",");
        HashMap<String, HashSet<Piece>> newPyramid = new HashMap<String, HashSet<Piece>>();
        HashSet<Piece> circles = new HashSet<>();
        HashSet<Piece> triangles = new HashSet<>();
        HashSet<Piece> squares = new HashSet<>();
        for (String piece : pyramidPieces) {
            switch (piece.substring(0, 1)) {
                case "C":
                    circles.add(new Circle(this.getColor(), Integer.parseInt(piece.substring(1)), this.getRow(), this.getCol()));
                    this.remainingTotalValue += Integer.parseInt(piece.substring(1));
                    break;
                case "T":
                    triangles.add(new Triangle(this.getColor(), Integer.parseInt(piece.substring(1)), this.getRow(), this.getCol()));
                    this.remainingTotalValue += Integer.parseInt(piece.substring(1));
                    break;
                case "S":
                    squares.add(new Square(this.getColor(), Integer.parseInt(piece.substring(1)), this.getRow(), this.getCol()));
                    this.remainingTotalValue += Integer.parseInt(piece.substring(1));
                    break;
                default:
                    break;
            }
        }
        newPyramid.put("C", circles);
        newPyramid.put("T", triangles);
        newPyramid.put("S", squares);
        super.setValue(remainingTotalValue);
        return newPyramid;
    }

    // Returns hashmap instead
    public HashMap<String, HashSet<Piece>> getPiecesAsMap() {
        return this.pieces;
    }

    private Set<Integer> getValues() {
        Set<Integer> values = new HashSet<Integer>();
        for (Piece piece : getPyramidAsSet()) {
            values.add(piece.getValue());
        }
        values.add(this.remainingTotalValue);
        return values;
    }

    private Set<Integer> getValuesForMakingCaptures(){
        Set<Integer> valuesForMakingCaptures = this.getValues();
        if (this.isComplete) {
            valuesForMakingCaptures.add(this.completeValue);
        }
        return valuesForMakingCaptures;
    }

    private Set<Piece> getPyramidAsSet() {
        Set<Piece> piecesSet = new HashSet<>();
        for (String pieceType : this.pieces.keySet()) {
            piecesSet.addAll(this.pieces.get(pieceType));
        }
        return piecesSet;
    }

    // This is for actual moving the piece logic. You tell the piece what is behaving as
    // Maybe not actually necessary? Because find moves and capture algorithms are going to check
    // everything possible anyways?
    public boolean modeSwitch(String shapeToUse, int value) {
        if (pieces.get(shapeToUse).isEmpty() || !getValues().contains(value)) {
            return false;
        }
        this.currentShape = shapeToUse;
        this.setShape(shapeToUse);
        this.currentValue = value;
        this.setValue(this.currentValue);
        return true;
    }

    // Override for findMoves. Loops through all remaining pieces, should run max three times,
    // Passes loop if none of the remaining piece type exists
    public Set<Move> findMoves(Board board) {
        Set<Move> moves = new HashSet<>();
        for (String pieceType : this.pieces.keySet()) {
            if (!pieces.get(pieceType).isEmpty()) {
                switch (pieceType) {
                    case "C":
                        Circle circleToCheck = new Circle(this.getColor(), 1, this.getRow(), this.getCol());
                        moves.addAll(circleToCheck.findMoves(board));
                        break;
                    case "T":
                        Triangle triangleToCheck = new Triangle(this.getColor(), 1, this.getRow(), this.getCol());
                        moves.addAll(triangleToCheck.findMoves(board));
                        break;
                    case "S":
                        Square squareToCheck = new Square (this.getColor(), 1, this.getRow(), this.getCol());
                        moves.addAll(squareToCheck.findMoves(board));
                        break;
                    default:
                        break;
                }
            }
        }
        return moves;
    }

    // Override that runs algorithm for each piece remaining in the Pyramid
    // Need to add way to check all values???
    // Need to create virtual piece of each type remaining in set and then run capture algorithm for each piece type for each possible value????
    public Set<Piece> captureByEncounter(Board board) {
        Set<Piece> encounters = new HashSet<>();
        Set<Integer> values = getValuesForMakingCaptures();
        for (String pieceType : this.pieces.keySet()) {
            if (!pieces.get(pieceType).isEmpty()) {
                switch (pieceType) {
                    case "C":
                        for (Integer value : values) {
                            Circle virtualCircle = new Circle(this.getColor(), value, this.getRow(), this.getCol());
                            encounters.addAll(virtualCircle.captureByEncounter(board));
                        }
                        break;
                    case "T":
                        for (Integer value : values) {
                            Triangle virtualTriangle = new Triangle(this.getColor(), value, this.getRow(), this.getCol());
                            encounters.addAll(virtualTriangle.captureByEncounter(board));
                        }
                        break;
                    case "S":
                        for (Integer value : values) {
                            Square virtualSquare = new Square(this.getColor(), value, this.getRow(), this.getCol());
                            encounters.addAll(virtualSquare.captureByEncounter(board));
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        return encounters;
    }

    // Override. This does not depend on piece type but will need to run over all values.
    public Set<Piece> captureByEruption(Board board) {
        Set<Piece> eruptions = new HashSet<>();
        for (int value : this.getValuesForMakingCaptures()) {
            Piece virtualPiece = new Circle(this.getColor(), value, this.getRow(), this.getCol());
            eruptions.addAll(virtualPiece.captureByEruption(board));
        }
        return eruptions;
    }

    // Override to string method "WP: WT3, WT6, WS8, WS9"
    public String toString() {
        StringBuilder pieceString = new StringBuilder(); // Create new StringBuilder
        pieceString.append(this.getColor()).append("P:"); // Start string with color and piece type indicator
        for (Piece piece : this.getPyramidAsSet()) { // Loop through remaining pieces as set
            pieceString.append(" ").append(piece.toString()).append(","); // Add current piece's string representation to the builder
        }
        pieceString.deleteCharAt(pieceString.length() - 1); // Remove trailing comma
        return pieceString.toString(); // Return the completed string
    }

    // Find out if pyramid is complete
    public boolean isComplete() {
        return isComplete;
    }

    public Set<Piece> getPieceAsSet() {
        return this.getPyramidAsSet();
    }

    // Will need to override deceipt
    // Other big problem: How to handle Pyramid BEING captured because it can be whole value, even if not complete

    public Set<Piece> captureByDeceit(Board board) {
        Set<Piece> deceits = new HashSet<>();
        for (int value : this.getValuesForMakingCaptures()) {
            System.out.println("Value found: " + value);
            Piece virtualPiece = new Circle(this.getColor(), value, this.getRow(), this.getCol());
            deceits.addAll(virtualPiece.captureByDeceit(board));
        }
        return deceits;
    }
}
