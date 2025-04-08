package rithmomachia;



import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import org.junit.Assert;

import static org.junit.Assert.assertEquals;

public class CaptureBySiegeTest {

    private String toString(Set<Piece> pp) {
        StringBuilder sb = new StringBuilder();
        for (Piece p : pp) {
            sb.append(p);
        }
        return sb.toString();
    }

    @Test
    public void test1() {
        String[] s = {
                "--- --- ---",
                "--- BC1 ---",
                "WC1 --- ---"
        };
        Board b = new Board(3, 3, s);
        b.printBoard();
        Piece p = b.getPiece(1, 1);
        Set<Piece> pp = p.captureBySiege(b);
        assertEquals(0,pp.size());
    }


    @Test
    public void test2() {
        String[] s = {
                "BC1 BC1 BC1",
                "BC1 WC1 BC1",
                "BC1 BC1 BC1"
        };
        Board b = new Board(3, 3, s);
        b.printBoard();
        Piece p = b.getPiece(0, 0);
        Set<Piece> pp = p.captureBySiege(b);
        String t = toString(pp);
        assertEquals("WC1", t);
    }

    @Test
    public void test3() {
        String[] s = {
                "--- BT5 --- ---",
                "BT5 WT5 BT5 ---",
                "--- BT5 --- ---"
        };
        Board b = new Board(3, 4, s);
        b.printBoard();
        Piece p = b.getPiece(1, 1);
        Set<Piece> pp = p.captureBySiege(b);
        assertEquals(0,pp.size());

    }

    @Test
    public void test4() {
        String[] s = {
                "WC1 --- WC1",
                "--- BC1 ---",
                "WC1 --- WC1"
        };
        Board b = new Board(3, 3, s);
        b.printBoard();
        Piece p = b.getPiece(0, 0);
        Set<Piece> pp = p.captureBySiege(b);
        assertEquals(1,pp.size());



    }

    @Test
    public void test5() {
        String[] s = {
                "WC1 --- ---",
                "--- BC1 ---",
                "WC1 --- WC1"
        };
        Board b = new Board(3, 3, s);
        b.printBoard();
        Piece p = b.getPiece(0, 0);
        Set<Piece> pp = p.captureBySiege(b);
        assertEquals(0,pp.size());



    }

    @Test
    public void test6() {
        String[] s = {
                "WC1 --- WC1",
                "--- BC1 ---",
                "WC1 --- WC1"
        };
        Board b = new Board(3, 3, s);
        b.printBoard();
        Piece p = b.getPiece(1, 1);
        Set<Piece> pp = p.captureBySiege(b);
        assertEquals(0,pp.size());



    }

    @Test
    public void test7() {
        String[] s = {
                "WC1 --- WC1",
                "--- P,B,C50,S10,T70 ---",
                "WC1 --- WC1"
        };
        Board b = new Board(3, 3, s);
        b.printBoard();
        Piece p = b.getPiece(0, 2);
        Set<Piece> pp = p.captureBySiege(b);
        assertEquals(1,pp.size());



    }

    @Test
    public void test8() {
        String[] s = {
                "WC1 --- P,W,C50,S20,T70",
                "--- P,B,C50,S10,T70 ---",
                "WC1 --- WC1"
        };
        Board b = new Board(3, 3, s);
        b.printBoard();
        Piece p = b.getPiece(0, 2);
        Set<Piece> pp = p.captureBySiege(b);
        assertEquals(1,pp.size());



    }

    @Test
    public void test9() {
        String[] s = {
                "--- --- WS17 --- ---",
                "--- WS11 BT4 WS9 ---",
                "WC15 BC2 WS5 BS6 WC3",
                "--- WT13 BT8 WT7 ---",
                "--- --- WS19 --- ---",
        };
        Board b = new Board(5, 5, s);
        b.printBoard();
        Piece p = b.getPiece(2, 2);
        Set<Piece> pp = p.captureBySiege(b);
        assertEquals(4,pp.size());



    }

    @Test
    public void test10() {
        String[] s = {
                "WT13 --- WC40 --- P,W,C50,S20,T70",
                "--- BS11 WT4 BS9 ---",
                "WC5 WC2 WS5 WS6 WT70",
                "--- BT13 WT8 BT7 ---",
                "WC30 --- WT60 --- WS20",
        };
        Board b = new Board(5, 5, s);
        b.printBoard();
        Piece p = b.getPiece(2, 2);
        Set<Piece> pp = p.captureBySiege(b);
        assertEquals(4,pp.size());



    }

}
