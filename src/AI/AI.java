package AI;

import Game.Game;
import Game.Piece;

import java.util.List;
import java.util.ArrayList;

public class AI {

    final private Game current_board;

    final private int moves_ahead;

    int best_moves_to_keep = 3;
    int best_move_max_distance = 2;
    boolean best_move_randomisation;


    public AI(Piece[][] board, boolean is_white, int moves_ahead, boolean best_move_randomisation){

        this.current_board = new Game(board, is_white);
        this.moves_ahead = moves_ahead;
        this.best_move_randomisation = best_move_randomisation;
    }



    public int[] getMove(boolean reduced){


        // Make a list to hold to best 3 moves. (At most 3 moves)
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

                    Piece from_position = this.current_board.getCopyOfPiece(i, j);
                    Piece to_position = this.current_board.getCopyOfPiece(new_i, new_j);

                    current_board.movePieceOnBoard(i, j, new_i, new_j, false);

                    // Take the difference between the old values and the current values.
                    piece_value = (getPieceValue(!this.current_board.isWhite()) - piece_value) * -1;

                    // Activate move value if needed.
                    if(!reduced){
                        move_value_p1 = getMoveValue(this.current_board.isWhite()) - move_value_p1;
                        move_value_p2 = getMoveValue(!this.current_board.isWhite()) - move_value_p2;
                    }

                    int recursion_value = recursionStep(moves_ahead);

                    this.current_board.putPieceCopyOnBoard(i, j, from_position);
                    this.current_board.putPieceCopyOnBoard(new_i, new_j, to_position);

                    int total_value = move_value_p1 - move_value_p2 + 100 * piece_value + recursion_value;

                    if(best_moves.isEmpty() || total_value > best_moves.get(0).move_value - best_move_max_distance){
                        best_moves.add(new Move(total_value, i, j, new_i, new_j));
                    }

                    // TODO: Need to find better solution
                    best_moves.sort(best_moves.get(0));

                    if(best_moves_to_keep < best_moves.size()){
                        best_moves.remove(best_moves.size()-1);
                    }

                }
            }
        }

        // TODO: Pick at random
        return best_moves.get(0).move;
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
        AI opponent = new AI(current_board.getGameBoardReference(), !current_board.isWhite(), 0, false);
        int[] opponent_move = opponent.getMove(true);


        Piece opp_from_position = this.current_board.getCopyOfPiece(opponent_move[0], opponent_move[1]);
        Piece opp_to_position = this.current_board.getCopyOfPiece(opponent_move[2], opponent_move[3]);
        current_board.movePieceOnBoard(opponent_move[0], opponent_move[1], opponent_move[2], opponent_move[3], false);
        //------------------


        // "My" next move
        //------------------
        AI self = new AI(current_board.getGameBoardReference(), current_board.isWhite(), 0, false);
        int[] self_move = self.getMove(true);

        Piece self_from_position = this.current_board.getCopyOfPiece(self_move[0], self_move[1]);
        Piece self_to_position = this.current_board.getCopyOfPiece(self_move[2], self_move[3]);
        current_board.movePieceOnBoard(self_move[0], self_move[1], self_move[2], self_move[3], false);
        //------------------

        // Recursion.
        //------------------
        int recursion = recursionStep(depth-1);
        //------------------

        // Calculate return Value.
        //------------------
        self_piece_value = getPieceValue(current_board.isWhite()) - self_piece_value;
        opponent_piece_value = getPieceValue(!current_board.isWhite()) - opponent_piece_value;
        int return_value = 100*(self_piece_value - opponent_piece_value) + recursion/2;
        //------------------


        // Self move redone
        //------------------
        this.current_board.putPieceCopyOnBoard(self_move[0], self_move[1], self_from_position);
        this.current_board.putPieceCopyOnBoard(self_move[2], self_move[3], self_to_position);
        //------------------


        // Opponent move redone
        //------------------
        this.current_board.putPieceCopyOnBoard(opponent_move[0], opponent_move[1], opp_from_position);
        this.current_board.putPieceCopyOnBoard(opponent_move[2], opponent_move[3], opp_to_position);
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
    private int getPieceValue(boolean is_white){

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


    //
    // Small Helper Function
    // -----------------------------



}
