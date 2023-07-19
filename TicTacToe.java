import java.util.HashMap;
import java.util.Map;

public class TicTacToe {
    private final int BOARD_CENTER = Main.BOARD_SIZE / 2;
    private final int CELL_SIZE = Main.BOARD_SIZE / 3;
    private final int CUT = 32;
    private final int IMAGE_SIZE = 128;
    private final int LINE_THICKNESS = 4;
    private final char PLAYER_O = 'O';
    private final char PLAYER_X = 'X';
    private final Map<String, int[]> WIN_COORDINATES = new HashMap<>();

    private char[][] board;
    private boolean isXTurn;
    private String winningCombination;
    private char winner;

    public TicTacToe() {
        board = new char[3][3];
        isXTurn = true;
        winningCombination = null;
        winner = Character.MIN_VALUE;
        setUpWinCoordinates();
    }

    private void setUpWinCoordinates() {
        final int CELL_HALF = CELL_SIZE / 2;
        final int BOARD_CUT = Main.BOARD_SIZE - CUT;
        final int HALF_PLUS_SIZE = CELL_HALF + CELL_SIZE;
        final int HALF_PLUS_TWO = CELL_HALF + CELL_SIZE * 2;
        WIN_COORDINATES.put("ROW_1", new int[] {CUT, CELL_HALF, BOARD_CUT, CELL_HALF});
        WIN_COORDINATES.put("ROW_2", new int[] {CUT, HALF_PLUS_SIZE, BOARD_CUT, HALF_PLUS_SIZE});
        WIN_COORDINATES.put("ROW_3", new int[] {CUT, HALF_PLUS_TWO, BOARD_CUT, HALF_PLUS_TWO});
        WIN_COORDINATES.put("COL_1", new int[] {CELL_HALF, CUT, CELL_HALF, BOARD_CUT});
        WIN_COORDINATES.put("COL_2", new int[] {HALF_PLUS_SIZE, CUT, HALF_PLUS_SIZE, BOARD_CUT});
        WIN_COORDINATES.put("COL_3", new int[] {HALF_PLUS_TWO, CUT, HALF_PLUS_TWO, BOARD_CUT});
        WIN_COORDINATES.put("DIAGONAL_1", new int[] {CUT, CUT, BOARD_CUT, BOARD_CUT});
        WIN_COORDINATES.put("DIAGONAL_2", new int[] {BOARD_CUT, CUT, CUT, BOARD_CUT});
    }

    public void drawStrike() {
        if (winningCombination != "TIE") {
            StdDraw.setPenColor(StdDraw.GREEN);
            StdDraw.setPenRadius(0.01);
            int[] coordinates = WIN_COORDINATES.get(winningCombination);
            StdDraw.line(coordinates[0], coordinates[1], coordinates[2], coordinates[3]);
        }
    }

    public void update() {
        if (StdDraw.isMousePressed()) {
            int row = (int) StdDraw.mouseY() / CELL_SIZE;
            int col = (int) StdDraw.mouseX() / CELL_SIZE;
            if (isEmpty(row, col)) {
                board[row][col] = isXTurn ? PLAYER_X : PLAYER_O;
                isXTurn = !isXTurn;
            }
        }
        winningCombination = checkWin();
    }

    private String checkWin() {
        for (int i = 0; i < board.length; i++) {
            if (!isEmpty(i, 0) && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                winner = board[i][0];
                return "ROW_" + (i + 1);
            } else if (!isEmpty(0, i) && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                winner = board[0][i];
                return "COL_" + (i + 1);
            }
        }
        if (!isEmpty(0, 0) && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            winner = board[0][0];
            return "DIAGONAL_1";
        } else if (!isEmpty(0, 2) && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            winner = board[0][2];
            return "DIAGONAL_2";
        }
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (isEmpty(row, col)) {
                    return null;
                }
            }
        }
        return "TIE";
    }

    private boolean isEmpty(int row, int col) {
        return board[row][col] == Character.MIN_VALUE;
    }

    public void draw() {
        drawGrid();
        drawPieces();
        drawStatus();
    }

    private void drawGrid() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledRectangle(BOARD_CENTER, CELL_SIZE, BOARD_CENTER, LINE_THICKNESS / 2);
        StdDraw.filledRectangle(BOARD_CENTER, CELL_SIZE * 2, BOARD_CENTER, LINE_THICKNESS / 2);
        StdDraw.filledRectangle(CELL_SIZE, BOARD_CENTER, LINE_THICKNESS / 2, BOARD_CENTER);
        StdDraw.filledRectangle(CELL_SIZE * 2, BOARD_CENTER, LINE_THICKNESS / 2, BOARD_CENTER);
        StdDraw.filledRectangle(BOARD_CENTER, Main.BOARD_SIZE, BOARD_CENTER, LINE_THICKNESS / 2);
    }

    private void drawPieces() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                int x = col * CELL_SIZE + CELL_SIZE / 2;
                int y = row * CELL_SIZE + CELL_SIZE / 2;
                if (board[row][col] == PLAYER_X) {
                    StdDraw.picture(x, y, "X.png", IMAGE_SIZE, IMAGE_SIZE);
                } else if (board[row][col] == PLAYER_O) {
                    StdDraw.picture(x, y, "O.png", IMAGE_SIZE, IMAGE_SIZE);
                }
            }
        }
    }

    private void drawStatus() {
        final int HALF_BOX = Main.STATUS_BOX_SIZE / 2;
        final int BOARD_AND_HALF_BOX = Main.BOARD_SIZE + HALF_BOX;
        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        StdDraw.filledRectangle(BOARD_CENTER, BOARD_AND_HALF_BOX, BOARD_CENTER, HALF_BOX);
        if (isGameOver()) {
            if (winner == PLAYER_X) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.text(BOARD_CENTER, BOARD_AND_HALF_BOX, "X Wins!");
            } else if (winner == PLAYER_O) {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.text(BOARD_CENTER, BOARD_AND_HALF_BOX, "O Wins!");
            } else {
                StdDraw.setPenColor(StdDraw.YELLOW);
                StdDraw.text(BOARD_CENTER, BOARD_AND_HALF_BOX, "Tie!");
            }
        } else {
            if (isXTurn) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.text(BOARD_CENTER, BOARD_AND_HALF_BOX, "X's Turn!");
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.text(BOARD_CENTER, BOARD_AND_HALF_BOX, "O's Turn!");
            }
        }
    }

    public boolean isGameOver() {
        return winningCombination != null;
    }
}
