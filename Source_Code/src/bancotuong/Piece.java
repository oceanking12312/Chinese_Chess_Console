package bancotuong;

import java.util.List;

enum TypePiece {
	KING, ADVISOR, ELEPHANT, HORSE, ROOK, CANNON, PAWN
}

enum Color {
	RED, // Đỏ
	BLACK // Đen
}

abstract class Piece {
	TypePiece type;
	Color color;

	public Piece(TypePiece type, Color color) {
		this.type = type;
		this.color = color;
	}
	
	// Phương thức trả về ký hiệu quân cờ dựa trên type
	public String getSymbol() {
        return ChineseChessBoard.getPieceAbbreviation(type);
    }

    
	public abstract List<String> getValidMoves(int row, int col, ChineseChessBoard board);
}