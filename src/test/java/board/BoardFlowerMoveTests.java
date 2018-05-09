package board;

import org.junit.runner.*;
import org.junit.runners.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BoardFlowerMoveFormatTest.class,
        BoardFlowerMovePositionTest.class,
        BoardFlowerMoveDitchTest.class
})
public class BoardFlowerMoveTests {
}
