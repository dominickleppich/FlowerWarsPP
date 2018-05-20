package board;

import flowerwarspp.preset.*;
import org.junit.*;

import java.util.*;

import static org.junit.Assert.*;

public class BoardPointCalculationTest {
    private static final int BOARD_SIZE = 18;
    private Board board;
    private Viewer viewer;

    @Before
    public void init() {
        board = TestBoardFactory.createInstance(BOARD_SIZE);
        viewer = board.viewer();

        List<Move> replayMoves = new LinkedList<>();

        replayMoves.add(new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                new Flower(new Position(3, 2), new Position(2, 3), new Position(3, 3))));
        replayMoves.add(new Move(new Flower(new Position(16, 2), new Position(17, 2), new Position(16, 3)),
                new Flower(new Position(15, 3), new Position(14, 4), new Position(15, 4))));
        replayMoves.add(new Move(new Flower(new Position(3, 2), new Position(4, 2), new Position(3, 3)),
                new Flower(new Position(2, 4), new Position(3, 4), new Position(2, 5))));
        replayMoves.add(new Move(new Flower(new Position(13, 5), new Position(14, 5), new Position(13, 6)),
                new Flower(new Position(12, 6), new Position(11, 7), new Position(12, 7))));
        replayMoves.add(new Move(new Flower(new Position(3, 4), new Position(4, 4), new Position(3, 5)),
                new Flower(new Position(2, 5), new Position(3, 5), new Position(2, 6))));
        replayMoves.add(new Move(new Flower(new Position(10, 8), new Position(11, 8), new Position(10, 9)),
                new Flower(new Position(9, 9), new Position(8, 10), new Position(9, 10))));
        replayMoves.add(new Move(new Flower(new Position(3, 6), new Position(2, 7), new Position(3, 7)),
                new Flower(new Position(3, 6), new Position(4, 6), new Position(3, 7))));
        replayMoves.add(new Move(new Flower(new Position(7, 11), new Position(8, 11), new Position(7, 12)),
                new Flower(new Position(6, 12), new Position(5, 13), new Position(6, 13))));
        replayMoves.add(new Move(new Flower(new Position(5, 2), new Position(6, 2), new Position(5, 3)),
                new Flower(new Position(3, 7), new Position(2, 8), new Position(3, 8))));
        replayMoves.add(new Move(new Flower(new Position(4, 14), new Position(5, 14), new Position(4, 15)),
                new Flower(new Position(3, 15), new Position(2, 16), new Position(3, 16))));
        replayMoves.add(new Move(new Flower(new Position(5, 3), new Position(6, 3), new Position(5, 4)),
                new Flower(new Position(6, 3), new Position(5, 4), new Position(6, 4))));
        replayMoves.add(new Move(new Flower(new Position(14, 2), new Position(15, 2), new Position(14, 3)),
                new Flower(new Position(13, 3), new Position(12, 4), new Position(13, 4))));

