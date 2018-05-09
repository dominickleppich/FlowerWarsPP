package flowerwarspp.preset;

import org.junit.*;

import static org.junit.Assert.*;

public class MoveTest {
    private Land landA, landB;
    private Bridge bridge;

    @Before
    public void init() {
        landA = new Land(new Position(2, 3), new Position(4, 5), new Position
                (6, 7));
        landB = new Land(new Position(4, 3), new Position(6, 5), new Position
                (8, 7));
        bridge = new Bridge(new Position(3, 3), new Position(4, 4));
    }

    // ------------------------------------------------------------

    @Test
    public void creatingNewMoveWithValidLandsWorks() {
        Move m = new Move(landA, landB);
        assertNotNull(m);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNewMoveWithNullFirstLandThrowsException() {
        new Move(null, landB);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNewMoveWithNullSecondLandThrowsException() {
        new Move(landA, null);
    }

    @Test
    public void creatingNewMoveWithValidBridgeWorks() {
        Move m = new Move(bridge);
        assertNotNull(m);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNewMoveWithNullBridgeThrowsException() {
        new Move(null);
    }

    // ------------------------------------------------------------
    // /* Getters */

    @Test
    public void getTypeForLandMoveReturnsCorrectType() {
        Move m = new Move(landA, landB);
        assertEquals(MoveType.Land, m.getType());
    }

    @Test
    public void getTypeForBridgeMoveReturnsCorrectType() {
        Move m = new Move(bridge);
        assertEquals(MoveType.Bridge, m.getType());
    }

    @Test
    public void getLandsForLandMoveReturnsCorrectValues() {
        Move m = new Move(landA, landB);
        assertArrayEquals(new Land[] {landA, landB}, m.getLands());
    }

    @Test
    public void getLandsForLandMoveCannotModifyMovesInnerState() {
        Move m = new Move(landA, landB);
        m.getLands()[0] = null;
        assertArrayEquals(new Land[] {landA, landB}, m.getLands());
    }

    @Test(expected = IllegalStateException.class)
    public void getLandsForBridgeMoveThrowsException() {
        Move m = new Move(bridge);
        m.getLands();
    }

    @Test
    public void getBridgeForBridgeMoveReturnsCorrectValue() {
        Move m = new Move(bridge);
        assertEquals(bridge, m.getBridge());
    }

    @Test(expected = IllegalStateException.class)
    public void getBridgeForLandMoveThrowsException() {
        Move m = new Move(landA, landB);
        m.getBridge();
    }

    // ------------------------------------------------------------
    // * Other stuff *

    @Test
    public void toStringForLandMoveReturnsSomething() {
        Move m = new Move(landA, landB);
        assertNotNull(m.toString());
        assertNotEquals(m.toString(), "");
    }

    @Test
    public void toStringForBridgeMoveReturnsSomething() {
        Move m = new Move(bridge);
        assertNotNull(m.toString());
        assertNotEquals(m.toString(), "");
    }
}
