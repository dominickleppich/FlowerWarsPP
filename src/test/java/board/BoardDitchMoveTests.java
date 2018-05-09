package board;

import org.junit.runner.*;
import org.junit.runners.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BoardDitchMoveValidTest.class,
        BoardDitchMoveInvalidToNoFlowerTest.class,
        BoardDitchMoveInvalidOverBlockedFlowerTest.class,
        BoardDitchMoveInvalidTooLongTest.class,
        BoardDitchMoveInvalidBlockedPositionTest.class
})
public class BoardDitchMoveTests {
}
