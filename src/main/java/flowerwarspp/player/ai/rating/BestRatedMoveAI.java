package flowerwarspp.player.ai.rating;

import flowerwarspp.player.*;
import flowerwarspp.preset.*;

import java.util.*;

import static java.util.stream.Collectors.*;

/**
 * This AI choses the best rated move.
 *
 * @author Dominick Leppich
 */
public class BestRatedMoveAI extends AbstractPlayer {
    private Rating strategy;

    // ------------------------------------------------------------

    public BestRatedMoveAI(Rating strategy) {
        this.strategy = strategy;
    }

    // ------------------------------------------------------------

    @Override
    public Move provideMove() throws Exception {
        List<RateableMove> moves = getViewer().getPossibleMoves()
                                              .stream()
                                              .map(move -> new RateableMove(move,
                                                      strategy.rate(getViewer(), getColor(), move)))
                                              .collect(toList());
        Collections.shuffle(moves);
        return Collections.max(moves)
                          .getMove();
    }
}
