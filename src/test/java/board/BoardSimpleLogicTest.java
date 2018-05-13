package board;

import flowerwarspp.board.*;
import flowerwarspp.preset.*;
import org.junit.*;

import static org.junit.Assert.*;

public class BoardSimpleLogicTest {
    private static final int BOARD_SIZE = 5;
    private Board board;
    private Viewer viewer;

    @Before
    public void init() {
        board = new BoardImpl(BOARD_SIZE);
        viewer = board.viewer();
    }

    // ------------------------------------------------------------
    // * Constructor and empty board values *

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
    public void newBoardHasNoFlowersForRedPlayer() {
        assertTrue(viewer.getFlowers(PlayerColor.Red)
                         .isEmpty());
    }

    @Test
    public void newBoardHasNoFlowersForBluePlayer() {
        assertTrue(viewer.getFlowers(PlayerColor.Blue)
                         .isEmpty());
    }

    @Test
    public void newBoardHasNoDitchesForRedPlayer() {
        assertTrue(viewer.getDitches(PlayerColor.Red)
                         .isEmpty());
    }

    @Test
    public void newBoardHasNoDitchesForBluePlayer() {
        assertTrue(viewer.getDitches(PlayerColor.Blue)
                         .isEmpty());
    }

    // ------------------------------------------------------------

    @Test
    public void changeTurnAfterValidFlowerMove() {
        Flower flowerA = new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3));
        Flower flowerB = new Flower(new Position(3, 2), new Position(2, 3), new Position(3, 3));
        Move m = new Move(flowerA, flowerB);

        board.make(m);
        assertEquals(PlayerColor.Blue, viewer.getTurn());
    }

    @Test
    public void firstMoveIsHasStatusOk() {
        Flower flowerA = new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3));
        Flower flowerB = new Flower(new Position(3, 2), new Position(2, 3), new Position(3, 3));
        Move m = new Move(flowerA, flowerB);

        board.make(m);
        assertEquals(Status.Ok, viewer.getStatus());
    }

    @Test
    public void firstMoveIsDoneByRedPlayer() {
        Flower flowerA = new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3));
        Flower flowerB = new Flower(new Position(3, 2), new Position(2, 3), new Position(3, 3));
        Move m = new Move(flowerA, flowerB);

        board.make(m);

        boolean firstContained = viewer.getFlowers(PlayerColor.Red)
                                       .contains(flowerA);
        boolean secondContained = viewer.getFlowers(PlayerColor.Red)
                                        .contains(flowerB);

        assertEquals("First Flower is contained", true, firstContained);
        assertEquals("Second Flower is contained", true, secondContained);
    }

    @Test
    public void firstMoveDoesNotSetAnyRedDitches() {
        Flower flowerA = new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3));
        Flower flowerB = new Flower(new Position(3, 2), new Position(2, 3), new Position(3, 3));
        Move m = new Move(flowerA, flowerB);

        board.make(m);

        assertTrue(viewer.getDitches(PlayerColor.Red)
                         .isEmpty());
    }

    @Test
    public void firstMoveDoesNotSetAnyBlueDitches() {
        Flower flowerA = new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3));
        Flower flowerB = new Flower(new Position(3, 2), new Position(2, 3), new Position(3, 3));
        Move m = new Move(flowerA, flowerB);

        board.make(m);

        assertTrue(viewer.getDitches(PlayerColor.Blue)
                         .isEmpty());
    }

    @Test
    public void firstMoveDoesNotAddBlueFlowers() {
        Flower flowerA = new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3));
        Flower flowerB = new Flower(new Position(3, 2), new Position(2, 3), new Position(3, 3));
        Move m = new Move(flowerA, flowerB);

        board.make(m);

        assertTrue(viewer.getFlowers(PlayerColor.Blue)
                         .isEmpty());
    }

    @Test
    public void firstMoveDoesOnlyAddTwoRedFlowers() {
        Flower flowerA = new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3));
        Flower flowerB = new Flower(new Position(3, 2), new Position(2, 3), new Position(3, 3));
        Move m = new Move(flowerA, flowerB);

        board.make(m);

        assertEquals(2, viewer.getFlowers(PlayerColor.Red)
                              .size());
    }

    @Test
    public void firstPlayerCanSurrender() {
        Move m = new Move(MoveType.Surrender);

        board.make(m);

        assertEquals(Status.BlueWin, viewer.getStatus());
    }

    @Test
    public void secondPlayerCanSurrender() {
        Flower flowerA = new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3));
        Flower flowerB = new Flower(new Position(3, 2), new Position(2, 3), new Position(3, 3));
        Move m = new Move(flowerA, flowerB);

        board.make(m);

        m = new Move(MoveType.Surrender);

        board.make(m);

        assertEquals(Status.RedWin, viewer.getStatus());
    }

    @Test
    public void firstPlayerCannotEndGame() {
        Move m = new Move(MoveType.End);

        board.make(m);

        assertEquals(Status.Illegal, viewer.getStatus());
    }

    @Test
    public void secondPlayerCannotEndGame() {
        Flower flowerA = new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3));
        Flower flowerB = new Flower(new Position(3, 2), new Position(2, 3), new Position(3, 3));
        Move m = new Move(flowerA, flowerB);

        board.make(m);

        m = new Move(MoveType.End);

        board.make(m);

        assertEquals(Status.Illegal, viewer.getStatus());
    }
}
