package board;

import flowerwarspp.board.*;
import flowerwarspp.preset.*;
import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class BoardFlowerMoveInvalidFusionFlowerBedsWithTwoFlowersToIllegalTouchingGardenTest {
    private static final int BOARD_SIZE = 15;
    private Board board;
    private Viewer viewer;

    private static final String MESSAGE = "Invalid move fusion flower beds with two flowers to illegal touching garden";

    // ------------------------------------------------------------

    @Before
    public void init() {
        board = new BoardImpl(BOARD_SIZE);
        viewer = board.viewer();

        // Set up board

        List<Move> replayMoves = new LinkedList<>();


        replayMoves.add(new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                new Flower(new Position(3, 2), new Position(4, 2), new Position(3, 3))));
        replayMoves.add(new Move(new Flower(new Position(13, 2), new Position(14, 2), new Position(13, 3)),
                new Flower(new Position(12, 3), new Position(11, 4), new Position(12, 4))));
        replayMoves.add(new Move(new Flower(new Position(4, 2), new Position(5, 2), new Position(4, 3)),
                new Flower(new Position(4, 4), new Position(5, 4), new Position(4, 5))));
        replayMoves.add(new Move(new Flower(new Position(10, 5), new Position(11, 5), new Position(10, 6)),
                new Flower(new Position(9, 6), new Position(8, 7), new Position(9, 7))));
        replayMoves.add(new Move(new Flower(new Position(2, 6), new Position(3, 6), new Position(2, 7)),
                new Flower(new Position(3, 6), new Position(2, 7), new Position(3, 7))));
        replayMoves.add(new Move(new Flower(new Position(11, 2), new Position(12, 2), new Position(11, 3)),
                new Flower(new Position(10, 3), new Position(9, 4), new Position(10, 4))));
        replayMoves.add(new Move(new Flower(new Position(4, 7), new Position(3, 8), new Position(4, 8)),
                new Flower(new Position(3, 9), new Position(2, 10), new Position(3, 10))));
        replayMoves.add(new Move(new Flower(new Position(8, 5), new Position(9, 5), new Position(8, 6)),
                new Flower(new Position(7, 6), new Position(6, 7), new Position(7, 7))));
        replayMoves.add(new Move(new Flower(new Position(4, 9), new Position(3, 10), new Position(4, 10)),
                new Flower(new Position(4, 10), new Position(5, 10), new Position(4, 11))));
        replayMoves.add(new Move(new Flower(new Position(9, 2), new Position(10, 2), new Position(9, 3)),
                new Flower(new Position(8, 3), new Position(7, 4), new Position(8, 4))));

        for (Move m : replayMoves)
            board.make(m);
    }

    // ------------------------------------------------------------

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {
                        new Move(new Flower(new Position(3, 2), new Position(2, 3), new Position(3, 3)),
                                new Flower(new Position(2, 3), new Position(3, 3), new Position(2, 4))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(4, 2), new Position(3, 3), new Position(4, 3)),
                                new Flower(new Position(3, 3), new Position(4, 3), new Position(3, 4))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(4, 2), new Position(3, 3), new Position(4, 3)),
                                new Flower(new Position(5, 2), new Position(4, 3), new Position(5, 3))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(3, 5), new Position(2, 6), new Position(3, 6)),
                                new Flower(new Position(3, 5), new Position(4, 5), new Position(3, 6))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(4, 5), new Position(3, 6), new Position(4, 6)),
                                new Flower(new Position(3, 6), new Position(4, 6), new Position(3, 7))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 7), new Position(3, 7), new Position(2, 8)),
                                new Flower(new Position(3, 7), new Position(2, 8), new Position(3, 8))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(3, 6), new Position(4, 6), new Position(3, 7)),
                                new Flower(new Position(4, 6), new Position(3, 7), new Position(4, 7))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(4, 8), new Position(3, 9), new Position(4, 9)),
                                new Flower(new Position(3, 9), new Position(4, 9), new Position(3, 10))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(3, 9), new Position(4, 9), new Position(3, 10)),
                                new Flower(new Position(2, 10), new Position(3, 10), new Position(2, 11))),
                        Status.Illegal
                }, {
                        new Move(new Flower(new Position(4, 9), new Position(5, 9), new Position(4, 10)),
                                new Flower(new Position(5, 9), new Position(4, 10), new Position(5, 10))),
                        Status.Illegal
                }, {
                        new Move(new Flower(new Position(3, 10), new Position(4, 10), new Position(3, 11)),
                                new Flower(new Position(4, 10), new Position(3, 11), new Position(4, 11))),
                        Status.Illegal
                }
        });
    }

    // ------------------------------------------------------------

    private Move move;
    private Status expectedStatus;

    public BoardFlowerMoveInvalidFusionFlowerBedsWithTwoFlowersToIllegalTouchingGardenTest(Move move, Status expected) {
        this.move = move;
        this.expectedStatus = expected;
    }

    @Test
    public void testFlowerGardenMove() {
        board.make(move);
        assertEquals(MESSAGE + " " + move.toString(), expectedStatus, viewer.getStatus());
    }
}
