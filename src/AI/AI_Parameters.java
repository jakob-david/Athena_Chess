package AI;

public class AI_Parameters {

    // Basic parameters
    // -------------------------------
    boolean is_white;
    int moves_ahead;
    // -------------------------------

    // Move randomisation
    // -------------------------------
    int best_moves_to_keep;
    int best_move_max_distance;
    boolean best_move_randomisation;
    // -------------------------------

    // Linear combination parameters
    // -------------------------------
    int own_move_weight;    // Own move value weight.
    int opp_move_weight;    // Opponent move value weight.
    int opp_piece_weight;   // Opponent piece value weight.
    int recursion_weight;   // Recursion weight.
    // -------------------------------

    // Recursion parameters
    // -------------------------------
    int recursion_smoothing;    // Smooths the recursion. recursion gets divided by this number.
    // -------------------------------


    public AI_Parameters(boolean is_white, int moves_ahead, boolean best_move_randomisation){
        this.moves_ahead = moves_ahead;
        this.best_move_randomisation = best_move_randomisation;
        this.is_white = is_white;

        this.best_moves_to_keep = 3;
        this.best_move_max_distance = 2;

        initLinearCombinationDefault();
    }

    private void initLinearCombinationDefault(){
        own_move_weight = 1;
        opp_move_weight = 1;
        opp_piece_weight = 100;
        recursion_weight = 100;

        recursion_smoothing = 2;
    }

    private void initLinearCombinationLearning(){
        own_move_weight = 1;
        opp_move_weight = 1;
        opp_piece_weight = 1;
        recursion_weight = 1;

        recursion_smoothing = 2;
    }
}
