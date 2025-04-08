package dtos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import manager.Board;
import manager.Piece;

@XmlRootElement(name = "board")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class BoardDTO {

    private List<PieceDTO> pieces = new ArrayList<>();

    public BoardDTO() {
    }

    public BoardDTO(Board b) {
        for (Piece p : b.getPieces()) {
            PieceDTO dto = new PieceDTO(p);
            pieces.add(dto);
        }
    }

    @XmlElement(name = "piece")
    public List<PieceDTO> getPieces() {
        return pieces;
    }

    public void setPieces(List<PieceDTO> pieces) {
        this.pieces = pieces;
    }

}
