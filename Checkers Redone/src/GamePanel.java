import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class GamePanel extends JPanel{

    private WhitePiece[] whitePieces = new WhitePiece[12];
    private BlackPiece[] blackPieces = new BlackPiece[12];
    private boolean turn = true; // made boolean bc easier to switch back and forth

    private SquareDetails squareDetails = new SquareDetails();

    private ArrayList<GreenSquare> greenSquares = new ArrayList<GreenSquare>();

    private BufferedImage img = null;

    public GamePanel(){
        setPreferredSize(new Dimension(600,600));
        setFocusable(true);
        setLayout(null);

        initializePieces();
        initActionListeners();

        getBackgroundImage();
    }

    private void initActionListeners() {
        for(int i = 0; i < 12; i++){
            WhitePiece white = whitePieces[i];
            white.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for(int i = 0; i < greenSquares.size(); i++)
                        remove(greenSquares.get(i));
                    greenSquares.clear();

                    if(turn)
                        initGreenSquares(white, null,0);

                    for(int i = 0; i < greenSquares.size(); i++){
                        greenSquares.get(i).setSize(new Dimension(65, 65));
                        initGreenButttonListener(greenSquares.get(i), white);
                        add(greenSquares.get(i));
                    }

                    repaint();
                }
            });

            BlackPiece black = blackPieces[i];
            black.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for(int i = 0; i < greenSquares.size(); i++)
                        remove(greenSquares.get(i));
                    greenSquares.clear();

                    if(!turn)
                        initGreenSquares(black, null, 0);

                    for(int i = 0; i < greenSquares.size(); i++){
                        greenSquares.get(i).setSize(new Dimension(65, 65));
                        initGreenButttonListener(greenSquares.get(i), black);
                        add(greenSquares.get(i));
                    }

                    repaint();
                }
            });
        }
    }

    public void clearMoveablePieces(){
        Piece[] pieces;
        if(turn)
            pieces = whitePieces;
        else
            pieces = blackPieces;
        for(int i = 0; i < 12; i++){
            pieces[i].setMoveable(false);
        }
    }

    public void checkMoveablePieces(){
        Piece[] pieces;
        if(turn)
            pieces = whitePieces;
        else
            pieces = blackPieces;

        for(int i = 0; i < 12;i++) {

            Point square = pieces[i].getSquare();
            Point Location = pieces[i].getLocation();

            if (pieces[i].getPlayer() == 0 || pieces[i].isKing()) {
                if (square.getY() != 7) { //end of the checker board same thing with statement below
                    if (square.getX() != 0) {
                        if (!squareDetails.isFilled((int) square.getY() + 1, (int) square.getX() - 1)) {
                            whitePieces[i].setMoveable(true);
                        } else if (squareDetails.getPlayer((int) square.getY() + 1, (int) square.getX() - 1) != pieces[i].getPlayer()) { // if piece is the opposite team's
                            if (square.getY() + 1 != 7 && square.getX() - 1 != 0) { // end of checker board
                                if (!squareDetails.isFilled((int) square.getY() + 2, (int) square.getX() - 2)) {
                                    whitePieces[i].setMoveable(true);
                                }
                            }
                        }
                    }
                    if (square.getX() != 7) {
                        if (!squareDetails.isFilled((int) square.getY() + 1, (int) square.getX() + 1)) {
                            pieces[i].setMoveable(true);
                        } else if (squareDetails.getPlayer((int) square.getY() + 1, (int) square.getX() + 1) != pieces[i].getPlayer()) { // if piece is the opposite team's
                            if (square.getY() + 1 != 7 && square.getX() + 1 != 7) { // end of checker board
                                if (!squareDetails.isFilled((int) square.getY() + 2, (int) square.getX() + 2)) {
                                    pieces[i].setMoveable(true);
                                }
                            }
                        }
                    }
                }
            }

            if(pieces[i].getPlayer() == 1 || pieces[i].isKing()){ // if black piece
                if (square.getY() != 0) { //end of the checker board same thing with if statement below
                    if (square.getX() != 0) {
                        if (!squareDetails.isFilled((int) square.getY() - 1, (int) square.getX() - 1)) {
                            pieces[i].setMoveable(true);
                        }
                        else if(squareDetails.getPlayer((int) square.getY() - 1, (int) square.getX() - 1) != pieces[i].getPlayer()){ // if piece is the opposite team's
                            if(square.getY() - 1 != 0 && square.getX() - 1 != 0){ // end of checker board
                                if(!squareDetails.isFilled((int) square.getY() - 2, (int) square.getX() - 2)) {
                                    pieces[i].setMoveable(true);
                                }
                            }
                        }
                    }
                    if (square.getX() != 7) {
                        if (!squareDetails.isFilled((int) square.getY() - 1, (int) square.getX() + 1)) {
                            pieces[i].setMoveable(true);
                        }
                        else if(squareDetails.getPlayer((int) square.getY() - 1, (int) square.getX() + 1) != pieces[i].getPlayer()){ // if piece is the opposite team's
                            if(square.getY() - 1 != 0 && square.getX() + 1 != 7){ // end of checker board
                                if(!squareDetails.isFilled((int) square.getY() - 2, (int) square.getX() + 2)) {
                                    pieces[i].setMoveable(true);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void initGreenSquares(Piece piece, GreenSquare pastSquare, int pastDirection){ //pastsquare is for recursion to get all the targeted pieces from the past greensquares, past direction is to avoid infinte loops with king pieces
        Point square = piece.getSquare();
        Point Location = piece.getLocation();

        if(piece.getPlayer() == 0 || piece.isKing()) { // if white peice
            if (square.getY() != 7) { //end of the checker board same thing with statement below
                if (square.getX() != 0) {
                    if (!squareDetails.isFilled((int) square.getY() + 1, (int) square.getX() - 1)) {
                        if(pastSquare == null)
                            greenSquares.add(new GreenSquare(new Point((int) Location.getX() - 65, (int) Location.getY() + 65)));
                    }
                    else if(squareDetails.getPlayer((int) square.getY() + 1, (int) square.getX() - 1) != piece.getPlayer()){ // if piece is the opposite team's
                        if(square.getY() + 1 != 7 && square.getX() - 1 != 0){ // end of checker board
                            if(!squareDetails.isFilled((int) square.getY() + 2, (int) square.getX() - 2)) {
                                if(pastDirection != 4) {
                                    GreenSquare greenSquare = new GreenSquare(new Point((int) Location.getX() - 130, (int) Location.getY() + 130));
                                    if (pastSquare != null) {
                                        ArrayList<Piece> temp = pastSquare.getDeadPieces();
                                        for (int i = 0; i < temp.size(); i++)
                                            greenSquare.addDeadPiece(temp.get(i));
                                    }
                                    greenSquare.addDeadPiece(squareDetails.getBoardPiece((int) square.getY() + 1, (int) square.getX() - 1));
                                    greenSquares.add(greenSquare);

                                    piece.setLocation((int) Location.getX() - 130, (int) Location.getY() + 130);
                                    initGreenSquares(piece, greenSquare, 1);
                                    piece.setLocation((int) Location.getX(), (int) Location.getY());
                                }
                            }
                        }
                    }
                }
                if (square.getX() != 7) {
                    if (!squareDetails.isFilled((int) square.getY() + 1, (int) square.getX() + 1)) {
                        if(pastSquare == null)
                            greenSquares.add(new GreenSquare(new Point((int) Location.getX() + 65, (int) Location.getY() + 65)));
                    }
                    else if(squareDetails.getPlayer((int) square.getY() + 1, (int) square.getX() + 1) != piece.getPlayer()){ // if piece is the opposite team's
                        if(square.getY() + 1 != 7 && square.getX() + 1 != 7){ // end of checker board
                            if(!squareDetails.isFilled((int) square.getY() + 2, (int) square.getX() + 2)) {
                                if(pastDirection != 3) {
                                    GreenSquare greenSquare = new GreenSquare(new Point((int) Location.getX() + 130, (int) Location.getY() + 130));
                                    if (pastSquare != null) {
                                        ArrayList<Piece> temp = pastSquare.getDeadPieces();
                                        for (int i = 0; i < temp.size(); i++)
                                            greenSquare.addDeadPiece(temp.get(i));
                                    }
                                    greenSquare.addDeadPiece(squareDetails.getBoardPiece((int) square.getY() + 1, (int) square.getX() + 1));
                                    greenSquares.add(greenSquare);

                                    piece.setLocation((int) Location.getX() + 130, (int) Location.getY() + 130);
                                    initGreenSquares(piece, greenSquare, 2);
                                    piece.setLocation((int) Location.getX(), (int) Location.getY());
                                }
                            }
                        }
                    }
                }
            }
        }

        if(piece.getPlayer() == 1 || piece.isKing()){ // if black piece
            if (square.getY() != 0) { //end of the checker board same thing with if statement below
                if (square.getX() != 0) {
                    if (!squareDetails.isFilled((int) square.getY() - 1, (int) square.getX() - 1)) {
                        if(pastSquare == null)
                            greenSquares.add(new GreenSquare(new Point((int) Location.getX() - 65, (int) Location.getY() - 65)));
                    }
                    else if(squareDetails.getPlayer((int) square.getY() - 1, (int) square.getX() - 1) != piece.getPlayer()){ // if piece is the opposite team's
                        if(square.getY() - 1 != 0 && square.getX() - 1 != 0){ // end of checker board
                            if(!squareDetails.isFilled((int) square.getY() - 2, (int) square.getX() - 2)) {
                                if(pastDirection != 2) {
                                    GreenSquare greenSquare = new GreenSquare(new Point((int) Location.getX() - 130, (int) Location.getY() - 130));
                                    if (pastSquare != null) {
                                        ArrayList<Piece> temp = pastSquare.getDeadPieces();
                                        for (int i = 0; i < temp.size(); i++)
                                            greenSquare.addDeadPiece(temp.get(i));
                                    }
                                    greenSquare.addDeadPiece(squareDetails.getBoardPiece((int) square.getY() - 1, (int) square.getX() - 1));
                                    greenSquares.add(greenSquare);

                                    piece.setLocation((int) Location.getX() - 130, (int) Location.getY() - 130);
                                    initGreenSquares(piece, greenSquare, 3);
                                    piece.setLocation((int) Location.getX(), (int) Location.getY());
                                }
                            }
                        }
                    }
                }
                if (square.getX() != 7) {
                    if (!squareDetails.isFilled((int) square.getY() - 1, (int) square.getX() + 1)) {
                        if(pastSquare == null)
                            greenSquares.add(new GreenSquare(new Point((int) Location.getX() + 65, (int) Location.getY() - 65)));
                    }
                    else if(squareDetails.getPlayer((int) square.getY() - 1, (int) square.getX() + 1) != piece.getPlayer()){ // if piece is the opposite team's
                        if(square.getY() - 1 != 0 && square.getX() + 1 != 7){ // end of checker board
                            if(!squareDetails.isFilled((int) square.getY() - 2, (int) square.getX() + 2)) {
                                if(pastDirection != 1) {
                                    GreenSquare greenSquare = new GreenSquare(new Point((int) Location.getX() + 130, (int) Location.getY() - 130));
                                    if (pastSquare != null) {
                                        ArrayList<Piece> temp = pastSquare.getDeadPieces();
                                        for (int i = 0; i < temp.size(); i++)
                                            greenSquare.addDeadPiece(temp.get(i));
                                    }
                                    greenSquare.addDeadPiece(squareDetails.getBoardPiece((int) square.getY() - 1, (int) square.getX() + 1));
                                    greenSquares.add(greenSquare);

                                    piece.setLocation((int) Location.getX() + 130, (int) Location.getY() - 130);
                                    initGreenSquares(piece, greenSquare, 4);
                                    piece.setLocation((int) Location.getX(), (int) Location.getY());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void initGreenButttonListener(GreenSquare greenSquare, Piece piece){
        greenSquare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                squareDetails.setBoardPiece((int)piece.getSquare().getY(), (int)piece.getSquare().getX(), null);

                piece.setLocation((int)greenSquare.getLocation().getX(), (int)greenSquare.getLocation().getY());

                squareDetails.setBoardPiece((int)piece.getSquare().getY(), (int)piece.getSquare().getX(), piece);

                ArrayList<Piece> deadPieces = greenSquare.getDeadPieces();
                for(int i = 0; i < deadPieces.size(); i++){
                    squareDetails.setBoardPiece((int)deadPieces.get(i).getSquare().getY(), (int)deadPieces.get(i).getSquare().getX(), null);
                    remove(deadPieces.get(i));
                }

                for(int i = 0; i < greenSquares.size(); i++)
                    remove(greenSquares.get(i));
                greenSquares.clear();

                if(piece.getPlayer() == 0 && piece.getSquare().getY() == 7)
                    piece.makeKing();
                else if(piece.getPlayer() == 1 && piece.getSquare().getY() == 0)
                    piece.makeKing();

                clearMoveablePieces();

                turn = !turn;

                checkMoveablePieces();

                repaint();
            }
        });
    }

    public void getBackgroundImage(){
        try {
            img = ImageIO.read(getClass().getResource("/Resources/checkerboard.jpg"));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void paintComponent(Graphics g){
        g.drawImage(img.getScaledInstance(600, 600, Image.SCALE_DEFAULT), 0, 0, null);
    }

    public void initializePieces(){
        int x = 105;
        int y = 40;
        int difference = -65; // adjusting left or right from white pieces
        for(int i = 0; i < 12; i++){
            WhitePiece white = new WhitePiece();
            BlackPiece black = new BlackPiece();
            white.setSize(new Dimension(65, 65));
            black.setSize(new Dimension(65, 65));
            white.setLocation(x, y);
            black.setLocation(x + difference, y + 325);
            x += 130;
            if ((i + 1) % 4 == 0) { // Every changing row
                if(x == 625) { // 625 bc after it adds up 4 times
                    x = 40;
                    difference = 65;
                }
                else {
                    x = 105;
                    difference = -65;
                }
                y += 65;
            }

            whitePieces[i] = white;
            add(whitePieces[i]);

            blackPieces[i] = black;
            add(blackPieces[i]);
        }

        int temp = -1;
        int temp2 = 1;
        int temp3 = 0;
        for(int i = 0 ; i < 12; i++){
            if(i % 4 == 0) {
                if(i == 4) {
                    temp2 = 0;
                    temp3 = 1;
                }
                else if(i == 8) {
                    temp2 = 1;
                    temp3 = 0;
                }
                temp++;
            }
            squareDetails.setBoardPiece(0 + temp, (2 * (i % 4)) + temp2, whitePieces[i]);
            squareDetails.setBoardPiece(5 + temp, (2 * (i % 4)) + temp3, blackPieces[i]);
        }
        checkMoveablePieces();
    }
}
