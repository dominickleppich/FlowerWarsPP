package board;

import org.junit.runner.*;
import org.junit.runners.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BoardLogicTest.class,
        BoardLandMoveTests.class,
        BoardBridgeMoveTests.class
})
public class BoardTests {
}
