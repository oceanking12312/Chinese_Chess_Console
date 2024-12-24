package bancotuong;

public class MoveHistory {
	int oldRow, oldCol, newRow, newCol;
    Piece movedPiece, capturedPiece;

    
    // Constructor để lưu lại thông tin nước đi
    public MoveHistory(int oldRow, int oldCol, int newRow, int newCol, Piece movedPiece, Piece capturedPiece) {
        this.oldRow = oldRow;
        this.oldCol = oldCol;
        this.newRow = newRow;
        this.newCol = newCol;
        this.movedPiece = movedPiece;
        this.capturedPiece = capturedPiece;
    }
}
