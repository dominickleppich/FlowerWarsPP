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
        assertEquals(PlayerColor.White, viewer.getTurn());
    }

    @Test
    public void newBoardHasCorrectStatus() {
        assertEquals(Status.Ok, viewer.getStatus());
    }

    @Test
    public void newBoardHasNoLandsForWhitePlayer() {
        assertTrue(viewer.getLands(PlayerColor.White).isEmpty());
    }

    @Test
    public void newBoardHasNoLandsForRedPlayer() {
        assertTrue(viewer.getLands(PlayerColor.Red).isEmpty());
    }

    @Test
    public void newBoardHasNoBridgesForWhitePlayer() {
        assertTrue(viewer.getBridges(PlayerColor.White).isEmpty());
    }

    @Test
    public void newBoardHasNoBridgesForRedPlayer() {
        assertTrue(viewer.getBridges(PlayerColor.Red).isEmpty());
    }

    // ------------------------------------------------------------

    @Test
    public void changeTurnAfterValidLandMove() {
        Land landA = new Land(new Position(2, 2), new Position(3, 2),
                new Position(2, 3));
        Land landB = new Land(new Position(3, 1), new Position(2, 2), new
                Position
                (3, 2));
        Move m = new Move(landA, landB);

        board.make(m);
        assertEquals(PlayerColor.Red, viewer.getTurn());
    }

    @Test
    public void firstMoveIsHasStatusOk() {
        Land landA = new Land(new Position(2, 2), new Position(3, 2),
                new Position(2, 3));
        Land landB = new Land(new Position(3, 1), new Position(2, 2), new
                Position
                (3, 2));
        Move m = new Move(landA, landB);

        board.make(m);

        assertEquals(Status.Ok, viewer.getStatus());
    }

    @Test
    public void firstMoveIsDoneByWhitePlayer() {
        Land landA = new Land(new Position(2, 2), new Position(3, 2),
                new Position(2, 3));
        Land landB = new Land(new Position(3, 1), new Position(2, 2), new
                Position
                (3, 2));
        Move m = new Move(landA, landB);

        board.make(m);

        boolean firstContained = viewer.getLands(PlayerColor.White).contains
                (landA);
        boolean secondContained = viewer.getLands(PlayerColor.White).contains
                (landB);

        assertEquals("First land is contained", true, firstContained);
        assertEquals("Second land is contained", true, secondContained);
    }

    @Test
    public void firstMoveDoesNotSetAnyWhiteBridges() {
        Land landA = new Land(new Position(2, 2), new Position(3, 2),
                new Position(2, 3));
        Land landB = new Land(new Position(3, 1), new Position(2, 2), new
                Position
                (3, 2));
        Move m = new Move(landA, landB);

        board.make(m);

        assertTrue(viewer.getBridges(PlayerColor.White).isEmpty());
    }

    @Test
    public void firstMoveDoesNotSetAnyRedBridges() {
        Land landA = new Land(new Position(2, 2), new Position(3, 2),
                new Position(2, 3));
        Land landB = new Land(new Position(3, 1), new Position(2, 2), new
                Position
                (3, 2));
        Move m = new Move(landA, landB);

        board.make(m);

        assertTrue(viewer.getBridges(PlayerColor.Red).isEmpty());
    }

    @Test
    public void firstMoveDoesNotAddRedLands() {
        Land landA = new Land(new Position(2, 2), new Position(3, 2),
                new Position(2, 3));
        Land landB = new Land(new Position(3, 1), new Position(2, 2), new
                Position
                (3, 2));
        Move m = new Move(landA, landB);

        board.make(m);

        assertTrue(viewer.getLands(PlayerColor.Red).isEmpty());
    }

    @Test
    public void firstMoveDoesOnlyAddTwoWhiteLands() {
        Land landA = new Land(new Position(2, 2), new Position(3, 2),
                new Position(2, 3));
        Land landB = new Land(new Position(3, 1), new Position(2, 2), new
                Position
                (3, 2));
        Move m = new Move(landA, landB);

        board.make(m);

        assertEquals(2, viewer.getLands(PlayerColor.White).size());
    }
}
