package board;

import org.junit.runner.*;
import org.junit.runners.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BoardBridgeMoveValidTest.class,
        BoardBridgeMoveInvalidToNoLandTest.class,
        BoardBridgeMoveInvalidOverBlockedLandTest.class,
        BoardBridgeMoveInvalidTooLongTest.class
})
public class BoardBridgeMoveTest {
}
