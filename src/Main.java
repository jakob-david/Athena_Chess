import AI.Trainer;
import GUI.Brett;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {


        Trainer trainer = new Trainer();
        trainer.test();



        //Brett obj = new Brett();q
        //obj.setVisible(true);

        // Set the Nimbus Look and Feel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            //e.printStackTrace();
        }

        // Create and show the GUI
        SwingUtilities.invokeLater(() -> {
            Brett obj = new Brett();
            obj.setVisible(true);

            //Log log = new Log();
            //log.setVisible(true);
        });


        // Maybe need later
        /*
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Brett().setVisible(true);
            }
        });
        */

    }

}

