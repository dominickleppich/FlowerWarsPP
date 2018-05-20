package flowerwarspp.test;

import ch.qos.logback.classic.*;
import flowerwarspp.board.*;
import flowerwarspp.preset.*;
import org.slf4j.*;
import org.slf4j.Logger;

import java.util.*;

public class RandomSpeedTest {
    //    private static final Logger logger = LoggerFactory.getLogger(RandomSpeedTest.class);

    // ------------------------------------------------------------

    public static void main(String[] args) throws Exception {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        lc.stop();

        ArgumentParser ap = new ArgumentParser(args);

        int boardSize = 10;
        if (ap.isSet("size"))
            boardSize = ap.getSize();

        while (true)
            runSimpleGame(boardSize);
    }

    private static void runSimpleGame(int boardSize) throws Exception {
        Board board = new BoardImpl(boardSize);
        Viewer viewer = board.viewer();
        Random rnd = new Random(System.currentTimeMillis());

        long start = System.currentTimeMillis();

        while (viewer.getStatus() == Status.Ok) {
            List<Move> possibleMoves = new LinkedList<>(viewer.getPossibleMoves());
            Move move = possibleMoves.get(rnd.nextInt(possibleMoves.size()));
            board.make(move);
        }

        long end = System.currentTimeMillis();

        System.out.println("Game endet with status [" + viewer.getStatus() + "] in " + String.format("%.3f",
                (double) (end - start) / 1000));
    }
}
