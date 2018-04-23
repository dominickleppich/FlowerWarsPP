package diavolopp.preset;

public interface Viewer {
    PlayerColor getTurn();
    int getSize();
    Status getStatus();
    Iterable<Land> getLands(PlayerColor color);
    Iterable<Bridge> getBridges(PlayerColor color);
}
