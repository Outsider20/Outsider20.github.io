package rithmomachia;
import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.junit.Test;

public class MoveTest {
	
    //Demo Tests
    @Test
    public void test1() {
        String[] s = {
        		"--- --- --- --- --- --- ---",
        		"--- --- --- --- --- --- ---",
        		"--- --- --- --- --- --- ---",
        		"--- --- --- WC1 --- --- ---",
        		"--- --- --- --- --- --- ---",
        		"--- --- --- --- --- --- ---",
        		"--- --- --- --- --- --- ---",
        };
        Board b = new Board(7, 7, s);
        System.out.print("Test 1\n");
        b.printBoard();
        Piece p = b.getPiece(3, 3);
        Set<Move> pp = p.findMoves(b);
        assertEquals(8, pp.size());
    }
    
    @Test
    public void test2() {
        String[] s = {
        		"--- --- --- --- --- --- ---",
        		"--- --- --- --- --- --- ---",
        		"--- --- --- --- --- --- ---",
        		"--- --- --- WT1 --- --- ---",
        		"--- --- --- --- --- --- ---",
        		"--- --- --- --- --- --- ---",
        		"--- --- --- --- --- --- ---",
        };
        Board b = new Board(7, 7, s);
        System.out.print("Test 2\n");
        b.printBoard();
        Piece p = b.getPiece(3, 3);
        Set<Move> pp = p.findMoves(b);
        assertEquals(12, pp.size());
    }
    
    @Test
    public void test3() {
        String[] s = {
        		
        		"--- --- --- --- --- --- ---",
        		"--- --- --- --- --- --- ---",
        		"--- --- --- --- --- --- ---",
        		"--- --- --- WS1 --- --- ---",
        		"--- --- --- --- --- --- ---",
        		"--- --- --- --- --- --- ---",
        		"--- --- --- --- --- --- ---",
        };
        Board b = new Board(7, 7, s);
        System.out.print("Test 3\n");
        b.printBoard();
        Piece p = b.getPiece(3, 3);
        Set<Move> pp = p.findMoves(b);
        assertEquals(8, pp.size());
    }

    @Test
    public void test4() {
        String[] s = {

                "--- --- --- --- --- --- ---",
                "--- --- --- --- --- --- ---",
                "--- --- --- --- --- --- ---",
                "--- --- --- P,W,T1,C1,S1 --- --- ---",
                "--- --- --- --- --- --- ---",
                "--- --- --- --- --- --- ---",
                "--- --- --- --- --- --- ---",
        };
        Board b = new Board(7, 7, s);
        System.out.print("Test 4\n");
        b.printBoard();
        Piece p = b.getPiece(3, 3);
        Set<Move> pp = p.findMoves(b);
        assertEquals(28, pp.size());
    }

    @Test
    public void test5() {
        String[] s = {

                "--- --- --- --- --- --- ---",
                "--- --- --- --- --- --- ---",
                "--- --- --- --- --- --- ---",
                "--- --- --- P,W,T1 --- --- ---",
                "--- --- --- --- --- --- ---",
                "--- --- --- --- --- --- ---",
                "--- --- --- --- --- --- ---",
        };
        Board b = new Board(7, 7, s);
        System.out.print("Test 5\n");
        b.printBoard();
        Piece p = b.getPiece(3, 3);
        Set<Move> pp = p.findMoves(b);
        assertEquals(12, pp.size());
    }

    @Test
    public void test6() {
        String[] s = {

                "--- --- --- --- --- --- ---",
                "--- --- --- --- --- --- ---",
                "--- --- --- --- --- --- ---",
                "--- --- --- P,W,S1 --- --- ---",
                "--- --- --- --- --- --- ---",
                "--- --- --- --- --- --- ---",
                "--- --- --- --- --- --- ---",
        };
        Board b = new Board(7, 7, s);
        System.out.print("Test 6\n");
        b.printBoard();
        Piece p = b.getPiece(3, 3);
        Set<Move> pp = p.findMoves(b);
        assertEquals(8, pp.size());
    }

    @Test
    public void test7() {
        String[] s = {

                "--- --- --- --- --- --- ---",
                "--- --- --- --- --- --- ---",
                "--- --- --- --- --- --- ---",
                "--- --- --- P,W,C1 --- --- ---",
                "--- --- --- --- --- --- ---",
                "--- --- --- --- --- --- ---",
                "--- --- --- --- --- --- ---",
        };
        Board b = new Board(7, 7, s);
        System.out.print("Test 7\n");
        b.printBoard();
        Piece p = b.getPiece(3, 3);
        Set<Move> pp = p.findMoves(b);
        assertEquals(8, pp.size());
    }

    @Test
    public void test8() {
        String[] s = {

                "--- --- --- --- --- --- ---",
                "--- --- --- --- --- --- ---",
                "--- --- --- --- --- --- ---",
                "--- --- --- P,W,T1,C1 --- --- ---",
                "--- --- --- --- --- --- ---",
                "--- --- --- --- --- --- ---",
                "--- --- --- --- --- --- ---",
        };
        Board b = new Board(7, 7, s);
        System.out.print("Test 8\n");
        b.printBoard();
        Piece p = b.getPiece(3, 3);
        Set<Move> pp = p.findMoves(b);
        assertEquals(20, pp.size());
    }

    @Test
    public void test9() {
        String[] s = {

                "--- --- --- --- --- --- ---",
                "--- --- --- --- --- --- ---",
                "--- --- --- --- --- --- ---",
                "--- --- --- P,W,S1,C5 --- --- ---",
                "--- --- --- --- --- --- ---",
                "--- --- --- --- --- --- ---",
                "--- --- --- --- --- --- ---",
        };
        Board b = new Board(7, 7, s);
        System.out.print("Test 9\n");
        b.printBoard();
        Piece p = b.getPiece(3, 3);
        Set<Move> pp = p.findMoves(b);
        assertEquals(16, pp.size());
    }
    
    
    
    
}
