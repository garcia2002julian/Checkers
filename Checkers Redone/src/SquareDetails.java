public class SquareDetails {

    private Piece[][] board = new Piece[8][8];

    public SquareDetails(){

    }

    public boolean isFilled(int y, int x){
        return board[y][x] != null;
    }

    public int getPlayer(int y, int x){
        return board[y][x].getPlayer();
    }

    public void setBoardPiece(int y, int x, Piece piece){
        board[y][x] = piece;
    }

    public Piece getBoardPiece(int y , int x){
        return board[y][x];
    }
}