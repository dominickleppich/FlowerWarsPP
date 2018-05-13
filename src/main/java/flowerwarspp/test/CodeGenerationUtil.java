package flowerwarspp.test;

import flowerwarspp.preset.*;

import java.util.*;

public class CodeGenerationUtil {

    public static String createReplayList(List<Move> moves) {
        StringBuilder sb = new StringBuilder();
        sb.append("List<Move> replayMoves = new LinkedList<>();\n");
        for (Move m : moves)
            sb.append("replayMoves.add(" + createMoveString(m) + ");\n");
        return sb.toString();
    }

    public static String createParameterArray(List<Move> moves, boolean createFrame) {
        StringBuilder sb = new StringBuilder();
        if (createFrame)
            sb.append("return Arrays.asList(new Object[][]{\n");
        for (Move m : moves)
            sb.append("{" + createMoveString(m) + "},\n");
        if (createFrame)
            sb.append("});\n");
        return sb.toString();
    }

    // ------------------------------------------------------------

    public static String createMoveString(Move move) {
        switch (move.getType()) {
            case Flower:
                return newFlowerMoveString(move);
            case Ditch:
                return newDitchMoveString(move);
            case Surrender:
                return "new Move(MoveType.Surrender)";
            case End:
                return "new Move(MoveType.End)";
        }
        return null;
    }

    public static String newFlowerMoveString(Move move) {
        return "new Move(" + newFlowerString(move.getFirstFlower()) + ", " + newFlowerString(
                move.getSecondFlower()) + ")";
    }

    public static String newDitchMoveString(Move move) {
        return "new Move(" + newDitchString(move.getDitch()) + ")";
    }

    public static String newFlowerString(Flower flower) {
        return "new Flower(" + newPositionString(flower.getFirst()) + ", " + newPositionString(
                flower.getSecond()) + ", " + newPositionString(flower.getThird()) + ")";
    }

    public static String newDitchString(Ditch ditch) {
        return "new Ditch(" + newPositionString(ditch.getFirst()) + ", " + newPositionString(ditch.getSecond()) + ")";
    }

    public static String newPositionString(Position position) {
        return "new Position(" + position.getColumn() + ", " + position.getRow() + ")";
    }
}
