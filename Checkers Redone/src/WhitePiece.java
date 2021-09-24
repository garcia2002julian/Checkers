import javax.imageio.ImageIO;
import java.io.IOException;

public class WhitePiece extends Piece {

    public WhitePiece(){
        super();
        player = 0;
    }

    @Override
    public void getImages(){
        try{
            Piece1 = ImageIO.read(getClass().getResource("/Resources/whiteone.jpg"));
            Piece2 = ImageIO.read(getClass().getResource("/Resources/whitetwo.jpg"));
            Piece3 = ImageIO.read(getClass().getResource("/Resources/whitethree.jpg"));
            Piece4 = ImageIO.read(getClass().getResource("/Resources/whitefour.jpg"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
