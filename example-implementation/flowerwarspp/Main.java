package flowerwarspp;

import flowerwarspp.preset.*;
import flowerwarspp.boarddisplay.*;

public class Main {
	public static void main(String[] args) {
		Board board = new MyBoard(6);
		BoardDisplay boardDisplay = new BoardDisplay();
		boardDisplay.setViewer(board.viewer());
	}
}