package board;

import flowerwarspp.board.*;
import flowerwarspp.preset.*;
import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;

import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class BoardDitchMoveInvalidNoTargetingFlowerTest {
    private static final int BOARD_SIZE = 10;
    private Board board;
    private Viewer viewer;

    @Before
    public void init() {
        board = new BoardImpl(BOARD_SIZE);
        viewer = board.viewer();

        // Place three lands to make 6 bridges possible
        // Red lands
        Flower r1, r2, r3, r4;
        r1 = new Flower(new Position(5, 5), new Position(6, 5), new Position(5,
                6));
        r2 = new Flower(new Position(4, 4), new Position(5, 4), new Position(4,
                5));
        r3 = new Flower(new Position(7, 4), new Position(8, 4), new Position(7,
                5));
        r4 = new Flower(new Position(4, 7), new Position(5, 7), new Position(4,
                8));

        // Blue lands
        Flower b1, b2, b3, b4;
        b1 = new Flower(new Position(2, 2), new Position(3, 2), new Position(2,
                3));
        b2 = new Flower(new Position(3, 2), new Position(4, 2), new Position(3,
                3));
        b3 = new Flower(new Position(3, 2), new Position(2, 3), new Position(3,
                3));
        b4 = new Flower(new Position(2, 3), new Position(3, 3), new Position(2,
                4));
        // Set up test scenario
        board.make(new Move(r1, r2));
        board.make(new Move(b1, b2));
        board.make(new Move(r3, r4));
        board.make(new Move(b3, b4));
    }

    // ------------------------------------------------------------

    private Ditch ditch;
    private Status expectedStatus;

    public BoardDitchMoveInvalidNoTargetingFlowerTest(Ditch ditch, Status expected) {
        this.ditch = ditch;
        this.expectedStatus = expected;
    }

    @Test
    public void testBridgeMove() {
        board.make(new Move(ditch));
        assertEquals("Invalid ditch, no target flower  " + ditch.toString(), expectedStatus, viewer
                .getStatus());
    }

    // ------------------------------------------------------------

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                // Invalid ditch (length 1)
                {new Ditch(new Position(5, 5), new Position(4, 6)), Status.Illegal},
                {new Ditch(new Position(5, 5), new Position(6, 4)), Status.Illegal},
                {new Ditch(new Position(6, 5), new Position(6, 4)), Status.Illegal},
                {new Ditch(new Position(6, 5), new Position(6, 6)), Status.Illegal},
                {new Ditch(new Position(5, 6), new Position(4, 6)), Status.Illegal},
                {new Ditch(new Position(5, 6), new Position(6, 6)), Status.Illegal}
        });
    }
}
