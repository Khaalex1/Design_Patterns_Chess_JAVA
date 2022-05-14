package GameCommons;

import All_Pieces.*;

import java.beans.PropertyChangeSupport;
import java.lang.reflect.Array;
import java.util.*;

import static java.lang.Math.abs;
import static java.util.Map.entry;

/**
 * Classe principale qui définit l'ensemble des méthodes permettant de gérer
 * une partie.
 *
 * Exemple:
 *  Board B = new Board();
 *  Board.make_move("w", "e4");
 *  Board.make_move("b", "e5");
 */
public class Board implements IBoard {

    private final PropertyChangeSupport pcSupport;

    private ArrayList<ArrayList<String>> last_move;
    private boolean en_passant;
    private int turn;
    private String who_plays;
    private boolean white_up;
    private ArrayList<Piece> state;
    private int[] tab120;
    private int[] tab64;
    private String orientation;
    private Map<Object, Object> coords;
    private final Records rec = new Records();
    private ArrayList<ArrayList<String>> records;

    public PropertyChangeSupport getPropertyChangeSupport(){
        return pcSupport;
    }
    private Boolean mate;
    private Boolean stalemate;

    public Board(){
        pcSupport = new PropertyChangeSupport(this);
        this.white_up = false;

        this.coords = Map.<Object, Object>ofEntries(
                entry("a8", 0), entry("b8", 1), entry("c8", 2), entry("d8", 3), entry("e8", 4), entry("f8", 5), entry("g8", 6), entry("h8", 7),
                entry("a7", 8), entry("b7", 9), entry("c7", 10), entry("d7", 11), entry("e7", 12), entry("f7", 13), entry("g7", 14), entry("h7", 15),
                entry("a6", 16), entry("b6", 17), entry("c6", 18), entry("d6", 19), entry("e6", 20), entry("f6", 21), entry("g6", 22), entry("h6", 23),
                entry("a5", 24), entry("b5", 25), entry("c5", 26), entry("d5", 27), entry("e5", 28), entry("f5", 29), entry("g5", 30), entry("h5", 31),
                entry("a4", 32), entry("b4", 33), entry("c4", 34), entry("d4", 35), entry("e4", 36), entry("f4", 37), entry("g4", 38), entry("h4", 39),
                entry("a3", 40), entry("b3", 41), entry("c3", 42), entry("d3", 43), entry("e3", 44), entry("f3", 45), entry("g3", 46), entry("h3", 47),
                entry("a2", 48), entry("b2", 49), entry("c2", 50), entry("d2", 51), entry("e2", 52), entry("f2", 53), entry("g2", 54), entry("h2", 55),
                entry("a1", 56), entry("b1", 57), entry("c1", 58), entry("d1", 59), entry("e1", 60), entry("f1", 61), entry("g1", 62), entry("h1", 63),
                entry(0, "a8"), entry(1, "b8"), entry(2, "c8"), entry(3, "d8"), entry(4, "e8"), entry(5, "f8"), entry(6, "g8"), entry(7, "h8"),
                entry(8, "a7"), entry(9, "b7"), entry(10, "c7"), entry(11, "d7"), entry(12, "e7"), entry(13, "f7"), entry(14, "g7"), entry(15, "h7"),
                entry(16, "a6"), entry(17, "b6"), entry(18, "c6"), entry(19, "d6"), entry(20, "e6"), entry(21, "f6"), entry(22, "g6"), entry(23, "h6"),
                entry(24, "a5"), entry(25, "b5"), entry(26, "c5"), entry(27, "d5"), entry(28, "e5"), entry(29, "f5"), entry(30, "g5"), entry(31, "h5"),
                entry(32, "a4"), entry(33, "b4"), entry(34, "c4"), entry(35, "d4"), entry(36, "e4"), entry(37, "f4"), entry(38, "g4"), entry(39, "h4"),
                entry(40, "a3"), entry(41, "b3"), entry(42, "c3"), entry(43, "d3"), entry(44, "e3"), entry(45, "f3"), entry(46, "g3"), entry(47, "h3"),
                entry(48, "a2"), entry(49, "b2"), entry(50, "c2"), entry(51, "d2"), entry(52, "e2"), entry(53, "f2"), entry(54, "g2"), entry(55, "h2"),
                entry(56, "a1"), entry(57, "b1"), entry(58, "c1"), entry(59, "d1"), entry(60, "e1"), entry(61, "f1"), entry(62, "g1"), entry(63, "h1")
        );

        this.state = new ArrayList<Piece>(Arrays.asList(
                new Rook("a8", "b"), new Knight("b8", "b"), new Bishop("c8", "b"), new Queen("d8", "b"), new King("e8", "b"), new Bishop("f8", "b"), new Knight("g8", "b"), new Rook("h8", "b"),
                new Pawn("a7", "b"), new Pawn("b7", "b"),   new Pawn("c7", "b"),   new Pawn("d7", "b"),  new Pawn("e7", "b"), new Pawn("f7", "b"),   new Pawn("g7", "b"),   new Pawn("h7", "b"),
                new None("a6",""),   new None("b6", ""),    new None("c6", ""),    new None("d6", ""),   new None("e6", ""),  new None("f6", ""),    new None("g6", ""),    new None("h6", ""),
                new None("a5",""),   new None("b5", ""),    new None("c5", ""),    new None("d6", ""),   new None("e5", ""),  new None("f5", ""),    new None("g5", ""),    new None("h5", ""),
                new None("a4",""),   new None("b4", ""),    new None("c4", ""),    new None("d6", ""),   new None("e4", ""),  new None("f4", ""),    new None("g4", ""),    new None("h4", ""),
                new None("a3",""),   new None("b3", ""),    new None("c3", ""),    new None("d6", ""),   new None("e3", ""),  new None("f3", ""),    new None("g3", ""),    new None("h3", ""),
                new Pawn("a2","w"),  new Pawn("b2", "w"),   new Pawn("c2", "w"),   new Pawn("d2", "w"),  new Pawn("e2", "w"), new Pawn("f2", "w"),   new Pawn("g2", "w"),   new Pawn("h2", "w"),
                new Rook("a1","w"),  new Knight("b1", "w"), new Bishop("c1", "w"), new Queen("d1", "w"), new King("e1", "w"), new Bishop("f1", "w"), new Knight("g1", "w"), new Rook("h1", "w")
        ));

        this.tab120 = new int[]
                {
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, 0, 1, 2, 3, 4, 5, 6, 7, -1,
                        -1, 8, 9, 10, 11, 12, 13, 14, 15, -1,
                        -1, 16, 17, 18, 19, 20, 21, 22, 23, -1,
                        -1, 24, 25, 26, 27, 28, 29, 30, 31, -1,
                        -1, 32, 33, 34, 35, 36, 37, 38, 39, -1,
                        -1, 40, 41, 42, 43, 44, 45, 46, 47, -1,
                        -1, 48, 49, 50, 51, 52, 53, 54, 55, -1,
                        -1, 56, 57, 58, 59, 60, 61, 62, 63, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1
                };

        this.tab64 = new int[]
                {
                        21, 22, 23, 24, 25, 26, 27, 28,
                        31, 32, 33, 34, 35, 36, 37, 38,
                        41, 42, 43, 44, 45, 46, 47, 48,
                        51, 52, 53, 54, 55, 56, 57, 58,
                        61, 62, 63, 64, 65, 66, 67, 68,
                        71, 72, 73, 74, 75, 76, 77, 78,
                        81, 82, 83, 84, 85, 86, 87, 88,
                        91, 92, 93, 94, 95, 96, 97, 98
                };

        if (this.white_up) {
            this.orientation = "up";
        } else {
            this.orientation = "down";
        }

        this.who_plays = "w";
        this.turn = 1;

        this.records = new ArrayList<>();

        this.last_move = new ArrayList<ArrayList<String>>();

        this.en_passant = false;

        mate = false;
        stalemate = false;
    }

