package Game;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece {

    public char name;
    public boolean isWhite;

    public int value;

    public Piece(char name, boolean isWhite){
        this.name = name;
        this.isWhite = isWhite;
    }

    public abstract List<Integer> getPossibleMoves(int i, int j, boolean[][] locations, boolean[][] own_locations);

    protected boolean checkIfOnBoard(int i, int j){

        if(i < 0 || i > 7){
            return false;
        }

        if(j < 0 || j > 7){
            return false;
        }

        return true;
    }

    protected int getListValue(int i, int j){

        return i*8+j;
    }

    public abstract Piece Copy();





    /*
     * Checks one field for the king.
     * */
    protected List<Integer> checkOneTile(int i, int j, int dir_i, int dir_j, boolean[][] own_locations){

        List<Integer> ret = new ArrayList<>();

        int tmp_i = i + dir_i;
        int tmp_j = j + dir_j;

        if(checkIfOnBoard(tmp_i, tmp_j) && !own_locations[tmp_i][tmp_j]){
            ret.add(getListValue(tmp_i, tmp_j));
        }

        return ret;
    }

    /*
     * Examines one be possible diagonal.
     * */
    protected List<Integer> examineDiagonal(int i, int j, int dir_i, int dir_j, boolean[][] locations, boolean[][] own_locations){

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

}
