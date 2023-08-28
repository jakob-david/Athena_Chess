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


    //
    // Abstract functions.
    // -----------------------------

    /*
     * Gets all possible moves for a Piece.
     * */
    public abstract List<Integer> getPossibleMoves(int i, int j, boolean[][] locations, boolean[][] own_locations);

    /*
     * Makes a copy of a Piece.
     * */
    public abstract Piece Copy();





    //
    // Helper functions for "getMoves".
    // -----------------------------

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
     * Examines one be possible diagonal/horizontal/vertical.
     * */
    private List<Integer> examineLine(int i, int j, int dir_i, int dir_j, boolean[][] locations, boolean[][] own_locations){

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
     * Checks Rook moves.
     * */
    protected List<Integer> checkRookMoves(int i, int j, boolean[][] locations, boolean[][] own_locations){

        List<Integer> ret = new ArrayList<>();

        // Rook Moves
        ret.addAll(examineLine(i, j, 1, 0, locations, own_locations));
        ret.addAll(examineLine(i, j, -1, 0, locations, own_locations));
        ret.addAll(examineLine(i, j, 0, 1, locations, own_locations));
        ret.addAll(examineLine(i, j, 0, -1, locations, own_locations));

        return ret;
    }

    /*
     * Checks Bishop moves.
     * */
    protected List<Integer> checkBishopMoves(int i, int j, boolean[][] locations, boolean[][] own_locations){

        List<Integer> ret = new ArrayList<>();

        // Bishop Moves
        ret.addAll(examineLine(i, j, 1, 1, locations, own_locations));
        ret.addAll(examineLine(i, j, 1, -1, locations, own_locations));
        ret.addAll(examineLine(i, j, -1, 1, locations, own_locations));
        ret.addAll(examineLine(i, j, -1, -1, locations, own_locations));

        return ret;
    }




    //
    // Small Helper functions
    // -----------------------------

    /*
     * Gets 1D coordinates from 2D coordinates.
     * Warning: Only for a 8x8 Board!!!!!!
     * */
    protected int getListValue(int i, int j){

        return i*8+j;
    }

    /*
     * Checks if a piece is still on the board.
     * Warning: Only for a 8x8 Board!!!!!!
     * */
    protected boolean checkIfOnBoard(int i, int j){

        return i >= 0 && i <= 7 && j >= 0 && j <= 7;
    }

}
