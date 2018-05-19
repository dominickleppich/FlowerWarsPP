package board;

import flowerwarspp.board.*;
import flowerwarspp.preset.*;
import org.junit.*;

import java.util.*;

import static org.junit.Assert.*;

public class BoardPossibleMovesTest {
    private static final int BOARD_SIZE = 5;
    private Board board;
    private Viewer viewer;

    @Before
    public void init() {
        board = new BoardImpl(BOARD_SIZE);
        viewer = board.viewer();

        List<Move> replayMoves = new LinkedList<>();

        replayMoves.add(new Move(new Flower(new Position(2, 1), new Position(1, 2), new Position(2, 2)),
                new Flower(new Position(1, 2), new Position(2, 2), new Position(1, 3))));
        replayMoves.add(new Move(new Flower(new Position(3, 2), new Position(4, 2), new Position(3, 3)),
                new Flower(new Position(4, 2), new Position(3, 3), new Position(4, 3))));
        replayMoves.add(new Move(new Flower(new Position(2, 2), new Position(1, 3), new Position(2, 3)),
                new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3))));
        replayMoves.add(new Move(new Flower(new Position(3, 3), new Position(2, 4), new Position(3, 4)),
                new Flower(new Position(2, 4), new Position(3, 4), new Position(2, 5))));
        replayMoves.add(new Move(new Flower(new Position(4, 1), new Position(5, 1), new Position(4, 2)),
                new Flower(new Position(1, 4), new Position(2, 4), new Position(1, 5))));
        replayMoves.add(new Move(new Flower(new Position(2, 1), new Position(3, 1), new Position(2, 2)),
                new Flower(new Position(3, 1), new Position(2, 2), new Position(3, 2))));
        replayMoves.add(new Move(new Ditch(new Position(2, 3), new Position(2, 4))));

        setupBoardState(replayMoves);
    }

    private void setupBoardState(List<Move> moves) {
        for (Move m : moves)
            board.make(m);
    }

    // ------------------------------------------------------------
    // * Compare possible moves *

    @Test
    public void comparePossibleMovesForBluePlayer() {
        Move[] correctMoves = new Move[]{
                new Move(new Flower(new Position(1, 1), new Position(2, 1), new Position(1, 2)),
                        new Flower(new Position(3, 1), new Position(4, 1), new Position(3, 2))),
                new Move(new Flower(new Position(1, 1), new Position(2, 1), new Position(1, 2)),
                        new Flower(new Position(4, 1), new Position(3, 2), new Position(4, 2))),
                new Move(new Flower(new Position(1, 1), new Position(2, 1), new Position(1, 2)),
                        new Flower(new Position(5, 1), new Position(4, 2), new Position(5, 2))),
                new Move(new Flower(new Position(1, 1), new Position(2, 1), new Position(1, 2)),
                        new Flower(new Position(5, 1), new Position(6, 1), new Position(5, 2))),
                new Move(new Flower(new Position(1, 1), new Position(2, 1), new Position(1, 2)),
                        new Flower(new Position(3, 2), new Position(2, 3), new Position(3, 3))),
                new Move(new Flower(new Position(1, 1), new Position(2, 1), new Position(1, 2)),
                        new Flower(new Position(4, 2), new Position(5, 2), new Position(4, 3))),
                new Move(new Flower(new Position(1, 1), new Position(2, 1), new Position(1, 2)),
                        new Flower(new Position(1, 3), new Position(2, 3), new Position(1, 4))),
                new Move(new Flower(new Position(1, 1), new Position(2, 1), new Position(1, 2)),
                        new Flower(new Position(2, 4), new Position(1, 5), new Position(2, 5))),
                new Move(new Flower(new Position(1, 1), new Position(2, 1), new Position(1, 2)),
                        new Flower(new Position(1, 5), new Position(2, 5), new Position(1, 6))),
                new Move(new Flower(new Position(3, 1), new Position(4, 1), new Position(3, 2)),
                        new Flower(new Position(5, 1), new Position(4, 2), new Position(5, 2))),
                new Move(new Flower(new Position(3, 1), new Position(4, 1), new Position(3, 2)),
                        new Flower(new Position(5, 1), new Position(6, 1), new Position(5, 2))),
                new Move(new Flower(new Position(3, 1), new Position(4, 1), new Position(3, 2)),
                        new Flower(new Position(3, 2), new Position(2, 3), new Position(3, 3))),
                new Move(new Flower(new Position(3, 1), new Position(4, 1), new Position(3, 2)),
                        new Flower(new Position(4, 2), new Position(5, 2), new Position(4, 3))),
                new Move(new Flower(new Position(3, 1), new Position(4, 1), new Position(3, 2)),
                        new Flower(new Position(1, 3), new Position(2, 3), new Position(1, 4))),
                new Move(new Flower(new Position(3, 1), new Position(4, 1), new Position(3, 2)),
                        new Flower(new Position(2, 4), new Position(1, 5), new Position(2, 5))),
                new Move(new Flower(new Position(3, 1), new Position(4, 1), new Position(3, 2)),
                        new Flower(new Position(1, 5), new Position(2, 5), new Position(1, 6))),
                new Move(new Flower(new Position(4, 1), new Position(3, 2), new Position(4, 2)),
                        new Flower(new Position(5, 1), new Position(4, 2), new Position(5, 2))),
                new Move(new Flower(new Position(4, 1), new Position(3, 2), new Position(4, 2)),
                        new Flower(new Position(5, 1), new Position(6, 1), new Position(5, 2))),
                new Move(new Flower(new Position(4, 1), new Position(3, 2), new Position(4, 2)),
                        new Flower(new Position(1, 3), new Position(2, 3), new Position(1, 4))),
                new Move(new Flower(new Position(4, 1), new Position(3, 2), new Position(4, 2)),
                        new Flower(new Position(2, 4), new Position(1, 5), new Position(2, 5))),
                new Move(new Flower(new Position(4, 1), new Position(3, 2), new Position(4, 2)),
                        new Flower(new Position(1, 5), new Position(2, 5), new Position(1, 6))),
                new Move(new Flower(new Position(5, 1), new Position(4, 2), new Position(5, 2)),
                        new Flower(new Position(5, 1), new Position(6, 1), new Position(5, 2))),
                new Move(new Flower(new Position(5, 1), new Position(4, 2), new Position(5, 2)),
                        new Flower(new Position(3, 2), new Position(2, 3), new Position(3, 3))),
                new Move(new Flower(new Position(5, 1), new Position(4, 2), new Position(5, 2)),
                        new Flower(new Position(1, 3), new Position(2, 3), new Position(1, 4))),
                new Move(new Flower(new Position(5, 1), new Position(4, 2), new Position(5, 2)),
                        new Flower(new Position(2, 4), new Position(1, 5), new Position(2, 5))),
                new Move(new Flower(new Position(5, 1), new Position(4, 2), new Position(5, 2)),
                        new Flower(new Position(1, 5), new Position(2, 5), new Position(1, 6))),
                new Move(new Flower(new Position(5, 1), new Position(6, 1), new Position(5, 2)),
                        new Flower(new Position(3, 2), new Position(2, 3), new Position(3, 3))),
                new Move(new Flower(new Position(5, 1), new Position(6, 1), new Position(5, 2)),
                        new Flower(new Position(4, 2), new Position(5, 2), new Position(4, 3))),
                new Move(new Flower(new Position(5, 1), new Position(6, 1), new Position(5, 2)),
                        new Flower(new Position(1, 3), new Position(2, 3), new Position(1, 4))),
                new Move(new Flower(new Position(5, 1), new Position(6, 1), new Position(5, 2)),
                        new Flower(new Position(2, 4), new Position(1, 5), new Position(2, 5))),
                new Move(new Flower(new Position(5, 1), new Position(6, 1), new Position(5, 2)),
                        new Flower(new Position(1, 5), new Position(2, 5), new Position(1, 6))),
                new Move(new Flower(new Position(3, 2), new Position(2, 3), new Position(3, 3)),
                        new Flower(new Position(1, 3), new Position(2, 3), new Position(1, 4))),
                new Move(new Flower(new Position(3, 2), new Position(2, 3), new Position(3, 3)),
                        new Flower(new Position(2, 4), new Position(1, 5), new Position(2, 5))),
                new Move(new Flower(new Position(3, 2), new Position(2, 3), new Position(3, 3)),
                        new Flower(new Position(1, 5), new Position(2, 5), new Position(1, 6))),
                new Move(new Flower(new Position(4, 2), new Position(5, 2), new Position(4, 3)),
                        new Flower(new Position(1, 3), new Position(2, 3), new Position(1, 4))),
                new Move(new Flower(new Position(4, 2), new Position(5, 2), new Position(4, 3)),
                        new Flower(new Position(2, 4), new Position(1, 5), new Position(2, 5))),
                new Move(new Flower(new Position(4, 2), new Position(5, 2), new Position(4, 3)),
                        new Flower(new Position(1, 5), new Position(2, 5), new Position(1, 6))),
                new Move(new Flower(new Position(1, 3), new Position(2, 3), new Position(1, 4)),
                        new Flower(new Position(2, 4), new Position(1, 5), new Position(2, 5))),
                new Move(new Flower(new Position(1, 3), new Position(2, 3), new Position(1, 4)),
                        new Flower(new Position(1, 5), new Position(2, 5), new Position(1, 6))),
                new Move(new Ditch(new Position(4, 3), new Position(3, 4))), new Move(MoveType.Surrender)
        };

        List<Move> possibleMovesCollection = new LinkedList<>(viewer.getPossibleMoves());
        Collections.sort(possibleMovesCollection);
        Move[] possibleMoves = new Move[possibleMovesCollection.size()];
        possibleMovesCollection.toArray(possibleMoves);
        assertArrayEquals(correctMoves, possibleMoves);
    }

    @Test
    public void comparePossibleMovesForFollowingRedPlayer() {
        board.make(new Move(new Flower(new Position(5, 1), new Position(6, 1), new Position(5, 2)),
                new Flower(new Position(4, 2), new Position(5, 2), new Position(4, 3))));

        Move[] correctMoves = new Move[]{
                new Move(new Flower(new Position(5, 1), new Position(4, 2), new Position(5, 2)),
                        new Flower(new Position(3, 3), new Position(4, 3), new Position(3, 4))),
                new Move(new Flower(new Position(5, 1), new Position(4, 2), new Position(5, 2)),
                        new Flower(new Position(2, 4), new Position(1, 5), new Position(2, 5))),
                new Move(new Flower(new Position(5, 1), new Position(4, 2), new Position(5, 2)),
                        new Flower(new Position(1, 5), new Position(2, 5), new Position(1, 6))),
                new Move(new Flower(new Position(3, 3), new Position(4, 3), new Position(3, 4)),
                        new Flower(new Position(2, 4), new Position(1, 5), new Position(2, 5))),
                new Move(new Flower(new Position(3, 3), new Position(4, 3), new Position(3, 4)),
                        new Flower(new Position(1, 5), new Position(2, 5), new Position(1, 6))),
                new Move(new Flower(new Position(2, 4), new Position(1, 5), new Position(2, 5)),
                        new Flower(new Position(1, 5), new Position(2, 5), new Position(1, 6))),
                new Move(new Ditch(new Position(4, 1), new Position(3, 2))),
                new Move(new Ditch(new Position(1, 3), new Position(1, 4))), new Move(MoveType.Surrender)
        };

        List<Move> possibleMovesCollection = new LinkedList<>(viewer.getPossibleMoves());
        Collections.sort(possibleMovesCollection);
        Move[] possibleMoves = new Move[possibleMovesCollection.size()];
        possibleMovesCollection.toArray(possibleMoves);
        assertArrayEquals(correctMoves, possibleMoves);
    }

    @Test
    public void comparePossibleMovesBeforeEndWithEndMove() {
        board.make(new Move(new Flower(new Position(1, 3), new Position(2, 3), new Position(1, 4)),
                new Flower(new Position(2, 4), new Position(1, 5), new Position(2, 5))));
        board.make(new Move(new Flower(new Position(5, 1), new Position(4, 2), new Position(5, 2)),
                new Flower(new Position(4, 2), new Position(5, 2), new Position(4, 3))));
        board.make(new Move(new Flower(new Position(1, 1), new Position(2, 1), new Position(1, 2)),
                new Flower(new Position(4, 1), new Position(3, 2), new Position(4, 2))));
        board.make(new Move(new Flower(new Position(3, 3), new Position(4, 3), new Position(3, 4)),
                new Flower(new Position(1, 5), new Position(2, 5), new Position(1, 6))));

        Move[] correctMoves = new Move[]{
                new Move(new Ditch(new Position(3, 1), new Position(4, 1))), new Move(MoveType.Surrender),
                new Move(MoveType.End)
        };

        List<Move> possibleMovesCollection = new LinkedList<>(viewer.getPossibleMoves());
        Collections.sort(possibleMovesCollection);
        Move[] possibleMoves = new Move[possibleMovesCollection.size()];
        possibleMovesCollection.toArray(possibleMoves);
        assertArrayEquals(correctMoves, possibleMoves);
    }
}
