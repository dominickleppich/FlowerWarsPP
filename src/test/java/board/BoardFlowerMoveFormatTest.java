package board;

import flowerwarspp.preset.*;
import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class BoardFlowerMoveFormatTest {
    private static final int BOARD_SIZE = 10;
    private static final Flower VALID_FLOWER = new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3));
    private Board board;
    private Viewer viewer;
    private Flower flower;

    // ------------------------------------------------------------
    private Status expectedStatus;

    public BoardFlowerMoveFormatTest(Flower flower, Status expected) {
        this.flower = flower;
        this.expectedStatus = expected;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                // Up valid
                {
                        new Flower(new Position(5, 5), new Position(6, 5), new Position(5, 6)), Status.Ok
                }, {
                        new Flower(new Position(5, 5), new Position(5, 6), new Position(6, 5)), Status.Ok
                }, {
                        new Flower(new Position(5, 5), new Position(6, 4), new Position(5, 4)), Status.Ok
                },
                // Down valid
                {
                        new Flower(new Position(5, 5), new Position(6, 5), new Position(6, 4)), Status.Ok
                }, {
                        new Flower(new Position(5, 5), new Position(5, 6), new Position(4, 6)), Status.Ok
                }, {
                        new Flower(new Position(5, 5), new Position(6, 4), new Position(6, 5)), Status.Ok
                },
                // Invalid (one distance too wide)
                {
                        new Flower(new Position(5, 5), new Position(6, 5), new Position(6, 6)), Status.Illegal
                }, {
                        new Flower(new Position(5, 5), new Position(6, 5), new Position(7, 4)), Status.Illegal
                }, {
                        new Flower(new Position(5, 5), new Position(6, 5), new Position(4, 6)), Status.Illegal
                }, {
                        new Flower(new Position(5, 5), new Position(6, 5), new Position(5, 4)), Status.Illegal
                },

                {
                        new Flower(new Position(5, 5), new Position(5, 6), new Position(6, 6)), Status.Illegal
                }, {
                        new Flower(new Position(5, 5), new Position(5, 6), new Position(4, 7)), Status.Illegal
                }, {
                        new Flower(new Position(5, 5), new Position(5, 6), new Position(6, 4)), Status.Illegal
                }, {
                        new Flower(new Position(5, 5), new Position(5, 6), new Position(4, 5)), Status.Illegal
                },

                {
                        new Flower(new Position(5, 5), new Position(6, 4), new Position(7, 4)), Status.Illegal
                }, {
                        new Flower(new Position(5, 5), new Position(6, 4), new Position(6, 4)), Status.Illegal
                }, {
                        new Flower(new Position(5, 5), new Position(6, 4), new Position(5, 6)), Status.Illegal
                }, {
                        new Flower(new Position(5, 5), new Position(6, 4), new Position(4, 5)), Status.Illegal
                },
                // Invalid in a line
                {
                        new Flower(new Position(5, 5), new Position(6, 5), new Position(7, 5)), Status.Illegal
                }, {
                        new Flower(new Position(5, 5), new Position(6, 5), new Position(4, 5)), Status.Illegal
                }, {
                        new Flower(new Position(5, 5), new Position(5, 6), new Position(5, 7)), Status.Illegal
                }, {
                        new Flower(new Position(5, 5), new Position(5, 6), new Position(5, 4)), Status.Illegal
                }, {
                        new Flower(new Position(5, 5), new Position(6, 4), new Position(7, 3)), Status.Illegal
                }, {
                        new Flower(new Position(5, 5), new Position(6, 4), new Position(4, 6)), Status.Illegal
                },
                // Invalid arbitrary
                {
                        new Flower(new Position(5, 5), new Position(5, 8), new Position(7, 2)), Status.Illegal
                },
                // Invalid same flower
                {VALID_FLOWER, Status.Illegal}
        });
    }

    @Before
    public void init() {
        board = TestBoardFactory.createInstance(BOARD_SIZE);
        viewer = board.viewer();
    }

    @Test
    public void testFlowerMovesFirstFlower() {
        board.make(new Move(flower, VALID_FLOWER));
        assertEquals("First flower " + flower.toString(), expectedStatus, viewer.getStatus());
    }

    // ------------------------------------------------------------

    @Test
    public void testFlowerMovesSecondFlower() {
        board.make(new Move(VALID_FLOWER, flower));
        assertEquals("Second flower " + flower.toString(), expectedStatus, viewer.getStatus());
    }

}
