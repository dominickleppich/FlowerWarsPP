package flowerwarspp.game;

import flowerwarspp.board.*;
import flowerwarspp.preset.*;
import flowerwarspp.ui.*;
import org.slf4j.*;

public class Match {
    private static final Logger logger = LoggerFactory.getLogger(Match.class);

    // ------------------------------------------------------------

    private Board board;
    private Viewer viewer;
    private Player[] players;
    private Display display;
    private Status status;
    private long delay;

    private int currentPlayer;

    // ------------------------------------------------------------

    public Match(int boardSize, Player redPlayer, Player bluePlayer, Display display) throws Exception {
        board = new BoardImpl(boardSize);
        viewer = board.viewer();
        this.display = display;
        display.setViewer(viewer);

        currentPlayer = 0;
        delay = 0L;

        initPlayers(boardSize, redPlayer, bluePlayer);
    }

    private void initPlayers(int boardSize, Player redPlayer, Player bluePlayer) throws Exception {
        // Init both players accordingly
        redPlayer.init(boardSize, PlayerColor.Red);
        bluePlayer.init(boardSize, PlayerColor.Blue);

        // Save players into array
        players = new Player[]{redPlayer, bluePlayer};
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    // ------------------------------------------------------------

    public Status play() {
        // Initial display reset
        display.reset();

        try {
            // Main game loop
            while (viewer.getStatus() == Status.Ok) {
                // Start measuring move time
                long start = System.currentTimeMillis();

                // Request move
                Move move = players[currentPlayer].request();

                // Do move on match board
                board.make(move);
                status = viewer.getStatus();

                // Confirm move to player and update other player
                players[currentPlayer].confirm(status);
                players[(currentPlayer + 1) % 2].update(move, status);

                // End measuring time
                long end = System.currentTimeMillis();

                // Update display
                display.update(move);

                // Change to next player
                currentPlayer = (currentPlayer + 1) % 2;

                // Wait if move was too fast
                long wait = delay - (end - start);
                if (wait > 0)
                    Thread.sleep(wait);
            }

            display.showStatus(status);

            logger.debug("Match ended successfully with status: " + status);
        } catch (Exception e) {
            logger.error("Match ended with exception: " + e.getMessage());
            return null;
        }

        return status;
    }
}
