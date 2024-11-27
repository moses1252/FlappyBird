import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import java.awt.*;

//implements ActionListener, KeyListener 
public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int boardWidth = 360;
    int boardHeight = 640;

    // images
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    // bird, give x and y posotion
    int birdX = boardWidth / 8; // going to place x 1/8 from the left side
    int birdY = boardHeight / 2; // going to place y 1/2 from the top of the side

    // lets give bird its width and height;
    int birdWidth = 34;
    int birdHeight = 24;

    // game logic
    Bird bird;
    int velocityY = 0;
    int gravity = 1; // will be 1 pixel heavy

    // variable for game loop
    Timer gameLoop;

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

    FlappyBird() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        // setBackground(Color.blue);
        setFocusable(true);// to make sure that our application uses this one takes in our key events
        addKeyListener(this); // to make sure we have implemented the needed methods

        // load images
        backgroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        // bird
        bird = new Bird(birdImg);

        // game timer: we want 60 frames per second
        // 1000 ms = 1 second
        // this refers to the fappy bird class!
        gameLoop = new Timer(1000 / 60, this); // 1000/60 = 16.6
        gameLoop.start(); // without this line the game will only draw once and nvr update again
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

    public void move() {
        // bird
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0);
        // bird.y = Math.max(bird.y, 640); i was trying to get the bird to not fall down
        // into map but didn't work
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // every 16 miliseconds or 60 times a seconds
        repaint(); // this will update the paint component
        move();
    }

    // the three ways we can apply key events
    @Override
    public void keyPressed(KeyEvent e) {// to read character keys, like 'a' or '8' AND f5, so for all keys
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocityY = -9;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) { // to read character keys, like 'a' or '8' but not f5

    }

    @Override
    public void keyReleased(KeyEvent e) {// to read how long you press a key and when you let go

    }

    // // pipe class
    // int pipeX = boardWidth;
    // int pipeY = 0;
    // int pipeWidth = 64; // scaled by 1/6
    // int pipeHeight = 512;

}
