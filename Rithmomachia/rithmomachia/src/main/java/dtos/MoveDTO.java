package dtos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import manager.Move;

@XmlRootElement(name = "move")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class MoveDTO {

    private String from;
    private String to;

    public MoveDTO() {
    }

    public MoveDTO(Move m) {
        from = toString(m.getRowFrom(), m.getColFrom());
        to = toString(m.getRowTo(), m.getColTo());
    }
    
    private String toString(int row, int col) {
        return (char) (col + 'A') + "" + (row + 1);
    }
    
    @XmlElement
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @XmlElement
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

}
