package flowerwarspp.preset;

import org.junit.*;

import static org.junit.Assert.*;

public class LandTest {
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
        Land l = new Land(first, second, third);
        assertNotNull(l);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingNewLandWithNullFirstThrowsException() {
        new Land(null, second, third);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingNewLandWithNullSecondThrowsException() {
        new Land(first, null, third);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingNewLandWithNullThirdThrowsException() {
        new Land(first, second, null);
    }

    // ------------------------------------------------------------
    // * Getters *

    @Test
    public void getFirstReturnsCorrectValue() {
        Land l = new Land(first, second, third);
        assertEquals(first, l.getFirst());
    }

    @Test
    public void getSecondReturnsCorrectValue() {
        Land l = new Land(first, second, third);
        assertEquals(second, l.getSecond());
    }

    @Test
    public void getThirdReturnsCorrectValue() {
        Land l = new Land(first, second, third);
        assertEquals(third, l.getThird());
    }

    // ------------------------------------------------------------
    // * Equals *

    @Test
    public void equalsOnEqualLandsReturnsTrue() {
        Land a = new Land(new Position(3, 4), new Position(5, 6), new
                Position(7, 8));
        Land b = new Land(new Position(3, 4), new Position(5, 6), new
                Position(7, 8));
        assertEquals(a, b);
    }

    @Test
    public void equalsOnPermutedLandsReturnsTrue1() {
        Land a = new Land(first, second, third);
        Land b = new Land(first, third, second);
        assertEquals(a, b);
    }

    @Test
    public void equalsOnPermutedLandsReturnsTrue2() {
        Land a = new Land(first, second, third);
        Land b = new Land(second, first, third);
        assertEquals(a, b);
    }

    @Test
    public void equalsOnPermutedLandsReturnsTrue3() {
        Land a = new Land(first, second, third);
        Land b = new Land(second, third, first);
        assertEquals(a, b);
    }

    @Test
    public void equalsOnPermutedLandsReturnsTrue4() {
        Land a = new Land(first, second, third);
        Land b = new Land(third, first, second);
        assertEquals(a, b);
    }

    @Test
    public void equalsOnPermutedLandsReturnsTrue5() {
        Land a = new Land(first, second, third);
        Land b = new Land(third, second, first);
        assertEquals(a, b);
    }

    @Test
    public void equalsOnUnequalFirstPositionReturnsFalse() {
        Land a = new Land(new Position(3, 4), new Position(5, 6), new
                Position(7, 8));
        Land b = new Land(new Position(3, 5), new Position(5, 6), new
                Position(7, 8));
        assertNotEquals(a, b);
    }

    @Test
    public void equalsOnUnequalSecondPositionReturnsFalse() {
        Land a = new Land(new Position(3, 4), new Position(5, 6), new
                Position(7, 8));
        Land b = new Land(new Position(3, 4), new Position(7, 6), new
                Position(7, 8));
        assertNotEquals(a, b);
    }

    @Test
    public void equalsOnUnequalThirdPositionReturnsFalse() {
        Land a = new Land(new Position(3, 4), new Position(5, 6), new
                Position(7, 8));
        Land b = new Land(new Position(3, 4), new Position(5, 6), new
                Position(8, 8));
        assertNotEquals(a, b);
    }

    @Test
    public void equalsOnNullReturnsFalse() {
        Land l = new Land(first, second, third);
        assertNotEquals(l, null);
    }

    @Test
    public void equalsOnWrongTypeReturnsFalse() {
        Land l = new Land(first, second, third);
        assertNotEquals(l, new Object());
    }

    // ------------------------------------------------------------
    // * Other stuff *

    @Test
    public void equalLandsReturnSameHashCode() {
        Land a = new Land(new Position(3, 4), new Position(5, 6), new
                Position(7, 8));
        Land b = new Land(new Position(3, 4), new Position(5, 6), new
                Position(7, 8));
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void equalPermutedLandsReturnSameHashCode1() {
        Land a = new Land(first, second, third);
        Land b = new Land(first, third, second);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void equalPermutedLandsReturnSameHashCode2() {
        Land a = new Land(first, second, third);
        Land b = new Land(second, first, third);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void equalPermutedLandsReturnSameHashCode3() {
        Land a = new Land(first, second, third);
        Land b = new Land(second, third, first);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void equalPermutedLandsReturnSameHashCode4() {
        Land a = new Land(first, second, third);
        Land b = new Land(third, first, second);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void equalPermutedLandsReturnSameHashCode5() {
        Land a = new Land(first, second, third);
        Land b = new Land(third, second, first);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void toStringReturnsSomething() {
        Land l = new Land(first, second, third);
        assertNotNull(l.toString());
        assertNotEquals(l.toString(), "");
    }
}
