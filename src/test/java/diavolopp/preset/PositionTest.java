package diavolopp.preset;

import org.junit.Test;

import static org.junit.Assert.*;

public class PositionTest {
    public static final int MAX_COLUMN = 20;
    public static final int MAX_ROW = 20;

    @Test
    public void creatingNewPositionWithValidValuesWorks() {
        Position p = new Position(5, 5);
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
        Position p = new Position(MAX_COLUMN, 5);
        assertNotNull(p);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingNewPositionWithTooHighColumnValueThrowsException() {
        new Position(MAX_COLUMN + 1, 5);
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
        Position p = new Position(5, MAX_ROW);
        assertNotNull(p);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingNewPositionWithTooHighRowValueThrowsException() {
        new Position(5, MAX_ROW + 1);
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
    // * Equals *

    @Test
    public void equalsOnEqualPositionsReturnsTrue() {
        Position a = new Position(3, 4);
        Position b = new Position(3, 4);
        assertEquals(a, b);
    }

    @Test
    public void equalsOnUnequalPositionsReturnsFalse() {
        Position a = new Position(3, 4);
        Position b = new Position(4, 4);
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
    public void equalPositionsReturnSameHashCode() {
        Position a = new Position(3, 4);
        Position b = new Position(3, 4);
        assertEquals(a.hashCode(), b.hashCode());
    }

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
}
