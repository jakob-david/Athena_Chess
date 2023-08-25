package Game;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {

    public Bishop(boolean isWhite){

        super('B', isWhite);
        this.value = 3;
    }

    @Override
    public List<Integer> getPossibleMoves(int i, int j, boolean[][] locations, boolean[][] own_locations) {

        List<Integer> ret = new ArrayList<Integer>();

        int tmp_i;
        int tmp_j;

        // left up
        tmp_i = i;
        tmp_j = j;

        while (true) {

            tmp_i--;
            tmp_j--;

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

        // right up
        tmp_i = i;
        tmp_j = j;

        while (true) {

            tmp_i--;
            tmp_j++;

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


        // left down
        tmp_i = i;
        tmp_j = j;

        while (true) {

            tmp_i++;
            tmp_j--;

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


        // right down
        tmp_i = i;
        tmp_j = j;

        while (true) {

            tmp_i++;
            tmp_j++;

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

    @Override
    public Piece Copy() {
        return new Bishop(this.isWhite);
    }


}
