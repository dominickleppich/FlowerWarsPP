package flowerwarspp.player.ai.simple;

import flowerwarspp.player.ai.rating.*;

/**
 * SimpleAI described in the project description
 *
 * @author Dominick Leppich
 */
public class SimpleAI extends BestRatedMoveAI {

    public SimpleAI() {
        super(new SimpleStrategy());
    }
}
