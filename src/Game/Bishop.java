package Game;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {

    public Bishop(boolean isWhite){

        super('B', isWhite);
        this.value = 3;
    }


    /*
     * Returns all possible moves for the bishop.
     * */
    @Override
    public List<Integer> getPossibleMoves(int i, int j, boolean[][] locations, boolean[][] own_locations) {

        List<Integer> ret = new ArrayList<>();


        ret.addAll(examineDiagonal(i, j, 1, 1, locations, own_locations));
        ret.addAll(examineDiagonal(i, j, 1, -1, locations, own_locations));
        ret.addAll(examineDiagonal(i, j, -1, 1, locations, own_locations));
        ret.addAll(examineDiagonal(i, j, -1, -1, locations, own_locations));

        return ret;
    }



    /*
     * Makes a copy of the Bishop.
     * */
    @Override
    public Piece Copy() {
        return new Bishop(this.isWhite);
    }


}
