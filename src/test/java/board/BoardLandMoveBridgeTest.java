package board;

import flowerwarspp.board.*;
import flowerwarspp.preset.*;
import org.junit.*;

import static org.junit.Assert.*;

public class BoardLandMoveBridgeTest {
    private static final int BOARD_SIZE = 3;

    private static final Land firstBlockedLand = new Land(new Position(2, 1), new Position(1,
            2), new Position(2, 2));
    private static final Land secondBlockedLand = new Land(new Position(2, 1), new Position(3,
            1), new Position(2, 2));

    private Board board;
    private Viewer viewer;

    @Before
    public void init() {
        board = new BoardImpl(BOARD_SIZE);
        viewer = board.viewer();

        board.make(new Move(new Land(new Position(1, 1), new Position(2, 1), new
                Position(1, 2)), new Land(new Position(2, 2), new Position(3,
                2), new Position(2, 3))));
        board.make(new Move(new Land(new Position(1, 3), new Position(2, 3), new
                Position(1, 4)), new Land(new Position(2, 2), new Position(1,
                3), new Position(2, 3))));
        board.make(new Move(new Bridge(new Position(2, 1), new Position(2, 2)
        )));
    }

    // ------------------------------------------------------------

    @Test
    public void validLandMoveForRedPlayer() {
        board.make(new Move(new Land(new Position(1, 2), new Position(2, 2),
                new Position(1, 3)), new Land(new Position(3, 1), new
                Position(2, 2), new Position(3, 2))));

        assertEquals(Status.Ok, viewer.getStatus());
    }

    @Test
    public void invalidLandMoveOnBridgeBlockedFieldByRedPlayerOnFirstBlockedFieldByFirstLand() {
        Land validLand = new Land(new Position(1, 2), new Position(2, 2),
                new Position(1, 3));

        board.make(new Move(firstBlockedLand, validLand));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void invalidLandMoveOnBridgeBlockedFieldByRedPlayerOnSecondBlockedFieldByFirstLand() {
        Land validLand = new Land(new Position(1, 2), new Position(2, 2),
                new Position(1, 3));

        board.make(new Move(secondBlockedLand, validLand));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void invalidLandMoveOnBridgeBlockedFieldByRedPlayerOnFirstBlockedFieldBySecondLand() {
        Land validLand = new Land(new Position(1, 2), new Position(2, 2),
                new Position(1, 3));

        board.make(new Move(validLand, firstBlockedLand));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void invalidLandMoveOnBridgeBlockedFieldByRedPlayerOnSecondBlockedFieldBySecondLand() {
        Land validLand = new Land(new Position(1, 2), new Position(2, 2),
                new Position(1, 3));

        board.make(new Move(validLand, secondBlockedLand));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void invalidLandMoveOnBridgeBlockedFieldsByRedPlayerOnBothBlockedFields() {
        board.make(new Move(firstBlockedLand, secondBlockedLand));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void invalidLandMoveOnBridgeBlockedFieldByWhitePlayerOnFirstBlockedFieldByFirstLand() {
        // Do red move before
        board.make(new Move(new Land(new Position(1, 2), new Position(2, 2),
                new Position(1, 3)), new Land(new Position(3, 1), new
                Position(2, 2), new Position(3, 2))));

        Land validLand = new Land(new Position(3, 1), new Position(4, 1), new
                Position(3, 2));

        board.make(new Move(firstBlockedLand, validLand));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void invalidLandMoveOnBridgeBlockedFieldByWhitePlayerOnSecondBlockedFieldByFirstLand() {
        // Do red move before
        board.make(new Move(new Land(new Position(1, 2), new Position(2, 2),
                new Position(1, 3)), new Land(new Position(3, 1), new
                Position(2, 2), new Position(3, 2))));

        Land validLand = new Land(new Position(3, 1), new Position(4, 1), new
                Position(3, 2));

        board.make(new Move(secondBlockedLand, validLand));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void invalidLandMoveOnBridgeBlockedFieldByWhitePlayerOnFirstBlockedFieldBySecondLand() {
        // Do red move before
        board.make(new Move(new Land(new Position(1, 2), new Position(2, 2),
                new Position(1, 3)), new Land(new Position(3, 1), new
                Position(2, 2), new Position(3, 2))));

        Land validLand = new Land(new Position(3, 1), new Position(4, 1), new
                Position(3, 2));

        board.make(new Move(validLand, firstBlockedLand));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void invalidLandMoveOnBridgeBlockedFieldByWhitePlayerOnSecondBlockedFieldBySecondLand() {
        // Do red move before
        board.make(new Move(new Land(new Position(1, 2), new Position(2, 2),
                new Position(1, 3)), new Land(new Position(3, 1), new
                Position(2, 2), new Position(3, 2))));

        Land validLand = new Land(new Position(3, 1), new Position(4, 1), new
                Position(3, 2));

        board.make(new Move(validLand, secondBlockedLand));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void invalidLandMoveOnBridgeBlockedFieldsByWhitePlayerOnBothBlockedFields() {
        // Do red move before
        board.make(new Move(new Land(new Position(1, 2), new Position(2, 2),
                new Position(1, 3)), new Land(new Position(3, 1), new
                Position(2, 2), new Position(3, 2))));

        board.make(new Move(firstBlockedLand, secondBlockedLand));

        assertEquals(Status.Illegal, viewer.getStatus());
    }
}
