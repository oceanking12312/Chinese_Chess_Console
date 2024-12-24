package bancotuong;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
	public Rook(Color color) {
		super(TypePiece.ROOK, color);
	}
	@Override
	public List<String> getValidMoves(int row, int col, ChineseChessBoard board) {
		List<String> moves = new ArrayList<>();

		// Các hướng di chuyển của quân Xe: lên, xuống, trái, phải
		int[][] directions = { { -1, 0 }, // Lên
				{ 1, 0 }, // Xuống
				{ 0, -1 }, // Trái
				{ 0, 1 } // Phải
		};
		for (int[] direction : directions) {
			int newRow = row;
			int newCol = col;
			// Tiếp tục di chuyển theo hướng cho đến khi chạm vào biên bàn cờ hoặc bị chặn
			while (true) {
				newRow += direction[0];
				newCol += direction[1];
				// Kiểm tra xem tọa độ mới có nằm trong phạm vi bàn cờ không
				if (newRow < 0 || newRow >= board.board.length || newCol < 0 || newCol >= board.board[0].length) {
					break; // Ra khỏi bàn cờ
				}
				// Nếu ô đích có quân cờ của đối phương hoặc không có quân nào
				if (board.board[newRow][newCol] == null) {
					// Nếu ô trống, thêm nước đi hợp lệ và tiếp tục di chuyển
					moves.add(newRow + "," + newCol);
				} else {
					// Nếu ô đích có quân đối phương, thêm nước đi và dừng lại
					if (board.board[newRow][newCol].color != this.color) {
						moves.add(newRow + "," + newCol);
					}
					break; // Dừng lại vì đã gặp quân cờ cản
				}
			}
		}
		return moves;
	}
}
