package diavolopp.preset;

import org.junit.*;

import static org.junit.Assert.*;

public class BridgeTest {
    private Position start, end;

    @Before
    public void init() {
        start = new Position(5, 5);
        end = new Position(6, 6);
    }

    // ------------------------------------------------------------

    @Test
    public void creatingNewBridgeWithValidValuesWorks() {
        Bridge b = new Bridge(start, end);
        assertNotNull(b);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingNewBridgeWithNullStartThrowsException() {
        new Bridge(null, end);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingNewBridgeWithNullEndThrowsException() {
        new Bridge(start, null);
    }

    // ------------------------------------------------------------
    // * Getters *

    @Test
    public void getStartReturnsCorrectValue() {
        Bridge b = new Bridge(start, end);
        assertEquals(start, b.getStart());
    }

    @Test
    public void getEndReturnsCorrectValue() {
        Bridge b = new Bridge(start, end);
        assertEquals(end, b.getEnd());
    }

    // ------------------------------------------------------------
    // * Equals *

    @Test
    public void equalsOnEqualBridgesReturnsTrue() {
        Bridge a = new Bridge(new Position(3, 4), new Position(5, 6));
        Bridge b = new Bridge(new Position(3, 4), new Position(5, 6));
        assertEquals(a, b);
    }

    @Test
    public void equalsOnInvertedBridgesReturnsTrue() {
        Bridge a = new Bridge(new Position(3, 4), new Position(5, 6));
        Bridge b = new Bridge(new Position(5, 6), new Position(3, 4));
        assertEquals(a, b);
    }

    @Test
    public void equalsOnUnequalStartPositionReturnsFalse() {
        Bridge a = new Bridge(new Position(3, 4), new Position(5, 6));
        Bridge b = new Bridge(new Position(3, 5), new Position(5, 6));
        assertNotEquals(a, b);
    }

    @Test
    public void equalsOnUnequalEndPositionReturnsFalse() {
        Bridge a = new Bridge(new Position(3, 4), new Position(5, 6));
        Bridge b = new Bridge(new Position(3, 4), new Position(5, 7));
        assertNotEquals(a, b);
    }

    @Test
    public void equalsOnNullReturnsFalse() {
        Bridge b = new Bridge(start, end);
        assertNotEquals(b, null);
    }

    @Test
    public void equalsOnWrongTypeReturnsFalse() {
        Bridge b = new Bridge(start, end);
        assertNotEquals(b, new Object());
    }

    // ------------------------------------------------------------
    // * Other stuff *

    @Test
    public void equalBridgesReturnSameHashCode() {
        Bridge a = new Bridge(new Position(3, 4), new Position(5, 6));
        Bridge b = new Bridge(new Position(3, 4), new Position(5, 6));
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void equalInvertedBridgesReturnSameHashCode() {
        Bridge a = new Bridge(new Position(3, 4), new Position(5, 6));
        Bridge b = new Bridge(new Position(5, 6), new Position(3, 4));
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void toStringReturnsSomething() {
        Bridge b = new Bridge(start, end);
        assertNotNull(b.toString());
        assertNotEquals(b.toString(), "");
    }
}
