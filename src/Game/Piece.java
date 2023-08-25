package Game;

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

}
