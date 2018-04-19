package board;

import diavolopp.board.*;
import diavolopp.preset.*;
import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class BoardLandMoveTest {
    private static final int BOARD_SIZE = 10;
    private static final Land VALID_LAND = new Land(new Position(2, 2), new
            Position(3, 2), new Position(2, 3));
    private Board board;
    private Viewer viewer;

    @Before
    public void init() {
        board = new BoardImpl(BOARD_SIZE);
        viewer = board.viewer();
    }

    // ------------------------------------------------------------

    private Land land;
    private Status expectedStatus;

    public BoardLandMoveTest(Land land, Status expected) {
        this.land = land;
        this.expectedStatus = expected;
    }

    @Test
    public void testLandMovesFirstLand() {
        board.make(new Move(land, VALID_LAND));
        assertEquals("Land " + land.toString(), expectedStatus, viewer.getStatus());
    }

    @Test
    public void testLandMovesSecondLand() {
        board.make(new Move(VALID_LAND, land));
        assertEquals("Land " + land.toString(), expectedStatus, viewer.getStatus());
    }

    // ------------------------------------------------------------

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                // Up valid
                {new Land(new Position(5, 5), new Position(6, 5), new
                        Position(5, 6)), Status.Ok},
                {new Land(new Position(5, 5), new Position(5, 6), new
                        Position(6, 5)), Status.Ok},
                {new Land(new Position(5, 5), new Position(6, 4), new
                        Position(5, 4)), Status.Ok},
                // Down valid
                {new Land(new Position(5, 5), new Position(6, 5), new
                        Position(6, 4)), Status.Ok},
                {new Land(new Position(5, 5), new Position(5, 6), new
                        Position(4, 6)), Status.Ok},
                {new Land(new Position(5, 5), new Position(6, 4), new
                        Position(6, 5)), Status.Ok},
                // Invalid (one distance too wide)
                {new Land(new Position(5, 5), new Position(6, 5), new
                        Position(6, 6)), Status.Illegal},
                {new Land(new Position(5, 5), new Position(6, 5), new
                        Position(7, 4)), Status.Illegal},
                {new Land(new Position(5, 5), new Position(6, 5), new
                        Position(4, 6)), Status.Illegal},
                {new Land(new Position(5, 5), new Position(6, 5), new
                        Position(5, 4)), Status.Illegal},

                {new Land(new Position(5, 5), new Position(5, 6), new
                        Position(6, 6)), Status.Illegal},
                {new Land(new Position(5, 5), new Position(5, 6), new
                        Position(4, 7)), Status.Illegal},
                {new Land(new Position(5, 5), new Position(5, 6), new
                        Position(6, 4)), Status.Illegal},
                {new Land(new Position(5, 5), new Position(5, 6), new
                        Position(4, 5)), Status.Illegal},

                {new Land(new Position(5, 5), new Position(6, 4), new
                        Position(7, 4)), Status.Illegal},
                {new Land(new Position(5, 5), new Position(6, 4), new
                        Position(6, 4)), Status.Illegal},
                {new Land(new Position(5, 5), new Position(6, 4), new
                        Position(5, 6)), Status.Illegal},
                {new Land(new Position(5, 5), new Position(6, 4), new
                        Position(4, 5)), Status.Illegal},
                // Invalid in a line
                {new Land(new Position(5, 5), new Position(6, 5), new
                        Position(7, 5)), Status.Illegal},
                {new Land(new Position(5, 5), new Position(6, 5), new
                        Position(4, 5)), Status.Illegal},
                {new Land(new Position(5, 5), new Position(5, 6), new
                        Position(5, 7)), Status.Illegal},
                {new Land(new Position(5, 5), new Position(5, 6), new
                        Position(5, 4)), Status.Illegal},
                {new Land(new Position(5, 5), new Position(6, 4), new
                        Position(7, 3)), Status.Illegal},
                {new Land(new Position(5, 5), new Position(6, 4), new
                        Position(4, 6)), Status.Illegal},
                // Invalid arbitrary
                {new Land(new Position(5, 5), new Position(5, 8), new
                        Position(7, 2)), Status.Illegal}
        });
    }

}
