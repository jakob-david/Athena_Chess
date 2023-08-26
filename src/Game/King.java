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

        ret.addAll(checkOneTile(i, j, 1, 1, locations, own_locations));
        ret.addAll(checkOneTile(i, j, 1, 0, locations, own_locations));
        ret.addAll(checkOneTile(i, j, 1, -1, locations, own_locations));

        ret.addAll(checkOneTile(i, j, 0, 1, locations, own_locations));
        ret.addAll(checkOneTile(i, j, 0, -1, locations, own_locations));

        ret.addAll(checkOneTile(i, j, -1, 1, locations, own_locations));
        ret.addAll(checkOneTile(i, j, -1, 0, locations, own_locations));
        ret.addAll(checkOneTile(i, j, -1, -1, locations, own_locations));

        return ret;
    }

    /*
     * Checks one field for the king.
     * */
    private List<Integer> checkOneTile(int i, int j, int dir_i, int dir_j, boolean[][] locations, boolean[][] own_locations){

        List<Integer> ret = new ArrayList<>();

        int tmp_i = i + dir_i;
        int tmp_j = j + dir_j;

        if(checkIfOnBoard(tmp_i, tmp_j) && !own_locations[tmp_i][tmp_j]){
            ret.add(getListValue(tmp_i, tmp_j));
        }

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
