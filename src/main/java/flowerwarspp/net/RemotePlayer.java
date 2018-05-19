package flowerwarspp.net;

import flowerwarspp.player.*;
import flowerwarspp.preset.*;
import flowerwarspp.ui.*;
import org.slf4j.*;

import java.rmi.*;
import java.rmi.server.*;

public class RemotePlayer extends UnicastRemoteObject implements Player {
    private static final Logger logger = LoggerFactory.getLogger(RemotePlayer.class);

    private Player player;
    private Display display;
    private Move move;

    // ------------------------------------------------------------

    public RemotePlayer(Player player, Display display) throws RemoteException {
        this.player = player;
        this.display = display;
    }

    // ------------------------------------------------------------

    @Override
    public Move request() throws Exception {
        move = player.request();
        logger.debug("Remote request: " + move);
        return move;
    }

    @Override
    public void confirm(Status status) throws Exception {
        player.confirm(status);
        display.update(move);
        logger.debug("Remote confirm: " + status);

        // Show end screen if game ended
        if (status != Status.Ok)
            display.showStatus(status);
    }

    @Override
    public void update(Move opponentMove, Status status) throws Exception {
        player.update(opponentMove, status);
        display.update(opponentMove);
        logger.debug("Remote update: " + opponentMove + ", " + status);

        // Show end screen if game ended
        if (status != Status.Ok)
            display.showStatus(status);
    }

    @Override
    public void init(int boardSize, PlayerColor color) throws Exception {
        player.init(boardSize, color);
        if (player instanceof AbstractPlayer)
            display.setViewer(((AbstractPlayer) player).getViewer());
        display.reset();
        logger.debug("Remote init: " + boardSize + ", " + color);
    }
}
