package board;

import org.junit.runner.*;
import org.junit.runners.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BoardLogicTest.class,
        BoardFlowerMoveTests.class,
        BoardDitchMoveTests.class
})
public class BoardTests {
}
