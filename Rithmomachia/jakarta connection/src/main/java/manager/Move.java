package manager;

public class Move {

    private final int rowFrom;
    private final int colFrom;
    private final int rowTo;
    private final int colTo;

    public Move(int rowFrom, int colFrom, int rowTo, int colTo) {
        this.rowFrom = rowFrom;
        this.colFrom = colFrom;
        this.rowTo = rowTo;
        this.colTo = colTo;
    }

    public int getRowFrom() {
        return rowFrom;
    }

    public int getColFrom() {
        return colFrom;
    }

    public int getRowTo() {
        return rowTo;
    }

    public int getColTo() {
        return colTo;
    }

    public String getFrom() {
        return String.format("%c%d", (char) (colFrom + 'A'), rowFrom + 1);
    }

    public String getTo() {
        return String.format("%c%d", (char) (colTo + 'A'), rowTo + 1);
    }

}
