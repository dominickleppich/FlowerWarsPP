package flowerwarspp;

import flowerwarspp.preset.*;
import flowerwarspp.boardviewer.*;

public class Main {
	public static void main(String[] args) {
		Board board = new MyBoard(6);
		BoardViewer boardViewer = new BoardViewer();
		boardViewer.setViewer(board.viewer());
	}
}