    /**
     * @return la liste des coups contenus dans l'historique records
     */
    @Override
    public ArrayList<String> moves_list() {
        ArrayList<String> moves = new ArrayList<String>();
        for (ArrayList<String> list: records) {
            moves.addAll(list);
        }
        return moves;
    }

    /**
     * @return le dernier coup ayant été joué
     */
    @Override
    public String last_move() {
        if (records.size() >= 1) {
            ArrayList<String> moves = moves_list();
            return moves.get(moves.size()-1);
        }
        return null;
    }

    /**
     * @return la liste des cases a priori/potentiellement accessibles à une pièce
     */
    @Override
    public ArrayList<String> potentially_reachable_squares(Piece piece) {
        ArrayList<Integer> dpl = piece.moves();
        ArrayList<String> PRS = new ArrayList<String>();

        int i = (int) Array.get(tab64,  (int) coords.get(piece.getCoord()));
        for (int k : dpl) {
            int s = (int) Array.get(tab120, i + k);

            if (s != -1) {
                if ((piece.name().charAt(1) != 'n') && (piece.name().charAt(1) != 'p') && (piece.name().charAt(1) != 'k')) {
                    int l = k;
                    while (s != -1) {
                        if (state.get(s).getVal() != 0) {
                            if (!Objects.equals(state.get(s).getColor(), piece.getColor())) {
                                PRS.add((String) coords.get(s));
                            }
                            break;
                        } else {
                            PRS.add((String) coords.get(s));
                            k += l;
                            s = (int) Array.get(tab120, i + k);
                        }
                    }
                }
                else {
                    if ((piece.name().charAt(1) == 'n') || (piece.name().charAt(1) == 'k')) {
                        if (!Objects.equals(state.get(s).getColor(), piece.getColor())) {
                            PRS.add((String) coords.get(s));
                        }
                        if ((piece.name().charAt(1) == 'k') && ((k == -1) || (k == 1))) {
                            if (((Objects.equals(piece.getCoord(), "e1")) && (Objects.equals(piece.getColor(), "w"))) || ((Objects.equals(piece.getCoord(), "e8")) && (Objects.equals(piece.getColor(), "b")))) {
                                int s2 = (int) Array.get(tab120, i + 2 * k);
                                if ((s2 != -1) && (state.get(s2).getVal() == 0)) {
                                    PRS.add((String) coords.get(s2));
                                }
                            }
                        }

                    }
                    else { //pawn case
                        if ((k == 9) || (k == 11) || (k == -9) || (k == -11)) {
                            if ((state.get(s).getVal() != 0) && (!Objects.equals(state.get(s).getColor(), piece.getColor()))) {
                                if ((k >= 0 && Objects.equals(piece.getColor(), "b")) || (k <= 0 && Objects.equals(piece.getColor(), "w"))) {
                                    PRS.add((String) coords.get(s));
                                }
                            }

                            String last = last_move();
                            ArrayList<String> moves = moves_list();

                            // En passant case
                            String cd = "", cg = "";
                            if ((int) Array.get(tab120, i + 1) != -1) {
                                cd = (String) coords.get((int) Array.get(tab120, i + 1));
                            }
                            if ((int) Array.get(tab120, i - 1) != -1) {
                                cg = (String) coords.get((int) Array.get(tab120, i - 1));
                            }
                            if (((k == 9) || (k == 11)) && (Objects.equals(piece.getColor(), "b"))) {
                                if (! (moves.contains("wp" + coords.get(s)))) {
                                    if (((k == 9) && (Objects.equals(last, "wp" + cg))) || ((k == 11) && (Objects.equals(last, "wp" + cd)))) {
                                        PRS.add((String) coords.get(s));
                                    }
                                }
                            } else {
                                if ((k == -9 || k == -11) && Objects.equals(piece.getColor(), "w")) {
                                    if (!moves.contains(("bp" + coords.get(s)))) {
                                        if (((k == -9) && (Objects.equals(last, "bp" + cd))) || ((k == -11) && (Objects.equals(last, "bp" + cg)))) {
                                            PRS.add((String) coords.get(s));
                                        }
                                    }
                                }
                            }
                        }
                        else { // deplacement vertical d'une ou 2 cases
                            if (state.get(s).getVal() == 0){
                                if ((k== 20) || (k == -20)){
                                    if((piece.getCoord().charAt(1) == '2')|| (piece.getCoord().charAt(1) == '7')){
                                        if (state.get((int) Array.get(tab120, i + k/2)).getVal() == 0) {
                                            PRS.add((String) coords.get(s));
                                        }
                                    }
                                } else {
                                    if (((k==10) && (Objects.equals(piece.getColor(), "b"))) || ((k == -10) && (Objects.equals(piece.getColor(), "w")))){
                                        PRS.add((String) coords.get(s));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return PRS;
    }

    /**
     * @return la liste des cases "entre" deux pièces données au sens de leurs déplacements respectifs
     */
    @Override
    public ArrayList<String> in_between_squares(Piece p1, Piece p2) {
        ArrayList<String> L = new ArrayList<String>();
        boolean same_color = false;
        String color0 = "";
        if (Objects.equals(p1.getColor(), p2.getColor())) {
            same_color = true;
            color0 = p1.getColor();
            if (Objects.equals(p2.getColor(), "w")) {
                p1.setColor("b");
            } else {
                p1.setColor("w");
            }
        }
        ArrayList<String> CA1 = potentially_reachable_squares(p1), CA2 = potentially_reachable_squares(p2);
        Piece pi;
        Piece pj;
        if (CA2.contains(p1.getCoord())||CA1.contains(p2.getCoord())) {
            if (CA2.contains(p1.getCoord())) {
                pi = p1;
                pj = p2;
            } else {
                pi = p2;
                pj = p1;
            }
            ArrayList<Integer> dpl = pj.moves();
            int i = (int) Array.get(tab64, (int) coords.get(pj.getCoord()));
            for (int k : dpl) {
                int l = 1;
                int s = (int) Array.get(tab120, i + k*l);
                while (s != -1) {
                    String c = (String) coords.get(s);
                    if (Objects.equals(c, pi.getCoord())) {
                        for (int j = 1; j < l; j++) {
                            L.add((String) coords.get(Array.get(tab120, i + k*j)));
                        }
                        if (same_color) {
                            p1.setColor(color0);
                            return L;
                        }
                    }
                    l += 1;
                    s = (int) Array.get(tab120, i + k*l);
                }
            }
        }
        if (same_color) {
            p1.setColor(color0);
        }
        return L;
    }

    /**
     * @return le roi d'un camp donné
     */
    @Override
    public Piece king(String color) {
        for(Piece piece : state){
            if (piece.getVal() == 1000 && Objects.equals(piece.getColor(), color)){
                return piece;
            }
        }
        return null;
    }

    /**
     * @return :
     *   - En cas d'échec du camp donné en paramètre:
     *      [true, L] où L est la liste des cases séparant le roi du camp mis en échec
     *      de la pièce adverse responsable de cet échec
     *   - Sinon
     *      [false, []]
     */
    @Override
    public ArrayList<Object> check(String color) {
        ArrayList<Object> check_status = new ArrayList<Object>();
        Piece king = king(color);

        for (Piece piece : state) {
            if (!Objects.equals(piece.getColor(), king.getColor()) && piece.getVal()!=0) {
                ArrayList<String> PRS = potentially_reachable_squares(piece);
                if (PRS.contains(king.getCoord())) {
                    check_status.add(true);
                    ArrayList<String> IBS = new ArrayList<String>(in_between_squares(king, piece));
                    IBS.add(piece.getCoord());
                    check_status.add(IBS);
                    return check_status;
                }
            }
        }
        check_status.add(false);
        check_status.add(new ArrayList<Object>());
        return check_status;
    }

    /**
     * Effectue le déplacement d'une piece donnée vers une case donnée au format "a8"
     * sans vérification préalable de la possibilité ou non de réaliser ce coup
     *
     * Exemple:
     *      Board B = new Board();
     *      Piece black_rook = B.getState().get(0);
     *      effective_move(black_rook, "f5");
     */
    @Override
    public void effective_move(Piece piece, String coord) {
        int i = (int) coords.get(piece.getCoord());
        int j = (int) coords.get(coord);
        ArrayList<String> obj = new ArrayList<String>(Arrays.asList(piece.getCoord(), coord, state.get(j).name()));
        last_move.add(obj);

        if (piece.name().charAt(1) == 'p') {

            boolean en_pass = ((state.get(j).getVal() == 0) && (abs(j - i) == 7 || abs(j - i) == 9));

            if (piece.getCoord().charAt(1) == '2' && Objects.equals(piece.getColor(), "b")) {
                state.set(j, new Queen((String) coords.get(j), "b"));
            } else {
                if (piece.getCoord().charAt(1) == '7' && Objects.equals(piece.getColor(), "w")) {
                    state.set(j, new Queen((String) coords.get(j), "w"));
                } else {
                    state.set(j, state.get(i));
                }
            }
            if (en_pass) {
                en_passant = true;
                if (j - i == -7 || j - i == 9) {
                    state.set(i + 1, new None((String) coords.get(i + 1), ""));
                } else {
                    if (j - i == -9 || j - i == 7) {
                        state.set(i - 1, new None((String) coords.get(i - 1), ""));
                    }
                }
            }
            state.set(i, new None((String) coords.get(i), ""));
            state.get(j).setCoord(coord);
        }
        else {
            if (piece.name().charAt(1) == 'k' && abs(j - i) == 2) { // castle case
                state.set(j, state.get(i));
                state.set(i, new None((String) coords.get(i), ""));
                state.get(j).setCoord(coord);
                if (j == i + 2) {
                    String coord1 = state.get(i + 1).getCoord();
                    String coord3 = state.get(i + 3).getCoord();
                    state.set(i + 1, state.get(i + 3));
                    state.set(i + 3, new None(coord3, ""));
                    state.get(i + 1).setCoord(coord1);
                }
                else {
                    if (j == i - 2) {
                        String coord1 = state.get(i - 1).getCoord();
                        String coord4 = state.get(i - 4).getCoord();
                        state.set(i - 1, state.get(i - 4));
                        state.set(i - 4, new None(coord4, ""));
                        state.get(i - 1).setCoord(coord1);
                    }
                }
            }
            else{
                state.set(j, state.get(i));
                state.set(i, new None((String) coords.get(i), ""));
                state.get(j).setCoord(coord);
            }
        }

        rec.addMove(who_plays, piece.name() + coord, turn);
        records = rec.records;

        if (Objects.equals(who_plays, "w")) {
            who_plays = "b";
        }
        else {
            who_plays = "w";
        }
    }

    /**
     * @return [B1, B2] où B1 et B2 sont deux booléens respectivement true si le camp donné
     * en paramètre peut roquer côté roi (petit roque) ou côté reine (grand roque)
     */
    @Override
    public ArrayList<Boolean> can_castle(String color) {
        ArrayList<Boolean> CC = new ArrayList<Boolean>();
        int i = Objects.equals(color, "b") ? 1 : 0;
        for (ArrayList<String> recorded_moves : records) {
            if (i < recorded_moves.size() && (recorded_moves.get(i).contains(color + 'k') || (recorded_moves.get(i).contains(color + 'r')))) {
                CC.add(false);
                CC.add(false);
                return CC;
            }
        }

        boolean king_side = true;
        boolean queen_side = true;

        ArrayList<Integer> KS_I = new ArrayList<Integer>(List.of(61, 63, 5, 7));
        ArrayList<Integer> QS_I = new ArrayList<Integer>(List.of(57, 60, 1, 4));
        List<Integer> I = List.of();
        List<Integer> J = List.of();

        if (Objects.equals(color, "w")) {
            I = KS_I.subList(0, 2);
            J = QS_I.subList(0, 2);
        } else if (Objects.equals(color, "b")) {
            I = KS_I.subList(2, 4);
            J = QS_I.subList(2, 4);
        }

        for (Piece piece : state.subList(I.get(0), I.get(1))) {
            if (!Objects.equals(piece.name(), "None")) {
                king_side = false;
            }
        }

        for (Piece piece : state.subList(J.get(0), J.get(1))) {
            if (!Objects.equals(piece.name(), "None")) {
                queen_side = false;
            }
        }

        if (!king_side && !queen_side) {
            CC.add(false);
            CC.add(false);
            return CC;
        }

        Piece rook1 = new Rook("", "");
        Piece rook2 = new Rook("", "");
        Piece king = new King("", "");

        if (Objects.equals(color, "w")) {
            rook1 = state.get(56);
            rook2 = state.get(63);
            king = state.get(60);
        }
        else if (Objects.equals(color, "b")) {
            rook1 = state.get(0);
            rook2 = state.get(7);
            king = state.get(4);
        }

        ArrayList<String> L1 = in_between_squares(king, rook1);
        L1.add(king.getCoord());
        ArrayList<String> L2 = in_between_squares(king, rook2);
        L2.add(king.getCoord());

        ArrayList<ArrayList<String>> L = new ArrayList<ArrayList<String>>(List.of(L1, L2));
        for (int j : List.of(0, 1)) {
            for (String square : L.get(j)) {
                for (Piece piece : state) {
                    if ((!Objects.equals(piece.getColor(), color)) && (potentially_reachable_squares(piece).contains(square))) {
                        if (j == 0) {
                            queen_side = false;
                        }
                        else {
                            king_side = false;
                        }
                    }
                }
            }
        }

        CC.add(king_side);
        CC.add(queen_side);
        return CC;
    }

    /**
     * @return la liste des cases effectivement accessibles à une pièce donnée.
     * On ne peut déplacer une pièce dont le déplacement engendre un échec pour son propre camp
     * ou est contraint par d'autres règles (en passant, roque, pion sur sa rangée initiale)
     */
    @Override @SuppressWarnings("unchecked")
    public ArrayList<String> truly_reachable_squares(Piece piece) {
        ArrayList<String> TRS = (ArrayList<String>) potentially_reachable_squares(piece).clone();
        String orig_coord = piece.getCoord();

        if (piece.getVal()==1000) {

            ArrayList<Boolean> CC = can_castle(piece.getColor());
            boolean king_side = CC.get(0);
            boolean queen_side = CC.get(1);

            ArrayList<String> L;
            if (Objects.equals(piece.getColor(), "w")) {
                L = new ArrayList<>(Arrays.asList("g1", "c1"));
            } else {
                L = new ArrayList<>(Arrays.asList("g8", "c8"));
            }
            if (!king_side && TRS.contains(L.get(0))) {
                TRS.remove(L.get(0));
            }
            if (!queen_side && TRS.contains(L.get(1))) {
                TRS.remove(L.get(1));
            }
        }

        ArrayList<String> TRS_test = (ArrayList<String>) TRS.clone();
        Board P2 = new Board();
        ArrayList<Piece> state0 = (ArrayList<Piece>) state.clone();

        for (String coord : TRS_test){
            P2.setState((ArrayList<Piece>) state0.clone());
            P2.effective_move(piece, coord);
            if ((boolean) P2.check(piece.getColor()).get(0)){
                TRS.remove(coord);
            }
            piece.setCoord(orig_coord);
        }
        return TRS;
    }

    /**
     * @return une deepcopy du plateau sur lequel cette méthode est appelée
     */
    public Board copyBoard() {
        Board copy = new Board();
        ArrayList<Piece> stateCopy = new ArrayList<>();
        for (Piece p0: state) {
            stateCopy.add(copyPiece(p0));
        }
        copy.setState(stateCopy);
        copy.records = new ArrayList<ArrayList<String>>();
        copy.last_move = new ArrayList<ArrayList<String>>();
        for (ArrayList<String> moves : records) {
            ArrayList<String> arr = new ArrayList<>(moves);
            copy.records.add(arr);
        }
        for (ArrayList<String> move : last_move) {
            ArrayList<String> arr = new ArrayList<>(move);
            copy.last_move.add(arr);
        }

        copy.who_plays = who_plays;
        copy.turn = turn;
        copy.en_passant = en_passant;
        return copy;
    }

    /**
     * @return une deepcopy de la pièce sur laquelle cette méthode est appelée
     */
    public Piece copyPiece(Piece p0) {
        String color = p0.getColor();
        String coord = p0.getCoord();
        String name = String.valueOf(p0.name().charAt(1));
        Piece p = new None(coord, color);
        switch (name) {
            case "p" -> p = new Pawn(coord, color);
            case "b" -> p = new Bishop(coord, color);
            case "n" -> p = new Knight(coord, color);
            case "r" -> p = new Rook(coord, color);
            case "q" -> p = new Queen(coord, color);
            case "k" -> p = new King(coord, color);
        }
        return p;
    }

    /**
     * Effectue si c'est possible un petit roque pour le camp donné ("w" ou "b")
     */
    @Override
    public void kingside_castle(String color) {
        if (can_castle(color).get(0)) {
            Piece king = king(color);
            int i = (int) coords.get(king.getCoord());
            int j = i+2;
            String coord = (String) coords.get(j);
            effective_move(king, coord);
        }
    }

    /**
     * Effectue si c'est possible un grand roque pour le camp donné ("w" ou "b")
     */
    @Override
    public void queenside_castle(String color) {
        if (can_castle(color).get(1)) {
            Piece king = king(color);
            int i = (int) coords.get(king.getCoord());
            int j = i-2;
            String coord = (String) coords.get(j);
            effective_move(king, coord);
        }
    }

    /**
     * @return le booléen true si le camp supposé jouer est en échec et mat, false sinon
     */
    @Override
    public boolean mate() {
        Boolean old_mate = mate;
        Piece king = king(who_plays);
        ArrayList<Object> check = check(king.getColor());
        if ((boolean) check.get(0) && truly_reachable_squares(king).isEmpty()) {
            for (Piece piece : state) {
                if (Objects.equals(piece.getColor(), king.getColor())) {
                    ArrayList<String> TRS = truly_reachable_squares(piece);
                    for (String c : TRS) {
                        if (TRS.contains(c)) {
                            return false;
                        }
                    }
                }
            }
            mate = true;
            pcSupport.firePropertyChange("Mate", old_mate, mate);
            return true;
        }
        return false;
    }

    /**
     * @return true en cas de situation de pat
     */
    @Override
    public boolean stalemate() {
        Boolean old_stalemate = stalemate;
        if(algebraic_possible_moves(who_plays).isEmpty()){
            stalemate = true;
            pcSupport.firePropertyChange("Stalemate", old_stalemate, stalemate);
        }
        return algebraic_possible_moves(who_plays).isEmpty();
    }

    /**
     * Prend en paramètre une pièce P et une case C
     *
     * @return le coup correspondant au déplacement de la pièce P vers la case C
     * en notation algébrique simplifiée
     */
    @Override
    public String algebraic_move(Piece piece, String coord) {
        String link = "";

        if (state.get((int) coords.get(coord)).getVal() != 0) {
            link = "x";
        } else {
            int i = (int) coords.get(piece.getCoord());
            int j = (int) coords.get(coord);
            if (piece.name().charAt(1) == 'p' && (abs(j - i) == 7 || abs(j - i) == 9)) { // en passant
                link = "x";
            }
        }
        char name = piece.name().charAt(1);

        if (name == 'p') {
            if (link.equals("x")) {
                return piece.getCoord().charAt(0) + link + coord;
            } else {
                return coord;
            }
        } else {
            String move = String.valueOf(name).toUpperCase() + link + coord;
            if (name == 'k') {
                int dx = (int) coords.get(coord) - (int) coords.get(piece.getCoord());
                if (dx == 2) {
                    return "0-0";
                } else {
                    if (dx == -2) {
                        return "0-0-0";
                    } else {
                        return move;
                    }
                }
            } else {
                String full_name = piece.name();
                ArrayList<Piece> identical_pieces = new ArrayList<Piece>();
                for (Piece p : state) {
                    if (Objects.equals(p.name(), full_name) && !Objects.equals(p.getCoord(), piece.getCoord())) {
                        ArrayList<String> TRS = truly_reachable_squares(p);
                        if (TRS.contains(coord)) {
                            identical_pieces.add(p);
                        }
                    }
                }
                if (identical_pieces.isEmpty()) {
                    return move;
                } else {
                    for (Piece p : identical_pieces) {
                        if (piece.getCoord().charAt(0) != p.getCoord().charAt(0)) {
                            return String.valueOf(name).toUpperCase() + piece.getCoord().charAt(0) + link + coord;
                        } else if (piece.getCoord().charAt(1) != p.getCoord().charAt(1)) {
                            return String.valueOf(name).toUpperCase() + piece.getCoord().charAt(1) + link + coord;
                        }
                    }
                    return String.valueOf(name).toUpperCase() + piece.getCoord() + link + coord;
                }
            }
        }
    }

    /**
     * @return la liste des coups possibles pour un camp donné en notation algébrique
     */
    @Override
    public ArrayList<String> algebraic_possible_moves(String color) {
        ArrayList<String> APM = new ArrayList<String>();
        for (Piece piece : state) {
            if (Objects.equals(piece.getColor(), color)) {
                ArrayList<String> TRS = truly_reachable_squares(piece);
                for (String coord : TRS) {
                    APM.add(algebraic_move(piece, coord));
                }
            }
        }
        return APM;
    }

    /**
     * Effectue un coup donné en notation algébrique
     */
    @Override
    public void make_move(String color, String move) {
        String[] symbols = new String[] {"x", "+", "#", "e.p", "=Q", "=B", "=N", "=R"};
        for (String symb: symbols) {
            move = move.replace(symb, "");
        }
        int l = move.length();
        String square;

        for (Piece p : state) {
            if (Objects.equals(p.getColor(), color)) {
                ArrayList<String> TRS = truly_reachable_squares(p);
                if (l == 2 && p.getVal() == 1) {
                    square = move;
                    if (TRS.contains(square)) {
                        effective_move(p, square);
                    }
                }

                else if (l == 3 && String.valueOf(p.name().charAt(1)).toUpperCase().equals(String.valueOf(move.charAt(0)))) {
                    square = move.substring(1);
                    if (TRS.contains(square)) {
                        effective_move(p, square);
                    }
                }

                else if (l == 3 && Character.isLowerCase(move.charAt(0))) {
                    square = move.substring(1);
                    if (String.valueOf(p.getCoord().charAt(0)).equals(String.valueOf(move.charAt(0))) && TRS.contains(square)) {
                        effective_move(p, square);
                    }
                }

                else if (l == 4 && String.valueOf(p.name().charAt(1)).toUpperCase().equals(String.valueOf(move.charAt(0)))) {
                    ArrayList<String> test = new ArrayList<String>();
                    test.add(String.valueOf(p.getCoord().charAt(0)));
                    test.add(String.valueOf(p.getCoord().charAt(1)));
                    if (test.contains(String.valueOf(move.charAt(1)))) {
                        square = move.substring(2);
                        if (TRS.contains(square)) {
                            effective_move(p, square);
                        }
                    }
                }

                else if (move.equals("O-O")) {
                    kingside_castle(color);
                }
                else if (move.equals("O-O-O")) {
                    queenside_castle(color);
                }
            }
        }
    }

    /**
     * Annule le dernier coup joué
     */
    @Override
    public void cancel_move() {
        if (last_move.size() == 0) {
            return;
        }
        ArrayList<String> lm = last_move.get(last_move.size() - 1);
        String c0 = lm.get(0);
        String c1 = lm.get(1);
        String p1_name = lm.get(2);

        if (p1_name.equals("None")) {
            p1_name = "-i";
        }

        if (coords.get(c1).equals("k") && Math.abs((int) coords.get(c1) - (int) coords.get(c0)) == 2) {
            int dx = (int) coords.get(c1) - (int) coords.get(c0);
            String clr = state.get((int) coords.get(c1)).getColor();
            int a = (clr.equals("w")) ? 60 : 4;

            if (dx == 2) {
                state.set(a + 3, new Rook((String) coords.get(a + 3), clr));
                state.set(a, new King(c0, clr));
                state.set(a + 2, new None(c1, ""));
                state.set(a + 1, new None((String) coords.get(a + 1), ""));
            } else if (dx == -2) {
                state.set(a - 4, new Rook((String) coords.get(a + 3), clr));
                state.set(a, new King(c0, clr));
                state.set(a - 2, new None(c1, ""));
                state.set(a - 1, new None((String) coords.get(a - 1), ""));
            }
        }
        else {
            Piece p1 = new None("", ""); // random initializer
            ArrayList<Piece> P = new ArrayList<Piece>(List.of(new Rook("", "w"), new Bishop("", "w"), new Knight("", "w"), new Queen("", "w"), new King("", "w"), new Pawn("", "w"), new None("", "w")));
            for (Piece p : P) {
                if (p.name().charAt(1) == p1_name.charAt(1)) {
                    p1 = p;
                    p1.setColor(String.valueOf(p1_name.charAt(0)));
                    p1.setCoord(c1);
                }
            }

            Piece p0 = state.get((int) coords.get(c1));
            state.set((int) coords.get(c0), p0);
            state.set((int) coords.get(c1), p1);
            p0.setCoord(c0);

            if (en_passant && p0.getVal() == 1) {
                String s = String.valueOf(c1.charAt(0)) + String.valueOf(c0.charAt(1));
                state.set((int) coords.get(s), new Pawn("", ""));
                state.get((int) coords.get(s)).setCoord(s);
                String clr = (p0.getColor().equals("b")) ? "w" : "b";
                state.get((int) coords.get(s)).setColor(clr);
            }

            en_passant = false;
        }

        last_move.remove(lm);
        int key = records.size() - 1;
        ArrayList<String> value = records.get(key);

        if (value.size() > 1) {
            value.remove(1);
            turn -= 1;
            who_plays = "b";
        } else {
            records.remove(key);
            who_plays = "w";
        }
    }

    public ArrayList<ArrayList<String>> getLastMove() {return last_move;}
    public void setLastMove(ArrayList<ArrayList<String>> lm) {this.last_move = lm;}
    public boolean isEn_passant() {return en_passant;}
    public void setEn_passant(boolean en_passant) {this.en_passant = en_passant;}
    public int getTurn() {return turn;}
    public void setTurn(int turn) {this.turn = turn;}
    public String getWho_plays() {return who_plays;}
    public void setWho_plays(String who_plays) {this.who_plays = who_plays;}
    public boolean isWhite_up() {return white_up;}
    public void setWhite_up(boolean white_up) {this.white_up = white_up;}
    public ArrayList<Piece> getState() {return state;}
    public void setState(ArrayList<Piece> state) {this.state = state;}
    public int[] getTab120() {return tab120;}
    public void setTab120(int[] tab120) {this.tab120 = tab120;}
    public int[] getTab64() {return tab64;}
    public void setTab64(int[] tab64) {this.tab64 = tab64;}
    public String getOrientation() {return orientation;}
    public void setOrientation(String orientation) {this.orientation = orientation;}
    public Map<Object, Object> getCoords() {return coords;}
    public void setCoords(Map<Object, Object> coords) {this.coords = coords;}
    // public Map<Integer, String> getReverse_coords() {return reverse_coords;}
    // public void setReverse_coords(Map<Integer, String> reverse_coords) {this.reverse_coords = reverse_coords;}
    public ArrayList<ArrayList<String>> getRecords() {return records;}
    public void setRecords(ArrayList<ArrayList<String>> records) {this.records = records;}
}
