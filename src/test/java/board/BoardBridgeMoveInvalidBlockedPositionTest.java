package board;

import flowerwarspp.board.*;
import flowerwarspp.preset.*;
import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class BoardBridgeMoveInvalidBlockedPositionTest {
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
        Land r1, r2, r3, r4, r5, r6, r7, r8, r9, r10;
        r1 = new Land(new Position(2, 2), new Position(3, 2), new Position(2,
                3));
        r2 = new Land(new Position(3, 2), new Position(4, 2), new Position(3,
                3));
        r3 = new Land(new Position(3, 2), new Position(2, 3), new Position(3,
                3));
        r4 = new Land(new Position(2, 3), new Position(3, 3), new Position(2,
                4));
        r5 = new Land(new Position(1, 5), new Position(2, 5), new Position(2,
                4));
        r6 = new Land(new Position(2, 4), new Position(3, 4), new Position(2,
                5));
        r7 = new Land(new Position(2, 4), new Position(3, 4), new Position(3,
                3));
        r8 = new Land(new Position(3, 3), new Position(4, 3), new Position(3,
                4));
        r9 = new Land(new Position(3, 3), new Position(4, 3), new Position(4,
                2));
        r10 = new Land(new Position(4, 2), new Position(5, 2), new Position
                (4, 3));

        // Set up test scenario
        board.make(new Move(w1, w2));
        board.make(new Move(r1, r2));
        board.make(new Move(w3, w4));
        board.make(new Move(r3, r4));
        board.make(new Move(new Bridge(new Position(5, 5), new Position(4, 5)
        )));
        board.make(new Move(r5, r6));
        board.make(new Move(new Bridge(new Position(6, 5), new Position(7, 5)
        )));
        board.make(new Move(r7, r8));
        board.make(new Move(new Bridge(new Position(5, 6), new Position(4, 7)
        )));
        board.make(new Move(r9, r10));

    }

    // ------------------------------------------------------------

    private Bridge bridge;
    private Status expectedStatus;

    public BoardBridgeMoveInvalidBlockedPositionTest(Bridge bridge, Status expected) {
        this.bridge = bridge;
        this.expectedStatus = expected;
    }

    @Test
    public void testBridgeMove() {
        board.make(new Move(bridge));
        assertEquals("Invalid bridge blocked position " + bridge.toString(), expectedStatus,
                viewer
                        .getStatus());
    }

    // ------------------------------------------------------------

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                // Bridges starting on blocked positions
                {new Bridge(new Position(5, 5), new Position(4, 5)), Status
                        .Illegal},
                {new Bridge(new Position(5, 5), new Position(5, 4)), Status
                        .Illegal},
                {new Bridge(new Position(6, 5), new Position(7, 5)), Status
                        .Illegal},
                {new Bridge(new Position(6, 5), new Position(7, 4)), Status
                        .Illegal},
                {new Bridge(new Position(5, 6), new Position(5, 7)), Status
                        .Illegal},
                {new Bridge(new Position(5, 6), new Position(4, 7)), Status
                        .Illegal}
        });
    }
}
