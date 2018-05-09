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

        Flower w1, w2, w3, w4;
        w1 = new Flower(new Position(5, 5), new Position(6, 5), new Position(5,
                6));
        w2 = new Flower(new Position(4, 4), new Position(5, 4), new Position(4,
                5));
        w3 = new Flower(new Position(7, 4), new Position(8, 4), new Position(7,
                5));
        w4 = new Flower(new Position(4, 7), new Position(5, 7), new Position(4,
                8));

        // Green lands
        Flower r1, r2, r3, r4, r5, r6, r7, r8, r9, r10;
        r1 = new Flower(new Position(2, 2), new Position(3, 2), new Position(2,
                3));
        r2 = new Flower(new Position(3, 2), new Position(4, 2), new Position(3,
                3));
        r3 = new Flower(new Position(3, 2), new Position(2, 3), new Position(3,
                3));
        r4 = new Flower(new Position(2, 3), new Position(3, 3), new Position(2,
                4));
        r5 = new Flower(new Position(1, 5), new Position(2, 5), new Position(2,
                4));
        r6 = new Flower(new Position(2, 4), new Position(3, 4), new Position(2,
                5));
        r7 = new Flower(new Position(2, 4), new Position(3, 4), new Position(3,
                3));
        r8 = new Flower(new Position(3, 3), new Position(4, 3), new Position(3,
                4));
        r9 = new Flower(new Position(3, 3), new Position(4, 3), new Position(4,
                2));
        r10 = new Flower(new Position(4, 2), new Position(5, 2), new Position
                (4, 3));

        List<Move> moves = new LinkedList<>();

        // Set up test scenario
        moves.add(new Move(w1, w2));
        moves.add(new Move(r1, r2));
        moves.add(new Move(w3, w4));
        moves.add(new Move(r3, r4));
        moves.add(new Move(new Ditch(new Position(5, 5), new Position(4, 5)
        )));
        moves.add(new Move(r5, r6));
        moves.add(new Move(new Ditch(new Position(6, 5), new Position(7, 5)
        )));
        moves.add(new Move(r7, r8));
        moves.add(new Move(new Ditch(new Position(5, 6), new Position(4, 7)
        )));
        moves.add(new Move(r9, r10));

        Thread.sleep(1000);
        for (Move m : moves) {
            board.make(m);
            window.update();
            Thread.sleep(1000);
        }

    }
}
