package Game;

import java.util.List;

public class Game {

    private Piece GameBoard[][];

    private boolean is_it_whites_turn = true;





    //
    // Constructors
    // -----------------------------
    public Game(){

        GameBoard = new Piece[8][8];
        for(int i=0; i < GameBoard.length; i++){
            for(int j=0; j < GameBoard[0].length; j++){
                GameBoard[i][j] = null;
            }
        }
    }

    public  Game(Piece[][] board, boolean is_white){

        this.is_it_whites_turn = is_white;

        // Make a real copy.
        GameBoard = new Piece[board.length][board[0].length];
        for(int i=0; i < GameBoard.length; i++){
            for(int j=0; j < GameBoard[0].length; j++){
                GameBoard[i][j] = board[i][j];
            }
        }
    }




    //
    // Larger Public Functions
    // -----------------------------

    /*
     * Wrapper Function for the method "getPossibleMove" of each piece.
     * */
    public List<Integer> getPossibleMoves(int i, int j, boolean check_check){

        boolean[][] locations = new boolean[8][8];
        boolean[][] own_locations = new boolean[8][8];

        // all locations
        for(int m=0; m<GameBoard.length; m++){
            for(int n=0; n<GameBoard.length; n++){
                if(GameBoard[m][n] == null){
                    locations[m][n] = false;
                } else {
                    locations[m][n] = true;
                }
            }
        }

        // own locations
        for(int m=0; m<GameBoard.length; m++){
            for(int n=0; n<GameBoard.length; n++){
                if(GameBoard[m][n] == null || GameBoard[m][n].isWhite != GameBoard[i][j].isWhite){
                    own_locations[m][n] = false;
                } else {
                    own_locations[m][n] = true;
                }
            }
        }



        List<Integer> possible_moves = GameBoard[i][j].getPossibleMoves(i, j, locations, own_locations);

        if(check_check) {
            possible_moves.removeIf((n) -> (inCheck(i, j, n)));
        }

        return possible_moves;
    }

    /*
     * Checks if a Pawn has reached the end of the board and transforms it to a Queen.
     * */
    public int[] checkForPawnTransformation(boolean is_white){

        int[] ret = new int[3];
        ret[0] = -1;
        ret[1] = -1;
        ret[2] = -1;

        // check white pawns
        if(is_white) {
            for (int i = 0; i < 8; i++) {
                if (this.GameBoard[0][i] != null && this.GameBoard[0][i].isWhite && this.GameBoard[0][i].name == 'P') {
                    ret[0] = 0;
                    ret[1] = i;
                    ret[2] = 1;
                    return ret;
                }
            }

        }
        else // check black pawns
        {
            for (int i = 0; i < 8; i++) {
                if (this.GameBoard[7][i] != null && !this.GameBoard[7][i].isWhite && this.GameBoard[7][i].name == 'P') {
                    ret[0] = 7;
                    ret[1] = i;
                    ret[2] = 0;
                    return ret;
                }
            }
        }

        return ret;
    }




    //
    // Larger Private Functions
    // -----------------------------

    /*
     * Checks if the active players king is currently in check.
     * */
    private boolean inCheck(int old_i, int old_j, int new_id){

        int[] tmp = get2DCoordinates(new_id);
        int new_i = tmp[0];
        int new_j = tmp[1];

        int king_i = -1;
        int king_j = -1;


        Piece from_position = getCopyOfPiece(old_i, old_j);
        Piece to_position = getCopyOfPiece(new_i, new_j);

        movePieceOnBoard(old_i, old_j, new_i, new_j, false);

        // get King position.
        for(int m = 0; m < 8; m++){
            for(int n = 0; n < 8; n++){

                if(         GameBoard[m][n] != null
                        &&  GameBoard[m][n].name == 'K'
                        &&  GameBoard[m][n].isWhite == is_it_whites_turn
                ){
                    king_i = m;
                    king_j = n;
                }
            }
        }

        int king_id = -1;
        // get King id
        if(king_i < 0 || king_j < 0){
            System.err.println("Error: King not found");
        } else {
            king_id = get1DCoordinates(king_i, king_j);
        }



        for(int m=0; m < getLengthX(); m++){
            for(int n=0; n<getLengthY(); n++){

                if (GameBoard[m][n] == null){
                    continue;
                }

                if (GameBoard[m][n].isWhite == GameBoard[new_i][new_j].isWhite){
                    continue;
                }

                List<Integer> possible_moves = getPossibleMoves(m, n, false);

                for(int in : possible_moves){
                    if(in == king_id){

                        putPieceCopyOnBoard(old_i, old_j, from_position);
                        putPieceCopyOnBoard(new_i, new_j, to_position);
                        return true;
                    }
                }
            }
        }

        putPieceCopyOnBoard(old_i, old_j, from_position);
        putPieceCopyOnBoard(new_i, new_j, to_position);
        return false;
    }




