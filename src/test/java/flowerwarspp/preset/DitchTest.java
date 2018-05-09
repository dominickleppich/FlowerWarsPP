package flowerwarspp.preset;

import org.junit.*;

import static org.junit.Assert.*;

public class DitchTest {
    private Position start, end;

    @Before
    public void init() {
        start = new Position(5, 5);
        end = new Position(6, 6);
    }

    // ------------------------------------------------------------

    @Test
    public void creatingNewBridgeWithValidValuesWorks() {
        Ditch b = new Ditch(start, end);
        assertNotNull(b);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingNewBridgeWithNullStartThrowsException() {
        new Ditch(null, end);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingNewBridgeWithNullEndThrowsException() {
        new Ditch(start, null);
    }

    // ------------------------------------------------------------
    // * Getters *

    @Test
    public void getStartReturnsCorrectValue() {
        Ditch b = new Ditch(start, end);
        assertEquals(start, b.getFirst());
    }

    @Test
    public void getEndReturnsCorrectValue() {
        Ditch b = new Ditch(start, end);
        assertEquals(end, b.getSecond());
    }

    // ------------------------------------------------------------
    // * Equals *

    @Test
    public void equalsOnEqualBridgesReturnsTrue() {
        Ditch a = new Ditch(new Position(3, 4), new Position(5, 6));
        Ditch b = new Ditch(new Position(3, 4), new Position(5, 6));
        assertEquals(a, b);
    }

    @Test
    public void equalsOnInvertedBridgesReturnsTrue() {
        Ditch a = new Ditch(new Position(3, 4), new Position(5, 6));
        Ditch b = new Ditch(new Position(5, 6), new Position(3, 4));
        assertEquals(a, b);
    }

    @Test
    public void equalsOnUnequalStartPositionReturnsFalse() {
        Ditch a = new Ditch(new Position(3, 4), new Position(5, 6));
        Ditch b = new Ditch(new Position(3, 5), new Position(5, 6));
        assertNotEquals(a, b);
    }

    @Test
    public void equalsOnUnequalEndPositionReturnsFalse() {
        Ditch a = new Ditch(new Position(3, 4), new Position(5, 6));
        Ditch b = new Ditch(new Position(3, 4), new Position(5, 7));
        assertNotEquals(a, b);
    }

    @Test
    public void equalsOnNullReturnsFalse() {
        Ditch b = new Ditch(start, end);
        assertNotEquals(b, null);
    }

    @Test
    public void equalsOnWrongTypeReturnsFalse() {
        Ditch b = new Ditch(start, end);
        assertNotEquals(b, new Object());
    }

    // ------------------------------------------------------------
    // * Other stuff *

    @Test
    public void equalBridgesReturnSameHashCode() {
        Ditch a = new Ditch(new Position(3, 4), new Position(5, 6));
        Ditch b = new Ditch(new Position(3, 4), new Position(5, 6));
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void equalInvertedBridgesReturnSameHashCode() {
        Ditch a = new Ditch(new Position(3, 4), new Position(5, 6));
        Ditch b = new Ditch(new Position(5, 6), new Position(3, 4));
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void toStringReturnsSomething() {
        Ditch b = new Ditch(start, end);
        assertNotNull(b.toString());
        assertNotEquals(b.toString(), "");
    }
}
