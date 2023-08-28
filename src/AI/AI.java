package AI;

import Game.Game;
import Game.Piece;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class AI {

    private Game current_board;

    private final AI_Parameters ai_parameters;


    //boolean is_white, int moves_ahead, boolean best_move_randomisation
    public AI(Piece[][] board, AI_Parameters ai_parameters){

        this.ai_parameters = ai_parameters;
        this.current_board = new Game(board, ai_parameters.is_white);
    }



    public Move getMove(boolean reduced){

        // Make a list to hold to best x moves. (At most x moves)
        List<Move> best_moves = new ArrayList<>();

        for(int i=0; i < this.current_board.getLengthX(); i++){
            for(int j=0; j < this.current_board.getLengthY(); j++){

                if  (   null == this.current_board.getGameBoardReference()[i][j] ||
                        (this.current_board.getGameBoardReference()[i][j].isWhite != this.current_board.isWhite())
                    ){
                    continue;
                }

                List<Integer> possible_moves = current_board.getPossibleMoves(i, j, true);


                for(int element : possible_moves){

                    int[] coordinates = current_board.get2DCoordinates(element);
                    int new_i = coordinates[0];
                    int new_j = coordinates[1];

                    // Declare values and initialise
                    int move_value_p1 = 0;
                    int move_value_p2 = 0;
                    int piece_value = getPieceValue(!this.current_board.isWhite());

                    // Activate move value if needed.
                    if(!reduced){
                        move_value_p1 = getMoveValue(this.current_board.isWhite());
                        move_value_p2 = getMoveValue(!this.current_board.isWhite());
                    }

                    Move tmp_move = new Move(i, j, new_i, new_j, this.current_board.getCopyOfPiece(i, j), this.current_board.getCopyOfPiece(new_i, new_j));

                    current_board.makeMove(i, j, new_i, new_j, false);

                    // Take the difference between the old values and the current values.
                    piece_value = (getPieceValue(!this.ai_parameters.is_white) - piece_value) * -1;

                    // Activate move value if needed.
                    if(!reduced){
                        move_value_p1 = getMoveValue(this.current_board.isWhite()) - move_value_p1;
                        move_value_p2 = getMoveValue(!this.current_board.isWhite()) - move_value_p2;
                    }

                    int recursion_value = recursionStep(ai_parameters.moves_ahead);

                    redoMove(tmp_move);

                    // Linear Combination
                    int total_value = getLinearCombination(move_value_p1, move_value_p2, piece_value, recursion_value);

                    if(best_moves.isEmpty() || total_value > best_moves.get(0).move_value - ai_parameters.best_move_max_distance){
                        best_moves.add(new Move(total_value, i, j, new_i, new_j));
                    }

                    Collections.sort(best_moves);


                    if(ai_parameters.best_moves_to_keep < best_moves.size()){
                        best_moves.remove(best_moves.size()-1);
                    }


                }
            }
        }


        int pick_move = 0;

        if(ai_parameters.best_move_randomisation){
            // Info: best_moves.size() is the max but it is not included in the randomisation process.
            pick_move = ThreadLocalRandom.current().nextInt(0, best_moves.size());
        }

        if(best_moves.isEmpty()){
            return new Move(true);
        }

        return best_moves.get(pick_move);
    }


    private int recursionStep(int depth){


        // End condition
        //------------------
        if (depth < 1){
            return 0;
        }
        //------------------


        // Declare and initialise piece.
        //------------------
        int self_piece_value = getPieceValue(current_board.isWhite());
        int opponent_piece_value = getPieceValue(!current_board.isWhite());
        //------------------

        // Opponent move
        //------------------
        Move opponent_move = makeAIMove(current_board.getGameBoardReference(), !current_board.isWhite());
        //------------------


        // "My" next move
        //------------------
        Move own_move = makeAIMove(current_board.getGameBoardReference(), current_board.isWhite());
        //------------------

        // Recursion.
        //------------------
        int recursion = recursionStep(depth-1);
        //------------------

        // Calculate return Value.
        //------------------
        self_piece_value = getPieceValue(current_board.isWhite()) - self_piece_value;
        opponent_piece_value = getPieceValue(!current_board.isWhite()) - opponent_piece_value;
        int return_value = (self_piece_value - opponent_piece_value) + recursion/ ai_parameters.recursion_smoothing;
        //------------------


        // Self move redone
        //------------------
        redoMove(own_move);
        //------------------


        // Opponent move redone
        //------------------
        redoMove(opponent_move);
        //------------------

        return return_value;
    }




    //
    // Large Helper Function
    // -----------------------------


    /*
     * Returns the amount of possible moves for the current player.
     * */
    private int getMoveValue(boolean is_white){

        int sum_of_possible_moves = 0;

        for(int i=0; i < this.current_board.getLengthX(); i++) {
            for (int j = 0; j < this.current_board.getLengthY(); j++) {

                if(this.current_board.getGameBoardReference()[i][j] != null && this.current_board.getGameBoardReference()[i][j].isWhite == is_white){
                    List<Integer> tmp_possible_moves = current_board.getPossibleMoves(i, j, true);
                    sum_of_possible_moves += tmp_possible_moves.size();
                }
            }
        }

        return sum_of_possible_moves;
    }

    /*
     * Returns the sum of the values of all the pieces of the player specified.
     * */
    public int getPieceValue(boolean is_white){

        int sum = 0;

        for(int i=0; i < current_board.getLengthX(); i++){
            for(int j=0; j < current_board.getLengthY(); j++){

                Piece tmp_piece = current_board.getReferenceOfPiece(i, j);

                if( null == tmp_piece || tmp_piece.isWhite != is_white){
                    continue;
                }

                sum += tmp_piece.value;
            }
        }

        return sum;
    }

    /*
     * Makes one AI move on the board and returns this move as a Move object.
     * */
    private Move makeAIMove(Piece[][] current_board_ref, boolean is_white){

        AI ai = new AI(current_board_ref, new AI_Parameters(is_white, 0, false));

        Move am = ai.getMove(true);

        am.setFromPiece(this.current_board.getCopyOfPiece(am.getFromI(), am.getFromJ()));
        am.setToPiece(this.current_board.getCopyOfPiece(am.getToI(), am.getToJ()));

        current_board.makeMove(am.getFromI(), am.getFromJ(), am.getToI(), am.getToJ(), false);

        return  am;
    }

    /*
     * Undoes a given move.
     * */
    private void redoMove(Move move){

        this.current_board.putPieceCopyOnBoard(move.getFromI(), move.getFromJ(), move.getFrom_pieceCopy());
        this.current_board.putPieceCopyOnBoard(move.getToI(), move.getToJ(), move.getTo_pieceCopy());
    }


    //
    // Setter functions
    // -----------------------------

    public void setGameBoard(Piece[][] board){
        this.current_board = new Game(board, ai_parameters.is_white);
    }



    //
    // Small Helper Function
    // -----------------------------

    /*
     * Calculates the linear combination to get the value of one move.
     * */
    private int getLinearCombination(int own_move_value, int opp_move_value, int opp_piece_value, int recursion_value){

        int ret = 0;

        ret += ai_parameters.weights[0] * own_move_value;
        ret += ai_parameters.weights[1] * opp_move_value;
        ret += ai_parameters.weights[2] * opp_piece_value;
        ret += ai_parameters.weights[3] * recursion_value;

        return ret;
    }

}
