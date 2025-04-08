package manager;

public class Piece {

    private Color color;
    private char kind;
    private int value;
    private Pos pos;

    public Piece(String desc) {
        char c = desc.charAt(0);
        color = Color.fromChar(c);
        kind = desc.charAt(1);
        int i = 2;
        for (; i < desc.length(); i++) {
            char d = desc.charAt(i);
            if (!Character.isDigit(d)) {
                break;
            }
        }
        value = Integer.parseInt(desc.substring(2, i));
        pos = Pos.fromString(desc.substring(i));
    }
    
    public Piece(char color, char kind, int value, String pos) {
        this.color = Color.fromChar(color);
        this.kind = kind;
        this.value = value;
        this.pos = Pos.fromString(pos);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public char getKind() {
        return kind;
    }

    public void setKind(char kind) {
        this.kind = kind;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Pos getPos() {
        return pos;
    }

    public void setPos(Pos pos) {
        this.pos = pos;
    }

}
