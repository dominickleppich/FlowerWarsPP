package diavolopp.board;

import diavolopp.preset.*;

import java.util.*;
import java.util.stream.*;

import static java.util.stream.Collectors.*;

public class BoardImpl implements Board {
    private final int size;
    private PlayerColor turn;
    private Status status;

    private Set<Land> whiteLands, redLands;
    private Set<Bridge> whiteBridges, redBridges;

    // ------------------------------------------------------------

    public BoardImpl(final int size) {
        this.size = size;

        turn = PlayerColor.White;
        status = Status.Ok;

        whiteLands = new HashSet<>();
        redLands = new HashSet<>();
        whiteBridges = new HashSet<>();
        redBridges = new HashSet<>();

    }

    // ------------------------------------------------------------

    @Override
    public void make(Move move) {
        // TODO write test for this
        if (status == Status.Illegal)
            throw new IllegalStateException("cannot perform moves on illegal " +
                    "state board");

        if (!isValidMoveFormat(move) || !isValidMove(move)) {
            status = Status.Illegal;
            return;
        }

        setMoveOnBoard(move);

        // Change to next player
        nextPlayer();
    }

    // ------------------------------------------------------------

    private boolean isValidMove(Move move) {
        switch (move.getType()) {
            case Land:
                // TODO test land on empty field

                break;
            case Bridge:
                Bridge b = move.getBridge();
                Position start, end;
                start = b.getStart();
                end = b.getEnd();

                // Bridge needs to be build on own land position
                Set<Position> landPositionSet = getLandPositionSet(turn);
                if (!landPositionSet.contains(start) || !landPositionSet
                        .contains(end))
                    return false;

                // Bridge cannot be build over land
                Set<Land> blockedLandIntersection = getBridgeBlockedLands(b);
                blockedLandIntersection.retainAll(getLandSet(null));
                if (!blockedLandIntersection.isEmpty())
                    return false;

                break;
            default:
                throw new IllegalArgumentException("illegal move type: " +
                        move.getType());
        }
        return true;
    }

    private boolean isValidMoveFormat(Move move) {
        switch (move.getType()) {
            case Land:
                Land[] lands = move.getLands();
                for (Land land : lands)
                    if (!isValidLandFormat(land))
                        return false;
                break;
            case Bridge:
                Bridge bridge = move.getBridge();
                if (!isValidBridgeFormat(bridge))
                    return false;
                break;
            default:
                return false;
        }
        return true;
    }

    private boolean isValidLandFormat(Land land) {
        Position a = land.getFirst();
        Position b = land.getSecond();
        Position c = land.getThird();

        Set<Position> aNeighbors = getNeighborPositions(a);
        Set<Position> bNeighbors = getNeighborPositions(b);
        Set<Position> cNeighbors = getNeighborPositions(c);

        if (!aNeighbors.contains(b) || !aNeighbors.contains(c) || !bNeighbors
                .contains(a) || !bNeighbors.contains(c) || !cNeighbors
                .contains(a) || !cNeighbors.contains(b))
            return false;

        return true;
    }

    private boolean isValidBridgeFormat(Bridge bridge) {
        Position a = bridge.getStart();
        Position b = bridge.getEnd();
        return getNeighborPositions(a).contains(b);
    }

    private void nextPlayer() {
        if (turn == PlayerColor.White)
            turn = PlayerColor.Red;
        else
            turn = PlayerColor.White;
    }

    private void setMoveOnBoard(Move move) {
        switch (move.getType()) {
            case Land:
                for (Land l : move.getLands())
                    getLandSet(turn).add(l);
                break;
            case Bridge:
                getBridgeSet(turn).add(move.getBridge());
                break;
            default:
                throw new IllegalStateException("illegal move type: " + move
                        .getType());
        }
    }

    // ------------------------------------------------------------

    private Set<Land> getLandSet(PlayerColor color) {
        if (color == null) {
            Set<Land> resultSet = new HashSet<>();
            resultSet.addAll(whiteLands);
            resultSet.addAll(redLands);
            return resultSet;
        } else if (color == PlayerColor.White)
            return whiteLands;
        else
            return redLands;
    }

    private Set<Bridge> getBridgeSet(PlayerColor color) {
        if (color == null) {
            Set<Bridge> resultSet = new HashSet<>();
            resultSet.addAll(whiteBridges);
            resultSet.addAll(redBridges);
            return resultSet;
        } else if (color == PlayerColor.White)
            return whiteBridges;
        else
            return redBridges;
    }

    private Set<Position> getLandPositionSet(PlayerColor color) {
        Set<Position> resultSet = new HashSet<>();
        for (Land l : getLandSet(color)) {
            resultSet.add(l.getFirst());
            resultSet.add(l.getSecond());
            resultSet.add(l.getThird());
        }
        return resultSet;
    }

