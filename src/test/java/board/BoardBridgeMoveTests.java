package board;

import org.junit.runner.*;
import org.junit.runners.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BoardBridgeMoveFormatTest.class,
        BoardBridgeMoveInvalidToNoLandTest.class,
        BoardBridgeMoveInvalidOverBlockedLandTest.class,
        BoardBridgeMoveInvalidTooLongTest.class
})
public class BoardBridgeMoveTests {
}
