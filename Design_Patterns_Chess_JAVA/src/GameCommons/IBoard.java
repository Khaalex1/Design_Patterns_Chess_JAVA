package GameCommons;

import All_Pieces.Piece;

import java.util.ArrayList;

public interface IBoard {

    ArrayList<String> moves_list();
    String last_move();
    ArrayList<String> potentially_reachable_squares(Piece piece);
    ArrayList<String> truly_reachable_squares(Piece piece);
    ArrayList<String> in_between_squares(Piece p1, Piece p2);
    Piece king(String color);
    ArrayList<Object> check(String color);
    void effective_move(Piece piece, String coord);
    ArrayList<Boolean> can_castle(String color);
    void kingside_castle(String color);
    void queenside_castle(String color);
    boolean mate();
    boolean stalemate();
    String algebraic_move(Piece piece, String coord);
    ArrayList<String> algebraic_possible_moves(String color);
    void make_move(String color, String move);
    void cancel_move();

}
