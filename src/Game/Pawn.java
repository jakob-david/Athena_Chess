package Game;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    public boolean first_move;

    public Pawn(boolean isWhite){

        super('P', isWhite);
        this.value = 1;
        this.first_move = true;
    }

    @Override
    public List<Integer> getPossibleMoves(int i, int j, boolean[][] locations, boolean[][] own_locations) {

        List<Integer> ret = new ArrayList<Integer>();

        int direction;
        if(this.isWhite){
            direction = -1;
        } else {
            direction = 1;
        }

        int tmp_i;
        int tmp_j;

        // move ahead
        tmp_i = i + direction;
        tmp_j = j;
        if(checkIfOnBoard(tmp_i, tmp_j) && !locations[tmp_i][tmp_j]){
            ret.add(getListValue(tmp_i, tmp_j));
        }

        // move left
        tmp_i = i + direction;
        tmp_j = j - 1 ;
        if(checkIfOnBoard(tmp_i, tmp_j) && locations[tmp_i][tmp_j] && !own_locations[tmp_i][tmp_j]){
            ret.add(getListValue(tmp_i, tmp_j));
        }

        // move left
        tmp_i = i + direction;
        tmp_j = j + 1 ;
        if(checkIfOnBoard(tmp_i, tmp_j) && locations[tmp_i][tmp_j] && !own_locations[tmp_i][tmp_j]) {
            ret.add(getListValue(tmp_i, tmp_j));
        }

        // two forward
        tmp_i = i + direction + direction;
        tmp_j = j;
        if(checkIfOnBoard(tmp_i, tmp_j) && !locations[tmp_i][tmp_j] && this.first_move) {
            ret.add(getListValue(tmp_i, tmp_j));
        }

        return ret;

    }

    @Override
    public Piece Copy() {
        Pawn tmp_pawn = new Pawn(this.isWhite);
        tmp_pawn.first_move = this.first_move;
        return tmp_pawn;
    }

    public void setFirstMove(){
        this.first_move = false;
    }
}
