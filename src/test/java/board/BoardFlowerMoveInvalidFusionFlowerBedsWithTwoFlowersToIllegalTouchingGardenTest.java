package board;

import flowerwarspp.preset.*;
import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class BoardFlowerMoveInvalidFusionFlowerBedsWithTwoFlowersToIllegalTouchingGardenTest {
    private static final int BOARD_SIZE = 15;
    private static final String MESSAGE = "Invalid move fusion flower beds with two flowers to illegal touching garden";
    private Board board;
    private Viewer viewer;

    // ------------------------------------------------------------
    private Move move;

    // ------------------------------------------------------------
    private Status expectedStatus;

    // ------------------------------------------------------------

    public BoardFlowerMoveInvalidFusionFlowerBedsWithTwoFlowersToIllegalTouchingGardenTest(Move move, Status expected) {
        this.move = move;
        this.expectedStatus = expected;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {
                        new Move(new Flower(new Position(2, 3), new Position(3, 3), new Position(2, 4)),
                                new Flower(new Position(3, 3), new Position(4, 3), new Position(3, 4))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 3), new Position(3, 3), new Position(2, 4)),
                                new Flower(new Position(3, 4), new Position(2, 5), new Position(3, 5))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(5, 2), new Position(6, 2), new Position(5, 3)),
                                new Flower(new Position(6, 2), new Position(7, 2), new Position(6, 3))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(6, 3), new Position(5, 4), new Position(6, 4)),
                                new Flower(new Position(6, 3), new Position(7, 3), new Position(6, 4))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(3, 7), new Position(4, 7), new Position(3, 8)),
                                new Flower(new Position(4, 7), new Position(5, 7), new Position(4, 8))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(3, 7), new Position(4, 7), new Position(3, 8)),
                                new Flower(new Position(3, 8), new Position(4, 8), new Position(3, 9))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(3, 8), new Position(4, 8), new Position(3, 9)),
                                new Flower(new Position(4, 8), new Position(3, 9), new Position(4, 9))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(4, 7), new Position(5, 7), new Position(4, 8)),
                                new Flower(new Position(5, 7), new Position(4, 8), new Position(5, 8))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(4, 5), new Position(5, 5), new Position(4, 6)),
                                new Flower(new Position(5, 5), new Position(6, 5), new Position(5, 6))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(5, 4), new Position(4, 5), new Position(5, 5)),
                                new Flower(new Position(4, 5), new Position(5, 5), new Position(4, 6))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(4, 4), new Position(3, 5), new Position(4, 5)),
                                new Flower(new Position(3, 5), new Position(4, 5), new Position(3, 6))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(6, 2), new Position(7, 2), new Position(6, 3)),
                                new Flower(new Position(6, 3), new Position(5, 4), new Position(6, 4))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(6, 2), new Position(7, 2), new Position(6, 3)),
                                new Flower(new Position(5, 3), new Position(4, 4), new Position(5, 4))), Status.Illegal
                }
        });
    }

    @Before
    public void init() {
        board = TestBoardFactory.createInstance(BOARD_SIZE);
        viewer = board.viewer();

        // Set up board

        List<Move> replayMoves = new LinkedList<>();

        replayMoves.add(new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                new Flower(new Position(3, 3), new Position(2, 4), new Position(3, 4))));
        replayMoves.add(new Move(new Flower(new Position(13, 2), new Position(14, 2), new Position(13, 3)),
                new Flower(new Position(12, 3), new Position(11, 4), new Position(12, 4))));
        replayMoves.add(new Move(new Flower(new Position(2, 4), new Position(3, 4), new Position(2, 5)),
                new Flower(new Position(3, 5), new Position(2, 6), new Position(3, 6))));
        replayMoves.add(new Move(new Flower(new Position(10, 5), new Position(11, 5), new Position(10, 6)),
                new Flower(new Position(9, 6), new Position(8, 7), new Position(9, 7))));
        replayMoves.add(new Move(new Flower(new Position(4, 5), new Position(3, 6), new Position(4, 6)),
                new Flower(new Position(5, 5), new Position(4, 6), new Position(5, 6))));
        replayMoves.add(new Move(new Flower(new Position(7, 8), new Position(8, 8), new Position(7, 9)),
                new Flower(new Position(6, 9), new Position(5, 10), new Position(6, 10))));
        replayMoves.add(new Move(new Flower(new Position(3, 7), new Position(2, 8), new Position(3, 8)),
                new Flower(new Position(4, 7), new Position(3, 8), new Position(4, 8))));
        replayMoves.add(new Move(new Flower(new Position(4, 11), new Position(5, 11), new Position(4, 12)),
                new Flower(new Position(3, 12), new Position(2, 13), new Position(3, 13))));
        replayMoves.add(new Move(new Flower(new Position(7, 2), new Position(8, 2), new Position(7, 3)),
                new Flower(new Position(4, 8), new Position(5, 8), new Position(4, 9))));
        replayMoves.add(new Move(new Flower(new Position(11, 2), new Position(12, 2), new Position(11, 3)),
                new Flower(new Position(10, 3), new Position(9, 4), new Position(10, 4))));
        replayMoves.add(new Move(new Flower(new Position(6, 2), new Position(5, 3), new Position(6, 3)),
                new Flower(new Position(5, 3), new Position(6, 3), new Position(5, 4))));
        replayMoves.add(new Move(new Flower(new Position(8, 5), new Position(9, 5), new Position(8, 6)),
                new Flower(new Position(7, 6), new Position(6, 7), new Position(7, 7))));

        for (Move m : replayMoves)
            board.make(m);
    }

    @Test
    public void testFlowerGardenMove() {
        board.make(move);
        assertEquals(MESSAGE + " " + move.toString(), expectedStatus, viewer.getStatus());
    }
}
