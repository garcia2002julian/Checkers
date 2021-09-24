import javax.imageio.ImageIO;
import java.io.IOException;

public class BlackPiece extends Piece {

    public BlackPiece(){
        super();
        player = 1;
    }

    @Override
    public void getImages(){
        try{
            Piece1 = ImageIO.read(getClass().getResource("/Resources/blackone.jpg"));
            Piece2 = ImageIO.read(getClass().getResource("/Resources/blacktwo.jpg"));
            Piece3 = ImageIO.read(getClass().getResource("/Resources/blackthree.jpg"));
            Piece4 = ImageIO.read(getClass().getResource("/Resources/blackfour.jpg"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