    //
    // Setter Functions
    // -----------------------------

    /*
     * Puts one Piece on the board.
     * */
    public void putPieceOnBoard(int i, int j, char name, boolean isWhite){

        if('P' == name){
            GameBoard[i][j] = new Pawn(isWhite);
        }
        else if ('R' == name){
            GameBoard[i][j] = new Rook(isWhite);
        }
        else if ('N' == name){
            GameBoard[i][j] = new Knight(isWhite);
        }
        else if ('B' == name){
            GameBoard[i][j] = new Bishop(isWhite);
        }
        else if ('Q' == name){
            GameBoard[i][j] = new Queen(isWhite);
        }
        else if ('K' == name){
            GameBoard[i][j] = new King(isWhite);
        }
    }

    /*
     * Puts a copy of the given Piece on the board
     * */
    public void putPieceCopyOnBoard(int i, int j, Piece piece){

        if(null == piece){
            this.GameBoard[i][j] = null;
        } else {
            this.GameBoard[i][j] = piece.Copy();
        }
    }

    /*
     * Moves one piece from one location to another. The "old" position becomes "null".
     * */
    public void movePieceOnBoard(int old_i, int old_j, int new_i, int new_j, boolean human_move){

        Piece tmp_piece = GameBoard[old_i][old_j].Copy();
        GameBoard[old_i][old_j] = null;
        GameBoard[new_i][new_j] = tmp_piece;

        if(null != GameBoard[new_i][new_j] && GameBoard[new_i][new_j].name == 'P'){

            // Not the best I know....
            ((Pawn) GameBoard[new_i][new_j]).setFirstMove();
        }

        if(!human_move) {
            int[] tmp_pawn = checkForPawnTransformation(isWhite());
            if (tmp_pawn[0] != -1) {
                putPieceOnBoard(tmp_pawn[0], tmp_pawn[1], 'Q', isWhite());
            }
        }

        if (human_move) {
            printMatrix();
        }
    }

    /*
     * Switches the current player.
     * */
    public void switchCurrentPlayer(){
        this.is_it_whites_turn = !this.is_it_whites_turn;
    }




    //
    // Getter Functions
    // -----------------------------

    /*
     * Getter Function for GameBoard. NO COPY only REFERENCE.
     * */
    public Piece[][] getGameBoardReference(){
        return GameBoard;
    }

    /*
     * Returns a real COPY of the game board.
     * */
    public Piece getCopyOfPiece(int i, int j){

        if(null == GameBoard[i][j]){
            return null;
        } else {
            return this.GameBoard[i][j].Copy();
        }
    }

    /*
     * Returns a REFERENCE of the game board.
     * */
    public Piece getReferenceOfPiece(int i, int j){

        if(null == GameBoard[i][j]){
            return null;
        } else {
            return this.GameBoard[i][j];
        }
    }

    /*
     * get length of first dimension fo the board (up-down)
     * */
    public int getLengthX(){
        return GameBoard.length;
    }

    /*
     * get length of second dimension fo the board (left-right)
     * */
    public int getLengthY(){
        return GameBoard[0].length;
    }

    /*
     * Converts the 1D coordinates (for the Array) to 2D coordinates (2D Game Board).
     * */
    public int[] get2DCoordinates(int ID){

        int[] ret = new int[2];

        ret[0] = ID/getLengthY();
        ret[1] = ID%getLengthY();

        return ret;
    }

    /*
     * Getter is_it_whites_turn
     * */
    public boolean isWhite(){
        return this.is_it_whites_turn;
    }

    /*
     * Checks whether the piece is the same colour as the player currently at turn.
     * */
    public boolean checkIfRightPlayer(int i, int j){

        if(GameBoard[i][j].isWhite == this.is_it_whites_turn) {
            return true;
        } else {
            return false;
        }
    }



    //
    // Small Helper Function
    // -----------------------------

    /*
     * Converts the 2D coordinates (2D Game Board) to 1D coordinates (for the Array).
     * */
    private int get1DCoordinates(int i, int j){
        return i*getLengthY()+j;
    }

    /*
     * Prints the Game Board to the console.
     * */
    private void printMatrix(){

        System.out.println("--------------");
        for (int i = 0; i < GameBoard.length; i++) {
            for (int j = 0; j < GameBoard[i].length; j++) {

                if(null == GameBoard[i][j]){
                    System.out.print(". ");
                } else {
                    System.out.print(GameBoard[i][j].name + " ");
                }
            }
            System.out.println();
        }
        System.out.println("--------------");
    }

}

