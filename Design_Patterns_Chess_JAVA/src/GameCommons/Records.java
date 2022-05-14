package GameCommons;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Composite du pattern Composant/Composite
 */
public class Records implements History {
    ArrayList<ArrayList<String>> records;

    public Records() {
        this.records = new ArrayList<>();
    }

    /**
     * Ajoute un coup à l'historique
     *
     * @param wp
     * @param move
     * @param turn
     */
    @Override
    public void addMove(String wp, String move, int turn) {
        if (Objects.equals(wp, "w")) {
            ArrayList<String> turn_move = new ArrayList<String>(List.of(move));
            records.add(turn_move);
        } else {
            records.get(records.size() - 1).add(move);
            turn += 1;
        }
    }
}
