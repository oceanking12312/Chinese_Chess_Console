package bancotuong;

import java.util.ArrayList;
import java.util.List;

public class Elephant extends Piece {
	public Elephant(Color color) {
		super(TypePiece.ELEPHANT, color);
	}

	@Override
	public List<String> getValidMoves(int row, int col, ChineseChessBoard board) {
		List<String> moves = new ArrayList<>();
		// Xác định phạm vi di chuyển của Tượng (không vượt sông)
		int minRow = (color == Color.RED) ? 0 : 5;
		int maxRow = (color == Color.RED) ? 4 : 9;
		// Các hướng di chuyển chéo hai ô: trên-trái, trên-phải, dưới-trái, dưới-phải
		int[][] directions = { { -2, -2 }, { -2, 2 }, { 2, -2 }, { 2, 2 } };
		for (int[] direction : directions) {
			int newRow = row + direction[0];
			int newCol = col + direction[1];
			int blockingRow = row + direction[0] / 2;
			int blockingCol = col + direction[1] / 2;
			// Kiểm tra xem nước đi có nằm trong phạm vi hợp lệ của Tượng không
			if (newRow >= minRow && newRow <= maxRow && newCol >= 0 && newCol < board.board[0].length) {
				// Kiểm tra xem ô chặn có quân nào không (để đảm bảo không bị cản đường)
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
