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
    * Examines one be possible diagonal.
    * */
    private List<Integer> examineDiagonal(int i, int j, int dir_i, int dir_j, boolean[][] locations, boolean[][] own_locations){

        List<Integer> ret = new ArrayList<>();

        int tmp_i = i;
        int tmp_j = j;

        while (true) {

            tmp_i += dir_i;
            tmp_j += dir_j;

            if (checkIfOnBoard(tmp_i, tmp_j)) {

                if (own_locations[tmp_i][tmp_j]) {
                    break;
                }

                ret.add(getListValue(tmp_i, tmp_j));

                if (locations[tmp_i][tmp_j]) {
                    break;
                }

            } else {
                break;
            }
        }

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
