package All_Pieces;

import java.util.ArrayList;
import java.util.Arrays;

public class Pawn extends Piece{

    public Pawn(String coord, String color){
        super(1, coord, color);
    }

    @Override
    public ArrayList<Integer> moves() {
        ArrayList<Integer> moves = new ArrayList<Integer>(Arrays.asList(9, 10, 11, 20, -9, -10, -11, -20));
        return moves;
    }

    @Override
    public String name() {
        String name = this.getColor() + 'p';
        return name;
    }
}
