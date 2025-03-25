package ws;

import dtos.BoardDTO;
import dtos.GameDTO;
import dtos.PieceDTO;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import manager.Board;
import manager.Game;
import manager.Games;
import manager.Piece;

@Path("games")
public class GamesWS {

    @GET
    @Produces("application/json")
    public Response read() {
        try {
            Games gs = Games.getInstance();
            List<GameDTO> dtos = new ArrayList<>();
            for (Game g : gs.getGames()) {
                GameDTO dto = new GameDTO(g);
                dtos.add(dto);
            }
            return Response.ok(dtos).build();
        } catch (IllegalArgumentException e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Produces("text/plain")
    public Response create() {
        try {
            Games gs = Games.getInstance();
            Game g = gs.createNewGame();
            return Response.ok(g.getId()).build();
        } catch (IllegalArgumentException e) {
            return Response.serverError().build();
        }
    }

    @PUT
    @Produces("text/plain")
    public Response create(BoardDTO board) {
        try {
            Games gs = Games.getInstance();
            Board b = toBoard(board);
            Game g = gs.createNewGame(b);
            return Response.ok(g.getId()).build();
        } catch (IllegalArgumentException e) {
            return Response.serverError().build();
        }
    }

    private Board toBoard(BoardDTO b) {
        List<Piece> pp = new ArrayList<>();
        for (PieceDTO dto : b.getPieces()) {
            Piece p = new Piece(
                    dto.getColor(),
                    dto.getKind(),
                    dto.getValue(),
                    dto.getPos());
            pp.add(p);
        }
        return new Board(pp);
    }

}
