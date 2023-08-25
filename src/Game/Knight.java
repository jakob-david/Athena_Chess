package Game;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {

    public Knight(boolean isWhite){

        super('N', isWhite);
        this.value = 3;
    }

    @Override
    public List<Integer> getPossibleMoves(int i, int j, boolean[][] locations, boolean[][] own_locations) {

        List<Integer> ret = new ArrayList<Integer>();

        int tmp_i;
        int tmp_j;

        //first upper layer - left
        tmp_i = i - 2;
        tmp_j = j - 1;
        if(checkIfOnBoard(tmp_i, tmp_j) && !own_locations[tmp_i][tmp_j]){
            ret.add(getListValue(tmp_i, tmp_j));
        }
        //first upper layer - right
        tmp_i = i - 2;
        tmp_j = j + 1;
        if(checkIfOnBoard(tmp_i, tmp_j) && !own_locations[tmp_i][tmp_j]){
            ret.add(getListValue(tmp_i, tmp_j));
        }

        //second upper layer - left
        tmp_i = i - 1;
        tmp_j = j - 2;
        if(checkIfOnBoard(tmp_i, tmp_j) && !own_locations[tmp_i][tmp_j]){
            ret.add(getListValue(tmp_i, tmp_j));
        }
        //second upper layer - right
        tmp_i = i - 1;
        tmp_j = j + 2;
        if(checkIfOnBoard(tmp_i, tmp_j) && !own_locations[tmp_i][tmp_j]){
            ret.add(getListValue(tmp_i, tmp_j));
        }

        //first lower layer - left
        tmp_i = i + 1;
        tmp_j = j - 2;
        if(checkIfOnBoard(tmp_i, tmp_j) && !own_locations[tmp_i][tmp_j]){
            ret.add(getListValue(tmp_i, tmp_j));
        }
        //first lower layer - right
        tmp_i = i + 1;
        tmp_j = j + 2;
        if(checkIfOnBoard(tmp_i, tmp_j) && !own_locations[tmp_i][tmp_j]){
            ret.add(getListValue(tmp_i, tmp_j));
        }

        //second lower layer - left
        tmp_i = i + 2;
        tmp_j = j - 1;
        if(checkIfOnBoard(tmp_i, tmp_j) && !own_locations[tmp_i][tmp_j]){
            ret.add(getListValue(tmp_i, tmp_j));
        }
        //second lower layer - right
        tmp_i = i + 2;
        tmp_j = j + 1;
        if(checkIfOnBoard(tmp_i, tmp_j) && !own_locations[tmp_i][tmp_j]) {
            ret.add(getListValue(tmp_i, tmp_j));
        }

        return ret;
    }

    @Override
    public Piece Copy() {
        return new Knight(this.isWhite);
    }
}
