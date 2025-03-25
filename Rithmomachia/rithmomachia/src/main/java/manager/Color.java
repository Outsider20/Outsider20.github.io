package manager;

public enum Color {
    W, B;

    public static Color fromChar(char c) {
        switch (c) {
            case 'W':
            case 'w':
                return W;
            case 'B':
            case 'b':
                return B;
            default:
                throw new AssertionError();
        }
    }
    
    public char toChar() {
        switch (this) {
            case W:
                return 'W';
            case B:
                return 'B';
            default:
                throw new AssertionError();
        }
    }

}