    private Set<Position> getBridgePositionSet(PlayerColor color) {
        Set<Position> resultSet = new HashSet<>();
        for (Bridge b : getBridgeSet(color)) {
            resultSet.add(b.getStart());
            resultSet.add(b.getEnd());
        }
        return resultSet;
    }

    private Set<Land> getBridgeBlockedLands(Bridge bridge) {
        Position start, end;
        start = bridge.getStart();
        end = bridge.getEnd();
        int minCol, maxCol, minRow, maxRow;
        minCol = Math.min(start.getColumn(), end.getColumn());
        maxCol = Math.max(start.getColumn(), end.getColumn());
        minRow = Math.min(start.getRow(), end.getRow());
        maxRow = Math.max(start.getRow(), end.getRow());

        Position posA, posB;
        if (start.getColumn() == end.getColumn()) {
            int col = minCol;
            posA = new Position(col - 1, maxRow);
            posB = new Position(col + 1, minRow);
        } else if (start.getRow() == end.getRow()) {
            int row = minRow;
            posA = new Position(minCol, row + 1);
            posB = new Position(maxCol, row - 1);
        } else {
            posA = new Position(minCol, minRow);
            posB = new Position(maxCol, maxRow);
        }

        Set<Land> resultLands = new HashSet<>();
        resultLands.add(new Land(start, end, posA));
        resultLands.add(new Land(start, end, posB));

        return resultLands;
    }

    // ------------------------------------------------------------

    public static Set<Position> getNeighborPositions(Position position) {
        int c = position.getColumn();
        int r = position.getRow();
        Set<Position> neighbors = new HashSet<>();
        neighbors.add(new Position(c + 1, r));
        neighbors.add(new Position(c + 1, r - 1));
        neighbors.add(new Position(c, r - 1));
        neighbors.add(new Position(c - 1, r));
        neighbors.add(new Position(c - 1, r + 1));
        neighbors.add(new Position(c, r + 1));
        return neighbors;
    }

    // ------------------------------------------------------------

    @Override
    public Viewer viewer() {
        return new Viewer() {
            @Override
            public PlayerColor getTurn() {
                return BoardImpl.this.turn;
            }

            @Override
            public int getSize() {
                return BoardImpl.this.size;
            }

            @Override
            public Status getStatus() {
                return BoardImpl.this.status;
            }
        };
    }

    // ------------------------------------------------------------

    @Override
    public String toString() {
        Stack<String> st = new Stack<>();

        String firstLine = "   ";
        for (int c = 1; c <= size; c++)
            firstLine += String.format("%2d          ", c);
        firstLine += (size + 1);
        st.add(firstLine);

        Set<Land> whiteLands = getLandSet(PlayerColor.White);
        Set<Land> redLands = getLandSet(PlayerColor.Red);

        String indent = "";
        for (int r = 1; r <= size; r++) {
            for (int l = 0; l < 6; l++) {
                String s = indent;
                if (l == 0)
                    s += String.format("%3d ", r);
                else
                    s += "    ";
                for (int c = 1; c <= size - r + 1; c++) {
                    s += "/";
                    if (l == 0)
                        s += Stream.generate(() -> "__").limit(5 - l).collect
                                (joining());
                    else {
                        String inner = Stream.generate(() -> "  ").limit(5 -
                                l).collect
                                (joining());

                        Land land = new Land(new Position(c, r), new
                                Position(c + 1, r), new Position(c, r + 1));
                        if (l == 1) {
                            if (whiteLands.contains(land))
                                inner = "  ++++  ";
                            else if (redLands.contains(land))
                                inner = "  ****  ";
                        } else if (l == 2) {
                            if (whiteLands.contains(land))
                                inner = "  ++  ";
                            else if (redLands.contains(land))
                                inner = "  **  ";
                        }

                        s += inner;
                    }
                    s += "\\";
                    String inner = Stream.generate(() -> "  ").limit(l)
                            .collect(joining());

                    if (c > 1) {
                        Land land = new Land(new Position(c + 1, r), new
                                Position(c , r + 1), new Position(c + 1, r + 1));

                        if (l == 3) {
                            if (whiteLands.contains(land))
                                inner = "  ++  ";
                            else if (redLands.contains(land))
                                inner = "  **  ";
                        } else if (l == 4) {
                            if (whiteLands.contains(land))
                                inner = "  ++++  ";
                            else if (redLands.contains(land))
                                inner = "  ****  ";
                        }
                    }

                    s += inner;
                }

                st.add(s);
                indent += " ";
            }
        }

        StringBuilder sb = new StringBuilder();
        while (!st.isEmpty()) {
            sb.append(st.pop()).append("\n");
        }
//        st.stream().forEach(s -> sb.append(s).append("\n"));
        return sb.toString();
    }
}
