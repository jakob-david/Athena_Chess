package AI;
import Game.Game;

public class Trainer {



    public void train(){

        AI_Parameters best_parameters = new AI_Parameters(true, 0, false);
        best_parameters.initLinearCombinationLearning();

        AI_Parameters parameters = new AI_Parameters(true, 0, false);
        parameters.initLinearCombinationLearning();


        playGame();

    }

    private void playGame(){

        Game game = new Game(true);

        Move move;

        AI_Parameters white_parameters = new AI_Parameters(true, 0, false);
        AI_Parameters black_parameters = new AI_Parameters(false, 0, false);

        AI white = new AI(game.getGameBoardReference(), white_parameters);
        AI black = new AI(game.getGameBoardReference(), black_parameters);

        for(int i = 0; i < 50; i++){

            white.setGameBoard(game.getGameBoardReference());
            move = white.getMove(false);

            if(move.no_move_possible){
                System.out.println("white won");
                break;
            } else {
                game.makeMove(move, false);
            }

            game.printMatrix();

            black.setGameBoard(game.getGameBoardReference());
            move = black.getMove(false);

            if(move.no_move_possible){
                System.out.println("black won");
                break;
            } else {
                game.makeMove(move, false);
            }

            game.printMatrix();
        }

        white.setGameBoard(game.getGameBoardReference());
        int white_value = white.getPieceValue(true);
        int black_value = white.getPieceValue(false);


        System.out.println("white: " + white_value);
        System.out.println("black: " + black_value);
        if(white_value > black_value){
            System.out.println("white won");
        } else if(black_value > white_value){
            System.out.println("black won");
        } else {
            System.out.println("draw");
        }


    }
}
