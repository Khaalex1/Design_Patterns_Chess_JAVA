package GameCommons;

public interface ITest_Board {
    boolean test_moves_list();
    boolean test_last_move();
    boolean test_potentially_reachable_squares();
    boolean test_truly_reachable_squares();
    boolean test_in_between_squares();
    boolean test_roi();
    boolean test_check();
    boolean test_effective_move();
    boolean test_can_castle();
    boolean test_kingside_castle();
    boolean test_queenside_castle();
    boolean test_mate();
    boolean test_stalemate();
    boolean test_algebraic_move();
    boolean test_algebraic_possible_moves();
    boolean test_make_move();
    boolean test_cancel_move();
}
