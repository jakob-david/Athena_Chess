package Game;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {

    public Queen(boolean isWhite){

        super('Q', isWhite);
        this.value = 9;
    }


    /*
     * Gets possible moves for a queen.
     * */
    @Override
    public List<Integer> getPossibleMoves(int i, int j, boolean[][] locations, boolean[][] own_locations) {

        List<Integer> ret = new ArrayList<>();

        ret.addAll(checkRookMoves(i, j, locations, own_locations));
        ret.addAll(checkBishopMoves(i, j, locations, own_locations));

        return ret;
    }


    /*
     * Makes copy of queen.
     * */
    @Override
    public Piece Copy() {
        return new Queen(this.isWhite);
    }
}
