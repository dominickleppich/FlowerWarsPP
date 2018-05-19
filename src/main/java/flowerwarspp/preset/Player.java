package flowerwarspp.preset;

import java.rmi.*;

public interface Player extends Remote {
    Move request() throws Exception;
    void confirm(Status status) throws Exception;
    void update(Move opponentMove, Status status) throws Exception;
    void init(int boardSize, PlayerColor color) throws Exception;
}
