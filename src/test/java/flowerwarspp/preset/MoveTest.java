package flowerwarspp.preset;

import org.junit.*;

import static org.junit.Assert.*;

public class MoveTest {
    private Flower flowerA, flowerB;
    private Ditch ditch;

    @Before
    public void init() {
        flowerA = new Flower(new Position(2, 3), new Position(4, 5), new Position
                (6, 7));
        flowerB = new Flower(new Position(4, 3), new Position(6, 5), new Position
                (8, 7));
        ditch = new Ditch(new Position(3, 3), new Position(4, 4));
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
        assertEquals(m.toString(), "Surrender");
    }

    @Test
    public void toStringForEndMoveReturnsCorrectValue() {
        Move m = new Move(MoveType.End);
        assertEquals(m.toString(), "End");
    }
}
