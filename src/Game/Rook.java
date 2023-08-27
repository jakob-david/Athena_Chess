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

        return new ArrayList<>(checkRookMoves(i, j, locations, own_locations));
    }


    @Override
    public Piece Copy() {
        return new Rook(this.isWhite);
    }
}
