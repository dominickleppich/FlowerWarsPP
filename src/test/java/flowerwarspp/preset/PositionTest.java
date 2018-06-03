package flowerwarspp.preset;

import org.junit.*;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class PositionTest {
    public static final int MAX_VALUE = 31;

    private Position[] positionCompareArray;

    // ------------------------------------------------------------

    @Before
    public void init() {
        positionCompareArray = new Position[] {
                new Position(3,2),
                new Position(4,2),
                new Position(2,3),
                new Position(3,3),
                new Position(4,3),
                new Position(2,4),
                new Position(3,4)
        };
    }

    // ------------------------------------------------------------

    @Test
    public void creatingNewPositionWithValidValuesWorks() {
        Position p = new Position(5, 5);
        assertNotNull(p);
    }

    @Test
    public void creatingNewPositionWithOneColumnValuesWorks() {
        Position p = new Position(1, 5);
        assertNotNull(p);
    }

    @Test
    public void creatingNewPositionWithOneRowValuesWorks() {
        Position p = new Position(5, 1);
        assertNotNull(p);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingNewPositionWithZeroColumnValueThrowsException() {
        new Position(0, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingNewPositionWithNegativeColumnValueThrowsException() {
        new Position(-5, 5);
    }

    @Test
    public void creatingNewPositionWithMaxColumnValueWorks() {
        Position p = new Position(MAX_VALUE, 5);
        assertNotNull(p);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingNewPositionWithTooHighColumnValueThrowsException() {
        new Position(MAX_VALUE + 1, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingNewPositionWithZeroRowValueThrowsException() {
        new Position(5, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingNewPositionWithNegativeRowValueThrowsException() {
        new Position(5, -5);
    }

    @Test
    public void creatingNewPositionWithMaxRowValueWorks() {
        Position p = new Position(5, MAX_VALUE);
        assertNotNull(p);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingNewPositionWithTooHighRowValueThrowsException() {
        new Position(5, MAX_VALUE + 1);
    }

    // ------------------------------------------------------------
    // * Getters *

    @Test
    public void getColumnReturnsCorrectValue() {
        Position p = new Position(4, 10);
        assertEquals(4, p.getColumn());
    }

    @Test
    public void getRowReturnsCorrectValue() {
        Position p = new Position(4, 10);
        assertEquals(10, p.getRow());
    }

    // ------------------------------------------------------------
    // * Hash *

    @Test
    public void hashFunctionDistributesPerfectly() {
        Set<Integer> knownHashes = new HashSet<>();
        for (int c = 1; c <= MAX_VALUE; c++) {
            for (int r = 1; r <= MAX_VALUE; r++) {
                Position p = new Position(c, r);
                int hash = p.hashCode();
                if (knownHashes.contains(hash))
                    fail("double hash code for position: " + p);
                else
                    knownHashes.add(hash);
            }
        }
    }

    @Test
    public void equalPositionsReturnSameHashCode() {
        Position a = new Position(3, 4);
        Position b = new Position(3, 4);
        assertEquals(a.hashCode(), b.hashCode());
    }

    // ------------------------------------------------------------
    // * Compare *

    @Test
    public void compareToOnEqualPositionsReturnsZero() {
        Position a = new Position(3, 4);
        Position b = new Position(3, 4);
        assertThat(a.compareTo(b), is(0));
    }

    @Test
    public void compareToDeterminesCorrectOrderingForUnequalPositions1() {
        Position a = new Position(3, 4);
        Position b = new Position(3, 3);
        assertThat(a, greaterThan(b));
    }

    @Test
    public void compareToDeterminesCorrectOrderingForUnequalPositions2() {
        Position a = new Position(3, 3);
        Position b = new Position(3, 4);
        assertThat(a, lessThan(b));
    }

    @Test
    public void compareToDeterminesCorrectOrderingForUnequalPositions3() {
        Position a = new Position(4, 4);
        Position b = new Position(3, 4);
        assertThat(a, greaterThan(b));
    }

    @Test
    public void compareToDeterminesCorrectOrderingForUnequalPositions4() {
        Position a = new Position(3, 4);
        Position b = new Position(4, 4);
        assertThat(a, lessThan(b));
    }

    @Test
    public void compareToCanBeUsedToSortCorrectly() {
        // The array can be sorted correctly

        List<Position> correct = new LinkedList<>();
        List<Position> toTest = new LinkedList<>();
        for (Position p : positionCompareArray) {
            correct.add(p);
            toTest.add(p);
        }

        // Shuffle test list (this might accidentally result in correct ordering)
        Collections.shuffle(toTest);
        Collections.sort(toTest);

        // Test equality
        assertEquals(correct, toTest);
    }

    // ------------------------------------------------------------
    // * Equals *

    @Test
    public void equalsOnEqualPositionsReturnsTrue() {
        Position a = new Position(3, 4);
        Position b = new Position(3, 4);
        assertEquals(a, b);
    }

    @Test
    public void equalsOnUnequalColumnReturnsFalse() {
        Position a = new Position(3, 4);
        Position b = new Position(4, 4);
        assertNotEquals(a, b);
    }

    @Test
    public void equalsOnUnequalRowReturnsFalse() {
        Position a = new Position(3, 4);
        Position b = new Position(3, 3);
        assertNotEquals(a, b);
    }

    @Test
    public void equalsOnNullReturnsFalse() {
        Position a = new Position(3, 4);
        assertNotEquals(a, null);
    }

    @Test
    public void equalsOnWrongTypeReturnsFalse() {
        Position a = new Position(3, 4);
        assertNotEquals(a, new Object());
    }

    // ------------------------------------------------------------
    // * Other stuff *

    @Test
    public void toStringMethodContainsColumn() {
        Position p = new Position(3, 7);
        assertNotNull(p.toString());
        assertTrue(p.toString().contains("3"));
    }

    @Test
    public void toStringMethodContainsRow() {
        Position p = new Position(3, 7);
        assertNotNull(p.toString());
        assertTrue(p.toString().contains("7"));
    }

    // ------------------------------------------------------------
    // * Parse *

    @Test (expected = PositionFormatException.class)
    public void parsePositionForNullStringThrowsException() {
        Position.parsePosition(null);
    }

    @Test (expected = PositionFormatException.class)
    public void parsePositionForEmptyStringThrowsException() {
        Position.parsePosition("");
    }

    @Test (expected = PositionFormatException.class)
    public void parsePositionForWrongFormatStringThrowsException() {
        Position.parsePosition("5,7");
    }

    @Test (expected = PositionFormatException.class)
    public void parsePositionForWrongNumberOfArgumentsThrowsException() {
        Position.parsePosition("(1,2,3)");
    }

    @Test (expected = PositionFormatException.class)
    public void parsePositionForWrongColumnNumberFormatThrowsException() {
        Position.parsePosition("(a,3)");
    }

    @Test (expected = PositionFormatException.class)
    public void parsePositionForWrongRowNumberFormatThrowsException() {
        Position.parsePosition("(2,b)");
    }

    @Test
    public void parsePositionCreatesCorrectPosition() {
        Position p = Position.parsePosition("(5,7)");

        assertEquals(5, p.getColumn());
        assertEquals(7, p.getRow());
    }
}
