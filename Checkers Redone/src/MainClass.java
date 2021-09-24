import javax.swing.*;

public class MainClass{

    public static void main(String[] args) {
        JFrame frame = new JFrame();

        GamePanel gamePanel = new GamePanel();
        gamePanel.setVisible(true);
        frame.add(gamePanel);

        frame.setTitle("Checkers");
        frame.setResizable(false);
        frame.setLocation(400, 100);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
