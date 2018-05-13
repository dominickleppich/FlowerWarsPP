package board;

import flowerwarspp.board.*;
import flowerwarspp.preset.*;
import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class BoardFlowerMoveValidCreateGardenTest {
    private static final int BOARD_SIZE = 15;
    private Board board;
    private Viewer viewer;

    private static final String MESSAGE = "Valid move creating a garden";

    // ------------------------------------------------------------

    @Before
    public void init() {
        board = new BoardImpl(BOARD_SIZE);
        viewer = board.viewer();

        // Set up board

        List<Move> replayMoves = new LinkedList<>();

        replayMoves.add(new Move(new Flower(new Position(3, 3), new Position(2, 4), new Position(3, 4)),
                new Flower(new Position(3, 3), new Position(4, 3), new Position(3, 4))));
        replayMoves.add(new Move(new Flower(new Position(8, 2), new Position(9, 2), new Position(8, 3)),
                new Flower(new Position(6, 4), new Position(7, 4), new Position(6, 5))));
        replayMoves.add(new Move(new Flower(new Position(4, 2), new Position(3, 3), new Position(4, 3)),
                new Flower(new Position(2, 8), new Position(3, 8), new Position(2, 9))));
        replayMoves.add(new Move(new Flower(new Position(7, 2), new Position(6, 3), new Position(7, 3)),
                new Flower(new Position(5, 5), new Position(4, 6), new Position(5, 6))));
        replayMoves.add(new Move(new Flower(new Position(5, 8), new Position(6, 8), new Position(5, 9)),
                new Flower(new Position(6, 8), new Position(5, 9), new Position(6, 9))));
        replayMoves.add(new Move(new Flower(new Position(10, 2), new Position(11, 2), new Position(10, 3)),
                new Flower(new Position(9, 3), new Position(8, 4), new Position(9, 4))));

        for (Move m : replayMoves)
            board.make(m);
    }

    // ------------------------------------------------------------

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {
                        new Move(new Flower(new Position(4, 3), new Position(3, 4), new Position(4, 4)),
                                new Flower(new Position(3, 6), new Position(2, 7), new Position(3, 7))), Status.Ok
                }, {
                        new Move(new Flower(new Position(2, 3), new Position(3, 3), new Position(2, 4)),
                                new Flower(new Position(3, 6), new Position(2, 7), new Position(3, 7))), Status.Ok
                }, {
                        new Move(new Flower(new Position(3, 2), new Position(4, 2), new Position(3, 3)),
                                new Flower(new Position(3, 6), new Position(2, 7), new Position(3, 7))), Status.Ok
                }, {
                        new Move(new Flower(new Position(2, 4), new Position(3, 4), new Position(2, 5)),
                                new Flower(new Position(3, 6), new Position(2, 7), new Position(3, 7))), Status.Ok
                }, {
                        new Move(new Flower(new Position(4, 2), new Position(5, 2), new Position(4, 3)),
                                new Flower(new Position(3, 6), new Position(2, 7), new Position(3, 7))), Status.Ok
                }, {
                        new Move(new Flower(new Position(6, 7), new Position(5, 8), new Position(6, 8)),
                                new Flower(new Position(5, 8), new Position(4, 9), new Position(5, 9))), Status.Ok
                }, {
                        new Move(new Flower(new Position(6, 8), new Position(7, 8), new Position(6, 9)),
                                new Flower(new Position(5, 9), new Position(6, 9), new Position(5, 10))), Status.Ok
                }, {
                        new Move(new Flower(new Position(4, 8), new Position(5, 8), new Position(4, 9)),
                                new Flower(new Position(5, 8), new Position(4, 9), new Position(5, 9))), Status.Ok
                }, {
                        new Move(new Flower(new Position(5, 8), new Position(4, 9), new Position(5, 9)),
                                new Flower(new Position(4, 9), new Position(5, 9), new Position(4, 10))), Status.Ok
                }, {
                        new Move(new Flower(new Position(5, 7), new Position(6, 7), new Position(5, 8)),
                                new Flower(new Position(6, 7), new Position(5, 8), new Position(6, 8))), Status.Ok
                }, {
                        new Move(new Flower(new Position(6, 7), new Position(5, 8), new Position(6, 8)),
                                new Flower(new Position(6, 7), new Position(7, 7), new Position(6, 8))), Status.Ok
                }, {
                        new Move(new Flower(new Position(5, 9), new Position(4, 10), new Position(5, 10)),
                                new Flower(new Position(5, 9), new Position(6, 9), new Position(5, 10))), Status.Ok
                }, {
                        new Move(new Flower(new Position(5, 9), new Position(6, 9), new Position(5, 10)),
                                new Flower(new Position(6, 9), new Position(5, 10), new Position(6, 10))), Status.Ok
                }, {
                        new Move(new Flower(new Position(6, 8), new Position(7, 8), new Position(6, 9)),
                                new Flower(new Position(7, 8), new Position(6, 9), new Position(7, 9))), Status.Ok
                }, {
                        new Move(new Flower(new Position(7, 7), new Position(6, 8), new Position(7, 8)),
                                new Flower(new Position(6, 8), new Position(7, 8), new Position(6, 9))), Status.Ok
                }
        });
    }

    // ------------------------------------------------------------

    private Move move;
    private Status expectedStatus;

    public BoardFlowerMoveValidCreateGardenTest(Move move, Status expected) {
        this.move = move;
        this.expectedStatus = expected;
    }

    @Test
    public void testFlowerGardenMove() {
        board.make(move);
        assertEquals(MESSAGE + " " + move.toString(), expectedStatus, viewer.getStatus());
    }
}
