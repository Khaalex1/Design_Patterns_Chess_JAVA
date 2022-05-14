package All_Pieces;

import java.util.ArrayList;

/**
 * Cette classe impose à chacune de ses sous-classes (King, Bishop...) de donner une valeur aux
 * variables d'instance (int) val, (String) coord et (String) color conformément à la valeur, la
 * position et la couleur qu'on souhaite leur donner respectivement.
 *
 * Chaque sous-classe doit de plus définir les méthodes moves() et name() qui renvoient respectivement
 * les directions des déplacements possibles d'une pièce et son nom.
 */
public abstract class Piece {

    private int val;
    private String coord;
    private String color;

    public Piece(int val, String coord, String color){
        this.val = val;
        this.coord = coord;
        this.color = color;
    }

    public abstract ArrayList<Integer> moves();
    public abstract String name();



    public int getVal() {
        return val;
    }
    public void setVal(int val) {
        this.val = val;
    }
    public String getCoord() {
        return coord;
    }
    public void setCoord(String coord) {
        this.coord = coord;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
}
