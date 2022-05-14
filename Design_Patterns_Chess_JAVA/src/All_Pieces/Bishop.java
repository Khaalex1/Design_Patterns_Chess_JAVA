package All_Pieces;

import java.util.ArrayList;
import java.util.Arrays;

public class Bishop extends Piece{

    public Bishop(String coord, String color){
        super(3, coord, color);
    }

    @Override
    public ArrayList<Integer> moves() {
        ArrayList<Integer> moves = new ArrayList<Integer>(Arrays.asList(-11, -9, 11, 9));
        return moves;
    }

    @Override
    public String name() {
        String name = this.getColor() + 'b';
        return name;
    }
}
