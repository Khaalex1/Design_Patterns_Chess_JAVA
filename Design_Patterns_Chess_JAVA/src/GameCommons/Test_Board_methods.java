package GameCommons;

import All_Pieces.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Test_Board_methods implements ITest_Board{

    Board B;

    public static void main(String[] args) {
        Test_Board_methods T = new Test_Board_methods();

        System.out.println("test_moves_list : " +  T.test_moves_list());
        System.out.println("test_last_move : " +  T.test_last_move());
        System.out.println("test_roi : " +  T.test_roi());
        System.out.println("test_check :" + T.test_check());
        System.out.println("test_effective_move : " + T.test_effective_move());
        System.out.println("test_potentially_reachable_squares : " + T.test_potentially_reachable_squares());
        System.out.println("test_in_between_squares : " + T.test_in_between_squares());
        System.out.println("test_truly_reachable_squares : " + T.test_truly_reachable_squares());
        System.out.println("test_algebraic_move : " + T.test_algebraic_move());
        System.out.println("test_algebraic_possible_moves : " + T.test_algebraic_possible_moves());
        System.out.println("test_mate : " + T.test_mate());
        System.out.println("test_stalemate : " + T.test_stalemate());
        System.out.println("test_make_move : " + T.test_make_move());
        System.out.println("test_can_castle : " + T.test_can_castle());
        System.out.println("test_kingside_castle : " + T.test_kingside_castle());
        System.out.println("test_queenside_castle : " + T.test_queenside_castle());

    }

    @Override
    public boolean test_moves_list() {
        B = new Board();
        ArrayList<ArrayList<String>> rec = new ArrayList<ArrayList<String>>();
        ArrayList<String> r1 = new ArrayList<String>(Arrays.asList("e4", "e5"));
        rec.add(r1);
        this.B.setRecords(rec);
        ArrayList<String> moves = this.B.moves_list();
        this.B = new Board();
        return Objects.equals(moves.get(0), "e4") && Objects.equals(moves.get(1), "e5");
    }

    @Override
    public boolean test_last_move() {
        B = new Board();
        ArrayList<ArrayList<String>> rec = new ArrayList<ArrayList<String>>();
        ArrayList<String> r1 = new ArrayList<String>(Arrays.asList("e4", "e5"));
        rec.add(r1);
        this.B.setRecords(rec);
        ArrayList<String> moves = this.B.moves_list();
        String last_move = "e5";
        this.B = new Board();
        return Objects.equals(moves.get(moves.size() - 1), last_move);
    }

    @Override
    public boolean test_potentially_reachable_squares() {
        B = new Board();
        Piece pawn_e2 = B.getState().get((int) B.getCoords().get("e2"));
        //System.out.println(pawn_e2.name());
        ArrayList<String> moves = B.potentially_reachable_squares(pawn_e2);
        String pos1 = (String) moves.get(0);
        String pos2 = (String) moves.get(1);
        //System.out.println(moves);
        return (Objects.equals(pos1, "e3") && Objects.equals(pos2, "e4"))|| Objects.equals(pos1, "e4") && Objects.equals(pos2, "e3");
    }

    @Override
    public boolean test_truly_reachable_squares() {
        B = new Board();
        boolean simple;
        boolean complex;
        boolean test_coord;

        Piece pawn_e2 = B.getState().get((int) B.getCoords().get("e2"));
        ArrayList<String> moves = B.truly_reachable_squares(pawn_e2);
        String pos1 = (String) moves.get(0);
        String pos2 = (String) moves.get(1);
        simple =  (Objects.equals(pos1, "e3") && Objects.equals(pos2, "e4"))|| Objects.equals(pos1, "e4") && Objects.equals(pos2, "e3");
        B = new Board();

        Piece pawn_w = B.getState().get((int) B.getCoords().get("e2"));
        Piece pawn_b = B.getState().get((int) B.getCoords().get("e7"));
        Piece q_b = B.getState().get((int) B.getCoords().get("d8"));
        Piece pawn_w_test = B.getState().get((int) B.getCoords().get("f2"));
        //System.out.println(B.potentially_reachable_squares(pawn_w_test));
        String coord = pawn_w_test.getCoord();
        String color = pawn_w_test.getColor();

        B.effective_move(pawn_w, "e4");
        B.effective_move(pawn_b, "e6");
        B.effective_move(pawn_w, "e5");
        //System.out.println(B.potentially_reachable_squares(pawn_w_test));
        B.effective_move(q_b, "h4");
        //System.out.println(B.potentially_reachable_squares(pawn_w_test));
        //B.effective_move(pawn_w_test, "f4");
        //System.out.println(B.check("w"));
        //System.out.println(B.potentially_reachable_squares(pawn_w_test));
        moves = B.truly_reachable_squares(pawn_w_test);
        //System.out.println(B.potentially_reachable_squares(pawn_w_test));
        String coord2 = pawn_w_test.getCoord();
        String color2 = pawn_w_test.getColor();
        //System.out.println(B.potentially_reachable_squares(pawn_w_test));
        complex = (moves.size()==0 && B.potentially_reachable_squares(pawn_w_test).size()==2);
        //System.out.println(B.potentially_reachable_squares(pawn_w_test));
        //System.out.println(moves);
        //System.out.println(B.potentially_reachable_squares(pawn_w_test));
        test_coord = (Objects.equals(coord, coord2) && Objects.equals(color, color2));

        B = new Board();
        return simple && complex && test_coord;
    }

    @Override
    public boolean test_in_between_squares() {
        B = new Board();
        ArrayList<Piece> nB = B.getState();
        nB.set(61, new None("f1", ""));
        nB.set(62, new None("g1", ""));
        B.setState(nB);
        ArrayList<String> L = B.in_between_squares(B.getState().get(60), B.getState().get(63));
        //System.out.println(L);
        String pos1 = L.get(0);
        String pos2 = L.get(1);

        B = new Board();
        return ((Objects.equals(pos1, "f1") && Objects.equals(pos2, "g1")) || (Objects.equals(pos1, "g1") && Objects.equals(pos2, "f1")));
    }

    @Override
    public boolean test_roi() {
        B = new Board();
        String color = "w";
        Piece P = this.B.king(color);
        return P.getVal()==1000 && Objects.equals(P.getCoord(), "e1");
    }

    @Override
    public boolean test_check() {
        B = new Board();
        ArrayList<Object> echec = B.check("b");
        boolean t1 = (Boolean) echec.get(0);

        ArrayList<Piece> nB = B.getState();
        nB.set(52, new None("e2", ""));
        nB.set(12, new Queen("e7", "b"));
        B.setState(nB);
        boolean t2 = (boolean) B.check("w").get(0);

        B = new Board();
        return !t1 && t2;
    }

    @Override
    public boolean test_effective_move() {
        B = new Board();

        boolean test_castle;
        boolean turn_change;
        boolean test_passant;
        boolean test_simple;

        ArrayList<Piece> nB = B.getState();
        nB.set(61, new None("f1", ""));
        nB.set(62, new None("g1", ""));
        B.setState(nB);
        ArrayList<String> L = B.in_between_squares(B.getState().get(60), B.getState().get(63));
        Piece wk = B.king("w");
        B.effective_move(wk, "g1");
        test_castle = ((B.getState().get((Integer) B.getCoords().get("g1")).name().equals("wk")) && (B.getState().get((Integer) B.getCoords().get("f1")).name().equals("wr") && B.getState().get((Integer) B.getCoords().get("e1")).name().equals("None") && B.getState().get((Integer) B.getCoords().get("h1")).name().equals("None")));

        B = new Board();
        Piece pawn_e2 = B.getState().get((Integer) B.getCoords().get("e2"));
        Piece pawn_e7 = B.getState().get((Integer) B.getCoords().get("e7"));
        B.effective_move(pawn_e2, "e4");
        String turn_black = B.getWho_plays();
        B.effective_move(pawn_e7, "e5");
        turn_change = (Objects.equals(turn_black, "b") && B.getWho_plays().equals("w"));

        test_simple = (B.getState().get((Integer) B.getCoords().get("e2")).name().equals("None") && B.getState().get((Integer) B.getCoords().get("e7")).name().equals("None") && B.getState().get((Integer) B.getCoords().get("e4")).name().equals("wp") && B.getState().get((Integer) B.getCoords().get("e5")).name().equals("bp") && B.getState().get((Integer) B.getCoords().get("e4")).getCoord()=="e4");

        B = new Board();
        Piece pawn_w = B.getState().get(52);
        Piece pawn_be = B.getState().get(12);
        Piece pawn_bd = B.getState().get(51);
        B.effective_move(pawn_w, "e4");
        B.effective_move(pawn_be, "e6");
        B.effective_move(pawn_w, "e5");
        B.effective_move(pawn_bd, "d5");
        B.effective_move(pawn_w, "d6");

        test_passant = (B.getState().get(19).name().equals("wp") && B.getState().get(27).name().equals("None"));
        B = new Board();

        return  test_castle &&turn_change  &&test_simple && test_passant;
    }

    @Override
    public boolean test_can_castle() {
        boolean trivial_test;
        boolean free_squares_test;
        boolean threaten_test;
        boolean test_blanc;
        boolean test_noir;

        B = new Board();

        trivial_test = (!B.can_castle("w").get(0) && !B.can_castle("w").get(1));
        //System.out.println(B.can_castle("w"));

        ArrayList<Piece> nb = B.getState();
        nb.set(57, new None("b1", ""));
        nb.set(58, new None("c1", ""));
        nb.set(59, new None("d1", ""));
        nb.set(61, new None("f1", ""));
        nb.set(62, new None("g1", ""));

        //B.setState(nb);

        //System.out.println(B.can_castle("w"));
        free_squares_test = (B.can_castle("w").get(0) && B.can_castle("w").get(1));

        nb.set(53, new Rook("f2", "b"));

        //System.out.println(B.getState());
        //System.out.println(B.can_castle("w"));

        threaten_test = (B.can_castle("w").get(1) && !B.can_castle("w").get(0));

        test_blanc = (trivial_test  && free_squares_test && threaten_test);


        B = new Board();

        trivial_test = (!B.can_castle("b").get(0) && !B.can_castle("b").get(1));
        //System.out.println(B.can_castle("w"));

        nb = B.getState();
        nb.set(1, new None("b8", ""));
        nb.set(2, new None("c8", ""));
        nb.set(3, new None("d8", ""));
        nb.set(5, new None("f8", ""));
        nb.set(6, new None("g8", ""));

        //B.setState(nb);

        //System.out.println(B.can_castle("w"));
        free_squares_test = (B.can_castle("b").get(0) && B.can_castle("b").get(1));

        nb.set(11, new Rook("d7", "w"));

        //System.out.println(B.getState());
        //System.out.println(B.can_castle("w"));

        threaten_test = (B.can_castle("b").get(0) && !B.can_castle("b").get(1));

        test_noir = (trivial_test  && free_squares_test && threaten_test);

        return  test_blanc && test_noir;
    }

    @Override
    public boolean test_kingside_castle() {
        B = new Board();
        return false;
    }

    @Override
    public boolean test_queenside_castle() {

        B = new Board();
        return false;
    }

    @Override
    public boolean test_mate() {
        B = new Board();
        ArrayList<Piece> nB = B.getState();
        nB.set(53, new None("f2", ""));
        nB.set(54, new None("g2", ""));
        nB.set(39, new Queen("h4", "b"));
        B.setState(nB);
        boolean mate = B.mate();
        B = new Board();
        return mate;
    }

    @Override
    public boolean test_stalemate() {
        B = new Board();
        boolean stalemate;

        ArrayList<Piece> nb = B.getState();
        for (Piece p : nb){
            nb.set((Integer) B.getCoords().get(p.getCoord()), new None(p.getCoord(), ""));
        }
        Piece k = new King("g1", "w");
        Piece b = new Bishop("b8", "b");
        Piece q = new Queen("a7", "b");
        nb.set(62, k);
        nb.set(1, b );
        nb.set(8, q);
        B.setState(nb);
        B.effective_move(k, "h1");
        B.effective_move(q, "g7");
        //return false;
        stalemate = B.stalemate();
        return stalemate;
    }

    @Override
    public boolean test_algebraic_move() {
        B = new Board();
        return false;
    }

    @Override
    public boolean test_algebraic_possible_moves() {
        B = new Board();
        return false;
    }

    @Override
    public boolean test_make_move() {
        B = new Board();
        return false;
    }

    @Override
    public boolean test_cancel_move() {
        return false;
    }


}
