package flowerwarspp.preset;

import org.junit.*;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class FlowerTest {
    private Position first, second, third;
    private Flower[] flowerCompareArray;

    // ------------------------------------------------------------

    @Before
    public void init() {
        first = new Position(2, 3);
        second = new Position(4, 5);
        third = new Position(6, 7);

        flowerCompareArray = new Flower[] {
                new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)),
                new Flower(new Position(3, 2), new Position(2, 3), new Position(3, 3)),
                new Flower(new Position(3, 2), new Position(4, 2), new Position(3, 3)),
                new Flower(new Position(4, 2), new Position(3, 3), new Position(4, 3)),
                new Flower(new Position(2, 3), new Position(3, 3), new Position(2, 4)),
                new Flower(new Position(3, 3), new Position(2, 4), new Position(3, 4)),
                new Flower(new Position(3, 3), new Position(4, 3), new Position(3, 4)),
                new Flower(new Position(4, 3), new Position(3, 4), new Position(4, 4))
        };
    }

    // ------------------------------------------------------------
    // * Constructors *

    @Test
    public void creatingNewFlowerWithValidValuesWorks() {
        Flower f = new Flower(first, second, third);
        assertNotNull(f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingNewFlowerWithNullFirstThrowsException() {
        new Flower(null, second, third);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingNewFlowerWithNullSecondThrowsException() {
        new Flower(first, null, third);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingNewFlowerWithNullThirdThrowsException() {
        new Flower(first, second, null);
    }

    // ------------------------------------------------------------
    // * Getters *

    @Test
    public void getFirstReturnsCorrectValue() {
        Flower f = new Flower(first, second, third);
        assertEquals(first, f.getFirst());
    }

    @Test
    public void getSecondReturnsCorrectValue() {
        Flower f = new Flower(first, second, third);
        assertEquals(second, f.getSecond());
    }

    @Test
    public void getThirdReturnsCorrectValue() {
        Flower f = new Flower(first, second, third);
        assertEquals(third, f.getThird());
    }

    // Don't test all combinations here
    @Test
    public void getFirstAfterAutomaticReorderingReturnsCorrectValue() {
        Flower f = new Flower(second, third, first);
        assertEquals(first, f.getFirst());
    }

    @Test
    public void getSecondAfterAutomaticReorderingReturnsCorrectValue() {
        Flower f = new Flower(second, third, first);
        assertEquals(second, f.getSecond());
    }

    @Test
    public void getThirdAfterAutomaticReorderingReturnsCorrectValue() {
        Flower f = new Flower(second, third, first);
        assertEquals(third, f.getThird());
    }

    // ------------------------------------------------------------
    // * Hash *

    public static Set<Flower> createAllPossibleFlowers() {
        Set<Flower> flowers = new HashSet<>();

        for (int c = 1; c < Position.MAX_VALUE; c++) {
            for (int r = 1; r < Position.MAX_VALUE; r++) {
                if (c + r <= Position.MAX_VALUE)
                    flowers.add(new Flower(new Position(c, r), new Position(c
                            + 1, r), new Position(c, r + 1)));

                if (c + r < Position.MAX_VALUE) {
                    flowers.add(new Flower(new Position(c + 1, r + 1), new
                            Position(c
                            + 1, r), new Position(c, r + 1)));
                }
            }
        }

        return flowers;
    }

    @Test
    public void hashFunctionDistributesPerfectlyForValidFlowers() {
        Set<Integer> knownHashes = new HashSet<>();
        for (Flower f : createAllPossibleFlowers()) {
            int hash = f.hashCode();
            if (knownHashes.contains(hash))
                fail("double hash code for flower: " + f);
            else
                knownHashes.add(hash);
        }
    }

    @Test
    public void equalFlowersReturnSameHashCode() {
        Flower a = new Flower(new Position(3, 4), new Position(5, 6), new
                Position(7, 8));
        Flower b = new Flower(new Position(3, 4), new Position(5, 6), new
                Position(7, 8));
        assertEquals(a.hashCode(), b.hashCode());
    }

    /*@Test
    public void equalPermutedFlowersReturnSameHashCode1() {
        Flower a = new Flower(first, second, third);
        Flower b = new Flower(first, third, second);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void equalPermutedFlowersReturnSameHashCode2() {
        Flower a = new Flower(first, second, third);
        Flower b = new Flower(second, first, third);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void equalPermutedFlowersReturnSameHashCode3() {
        Flower a = new Flower(first, second, third);
        Flower b = new Flower(second, third, first);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void equalPermutedFlowersReturnSameHashCode4() {
        Flower a = new Flower(first, second, third);
        Flower b = new Flower(third, first, second);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void equalPermutedFlowersReturnSameHashCode5() {
        Flower a = new Flower(first, second, third);
        Flower b = new Flower(third, second, first);
        assertEquals(a.hashCode(), b.hashCode());
    }*/

    // ------------------------------------------------------------
    // * Compare *

    @Test
    public void compareToOnEqualFlowersReturnsZero() {
        Flower a = new Flower(new Position(3, 4), new Position(5, 6), new
                Position(7, 8));
        Flower b = new Flower(new Position(3, 4), new Position(5, 6), new
                Position(7, 8));
        assertThat(a.compareTo(b), is(0));
    }

    @Test
    public void compareToCanBeUsedToSortCorrectly() {
        // The array can be sorted correctly

        List<Flower> correct = new LinkedList<>();
        List<Flower> toTest = new LinkedList<>();
        for (Flower f : flowerCompareArray) {
            correct.add(f);
            toTest.add(f);
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
    public void equalsOnEqualFlowersReturnsTrue() {
        Flower a = new Flower(new Position(3, 4), new Position(5, 6), new
                Position(7, 8));
        Flower b = new Flower(new Position(3, 4), new Position(5, 6), new
                Position(7, 8));
        assertEquals(a, b);
    }

    @Test
    public void equalsOnPermutedEqualFlowersReturnsTrue1() {
        Flower a = new Flower(first, second, third);
        Flower b = new Flower(first, third, second);
        assertEquals(a, b);
    }

    @Test
    public void equalsOnPermutedEqualFlowersReturnsTrue2() {
        Flower a = new Flower(first, second, third);
        Flower b = new Flower(second, first, third);
        assertEquals(a, b);
    }

    @Test
    public void equalsOnPermutedEqualFlowersReturnsTrue3() {
        Flower a = new Flower(first, second, third);
        Flower b = new Flower(second, third, first);
        assertEquals(a, b);
    }

    @Test
    public void equalsOnPermutedEqualFlowersReturnsTrue4() {
        Flower a = new Flower(first, second, third);
        Flower b = new Flower(third, first, second);
        assertEquals(a, b);
    }

    @Test
    public void equalsOnPermutedEqualFlowersReturnsTrue5() {
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
        Flower f = new Flower(first, second, third);
        assertNotEquals(f, null);
    }

    @Test
    public void equalsOnWrongTypeReturnsFalse() {
        Flower f = new Flower(first, second, third);
        assertNotEquals(f, new Object());
    }

    // ------------------------------------------------------------
    // * Other stuff *

    @Test
    public void flowersAreCorrectlyCloneable() {
        // This is sufficient, because preset classes are not mutable
        Flower[] clone = flowerCompareArray.clone();
        clone[0] = null;
        assertNotNull(flowerCompareArray[0]);
    }

    @Test
    public void toStringReturnsSomething() {
        Flower l = new Flower(first, second, third);
        assertNotNull(l.toString());
        assertNotEquals(l.toString(), "");
    }

    // ------------------------------------------------------------
    // * Parse *

    @Test (expected = FlowerFormatException.class)
    public void parseFlowerForNullStringThrowsException() {
        Flower.parseFlower(null);
    }

    @Test (expected = FlowerFormatException.class)
    public void parseFlowerForEmptyStringThrowsException() {
        Flower.parseFlower("");
    }

    @Test (expected = FlowerFormatException.class)
    public void parseFlowerForWrongFormatStringThrowsException() {
        Flower.parseFlower("(1,2),(3,4),(5,6)");
    }

    @Test (expected = FlowerFormatException.class)
    public void parseFlowerForWrongNumberOfArgumentsThrowsException() {
        Flower.parseFlower("{(1,2),(3,4),(5,6),(7,8)}");
    }

    @Test (expected = FlowerFormatException.class)
    public void parseFlowerForWrongColumnNumberInOnePositionFormatThrowsException() {
        Flower.parseFlower("{(a,2),(3,4),(5,6)}");
    }

    @Test (expected = FlowerFormatException.class)
    public void parseFlowerForWrongRowNumberInOnePositionFormatThrowsException() {
        Flower.parseFlower("{(1,b),(3,4),(5,6)}");
    }

    @Test
    public void parseFlowerCreatesCorrectFlower() {
        Flower f = Flower.parseFlower("{(1,2),(3,4),(5,6)}");

        assertEquals(new Position(1,2), f.getFirst());
        assertEquals(new Position(3,4), f.getSecond());
        assertEquals(new Position(5,6), f.getThird());
    }
}
