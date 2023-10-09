package snakegame;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Random;

public class Menu extends JFrame {

    Menu() {
        //System.out.println("Hello World");

        JFrame f1 = new JFrame("Menu");
        JButton b1 = new JButton("Play");
        JButton b2 = new JButton("HighScore");

        JLabel l1 = new JLabel("Snake Game");
        l1.setFont(new Font("Verdana", Font.BOLD, 30));
        l1.setForeground(Color.RED);

        setLayout(new BorderLayout());
        JLabel background = new JLabel(new ImageIcon("C:\\Users\\Raman\\OneDrive\\Desktop\\PROJECTS\\JAVA\\snakelogo1.png"));
        f1.add(background);
//	background.setLayout(new FlowLayout());

        l1.setBounds(360, 150, 300, 50);
        b1.setBounds(415, 250, 100, 30);
        b2.setBounds(415, 300, 100, 30);

        background.add(l1);
        background.add(b1);
        background.add(b2);

        b1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                new NameField();
            }
        }
        );
        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new HighScoreFrame();
            }
        });
        f1.pack();
        f1.setLocationRelativeTo(null);
        f1.setResizable(false);
//        f1.setLayout(null);
        f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f1.setVisible(true);
    }
}
