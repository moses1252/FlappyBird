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
    int velocityX = -4; // rate at which pipes move to the left (simulates bird moving right)
    int velocityY = 0; // rate at which bird moves up and down
    int gravity = 1; // will be 1 pixel heavy
    Random random = new Random();

    // because we have many pipes we need to store them in a list
    ArrayList<Pipe> pipes;

    // variable for game loop and pipes
    Timer gameLoop;
    Timer placePipesTimer;
    boolean gameOver = false;

    // Pipes
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64; // scaled by 1/6
    int pipeHeight = 512;

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

    class Pipe {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false; // this is how we will keep track of score

        Pipe(Image img) {
            this.img = img;
        }

    }

    public void placePipes() {
        // (0-1) * pipeHeight/2 -> so this will give us a random number between 0 to 256
        // 128
        // 0 - 128 - (0 - 256) -->
        // so the range is going to be 1/4 pipeHeight -> 3/4 pipeHeight

        int randomPipeY = (int) (pipeY - pipeHeight / 4 - Math.random() * (pipeHeight / 2));
        int openingSpace = boardHeight / 4;

        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe); // we need a timer for this place pipe function

        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
        pipes.add(bottomPipe);
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

        // bird and pipe
        bird = new Bird(birdImg);
        pipes = new ArrayList<Pipe>();

        // place pipes timer is used here
        // this will place a new pipe every 1.5 secs
        placePipesTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });

        placePipesTimer.start(); // this is the reason the pipes refresh!!

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

        // pipes
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }
    }

    public void move() {
        // bird
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0);
        // bird.y = Math.max(bird.y, 640); i was trying to get the bird to not fall down
        // into map but didn't work

        // pipes
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;

            if (collision(bird, pipe)) {
                gameOver = true;
            }
        }

        if (bird.y > boardHeight) {
            gameOver = true;
        }
    }

    boolean collision(Bird a, Pipe b) {
        return a.x < b.x + b.width && // a's top left corner doesn't reach b's top right corner
                a.x + a.width > b.x && // a's top right corner passes b's top left corner
                a.y < b.y + b.height && // a's top left corner doesn't reach b's bottom left corner
                a.y + a.height > b.y; // a's bottom left corner passes b's top left corner
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // every 16 miliseconds or 60 times a seconds
        move();
        repaint(); // this will update the paint component
        if (gameOver) { // if game over stop the times and stop adding more pipes to array list
            placePipesTimer.stop();
            gameLoop.stop();
        }
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
