package dtos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import manager.Game;

@XmlRootElement(name = "game")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class GameDTO {

    private String id;
    private BoardDTO board;
    private char onMove;

    public GameDTO() {
    }

    public GameDTO(Game g) {
        id = g.getId();
        board = new BoardDTO(g.getBoard());
        onMove = g.getOnMove().toChar();
    }

    @XmlElement
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement
    public BoardDTO getBoard() {
        return board;
    }

    public void setBoard(BoardDTO board) {
        this.board = board;
    }

    @XmlElement
    public char getOnMove() {
        return onMove;
    }

    public void setOnMove(char onMove) {
        this.onMove = onMove;
    }

}
