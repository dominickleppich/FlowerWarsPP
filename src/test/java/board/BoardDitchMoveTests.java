package board;

import org.junit.runner.*;
import org.junit.runners.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
                            BoardDitchMoveValidTest.class, BoardDitchMoveInvalidNoTargetingFlowerTest.class,
                            BoardDitchMoveInvalidNextToBlockedFlowerTest.class, BoardDitchMoveInvalidTooLongTest.class,
                            BoardDitchMoveInvalidBlockedPositionTest.class
                    })
public class BoardDitchMoveTests {
}
