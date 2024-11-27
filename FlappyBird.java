import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import java.awt.*;

//implements ActionListener, KeyListener 
public class FlappyBird extends JPanel {
    int boardWidth = 360;
    int boardHeight = 640;

    // images
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    FlappyBird() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        // setBackground(Color.blue);

        // load images
        backgroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        // bird
        bird = new Bird(birdImg);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // background
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);

        // bird
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);
    }

    // bird, give x and y posotion
    int birdX = boardWidth / 8; // going to place x 1/8 from the left side
    int birdY = boardHeight / 2; // going to place y 1/2 from the top of the side

    // lets give bird its width and height;
    int birdWidth = 34;
    int birdHeight = 24;

    class Bird {
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img;

        Bird(Image img) {
            this.img = img;
        }
        // we can change this bird implementation if you want
    }

    // game logic
    Bird bird;

    // // pipe class
    // int pipeX = boardWidth;
    // int pipeY = 0;
    // int pipeWidth = 64; // scaled by 1/6
    // int pipeHeight = 512;

}