        setupBoardState(replayMoves);
    }

    private void setupBoardState(List<Move> moves) {
        for (Move m : moves)
            board.make(m);
    }

    // ------------------------------------------------------------
    // * Test correct point calculation (for red player) *

    @Test
    public void playerHasNoPointsAtTheBeginning() {
        assertEquals(0, viewer.getPoints(PlayerColor.Red));
    }

    @Test
    public void playerHasOnePointAfterCreatingFirstGarden() {
        board.make(new Move(new Flower(new Position(4, 2), new Position(3, 3), new Position(4, 3)),
                new Flower(new Position(7, 2), new Position(8, 2), new Position(7, 3))));

        assertEquals(1, viewer.getPoints(PlayerColor.Red));
    }

    @Test
    public void playerHasTwoPointsAfterCreatingTwoGardensInTwoMoves() {
        board.make(new Move(new Flower(new Position(4, 2), new Position(3, 3), new Position(4, 3)),
                new Flower(new Position(7, 2), new Position(8, 2), new Position(7, 3))));
        board.make(new Move(new Flower(new Position(11, 5), new Position(12, 5), new Position(11, 6)),
                new Flower(new Position(10, 6), new Position(9, 7), new Position(10, 7))));
        board.make(new Move(new Flower(new Position(9, 2), new Position(8, 3), new Position(9, 3)),
                new Flower(new Position(3, 4), new Position(2, 5), new Position(3, 5))));

        assertEquals(2, viewer.getPoints(PlayerColor.Red));
    }

    @Test
    public void playerHasTwoPointsAfterCreatingTwoGardensInSameMove() {
        board.make(new Move(new Flower(new Position(4, 2), new Position(3, 3), new Position(4, 3)),
                new Flower(new Position(3, 4), new Position(2, 5), new Position(3, 5))));

        assertEquals(2, viewer.getPoints(PlayerColor.Red));
    }

    @Test
    public void playerHasThreePointsAfterCreatingTwoGardensAndDirectlyConnectingThem() {
        board.make(new Move(new Flower(new Position(4, 2), new Position(3, 3), new Position(4, 3)),
                new Flower(new Position(3, 4), new Position(2, 5), new Position(3, 5))));
        board.make(new Move(new Flower(new Position(11, 5), new Position(12, 5), new Position(11, 6)),
                new Flower(new Position(10, 6), new Position(9, 7), new Position(10, 7))));
        board.make(new Move(new Ditch(new Position(3, 3), new Position(2, 4))));

        assertEquals(3, viewer.getPoints(PlayerColor.Red));
    }

    @Test
    public void playerHasThreePointsAfterCreatingTwoGardensAndConnectingThroughOneFlowerBed() {
        board.make(new Move(new Flower(new Position(4, 2), new Position(3, 3), new Position(4, 3)),
                new Flower(new Position(3, 4), new Position(2, 5), new Position(3, 5))));
        board.make(new Move(new Flower(new Position(11, 5), new Position(12, 5), new Position(11, 6)),
                new Flower(new Position(10, 6), new Position(9, 7), new Position(10, 7))));
        board.make(new Move(new Ditch(new Position(4, 2), new Position(5, 2))));
        board.make(new Move(new Flower(new Position(8, 8), new Position(9, 8), new Position(8, 9)),
                new Flower(new Position(7, 9), new Position(6, 10), new Position(7, 10))));
        board.make(new Move(new Ditch(new Position(5, 3), new Position(4, 4))));

        assertEquals(3, viewer.getPoints(PlayerColor.Red));
    }

    @Test
    public void playerHasThreePointsAfterCreatingTwoGardensAndConnectingThroughTwoFlowerBeds() {
        board.make(new Move(new Flower(new Position(4, 2), new Position(3, 3), new Position(4, 3)),
                new Flower(new Position(3, 4), new Position(2, 5), new Position(3, 5))));
        board.make(new Move(new Flower(new Position(11, 5), new Position(12, 5), new Position(11, 6)),
                new Flower(new Position(10, 6), new Position(9, 7), new Position(10, 7))));
        board.make(new Move(new Ditch(new Position(5, 2), new Position(4, 3))));
        board.make(new Move(new Flower(new Position(8, 8), new Position(9, 8), new Position(8, 9)),
                new Flower(new Position(7, 9), new Position(6, 10), new Position(7, 10))));
        board.make(new Move(new Ditch(new Position(6, 2), new Position(6, 3))));
        board.make(new Move(new Flower(new Position(5, 11), new Position(6, 11), new Position(5, 12)),
                new Flower(new Position(4, 12), new Position(3, 13), new Position(4, 13))));
        board.make(new Move(new Ditch(new Position(4, 4), new Position(5, 4))));

        assertEquals(3, viewer.getPoints(PlayerColor.Red));
    }

    @Test
    public void playerHasFourPointsAfterCreatingFourGardens() {
        board.make(new Move(new Flower(new Position(4, 2), new Position(3, 3), new Position(4, 3)),
                new Flower(new Position(3, 4), new Position(2, 5), new Position(3, 5))));
        board.make(new Move(new Flower(new Position(11, 5), new Position(12, 5), new Position(11, 6)),
                new Flower(new Position(10, 6), new Position(9, 7), new Position(10, 7))));
        board.make(new Move(new Flower(new Position(6, 2), new Position(5, 3), new Position(6, 3)),
                new Flower(new Position(2, 7), new Position(3, 7), new Position(2, 8))));
        board.make(new Move(new Flower(new Position(8, 8), new Position(9, 8), new Position(8, 9)),
                new Flower(new Position(7, 9), new Position(6, 10), new Position(7, 10))));

        assertEquals(4, viewer.getPoints(PlayerColor.Red));
    }

    @Test
    public void playerHasSixPointsAfterCreatingFourGardensAndConnectingTwoEach() {
        board.make(new Move(new Flower(new Position(4, 2), new Position(3, 3), new Position(4, 3)),
                new Flower(new Position(3, 4), new Position(2, 5), new Position(3, 5))));
        board.make(new Move(new Flower(new Position(11, 5), new Position(12, 5), new Position(11, 6)),
                new Flower(new Position(10, 6), new Position(9, 7), new Position(10, 7))));
        board.make(new Move(new Flower(new Position(6, 2), new Position(5, 3), new Position(6, 3)),
                new Flower(new Position(2, 7), new Position(3, 7), new Position(2, 8))));
        board.make(new Move(new Flower(new Position(8, 8), new Position(9, 8), new Position(8, 9)),
                new Flower(new Position(7, 9), new Position(6, 10), new Position(7, 10))));
        board.make(new Move(new Ditch(new Position(4, 2), new Position(5, 2))));
        board.make(new Move(new Flower(new Position(5, 11), new Position(6, 11), new Position(5, 12)),
                new Flower(new Position(4, 12), new Position(3, 13), new Position(4, 13))));
        board.make(new Move(new Ditch(new Position(2, 6), new Position(3, 6))));

        assertEquals(6, viewer.getPoints(PlayerColor.Red));
    }

    @Test
    public void playerHasSevenPointsAfterCreatingFourGardensAndConnectingThreeOfThem() {
        board.make(new Move(new Flower(new Position(4, 2), new Position(3, 3), new Position(4, 3)),
                new Flower(new Position(3, 4), new Position(2, 5), new Position(3, 5))));
        board.make(new Move(new Flower(new Position(11, 5), new Position(12, 5), new Position(11, 6)),
                new Flower(new Position(10, 6), new Position(9, 7), new Position(10, 7))));
        board.make(new Move(new Flower(new Position(6, 2), new Position(5, 3), new Position(6, 3)),
                new Flower(new Position(2, 7), new Position(3, 7), new Position(2, 8))));
        board.make(new Move(new Flower(new Position(8, 8), new Position(9, 8), new Position(8, 9)),
                new Flower(new Position(7, 9), new Position(6, 10), new Position(7, 10))));
        board.make(new Move(new Ditch(new Position(2, 6), new Position(2, 7))));
        board.make(new Move(new Flower(new Position(5, 11), new Position(6, 11), new Position(5, 12)),
                new Flower(new Position(4, 12), new Position(3, 13), new Position(4, 13))));
        board.make(new Move(new Ditch(new Position(3, 3), new Position(3, 4))));

        assertEquals(7, viewer.getPoints(PlayerColor.Red));
    }

    @Test
    public void playerHasTenPointsAfterCreatingFourGardensAndConnectingAllOfThemDirectly() {
        board.make(new Move(new Flower(new Position(4, 2), new Position(3, 3), new Position(4, 3)),
                new Flower(new Position(3, 4), new Position(2, 5), new Position(3, 5))));
        board.make(new Move(new Flower(new Position(11, 5), new Position(12, 5), new Position(11, 6)),
                new Flower(new Position(10, 6), new Position(9, 7), new Position(10, 7))));
        board.make(new Move(new Flower(new Position(6, 2), new Position(5, 3), new Position(6, 3)),
                new Flower(new Position(2, 7), new Position(3, 7), new Position(2, 8))));
        board.make(new Move(new Flower(new Position(8, 8), new Position(9, 8), new Position(8, 9)),
                new Flower(new Position(7, 9), new Position(6, 10), new Position(7, 10))));
        board.make(new Move(new Ditch(new Position(2, 6), new Position(2, 7))));
        board.make(new Move(new Flower(new Position(5, 11), new Position(6, 11), new Position(5, 12)),
                new Flower(new Position(4, 12), new Position(3, 13), new Position(4, 13))));
        board.make(new Move(new Ditch(new Position(4, 4), new Position(5, 4))));
        board.make(new Move(new Flower(new Position(12, 2), new Position(13, 2), new Position(12, 3)),
                new Flower(new Position(2, 14), new Position(3, 14), new Position(2, 15))));
        board.make(new Move(new Ditch(new Position(5, 2), new Position(4, 3))));

        assertEquals(10, viewer.getPoints(PlayerColor.Red));
    }

    @Test
    public void playerHasTenPointsAfterCreatingFourGardensAndConnectingAllOfThemDirectlyMultipleTimes() {
        board.make(new Move(new Flower(new Position(4, 2), new Position(3, 3), new Position(4, 3)),
                new Flower(new Position(3, 4), new Position(2, 5), new Position(3, 5))));
        board.make(new Move(new Flower(new Position(11, 5), new Position(12, 5), new Position(11, 6)),
                new Flower(new Position(10, 6), new Position(9, 7), new Position(10, 7))));
        board.make(new Move(new Flower(new Position(6, 2), new Position(5, 3), new Position(6, 3)),
                new Flower(new Position(2, 7), new Position(3, 7), new Position(2, 8))));
        board.make(new Move(new Flower(new Position(8, 8), new Position(9, 8), new Position(8, 9)),
                new Flower(new Position(7, 9), new Position(6, 10), new Position(7, 10))));
        board.make(new Move(new Ditch(new Position(2, 6), new Position(2, 7))));
        board.make(new Move(new Flower(new Position(5, 11), new Position(6, 11), new Position(5, 12)),
                new Flower(new Position(4, 12), new Position(3, 13), new Position(4, 13))));
        board.make(new Move(new Ditch(new Position(3, 5), new Position(3, 6))));
        board.make(new Move(new Flower(new Position(12, 2), new Position(13, 2), new Position(12, 3)),
                new Flower(new Position(2, 14), new Position(3, 14), new Position(2, 15))));
        board.make(new Move(new Ditch(new Position(4, 4), new Position(5, 4))));
        board.make(new Move(new Flower(new Position(10, 4), new Position(11, 4), new Position(10, 5)),
                new Flower(new Position(9, 5), new Position(8, 6), new Position(9, 6))));
        board.make(new Move(new Ditch(new Position(4, 2), new Position(5, 2))));
        board.make(new Move(new Flower(new Position(7, 7), new Position(8, 7), new Position(7, 8)),
                new Flower(new Position(6, 8), new Position(5, 9), new Position(6, 9))));
        board.make(new Move(new Ditch(new Position(3, 3), new Position(2, 4))));
        board.make(new Move(new Flower(new Position(4, 10), new Position(5, 10), new Position(4, 11)),
                new Flower(new Position(3, 11), new Position(2, 12), new Position(3, 12))));
        board.make(new Move(new Ditch(new Position(4, 3), new Position(3, 4))));

        assertEquals(10, viewer.getPoints(PlayerColor.Red));
    }

    @Test
    public void playerHasTenPointsAfterCreatingFourGardensAndConnectingAllOfThemThroughSomeFlowerBeds() {
        board.make(new Move(new Flower(new Position(4, 2), new Position(3, 3), new Position(4, 3)),
                new Flower(new Position(3, 4), new Position(2, 5), new Position(3, 5))));
        board.make(new Move(new Flower(new Position(11, 5), new Position(12, 5), new Position(11, 6)),
                new Flower(new Position(10, 6), new Position(9, 7), new Position(10, 7))));
        board.make(new Move(new Flower(new Position(6, 2), new Position(5, 3), new Position(6, 3)),
                new Flower(new Position(2, 7), new Position(3, 7), new Position(2, 8))));
        board.make(new Move(new Flower(new Position(8, 8), new Position(9, 8), new Position(8, 9)),
                new Flower(new Position(7, 9), new Position(6, 10), new Position(7, 10))));
        board.make(new Move(new Flower(new Position(8, 2), new Position(7, 3), new Position(8, 3)),
                new Flower(new Position(7, 3), new Position(8, 3), new Position(7, 4))));
        board.make(new Move(new Flower(new Position(5, 11), new Position(6, 11), new Position(5, 12)),
                new Flower(new Position(4, 12), new Position(3, 13), new Position(4, 13))));
        board.make(new Move(new Flower(new Position(5, 6), new Position(4, 7), new Position(5, 7)),
                new Flower(new Position(5, 6), new Position(6, 6), new Position(5, 7))));
        board.make(new Move(new Flower(new Position(12, 2), new Position(13, 2), new Position(12, 3)),
                new Flower(new Position(11, 3), new Position(10, 4), new Position(11, 4))));
        board.make(new Move(new Flower(new Position(8, 4), new Position(7, 5), new Position(8, 5)),
                new Flower(new Position(7, 5), new Position(8, 5), new Position(7, 6))));
        board.make(new Move(new Flower(new Position(9, 5), new Position(10, 5), new Position(9, 6)),
                new Flower(new Position(8, 6), new Position(7, 7), new Position(8, 7))));
        board.make(new Move(new Flower(new Position(8, 4), new Position(9, 4), new Position(8, 5)),
                new Flower(new Position(4, 8), new Position(3, 9), new Position(4, 9))));
        board.make(new Move(new Flower(new Position(6, 8), new Position(7, 8), new Position(6, 9)),
                new Flower(new Position(5, 9), new Position(4, 10), new Position(5, 10))));
        board.make(new Move(new Ditch(new Position(5, 2), new Position(4, 3))));
        board.make(new Move(new Flower(new Position(2, 10), new Position(3, 10), new Position(2, 11)),
                new Flower(new Position(3, 11), new Position(4, 11), new Position(3, 12))));
        board.make(new Move(new Ditch(new Position(6, 3), new Position(7, 3))));
        board.make(new Move(new Flower(new Position(2, 13), new Position(3, 13), new Position(2, 14)),
                new Flower(new Position(2, 14), new Position(3, 14), new Position(2, 15))));
        board.make(new Move(new Ditch(new Position(8, 3), new Position(8, 4))));
        board.make(new Move(new Flower(new Position(5, 12), new Position(4, 13), new Position(5, 13)),
                new Flower(new Position(4, 13), new Position(3, 14), new Position(4, 14))));
        board.make(new Move(new Ditch(new Position(2, 6), new Position(3, 6))));
        board.make(new Move(new Flower(new Position(5, 10), new Position(4, 11), new Position(5, 11)),
                new Flower(new Position(7, 10), new Position(6, 11), new Position(7, 11))));
        board.make(new Move(new Ditch(new Position(3, 8), new Position(3, 9))));
        board.make(new Move(new Flower(new Position(9, 8), new Position(10, 8), new Position(9, 9)),
                new Flower(new Position(8, 9), new Position(7, 10), new Position(8, 10))));
        board.make(new Move(new Ditch(new Position(5, 7), new Position(4, 8))));
        board.make(new Move(new Flower(new Position(12, 5), new Position(13, 5), new Position(12, 6)),
                new Flower(new Position(10, 7), new Position(11, 7), new Position(10, 8))));
        board.make(new Move(new Ditch(new Position(6, 6), new Position(7, 6))));

        assertEquals(10, viewer.getPoints(PlayerColor.Red));
    }
}
