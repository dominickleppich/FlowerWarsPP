package board;

import diavolopp.board.*;
import diavolopp.preset.*;
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
        for (Land l : viewer.getLands(PlayerColor.White))
            fail("Land for white player found on empty board");
    }

    @Test
    public void newBoardHasNoLandsForRedPlayer() {
        for (Land l : viewer.getLands(PlayerColor.Red))
            fail("Land for red player found on empty board");
    }

    @Test
    public void newBoardHasNoBridgesForWhitePlayer() {
        for (Bridge b : viewer.getBridges(PlayerColor.White))
            fail("Bridge for white player found on empty board");
    }

    @Test
    public void newBoardHasNoBridgesForRedPlayer() {
        for (Bridge b : viewer.getBridges(PlayerColor.Red))
            fail("Bridge for red player found on empty board");
    }

    // ------------------------------------------------------------

    @Test
    public void changeTurnAfterValidLandMove() {
        Land landA = new Land(new Position(2, 2), new Position(3, 2),
                new Position(2, 3));
        Land landB = new Land(new Position(3, 2), new Position(2, 3), new Position
                (3, 3));
        Move m = new Move(landA, landB);

        board.make(m);
        assertEquals(PlayerColor.Red, viewer.getTurn());
    }

    @Test
    public void firstMoveIsDoneByWhitePlayer() {
        Land landA = new Land(new Position(2, 2), new Position(3, 2),
                new Position(2, 3));
        Land landB = new Land(new Position(3, 2), new Position(2, 3), new Position
                (3, 3));
        Move m = new Move(landA, landB);

        board.make(m);

        boolean firstContained = false;
        boolean secondContained = false;
        for (Land l : viewer.getLands(PlayerColor.White)) {
            if (l.equals(landA))
                firstContained = true;
            if (l.equals(landB))
                secondContained = true;
        }

        assertEquals("First land is contained", true, firstContained);
        assertEquals("Second land is contained", true, secondContained);
    }
}
