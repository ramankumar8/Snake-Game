package snakegame;

import javax.swing.*;

public class GameFrame extends JFrame{
    GameFrame(String s){
        this.add(new GamePanel(s));
        this.setTitle("SnakeGame");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null); 
    }
}
