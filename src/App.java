import javax.swing.JFrame;

public class App {
    public static void main(String[] args) throws Exception {
        int boardWidth = 360;
        int boardHeight = 640;

        JFrame frame = new JFrame("Flappy Bird");
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null); // is going to place the window at the center of our screen
        frame.setResizable(false);// lets make sure the user cant fix the size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // this will termintate the program once user closes
                                                              // program

    }
}
