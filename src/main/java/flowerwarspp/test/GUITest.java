package flowerwarspp.test;

import flowerwarspp.board.*;
import flowerwarspp.preset.*;
import flowerwarspp.ui.graphical.*;

import java.util.*;

public class GUITest {
    public static void main(String[] args) throws InterruptedException {
        Board board = new BoardImpl(10);
        Viewer viewer = board.viewer();
        UIWindow window = new UIWindow();
        window.setViewer(viewer);
        int timeout = 500;

        List<Move> moves = new LinkedList<>();



        Thread.sleep(timeout);
        for (Move m : moves) {
            board.make(m);
            window.update(m);
            Thread.sleep(timeout);
        }

    }
}
