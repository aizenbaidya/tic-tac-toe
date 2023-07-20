import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

public class Main {
    public static final int BOARD_SIZE = 512;
    public static final int STATUS_BOX_SIZE = 64;

    public static void main(String[] args) {
        StdDraw.setTitle("Tic-Tac-Toe");
        StdDraw.setCanvasSize(BOARD_SIZE, BOARD_SIZE + STATUS_BOX_SIZE);
        StdDraw.setXscale(0, BOARD_SIZE);
        StdDraw.setYscale(BOARD_SIZE + STATUS_BOX_SIZE, 0);
        StdDraw.enableDoubleBuffering();
        StdDraw.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
        final Color BEIGE = new Color(245, 245, 220);
        TicTacToe ticTacToe = new TicTacToe();
        while (true) {
            if (ticTacToe.isGameOver()) {
                ticTacToe.drawStrike();
                StdDraw.show();
                StdDraw.pause(67);
                if (StdDraw.isKeyPressed(KeyEvent.VK_R)) {
                    ticTacToe = new TicTacToe();
                }
            } else {
                StdDraw.clear(BEIGE);
                ticTacToe.update();
                ticTacToe.draw();
                StdDraw.show();
                StdDraw.pause(67);
            }
        }
    }
}
