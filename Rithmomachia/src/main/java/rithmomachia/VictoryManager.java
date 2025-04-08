package rithmomachia;

import java.util.List;
import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;

public class VictoryManager {

    private final Board board;
    private final HashMap<String, Integer> whiteCaptures;
    private final HashMap<String, Integer> blackCaptures;
    private final Victory victory;
    private final int bodiesGoal;
    private final int digitsGoal;
    private final int valueGoal;

    // Please construct with appropriate values or -1 if that value is not being used.
    public VictoryManager(Board board, Victory victory, int numBodies, int numDigits, int value) {
        this.board = board;
        this.whiteCaptures = new HashMap<>();
        this.whiteCaptures.put("bodies", 0);
        this.whiteCaptures.put("digits", 0);
        this.whiteCaptures.put("value", 0);
        this.blackCaptures = new HashMap<>();
        this.blackCaptures.put("bodies", 0);
        this.blackCaptures.put("digits", 0);
        this.blackCaptures.put("value", 0);
        this.victory = victory;
        this.bodiesGoal = numBodies;
        this.digitsGoal = numDigits;
        this.valueGoal = value;
    }

    // Will need to account for simple movements triggering glorious victories. Change this to return an enum?
    public Victory capture(Piece piece) {
        switch (piece.getColor()){
            case B: // If a black piece was captured, update white's capture map
                // We check glorious in order of descending value. This happens first because they are better than the normal victories
                if (this.checkVictoriaExcelentisma(Color.W)){
                    return Victory.EXCELENTISMA;
                }
                if (this.checkVictoriaMayor(Color.W)){
                    return Victory.MAYOR;
                }
                if (this.checkVictoriaMayor(Color.W)){
                    return Victory.MAGNA;
                }
                this.whiteCaptures.put("bodies", this.whiteCaptures.get("bodies") + 1);
                this.whiteCaptures.put("digits", this.whiteCaptures.get("digits") + Integer.toString(piece.getValue()).length());
                this.whiteCaptures.put("value", this.whiteCaptures.get("value") + piece.getValue());
                break;
            case W: // If a white piece was captured, update black's capture map
                if (this.checkVictoriaExcelentisma(Color.B)){
                    return Victory.EXCELENTISMA;
                }
                if (this.checkVictoriaMayor(Color.B)){
                    return Victory.MAYOR;
                }
                if (this.checkVictoriaMagna(Color.B)){
                    return Victory.MAGNA;
                }
                this.blackCaptures.put("bodies", this.blackCaptures.get("bodies") + 1);
                this.blackCaptures.put("digits", this.blackCaptures.get("digits") + Integer.toString(piece.getValue()).length());
                this.blackCaptures.put("value", this.blackCaptures.get("value") + piece.getValue());
        }
        Color colorToCheck = piece.getColor() == Color.W ? Color.B : Color.W; // Check if the color opposite to the captured piece has won
        return this.checkForVictory(colorToCheck); // Return true if the color that did the capture has won
    }

    // Called when a piece is moved to check for glorious victories
    public Victory move(Piece piece) {
        switch (piece.getColor()){
            case B: // If a black piece was moved
                // We check glorious in order of descending value.
                if (this.checkVictoriaExcelentisma(Color.B)){
                    return Victory.EXCELENTISMA;
                }
                if (this.checkVictoriaMayor(Color.B)){
                    return Victory.MAYOR;
                }
                if (this.checkVictoriaMayor(Color.B)){
                    return Victory.MAGNA;
                }
                break;
            case W: // If a white piece was moved, we check for white's glorious victories
                if (this.checkVictoriaExcelentisma(Color.W)){
                    return Victory.EXCELENTISMA;
                }
                if (this.checkVictoriaMayor(Color.W)){
                    return Victory.MAYOR;
                }
                if (this.checkVictoriaMagna(Color.W)){
                    return Victory.MAGNA;
                }
        }
        return Victory.NONE;
    }

