package Game;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    public King(boolean isWhite){

        super('K', isWhite);

        // maybe not the best idea. Should be infinite.
        // "-2000" because when sum all values of one colour we would get an overflow.
        // Imagine the Queen has the highest value (9) and one queen per square we have 8*62=576 Points
        // Which is less than 2000.
        this.value = Integer.MAX_VALUE - 2000;

    }

    @Override
    public List<Integer> getPossibleMoves(int i, int j, boolean[][] locations, boolean[][] own_locations) {

        List<Integer> ret = new ArrayList<Integer>();

        int tmp_i;
        int tmp_j;

        // upper layer
        tmp_i = i - 1;
        tmp_j = j - 1;
        if(checkIfOnBoard(tmp_i, tmp_j) && !own_locations[tmp_i][tmp_j]){
            ret.add(getListValue(tmp_i, tmp_j));
        }
        tmp_i = i - 1;
        tmp_j = j;
        if(checkIfOnBoard(tmp_i, tmp_j) && !own_locations[tmp_i][tmp_j]){
            ret.add(getListValue(tmp_i, tmp_j));
        }
        tmp_i = i - 1;
        tmp_j = j + 1;
        if(checkIfOnBoard(tmp_i, tmp_j) && !own_locations[tmp_i][tmp_j]){
            ret.add(getListValue(tmp_i, tmp_j));
        }

        // middle layer
        tmp_i = i;
        tmp_j = j - 1;
        if(checkIfOnBoard(tmp_i, tmp_j) && !own_locations[tmp_i][tmp_j]){
            ret.add(getListValue(tmp_i, tmp_j));
        }
        tmp_i = i;
        tmp_j = j + 1;
        if(checkIfOnBoard(tmp_i, tmp_j) && !own_locations[tmp_i][tmp_j]){
            ret.add(getListValue(tmp_i, tmp_j));
        }

        // lower layer
        tmp_i = i + 1;
        tmp_j = j - 1;
        if(checkIfOnBoard(tmp_i, tmp_j) && !own_locations[tmp_i][tmp_j]){
            ret.add(getListValue(tmp_i, tmp_j));
        }
        tmp_i = i + 1;
        tmp_j = j;
        if(checkIfOnBoard(tmp_i, tmp_j) && !own_locations[tmp_i][tmp_j]){
            ret.add(getListValue(tmp_i, tmp_j));
        }
        tmp_i = i + 1;
        tmp_j = j + 1;
        if(checkIfOnBoard(tmp_i, tmp_j) && !own_locations[tmp_i][tmp_j]){
            ret.add(getListValue(tmp_i, tmp_j));
        }

        return ret;
    }

    @Override
    public Piece Copy() {
        return new King(this.isWhite);
    }
}
