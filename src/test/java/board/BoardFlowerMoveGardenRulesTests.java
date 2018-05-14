package board;

import org.junit.runner.*;
import org.junit.runners.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BoardFlowerMoveValidCreateFlowerBedTest.class,
        BoardFlowerMoveValidCreateGardenTest.class,
        BoardFlowerMoveValidTouchFlowerBedTest.class,
        BoardFlowerMoveInvalidTouchesOwnGardenTest.class,
        BoardFlowerMoveValidTouchesOpponentGardenTest.class,
        BoardFlowerMoveValidFusionFlowerBedsToGardenTest.class,
        BoardFlowerMoveInvalidFusionFlowerBedsToOversizedGardenTest.class,
        BoardFlowerMoveInvalidFusionFlowerBedsToIllegalTouchingGardenTest.class,
        BoardFlowerMoveInvalidFusionFlowerBedsWithTwoFlowersToOversizedGardenTest.class,
        BoardFlowerMoveInvalidFusionFlowerBedsWithTwoFlowersToIllegalTouchingGardenTest.class
})
public class BoardFlowerMoveGardenRulesTests {
}
