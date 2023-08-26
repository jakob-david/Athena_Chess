package Game;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {

    public Knight(boolean isWhite){

        super('N', isWhite);
        this.value = 3;
    }

    @Override
    public List<Integer> getPossibleMoves(int i, int j, boolean[][] locations, boolean[][] own_locations) {

        List<Integer> ret = new ArrayList<>();

        ret.addAll(checkOneTile(i, j, -2, -1, own_locations));
        ret.addAll(checkOneTile(i, j, -2, 1, own_locations));
        ret.addAll(checkOneTile(i, j, -1, -2, own_locations));
        ret.addAll(checkOneTile(i, j, -1, 2, own_locations));
        ret.addAll(checkOneTile(i, j, 1, -2, own_locations));
        ret.addAll(checkOneTile(i, j, 1, 2, own_locations));
        ret.addAll(checkOneTile(i, j, 2, -1, own_locations));
        ret.addAll(checkOneTile(i, j, 2, 1, own_locations));

        return ret;
    }

    @Override
    public Piece Copy() {
        return new Knight(this.isWhite);
    }
}
