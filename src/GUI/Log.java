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

    public void addToLog(String text){
        this.text += "<br>" +  text + "</br>";
        label.setText("<html>" + this.text + "</html>");
    }
}
