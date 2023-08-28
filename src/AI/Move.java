package AI;

import Game.Piece;

public class Move implements Comparable<Move> {

    int move_value;
    int[] move = new int[4];

    Piece from_piece;
    Piece to_piece;

    boolean no_move_possible = false;

    //
    // Constructors
    // -----------------------------

    public Move (int move_value, int from_i, int from_j, int to_i, int to_j){

        this.move_value = move_value;

        move[0] = from_i;
        move[1] = from_j;
        move[2] = to_i;
        move[3] = to_j;
    }

    public Move (int old_i, int old_j, int new_i, int new_j, Piece from_piece, Piece to_piece){

        this.to_piece = to_piece;
        this.from_piece = from_piece;

        move[0] = old_i;
        move[1] = old_j;
        move[2] = new_i;
        move[3] = new_j;
    }

    /*
     * Constructor. Makes a dummy Move object if no move is possible.
     * */
    public Move(boolean no_move_possible){
        this.no_move_possible = no_move_possible;
    }



    //
    // Getter
    // -----------------------------

    public int getFromI(){
        return move[0];
    }

    public int getFromJ(){
        return move[1];
    }

    public int getToI(){
        return move[2];
    }

    public int getToJ(){
        return move[3];
    }


    /*
     * Gets copy of from_piece.
     * */
    public Piece getFrom_pieceCopy(){

        if(this.from_piece != null){
            return this.from_piece.Copy();
        } else {
            return null;
        }

    }

    /*
     * Gets a copy of to_piece.
     * */
    public Piece getTo_pieceCopy(){

        if(this.to_piece != null){
            return this.to_piece.Copy();
        } else {
            return null;
        }

    }


    //
    // Setter
    // -----------------------------

    /*
     * Setter: from_piece.
     * */
    public void setFromPiece(Piece from_piece){

        if(null == from_piece){
            this.from_piece = null;
        } else {
            this.from_piece = from_piece.Copy();
        }
    }

    /*
     * Setter: to_piece.
     * */
    public void setToPiece(Piece to_piece){

        if(null == to_piece){
            this.to_piece = null;
        } else {
            this.to_piece = to_piece.Copy();
        }
    }



    //
    // Compare
    // -----------------------------

    /*
     * Compares two move objects.
     * */
    @Override
    public int compareTo(Move m) {
        return m.move_value - this.move_value;
    }


}