package manager;

public class Game {

    private final String id;
    private final Board board;
    private Color onMove;

    public Game(String id, Board board) {
        this.id = id;
        this.board = board;
        onMove = Color.W;
    }

    public String getId() {
        return id;
    }

    public Board getBoard() {
        return board;
    }

    public Color getOnMove() {
        return onMove;
    }

    public void move(String from, String to) {
        board.move(from, to);
        flipOnMove();
    }

    private void flipOnMove() {
        onMove = (onMove == Color.W) ? Color.B : Color.W;
    }

    public Move play() {
        Engine eng = new Engine(board, onMove);
        Move m = eng.play();
        move(m.getFrom(), m.getTo());
        return m;
    }

}
