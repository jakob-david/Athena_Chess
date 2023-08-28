package GUI;

import javax.swing.*;

public class Log extends JFrame{

    private String text = "";

    JLabel label;
    JPanel panel;
    public Log(){
        this.setSize(200, 400);

        label = new JLabel();
        panel = new JPanel();

        panel.add(label);
        this.add(panel);

        label.setText(this.text);
    }

    /*
     * Adds a string to the log.
     * */
    public void addToLog(String text, boolean is_white){
        this.text += "<br>" + (is_white?"white: ":"black: ") +  text + "</br>";
        label.setText("<html>" + this.text + "</html>");
    }
}
