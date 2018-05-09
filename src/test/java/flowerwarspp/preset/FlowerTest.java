package flowerwarspp.preset;

import org.junit.*;

import static org.junit.Assert.*;

public class FlowerTest {
    private Position first, second, third;

    @Before
    public void init() {
        first = new Position(2, 3);
        second = new Position(4, 5);
        third = new Position(6, 7);
    }

    // ------------------------------------------------------------

    @Test
    public void creatingNewLandWithValidValuesWorks() {
        Flower l = new Flower(first, second, third);
        assertNotNull(l);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingNewLandWithNullFirstThrowsException() {
        new Flower(null, second, third);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingNewLandWithNullSecondThrowsException() {
        new Flower(first, null, third);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingNewLandWithNullThirdThrowsException() {
        new Flower(first, second, null);
    }

    // ------------------------------------------------------------
    // * Getters *

    @Test
    public void getFirstReturnsCorrectValue() {
        Flower l = new Flower(first, second, third);
        assertEquals(first, l.getFirst());
    }

    @Test
    public void getSecondReturnsCorrectValue() {
        Flower l = new Flower(first, second, third);
        assertEquals(second, l.getSecond());
    }

    @Test
    public void getThirdReturnsCorrectValue() {
        Flower l = new Flower(first, second, third);
        assertEquals(third, l.getThird());
    }

    // ------------------------------------------------------------
    // * Equals *

    @Test
    public void equalsOnEqualLandsReturnsTrue() {
        Flower a = new Flower(new Position(3, 4), new Position(5, 6), new
                Position(7, 8));
        Flower b = new Flower(new Position(3, 4), new Position(5, 6), new
                Position(7, 8));
        assertEquals(a, b);
    }

    @Test
    public void equalsOnPermutedLandsReturnsTrue1() {
        Flower a = new Flower(first, second, third);
        Flower b = new Flower(first, third, second);
        assertEquals(a, b);
    }

    @Test
    public void equalsOnPermutedLandsReturnsTrue2() {
        Flower a = new Flower(first, second, third);
        Flower b = new Flower(second, first, third);
        assertEquals(a, b);
    }

    @Test
    public void equalsOnPermutedLandsReturnsTrue3() {
        Flower a = new Flower(first, second, third);
        Flower b = new Flower(second, third, first);
        assertEquals(a, b);
    }

    @Test
    public void equalsOnPermutedLandsReturnsTrue4() {
        Flower a = new Flower(first, second, third);
        Flower b = new Flower(third, first, second);
        assertEquals(a, b);
    }

    @Test
    public void equalsOnPermutedLandsReturnsTrue5() {
        Flower a = new Flower(first, second, third);
        Flower b = new Flower(third, second, first);
        assertEquals(a, b);
    }

    @Test
    public void equalsOnUnequalFirstPositionReturnsFalse() {
        Flower a = new Flower(new Position(3, 4), new Position(5, 6), new
                Position(7, 8));
        Flower b = new Flower(new Position(3, 5), new Position(5, 6), new
                Position(7, 8));
        assertNotEquals(a, b);
    }

    @Test
    public void equalsOnUnequalSecondPositionReturnsFalse() {
        Flower a = new Flower(new Position(3, 4), new Position(5, 6), new
                Position(7, 8));
        Flower b = new Flower(new Position(3, 4), new Position(7, 6), new
                Position(7, 8));
        assertNotEquals(a, b);
    }

    @Test
    public void equalsOnUnequalThirdPositionReturnsFalse() {
        Flower a = new Flower(new Position(3, 4), new Position(5, 6), new
                Position(7, 8));
        Flower b = new Flower(new Position(3, 4), new Position(5, 6), new
                Position(8, 8));
        assertNotEquals(a, b);
    }

    @Test
    public void equalsOnNullReturnsFalse() {
        Flower l = new Flower(first, second, third);
        assertNotEquals(l, null);
    }

    @Test
    public void equalsOnWrongTypeReturnsFalse() {
        Flower l = new Flower(first, second, third);
        assertNotEquals(l, new Object());
    }

    // ------------------------------------------------------------
    // * Other stuff *

    @Test
    public void equalLandsReturnSameHashCode() {
        Flower a = new Flower(new Position(3, 4), new Position(5, 6), new
                Position(7, 8));
        Flower b = new Flower(new Position(3, 4), new Position(5, 6), new
                Position(7, 8));
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void equalPermutedLandsReturnSameHashCode1() {
        Flower a = new Flower(first, second, third);
        Flower b = new Flower(first, third, second);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void equalPermutedLandsReturnSameHashCode2() {
        Flower a = new Flower(first, second, third);
        Flower b = new Flower(second, first, third);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void equalPermutedLandsReturnSameHashCode3() {
        Flower a = new Flower(first, second, third);
        Flower b = new Flower(second, third, first);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void equalPermutedLandsReturnSameHashCode4() {
        Flower a = new Flower(first, second, third);
        Flower b = new Flower(third, first, second);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void equalPermutedLandsReturnSameHashCode5() {
        Flower a = new Flower(first, second, third);
        Flower b = new Flower(third, second, first);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void toStringReturnsSomething() {
        Flower l = new Flower(first, second, third);
        assertNotNull(l.toString());
        assertNotEquals(l.toString(), "");
    }
}
