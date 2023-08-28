import AI.Trainer;
import GUI.Board;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {

        // Activate trainer if wanted. Just set to true.
        Trainer trainer = new Trainer(5, 15);
        trainer.train(false);


        // Set the Nimbus Look and Feel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create and show the GUI
        SwingUtilities.invokeLater(() -> {
            Board obj = new Board();
            obj.setVisible(true);
        });
    }
}

