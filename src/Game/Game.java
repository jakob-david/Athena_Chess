package Game;

import java.util.List;

public class Game {

    final private Piece[][] GameBoard;

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
            System.arraycopy(board[i], 0, GameBoard[i], 0, GameBoard[0].length);
        }
    }

    public Game(boolean is_white){

        this.is_it_whites_turn = is_white;

        GameBoard = new Piece[8][8];
        for(int i=0; i < GameBoard.length; i++){
            for(int j=0; j < GameBoard[0].length; j++){
                GameBoard[i][j] = null;
            }
        }

        // Pawns
        for(int j=0; j<8; j++){
            putPieceOnBoard(6, j, 'P', true);
        }
        for(int j=0; j<8; j++){
            putPieceOnBoard(1, j, 'P', false);
        }

        // Rooks
        putPieceOnBoard(0, 0, 'R', false);
        putPieceOnBoard(0, 7, 'R', false);
        putPieceOnBoard(7, 0, 'R', true);
        putPieceOnBoard(7, 7, 'R', true);

        // Knights
        putPieceOnBoard(0, 1, 'N', false);
        putPieceOnBoard(0, 6, 'N', false);
        putPieceOnBoard(7, 1, 'N', true);
        putPieceOnBoard(7, 6, 'N', true);

        // Bishops
        putPieceOnBoard(0, 2, 'B', false);
        putPieceOnBoard(0, 5, 'B', false);
        putPieceOnBoard(7, 2, 'B', true);
        putPieceOnBoard(7, 5, 'B', true);

        // Queens
        putPieceOnBoard(0, 3, 'Q', false);
        putPieceOnBoard(7, 3, 'Q', true);

        // Kings
        putPieceOnBoard(0, 4, 'K', false);
        putPieceOnBoard(7, 4, 'K', true);

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
                locations[m][n] = GameBoard[m][n] != null;
            }
        }

        // own locations
        for(int m=0; m<GameBoard.length; m++){
            for(int n=0; n<GameBoard.length; n++){
                own_locations[m][n] = GameBoard[m][n] != null && GameBoard[m][n].isWhite == GameBoard[i][j].isWhite;
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
    // Functions to check whether in check.
    // -----------------------------

    /*
     * Checks if the active players king is currently in check.
     * */
    private boolean inCheck(int old_i, int old_j, int new_id){

        int[] tmp = get2DCoordinates(new_id);
        int new_i = tmp[0];
        int new_j = tmp[1];


        Piece from_position = getCopyOfPiece(old_i, old_j);
        Piece to_position = getCopyOfPiece(new_i, new_j);

        makeMove(old_i, old_j, new_i, new_j, false);

        int king_id = getKingID(from_position.isWhite);

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

    /*
     * Gets the ID of the king.
     * */
    private int getKingID(boolean is_white){

        for(int m = 0; m < 8; m++){
            for(int n = 0; n < 8; n++){

                if(         GameBoard[m][n] != null
                        &&  GameBoard[m][n].name == 'K'
                        &&  GameBoard[m][n].isWhite == is_white
                ){
                    return get1DCoordinates(m, n);
                }
            }
        }

        System.err.println("Error: King not found");
        return -1;
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
    public void makeMove(int old_i, int old_j, int new_i, int new_j, boolean human_move){

        Piece tmp_piece;
        if(null != GameBoard[old_i][old_j]){
            tmp_piece = GameBoard[old_i][old_j].Copy();
        } else {
            tmp_piece = null;
        }
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
     * Makes one move using a Move object.
     * */
    public void makeMove(AI.Move move, boolean human_move){

        int old_i = move.getFromI();
        int old_j = move.getFromJ();
        int new_i = move.getToI();
        int new_j = move.getToJ();

        Piece tmp_piece;
        if(null != GameBoard[old_i][old_j]){
            tmp_piece = GameBoard[old_i][old_j].Copy();
        } else {
            tmp_piece = null;
        }
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
     * Converts the 2D coordinates (2D Game Board) to 1D coordinates (for the Array).
     * */
    public int get1DCoordinates(int i, int j){
        return i*getLengthY()+j;
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

        return GameBoard[i][j].isWhite == this.is_it_whites_turn;
    }



    //
    // Small Helper Function
    // -----------------------------

    /*
     * Prints the Game Board to the console.
     * */
    public void printMatrix(){

        System.out.println("--------------");
        for (Piece[] pieces : GameBoard) {
            for (Piece piece : pieces) {

                if (null == piece) {
                    System.out.print(". ");
                } else {
                    System.out.print(/*(piece.isWhite?"w":"b")*/ piece.name + " ");
                }
            }
            System.out.println();
        }
        System.out.println("--------------");
    }

}

