import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GreenSquare extends JButton {

    private ArrayList<Piece> deadPieces = new ArrayList<Piece>();

    public GreenSquare(Point point){
        setPreferredSize(new Dimension(65, 65));
        setVisible(true);
        setLocation((int)point.getX(), (int)point.getY());
    }

    @Override
    public void paintComponent(Graphics g){
        g.setColor(Color.green);
        g.fillRect(0,0,65,65);
    }

    public void addDeadPiece(Piece piece){
        deadPieces.add(piece);
    }

    public ArrayList<Piece> getDeadPieces(){
        return deadPieces;
    }
}
