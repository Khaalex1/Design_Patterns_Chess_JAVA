package GameCommons;

import All_Pieces.Piece;

import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation de l'algorithme Negamax avec élagage alpha-beta
 */
public class Engine {
    private String swap(String wp) {
        return (wp.equals("b")) ? "w" :"b";
    }

    /**
     * Fonction d'évaluation naïve qui ne calcule que la différence
     * de matériel entre deux camps à un instant donné
     *
     * @param B
     * @param wp
     * @return avantage matériel pour le camp wp
     */
    private int evaluate(Board B, String wp) {
        int white_score = 0;
        int black_score = 0;
        for (Piece p: B.getState()) {
            if (p.getColor().equals("w") && (p.getVal()<10)) {
                white_score += p.getVal();
            } else if (p.getColor().equals("b") && (p.getVal()<10)) {
                black_score += p.getVal();
            }
        }
        int advantage = white_score - black_score;
        if (wp.equals("b")) {
            advantage *= -1;
        }
        return advantage;
    }

    private int negaMax(Board B, String wp, int depth, double a, double b) {
        if (depth == 0) {
            return evaluate(B, wp);
        }
        double max_score = Double.NEGATIVE_INFINITY;
        ArrayList<String> APM = B.algebraic_possible_moves(wp);

        for (String move: APM) {
            B.make_move(wp, move);
            max_score = Math.max(max_score, -negaMax(B, swap(wp), depth-1, -b, -a));

            a = Math.max(a, max_score);
            if (a >= b) {
                break;
            }
        }
        return (int) max_score;
    }

    /**
     * Calcul le meilleur coup possible pour le camp wp selon la fonction
     * d'évaluation evaluate() avec une profondeur depth
     *
     * @param B
     * @param wp
     * @param depth
     * @param a
     * @param b
     * @return [best_move, score]
     */
    public ArrayList<Object> best_move(Board B, String wp, int depth, double a, double b) {
        String best_move = "";
        double score = Double.NEGATIVE_INFINITY;
        int move_score;
        ArrayList<String> APM = B.algebraic_possible_moves(wp);

        for (String move: APM) {
            Board copy = B.copyBoard();
            copy.make_move(wp, move);
            move_score = -negaMax(copy, swap(wp), depth-1, a, b);

            if (move_score >= score) {
                best_move = move;
                score = move_score;
            }
        }
        return new ArrayList<>(List.of(best_move, score));
    }

    public static void main(String[] args) {
        System.out.println("");
    }
}
