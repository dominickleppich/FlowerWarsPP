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

    // ------------------------------------------------------------

    @Test
    public void makeValidBottomLeftFlowerMoveInFirstFlower() {
        board.make(createMove(true, new Flower(new Position(1, 1), new Position
                (2, 1), new Position(1, 2))));

        assertEquals(Status.Ok, viewer.getStatus());
    }

    @Test
    public void makeValidBottomRightFlowerMoveInFirstFlower() {
        board.make(createMove(true, new Flower(new Position(3, 1), new Position
                (4, 1), new Position(3, 2))));

        assertEquals(Status.Ok, viewer.getStatus());
    }

    @Test
    public void makeValidTopFlowerMoveInFirstFlower() {
        board.make(createMove(true, new Flower(new Position(1, 3), new Position
                (2, 3), new Position(1, 4))));

        assertEquals(Status.Ok, viewer.getStatus());
    }

    @Test
    public void makeValidBorderFlowerMoveInFirstFlower() {
        board.make(createMove(true, new Flower(new Position(2, 2), new Position
                (3, 2), new Position(2, 3))));

        assertEquals(Status.Ok, viewer.getStatus());
    }

    @Test
    public void makeValidBottomLeftFlowerMoveInSecondFlower() {
        board.make(createMove(false, new Flower(new Position(1, 1), new Position
                (2, 1), new Position(1, 2))));

        assertEquals(Status.Ok, viewer.getStatus());
    }

    @Test
    public void makeValidBottomRightFlowerMoveInSecondFlower() {
        board.make(createMove(false, new Flower(new Position(3, 1), new Position
                (4, 1), new Position(3, 2))));

        assertEquals(Status.Ok, viewer.getStatus());
    }

    @Test
    public void makeValidTopFlowerMoveInSecondFlower() {
        board.make(createMove(false, new Flower(new Position(1, 3), new Position
                (2, 3), new Position(1, 4))));

        assertEquals(Status.Ok, viewer.getStatus());
    }

    @Test
    public void makeValidBorderFlowerMoveInSecondFlower() {
        board.make(createMove(false, new Flower(new Position(2, 2), new Position
                (3, 2), new Position(2, 3))));

        assertEquals(Status.Ok, viewer.getStatus());
    }

    @Test
    public void makeInvalidTopOutsideFlowerMoveInFirstFlower() {
        board.make(createMove(true, new Flower(new Position(1, 4), new Position
                (2, 4), new Position(1, 5))));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void makeInvalidBottomRightOutsideFlowerMoveInFirstFlower() {
        board.make(createMove(true, new Flower(new Position(4, 1), new Position
                (5, 1), new Position(4, 2))));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void makeInvalidTopBorderOutsideFlowerMoveInFirstFlower() {
        board.make(createMove(true, new Flower(new Position(2, 3), new Position
                (1, 4), new Position(2, 4))));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void makeInvalidMiddleBorderOutsideFlowerMoveInFirstFlower() {
        board.make(createMove(true, new Flower(new Position(3, 2), new Position
                (2, 3), new Position(3, 3))));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void makeInvalidBottomBorderOutsideFlowerMoveInFirstFlower() {
        board.make(createMove(true, new Flower(new Position(4, 1), new Position
                (3, 2), new Position(4, 2))));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void makeInvalidTopOutsideFlowerMoveInSecondFlower() {
        board.make(createMove(false, new Flower(new Position(1, 4), new Position
                (2, 4), new Position(1, 5))));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void makeInvalidBottomRightOutsideFlowerMoveInSecondFlower() {
        board.make(createMove(false, new Flower(new Position(4, 1), new Position
                (5, 1), new Position(4, 2))));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void makeInvalidTopBorderOutsideFlowerMoveInSecondFlower() {
        board.make(createMove(false, new Flower(new Position(2, 3), new Position
                (1, 4), new Position(2, 4))));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void makeInvalidMiddleBorderOutsideFlowerMoveInSecondFlower() {
        board.make(createMove(false, new Flower(new Position(3, 2), new Position
                (2, 3), new Position(3, 3))));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void makeInvalidBottomBorderOutsideFlowerMoveInSecondFlower() {
        board.make(createMove(false, new Flower(new Position(4, 1), new Position
                (3, 2), new Position(4, 2))));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    // ------------------------------------------------------------
    // ** Flower moves only on empty fields allowed **

    private void prepareBoard() {
        // Add four white and red Flowers to the bottom left corner of the map
        board.make(new Move(new Flower(new Position(1, 1), new Position(2, 1),
                new Position(1, 2)), new Flower(new Position(1, 2), new
                Position(1, 3), new Position(2, 2))));
        board.make(new Move(new Flower(new Position(2, 1), new Position(1, 2),
                new Position(2, 2)), new Flower(new Position(2, 1), new
                Position(3, 1), new Position(2, 2))));

    }

    @Test
    public void makeValidFlowerMoveOnEmptyFieldInFirstFlower() {
        prepareBoard();

        board.make(createMove(true, new Flower(new Position(3, 1), new Position
                (2, 2), new Position(3, 2))));

        assertEquals(Status.Ok, viewer.getStatus());
    }

    @Test
    public void makeInvalidFlowerMoveOnRedFlowerInFirstFlower() {
        prepareBoard();

        board.make(createMove(true, new Flower(new Position(1, 2), new
                Position(1, 3), new Position(2, 2))));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void makeInvalidFlowerMoveOnBlueFlowerInFirstFlower() {
        prepareBoard();

        board.make(createMove(true, new Flower(new Position(2, 1), new
                Position(3, 1), new Position(2, 2))));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void makeValidFlowerMoveOnEmptyFieldInSecondFlower() {
        prepareBoard();

        board.make(createMove(false, new Flower(new Position(3, 1), new Position
                (2, 2), new Position(3, 2))));

        assertEquals(Status.Ok, viewer.getStatus());
    }

    @Test
    public void makeInvalidFlowerMoveOnRedFlowerInSecondFlower() {
        prepareBoard();

        board.make(createMove(false, new Flower(new Position(1, 2), new
                Position(1, 3), new Position(2, 2))));

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void makeInvalidFlowerMoveOnBlueFlowerInSecondFlower() {
        prepareBoard();

        board.make(createMove(false, new Flower(new Position(2, 1), new
                Position(3, 1), new Position(2, 2))));

        assertEquals(Status.Illegal, viewer.getStatus());
    }
}
