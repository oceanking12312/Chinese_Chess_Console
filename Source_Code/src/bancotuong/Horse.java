package bancotuong;

import java.util.ArrayList;
import java.util.List;

public class Horse extends Piece {
	public Horse(Color color) {
		super(TypePiece.HORSE, color);
	}

	@Override
	public List<String> getValidMoves(int row, int col, ChineseChessBoard board) {
		List<String> moves = new ArrayList<>();
		// Các hướng di chuyển theo hình chữ "L" của quân Mã
		int[][] directions = { { -2, -1, -1, 0 }, { -2, 1, -1, 0 }, // Lên 2 ô, sang trái hoặc phải 1 ô
				{ 2, -1, 1, 0 }, { 2, 1, 1, 0 }, // Xuống 2 ô, sang trái hoặc phải 1 ô
				{ -1, -2, 0, -1 }, { -1, 2, 0, 1 }, // Sang trái 2 ô, lên hoặc xuống 1 ô
				{ 1, -2, 0, -1 }, { 1, 2, 0, 1 } // Sang phải 2 ô, lên hoặc xuống 1 ô
		};
		for (int[] direction : directions) {
			int newRow = row + direction[0];
			int newCol = col + direction[1];
			int blockingRow = row + direction[2];
			int blockingCol = col + direction[3];
			// Kiểm tra xem nước đi có nằm trong phạm vi bàn cờ không
			if (newRow >= 0 && newRow < board.board.length && newCol >= 0 && newCol < board.board[0].length) {
				// Kiểm tra xem ô chặn có quân nào không (để đảm bảo không bị "chống mã")
				if (board.board[blockingRow][blockingCol] == null) {
					// Kiểm tra xem ô đích có quân cờ của đối phương không
					if (board.board[newRow][newCol] == null || board.board[newRow][newCol].color != this.color) {
						moves.add(newRow + "," + newCol);
					}
				}
			}
		}
		return moves;
	}
}
