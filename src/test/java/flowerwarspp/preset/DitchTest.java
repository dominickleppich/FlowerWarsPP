package flowerwarspp.preset;

import org.junit.*;

import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class DitchTest {
    private Position first, second;
    private Ditch[] ditchCompareArray;

    // ------------------------------------------------------------

    @Before
    public void init() {
        first = new Position(5, 5);
        second = new Position(6, 6);

        ditchCompareArray = new Ditch[]{
                new Ditch(new Position(3,3), new Position(3,2)),
                new Ditch(new Position(4,2), new Position(3,3)),
                new Ditch(new Position(2,3), new Position(3,3)),
                new Ditch(new Position(3,3), new Position(2,4)),
                new Ditch(new Position(3,3), new Position(3,4)),
                new Ditch(new Position(3,3), new Position(4,3))
        };
    }

    // ------------------------------------------------------------

    @Test
    public void creatingNewDitchWithValidValuesWorks() {
        Ditch b = new Ditch(first, second);
        assertNotNull(b);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingNewDitchWithNullFirstThrowsException() {
        new Ditch(null, second);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingNewDitchWithNullSecondThrowsException() {
        new Ditch(first, null);
    }

    // ------------------------------------------------------------
    // * Getters *

    @Test
    public void getFirstReturnsCorrectValue() {
        Ditch d = new Ditch(first, second);
        assertEquals(first, d.getFirst());
    }

    @Test
    public void getSecondReturnsCorrectValue() {
        Ditch d = new Ditch(first, second);
        assertEquals(second, d.getSecond());
    }

    @Test
    public void getFirstAfterAutomaticReorderingReturnsCorrectValue() {
        Ditch d = new Ditch(second, first);
        assertEquals(first, d.getFirst());
    }

    @Test
    public void getSecondAfterAutomaticReorderingReturnsCorrectValue() {
        Ditch d = new Ditch(second, first);
        assertEquals(second, d.getSecond());
    }

    // ------------------------------------------------------------
    // * Hash *

    public static Set<Ditch> createAllPossibleDitches() {
        Set<Ditch> ditches = new HashSet<>();

        for (int c = 1; c <= Position.MAX_VALUE; c++) {
            for (int r = 1; r < Position.MAX_VALUE; r++) {
                if (c + r <= Position.MAX_VALUE) {
                    ditches.add(new Ditch(new Position(c,r), new Position(c+1, r)));
                    ditches.add(new Ditch(new Position(c, r), new Position(c,
                            r + 1)));
                }
                if (c > 1)
                    ditches.add(new Ditch(new Position(c, r), new Position(c
                            - 1, r + 1)));
            }
        }

        return ditches;
    }

    @Test
    public void hashFunctionDistributesPerfectlyForValidDitches() {
        Set<Integer> knownHashes = new HashSet<>();
        for (Ditch d : createAllPossibleDitches()) {
            int hash = d.hashCode();
            if (knownHashes.contains(hash))
                fail("double hash code for ditch: " + d);
            else
                knownHashes.add(hash);
        }
    }

    @Test
    public void equalDitchsReturnSameHashCode() {
        Ditch a = new Ditch(new Position(3, 4), new Position(5, 6));
        Ditch b = new Ditch(new Position(3, 4), new Position(5, 6));
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void equalInvertedDitchsReturnSameHashCode() {
        Ditch a = new Ditch(new Position(3, 4), new Position(5, 6));
        Ditch b = new Ditch(new Position(5, 6), new Position(3, 4));
        assertEquals(a.hashCode(), b.hashCode());
    }

    // ------------------------------------------------------------
    // * Compare *

    @Test
    public void compareToOnEqualDitchesReturnsZero() {
        Ditch a = new Ditch(new Position(3, 4), new Position(5, 6));
        Ditch b = new Ditch(new Position(3, 4), new Position(5, 6));
        assertThat(a.compareTo(b), is(0));
    }

    @Test
    public void compareToCanBeUsedToSortCorrectly() {
        // The array can be sorted correctly

        List<Ditch> correct = new LinkedList<>();
        List<Ditch> toTest = new LinkedList<>();
        for (Ditch d : ditchCompareArray) {
            correct.add(d);
            toTest.add(d);
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
    public void equalsOnEqualDitchesReturnsTrue() {
        Ditch a = new Ditch(new Position(3, 4), new Position(5, 6));
        Ditch b = new Ditch(new Position(3, 4), new Position(5, 6));
        assertEquals(a, b);
    }

    @Test
    public void equalsOnInvertedDitchesReturnsTrue() {
        Ditch a = new Ditch(new Position(3, 4), new Position(5, 6));
        Ditch b = new Ditch(new Position(5, 6), new Position(3, 4));
        assertEquals(a, b);
    }

    @Test
    public void equalsOnUnequalFirstPositionReturnsFalse() {
        Ditch a = new Ditch(new Position(3, 4), new Position(5, 6));
        Ditch b = new Ditch(new Position(3, 5), new Position(5, 6));
        assertNotEquals(a, b);
    }

    @Test
    public void equalsOnUnequalSecondPositionReturnsFalse() {
        Ditch a = new Ditch(new Position(3, 4), new Position(5, 6));
        Ditch b = new Ditch(new Position(3, 4), new Position(5, 7));
        assertNotEquals(a, b);
    }

    @Test
    public void equalsOnNullReturnsFalse() {
        Ditch d = new Ditch(first, second);
        assertNotEquals(d, null);
    }

    @Test
    public void equalsOnWrongTypeReturnsFalse() {
        Ditch d = new Ditch(first, second);
        assertNotEquals(d, new Object());
    }

    // ------------------------------------------------------------
    // * Other stuff *

    @Test
    public void flowersAreCorrectlyCloneable() {
        // This is sufficient, because preset classes are not mutable
        Ditch[] clone = ditchCompareArray.clone();
        clone[0] = null;
        assertNotNull(ditchCompareArray[0]);
    }

    @Test
    public void toStringReturnsSomething() {
        Ditch d = new Ditch(first, second);
        assertNotNull(d.toString());
        assertNotEquals(d.toString(), "");
    }
}
