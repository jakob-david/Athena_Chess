package AI;

import Game.Piece;

class Move implements Comparable<Move> {

    int move_value;
    int[] move = new int[4];

    Piece from_piece;
    Piece to_piece;


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

    public Move (int[] coordinates, Piece from_piece, Piece to_piece){

        this.to_piece = to_piece;
        this.from_piece = from_piece;

        move[0] = coordinates[0];
        move[1] = coordinates[1];
        move[2] = coordinates[2];
        move[3] = coordinates[3];
    }

    public Move (int old_i, int old_j, int new_i, int new_j, Piece from_piece, Piece to_piece){

        this.to_piece = to_piece;
        this.from_piece = from_piece;

        move[0] = old_i;
        move[1] = old_j;
        move[2] = new_i;
        move[3] = new_j;
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

    public Piece getFrom_pieceCopy(){

        if(this.from_piece != null){
            return this.from_piece.Copy();
        } else {
            return null;
        }

    }

    public Piece getTo_pieceCopy(){

        if(this.to_piece != null){
            return this.to_piece.Copy();
        } else {
            return null;
        }

    }



    //
    // Compare
    // -----------------------------

    @Override
    public int compareTo(Move m) {
        return m.move_value - this.move_value;
    }


}