package flowerwarspp.test;

import flowerwarspp.board.*;
import flowerwarspp.preset.*;
import flowerwarspp.ui.graphical.*;

public class MiniGameTest {
    public static void main(String[] args) throws InterruptedException {
        Board board = new BoardImpl(10);
        Viewer viewer = board.viewer();
        UIWindow window = new UIWindow();
        window.setViewer(viewer);

        while (viewer.getStatus() == Status.Ok) {
            Move move = window.request();
            board.make(move);
            window.update(move);

//            Thread.sleep(3000);
        }

        window.dispose();
    }
}
