package bancotuong;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
	public King(Color color) {
		super(TypePiece.KING, color);
	}

	@Override
	public List<String> getValidMoves(int row, int col, ChineseChessBoard board) {
		List<String> moves = new ArrayList<>();
		// Xác định phạm vi cung cho quân Tướng
		int minRow = (color == Color.RED) ? 0 : 7;
		int maxRow = (color == Color.RED) ? 2 : 9;
		int minCol = 3;
		int maxCol = 5;
		// Các hướng di chuyển: lên, xuống, trái, phải
		int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
		for (int[] direction : directions) {
			int newRow = row + direction[0];
			int newCol = col + direction[1];
			// Kiểm tra xem nước đi có nằm trong phạm vi cung của Tướng không
			if (newRow >= minRow && newRow <= maxRow && newCol >= minCol && newCol <= maxCol) {
				// Kiểm tra xem ô mới có quân cờ của đối phương không
				if (board.board[newRow][newCol] == null || board.board[newRow][newCol].color != this.color) {
					moves.add(newRow + "," + newCol);
				}
			}
		}
		return moves;
	}
}
