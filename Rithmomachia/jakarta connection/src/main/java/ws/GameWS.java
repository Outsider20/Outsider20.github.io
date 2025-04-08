package ws;

import dtos.GameDTO;
import dtos.MoveDTO;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import manager.Game;
import manager.Games;

@Path("game/{id}")
public class GameWS {

    @GET
    @Produces("application/json")
    public Response board(@PathParam("id") String id) {
        try {
            Games gs = Games.getInstance();
            Game g = gs.getGame(id);
            GameDTO dto = new GameDTO(g);
            return Response.ok(dto).build();
        } catch (IllegalArgumentException e) {
            return Response.serverError().build();
        }
    }

    @DELETE
    public Response delete(@PathParam("id") String id) {
        try {
            Games gs = Games.getInstance();
            gs.deleteGame(id);
            return Response.ok().build();
        } catch (IllegalArgumentException e) {
            return Response.serverError().build();
        }
    }

    @PUT
    @Consumes("application/json")
    public Response move(@PathParam("id") String id, MoveDTO dto) {
        try {
            Games gs = Games.getInstance();
            Game g = gs.getGame(id);
            g.move(dto.getFrom(), dto.getTo());
            return Response.ok().build();
        } catch (IllegalArgumentException e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Consumes("application/json")
    public Response play(@PathParam("id") String id) {
        try {
            Games gs = Games.getInstance();
            Game g = gs.getGame(id);
            MoveDTO dto = new MoveDTO(g.play());
            return Response.ok(dto).build();
        } catch (IllegalArgumentException e) {
            return Response.serverError().build();
        }
    }

}
