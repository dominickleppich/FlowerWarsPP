package flowerwarspp.player;

import flowerwarspp.board.*;
import flowerwarspp.preset.*;
import org.slf4j.*;

public abstract class AbstractPlayer implements Player {
    private static final Logger logger = LoggerFactory.getLogger(AbstractPlayer.class);
    protected Board board;
    protected Viewer viewer;
    private State expectedState;
    private Move move;
    private PlayerColor color;
    public AbstractPlayer() {
        expectedState = State.INIT;
    }

    // ------------------------------------------------------------

    public PlayerColor getColor() {
        return color;
    }

    // ------------------------------------------------------------

    public Viewer getViewer() {
        return viewer;
    }

    private void verifyState(State check) throws Exception {
        if (expectedState != check)
            throw new Exception("Wrong player usage! Expected " + expectedState + " but tried " + check);
    }

    @Override
    public Move request() throws Exception {
        verifyState(State.REQUEST);

        logger.debug("Requesting move...");
        move = provideMove();
        logger.debug("Requested move: " + move);

        expectedState = State.CONFIRM;

        logger.debug("Next expected call: " + expectedState);

        return move;
    }

    // ------------------------------------------------------------

    protected abstract Move provideMove() throws Exception;

    @Override
    public void confirm(Status status) throws Exception {
        verifyState(State.CONFIRM);

        logger.debug("Confirming move...");

        // Perform move on board and check status
        board.make(move);

        Status ownStatus = viewer.getStatus();
        if (status != ownStatus)
            throw new Exception(
                    "Status mismatch! Main board status is " + status + " but player status is " + ownStatus);

        logger.debug("Move confirmed with status: " + status);

        // If game is over, init next. Otherwise, update next
        if (status == Status.Ok)
            expectedState = State.UPDATE;
        else
            expectedState = State.INIT;

        logger.debug("Next expected call: " + expectedState);
    }

    @Override
    public void update(Move opponentMove, Status status) throws Exception {
        verifyState(State.UPDATE);

        logger.debug("Updating opponent move...");

        board.make(opponentMove);

        Status ownStatus = viewer.getStatus();
        if (status != ownStatus)
            throw new Exception(
                    "Status mismatch! Main board status is " + status + " but player status is " + ownStatus);

        logger.debug("Opponent move done with status: " + status);

        // If game is over, init next. Otherwise, update next
        if (status == Status.Ok)
            expectedState = State.REQUEST;
        else
            expectedState = State.INIT;

        logger.debug("Next expected call: " + expectedState);
    }

    @Override
    public void init(int boardSize, PlayerColor color) throws Exception {
        verifyState(State.INIT);

        logger.debug("Initializing player with board size " + boardSize + " and color " + color);

        this.color = color;
        board = new BoardImpl(boardSize);
        viewer = board.viewer();

        logger.debug("Player initialized successfully!");

        if (color == PlayerColor.Red)
            expectedState = State.REQUEST;
        else
            expectedState = State.UPDATE;

        logger.debug("Next expected call: " + expectedState);
    }

    enum State {
        INIT,
        REQUEST,
        CONFIRM,
        UPDATE;
    }
}
