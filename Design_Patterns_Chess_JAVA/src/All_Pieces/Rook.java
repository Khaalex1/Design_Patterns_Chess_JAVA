package All_Pieces;

import java.util.ArrayList;
import java.util.Arrays;

public class Rook extends Piece{

    public Rook(String coord, String color){
        super(5, coord, color);
    }
    @Override
    public ArrayList<Integer> moves() {
        ArrayList<Integer> moves = new ArrayList<Integer>(Arrays.asList(-10, 10, -1, 1));
        return moves;
    }

    @Override
    public String name() {
        String name = this.getColor() + 'r';
        return name;
    }
}
