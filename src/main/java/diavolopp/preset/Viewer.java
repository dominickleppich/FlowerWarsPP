package diavolopp.preset;

import java.util.*;

public interface Viewer {
    PlayerColor getTurn();
    int getSize();
    Status getStatus();
    Collection<Land> getLands(PlayerColor color);
    Collection<Bridge> getBridges(PlayerColor color);
}
