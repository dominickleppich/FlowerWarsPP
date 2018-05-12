package flowerwarspp.preset;

import org.junit.*;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class MoveTest {
    private Flower flowerA, flowerB;
    private Ditch ditch;
    private Move[] moveCompareArray;

    // ------------------------------------------------------------

    @Before
    public void init() {
        flowerA = new Flower(new Position(2, 3), new Position(4, 5), new Position(6, 7));
        flowerB = new Flower(new Position(4, 3), new Position(6, 5), new Position(8, 7));
        ditch = new Ditch(new Position(3, 3), new Position(4, 4));

        moveCompareArray = new Move[]{
                new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                        new Flower(new Position(3, 2), new Position(2, 3), new Position(3, 3))),
                new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                        new Flower(new Position(3, 2), new Position(4, 2), new Position(3, 3))),
                new Move(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                        new Flower(new Position(4, 2), new Position(3, 3), new Position(4, 3))),
                new Move(new Flower(new Position(3, 2), new Position(2, 3), new Position(3, 3)),
                        new Flower(new Position(3, 2), new Position(4, 2), new Position(3, 3))),
                new Move(new Flower(new Position(3, 2), new Position(2, 3), new Position(3, 3)),
                        new Flower(new Position(4, 2), new Position(3, 3), new Position(4, 3))),
                new Move(new Flower(new Position(3, 2), new Position(4, 2), new Position(3, 3)),
                        new Flower(new Position(4, 2), new Position(3, 3), new Position(4, 3))),
                new Move(new Ditch(new Position(2, 3), new Position(3, 2))),
                new Move(new Ditch(new Position(3, 2), new Position(3, 3))),
                new Move(new Ditch(new Position(3, 2), new Position(4, 2))),
                new Move(new Ditch(new Position(4, 2), new Position(3, 3))),
                new Move(new Ditch(new Position(4, 2), new Position(4, 3))),
                new Move(new Ditch(new Position(4, 2), new Position(5, 2))), new Move(MoveType.Surrender),
                new Move(MoveType.End)
        };
    }

    // ------------------------------------------------------------

    @Test
    public void creatingNewMoveWithValidFlowersWorks() {
        Move m = new Move(flowerA, flowerB);
        assertNotNull(m);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNewMoveWithNullFirstFlowerThrowsException() {
        new Move(null, flowerB);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNewMoveWithNullSecondFlowerThrowsException() {
        new Move(flowerA, null);
    }

    @Test
    public void creatingNewMoveWithValidDitchWorks() {
        Move m = new Move(ditch);
        assertNotNull(m);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNewMoveWithFlowerTypeThrowsException() {
        new Move(MoveType.Flower);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNewMoveWithDitchTypeThrowsException() {
        new Move(MoveType.Ditch);
    }

    // ------------------------------------------------------------
    // /* Getters */

    @Test
    public void getTypeForFlowerMoveReturnsCorrectType() {
        Move m = new Move(flowerA, flowerB);
        assertEquals(MoveType.Flower, m.getType());
    }

    @Test
    public void getTypeForDitchMoveReturnsCorrectType() {
        Move m = new Move(ditch);
        assertEquals(MoveType.Ditch, m.getType());
    }

    @Test
    public void getTypeForSurrenderMoveReturnsCorrectType() {
        Move m = new Move(MoveType.Surrender);
        assertEquals(MoveType.Surrender, m.getType());
    }

    @Test
    public void getTypeForEndMoveReturnsCorrectType() {
        Move m = new Move(MoveType.End);
        assertEquals(MoveType.End, m.getType());
    }

    @Test
    public void getFirstFlowerForFlowerMoveReturnsCorrectValue() {
        Move m = new Move(flowerA, flowerB);
        assertEquals(flowerA, m.getFirstFlower());
    }

    @Test
    public void getSecondFlowerForFlowerMoveReturnsCorrectValue() {
        Move m = new Move(flowerA, flowerB);
        assertEquals(flowerB, m.getSecondFlower());
    }

    @Test(expected = IllegalStateException.class)
    public void getFirstFlowerForDitchMoveThrowsException() {
        Move m = new Move(ditch);
        m.getFirstFlower();
    }

    @Test(expected = IllegalStateException.class)
    public void getSecondFlowerForDitchMoveThrowsException() {
        Move m = new Move(ditch);
        m.getSecondFlower();
    }

    @Test
    public void getBridgeForBridgeMoveReturnsCorrectValue() {
        Move m = new Move(ditch);
        assertEquals(ditch, m.getDitch());
    }

    @Test(expected = IllegalStateException.class)
    public void getBridgeForFlowerMoveThrowsException() {
        Move m = new Move(flowerA, flowerB);
        m.getDitch();
    }

    // ------------------------------------------------------------
    // * Hash *

    public static Set<Move> createAllPossibleMoves() {
        Set<Move> moves = new HashSet<>();

        // All possible flower moves
        Set<Flower> possibleFlowers = FlowerTest.createAllPossibleFlowers();
        for (Flower f1 : possibleFlowers)
            for (Flower f2 : possibleFlowers)
                if (!f1.equals(f2))
                    moves.add(new Move(f1, f2));

        // All possible ditch moves
        for (Ditch d : DitchTest.createAllPossibleDitches())
            moves.add(new Move(d));

        // Add special moves
        moves.add(new Move(MoveType.Surrender));
        moves.add(new Move(MoveType.End));

        return moves;
    }

    //    @Ignore("Too time consuming")
    @Test
    public void hashFunctionDistributesPerfectlyForValidMoves() {
        Set<Integer> knownHashes = new HashSet<>();
        for (Move m : createAllPossibleMoves()) {
            int hash = m.hashCode();
            if (knownHashes.contains(hash))
                fail("double hash code for move: " + m);
            else
                knownHashes.add(hash);
        }
    }

    @Test
    public void equalFlowerMovesReturnSameHashCode() {
        Move a = new Move(new Flower(new Position(3, 4), new Position(5, 6), new Position(7, 8)),
                new Flower(new Position(5, 6), new Position(8, 8), new Position(7, 9)));
        Move b = new Move(new Flower(new Position(3, 4), new Position(5, 6), new Position(7, 8)),
                new Flower(new Position(5, 6), new Position(8, 8), new Position(7, 9)));
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void equalDitchMovesReturnSameHashCode() {
        Move a = new Move(new Ditch(new Position(2, 2), new Position(3, 2)));
        Move b = new Move(new Ditch(new Position(2, 2), new Position(3, 2)));
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void equalSurrenderMovesReturnSameHashCode() {
        Move a = new Move(MoveType.Surrender);
        Move b = new Move(MoveType.Surrender);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void equalEndMovesReturnSameHashCode() {
        Move a = new Move(MoveType.End);
        Move b = new Move(MoveType.End);
        assertEquals(a.hashCode(), b.hashCode());
    }

    // ------------------------------------------------------------
    // * Compare *

    @Test
    public void compareToOnEqualFlowerMovesReturnsZero() {
        Move a = new Move(new Flower(new Position(3, 4), new Position(5, 6), new Position(7, 8)),
                new Flower(new Position(5, 6), new Position(8, 8), new Position(7, 9)));
        Move b = new Move(new Flower(new Position(3, 4), new Position(5, 6), new Position(7, 8)),
                new Flower(new Position(5, 6), new Position(8, 8), new Position(7, 9)));
        assertThat(a.compareTo(b), is(0));
    }

    @Test
    public void compareToOnEqualDitchMovesReturnsZero() {
        Move a = new Move(new Ditch(new Position(2, 2), new Position(3, 2)));
        Move b = new Move(new Ditch(new Position(2, 2), new Position(3, 2)));
        assertThat(a.compareTo(b), is(0));
    }

    @Test
    public void compareToOnEqualSurrenderMovesReturnsZero() {
        Move a = new Move(MoveType.Surrender);
        Move b = new Move(MoveType.Surrender);
        assertThat(a.compareTo(b), is(0));
    }

    @Test
    public void compareToOnEqualEndMovesReturnsZero() {
        Move a = new Move(MoveType.End);
        Move b = new Move(MoveType.End);
        assertThat(a.compareTo(b), is(0));
    }

    @Test
    public void compareToCanBeUsedToSortCorrectly() {
        // The array can be sorted correctly

        List<Move> correct = new LinkedList<>();
        List<Move> toTest = new LinkedList<>();
        for (Move m : moveCompareArray) {
            correct.add(m);
            toTest.add(m);
        }

        // Shuffle test list (this might accidentally result in correct
        // ordering)
        Collections.shuffle(toTest);
        Collections.sort(toTest);

        // Test equality
        assertEquals(correct, toTest);
    }

    // ------------------------------------------------------------
    // * Equals *

    @Test
    public void equalsOnEqualFlowerMovesReturnsTrue() {
        Move a = new Move(new Flower(new Position(3, 4), new Position(5, 6), new Position(7, 8)),
                new Flower(new Position(5, 5), new Position(6, 6), new Position(7, 7)));
        Move b = new Move(new Flower(new Position(3, 4), new Position(5, 6), new Position(7, 8)),
                new Flower(new Position(5, 5), new Position(6, 6), new Position(7, 7)));
        assertEquals(a, b);
    }

    @Test
    public void equalsOnPermutedEqualFlowerMovesReturnsTrue() {
        Move a = new Move(new Flower(new Position(3, 4), new Position(5, 6), new Position(7, 8)),
                new Flower(new Position(5, 5), new Position(6, 6), new Position(7, 7)));
        Move b = new Move(new Flower(new Position(5, 5), new Position(6, 6), new Position(7, 7)),
                new Flower(new Position(3, 4), new Position(5, 6), new Position(7, 8)));
        assertEquals(a, b);
    }

    @Test
    public void equalsOnEqualDitchMovesReturnsTrue() {
        Move a = new Move(new Ditch(new Position(2, 2), new Position(3, 2)));
        Move b = new Move(new Ditch(new Position(2, 2), new Position(3, 2)));
        assertEquals(a, b);
    }

    @Test
    public void equalsOnEqualSurrenderMovesReturnsTrue() {
        Move a = new Move(MoveType.Surrender);
        Move b = new Move(MoveType.Surrender);
        assertEquals(a, b);
    }

    @Test
    public void equalsOnEqualEndMovesReturnsTrue() {
        Move a = new Move(MoveType.End);
        Move b = new Move(MoveType.End);
        assertEquals(a, b);
    }

    @Test
    public void equalsOnUnequalFlowerMovesReturnsFalse() {
        Move a = new Move(new Flower(new Position(3, 4), new Position(5, 6), new Position(7, 8)),
                new Flower(new Position(5, 5), new Position(6, 6), new Position(7, 7)));
        Move b = new Move(new Flower(new Position(3, 4), new Position(5, 6), new Position(7, 8)),
                new Flower(new Position(5, 6), new Position(6, 6), new Position(7, 7)));
        assertNotEquals(a, b);
    }

    @Test
    public void equalsOnUnequalDitchMovesReturnsFalse() {
        Move a = new Move(new Ditch(new Position(2, 2), new Position(3, 2)));
        Move b = new Move(new Ditch(new Position(2, 3), new Position(3, 2)));
        assertNotEquals(a, b);
    }

    @Test
    public void equalsOnWrongTypeReturnsFalse() {
        Move a = new Move(MoveType.Surrender);
        assertNotEquals(a, new Object());
    }

    @Test
    public void equalsOnWrongMoveTypeReturnsFalse() {
        Move a = new Move(MoveType.Surrender);
        Move b = new Move(MoveType.End);
        assertNotEquals(a, b);
    }

    @Test
    public void equalsOnNullReturnsFalse() {
        Move m = new Move(MoveType.Surrender);
        assertNotEquals(m, null);
    }

    // ------------------------------------------------------------
    // * Other stuff *

    @Test
    public void toStringForFlowerMoveReturnsSomething() {
        Move m = new Move(flowerA, flowerB);
        assertNotNull(m.toString());
        assertNotEquals(m.toString(), "");
    }

    @Test
    public void toStringForDitchMoveReturnsSomething() {
        Move m = new Move(ditch);
        assertNotNull(m.toString());
        assertNotEquals(m.toString(), "");
    }

    @Test
    public void toStringForSurrenderMoveReturnsCorrectValue() {
        Move m = new Move(MoveType.Surrender);
        assertEquals(m.toString(), "{Surrender}");
    }

    @Test
    public void toStringForEndMoveReturnsCorrectValue() {
        Move m = new Move(MoveType.End);
        assertEquals(m.toString(), "{End}");
    }
}
