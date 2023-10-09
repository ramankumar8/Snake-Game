package snakegame;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class NameField {

    public String nameOfPlayer;

    NameField() {

        JFrame frame3 = new JFrame("Enter Name");
        JLabel lbl = new JLabel("Enter Your Name", JLabel.CENTER);
        lbl.setFont(new Font("Verdana", Font.PLAIN, 20));
        JTextField field3 = new JTextField();
        JButton b4 = new JButton("Okay");
        frame3.add(lbl);
        frame3.add(b4);
        frame3.add(field3);
        lbl.setBounds(240, 150, 200, 20);
        b4.setBounds(290, 250, 100, 30);
        field3.setBounds(240, 200, 200, 30);
        frame3.setSize(700, 600);
        frame3.setResizable(false);
        frame3.setLocationRelativeTo(null);
        frame3.setLayout(null);
        frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame3.setVisible(true);
        b4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nameOfPlayer = field3.getText();
            //GamePanel obj1 = new GamePanel();
                //obj1.my_update(nameOfPlayer);

                new GameFrame(nameOfPlayer);
                frame3.dispose();
            }
        });
    }
}
