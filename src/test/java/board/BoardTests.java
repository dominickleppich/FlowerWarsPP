package board;

import org.junit.runner.*;
import org.junit.runners.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BoardSimpleLogicTest.class,
        BoardFlowerMoveTests.class,
        BoardDitchMoveTests.class,
        BoardPointCalculationTest.class,
        BoardPossibleMovesTest.class
})
public class BoardTests {
}
