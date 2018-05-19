package board;

import flowerwarspp.board.*;
import flowerwarspp.preset.*;
import org.junit.*;

import java.util.*;

import static org.junit.Assert.*;

public class BoardFlowerMoveDitchTest {
    private static final int BOARD_SIZE = 10;

    private static final Flower FIRST_BLOCKED_FLOWER = new Flower(new Position(3, 2), new Position(2, 3),
            new Position(3, 3));
    private static final Flower SECOND_BLOCKED_FLOWER = new Flower(new Position(3, 2), new Position(4, 2),
            new Position(3, 3));

    private static final Flower FIRST_VALID_RED_FLOWER = new Flower(new Position(2, 8), new Position(3, 8),
            new Position(2, 9));
    private static final Flower SECOND_VALID_RED_FLOWER = new Flower(new Position(3, 5), new Position(2, 6),
            new Position(3, 6));

    private static final Flower FIRST_VALID_BLUE_FLOWER = new Flower(new Position(5, 5), new Position(6, 5),
            new Position(5, 6));
    private static final Flower SECOND_VALID_BLUE_FLOWER = new Flower(new Position(4, 6), new Position(3, 7),
            new Position(4, 7));

    private Board board;
    private Viewer viewer;

    @Before
    public void init() {
        board = new BoardImpl(BOARD_SIZE);
        viewer = board.viewer();

        // Set up board

        List<Move> replayMoves = new LinkedList<>();

        replayMoves.add(new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                new Flower(new Position(3, 3), new Position(2, 4), new Position(3, 4))));
        replayMoves.add(new Move(new Flower(new Position(8, 2), new Position(9, 2), new Position(8, 3)), new Flower(new Position(7, 3), new Position(6, 4), new Position(7, 4))));
        replayMoves.add(new Move(new Ditch(new Position(3, 2), new Position(3, 3))));

        for (Move m : replayMoves)
            board.make(m);
    }

    // ------------------------------------------------------------

    @Test
    public void validFlowerMoveForRedPlayer() {
        // Do blue move before
        board.make(new Move(FIRST_VALID_BLUE_FLOWER, SECOND_VALID_BLUE_FLOWER));

        board.make(new Move(FIRST_VALID_RED_FLOWER, SECOND_VALID_RED_FLOWER));

        assertEquals(Status.Ok, viewer.getStatus());
    }

    @Test
    public void invalidFlowerMoveOnDitchBlockedFieldByRedPlayerOnFirstBlockedFieldByFirstFlower() {
        // Do blue move before
        board.make(new Move(FIRST_VALID_BLUE_FLOWER, SECOND_VALID_BLUE_FLOWER));

        board.make(new Move(FIRST_BLOCKED_FLOWER, SECOND_VALID_RED_FLOWER));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void invalidFlowerMoveOnDitchBlockedFieldByRedPlayerOnSecondBlockedFieldByFirstFlower() {
        // Do blue move before
        board.make(new Move(FIRST_VALID_BLUE_FLOWER, SECOND_VALID_BLUE_FLOWER));

        board.make(new Move(SECOND_BLOCKED_FLOWER, SECOND_VALID_RED_FLOWER));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void invalidFlowerMoveOnDitchBlockedFieldByRedPlayerOnFirstBlockedFieldBySecondFlower() {
        // Do blue move before
        board.make(new Move(FIRST_VALID_BLUE_FLOWER, SECOND_VALID_BLUE_FLOWER));

        board.make(new Move(FIRST_VALID_RED_FLOWER, FIRST_BLOCKED_FLOWER));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void invalidFlowerMoveOnDitchBlockedFieldByRedPlayerOnSecondBlockedFieldBySecondFlower() {
        // Do blue move before
        board.make(new Move(FIRST_VALID_BLUE_FLOWER, SECOND_VALID_BLUE_FLOWER));

        board.make(new Move(FIRST_VALID_RED_FLOWER, SECOND_BLOCKED_FLOWER));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void invalidFlowerMoveOnDitchBlockedFieldsByRedPlayerOnBothBlockedFields() {
        // Do blue move before
        board.make(new Move(FIRST_VALID_BLUE_FLOWER, SECOND_VALID_BLUE_FLOWER));

        board.make(new Move(FIRST_BLOCKED_FLOWER, SECOND_BLOCKED_FLOWER));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void invalidFlowerMoveOnDitchBlockedFieldByBluePlayerOnFirstBlockedFieldByFirstFlower() {
        board.make(new Move(FIRST_BLOCKED_FLOWER, SECOND_VALID_BLUE_FLOWER));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void invalidFlowerMoveOnDitchBlockedFieldByBluePlayerOnSecondBlockedFieldByFirstFlower() {
        board.make(new Move(SECOND_BLOCKED_FLOWER, SECOND_VALID_BLUE_FLOWER));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void invalidFlowerMoveOnDitchBlockedFieldByBluePlayerOnFirstBlockedFieldBySecondFlower() {
        board.make(new Move(FIRST_VALID_BLUE_FLOWER, FIRST_BLOCKED_FLOWER));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void invalidFlowerMoveOnDitchBlockedFieldByBluePlayerOnSecondBlockedFieldBySecondFlower() {
        board.make(new Move(FIRST_VALID_BLUE_FLOWER, SECOND_BLOCKED_FLOWER));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void invalidFlowerMoveOnDitchBlockedFieldsByBluePlayerOnBothBlockedFields() {
        board.make(new Move(FIRST_BLOCKED_FLOWER, SECOND_BLOCKED_FLOWER));

        assertEquals(Status.Illegal, viewer.getStatus());
    }
}
