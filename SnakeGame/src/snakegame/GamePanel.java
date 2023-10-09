
package snakegame;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Random;
import java.sql.*;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE);
    static final int DELAY = 75;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 3;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

//    For adding the music to the code
    InputStream music_eat, music_collison;
    AudioStream audios;

//    NameField obj = new NameField();
//    Menu obj1 = new Menu();
    //String nameOfPlayer1 = obj.nameOfPlayer;
    String uname = "root";
    String passoword = "mysql";
    String url = "jdbc:mysql://localhost:3306/snakegame";
    private String string1;
//    public void my_update(String str){
//        string1 = str;
//        System.out.println(string1);
//    }

    GamePanel(String s) {
        System.out.println("GamePanel Constructor");
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
        string1 = s;
    }

    public void startGame() {
        System.out.println("public void startGame");
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        System.out.println("public void paintComponent");
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        System.out.println("public void draw");
        if (running) {
//            TO PRINT THE GRID IN GAME PANEL.
//            for(int i = 0;i<SCREEN_HEIGHT/UNIT_SIZE;i++){
//            g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,SCREEN_HEIGHT);
//            g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH,i*UNIT_SIZE);
//            }
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

//            SETTING THE COLOR OF THE SNAKE.
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }

//            SETTING THE SCORE BOARD ON TOP OF SCREEN.
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());

        } else {
            gameOver(g);
        }
    }

    public void newApple() {
        System.out.println("public void newApple");
        appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    public void move() {
        System.out.println("public void move");
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    public void checkApple() {
        System.out.println("public void checkApple");
        if ((x[0] == appleX) && (y[0] == appleY)) {
            try {
                music_eat = new FileInputStream(new File("C:\\Users\\Raman\\OneDrive\\Desktop\\PROJECTS\\JAVA\\eat.wav"));
                audios = new AudioStream(music_eat);
                AudioPlayer.player.start(audios);
            } catch (Exception e) {
                e.printStackTrace();
            }
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollisions() {
        System.out.println("public void checkCollision");
//        BODY PART COLLISOIN
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }

//        BOUNDARY COLLISOIN
        if (x[0] < 0) {
            running = false;
        }
        if (x[0] > SCREEN_WIDTH) {
            running = false;
        }
        if (y[0] < 0) {
            running = false;
        }
        if (y[0] > SCREEN_HEIGHT) {
            running = false;
        }
        if (!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        System.out.println("public void gameOver");
//        SETTING GAMEOVER AND SCORE IN THE END OF THE SCREEN

        try {
            music_collison = new FileInputStream(new File("C:\\Users\\Raman\\OneDrive\\Desktop\\PROJECTS\\JAVA\\died.wav"));
            audios = new AudioStream(music_collison);
            AudioPlayer.player.start(audios);
        } catch (Exception e) {
            e.printStackTrace();
        }
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score:" + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score:" + applesEaten)) / 2, g.getFont().getSize());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics1.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);

//        JDBC
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection con = DriverManager.getConnection(url, uname, passoword);
            Statement statement = con.createStatement();

            String qstring = "INSERT INTO highscore(name,score) VALUES('" + string1 + "'," + applesEaten + ")";
            statement.executeUpdate(qstring);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("public void actionPerformed");
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
//        if(applesEaten%5==0){
//            DELAY-=100;
//        }
//        The repaint method in java is accessible in java.applet.Applet class is a final method utilized at whatever point 
//        we need to call update technique alongside the call to paint method; the call to refresh method clears the ongoing window, 
//        plays out an update, and a short time later calls paint method.
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("public void MykeyAdapter");
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
