package All_Pieces;

import java.util.ArrayList;
import java.util.Arrays;

public class King extends Piece{
    public King(String coord, String color){
        super(1000, coord, color);
    }
    @Override
    public ArrayList<Integer> moves() {
        ArrayList<Integer> moves = new ArrayList<Integer>(Arrays.asList(-11, -9, 11, 9, -10, 10, -1, 1));
        return moves;
    }

    @Override
    public String name() {
        String name = this.getColor() + 'k';
        return name;
    }
}
