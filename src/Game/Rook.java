package Game;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {

    public Rook(boolean isWhite){

        super('R', isWhite);
        this.value = 5;
    }

    @Override
    public List<Integer> getPossibleMoves(int i, int j, boolean[][] locations, boolean[][] own_locations) {

        List<Integer> ret = new ArrayList<Integer>();

        // HORIZONTAL
        // down
        int tmp_i = i;
        int tmp_j = j;

        while(true){

            tmp_i++;
            if(checkIfOnBoard(tmp_i, tmp_j)){

                if(own_locations[tmp_i][tmp_j]){
                    break;
                }

                ret.add(getListValue(tmp_i, tmp_j));

                if(locations[tmp_i][tmp_j]){
                    break;
                }

            } else {
                break;
            }
        }

        // HORIZONTAL
        // up
        tmp_i = i;
        tmp_j = j;
        while(true) {

            tmp_i--;
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

        // VERTICAL
        // left
        tmp_i = i;
        tmp_j = j;
        while(true) {

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

        // VERTICAL
        // right
        tmp_i = i;
        tmp_j = j;
        while(true) {

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
        return new Rook(this.isWhite);
    }
}
