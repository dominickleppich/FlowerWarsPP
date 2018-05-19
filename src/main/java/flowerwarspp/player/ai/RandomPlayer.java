package flowerwarspp.player.ai;

import flowerwarspp.player.*;
import flowerwarspp.preset.*;
import org.slf4j.*;

import java.util.*;

public class RandomPlayer extends AbstractPlayer {
    private static final Logger logger = LoggerFactory.getLogger(RandomPlayer.class);

    private static final Move SURRENDER_MOVE = new Move(MoveType.Surrender);

    private Random random;

    // ------------------------------------------------------------

    public RandomPlayer() {
        random = new Random(System.currentTimeMillis());
    }

    // ------------------------------------------------------------

    @Override
    protected Move provideMove() throws Exception {
        List<Move> possibleMoves = new LinkedList<>(viewer.getPossibleMoves());

        // Don't surrender
        possibleMoves.remove(SURRENDER_MOVE);

        Move m = possibleMoves.get(random.nextInt(possibleMoves.size()));

        logger.debug("Chose random move: " + m);

        return m;
    }
}
