package flowerwarspp;

public class Main {
    public static void main(String[] args) {
        Board board = new MyBoard(6);
        Viewer viewer = board.viewer();
        BoardDisplay boardDisplay = new BoardDisplay();
        boardDisplay.setViewer(viewer);

        Move move = new Move(MoveType.Surrender);
        board.make(move);
        boardDisplay.update(move);
        boardDisplay.showStatus(viewer.getStatus());
    }
}
