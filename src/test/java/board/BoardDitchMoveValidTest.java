package board;

import flowerwarspp.preset.*;
import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class BoardDitchMoveValidTest {
    private static final int BOARD_SIZE = 10;
    private Board board;
    private Viewer viewer;
    private Ditch ditch;

    // ------------------------------------------------------------
    private Status expectedStatus;

    public BoardDitchMoveValidTest(Ditch ditch, Status expected) {
        this.ditch = ditch;
        this.expectedStatus = expected;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                // Normal valid Ditches
                {new Ditch(new Position(5, 5), new Position(4, 5)), Status.Ok},
                {new Ditch(new Position(5, 5), new Position(5, 4)), Status.Ok},
                {new Ditch(new Position(6, 5), new Position(7, 5)), Status.Ok},
                {new Ditch(new Position(6, 5), new Position(7, 4)), Status.Ok},
                {new Ditch(new Position(5, 6), new Position(5, 7)), Status.Ok},
                {new Ditch(new Position(5, 6), new Position(4, 7)), Status.Ok}
        });
    }

    @Before
    public void init() {
        board = TestBoardFactory.createInstance(BOARD_SIZE);
        viewer = board.viewer();

        // Place three Flowers to make 6 Ditches possible
        // Red Flowers
        Flower r1, r2, r3, r4;
        r1 = new Flower(new Position(5, 5), new Position(6, 5), new Position(5, 6));
        r2 = new Flower(new Position(4, 4), new Position(5, 4), new Position(4, 5));
        r3 = new Flower(new Position(7, 4), new Position(8, 4), new Position(7, 5));
        r4 = new Flower(new Position(4, 7), new Position(5, 7), new Position(4, 8));

        // Blue Flowers
        Flower b1, b2, b3, b4;
        b1 = new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3));
        b2 = new Flower(new Position(3, 2), new Position(4, 2), new Position(3, 3));
        b3 = new Flower(new Position(3, 2), new Position(2, 3), new Position(3, 3));
        b4 = new Flower(new Position(2, 3), new Position(3, 3), new Position(2, 4));
        // Set up test scenario
        board.make(new Move(r1, r2));
        board.make(new Move(b1, b2));
        board.make(new Move(r3, r4));
        board.make(new Move(b3, b4));
    }

    // ------------------------------------------------------------

    @Test
    public void testDitchMove() {
        board.make(new Move(ditch));
        assertEquals("Valid ditch " + ditch.toString(), expectedStatus, viewer.getStatus());
    }
}
