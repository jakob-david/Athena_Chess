package GUI;

import AI.*;
import Game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class Brett extends JFrame implements ActionListener{

    private Game game = new Game();

    JButton[] grid;
    JPanel panel;

    /*
    The current state for one move and its helper variables.
     */
    private int state = 0;
    private String state_string;
    private int state_id;
    private List<Integer> state_possibleMoves;

    private Log log = new Log();


    // AI stuff
    private boolean AI_activated = true;
    private boolean AI_is_white = false;
    private int AI_moves_ahead = 1;


    public Brett(){

        initBoard();
        initPieces();
        initLog();
    }

    /*
     * Initialises the board.
     * */
    private void initBoard(){

        this.setSize(600, 600);
        this.panel = new JPanel(new GridLayout(8,8));
        this.grid = new JButton[64];

        for (int i=0; i<grid.length; i++)
        {
            this.grid[i] = new JButton();
            this.grid[i].addActionListener(this);

            //grid[i].setLayout();
            grid[i].setOpaque(true);

            colourField(i);

            //this.grid[i].setBackground(Color.WHITE);
            //this.grid[i].setOpaque(true);

            this.panel.add(grid[i]);
        }
        this.add(panel);
    }

    /*
    * Initialises the pieces.
    * */
    private void initPieces(){

        // Pawns
        for(int j=0; j<8; j++){
            putPieceOnBoard(6, j, 'P', true, "WP");
        }
        for(int j=0; j<8; j++){
            putPieceOnBoard(1, j, 'P', false, "BP");
        }

        // Rooks
        putPieceOnBoard(0, 0, 'R', false, "BR");
        putPieceOnBoard(0, 7, 'R', false, "BR");
        putPieceOnBoard(7, 0, 'R', true, "WR");
        putPieceOnBoard(7, 7, 'R', true, "WR");

        // Knights
        putPieceOnBoard(0, 1, 'N', false, "BN");
        putPieceOnBoard(0, 6, 'N', false, "BN");
        putPieceOnBoard(7, 1, 'N', true, "WN");
        putPieceOnBoard(7, 6, 'N', true, "WN");

        // Bishops
        putPieceOnBoard(0, 2, 'B', false, "BB");
        putPieceOnBoard(0, 5, 'B', false, "BB");
        putPieceOnBoard(7, 2, 'B', true, "WB");
        putPieceOnBoard(7, 5, 'B', true, "WB");

        // Queens
        putPieceOnBoard(0, 3, 'Q', false, "BQ");
        putPieceOnBoard(7, 3, 'Q', true, "WQ");

        // Kings
        putPieceOnBoard(0, 4, 'K', false, "BK");
        putPieceOnBoard(7, 4, 'K', true, "WK");

    }

    /*
     * Initialises the Log.
     * */
    private void initLog(){
        log.setVisible(true);
    }

    /*
    * Handle Actions
    * */
    public void actionPerformed (ActionEvent ae){

        if(this.state == 0){

            int i = 0;
            for(JButton but: grid){
                if(ae.getSource() == but){

                    this.state_string = but.getText();
                    this.state_id = i;
                    break;
                }
                i++;
            }

            // check possible move is possible
            int[] tmp = getGridID(i);
            int id_i = tmp[0];
            int id_j = tmp[1];

            if(this.state_string.isEmpty() || !game.checkIfRightPlayer(id_i, id_j)){
                return;
            }

            this.state_possibleMoves = game.getPossibleMoves(id_i, id_j, true);

            this.state_possibleMoves.forEach((c) -> grid[c].setBackground(Color.PINK));

            this.state = 1;

        } else if (this.state == 1) {


            this.state_possibleMoves.forEach((c) -> colourField(c));

            int i = 0;
            for(JButton but: grid){


                if(ae.getSource() == but){

                    //here

                    boolean correct_move = false;
                    if(state_possibleMoves.contains(i)){
                        correct_move = true;
                    }

                    if(this.state_id == i || !correct_move){
                        this.state = 0;
                        return;
                    }
                    else {
                        but.setText(this.state_string);
                        this.grid[state_id].setText("");

                        int[] o = getGridID(state_id);
                        int[] n = getGridID(i);
                        game.movePieceOnBoard(o[0], o[1], n[0], n[1], true);


                        /*Log*/
                        String tmp_string = "(" + (char)('A'+o[1]) + "," + (8-o[0]) + ") -> (" + (char)('A'+n[1]) + "," + (8-n[0]) + ")";
                        log.addToLog(tmp_string);
                        /*Log end*/
                    }

                    break;
                }
                i++;
            }

            // AI stuff
            if(AI_activated){
                AI ai_player = new AI(game.getGameBoardReference(), AI_is_white, AI_moves_ahead);
                int[] move = ai_player.getMove(false);

                game.movePieceOnBoard(move[0], move[1], move[2], move[3], true);

                int old_id = getGridID(move[0], move[1]);
                int new_id = getGridID(move[2], move[3]);
                this.grid[new_id].setText(this.grid[old_id].getText());
                this.grid[old_id].setText("");

            } else {
                game.switchCurrentPlayer();
            }
            this.state = 0;

            /*
             * Pawn Transition
             * */
            int[] tmp_pawn = game.checkForPawnTransformation(game.isWhite());
            if(tmp_pawn[0] != -1){
                putPieceOnBoard(tmp_pawn[0], tmp_pawn[1], 'Q', game.isWhite(), game.isWhite()?"WQ":"BQ");
            }
            /*
             * Pawn Transition end
             * */

        }
    }


    //
    // Helper Function
    // -----------------------------

    /*
     * Get the i and j positions on the grid and returns the position in the array. (button array)
     * */
    private int getGridID(int i, int j){
        return i*8+j;
    }

    /*
     * Gets the id of an element of the buttons array and returns its i and j coordinates.
     * */
    private int[] getGridID(int ID){

        int[] ret = new int[2];

        ret[0] = ID/8;
        ret[1] = ID%8;

        return ret;
    }

    /*
     * Puts one Piece on the board
     * */
    private void putPieceOnBoard(int i, int j, char name, boolean isWhite, String value){

        int id = getGridID(i,j);
        this.grid[id].setText(value);
        game.putPieceOnBoard(i, j, name, isWhite);
    }

    /*
     * Checks if the current field is a white field (instead of a black field)
     * */
    private boolean isWhiteField(int ID){


        int subID = ID%16;

        if((subID < 8) && (subID%2 == 0)){
            return true;
        }

        if(subID > 8 && subID%2 == 1){
            return true;
        }

        return false;
    }

    /*
     * The code to colour the fields on the graphic UI.
     * */
    private void colourField(int c){

        if(isWhiteField(c)){
            grid[c].setBackground(Color.WHITE);
        } else {
            grid[c].setBackground(Color.GRAY);
        }
    }

}
