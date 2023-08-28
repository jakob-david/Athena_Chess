package AI;
import Game.Game;

public class Trainer {

    int games;
    int rounds;

    public Trainer(int games, int rounds){
        this.games = games;
        this.rounds = rounds;
    }

    /*
     * Trains the four parameters for the linear combination.
     * */
    public void train(boolean train){

        if (!train){
            return;
        }

        AI_Parameters best_parameters = new AI_Parameters(true, 0, false);
        best_parameters.setParametersToLearning();

        int training_rounds = 2;
        for(int i=0; i<training_rounds; i++){
            System.out.println("Round " + (i+1) + " of " + training_rounds);
            System.out.println("-----------------------");
            best_parameters = updateParameters(best_parameters, this.games, this.rounds);
            System.out.println("-----------------------");

        }

        best_parameters.writeToFile();
    }

    /*
     * Updates the four parameters for the linear combination.
     * */
    private AI_Parameters updateParameters(AI_Parameters parameters, int games, int rounds){

        AI_Parameters new_best_parameters = parameters.Copy();


        for(int i=0; i<4; i++){

            AI_Parameters best_parameters = parameters.Copy();
            AI_Parameters new_parameters = parameters.Copy();

            best_parameters.weights[i] = 1;
            new_parameters.weights[i] = 2;

            while(true){
                int score = makeSimulation(best_parameters, new_parameters, games, rounds);

                if(score < -1){
                    best_parameters.weights[i] = new_parameters.weights[i];
                    new_parameters.weights[i]++;
                    System.out.println("parameter " + (i+1) + "....updated");
                } else {
                    System.out.println("BEST VALUE FOUND: parameter " + (i+1));
                    new_best_parameters.weights[i] = best_parameters.weights[i];
                    break;
                }
            }
        }

        return new_best_parameters;
    }

    /*
     * Makes a simulation for x games and y rounds.
     * */
    private int makeSimulation(AI_Parameters best_parameters, AI_Parameters new_parameters, int games, int rounds){

        best_parameters.setIsWhite(true);
        new_parameters.setIsWhite(false);
        int score = 0;

        for(int i = 0; i < games; i++){
            score += playGame(best_parameters, new_parameters, rounds);
        }

        best_parameters.setIsWhite(false);
        new_parameters.setIsWhite(true);

        for(int i = 0; i < games; i++){
            // -= since now best_parameters is second.
            score -= playGame(new_parameters, best_parameters, rounds);
        }

        return score;
    }

    /*
     * Plays one game for x rounds.
     * Return: 1...first player wins, 2...second player wins, 3...draw.
     * */
    private int playGame(AI_Parameters white_parameters, AI_Parameters black_parameters, int rounds){

        Game game = new Game(true);

        Move move;

        AI white = new AI(game.getGameBoardReference(), white_parameters);
        AI black = new AI(game.getGameBoardReference(), black_parameters);

        for(int i = 0; i < rounds; i++){

            white.setGameBoard(game.getGameBoardReference());
            move = white.getMove(false);

            if(move.no_move_possible){
                return -1;  // black won
            } else {
                game.makeMove(move, false);
            }


            black.setGameBoard(game.getGameBoardReference());
            move = black.getMove(false);

            if(move.no_move_possible){
                return 1;  // white won
            } else {
                game.makeMove(move, false);
            }
        }

        white.setGameBoard(game.getGameBoardReference());
        int white_value = white.getPieceValue(true);
        int black_value = white.getPieceValue(false);


        if(white_value > black_value){
            return 1;  // white won
        } else if(black_value > white_value){
            return -1;  // black won
        } else {
            return 0;  // draw
        }
    }
}
