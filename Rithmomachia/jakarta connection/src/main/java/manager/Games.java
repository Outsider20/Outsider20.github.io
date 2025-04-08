package manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Games {

    private static final Games instance = new Games();
    private final Map<String, Game> gameMap = new HashMap<>();

    public static Games getInstance() {
        return instance;
    }

    public Game createNewGame() {
        Board b = standardBoard();
        return createNewGame(b);
    }
    
    private Board standardBoard() {
        List<Piece> pp = Arrays.asList(
                new Piece("WS289A1"),
                new Piece("WS169B1"),
                new Piece("WS81G1"),
                new Piece("WS25H1"),
                new Piece("WS153A2"),
                new Piece("WP91B2"),
                new Piece("WT49C2"),
                new Piece("WT42D2"),
                new Piece("WT20E2"),
                new Piece("WT25F2"),
                new Piece("WS45G2"),
                new Piece("WS15H2"),
                new Piece("WT81A3"),
                new Piece("WT72B3"),
                new Piece("WR64C3"),
                new Piece("WR36D3"),
                new Piece("WR16E3"),
                new Piece("WR4F3"),
                new Piece("WT6G3"),
                new Piece("WT9H3"),
                new Piece("WR8C4"),
                new Piece("WR6D4"),
                new Piece("WR4E4"),
                new Piece("WR2F4"),
                new Piece("BR3C13"),
                new Piece("BR5D13"),
                new Piece("BR7E13"),
                new Piece("BR9F13"),
                new Piece("BT16A14"),
                new Piece("BT12B14"),
                new Piece("BR9C14"),
                new Piece("BR25D14"),
                new Piece("BR49E14"),
                new Piece("BR18F14"),
                new Piece("BT90G14"),
                new Piece("BT160H14"),
                new Piece("BS28A15"),
                new Piece("BS66B15"),
                new Piece("BT36C15"),
                new Piece("BT30D15"),
                new Piece("BT56E15"),
                new Piece("BT64F15"),
                new Piece("BS120G15"),
                new Piece("BP190H15"),
                new Piece("BS49A16"),
                new Piece("BS121B16"),
                new Piece("BS225G16"),
                new Piece("BS361H16")
        );
        return new Board(pp);
    }
    
    public Game createNewGame(Board board) {
        String id = newId();
        Game g = new Game(id, board);
        gameMap.put(id, g);
        return g;
    }

    private String newId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
    
    public Game getGame(String id) {
        return gameMap.get(id);
    }
    
    public List<Game> getGames() {
        List<Game> gg = new ArrayList<>();
        for (Game g : gameMap.values()) {
            gg.add(g);
        }
        return gg;
    }
    
    public void deleteGame(String id) {
        gameMap.remove(id);
    }

}
