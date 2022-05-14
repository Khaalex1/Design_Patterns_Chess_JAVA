package All_Pieces;

import java.util.ArrayList;

public class None extends Piece{

    public None(String coord, String color){
        super(0, coord, color);
    }
    @Override
    public ArrayList<Integer> moves() {
        ArrayList<Integer> moves = new ArrayList<Integer>();
        return moves;
    }

    @Override
    public String name() {
        String name = "None";
        return name;
    }
}
