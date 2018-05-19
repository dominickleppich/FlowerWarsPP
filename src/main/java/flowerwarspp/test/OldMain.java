package flowerwarspp.test;

import flowerwarspp.board.*;
import flowerwarspp.preset.*;
import flowerwarspp.ui.graphical.*;
import org.slf4j.*;

import java.nio.file.*;
import java.util.*;

import static flowerwarspp.test.CodeGenerationUtil.*;

public class OldMain {
    private static final Logger logger = LoggerFactory.getLogger(OldMain.class);

    // ------------------------------------------------------------

    public static void main(String[] args) throws Exception {
        ArgumentParser ap = new ArgumentParser(args);

        //                while (true)
        runSimpleGame(ap);
    }

    private static void runSimpleGame(ArgumentParser ap) throws Exception {
        Board board = new BoardImpl(ap.getSize());
        Viewer viewer = board.viewer();
        UIWindow window = new UIWindow();
        //        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setViewer(viewer);
        Random rnd = new Random(System.currentTimeMillis());
        List<Move> moves = new LinkedList<>();

        List<Move> replayMoves = new LinkedList<>();

        // ------------------------------------------------------------
        // * Replay *


        // ------------------------------------------------------------

        int counter = 0;
        for (Move m : replayMoves) {
            board.make(m);
            counter++;
            logger.debug("Replayed moves: " + counter);
            //            moves.add(m);
        }

        while (viewer.getStatus() == Status.Ok) {

            List<Move> possibleMoves = new LinkedList<>(viewer.getPossibleMoves());
            possibleMoves.remove(new Move(MoveType.Surrender));
            /*Collections.sort(possibleMoves);
            logger.debug("Possible moves [" + possibleMoves.size() + "]:");
            System.out.println(CodeGenerationUtil.createParameterArray(possibleMoves, false));*/

            //            Move move = window.request();
            Move move = possibleMoves.get(rnd.nextInt(possibleMoves.size()));

            board.make(move);
            window.update(move);
            //            System.out.println(board);

            //            if (viewer.getStatus() != Status.Illegal)
            if (viewer.getStatus() != Status.RedWin && viewer.getStatus() != Status.BlueWin)
                moves.add(move);
            //                        Thread.sleep(1000);
        }

        logger.debug("Game ended with status: " + viewer.getStatus());

        // Print code to replay game

        //        System.out.println(createReplayList(moves));

        // Append code to a file

        //                new File("moves.txt").createNewFile();
        Files.write(Paths.get("moves.txt"), createParameterArray(moves, false).getBytes(), StandardOpenOption.APPEND);

        //        window.close();
    }
}