    public Victory checkForVictory(Color colorToCheck) { // Checks given victory condition for color that is being checked and returns either that condition or NONE
        switch (this.victory) {
            case BODIES:
                if (this.checkVictoryBodies(colorToCheck)) {
                    return Victory.BODIES;
                }
                break;
            case GOODS:
                if (this.checkVictoryValues(colorToCheck)) {
                    return Victory.GOODS;
                }
                break;
            case QUARREL:
                if (this.checkVictoryValues(colorToCheck) && this.checkVictoryDigits(colorToCheck)) {
                    return Victory.QUARREL;
                }
                break;
            case HONOR:
                if (this.checkVictoryValues(colorToCheck) && this.checkVictoryBodies(colorToCheck)) {
                    return Victory.HONOR;
                }
                break;
            case HONOR_AND_QUARREL:
                if (this.checkVictoryValues(colorToCheck) && this.checkVictoryDigits(colorToCheck) && this.checkVictoryBodies(colorToCheck)) {
                    return Victory.HONOR_AND_QUARREL;
                }
                break;
            default:
                return Victory.NONE;
        }
        return Victory.NONE;
    }

    private boolean checkVictoryBodies(Color colorToCheck) {
        return colorToCheck == Color.B ? blackCaptures.get("bodies") >= bodiesGoal : whiteCaptures.get("bodies") >= bodiesGoal;
    }

    private boolean checkVictoryDigits(Color colorToCheck) {
        return colorToCheck == Color.B ? blackCaptures.get("digits") >= digitsGoal : whiteCaptures.get("digits") >= digitsGoal;
    }

    private boolean checkVictoryValues(Color colorToCheck) {
        return colorToCheck == Color.B ? blackCaptures.get("value") >= valueGoal : whiteCaptures.get("value") >= valueGoal;
    }

    // Glorious victory involves making an arithmetic, geometric, or harmonic progression with a certain number of pieces
    // May include enemy pieces but must have one of your own pieces on one end
    // May be straight line, angle, or box as long as proportional distance between men and no obstruction between men
    // Must be positioned in enemy territory: spaces forward from your own first row

    // Requires only 3 pieces in one of the progressions
    private boolean checkVictoriaMagna(Color colorToCheck) {
        // Pull the set of valid tuples from the board
        Set<List<Piece>> triplesToCheck = board.getTriplesForColor(colorToCheck).keySet();
        // Loop through each list of pieces in the tuple
        for (List<Piece> pieces : triplesToCheck) {
            // Sort each tuple and assign the values to integers
            List<Integer> sortedPieceValues = this.sortTriple(pieces);
            int a = sortedPieceValues.get(0);
            int b = sortedPieceValues.get(1);
            int c = sortedPieceValues.get(2);
            // Check if the integers satisfy any of the progression type.
            if (this.isArithmeticProgression(a, b, c)
                || this.isGeometricProgression(a, b, c)
                || this.isHarmonicProgression(a, b, c)){
                return true;
            }
        }
        return false;
    }

    // Requires 4 total pieces that includes 2 different 3 piece progressions.
    private boolean checkVictoriaMayor(Color colorToCheck) {
        Set<List<Piece>> quadruplesToCheck = board.getQuadruplesForColor(colorToCheck);
        for (List<Piece> pieces : quadruplesToCheck) {
            List<Integer> sortedPieceValues = this.sortTriple(pieces);
            int a = sortedPieceValues.get(0);
            int b = sortedPieceValues.get(1);
            int c = sortedPieceValues.get(2);
            int d = sortedPieceValues.get(3);
            // All permutations of a, b, c, d are:
            // a, b, c; a, b, d; a, c, d; b, c, d
            // I believe this algorithm covers all cases?
            if (this.isArithmeticProgression(a, b, c) &&
                    ((this.isGeometricProgression(a,b,d)||this.isHarmonicProgression(a,b,d))
                    ||(this.isGeometricProgression(a,c,d)||this.isHarmonicProgression(a,c,d))
                    ||(this.isGeometricProgression(b,c,d)||this.isHarmonicProgression(b,c,d)))){
                return true;
            }else if (this.isGeometricProgression(a,b,c)&&
                    ((this.isArithmeticProgression(a,b,d)||this.isHarmonicProgression(a,b,d))
                    ||(this.isArithmeticProgression(a,c,d)||this.isHarmonicProgression(a,c,d))
                    ||(this.isArithmeticProgression(b,c,d)||this.isHarmonicProgression(b,c,d)))){
                return true;
            }else if(this.isHarmonicProgression(a,b,c) &&
                    ((this.isArithmeticProgression(a,b,d)||this.isGeometricProgression(a,b,d))
                    ||(this.isArithmeticProgression(a,c,d)||this.isGeometricProgression(a,c,d))
                    ||(this.isArithmeticProgression(b,c,d)||this.isGeometricProgression(b,c,d)))){
                return true;
            }
        }
        return false;
    }

