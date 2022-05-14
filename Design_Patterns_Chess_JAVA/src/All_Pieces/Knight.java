package All_Pieces;

import java.util.ArrayList;
import java.util.Arrays;

public class Knight extends Piece{

    public Knight(String coord, String color){
        super(3, coord, color);
    }
    @Override
    public ArrayList<Integer> moves() {
        ArrayList<Integer> moves = new ArrayList<Integer>(Arrays.asList(-12, -21, -19, -8, 12, 21, 19, 8));
        return moves;
    }

    @Override
    public String name() {
        String name = this.getColor() + 'n';
        return name;
    }
}
