package flowerwarspp.preset;

import java.util.*;

public interface Viewer {
    PlayerColor getTurn();
    int getSize();
    Status getStatus();
    Collection<Flower> getFlowers(PlayerColor color);
    Collection<Ditch> getDitches(PlayerColor color);

    // ------------------------------------------------------------

    Collection<Move> getPossibleMoves();
    int getPoints(PlayerColor color);
}
