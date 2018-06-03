package board;

import flowerwarspp.preset.*;
import org.junit.*;

import java.util.*;

import static org.junit.Assert.*;

public class BoardWinSituationTest {
    private Board board;
    private Viewer viewer;

    public void init(int size) {
        board = TestBoardFactory.createInstance(size);
        viewer = board.viewer();
    }

    private void setupBoardState(List<Move> moves) {
        for (Move m : moves)
            board.make(m);
    }

    // ------------------------------------------------------------
    // * Only garden tests *

    @Test
    public void redPlayerWinsWithMoreGardens() {
        init(5);

        List<Move> replayMoves = new LinkedList<>();
        replayMoves.add(new Move(new Flower(new Position(1, 1), new Position(2, 1), new Position(1, 2)),
                new Flower(new Position(2, 1), new Position(1, 2), new Position(2, 2))));
        replayMoves.add(new Move(new Flower(new Position(5, 1), new Position(4, 2), new Position(5, 2)),
                new Flower(new Position(4, 2), new Position(5, 2), new Position(4, 3))));
        replayMoves.add(new Move(new Flower(new Position(2, 1), new Position(3, 1), new Position(2, 2)),
                new Flower(new Position(1, 2), new Position(2, 2), new Position(1, 3))));
        replayMoves.add(new Move(new Flower(new Position(4, 1), new Position(5, 1), new Position(4, 2)),
                new Flower(new Position(5, 1), new Position(6, 1), new Position(5, 2))));
        replayMoves.add(new Move(new Flower(new Position(2, 3), new Position(1, 4), new Position(2, 4)),
                new Flower(new Position(2, 3), new Position(3, 3), new Position(2, 4))));
        replayMoves.add(new Move(new Flower(new Position(3, 1), new Position(2, 2), new Position(3, 2)),
                new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3))));
        replayMoves.add(new Move(new Flower(new Position(1, 4), new Position(2, 4), new Position(1, 5)),
                new Flower(new Position(2, 4), new Position(1, 5), new Position(2, 5))));
        replayMoves.add(new Move(new Flower(new Position(3, 2), new Position(2, 3), new Position(3, 3)),
                new Flower(new Position(3, 3), new Position(2, 4), new Position(3, 4))));
        replayMoves.add(new Move(MoveType.End));

        setupBoardState(replayMoves);

        assertEquals(Status.RedWin, viewer.getStatus());
    }

    @Test
    public void bluePlayerWinsWithMoreGardens() {
        init(5);

        List<Move> replayMoves = new LinkedList<>();
        replayMoves.add(new Move(new Flower(new Position(1, 1), new Position(2, 1), new Position(1, 2)),
                new Flower(new Position(2, 1), new Position(3, 1), new Position(2, 2))));
        replayMoves.add(new Move(new Flower(new Position(5, 1), new Position(4, 2), new Position(5, 2)),
                new Flower(new Position(5, 1), new Position(6, 1), new Position(5, 2))));
        replayMoves.add(new Move(new Flower(new Position(2, 1), new Position(1, 2), new Position(2, 2)),
                new Flower(new Position(1, 2), new Position(2, 2), new Position(1, 3))));
        replayMoves.add(new Move(new Flower(new Position(4, 1), new Position(5, 1), new Position(4, 2)),
                new Flower(new Position(4, 2), new Position(5, 2), new Position(4, 3))));
        replayMoves.add(new Move(new Flower(new Position(2, 3), new Position(1, 4), new Position(2, 4)),
                new Flower(new Position(1, 4), new Position(2, 4), new Position(1, 5))));
        replayMoves.add(new Move(new Flower(new Position(2, 3), new Position(3, 3), new Position(2, 4)),
                new Flower(new Position(3, 3), new Position(2, 4), new Position(3, 4))));
        replayMoves.add(new Move(new Flower(new Position(4, 1), new Position(3, 2), new Position(4, 2)),
                new Flower(new Position(2, 4), new Position(1, 5), new Position(2, 5))));
        replayMoves.add(new Move(new Flower(new Position(3, 2), new Position(2, 3), new Position(3, 3)),
                new Flower(new Position(2, 4), new Position(3, 4), new Position(2, 5))));
        replayMoves.add(new Move(new Flower(new Position(3, 2), new Position(4, 2), new Position(3, 3)),
                new Flower(new Position(4, 2), new Position(3, 3), new Position(4, 3))));
        replayMoves.add(new Move(MoveType.End));

        setupBoardState(replayMoves);

        assertEquals(Status.BlueWin, viewer.getStatus());
    }

    @Test
    public void drawSituationWithGardens() {
        init(5);

        List<Move> replayMoves = new LinkedList<>();
        replayMoves.add(new Move(new Flower(new Position(1, 1), new Position(2, 1), new Position(1, 2)),
                new Flower(new Position(2, 1), new Position(1, 2), new Position(2, 2))));
        replayMoves.add(new Move(new Flower(new Position(5, 1), new Position(4, 2), new Position(5, 2)),
                new Flower(new Position(5, 1), new Position(6, 1), new Position(5, 2))));
        replayMoves.add(new Move(new Flower(new Position(1, 2), new Position(2, 2), new Position(1, 3)),
                new Flower(new Position(2, 2), new Position(1, 3), new Position(2, 3))));
        replayMoves.add(new Move(new Flower(new Position(4, 1), new Position(5, 1), new Position(4, 2)),
                new Flower(new Position(4, 2), new Position(5, 2), new Position(4, 3))));
        replayMoves.add(new Move(new Flower(new Position(1, 4), new Position(2, 4), new Position(1, 5)),
                new Flower(new Position(2, 4), new Position(1, 5), new Position(2, 5))));
        replayMoves.add(new Move(new Flower(new Position(2, 1), new Position(3, 1), new Position(2, 2)),
                new Flower(new Position(3, 1), new Position(2, 2), new Position(3, 2))));
        replayMoves.add(new Move(new Flower(new Position(2, 4), new Position(3, 4), new Position(2, 5)),
                new Flower(new Position(1, 5), new Position(2, 5), new Position(1, 6))));
        replayMoves.add(new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                new Flower(new Position(3, 2), new Position(2, 3), new Position(3, 3))));
        replayMoves.add(new Move(new Flower(new Position(4, 1), new Position(3, 2), new Position(4, 2)),
                new Flower(new Position(3, 2), new Position(4, 2), new Position(3, 3))));
        replayMoves.add(new Move(MoveType.End));

        setupBoardState(replayMoves);

        assertEquals(Status.Draw, viewer.getStatus());
    }

    // ------------------------------------------------------------
    // * Garden ditch tests *

    @Test
    public void redPlayerWinsWithMoreConnectedGardens() {
        init(6);

        List<Move> replayMoves = new LinkedList<>();
        replayMoves.add(new Move(new Flower(new Position(1, 1), new Position(2, 1), new Position(1, 2)),
                new Flower(new Position(2, 1), new Position(1, 2), new Position(2, 2))));
        replayMoves.add(new Move(new Flower(new Position(6, 1), new Position(5, 2), new Position(6, 2)),
                new Flower(new Position(6, 1), new Position(7, 1), new Position(6, 2))));
        replayMoves.add(new Move(new Flower(new Position(1, 2), new Position(2, 2), new Position(1, 3)),
                new Flower(new Position(2, 2), new Position(1, 3), new Position(2, 3))));
        replayMoves.add(new Move(new Flower(new Position(5, 1), new Position(6, 1), new Position(5, 2)),
                new Flower(new Position(5, 2), new Position(6, 2), new Position(5, 3))));
        replayMoves.add(new Move(new Flower(new Position(1, 4), new Position(2, 4), new Position(1, 5)),
                new Flower(new Position(2, 4), new Position(1, 5), new Position(2, 5))));
        replayMoves.add(new Move(new Flower(new Position(3, 3), new Position(4, 3), new Position(3, 4)),
                new Flower(new Position(4, 3), new Position(3, 4), new Position(4, 4))));
        replayMoves.add(new Move(new Flower(new Position(2, 4), new Position(3, 4), new Position(2, 5)),
                new Flower(new Position(3, 4), new Position(2, 5), new Position(3, 5))));
        replayMoves.add(new Move(new Flower(new Position(3, 2), new Position(4, 2), new Position(3, 3)),
                new Flower(new Position(4, 2), new Position(3, 3), new Position(4, 3))));
        replayMoves.add(new Move(new Flower(new Position(4, 3), new Position(5, 3), new Position(4, 4)),
                new Flower(new Position(1, 6), new Position(2, 6), new Position(1, 7))));
        replayMoves.add(new Move(new Flower(new Position(2, 1), new Position(3, 1), new Position(2, 2)),
                new Flower(new Position(1, 5), new Position(2, 5), new Position(1, 6))));
        replayMoves.add(new Move(new Ditch(new Position(2, 3), new Position(2, 4))));
        replayMoves.add(new Move(new Flower(new Position(2, 5), new Position(1, 6), new Position(2, 6)),
                new Flower(new Position(2, 5), new Position(3, 5), new Position(2, 6))));
        replayMoves.add(new Move(new Flower(new Position(4, 1), new Position(5, 1), new Position(4, 2)),
                new Flower(new Position(5, 1), new Position(4, 2), new Position(5, 2))));
        replayMoves.add(new Move(MoveType.End));

        setupBoardState(replayMoves);

        assertEquals(Status.RedWin, viewer.getStatus());
    }

    @Test
    public void bluePlayerWinsWithMoreConnectedGardens() {
        init(6);

        List<Move> replayMoves = new LinkedList<>();
        replayMoves.add(new Move(new Flower(new Position(1, 1), new Position(2, 1), new Position(1, 2)),
                new Flower(new Position(2, 1), new Position(1, 2), new Position(2, 2))));
        replayMoves.add(new Move(new Flower(new Position(6, 1), new Position(5, 2), new Position(6, 2)),
                new Flower(new Position(6, 1), new Position(7, 1), new Position(6, 2))));
        replayMoves.add(new Move(new Flower(new Position(2, 1), new Position(3, 1), new Position(2, 2)),
                new Flower(new Position(1, 2), new Position(2, 2), new Position(1, 3))));
        replayMoves.add(new Move(new Flower(new Position(5, 2), new Position(4, 3), new Position(5, 3)),
                new Flower(new Position(5, 2), new Position(6, 2), new Position(5, 3))));
        replayMoves.add(new Move(new Flower(new Position(2, 3), new Position(1, 4), new Position(2, 4)),
                new Flower(new Position(1, 4), new Position(2, 4), new Position(1, 5))));
        replayMoves.add(new Move(new Flower(new Position(4, 1), new Position(3, 2), new Position(4, 2)),
                new Flower(new Position(4, 1), new Position(5, 1), new Position(4, 2))));
        replayMoves.add(new Move(new Flower(new Position(2, 3), new Position(3, 3), new Position(2, 4)),
                new Flower(new Position(3, 3), new Position(2, 4), new Position(3, 4))));
        replayMoves.add(new Move(new Flower(new Position(3, 2), new Position(2, 3), new Position(3, 3)),
                new Flower(new Position(3, 2), new Position(4, 2), new Position(3, 3))));
        replayMoves.add(new Move(new Flower(new Position(2, 5), new Position(1, 6), new Position(2, 6)),
                new Flower(new Position(2, 5), new Position(3, 5), new Position(2, 6))));
        replayMoves.add(new Move(new Flower(new Position(3, 4), new Position(2, 5), new Position(3, 5)),
                new Flower(new Position(3, 4), new Position(4, 4), new Position(3, 5))));
        replayMoves.add(new Move(new Flower(new Position(4, 2), new Position(5, 2), new Position(4, 3)),
                new Flower(new Position(4, 3), new Position(5, 3), new Position(4, 4))));
        replayMoves.add(new Move(new Ditch(new Position(5, 1), new Position(5, 2))));
        replayMoves.add(new Move(MoveType.End));

        setupBoardState(replayMoves);

        assertEquals(Status.BlueWin, viewer.getStatus());
    }

    @Test
    public void drawSituationWithConnectedGardens() {
        init(6);

        List<Move> replayMoves = new LinkedList<>();
        replayMoves.add(new Move(new Flower(new Position(1, 1), new Position(2, 1), new Position(1, 2)),
                new Flower(new Position(2, 1), new Position(1, 2), new Position(2, 2))));
        replayMoves.add(new Move(new Flower(new Position(6, 1), new Position(5, 2), new Position(6, 2)),
                new Flower(new Position(6, 1), new Position(7, 1), new Position(6, 2))));
        replayMoves.add(new Move(new Flower(new Position(2, 1), new Position(3, 1), new Position(2, 2)),
                new Flower(new Position(1, 2), new Position(2, 2), new Position(1, 3))));
        replayMoves.add(new Move(new Flower(new Position(5, 2), new Position(4, 3), new Position(5, 3)),
                new Flower(new Position(5, 2), new Position(6, 2), new Position(5, 3))));
        replayMoves.add(new Move(new Flower(new Position(2, 3), new Position(1, 4), new Position(2, 4)),
                new Flower(new Position(1, 4), new Position(2, 4), new Position(1, 5))));
        replayMoves.add(new Move(new Flower(new Position(4, 1), new Position(3, 2), new Position(4, 2)),
                new Flower(new Position(4, 1), new Position(5, 1), new Position(4, 2))));
        replayMoves.add(new Move(new Flower(new Position(2, 4), new Position(1, 5), new Position(2, 5)),
                new Flower(new Position(2, 4), new Position(3, 4), new Position(2, 5))));
        replayMoves.add(new Move(new Flower(new Position(3, 1), new Position(4, 1), new Position(3, 2)),
                new Flower(new Position(3, 2), new Position(4, 2), new Position(3, 3))));
        replayMoves.add(new Move(new Ditch(new Position(1, 3), new Position(2, 3))));
        replayMoves.add(new Move(new Ditch(new Position(4, 2), new Position(4, 3))));
        replayMoves.add(new Move(new Flower(new Position(4, 3), new Position(5, 3), new Position(4, 4)),
                new Flower(new Position(1, 6), new Position(2, 6), new Position(1, 7))));
        replayMoves.add(new Move(new Flower(new Position(3, 4), new Position(2, 5), new Position(3, 5)),
                new Flower(new Position(2, 5), new Position(3, 5), new Position(2, 6))));
        replayMoves.add(new Move(new Flower(new Position(5, 1), new Position(4, 2), new Position(5, 2)),
                new Flower(new Position(5, 1), new Position(6, 1), new Position(5, 2))));
        replayMoves.add(new Move(new Flower(new Position(3, 4), new Position(4, 4), new Position(3, 5)),
                new Flower(new Position(1, 5), new Position(2, 5), new Position(1, 6))));

        setupBoardState(replayMoves);

        assertEquals(Status.Draw, viewer.getStatus());
    }

    // ------------------------------------------------------------
    // * More complex test situation *

    @Test
    public void redPlayerWinsByMostPointsInComplexGame() {
        init(10);

        List<Move> replayMoves = new LinkedList<>();
        replayMoves.add(new Move(new Flower(new Position(2, 1), new Position(1, 2), new Position(2, 2)),
                new Flower(new Position(1, 2), new Position(2, 2), new Position(1, 3))));
        replayMoves.add(new Move(new Flower(new Position(6, 3), new Position(5, 4), new Position(6, 4)),
                new Flower(new Position(5, 4), new Position(6, 4), new Position(5, 5))));
        replayMoves.add(new Move(new Flower(new Position(2, 2), new Position(1, 3), new Position(2, 3)),
                new Flower(new Position(1, 3), new Position(2, 3), new Position(1, 4))));
        replayMoves.add(new Move(new Flower(new Position(6, 3), new Position(7, 3), new Position(6, 4)),
                new Flower(new Position(7, 3), new Position(6, 4), new Position(7, 4))));
        replayMoves.add(new Move(new Flower(new Position(3, 3), new Position(2, 4), new Position(3, 4)),
                new Flower(new Position(2, 4), new Position(3, 4), new Position(2, 5))));
        replayMoves.add(new Move(new Flower(new Position(6, 5), new Position(5, 6), new Position(6, 6)),
                new Flower(new Position(6, 5), new Position(7, 5), new Position(6, 6))));
        replayMoves.add(new Move(new Flower(new Position(2, 4), new Position(1, 5), new Position(2, 5)),
                new Flower(new Position(3, 4), new Position(2, 5), new Position(3, 5))));
        replayMoves.add(new Move(new Flower(new Position(5, 6), new Position(4, 7), new Position(5, 7)),
                new Flower(new Position(5, 6), new Position(6, 6), new Position(5, 7))));
        replayMoves.add(new Move(new Flower(new Position(5, 2), new Position(4, 3), new Position(5, 3)),
                new Flower(new Position(4, 3), new Position(5, 3), new Position(4, 4))));
        replayMoves.add(new Move(new Flower(new Position(4, 5), new Position(3, 6), new Position(4, 6)),
                new Flower(new Position(3, 6), new Position(4, 6), new Position(3, 7))));
        replayMoves.add(new Move(new Flower(new Position(4, 2), new Position(5, 2), new Position(4, 3)),
                new Flower(new Position(5, 2), new Position(6, 2), new Position(5, 3))));
        replayMoves.add(new Move(new Flower(new Position(2, 6), new Position(3, 6), new Position(2, 7)),
                new Flower(new Position(3, 6), new Position(2, 7), new Position(3, 7))));
        replayMoves.add(new Move(new Flower(new Position(3, 1), new Position(4, 1), new Position(3, 2)),
                new Flower(new Position(7, 1), new Position(8, 1), new Position(7, 2))));
        replayMoves.add(new Move(new Ditch(new Position(3, 7), new Position(4, 7))));
        replayMoves.add(new Move(new Flower(new Position(8, 1), new Position(7, 2), new Position(8, 2)),
                new Flower(new Position(8, 1), new Position(9, 1), new Position(8, 2))));
        replayMoves.add(new Move(new Flower(new Position(3, 8), new Position(2, 9), new Position(3, 9)),
                new Flower(new Position(3, 8), new Position(4, 8), new Position(3, 9))));
        replayMoves.add(new Move(new Ditch(new Position(3, 1), new Position(2, 2))));
        replayMoves.add(new Move(new Flower(new Position(9, 2), new Position(8, 3), new Position(9, 3)),
                new Flower(new Position(8, 3), new Position(9, 3), new Position(8, 4))));
        replayMoves.add(new Move(new Ditch(new Position(4, 1), new Position(4, 2))));
        replayMoves.add(new Move(new Flower(new Position(2, 8), new Position(3, 8), new Position(2, 9)),
                new Flower(new Position(2, 9), new Position(3, 9), new Position(2, 10))));
        replayMoves.add(new Move(new Ditch(new Position(1, 4), new Position(1, 5))));
        replayMoves.add(new Move(new Flower(new Position(8, 2), new Position(9, 2), new Position(8, 3)),
                new Flower(new Position(9, 2), new Position(10, 2), new Position(9, 3))));
        replayMoves.add(new Move(new Ditch(new Position(3, 4), new Position(4, 4))));
        replayMoves.add(new Move(new Flower(new Position(3, 2), new Position(2, 3), new Position(3, 3)),
                new Flower(new Position(3, 2), new Position(4, 2), new Position(3, 3))));
        replayMoves.add(new Move(new Flower(new Position(9, 1), new Position(8, 2), new Position(9, 2)),
                new Flower(new Position(7, 3), new Position(8, 3), new Position(7, 4))));
        replayMoves.add(new Move(new Ditch(new Position(6, 4), new Position(6, 5))));
        replayMoves.add(new Move(new Ditch(new Position(6, 2), new Position(7, 2))));
        replayMoves.add(new Move(new Flower(new Position(4, 2), new Position(3, 3), new Position(4, 3)),
                new Flower(new Position(3, 3), new Position(4, 3), new Position(3, 4))));
        replayMoves.add(new Move(new Flower(new Position(1, 7), new Position(2, 7), new Position(1, 8)),
                new Flower(new Position(2, 7), new Position(1, 8), new Position(2, 8))));
        replayMoves.add(new Move(new Flower(new Position(5, 1), new Position(6, 1), new Position(5, 2)),
                new Flower(new Position(6, 1), new Position(5, 2), new Position(6, 2))));
        replayMoves.add(new Move(new Flower(new Position(1, 6), new Position(2, 6), new Position(1, 7)),
                new Flower(new Position(2, 6), new Position(1, 7), new Position(2, 7))));
        replayMoves.add(new Move(new Flower(new Position(6, 1), new Position(7, 1), new Position(6, 2)),
                new Flower(new Position(1, 5), new Position(2, 5), new Position(1, 6))));
        replayMoves.add(new Move(new Flower(new Position(5, 5), new Position(4, 6), new Position(5, 6)),
                new Flower(new Position(4, 6), new Position(5, 6), new Position(4, 7))));
        replayMoves.add(new Move(MoveType.End));

        setupBoardState(replayMoves);

        assertEquals(Status.RedWin, viewer.getStatus());
    }

    @Test
    public void bluePlayerWinsByMostPointsInComplexGame() {
        init(10);

        List<Move> replayMoves = new LinkedList<>();
        replayMoves.add(new Move(new Flower(new Position(1, 1), new Position(2, 1), new Position(1, 2)),
                new Flower(new Position(2, 1), new Position(1, 2), new Position(2, 2))));
        replayMoves.add(new Move(new Flower(new Position(4, 4), new Position(5, 4), new Position(4, 5)),
                new Flower(new Position(4, 5), new Position(3, 6), new Position(4, 6))));
        replayMoves.add(new Move(new Flower(new Position(2, 1), new Position(3, 1), new Position(2, 2)),
                new Flower(new Position(1, 2), new Position(2, 2), new Position(1, 3))));
        replayMoves.add(new Move(new Flower(new Position(4, 4), new Position(3, 5), new Position(4, 5)),
                new Flower(new Position(3, 5), new Position(4, 5), new Position(3, 6))));
        replayMoves.add(new Move(new Flower(new Position(9, 1), new Position(10, 1), new Position(9, 2)),
                new Flower(new Position(10, 1), new Position(9, 2), new Position(10, 2))));
        replayMoves.add(new Move(new Flower(new Position(6, 4), new Position(5, 5), new Position(6, 5)),
                new Flower(new Position(5, 5), new Position(6, 5), new Position(5, 6))));
        replayMoves.add(new Move(new Flower(new Position(10, 1), new Position(11, 1), new Position(10, 2)),
                new Flower(new Position(9, 2), new Position(10, 2), new Position(9, 3))));
        replayMoves.add(new Move(new Flower(new Position(6, 4), new Position(7, 4), new Position(6, 5)),
                new Flower(new Position(6, 5), new Position(5, 6), new Position(6, 6))));
        replayMoves.add(new Move(new Flower(new Position(1, 9), new Position(2, 9), new Position(1, 10)),
                new Flower(new Position(2, 9), new Position(1, 10), new Position(2, 10))));
        replayMoves.add(new Move(new Flower(new Position(9, 2), new Position(8, 3), new Position(9, 3)),
                new Flower(new Position(8, 3), new Position(9, 3), new Position(8, 4))));
        replayMoves.add(new Move(new Flower(new Position(2, 9), new Position(3, 9), new Position(2, 10)),
                new Flower(new Position(1, 10), new Position(2, 10), new Position(1, 11))));
        replayMoves.add(new Move(new Flower(new Position(8, 2), new Position(7, 3), new Position(8, 3)),
                new Flower(new Position(8, 2), new Position(9, 2), new Position(8, 3))));
        replayMoves.add(new Move(new Flower(new Position(2, 7), new Position(1, 8), new Position(2, 8)),
                new Flower(new Position(3, 7), new Position(2, 8), new Position(3, 8))));
        replayMoves.add(new Move(new Flower(new Position(6, 2), new Position(5, 3), new Position(6, 3)),
                new Flower(new Position(6, 2), new Position(7, 2), new Position(6, 3))));
        replayMoves.add(new Move(new Flower(new Position(2, 7), new Position(3, 7), new Position(2, 8)),
                new Flower(new Position(3, 7), new Position(4, 7), new Position(3, 8))));
        replayMoves.add(new Move(new Flower(new Position(7, 1), new Position(6, 2), new Position(7, 2)),
                new Flower(new Position(5, 2), new Position(6, 2), new Position(5, 3))));
        replayMoves.add(new Move(new Flower(new Position(2, 5), new Position(1, 6), new Position(2, 6)),
                new Flower(new Position(1, 6), new Position(2, 6), new Position(1, 7))));
        replayMoves.add(new Move(new Flower(new Position(3, 2), new Position(4, 2), new Position(3, 3)),
                new Flower(new Position(4, 2), new Position(3, 3), new Position(4, 3))));
        replayMoves.add(new Move(new Flower(new Position(2, 4), new Position(1, 5), new Position(2, 5)),
                new Flower(new Position(1, 5), new Position(2, 5), new Position(1, 6))));
        replayMoves.add(new Move(new Flower(new Position(4, 1), new Position(3, 2), new Position(4, 2)),
                new Flower(new Position(4, 1), new Position(5, 1), new Position(4, 2))));
        replayMoves.add(new Move(new Flower(new Position(3, 2), new Position(2, 3), new Position(3, 3)),
                new Flower(new Position(5, 6), new Position(6, 6), new Position(5, 7))));
        replayMoves.add(new Move(new Ditch(new Position(5, 5), new Position(4, 6))));
        replayMoves.add(new Move(new Ditch(new Position(2, 8), new Position(2, 9))));
        replayMoves.add(new Move(new Ditch(new Position(8, 3), new Position(7, 4))));
        replayMoves.add(new Move(new Ditch(new Position(1, 7), new Position(2, 7))));
        replayMoves.add(new Move(new Ditch(new Position(7, 2), new Position(7, 3))));
        replayMoves.add(new Move(new Ditch(new Position(2, 3), new Position(2, 4))));
        replayMoves.add(new Move(new Ditch(new Position(4, 2), new Position(5, 2))));
        replayMoves.add(new Move(new Ditch(new Position(2, 2), new Position(3, 2))));
        replayMoves.add(new Move(new Flower(new Position(4, 7), new Position(3, 8), new Position(4, 8)),
                new Flower(new Position(4, 7), new Position(5, 7), new Position(4, 8))));
        replayMoves.add(new Move(new Flower(new Position(5, 3), new Position(4, 4), new Position(5, 4)),
                new Flower(new Position(5, 3), new Position(6, 3), new Position(5, 4))));
        replayMoves.add(new Move(new Flower(new Position(1, 8), new Position(2, 8), new Position(1, 9)),
                new Flower(new Position(3, 8), new Position(4, 8), new Position(3, 9))));
        replayMoves.add(new Move(new Flower(new Position(6, 3), new Position(5, 4), new Position(6, 4)),
                new Flower(new Position(6, 3), new Position(7, 3), new Position(6, 4))));
        replayMoves.add(new Move(new Flower(new Position(2, 4), new Position(3, 4), new Position(2, 5)),
                new Flower(new Position(3, 8), new Position(2, 9), new Position(3, 9))));
        replayMoves.add(new Move(new Flower(new Position(5, 1), new Position(6, 1), new Position(5, 2)),
                new Flower(new Position(6, 1), new Position(5, 2), new Position(6, 2))));
        replayMoves.add(new Move(new Flower(new Position(2, 2), new Position(1, 3), new Position(2, 3)),
                new Flower(new Position(1, 3), new Position(2, 3), new Position(1, 4))));
        replayMoves.add(new Move(new Flower(new Position(6, 1), new Position(7, 1), new Position(6, 2)),
                new Flower(new Position(7, 1), new Position(8, 1), new Position(7, 2))));
        replayMoves.add(new Move(MoveType.End));

        setupBoardState(replayMoves);

        assertEquals(Status.BlueWin, viewer.getStatus());
    }

    @Test
    public void drawSituationByMostPointsInComplexGame() {
        init(10);

        List<Move> replayMoves = new LinkedList<>();
        replayMoves.add(new Move(new Flower(new Position(4, 4), new Position(3, 5), new Position(4, 5)),
                new Flower(new Position(3, 5), new Position(4, 5), new Position(3, 6))));
        replayMoves.add(new Move(new Flower(new Position(7, 4), new Position(6, 5), new Position(7, 5)),
                new Flower(new Position(6, 5), new Position(7, 5), new Position(6, 6))));
        replayMoves.add(new Move(new Flower(new Position(4, 4), new Position(5, 4), new Position(4, 5)),
                new Flower(new Position(4, 5), new Position(3, 6), new Position(4, 6))));
        replayMoves.add(new Move(new Flower(new Position(6, 5), new Position(5, 6), new Position(6, 6)),
                new Flower(new Position(5, 6), new Position(6, 6), new Position(5, 7))));
        replayMoves.add(new Move(new Flower(new Position(6, 4), new Position(5, 5), new Position(6, 5)),
                new Flower(new Position(5, 5), new Position(6, 5), new Position(5, 6))));
        replayMoves.add(new Move(new Flower(new Position(9, 2), new Position(8, 3), new Position(9, 3)),
                new Flower(new Position(8, 3), new Position(9, 3), new Position(8, 4))));
        replayMoves.add(new Move(new Flower(new Position(7, 3), new Position(6, 4), new Position(7, 4)),
                new Flower(new Position(6, 4), new Position(7, 4), new Position(6, 5))));
        replayMoves.add(new Move(new Flower(new Position(8, 2), new Position(7, 3), new Position(8, 3)),
                new Flower(new Position(8, 2), new Position(9, 2), new Position(8, 3))));
        replayMoves.add(new Move(new Flower(new Position(5, 2), new Position(6, 2), new Position(5, 3)),
                new Flower(new Position(6, 2), new Position(5, 3), new Position(6, 3))));
        replayMoves.add(new Move(new Ditch(new Position(7, 4), new Position(8, 4))));
        replayMoves.add(new Move(new Flower(new Position(7, 1), new Position(6, 2), new Position(7, 2)),
                new Flower(new Position(6, 2), new Position(7, 2), new Position(6, 3))));
        replayMoves.add(new Move(new Flower(new Position(6, 1), new Position(5, 2), new Position(6, 2)),
                new Flower(new Position(6, 1), new Position(7, 1), new Position(6, 2))));
        replayMoves.add(new Move(new Ditch(new Position(6, 3), new Position(7, 3))));
        replayMoves.add(new Move(new Flower(new Position(5, 1), new Position(4, 2), new Position(5, 2)),
                new Flower(new Position(5, 1), new Position(6, 1), new Position(5, 2))));
        replayMoves.add(new Move(new Ditch(new Position(5, 4), new Position(5, 5))));
        replayMoves.add(new Move(new Flower(new Position(3, 1), new Position(2, 2), new Position(3, 2)),
                new Flower(new Position(3, 1), new Position(4, 1), new Position(3, 2))));
        replayMoves.add(new Move(new Flower(new Position(3, 7), new Position(2, 8), new Position(3, 8)),
                new Flower(new Position(3, 7), new Position(4, 7), new Position(3, 8))));
        replayMoves.add(new Move(new Flower(new Position(2, 1), new Position(3, 1), new Position(2, 2)),
                new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3))));
        replayMoves.add(new Move(new Flower(new Position(3, 2), new Position(2, 3), new Position(3, 3)),
                new Flower(new Position(3, 2), new Position(4, 2), new Position(3, 3))));
        replayMoves.add(new Move(new Ditch(new Position(4, 1), new Position(4, 2))));
        replayMoves.add(new Move(new Flower(new Position(4, 2), new Position(3, 3), new Position(4, 3)),
                new Flower(new Position(3, 3), new Position(4, 3), new Position(3, 4))));
        replayMoves.add(new Move(new Flower(new Position(4, 7), new Position(3, 8), new Position(4, 8)),
                new Flower(new Position(3, 8), new Position(4, 8), new Position(3, 9))));
        replayMoves.add(new Move(new Flower(new Position(2, 7), new Position(1, 8), new Position(2, 8)),
                new Flower(new Position(2, 7), new Position(3, 7), new Position(2, 8))));
        replayMoves.add(new Move(new Flower(new Position(2, 8), new Position(3, 8), new Position(2, 9)),
                new Flower(new Position(3, 8), new Position(2, 9), new Position(3, 9))));
        replayMoves.add(new Move(new Flower(new Position(1, 9), new Position(2, 9), new Position(1, 10)),
                new Flower(new Position(2, 9), new Position(1, 10), new Position(2, 10))));
        replayMoves.add(new Move(new Ditch(new Position(4, 7), new Position(5, 7))));
        replayMoves.add(new Move(new Flower(new Position(2, 9), new Position(3, 9), new Position(2, 10)),
                new Flower(new Position(1, 10), new Position(2, 10), new Position(1, 11))));
        replayMoves.add(new Move(new Flower(new Position(2, 4), new Position(3, 4), new Position(2, 5)),
                new Flower(new Position(3, 4), new Position(2, 5), new Position(3, 5))));
        replayMoves.add(new Move(new Flower(new Position(9, 1), new Position(10, 1), new Position(9, 2)),
                new Flower(new Position(10, 1), new Position(9, 2), new Position(10, 2))));
        replayMoves.add(new Move(new Flower(new Position(2, 4), new Position(1, 5), new Position(2, 5)),
                new Flower(new Position(1, 5), new Position(2, 5), new Position(1, 6))));
        replayMoves.add(new Move(new Flower(new Position(10, 1), new Position(11, 1), new Position(10, 2)),
                new Flower(new Position(9, 2), new Position(10, 2), new Position(9, 3))));
        replayMoves.add(new Move(new Flower(new Position(2, 6), new Position(1, 7), new Position(2, 7)),
                new Flower(new Position(1, 7), new Position(2, 7), new Position(1, 8))));
        replayMoves.add(new Move(new Flower(new Position(2, 1), new Position(1, 2), new Position(2, 2)),
                new Flower(new Position(1, 2), new Position(2, 2), new Position(1, 3))));
        replayMoves.add(new Move(new Flower(new Position(3, 6), new Position(2, 7), new Position(3, 7)),
                new Flower(new Position(3, 6), new Position(4, 6), new Position(3, 7))));
        replayMoves.add(new Move(new Flower(new Position(1, 1), new Position(2, 1), new Position(1, 2)),
                new Flower(new Position(1, 4), new Position(2, 4), new Position(1, 5))));
        replayMoves.add(new Move(new Flower(new Position(5, 3), new Position(4, 4), new Position(5, 4)),
                new Flower(new Position(5, 3), new Position(6, 3), new Position(5, 4))));
        replayMoves.add(new Move(new Flower(new Position(2, 5), new Position(1, 6), new Position(2, 6)),
                new Flower(new Position(1, 6), new Position(2, 6), new Position(1, 7))));
        replayMoves.add(new Move(new Flower(new Position(6, 3), new Position(5, 4), new Position(6, 4)),
                new Flower(new Position(4, 5), new Position(5, 5), new Position(4, 6))));
        replayMoves.add(new Move(MoveType.End));

        setupBoardState(replayMoves);

        assertEquals(Status.Draw, viewer.getStatus());
    }
}
