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


    /*
     * Gets possible moves for a pawn.
     * */
    @Override
    public List<Integer> getPossibleMoves(int i, int j, boolean[][] locations, boolean[][] own_locations) {

        int direction;
        if(this.isWhite){
            direction = -1;
        } else {
            direction = 1;
        }

        List<Integer> ret = new ArrayList<>();

        // move one ahead.
        if(checkIfOnBoard(i+direction, j) && !locations[i+direction][j]){
            ret.addAll(checkOneTile(i, j, direction, 0, own_locations));
        }

        // move direction one.
        if(checkIfOnBoard(i + direction, j-1) && locations[i + direction][j-1]){
            ret.addAll(checkOneTile(i, j, direction, -1, own_locations));
        }

        // move direction two.
        if(checkIfOnBoard(i + direction, j+1) && locations[i + direction][j + 1]){
            ret.addAll(checkOneTile(i, j, direction, +1, own_locations));
        }

        // two forward
        if(this.first_move && !locations[i+direction][j] && !locations[i+direction+direction][j]){
            ret.addAll(checkOneTile(i, j, direction + direction, 0, own_locations));
        }

        return ret;

    }

    /*
     * Makes a copy of queen.
     * */
    @Override
    public Piece Copy() {
        Pawn tmp_pawn = new Pawn(this.isWhite);
        tmp_pawn.first_move = this.first_move;
        return tmp_pawn;
    }

    /*
     * Sets first move to false.
     * */
    public void setFirstMove(){
        this.first_move = false;
    }
}
