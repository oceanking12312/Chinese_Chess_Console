package bancotuong;

import java.util.ArrayList;
import java.util.List;

public class Cannon extends Piece {
	public Cannon(Color color) {
		super(TypePiece.CANNON, color);
	}

	@Override
	public List<String> getValidMoves(int row, int col, ChineseChessBoard board) {
		List<String> validMoves = new ArrayList<>();

		// Kiểm tra các nước đi theo hàng ngang và dọc
		validMoves.addAll(getValidLinearMoves(row, col, board, true)); // Di chuyển theo cột (dọc)
		validMoves.addAll(getValidLinearMoves(row, col, board, false)); // Di chuyển theo hàng (ngang)

		return validMoves;
	}

	private List<String> getValidLinearMoves(int row, int col, ChineseChessBoard board, boolean isVertical) {
		List<String> moves = new ArrayList<>();

		// Di chuyển dọc hoặc ngang
		for (int direction = -1; direction <= 1; direction += 2) { // -1 (trái/lên), 1 (phải/xuống)
			boolean hasJumped = false;

			for (int i = 1; i < (isVertical ? ChineseChessBoard.getRow() : ChineseChessBoard.getCol()); i++) {
				int newRow = row + (isVertical ? i * direction : 0);
				int newCol = col + (isVertical ? 0 : i * direction);

				if (newRow < 0 || newRow >= ChineseChessBoard.getRow() || newCol < 0
						|| newCol >= ChineseChessBoard.getCol()) {
					break; // Vượt quá bàn cờ
				}

				Piece targetPiece = board.board[newRow][newCol];

				if (targetPiece == null) {
					if (!hasJumped) {
						moves.add(newRow + "," + newCol); // Di chuyển không bắt quân
					}
				} else {
					if (!hasJumped) {
						hasJumped = true; // Gặp quân cản, chuẩn bị để nhảy qua
					} else {
						if (targetPiece.color != this.color) {
							moves.add(newRow + "," + newCol); // Tấn công quân của đối phương sau khi nhảy qua 1 quân cản
						}
						break; // Không thể nhảy thêm sau khi đã bắt quân
					}
				}
			}
		}
		return moves;
	}
}
