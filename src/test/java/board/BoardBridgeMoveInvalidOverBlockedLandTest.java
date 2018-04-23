package board;

import diavolopp.board.*;
import diavolopp.preset.*;
import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;

import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class BoardBridgeMoveInvalidOverBlockedLandTest {
    private static final int BOARD_SIZE = 10;
    private Board board;
    private Viewer viewer;

    @Before
    public void init() {
        board = new BoardImpl(BOARD_SIZE);
        viewer = board.viewer();

        // Place three lands to make 6 bridges possible
        // White lands
        Land w1, w2, w3, w4;
        w1 = new Land(new Position(5, 5), new Position(6, 5), new Position(5,
                6));
        w2 = new Land(new Position(4, 4), new Position(5, 4), new Position(4,
                5));
        w3 = new Land(new Position(7, 4), new Position(8, 4), new Position(7,
                5));
        w4 = new Land(new Position(4, 7), new Position(5, 7), new Position(4,
                8));

        // Red lands
        Land r1, r2, r3, r4;
        r1 = new Land(new Position(2, 2), new Position(3, 2), new Position(2,
                3));
        r2 = new Land(new Position(3, 2), new Position(4, 2), new Position(3,
                3));
        r3 = new Land(new Position(3, 2), new Position(2, 3), new Position(3,
                3));
        r4 = new Land(new Position(2, 3), new Position(3, 3), new Position(2,
                4));
        // Set up test scenario
        board.make(new Move(w1, w2));
        board.make(new Move(r1, r2));
        board.make(new Move(w3, w4));
        board.make(new Move(r3, r4));
    }

    // ------------------------------------------------------------

    private Bridge bridge;
    private Status expectedStatus;

    public BoardBridgeMoveInvalidOverBlockedLandTest(Bridge bridge, Status expected) {
        this.bridge = bridge;
        this.expectedStatus = expected;
    }

    @Test
    public void testBridgeMove() {
        board.make(new Move(bridge));
        assertEquals("Invalid bridge to no target land " + bridge.toString(), expectedStatus, viewer
                .getStatus());
    }

    // ------------------------------------------------------------

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new Bridge(new Position(5, 5), new Position(5, 6)), Status.Illegal},
                {new Bridge(new Position(5, 5), new Position(6, 5)), Status.Illegal},
                {new Bridge(new Position(6, 5), new Position(5, 6)), Status.Illegal}
        });
    }
}
