package manager;

public class Pos {

    private final int row;
    private final int col;

    public Pos(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public static Pos fromString(String s) {
        int col = s.charAt(0) - 'A';
        int row = Integer.parseInt(s.substring(1)) - 1;
        return new Pos(row, col);
    }

    @Override
    public String toString() {
        return String.format("%c%d", (char) (col + 'A'), row + 1);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pos) {
            Pos that = (Pos) obj;
            return row == that.row && col == that.col;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 89 * row + col;
    }

}
