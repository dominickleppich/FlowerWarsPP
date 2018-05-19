package flowerwarspp.preset;

public interface Player {
    Move request() throws Exception;
    void confirm(Status status) throws Exception;
    void update(Move opponentMove, Status status) throws Exception;
    void init(int boardSize, PlayerColor color) throws Exception;
}
