package bancotuong;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ChineseChessBoard {
	private static final int row = 10;
	private static final int col = 9;
	private int redCheckCount = 0;   // Số lần ĐỎ chiếu tướng liên tiếp
	private int blackCheckCount = 0; // Số lần ĐEN chiếu tướng liên tiếp
	private static final int MAX_CHECK_COUNT = 10; // Giới hạn số lần chiếu liên tục
	private int moveWithoutCaptureCount = 0; // Số lượt đi không có quân bị ăn
	private static final int MAX_MOVES_WITHOUT_CAPTURE = 120; // Giới hạn số lượt đi không có quân bị ăn
	private List<MoveHistory> moveHistoryList = new ArrayList<>();
	
	public static int getRow() {
		return row;
	}

	public static int getCol() {
		return col;
	}

	Piece[][] board = new Piece[row][col];
	String red = "\033[31m"; // Mã ANSI cho màu đỏ
	String white = "\033[37m"; // Mã ANSI cho màu trắng
	String reset = "\033[0m"; // Mã ANSI để reset màu

	public ChineseChessBoard() {
		// Khởi tạo vị trí các quân cờ cho bên ĐỎ
		board[0][4] = new King(Color.RED);
		board[0][3] = new Advisor(Color.RED);
		board[0][5] = new Advisor(Color.RED);
		board[0][2] = new Elephant(Color.RED);
		board[0][6] = new Elephant(Color.RED);
		board[0][1] = new Horse(Color.RED);
		board[0][7] = new Horse(Color.RED);
		board[0][0] = new Rook(Color.RED);
		board[0][8] = new Rook(Color.RED);
		board[2][1] = new Cannon(Color.RED);
		board[2][7] = new Cannon(Color.RED);
		board[3][0] = new Pawn(Color.RED);
		board[3][2] = new Pawn(Color.RED);
		board[3][4] = new Pawn(Color.RED);
		board[3][6] = new Pawn(Color.RED);
		board[3][8] = new Pawn(Color.RED);

		// Khởi tạo vị trí các quân cờ cho bên ĐEN
		board[9][4] = new King(Color.BLACK);
		board[9][3] = new Advisor(Color.BLACK);
		board[9][5] = new Advisor(Color.BLACK);
		board[9][2] = new Elephant(Color.BLACK);
		board[9][6] = new Elephant(Color.BLACK);
		board[9][1] = new Horse(Color.BLACK);
		board[9][7] = new Horse(Color.BLACK);
		board[9][0] = new Rook(Color.BLACK);
		board[9][8] = new Rook(Color.BLACK);
		board[7][1] = new Cannon(Color.BLACK);
		board[7][7] = new Cannon(Color.BLACK);
		board[6][0] = new Pawn(Color.BLACK);
		board[6][2] = new Pawn(Color.BLACK);
		board[6][4] = new Pawn(Color.BLACK);
		board[6][6] = new Pawn(Color.BLACK);
		board[6][8] = new Pawn(Color.BLACK);
		
	}
	
	// Hàm hỗ trợ: Chuyển trạng thái bàn cờ thành chuỗi
	private String getBoardStateAsString() {
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < 10; i++) {
	        for (int j = 0; j < 9; j++) {
	            if (board[i][j] != null) {
	                sb.append(board[i][j].getSymbol()).append(board[i][j].color).append(";");
	            } else {
	                sb.append(".");
	            }
	        }
	    }
	    return sb.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder brdStr = new StringBuilder();
		for (int i = 0; i < row; i++) {
			brdStr.append(i).append(" ");
			for (int j = 0; j < col; j++) {
				if (board[i][j] != null) {
					String pieceAbbr = getPieceAbbreviation(board[i][j].type);
					;
					if (board[i][j].color == Color.RED) {
						pieceAbbr = red + pieceAbbr + reset; // Màu đỏ
					} else {
						pieceAbbr = white + pieceAbbr + reset; // Màu trắng
					}
					brdStr.append(pieceAbbr).append(" ");
				} else {
					brdStr.append(". ");
				}
			}
			brdStr.append("\n");
		}
		brdStr.append("  ");
		for (int j = 0; j < col; j++) {
			brdStr.append(j).append(" ");
		}
		return brdStr.toString();
	}

	public static String getPieceAbbreviation(TypePiece piece) {
		switch (piece) {
		case KING:
			return "K";
		case ADVISOR:
			return "A";
		case ELEPHANT:
			return "E";
		case HORSE:
			return "H";
		case ROOK:
			return "R";
		case CANNON:
			return "C";
		case PAWN:
			return "P";
		default:
			return "";
		}
	}

	// Các hàm kiểm tra hợp lệ
	// Lấy nước đi hợp lệ của quân cờ dựa vào vị trí
	public List<String> getValidMoves(int row, int col) {
		if (board[row][col] != null) {
			List<String> validMoves = new ArrayList<>();
			for (String move : board[row][col].getValidMoves(row, col, this)) {
				int targetRow = Integer.parseInt(move.split(",")[0]);
				int targetCol = Integer.parseInt(move.split(",")[1]);

				if (isMoveSafe(board[row][col].color, row, col, targetRow, targetCol)) {
					validMoves.add(move);
				}
			}
			if (validMoves.isEmpty()) {
				System.out.println("Quân cờ không có nước đi an toàn nào.");
			}
			return validMoves;
		} else {
			System.out.println("Không có quân cờ nào ở vị trí này!");
			return null;
		}

	}

	// Kiểm tra hợp lệ
	static int validInt(String name) {
		Scanner scanner = new Scanner(System.in);
		int x = 0;
		while (true) {
			System.out.print(name);
			if (scanner.hasNextInt()) {
				x = scanner.nextInt();
				break;
			} else {
				System.out.println("Nhập sai định dạng. Vui lòng nhập lại số nguyên.");
				scanner.next();
			}
		}

		return x;
	}
	
	// Thêm phương thức để kiểm tra xem người chơi có nước đi hợp lệ không
	public boolean hasValidMove(Color player) {
	    for (int i = 0; i < row; i++) {
	        for (int j = 0; j < col; j++) {
	            if (board[i][j] != null && board[i][j].color == player) {
	                List<String> validMoves = getValidMoves(i, j);
	                if (validMoves != null && !validMoves.isEmpty()) {
	                    return true;  // Nếu có ít nhất một nước đi hợp lệ, trả về true
	                }
	            }
	        }
	    }
	    return false;  // Nếu không có nước đi hợp lệ nào
	}
	//Kết thúc các hàm kiểm tra hợp lệ
	
	//Các hàm cho King
	private boolean isMoveSafe(Color color, int fromRow, int fromCol, int toRow, int toCol) {
		Piece temp = board[toRow][toCol]; // Save the piece at the destination
		board[toRow][toCol] = board[fromRow][fromCol]; // Move the piece
		board[fromRow][fromCol] = null;

		boolean isSafe = !isKingInCheck(color); // Check if the king is still safe

		// Restore the original state
		board[fromRow][fromCol] = board[toRow][toCol];
		board[toRow][toCol] = temp;

		return isSafe;
	}

	private boolean isKingInCheck(Color color) {
		// Tìm vị trí của tướng
		int kingRow = -1, kingCol = -1;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (board[i][j] != null && board[i][j].type == TypePiece.KING && board[i][j].color == color) {
					kingRow = i;
					kingCol = j;
					break;
				}
			}
		}

		// Kiểm tra xem có quân cờ nào của đối phương có thể chiếu vào tướng
		Color opponentColor = (color == Color.RED) ? Color.BLACK : Color.RED;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (board[i][j] != null && board[i][j].color == opponentColor) {
					List<String> moves = board[i][j].getValidMoves(i, j, this);
					String kingPos = kingRow + "," + kingCol;
					if (moves.contains(kingPos)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	private boolean isCheckmate(Color color) {
		// Nếu tướng của màu này không bị chiếu thì không phải là chiếu bí
		if (!isKingInCheck(color)) {
			return false;
		}

		// Duyệt qua tất cả quân cờ của màu hiện tại để kiểm tra các nước đi có thể cứu
		// tướng
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (board[i][j] != null && board[i][j].color == color) {
					List<String> moves = board[i][j].getValidMoves(i, j, this);
					for (String move : moves) {
						// Lưu vị trí ban đầu
						Piece temp = board[i][j];
						int targetRow = Integer.parseInt(move.split(",")[0]);
						int targetCol = Integer.parseInt(move.split(",")[1]);
						Piece targetPiece = board[targetRow][targetCol];

						// Di chuyển quân cờ tạm thời để kiểm tra
						board[targetRow][targetCol] = temp;
						board[i][j] = null;

						// Kiểm tra nếu tướng còn bị chiếu sau nước đi này
						boolean isStillInCheck = isKingInCheck(color);

						// Khôi phục vị trí ban đầu
						board[i][j] = temp;
						board[targetRow][targetCol] = targetPiece;

						if (!isStillInCheck) {
							return false; // Nếu có nước đi tránh chiếu, không phải chiếu bí
						}
					}
				}
			}
		}

		return true; // Không có nước đi nào để cứu tướng => chiếu bí
	}

	private boolean isKingExposed() {
	    int redKingRow = -1, redKingCol = -1;
	    int blackKingRow = -1, blackKingCol = -1;

	    // Tìm vị trí của tướng đỏ và tướng đen
	    for (int i = 0; i < row; i++) {
	        for (int j = 0; j < col; j++) {
	            if (board[i][j] != null) {
	                if (board[i][j].type == TypePiece.KING) {
	                    if (board[i][j].color == Color.RED) {
	                        redKingRow = i;
	                        redKingCol = j;
	                    } else if (board[i][j].color == Color.BLACK) {
	                        blackKingRow = i;
	                        blackKingCol = j;
	                    }
	                }
	            }
	        }
	    }

	    // Kiểm tra nếu hai tướng ở cùng một cột
	    if (redKingCol == blackKingCol) {
	        // Kiểm tra xem có quân cờ nào nằm giữa tướng không
	        int minRow = Math.min(redKingRow, blackKingRow);
	        int maxRow = Math.max(redKingRow, blackKingRow);
	        
	        // Duyệt từ hàng trên đến hàng dưới giữa hai tướng để kiểm tra có quân cờ nào không
	        for (int i = minRow + 1; i < maxRow; i++) {
	            if (board[i][redKingCol] != null) {
	                return false;  // Có quân cờ nằm giữa, không vi phạm
	            }
	        }
	        // Nếu không có quân cờ nào, tức là hai tướng đối mặt với nhau
	        return true;
	    }

	    // Nếu hai tướng không cùng cột, không vi phạm
	    return false;
	}
	//Kết thúc các hàm cho King
	

	
    

    // Hàm lưu nước đi vào lịch sử
    private void addMoveToHistory(int oldRow, int oldCol, int newRow, int newCol, Piece movedPiece, Piece capturedPiece) {
        moveHistoryList.add(new MoveHistory(oldRow, oldCol, newRow, newCol, movedPiece, capturedPiece));
    }

    // Hàm in lịch sử nước đi
    private void printMoveHistory() {
        System.out.println("Lịch sử các nước đi:");
        int i = 1;
        for (MoveHistory move : moveHistoryList) {
            System.out.println(i + ". " + (move.movedPiece.color == Color.RED ? "ĐỎ" : "ĐEN") + ": (" +
                    move.oldRow + "," + move.oldCol + ") --> (" + move.newRow + "," + move.newCol + ")");
            i++;
        }
    }
	
	// Hàm hoàn tác nước đi và xóa khỏi lịch sử
    public void undoMove() {
        if (moveHistoryList.isEmpty()) {
            System.out.println("Không có nước đi nào để hoàn tác.");
            return;
        }

        // Lấy nước đi cuối cùng từ lịch sử
        MoveHistory lastMove = moveHistoryList.remove(moveHistoryList.size() - 1);

        // Khôi phục quân cờ đã di chuyển về vị trí cũ
        board[lastMove.oldRow][lastMove.oldCol] = lastMove.movedPiece;
        board[lastMove.newRow][lastMove.newCol] = lastMove.capturedPiece;

        System.out.println("Đã hoàn tác nước đi.");
    }
    
    //Hàm bắt đầu trò chơi
	public void startGame() {
		Scanner scanner = new Scanner(System.in);
		Color currentPlayer = Color.RED;
		// Map để lưu trạng thái bàn cờ và số lần xuất hiện của mỗi trạng thái
	    Map<String, Integer> boardStates = new HashMap<>();
		while (true) {
			System.out.println(toString());
			System.out.println("\nLượt của " + (currentPlayer == Color.RED ? red + "ĐỎ" + reset : "ĐEN")
					+ ". Chọn quân cờ và vị trí cần đến.");
			
			// Kiểm tra xem người chơi hiện tại có đang bị chiếu tướng hay không
	        if (isKingInCheck(currentPlayer)) {
	            System.out.println((currentPlayer == Color.RED ? red + "ĐỎ" + reset : "ĐEN") + " đang bị chiếu!");
	            
	            // Cập nhật số lần chiếu liên tục
	            if (currentPlayer == Color.RED) {
	                redCheckCount++;
	                blackCheckCount = 0; // Reset cho Đen
	            } else {
	                blackCheckCount++;
	                redCheckCount = 0; // Reset cho Đỏ
	            }

	            // Kiểm tra nếu vượt quá số lần chiếu liên tục cho phép
	            if (redCheckCount >= MAX_CHECK_COUNT) {
	                System.out.println("ĐỎ đã chiếu liên tục 10 lần. ĐỎ bị xử thua!");
	                break;
	            }
	            if (blackCheckCount >= MAX_CHECK_COUNT) {
	                System.out.println("ĐEN đã chiếu liên tục 10 lần. ĐEN bị xử thua!");
	                break;
	            }
	        } else {
	            // Nếu không chiếu tướng, reset số lần chiếu liên tục
	            if (currentPlayer == Color.RED) {
	                redCheckCount = 0;
	            } else {
	                blackCheckCount = 0;
	            }
	        }
			
			// Kiểm tra xem người chơi bị chiếu bí không
			if (isCheckmate(currentPlayer)) {
				System.out
						.println("Chiếu bí! " + (currentPlayer == Color.RED ? "ĐEN" : red + "ĐỎ" + reset) + " thắng!");
				// Hỏi người chơi có muốn chơi ván cờ mới không
				System.out.println("Bạn có muốn tạo ván cờ mới không? (1: Có, 2: Thoát)");
				int choice = validInt("Lựa chọn của bạn: ");
				if (choice == 1) {
					resetBoard(); // Tạo lại bàn cờ mới
					currentPlayer = Color.RED; // Đặt lại lượt đầu tiên cho Đỏ
					boardStates.clear(); // Xóa lịch sử trạng thái bàn cờ
					continue; // Bắt đầu lại vòng lặp trò chơi
				} else {
					System.out.println("Kết thúc trò chơi. Cảm ơn đã chơi!");
					break; // Thoát chương trình
				}
			}
			
			// Kiểm tra "Hết nước đi": Nếu không còn nước đi hợp lệ, người chơi thua
	        if (!hasValidMove(currentPlayer)) {
	            System.out.println((currentPlayer == Color.RED ? red + "ĐỎ" + reset : "ĐEN") + " không có nước đi hợp lệ. " + 
	                               (currentPlayer == Color.RED ? "ĐEN" : red + "ĐỎ" + reset) + " thắng!");
	            break;  // Kết thúc trò chơi
	        }
			
			 // Lưu trạng thái hiện tại của bàn cờ dưới dạng chuỗi
	        String currentState = getBoardStateAsString();
	        boardStates.put(currentState, boardStates.getOrDefault(currentState, 0) + 1);

	        // Kiểm tra trạng thái bàn cờ đã lặp lại 3 lần hay chưa
	        if (boardStates.get(currentState) == 3) {
	            System.out.println("Hòa cờ: Trạng thái bàn cờ đã lặp lại 3 lần.");
	            break; // Kết thúc trò chơi
	        }

			int row = validInt("Nhập hàng (0-9): ");
			while (row < 0 || row > 9) { // Kiểm tra xem hàng có hợp lệ không
				System.out.println("Hàng không hợp lệ! Vui lòng nhập lại (0-9).");
				row = validInt("Nhập hàng (0-9): ");
			}

			int col = validInt("Nhập cột (0-8): ");
			while (col < 0 || col > 8) { // Kiểm tra xem cột có hợp lệ không
				System.out.println("Cột không hợp lệ! Vui lòng nhập lại (0-8).");
				col = validInt("Nhập cột (0-8): ");
			}

			if (board[row][col] != null && board[row][col].color == currentPlayer) {
				List<String> moves = getValidMoves(row, col);
				if (moves != null && !moves.isEmpty()) {
					System.out.println("\nCác nước đi hợp lệ:");
					for (String move : moves) {
						System.out.print(move + " ");
					}
					System.out.println("\n\n1. Đánh (nhập tọa độ mới để di chuyển)");
					System.out.println("2. Chọn quân cờ khác");
					System.out.print("Lựa chọn của bạn (1 hoặc 2): ");
					int choice = validInt("");
					if (choice == 1) {
						System.out.println("\nNhập vị trí cần đến:");
						int newRow = validInt("Nhập hàng mới (0-9): ");
						while (newRow < 0 || newRow > 9) { // Kiểm tra xem hàng có hợp lệ không
							System.out.println("Hàng không hợp lệ! Vui lòng nhập lại (0-9).");
							newRow = validInt("Nhập hàng mới (0-9): ");
						}
						int newCol = validInt("Nhập cột mới (0-8): ");
						while (newCol < 0 || newCol > 8) { // Kiểm tra xem cột có hợp lệ không
							System.out.println("Cột không hợp lệ! Vui lòng nhập lại (0-8).");
							newCol = validInt("Nhập cột mới (0-8): ");
						}
						String targetPosition = newRow + "," + newCol;
						if (moves.contains(targetPosition)) {
							// Lưu lại trạng thái bàn cờ trước khi di chuyển
						    Piece movedPiece = board[row][col];  // Quân cờ đã di chuyển
						    Piece capturedPiece = board[newRow][newCol];  // Quân cờ bị ăn (nếu có)
						    addMoveToHistory(row, col, newRow, newCol, movedPiece, capturedPiece);
						    
						    // Kiểm tra xem có quân bị ăn hay không
		                    if (board[newRow][newCol] != null) {
		                        moveWithoutCaptureCount = 0; // Reset nếu có quân bị ăn
		                    } else {
		                        moveWithoutCaptureCount++; // Tăng đếm nếu không có quân bị ăn
		                    }

		                    // Kiểm tra nếu vượt quá giới hạn 120 nước đi không có quân bị ăn
		                    if (moveWithoutCaptureCount >= MAX_MOVES_WITHOUT_CAPTURE) {
		                        System.out.println("Hòa cờ: Đã có 120 nước đi mà không có quân nào bị ăn.");
		                        break;
		                    }
		                    
						    // Tiến hành di chuyển quân cờ
						    board[newRow][newCol] = board[row][col];
							board[row][col] = null;
							printMoveHistory();
							System.out.println("\nBàn cờ sau khi di chuyển:");
							
							// Kiểm tra "Lộ mặt tướng" sau khi di chuyển
	                        if (isKingExposed()) {
	                            System.out.println("Lộ mặt tướng! Quy tắc bị vi phạm.");
	                            // Quay lại lượt đi trước đó, yêu cầu người chơi di chuyển lại
	                            undoMove();
	                            continue;
	                        }

						} else {
							System.out.println("\nVị trí cần đến không hợp lệ.");
							continue;
						}
					} else if (choice == 2) {
						System.out.println("\nChọn quân cờ khác.");
						continue; // Quay lại để chọn quân cờ khác
					} else {
						System.out.println("\nLựa chọn không hợp lệ.");
						continue;
					}

				} else {
					System.out.println("\nQuân cờ không có nước đi hợp lệ.");
					continue;
				}
			} else {
				System.out.println("Không có quân cờ của bạn ở vị trí này. Vui lòng chọn lại.");
				continue;
			}
			currentPlayer = (currentPlayer == Color.RED) ? Color.BLACK : Color.RED; // Đổi lượt
		}
		scanner.close();
	}

	

	private void resetBoard() {
		// Khởi tạo lại bàn cờ với các quân cờ ở vị trí ban đầu
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				board[i][j] = null;
			}
		}
		// Khởi tạo lại các quân cờ cho bên ĐỎ và ĐEN (tương tự như trong constructor)
		board[0][4] = new King(Color.RED);
		board[0][3] = new Advisor(Color.RED);
		board[0][5] = new Advisor(Color.RED);
		board[0][2] = new Elephant(Color.RED);
		board[0][6] = new Elephant(Color.RED);
		board[0][1] = new Horse(Color.RED);
		board[0][7] = new Horse(Color.RED);
		board[0][0] = new Rook(Color.RED);
		board[0][8] = new Rook(Color.RED);
		board[2][1] = new Cannon(Color.RED);
		board[2][7] = new Cannon(Color.RED);
		board[3][0] = new Pawn(Color.RED);
		board[3][2] = new Pawn(Color.RED);
		board[3][4] = new Pawn(Color.RED);
		board[3][6] = new Pawn(Color.RED);
		board[3][8] = new Pawn(Color.RED);

		board[9][4] = new King(Color.BLACK);
		board[9][3] = new Advisor(Color.BLACK);
		board[9][5] = new Advisor(Color.BLACK);
		board[9][2] = new Elephant(Color.BLACK);
		board[9][6] = new Elephant(Color.BLACK);
		board[9][1] = new Horse(Color.BLACK);
		board[9][7] = new Horse(Color.BLACK);
		board[9][0] = new Rook(Color.BLACK);
		board[9][8] = new Rook(Color.BLACK);
		board[7][1] = new Cannon(Color.BLACK);
		board[7][7] = new Cannon(Color.BLACK);
		board[6][0] = new Pawn(Color.BLACK);
		board[6][2] = new Pawn(Color.BLACK);
		board[6][4] = new Pawn(Color.BLACK);
		board[6][6] = new Pawn(Color.BLACK);
		board[6][8] = new Pawn(Color.BLACK);
	}
}
