package Game;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {

    public Rook(boolean isWhite){

        super('R', isWhite);
        this.value = 5;
    }


    /*
     * Gets possible moves for a rook.
     * */
    @Override
    public List<Integer> getPossibleMoves(int i, int j, boolean[][] locations, boolean[][] own_locations) {

        return new ArrayList<>(checkRookMoves(i, j, locations, own_locations));
    }


    /*
     * Makes a copy of rook.
     * */
    @Override
    public Piece Copy() {
        return new Rook(this.isWhite);
    }
}
