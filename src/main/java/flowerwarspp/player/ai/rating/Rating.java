package flowerwarspp.player.ai.rating;

import flowerwarspp.preset.*;

/**
 * Created on 11.06.2017.
 *
 * @author Dominick Leppich
 */
public interface Rating {
    int rate(Viewer viewer, PlayerColor color, Move move);
}
