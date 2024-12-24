package bancotuong;

import java.util.ArrayList;
import java.util.List;

public class Advisor extends Piece {
	public Advisor(Color color) {
		super(TypePiece.ADVISOR, color);
	}

	@Override
	public List<String> getValidMoves(int row, int col, ChineseChessBoard board) {
		List<String> moves = new ArrayList<>();
		// Xác định phạm vi cung cho quân Sĩ
		int minRow = (color == Color.RED) ? 0 : 7;
		int maxRow = (color == Color.RED) ? 2 : 9;
		int minCol = 3;
		int maxCol = 5;
		// Các hướng di chuyển chéo: trên-trái, trên-phải, dưới-trái, dưới-phải
		int[][] directions = { { -1, -1 }, { -1, 1 }, { 1, -1 }, { 1, 1 } };
		for (int[] direction : directions) {
			int newRow = row + direction[0];
			int newCol = col + direction[1];
			// Kiểm tra xem nước đi có nằm trong phạm vi cung của Sĩ không
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
