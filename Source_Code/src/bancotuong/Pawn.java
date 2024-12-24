package bancotuong;

import java.util.ArrayList;
import java.util.List;

class Pawn extends Piece {
	public Pawn(Color color) {
		super(TypePiece.PAWN, color);
	}

	@Override
	public List<String> getValidMoves(int row, int col, ChineseChessBoard board) {
		List<String> validMoves = new ArrayList<>();
		int direction = (color == Color.RED) ? 1 : -1; // Đỏ đi xuống (+1), Đen đi lên (-1)
		// Quân Tốt đi thẳng về phía đối phương
		int newRow = row + direction;
		if (newRow >= 0 && newRow < 10 && board.board[newRow][col] == null) {
			validMoves.add(newRow + "," + col);
		}
		// Nếu quân Tốt đã qua sông, nó có thể đi ngang
		boolean crossedRiver = (color == Color.RED) ? row >= 5 : row <= 4;
		if (crossedRiver) {
			// Sang trái
			if (col - 1 >= 0 && board.board[row][col - 1] == null) {
				validMoves.add(row + "," + (col - 1));
			}
			// Sang phải
			if (col + 1 < 9 && board.board[row][col + 1] == null) {
				validMoves.add(row + "," + (col + 1));
			}
		}
		// Quân Tốt có thể ăn quân đối phương khi cùng ở trên cùng cột
		if (board.board[row + direction][col] != null && board.board[row + direction][col].color != color) { // Kiểm tra nếu có quân đối phương																			
			validMoves.add((row + direction) + "," + col);
		}
		return validMoves;
	}
}
