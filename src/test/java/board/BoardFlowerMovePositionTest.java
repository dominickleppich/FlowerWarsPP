package board;

import flowerwarspp.board.*;
import flowerwarspp.preset.*;
import org.junit.*;

import static org.junit.Assert.*;

public class BoardFlowerMovePositionTest {
    private static final int BOARD_SIZE = 3;

    private static final Flower VALID_FLOWER = new Flower(new Position(2, 2), new
            Position(1, 3), new Position(2, 3));

    private Board board;
    private Viewer viewer;

    @Before
    public void init() {
        board = new BoardImpl(BOARD_SIZE);
        viewer = board.viewer();
    }

    // ------------------------------------------------------------
    // ** Flower inside or outside of the board **

    /**
     * Create a flower move with given flower and another valid flower, use the given
     * flower as first move parameter, if the {@code first} flag is set.
     *
     * @param first - enable this flag, if the given {@code flower} should be
     *              used as first move parameter flower
     * @param flower  - the flower to test in the move
     */
    private Move createMove(boolean first, Flower flower) {
        Move m;
        if (first)
            m = new Move(flower, VALID_FLOWER);
        else
            m = new Move(VALID_FLOWER, flower);
        return m;
    }

    @Test
    public void makeValidBottomLeftLandMoveInFirstLand() {
        board.make(createMove(true, new Flower(new Position(1, 1), new Position
                (2, 1), new Position(1, 2))));

        assertEquals(Status.Ok, viewer.getStatus());
    }

    @Test
    public void makeValidBottomRightLandMoveInFirstLand() {
        board.make(createMove(true, new Flower(new Position(3, 1), new Position
                (4, 1), new Position(3, 2))));

        assertEquals(Status.Ok, viewer.getStatus());
    }

    @Test
    public void makeValidTopLandMoveInFirstLand() {
        board.make(createMove(true, new Flower(new Position(1, 3), new Position
                (2, 3), new Position(1, 4))));

        assertEquals(Status.Ok, viewer.getStatus());
    }

    @Test
    public void makeValidBorderLandMoveInFirstLand() {
        board.make(createMove(true, new Flower(new Position(2, 2), new Position
                (3, 2), new Position(2, 3))));

        assertEquals(Status.Ok, viewer.getStatus());
    }

    @Test
    public void makeValidBottomLeftLandMoveInSecondLand() {
        board.make(createMove(false, new Flower(new Position(1, 1), new Position
                (2, 1), new Position(1, 2))));

        assertEquals(Status.Ok, viewer.getStatus());
    }

    @Test
    public void makeValidBottomRightLandMoveInSecondLand() {
        board.make(createMove(false, new Flower(new Position(3, 1), new Position
                (4, 1), new Position(3, 2))));

        assertEquals(Status.Ok, viewer.getStatus());
    }

    @Test
    public void makeValidTopLandMoveInSecondLand() {
        board.make(createMove(false, new Flower(new Position(1, 3), new Position
                (2, 3), new Position(1, 4))));

        assertEquals(Status.Ok, viewer.getStatus());
    }

    @Test
    public void makeValidBorderLandMoveInSecondLand() {
        board.make(createMove(false, new Flower(new Position(2, 2), new Position
                (3, 2), new Position(2, 3))));

        assertEquals(Status.Ok, viewer.getStatus());
    }

    @Test
    public void makeInvalidTopOutsideLandMoveInFirstLand() {
        board.make(createMove(true, new Flower(new Position(1, 4), new Position
                (2, 4), new Position(1, 5))));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void makeInvalidBottomRightOutsideLandMoveInFirstLand() {
        board.make(createMove(true, new Flower(new Position(4, 1), new Position
                (5, 1), new Position(4, 2))));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void makeInvalidTopBorderOutsideLandMoveInFirstLand() {
        board.make(createMove(true, new Flower(new Position(2, 3), new Position
                (1, 4), new Position(2, 4))));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void makeInvalidMiddleBorderOutsideLandMoveInFirstLand() {
        board.make(createMove(true, new Flower(new Position(3, 2), new Position
                (2, 3), new Position(3, 3))));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void makeInvalidBottomBorderOutsideLandMoveInFirstLand() {
        board.make(createMove(true, new Flower(new Position(4, 1), new Position
                (3, 2), new Position(4, 2))));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void makeInvalidTopOutsideLandMoveInSecondLand() {
        board.make(createMove(false, new Flower(new Position(1, 4), new Position
                (2, 4), new Position(1, 5))));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void makeInvalidBottomRightOutsideLandMoveInSecondLand() {
        board.make(createMove(false, new Flower(new Position(4, 1), new Position
                (5, 1), new Position(4, 2))));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void makeInvalidTopBorderOutsideLandMoveInSecondLand() {
        board.make(createMove(false, new Flower(new Position(2, 3), new Position
                (1, 4), new Position(2, 4))));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void makeInvalidMiddleBorderOutsideLandMoveInSecondLand() {
        board.make(createMove(false, new Flower(new Position(3, 2), new Position
                (2, 3), new Position(3, 3))));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void makeInvalidBottomBorderOutsideLandMoveInSecondLand() {
        board.make(createMove(false, new Flower(new Position(4, 1), new Position
                (3, 2), new Position(4, 2))));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    // ------------------------------------------------------------
    // ** Flower moves only on empty fields allowed **

    private void prepareBoard() {
        // Add four white and red lands to the bottom left corner of the map
        board.make(new Move(new Flower(new Position(1, 1), new Position(2, 1),
                new Position(1, 2)), new Flower(new Position(1, 2), new
                Position(1, 3), new Position(2, 2))));
        board.make(new Move(new Flower(new Position(2, 1), new Position(1, 2),
                new Position(2, 2)), new Flower(new Position(2, 1), new
                Position(3, 1), new Position(2, 2))));

    }

    @Test
    public void makeValidLandMoveOnEmptyFieldInFirstLand() {
        prepareBoard();

        board.make(createMove(true, new Flower(new Position(3, 1), new Position
                (2, 2), new Position(3, 2))));

        assertEquals(Status.Ok, viewer.getStatus());
    }

    @Test
    public void makeInvalidLandMoveOnWhiteLandInFirstLand() {
        prepareBoard();

        board.make(createMove(true, new Flower(new Position(1, 2), new
                Position(1, 3), new Position(2, 2))));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void makeInvalidLandMoveOnRedLandInFirstLand() {
        prepareBoard();

        board.make(createMove(true, new Flower(new Position(2, 1), new
                Position(3, 1), new Position(2, 2))));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void makeValidLandMoveOnEmptyFieldInSecondLand() {
        prepareBoard();

        board.make(createMove(false, new Flower(new Position(3, 1), new Position
                (2, 2), new Position(3, 2))));

        assertEquals(Status.Ok, viewer.getStatus());
    }

    @Test
    public void makeInvalidLandMoveOnWhiteLandInSecondLand() {
        prepareBoard();

        board.make(createMove(false, new Flower(new Position(1, 2), new
                Position(1, 3), new Position(2, 2))));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void makeInvalidLandMoveOnRedLandInSecondLand() {
        prepareBoard();

        board.make(createMove(false, new Flower(new Position(2, 1), new
                Position(3, 1), new Position(2, 2))));

        assertEquals(Status.Illegal, viewer.getStatus());
    }
}
