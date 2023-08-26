package AI;

import java.util.Comparator;

class Move implements Comparator<Move> {

    int move_value;
    int[] move = new int[4];

    public Move (int move_value, int from_i, int from_j, int to_i, int to_j){

        this.move_value = move_value;

        move[0] = from_i;
        move[1] = from_j;
        move[2] = to_i;
        move[3] = to_j;
    }


    @Override
    public int compare(Move m1, Move m2) {
        return m2.move_value - m1.move_value;
    }
}