package AI;

public class AI_Parameters {

    boolean is_white;
    int moves_ahead;
    int best_moves_to_keep;
    int best_move_max_distance;
    boolean best_move_randomisation;

    public AI_Parameters(boolean is_white, int moves_ahead, boolean best_move_randomisation){
        this.moves_ahead = moves_ahead;
        this.best_move_randomisation = best_move_randomisation;
        this.is_white = is_white;

        this.best_moves_to_keep = 3;
        this.best_move_max_distance = 2;
    }
}
