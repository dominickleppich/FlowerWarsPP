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

    // ------------------------------------------------------------

    @Test
    public void changeTurnAfterValidLandMove() {
        Move m = new Move(new Land(new Position(2, 2), new Position(3, 2),
                new Position(2, 3)),
                new Land(new Position(3, 2), new Position(2, 3), new Position
                        (3, 3)));
        board.make(m);
        assertEquals(PlayerColor.Red, viewer.getTurn());
    }

}
