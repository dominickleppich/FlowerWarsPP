package board;

import flowerwarspp.board.*;
import flowerwarspp.preset.*;
import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class BoardFlowerMoveInvalidTouchesOwnGardenTest {
    private static final int BOARD_SIZE = 15;
    private Board board;
    private Viewer viewer;

    private static final String MESSAGE = "Invalid move touching own garden";

    // ------------------------------------------------------------

    @Before
    public void init() {
        board = new BoardImpl(BOARD_SIZE);
        viewer = board.viewer();

        // Set up board

        List<Move> replayMoves = new LinkedList<>();

        replayMoves.add(new Move(new Flower(new Position(3, 3), new Position(4, 3), new Position(3, 4)),
                new Flower(new Position(4, 3), new Position(3, 4), new Position(4, 4))));
        replayMoves.add(new Move(new Flower(new Position(13, 2), new Position(14, 2), new Position(13, 3)),
                new Flower(new Position(12, 3), new Position(11, 4), new Position(12, 4))));
        replayMoves.add(new Move(new Flower(new Position(4, 3), new Position(5, 3), new Position(4, 4)),
                new Flower(new Position(3, 4), new Position(4, 4), new Position(3, 5))));
        replayMoves.add(new Move(new Flower(new Position(11, 2), new Position(12, 2), new Position(11, 3)),
                new Flower(new Position(10, 3), new Position(9, 4), new Position(10, 4))));
        replayMoves.add(new Move(new Flower(new Position(3, 10), new Position(4, 10), new Position(3, 11)),
                new Flower(new Position(4, 10), new Position(3, 11), new Position(4, 11))));
        replayMoves.add(new Move(new Flower(new Position(9, 5), new Position(8, 6), new Position(9, 6)),
                new Flower(new Position(10, 5), new Position(11, 5), new Position(10, 6))));
        replayMoves.add(new Move(new Flower(new Position(5, 9), new Position(4, 10), new Position(5, 10)),
                new Flower(new Position(4, 10), new Position(5, 10), new Position(4, 11))));
        replayMoves.add(new Move(new Flower(new Position(9, 2), new Position(10, 2), new Position(9, 3)),
                new Flower(new Position(8, 3), new Position(7, 4), new Position(8, 4))));
        replayMoves.add(new Move(new Flower(new Position(3, 7), new Position(4, 7), new Position(3, 8)),
                new Flower(new Position(4, 7), new Position(3, 8), new Position(4, 8))));
        replayMoves.add(new Move(new Flower(new Position(7, 2), new Position(8, 2), new Position(7, 3)),
                new Flower(new Position(8, 7), new Position(9, 7), new Position(8, 8))));
        replayMoves.add(new Move(new Flower(new Position(4, 7), new Position(5, 7), new Position(4, 8)),
                new Flower(new Position(5, 7), new Position(4, 8), new Position(5, 8))));
        replayMoves.add(new Move(new Flower(new Position(7, 5), new Position(8, 5), new Position(7, 6)),
                new Flower(new Position(2, 13), new Position(3, 13), new Position(2, 14))));

        for (Move m : replayMoves)
            board.make(m);
    }

    // ------------------------------------------------------------

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                // One flower touching garden by side
                {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(3, 3), new Position(2, 4), new Position(3, 4))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(3, 4), new Position(2, 5), new Position(3, 5))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(4, 4), new Position(3, 5), new Position(4, 5))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(5, 3), new Position(4, 4), new Position(5, 4))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(4, 2), new Position(3, 3), new Position(4, 3))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(5, 2), new Position(4, 3), new Position(5, 3))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(3, 7), new Position(2, 8), new Position(3, 8))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(3, 8), new Position(4, 8), new Position(3, 9))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(4, 8), new Position(5, 8), new Position(4, 9))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(4, 6), new Position(3, 7), new Position(4, 7))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(5, 6), new Position(4, 7), new Position(5, 7))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(5, 7), new Position(6, 7), new Position(5, 8))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(3, 10), new Position(2, 11), new Position(3, 11))),
                        Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(3, 11), new Position(4, 11), new Position(3, 12))),
                        Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(5, 10), new Position(4, 11), new Position(5, 11))),
                        Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(5, 9), new Position(6, 9), new Position(5, 10))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(4, 9), new Position(5, 9), new Position(4, 10))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(4, 9), new Position(3, 10), new Position(4, 10))),
                        Status.Illegal
                },
                // One flower touching garden by point
                {
                        new Move(new Flower(new Position(3, 2), new Position(2, 3), new Position(3, 3)),
                                new Flower(new Position(7, 8), new Position(6, 9), new Position(7, 9))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(3, 2), new Position(4, 2), new Position(3, 3)),
                                new Flower(new Position(7, 8), new Position(6, 9), new Position(7, 9))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 3), new Position(3, 3), new Position(2, 4)),
                                new Flower(new Position(7, 8), new Position(6, 9), new Position(7, 9))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 4), new Position(3, 4), new Position(2, 5)),
                                new Flower(new Position(7, 8), new Position(6, 9), new Position(7, 9))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(4, 2), new Position(5, 2), new Position(4, 3)),
                                new Flower(new Position(7, 8), new Position(6, 9), new Position(7, 9))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(5, 2), new Position(6, 2), new Position(5, 3)),
                                new Flower(new Position(7, 8), new Position(6, 9), new Position(7, 9))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(6, 2), new Position(5, 3), new Position(6, 3)),
                                new Flower(new Position(7, 8), new Position(6, 9), new Position(7, 9))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(5, 3), new Position(6, 3), new Position(5, 4)),
                                new Flower(new Position(7, 8), new Position(6, 9), new Position(7, 9))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(4, 4), new Position(5, 4), new Position(4, 5)),
                                new Flower(new Position(7, 8), new Position(6, 9), new Position(7, 9))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(3, 5), new Position(4, 5), new Position(3, 6)),
                                new Flower(new Position(7, 8), new Position(6, 9), new Position(7, 9))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(3, 5), new Position(2, 6), new Position(3, 6)),
                                new Flower(new Position(7, 8), new Position(6, 9), new Position(7, 9))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 5), new Position(3, 5), new Position(2, 6)),
                                new Flower(new Position(7, 8), new Position(6, 9), new Position(7, 9))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(2, 7), new Position(3, 7), new Position(2, 8))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(3, 6), new Position(2, 7), new Position(3, 7))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(3, 6), new Position(4, 6), new Position(3, 7))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(4, 6), new Position(5, 6), new Position(4, 7))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(5, 6), new Position(6, 6), new Position(5, 7))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(6, 6), new Position(5, 7), new Position(6, 7))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(6, 7), new Position(5, 8), new Position(6, 8))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(5, 8), new Position(6, 8), new Position(5, 9))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(5, 8), new Position(4, 9), new Position(5, 9))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(4, 8), new Position(3, 9), new Position(4, 9))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(3, 8), new Position(2, 9), new Position(3, 9))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(2, 8), new Position(3, 8), new Position(2, 9))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(6, 8), new Position(5, 9), new Position(6, 9))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(3, 9), new Position(4, 9), new Position(3, 10))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(3, 9), new Position(2, 10), new Position(3, 10))),
                        Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(2, 10), new Position(3, 10), new Position(2, 11))),
                        Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(2, 11), new Position(3, 11), new Position(2, 12))),
                        Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(3, 11), new Position(2, 12), new Position(3, 12))),
                        Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(4, 11), new Position(3, 12), new Position(4, 12))),
                        Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(4, 11), new Position(5, 11), new Position(4, 12))),
                        Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(5, 10), new Position(6, 10), new Position(5, 11))),
                        Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(6, 9), new Position(5, 10), new Position(6, 10))),
                        Status.Illegal
                },
                // Some combinations
                {
                        new Move(new Flower(new Position(3, 3), new Position(2, 4), new Position(3, 4)),
                                new Flower(new Position(3, 4), new Position(2, 5), new Position(3, 5))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(4, 2), new Position(3, 3), new Position(4, 3)),
                                new Flower(new Position(5, 2), new Position(4, 3), new Position(5, 3))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(5, 3), new Position(4, 4), new Position(5, 4)),
                                new Flower(new Position(4, 4), new Position(3, 5), new Position(4, 5))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(4, 6), new Position(3, 7), new Position(4, 7)),
                                new Flower(new Position(5, 6), new Position(4, 7), new Position(5, 7))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(3, 7), new Position(2, 8), new Position(3, 8)),
                                new Flower(new Position(5, 7), new Position(6, 7), new Position(5, 8))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(3, 8), new Position(4, 8), new Position(3, 9)),
                                new Flower(new Position(4, 8), new Position(5, 8), new Position(4, 9))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(4, 9), new Position(3, 10), new Position(4, 10)),
                                new Flower(new Position(4, 9), new Position(5, 9), new Position(4, 10))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(3, 10), new Position(2, 11), new Position(3, 11)),
                                new Flower(new Position(3, 11), new Position(4, 11), new Position(3, 12))),
                        Status.Illegal
                }, {
                        new Move(new Flower(new Position(5, 9), new Position(6, 9), new Position(5, 10)),
                                new Flower(new Position(5, 10), new Position(4, 11), new Position(5, 11))),
                        Status.Illegal
                }, {
                        new Move(new Flower(new Position(5, 8), new Position(6, 8), new Position(5, 9)),
                                new Flower(new Position(6, 8), new Position(5, 9), new Position(6, 9))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(3, 9), new Position(2, 10), new Position(3, 10)),
                                new Flower(new Position(4, 11), new Position(5, 11), new Position(4, 12))),
                        Status.Illegal
                }, {
                        new Move(new Flower(new Position(3, 6), new Position(2, 7), new Position(3, 7)),
                                new Flower(new Position(5, 8), new Position(6, 8), new Position(5, 9))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(3, 2), new Position(2, 3), new Position(3, 3)),
                                new Flower(new Position(3, 5), new Position(2, 6), new Position(3, 6))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(4, 2), new Position(5, 2), new Position(4, 3)),
                                new Flower(new Position(4, 4), new Position(5, 4), new Position(4, 5))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(6, 2), new Position(5, 3), new Position(6, 3)),
                                new Flower(new Position(2, 4), new Position(3, 4), new Position(2, 5))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(5, 2), new Position(4, 3), new Position(5, 3)),
                                new Flower(new Position(3, 8), new Position(4, 8), new Position(3, 9))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(3, 2), new Position(2, 3), new Position(3, 3)),
                                new Flower(new Position(4, 9), new Position(5, 9), new Position(4, 10))), Status.Illegal
                },
                // Flower touching garden by point with both flowers from move connected side by side
                {
                        new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                                new Flower(new Position(3, 2), new Position(2, 3), new Position(3, 3))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 3), new Position(1, 4), new Position(2, 4)),
                                new Flower(new Position(2, 3), new Position(3, 3), new Position(2, 4))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(4, 1), new Position(3, 2), new Position(4, 2)),
                                new Flower(new Position(3, 2), new Position(4, 2), new Position(3, 3))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(5, 1), new Position(4, 2), new Position(5, 2)),
                                new Flower(new Position(4, 2), new Position(5, 2), new Position(4, 3))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(6, 1), new Position(5, 2), new Position(6, 2)),
                                new Flower(new Position(5, 2), new Position(6, 2), new Position(5, 3))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(5, 3), new Position(6, 3), new Position(5, 4)),
                                new Flower(new Position(6, 3), new Position(5, 4), new Position(6, 4))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(4, 4), new Position(5, 4), new Position(4, 5)),
                                new Flower(new Position(5, 4), new Position(4, 5), new Position(5, 5))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(3, 5), new Position(4, 5), new Position(3, 6)),
                                new Flower(new Position(4, 5), new Position(3, 6), new Position(4, 6))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(3, 5), new Position(2, 6), new Position(3, 6)),
                                new Flower(new Position(2, 6), new Position(3, 6), new Position(2, 7))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 5), new Position(1, 6), new Position(2, 6)),
                                new Flower(new Position(2, 5), new Position(3, 5), new Position(2, 6))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 4), new Position(1, 5), new Position(2, 5)),
                                new Flower(new Position(2, 4), new Position(3, 4), new Position(2, 5))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 6), new Position(3, 6), new Position(2, 7)),
                                new Flower(new Position(3, 6), new Position(2, 7), new Position(3, 7))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(4, 5), new Position(3, 6), new Position(4, 6)),
                                new Flower(new Position(3, 6), new Position(4, 6), new Position(3, 7))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(5, 5), new Position(4, 6), new Position(5, 6)),
                                new Flower(new Position(4, 6), new Position(5, 6), new Position(4, 7))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(6, 5), new Position(5, 6), new Position(6, 6)),
                                new Flower(new Position(5, 6), new Position(6, 6), new Position(5, 7))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(6, 6), new Position(5, 7), new Position(6, 7)),
                                new Flower(new Position(6, 6), new Position(7, 6), new Position(6, 7))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(6, 7), new Position(5, 8), new Position(6, 8)),
                                new Flower(new Position(6, 7), new Position(7, 7), new Position(6, 8))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(5, 8), new Position(6, 8), new Position(5, 9)),
                                new Flower(new Position(6, 8), new Position(5, 9), new Position(6, 9))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(6, 7), new Position(5, 8), new Position(6, 8)),
                                new Flower(new Position(5, 8), new Position(6, 8), new Position(5, 9))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(4, 8), new Position(3, 9), new Position(4, 9)),
                                new Flower(new Position(3, 9), new Position(4, 9), new Position(3, 10))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(3, 8), new Position(2, 9), new Position(3, 9)),
                                new Flower(new Position(2, 9), new Position(3, 9), new Position(2, 10))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 8), new Position(1, 9), new Position(2, 9)),
                                new Flower(new Position(2, 8), new Position(3, 8), new Position(2, 9))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 7), new Position(1, 8), new Position(2, 8)),
                                new Flower(new Position(2, 7), new Position(3, 7), new Position(2, 8))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 9), new Position(3, 9), new Position(2, 10)),
                                new Flower(new Position(3, 9), new Position(2, 10), new Position(3, 10))),
                        Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 10), new Position(1, 11), new Position(2, 11)),
                                new Flower(new Position(2, 10), new Position(3, 10), new Position(2, 11))),
                        Status.Illegal
                }, {
                        new Move(new Flower(new Position(2, 11), new Position(1, 12), new Position(2, 12)),
                                new Flower(new Position(2, 11), new Position(3, 11), new Position(2, 12))),
                        Status.Illegal
                }, {
                        new Move(new Flower(new Position(3, 11), new Position(2, 12), new Position(3, 12)),
                                new Flower(new Position(2, 12), new Position(3, 12), new Position(2, 13))),
                        Status.Illegal
                }, {
                        new Move(new Flower(new Position(4, 11), new Position(3, 12), new Position(4, 12)),
                                new Flower(new Position(3, 12), new Position(4, 12), new Position(3, 13))),
                        Status.Illegal
                }, {
                        new Move(new Flower(new Position(5, 10), new Position(6, 10), new Position(5, 11)),
                                new Flower(new Position(6, 10), new Position(5, 11), new Position(6, 11))),
                        Status.Illegal
                }, {
                        new Move(new Flower(new Position(6, 9), new Position(5, 10), new Position(6, 10)),
                                new Flower(new Position(6, 9), new Position(7, 9), new Position(6, 10))), Status.Illegal
                }, {
                        new Move(new Flower(new Position(6, 8), new Position(5, 9), new Position(6, 9)),
                                new Flower(new Position(6, 8), new Position(7, 8), new Position(6, 9))), Status.Illegal
                }
        });
    }

    // ------------------------------------------------------------

    private Move move;
    private Status expectedStatus;

    public BoardFlowerMoveInvalidTouchesOwnGardenTest(Move move, Status expected) {
        this.move = move;
        this.expectedStatus = expected;
    }

    @Test
    public void testFlowerGardenMove() {
        board.make(move);
        assertEquals(MESSAGE + " " + move.toString(), expectedStatus, viewer.getStatus());
    }
}
