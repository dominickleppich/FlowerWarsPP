package board;

import org.junit.runner.*;
import org.junit.runners.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BoardLandMoveFormatTest.class,
        BoardLandMovePositionTest.class,
        BoardLandMoveBridgeTest.class
})
public class BoardLandMoveTests {
}
