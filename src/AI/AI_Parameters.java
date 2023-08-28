package AI;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

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
    // 0...Own move value weight.
    // 1...Opponent move value weight.
    // 2...Opponent piece value weight.
    // 3...Recursion weight.
    // -------------------------------
    int[] weights = new int[4];
    //List<String> labels = new ArrayList<String>();
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

        this.recursion_smoothing = 2;

        readFromFile();
    }

    public AI_Parameters(){
    }

    public void setIsWhite(boolean is_white){
        this.is_white = is_white;
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
                        this.weights[0] = Integer.parseInt(myReader.nextLine());
                    } else if (data.contains("opp_move_weight")) {
                        this.weights[1] = Integer.parseInt(myReader.nextLine());
                    } else if (data.contains("opp_piece_weight")) {
                        this.weights[2] = Integer.parseInt(myReader.nextLine());
                    } else if (data.contains("recursion_weight")) {
                        this.weights[3] = Integer.parseInt(myReader.nextLine());
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
        ret_string += weights[0] + "\n";
        ret_string += "opp_move_weight: \n";
        ret_string += weights[1] + "\n";
        ret_string += "opp_piece_weight: \n";
        ret_string += weights[2] + "\n";
        ret_string += "recursion_weight: \n";
        ret_string += weights[3] + "\n";
        ret_string += "-------------------------------\n\n";

        return ret_string;
    }

    public AI_Parameters Copy(){
        AI_Parameters ret = new AI_Parameters();

        ret.is_white = this.is_white;
        ret.moves_ahead = this.moves_ahead;

        ret.best_moves_to_keep = this.best_moves_to_keep;
        ret.best_move_max_distance = this.best_move_max_distance;
        ret.best_move_randomisation = this.best_move_randomisation;

        ret.weights[0] = this.weights[0];
        ret.weights[1] = this.weights[1];
        ret.weights[2] = this.weights[2];
        ret.weights[3] = this.weights[3];

        ret.recursion_smoothing = this.recursion_smoothing;

        return ret;
    }
}
