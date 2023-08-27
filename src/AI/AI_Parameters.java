package AI;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.util.Scanner;

public class AI_Parameters {

    File file = new File("./src/AI/AI_Data.txt");

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


    /*
     * Write training data to file.
     * */
    public void writeToFile(){
        String str = toString();

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.file.getPath()));
            writer.write(str);

            writer.close();
        }

        catch (Exception e) {
            System.err.println("Error: File writing failed");
            e.getStackTrace();
        }
    }

    /*
     * Read training data from file.
     * */
    public void readFromFile() {
        String data = "";

        try {
            Scanner myReader = new Scanner(file);
                while (myReader.hasNextLine()) {
                    data = myReader.nextLine();

                    if(data.contains("own_move_weight")){
                        this.own_move_weight = Integer.parseInt(myReader.nextLine());
                    } else if (data.contains("opp_move_weight")) {
                        this.opp_move_weight = Integer.parseInt(myReader.nextLine());
                    } else if (data.contains("opp_piece_weight")) {
                        this.opp_piece_weight = Integer.parseInt(myReader.nextLine());
                    } else if (data.contains("recursion_weight")) {
                        this.recursion_weight = Integer.parseInt(myReader.nextLine());
                    }
                }
                myReader.close();
        } catch(Exception e) {
            System.err.println("Error: File reading failed");
            e.getStackTrace();
        }
    }


    /*
     * Creates a string of all parameters.
     * */
    @Override
    public String toString() {

        String ret_string = "";

        ret_string += "Linear combination parameters\n-------------------------------\n";
        ret_string += "own_move_weight: \n";
        ret_string += own_move_weight + "\n";
        ret_string += "opp_move_weight: \n";
        ret_string +=  opp_move_weight + "\n";
        ret_string += "opp_piece_weight: \n";
        ret_string += opp_piece_weight + "\n";
        ret_string += "recursion_weight: \n";
        ret_string += recursion_weight + "\n";
        ret_string += "-------------------------------\n\n";

        return ret_string;
    }
}
