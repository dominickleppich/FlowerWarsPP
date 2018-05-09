package board;

import flowerwarspp.board.*;
import flowerwarspp.preset.*;
import org.junit.*;

import static org.junit.Assert.*;

public class BoardFlowerMoveDitchTest {
    private static final int BOARD_SIZE = 3;

    private static final Flower FIRST_BLOCKED_FLOWER = new Flower(new Position(2, 1), new Position(1,
            2), new Position(2, 2));
    private static final Flower SECOND_BLOCKED_FLOWER = new Flower(new Position(2, 1), new Position(3,
            1), new Position(2, 2));

    private Board board;
    private Viewer viewer;

    @Before
    public void init() {
        board = new BoardImpl(BOARD_SIZE);
        viewer = board.viewer();

        board.make(new Move(new Flower(new Position(1, 1), new Position(2, 1), new
                Position(1, 2)), new Flower(new Position(2, 2), new Position(3,
                2), new Position(2, 3))));
        board.make(new Move(new Flower(new Position(1, 3), new Position(2, 3), new
                Position(1, 4)), new Flower(new Position(2, 2), new Position(1,
                3), new Position(2, 3))));
        board.make(new Move(new Ditch(new Position(2, 1), new Position(2, 2)
        )));
    }

    // ------------------------------------------------------------

    @Test
    public void validLandMoveForRedPlayer() {
        board.make(new Move(new Flower(new Position(1, 2), new Position(2, 2),
                new Position(1, 3)), new Flower(new Position(3, 1), new
                Position(2, 2), new Position(3, 2))));

        assertEquals(Status.Ok, viewer.getStatus());
    }

    @Test
    public void invalidLandMoveOnBridgeBlockedFieldByRedPlayerOnFirstBlockedFieldByFirstLand() {
        Flower validFlower = new Flower(new Position(1, 2), new Position(2, 2),
                new Position(1, 3));

        board.make(new Move(FIRST_BLOCKED_FLOWER, validFlower));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void invalidLandMoveOnBridgeBlockedFieldByRedPlayerOnSecondBlockedFieldByFirstLand() {
        Flower validFlower = new Flower(new Position(1, 2), new Position(2, 2),
                new Position(1, 3));

        board.make(new Move(SECOND_BLOCKED_FLOWER, validFlower));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void invalidLandMoveOnBridgeBlockedFieldByRedPlayerOnFirstBlockedFieldBySecondLand() {
        Flower validFlower = new Flower(new Position(1, 2), new Position(2, 2),
                new Position(1, 3));

        board.make(new Move(validFlower, FIRST_BLOCKED_FLOWER));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void invalidLandMoveOnBridgeBlockedFieldByRedPlayerOnSecondBlockedFieldBySecondLand() {
        Flower validFlower = new Flower(new Position(1, 2), new Position(2, 2),
                new Position(1, 3));

        board.make(new Move(validFlower, SECOND_BLOCKED_FLOWER));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void invalidLandMoveOnBridgeBlockedFieldsByRedPlayerOnBothBlockedFields() {
        board.make(new Move(FIRST_BLOCKED_FLOWER, SECOND_BLOCKED_FLOWER));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void invalidLandMoveOnBridgeBlockedFieldByWhitePlayerOnFirstBlockedFieldByFirstLand() {
        // Do red move before
        board.make(new Move(new Flower(new Position(1, 2), new Position(2, 2),
                new Position(1, 3)), new Flower(new Position(3, 1), new
                Position(2, 2), new Position(3, 2))));

        Flower validFlower = new Flower(new Position(3, 1), new Position(4, 1), new
                Position(3, 2));

        board.make(new Move(FIRST_BLOCKED_FLOWER, validFlower));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void invalidLandMoveOnBridgeBlockedFieldByWhitePlayerOnSecondBlockedFieldByFirstLand() {
        // Do red move before
        board.make(new Move(new Flower(new Position(1, 2), new Position(2, 2),
                new Position(1, 3)), new Flower(new Position(3, 1), new
                Position(2, 2), new Position(3, 2))));

        Flower validFlower = new Flower(new Position(3, 1), new Position(4, 1), new
                Position(3, 2));

        board.make(new Move(SECOND_BLOCKED_FLOWER, validFlower));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void invalidLandMoveOnBridgeBlockedFieldByWhitePlayerOnFirstBlockedFieldBySecondLand() {
        // Do red move before
        board.make(new Move(new Flower(new Position(1, 2), new Position(2, 2),
                new Position(1, 3)), new Flower(new Position(3, 1), new
                Position(2, 2), new Position(3, 2))));

        Flower validFlower = new Flower(new Position(3, 1), new Position(4, 1), new
                Position(3, 2));

        board.make(new Move(validFlower, FIRST_BLOCKED_FLOWER));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void invalidLandMoveOnBridgeBlockedFieldByWhitePlayerOnSecondBlockedFieldBySecondLand() {
        // Do red move before
        board.make(new Move(new Flower(new Position(1, 2), new Position(2, 2),
                new Position(1, 3)), new Flower(new Position(3, 1), new
                Position(2, 2), new Position(3, 2))));

        Flower validFlower = new Flower(new Position(3, 1), new Position(4, 1), new
                Position(3, 2));

        board.make(new Move(validFlower, SECOND_BLOCKED_FLOWER));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void invalidLandMoveOnBridgeBlockedFieldsByWhitePlayerOnBothBlockedFields() {
        // Do red move before
        board.make(new Move(new Flower(new Position(1, 2), new Position(2, 2),
                new Position(1, 3)), new Flower(new Position(3, 1), new
                Position(2, 2), new Position(3, 2))));

        board.make(new Move(FIRST_BLOCKED_FLOWER, SECOND_BLOCKED_FLOWER));

        assertEquals(Status.Illegal, viewer.getStatus());
    }
}
