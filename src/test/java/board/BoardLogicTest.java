package board;

import flowerwarspp.board.*;
import flowerwarspp.preset.*;
import org.junit.*;

import static org.junit.Assert.*;

public class BoardLogicTest {
    private static final int BOARD_SIZE = 3;
    private Board board;
    private Viewer viewer;

    @Before
    public void init() {
        board = new BoardImpl(BOARD_SIZE);
        viewer = board.viewer();
    }

    // ------------------------------------------------------------

    @Test
    public void createNewBoardWorks() {
        board = new BoardImpl(BOARD_SIZE);
        assertNotNull(board);
    }

    @Test
    public void newBoardHasCorrectSize() {
        assertEquals(BOARD_SIZE, viewer.getSize());
    }

    @Test
    public void newBoardStartsWithCorrectPlayer() {
        assertEquals(PlayerColor.Red, viewer.getTurn());
    }

    @Test
    public void newBoardHasCorrectStatus() {
        assertEquals(Status.Ok, viewer.getStatus());
    }

    @Test
    public void newBoardHasNoLandsForWhitePlayer() {
        assertTrue(viewer.getFlowers(PlayerColor.Red).isEmpty());
    }

    @Test
    public void newBoardHasNoLandsForRedPlayer() {
        assertTrue(viewer.getFlowers(PlayerColor.Blue).isEmpty());
    }

    @Test
    public void newBoardHasNoBridgesForWhitePlayer() {
        assertTrue(viewer.getDitches(PlayerColor.Red).isEmpty());
    }

    @Test
    public void newBoardHasNoBridgesForRedPlayer() {
        assertTrue(viewer.getDitches(PlayerColor.Blue).isEmpty());
    }

    // ------------------------------------------------------------

    @Test
    public void changeTurnAfterValidLandMove() {
        Flower flowerA = new Flower(new Position(2, 2), new Position(3, 2),
                new Position(2, 3));
        Flower flowerB = new Flower(new Position(3, 1), new Position(2, 2), new
                Position
                (3, 2));
        Move m = new Move(flowerA, flowerB);

        board.make(m);
        assertEquals(PlayerColor.Blue, viewer.getTurn());
    }

    @Test
    public void firstMoveIsHasStatusOk() {
        Flower flowerA = new Flower(new Position(2, 2), new Position(3, 2),
                new Position(2, 3));
        Flower flowerB = new Flower(new Position(3, 1), new Position(2, 2), new
                Position
                (3, 2));
        Move m = new Move(flowerA, flowerB);

        board.make(m);

        assertEquals(Status.Ok, viewer.getStatus());
    }

    @Test
    public void firstMoveIsDoneByWhitePlayer() {
        Flower flowerA = new Flower(new Position(2, 2), new Position(3, 2),
                new Position(2, 3));
        Flower flowerB = new Flower(new Position(3, 1), new Position(2, 2), new
                Position
                (3, 2));
        Move m = new Move(flowerA, flowerB);

        board.make(m);

        boolean firstContained = viewer.getFlowers(PlayerColor.Red).contains
                (flowerA);
        boolean secondContained = viewer.getFlowers(PlayerColor.Red).contains
                (flowerB);

        assertEquals("First land is contained", true, firstContained);
        assertEquals("Second land is contained", true, secondContained);
    }

    @Test
    public void firstMoveDoesNotSetAnyWhiteBridges() {
        Flower flowerA = new Flower(new Position(2, 2), new Position(3, 2),
                new Position(2, 3));
        Flower flowerB = new Flower(new Position(3, 1), new Position(2, 2), new
                Position
                (3, 2));
        Move m = new Move(flowerA, flowerB);

        board.make(m);

        assertTrue(viewer.getDitches(PlayerColor.Red).isEmpty());
    }

    @Test
    public void firstMoveDoesNotSetAnyRedBridges() {
        Flower flowerA = new Flower(new Position(2, 2), new Position(3, 2),
                new Position(2, 3));
        Flower flowerB = new Flower(new Position(3, 1), new Position(2, 2), new
                Position
                (3, 2));
        Move m = new Move(flowerA, flowerB);

        board.make(m);

        assertTrue(viewer.getDitches(PlayerColor.Blue).isEmpty());
    }

    @Test
    public void firstMoveDoesNotAddRedLands() {
        Flower flowerA = new Flower(new Position(2, 2), new Position(3, 2),
                new Position(2, 3));
        Flower flowerB = new Flower(new Position(3, 1), new Position(2, 2), new
                Position
                (3, 2));
        Move m = new Move(flowerA, flowerB);

        board.make(m);

        assertTrue(viewer.getFlowers(PlayerColor.Blue).isEmpty());
    }

    @Test
    public void firstMoveDoesOnlyAddTwoWhiteLands() {
        Flower flowerA = new Flower(new Position(2, 2), new Position(3, 2),
                new Position(2, 3));
        Flower flowerB = new Flower(new Position(3, 1), new Position(2, 2), new
                Position
                (3, 2));
        Move m = new Move(flowerA, flowerB);

        board.make(m);

        assertEquals(2, viewer.getFlowers(PlayerColor.Red).size());
    }
}
