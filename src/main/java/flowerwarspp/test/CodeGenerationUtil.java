package flowerwarspp.test;

import flowerwarspp.preset.*;

import java.util.*;

public class CodeGenerationUtil {
    public static String printReplayCode(List<Move> moves) {
        StringBuilder sb = new StringBuilder();
        sb.append("List<Move> replayMoves = new LinkedList<>();\n");
        for (Move m : moves) {
            String newMoveString = "";
            switch (m.getType()) {
                case Flower:
                    newMoveString = newFlowerMoveString(m);
                    break;
                case Ditch:
                    newMoveString = newDitchMoveString(m);
                    break;
                case Surrender:
                    newMoveString = "new Move(MoveType.Surrender)";
                    break;
                case End:
                    newMoveString = "new Move(MoveType.End)";
                    break;
            }
           sb.append("replayMoves.add(" + newMoveString + ");\n");
        }
        return sb.toString();
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
