package flowerwarspp.test;

import flowerwarspp.board.*;
import flowerwarspp.preset.*;
import flowerwarspp.ui.graphical.*;

import java.util.*;

public class MiniGameTest {
    public static void main(String[] args) throws InterruptedException {
        Board board = new BoardImpl(10);
        Viewer viewer = board.viewer();
        UIWindow window = new UIWindow();
        window.setViewer(viewer);

        List<Move> moves = new LinkedList<Move>();

        while (viewer.getStatus() == Status.Ok) {
            Move move = window.request();
            board.make(move);
            window.update(move);

            if (viewer.getStatus() != Status.Illegal)
                moves.add(move);
            //            Thread.sleep(3000);
        }

        // Print code to replay game
        printReplayCode(moves);

        window.dispose();
    }

    private static void printReplayCode(List<Move> moves) {
        for (Move m : moves) {
            String newMoveString = "";
            switch (m.getType()) {
                case Flower:
                    newMoveString = newFlowerMoveString(m);
                    break;
                case Ditch:
                    newMoveString = newDitchMoveString(m);
                    break;
                case Surrender:
                    newMoveString = "new Move(MoveType.Surrender)";
                    break;
                case End:
                    newMoveString = "new Move(MoveType.End)";
                    break;
            }
            System.out.println("moves.add(" + newMoveString + ");");
        }
    }

    private static String newFlowerMoveString(Move move) {
        return "new Move(" + newFlowerString(move.getFirstFlower()) + ", " + newFlowerString(move.getSecondFlower()) +
               ")";
    }

    private static String newDitchMoveString(Move move) {
        return "new Move(" + newDitchString(move.getDitch()) + ")";
    }

    private static String newFlowerString(Flower flower) {
        return "new Flower(" + newPositionString(flower.getFirst()) + ", " + newPositionString(flower.getSecond()) +
               ", " + newPositionString(flower.getThird()) + ")";
    }

    private static String newDitchString(Ditch ditch) {
        return "new Ditch(" + newPositionString(ditch.getFirst()) + ", " + newPositionString(ditch.getSecond()) + ")";
    }

    private static String newPositionString(Position position) {
        return "new Position(" + position.getColumn() + ", " + position.getRow() + ")";
    }
}
