package flowerwarspp.player.ai.simple;

import flowerwarspp.board.*;
import flowerwarspp.player.ai.rating.*;
import flowerwarspp.preset.*;
import org.slf4j.*;

import java.util.*;

/**
 * Created on 11.06.2017.
 *
 * @author Dominick Leppich
 */
public class SimpleStrategy implements Rating {
    private static final Logger logger = LoggerFactory.getLogger(SimpleStrategy.class);

    @Override
    public int rate(Viewer viewer, PlayerColor color, Move move) {
        MoveType type = move.getType();

        if (type == MoveType.Surrender || type == MoveType.End)
            return Integer.MIN_VALUE;

        int score = 0;

        if (type == MoveType.Flower) {
            Flower f1 = move.getFirstFlower();
            Flower f2 = move.getSecondFlower();


            // Give a bonus for flowers, connecting existing flowers
            Collection<Flower> flowersOnBoard = viewer.getFlowers(color);
            //            logger.debug("Found " + flowersOnBoard.size() +" flowers for player " + color);

            Set<Flower> f1Neighbors = BoardImpl.getNeighborFlowers(f1);
            Set<Flower> f2Neighbors = BoardImpl.getNeighborFlowers(f2);

            f1Neighbors.retainAll(flowersOnBoard);
            f2Neighbors.retainAll(flowersOnBoard);

            score += (f1Neighbors.size() * 5 + 1) * (f2Neighbors.size() * 5 + 1);

            // Double score for connected flowers
            if (BoardImpl.getNeighborFlowers(f1)
                         .contains(f2))
                score += score;
        }

        return score;
    }
}
