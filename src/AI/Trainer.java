package AI;

public class Trainer {



    public void test(){
        System.out.println("Hi");
        AI_Parameters parameters = new AI_Parameters(true, 0, false);
        //parameters.writeToFile();

        System.out.println(parameters.toString());
        parameters.readFromFile();
        System.out.println(parameters.toString());
    }
}