    // Requires 4 total pieces that contain all 3 types of 3 piece progressions
    // An unfathomable amount of combinatorics, but I think this covers everything
    // There are 4 different arrangements of [a, b, c, d] that can be put into each of three different progressions.
    // So fundamental theorem of counting says the total is 4*3*2 = 24
    @SuppressWarnings("DuplicatedCode")
    private boolean checkVictoriaExcelentisma(Color colorToCheck) {
        Set<List<Piece>> quadruplesToCheck = board.getQuadruplesForColor(colorToCheck);
        for (List<Piece> pieces : quadruplesToCheck) {
            List<Integer> sortedPieceValues = this.sortTriple(pieces);
            int a = sortedPieceValues.get(0);
            int b = sortedPieceValues.get(1);
            int c = sortedPieceValues.get(2);
            int d = sortedPieceValues.get(3);
            if ((this.isArithmeticProgression(a, b, c) &&
                    ((this.isGeometricProgression(a, b, d) &&
                        (this.isHarmonicProgression(a,c,d) || this.isHarmonicProgression(b,c,d)))
                    ||(this.isGeometricProgression(a,c,d) &&
                        (this.isHarmonicProgression(a,b,d) || this.isHarmonicProgression(b,c,d)))
                    ||(this.isGeometricProgression(b,c,d) &&
                        (this.isHarmonicProgression(a,b,d) || this.isHarmonicProgression(a,c,d)))))
                ||(this.isArithmeticProgression(a, b, d) &&
                    ((this.isGeometricProgression(a, b, c) &&
                        (this.isHarmonicProgression(a,c,d) || this.isHarmonicProgression(b,c,d)))
                    ||(this.isGeometricProgression(a,c,d) &&
                        (this.isHarmonicProgression(a,b,c) || this.isHarmonicProgression(b,c,d)))
                    ||(this.isGeometricProgression(b,c,d) &&
                        (this.isHarmonicProgression(a,b,c) || this.isHarmonicProgression(a,c,d)))))
                ||(this.isArithmeticProgression(a, c, d) &&
                    ((this.isGeometricProgression(a, b, c) &&
                        (this.isHarmonicProgression(a,b,d) || this.isHarmonicProgression(b,c,d)))
                    ||(this.isGeometricProgression(a,b,d) &&
                        (this.isHarmonicProgression(a,b,c) || this.isHarmonicProgression(b,c,d)))
                    ||(this.isGeometricProgression(b,c,d) &&
                        (this.isHarmonicProgression(a,b,c) || this.isHarmonicProgression(a,b,d)))))
                ||(this.isArithmeticProgression(b, c, d) &&
                    ((this.isGeometricProgression(a, b, c) &&
                        (this.isHarmonicProgression(a,b,d) || this.isHarmonicProgression(a,c,d)))
                    ||(this.isGeometricProgression(a,b,d) &&
                        (this.isHarmonicProgression(a,b,c) || this.isHarmonicProgression(a,c,d)))
                    ||(this.isGeometricProgression(a,c,d) &&
                        (this.isHarmonicProgression(a,b,c) || this.isHarmonicProgression(a,b,d)))))
            ){
                return true;
            }
        }
        return false;
    }

    private boolean isArithmeticProgression(int a, int b, int c){
        // given ints a<b<c, arithmetic if b-1 = c-b => 2b = c-1 ????? Typo??
        // Should be c-b = b-a or b = (c-a)/2
        return (c-b) == (b-a);
    }

    private boolean isGeometricProgression(int a, int b, int c){
        // given ints a<b<c, geometric if b/a=c/b or b = sqrt(ac)
        // allow any type of ratio, account for division inaccuracies
        return Math.abs(((b/a)-(c/b))) < 0.00001;
    }

    private boolean isHarmonicProgression(int a, int b, int c){
        // given ints a<b<c, harmonic if (c-b)/(b-a) = c/a
        // or b = (2ac)/(a+c)
        return Math.abs(b - (2*a*c)/(a+c)) < 0.00001;
    }

    // This takes a tuple and returns it as a list of sorted integers. This makes it easier for victory checks.
    private List<Integer> sortTriple(List<Piece> pieces){
        List<Integer> sortedValues = new ArrayList<>();
        pieces.forEach(piece -> sortedValues.add(piece.getValue()));
        Collections.sort(sortedValues);
        return sortedValues;
    }
}