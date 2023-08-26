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

    /*
     * Returns all possible moves for the king.
     * */
    @Override
    public List<Integer> getPossibleMoves(int i, int j, boolean[][] locations, boolean[][] own_locations) {

        List<Integer> ret = new ArrayList<>();

        ret.addAll(checkOneTile(i, j, 1, 1, own_locations));
        ret.addAll(checkOneTile(i, j, 1, 0, own_locations));
        ret.addAll(checkOneTile(i, j, 1, -1, own_locations));

        ret.addAll(checkOneTile(i, j, 0, 1, own_locations));
        ret.addAll(checkOneTile(i, j, 0, -1, own_locations));

        ret.addAll(checkOneTile(i, j, -1, 1, own_locations));
        ret.addAll(checkOneTile(i, j, -1, 0, own_locations));
        ret.addAll(checkOneTile(i, j, -1, -1, own_locations));

        return ret;
    }


    /*
     * Returns a copy of the king.
     * */
    @Override
    public Piece Copy() {
        return new King(this.isWhite);
    }
}
