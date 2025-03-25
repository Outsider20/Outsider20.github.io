package dtos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import manager.Piece;

@XmlRootElement(name = "piece")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class PieceDTO {

    private char color;
    private char kind;
    private int value;
    private String pos;

    public PieceDTO() {
    }

    public PieceDTO(Piece p) {
        color = p.getColor().toChar();
        kind = p.getKind();
        value = p.getValue();
        pos = p.getPos().toString();
    }

    @XmlElement
    public char getColor() {
        return color;
    }

    public void setColor(char color) {
        this.color = color;
    }

    @XmlElement
    public char getKind() {
        return kind;
    }

    public void setKind(char kind) {
        this.kind = kind;
    }

    @XmlElement
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @XmlElement
    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

}
