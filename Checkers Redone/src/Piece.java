import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Piece extends JButton {

    private boolean King = false;
    private boolean Moveable = false;

    protected BufferedImage Piece1;
    protected BufferedImage Piece2;
    protected BufferedImage Piece3;
    protected BufferedImage Piece4;

    protected int player;

    public Piece(){
        setPreferredSize(new Dimension(65, 65));
        getImages();
        setVisible(true);
    }

    public void paintComponent(Graphics g){
        BufferedImage img = null;
        if(King){
            if(Moveable)
                img = Piece4;
            else
                img = Piece2;
        }
        else{
            if(Moveable)
                img = Piece3;
            else
                img = Piece1;
        }
        g.drawImage(img.getScaledInstance(65, 65, Image.SCALE_DEFAULT), 0, 0, null);
    }

    // meant to be overriden by the WhitePiece and BlackPiece classes
    protected void getImages(){}

    public void makeKing(){
        King = true;
        repaint();
    }

    public void setMoveable(Boolean moveable){
        Moveable = moveable;
        repaint();
    }

    public boolean isKing(){
        return King;
    }

    public int getPlayer(){
        return player;
    }

    public Point getSquare(){
        return new Point((int)(getLocation().getX() - 40) / 65, (int)(getLocation().getY() - 40) / 65);
    }
}
