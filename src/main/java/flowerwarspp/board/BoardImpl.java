package flowerwarspp.board;

import flowerwarspp.preset.*;
import org.slf4j.*;

import java.util.*;
import java.util.stream.*;

import static java.util.stream.Collectors.*;

public class BoardImpl implements Board {
    private static final Logger logger = LoggerFactory.getLogger(BoardImpl.class);

    private final int size;
    private PlayerColor turn;
    private Status status;

    private Set<Flower> redFlowers, greenFlowers;
    private Set<Ditch> redDitches, greenDitches;

    // ------------------------------------------------------------

    public BoardImpl(final int size) {
        this.size = size;

        turn = PlayerColor.Red;
        status = Status.Ok;

        redFlowers = new HashSet<>();
        greenFlowers = new HashSet<>();
        redDitches = new HashSet<>();
        greenDitches = new HashSet<>();

        logger.debug("New board instantiated");
    }

    // ------------------------------------------------------------

    public static Set<Position> getNeighborPositions(Position position, int boardSize) {
        int c = position.getColumn();
        int r = position.getRow();
        Set<Position> neighbors = new HashSet<>();
        // Try to add all 6 neighbors, beginning with the neighbor to the right
        // continuing counter clockwise
        if (c + r <= boardSize + 1) {
            neighbors.add(new Position(c + 1, r));
            neighbors.add(new Position(c, r + 1));
        }
        if (c > 1) {
            neighbors.add(new Position(c - 1, r));
            neighbors.add(new Position(c - 1, r + 1));
        }
        if (r > 1) {
            neighbors.add(new Position(c, r - 1));
            neighbors.add(new Position(c + 1, r - 1));
        }

        return neighbors;
    }

    // ------------------------------------------------------------

    public static Set<Flower> getAllPossibleFlowers(int boardSize) {
        Set<Flower> flowers = new HashSet<>();

        for (int c = 1; c <= boardSize; c++) {
            for (int r = 1; r <= boardSize; r++) {
                if (c + r <= boardSize + 1)
                    flowers.add(new Flower(new Position(c, r), new Position(c + 1, r), new Position(c, r + 1)));
                if (c + r <= boardSize)
                    flowers.add(new Flower(new Position(c + 1, r + 1), new Position(c + 1, r), new Position(c, r + 1)));
            }
        }

        return flowers;
    }

    public static Set<Ditch> getAllPossibleDitches(int boardSize) {
        Set<Ditch> ditches = new HashSet<>();

        for (int c = 1; c <= boardSize + 1; c++) {
            for (int r = 1; r <= boardSize; r++) {
                // For each point create 3 ditches, if available
                if (c + r <= boardSize + 1) {
                    Ditch d1 = new Ditch(new Position(c, r), new Position(c, r + 1));
                    Ditch d2 = new Ditch(new Position(c, r), new Position(c + 1, r));
                    ditches.add(d1);
                    ditches.add(d2);
                }
                if (c > 1 && c + r <= boardSize + 2) {
                    Ditch d = new Ditch(new Position(c, r), new Position(c - 1, r + 1));
                    ditches.add(d);
                }
            }
        }

        return ditches;
    }

    @Override
    public void make(Move move) throws IllegalStateException {
        // TODO write test for this
        if (status != Status.Ok)
            throw new IllegalStateException("cannot perform moves on this " + "board anymore");

        if (!isValidMoveFormat(move) || !isValidMove(move)) {
            status = Status.Illegal;
            return;
        }

        if (move.getType() == MoveType.Surrender) {
            if (turn == PlayerColor.Red)
                status = Status.BlueWin;
            else
                status = Status.RedWin;
            return;
        }

        setMoveOnBoard(move);

        // Change to next player
        nextPlayer();
    }

    private boolean isValidMove(Move move) {

        switch (move.getType()) {
            case Flower:
                // Flower needs to be placed on the board!
                Flower[] flowers = new Flower[]{
                        move.getFirstFlower(), move.getSecondFlower()
                };

                // Flower needs to be on the board
                for (Flower l : flowers) {
                    if (!isPositionOnBoard(l.getFirst()) | !isPositionOnBoard(l.getSecond()) | !isPositionOnBoard(
                            l.getThird()))
                        return false;
                }

                // Flower cannot be placed on other flower
                Set<Flower> flowerSet = getFlowerSet(null);
                if (flowerSet.contains(flowers[0]) || flowerSet.contains(flowers[1]))
                    return false;

                // Flower cannot be placed on ditch blocked fields
                Set<Flower> ditchBlocked = getAllDitchBlockedFlowers();
                if (ditchBlocked.contains(flowers[0]) || ditchBlocked.contains(flowers[1]))
                    return false;

                break;
            case Ditch:
                Ditch b = move.getDitch();
                Position start, end;
                start = b.getFirst();
                end = b.getSecond();

                // Ditch cannot be build on blocked position
                Set<Position> blockedPositionSet = getDitchPositionSet(null);
                if (blockedPositionSet.contains(start) || blockedPositionSet.contains(end))
                    return false;

                // Ditch needs to be build on own flower position
                Set<Position> flowerPositionSet = getFlowerPositionSet(turn);
                if (!flowerPositionSet.contains(start) || !flowerPositionSet.contains(end))
                    return false;

                // Ditch cannot be build next to flowers
                Set<Flower> blockedFlowerIntersection = getDitchBlockedFlowers(b);
                blockedFlowerIntersection.retainAll(getFlowerSet(null));
                if (!blockedFlowerIntersection.isEmpty())
                    return false;

                break;
            case Surrender:
                return true;
            case End:
                // TODO check
                return false;
            default:
                throw new IllegalArgumentException("illegal move type: " + move.getType());
        }
        return true;
    }

    private boolean isValidMoveFormat(Move move) {
        switch (move.getType()) {
            case Flower:
                Flower[] flowers = new Flower[]{
                        move.getFirstFlower(), move.getSecondFlower()
                };

                if (flowers[0].equals(flowers[1]))
                    return false;

                for (Flower flower : flowers)
                    if (!isValidFlowerFormat(flower))
                        return false;
                break;
            case Ditch:
                Ditch ditch = move.getDitch();
                if (!isValidDitchFormat(ditch))
                    return false;
                break;
            case Surrender:
            case End:
                return true;
            default:
                throw new IllegalArgumentException("illegal move type: " + move.getType());
        }
        return true;
    }

    private boolean isValidFlowerFormat(Flower flower) {
        Position a = flower.getFirst();
        Position b = flower.getSecond();
        Position c = flower.getThird();

        Set<Position> aNeighbors = getNeighborPositions(a);
        Set<Position> bNeighbors = getNeighborPositions(b);
        Set<Position> cNeighbors = getNeighborPositions(c);

        if (!aNeighbors.contains(b) || !aNeighbors.contains(c) || !bNeighbors.contains(a) || !bNeighbors.contains(
                c) || !cNeighbors.contains(a) || !cNeighbors.contains(b))
            return false;

        return true;
    }

    private boolean isValidDitchFormat(Ditch ditch) {
        Position a = ditch.getFirst();
        Position b = ditch.getSecond();
        return getNeighborPositions(a).contains(b);
    }

    // ------------------------------------------------------------

    /**
     * Test if a given {@link Position} is located on the board. A position
     * is on the board if and only if {@code column + row <= 2 + size}
     *
     * @param position - {@link Position} to test
     * @return true, if the position is on the board
     */
    private boolean isPositionOnBoard(Position position) {
        return position.getColumn() + position.getRow() <= size + 2;
    }

    private void nextPlayer() {
        if (turn == PlayerColor.Red)
            turn = PlayerColor.Blue;
        else
            turn = PlayerColor.Red;
    }

    private void setMoveOnBoard(Move move) {
        switch (move.getType()) {
            case Flower:
                getFlowerSet(turn).add(move.getFirstFlower());
                getFlowerSet(turn).add(move.getSecondFlower());
                break;
            case Ditch:
                getDitchSet(turn).add(move.getDitch());
                break;
            default:
                throw new IllegalArgumentException("illegal move type: " + move.getType());
        }
    }

    private Set<Flower> getFlowerSet(PlayerColor color) {
        if (color == null) {
            Set<Flower> resultSet = new HashSet<>();
            resultSet.addAll(redFlowers);
            resultSet.addAll(greenFlowers);
            return resultSet;
        } else if (color == PlayerColor.Red)
            return redFlowers;
        else
            return greenFlowers;
    }

    private Set<Ditch> getDitchSet(PlayerColor color) {
        if (color == null) {
            Set<Ditch> resultSet = new HashSet<>();
            resultSet.addAll(redDitches);
            resultSet.addAll(greenDitches);
            return resultSet;
        } else if (color == PlayerColor.Red)
            return redDitches;
        else
            return greenDitches;
    }

    private Set<Position> getFlowerPositionSet(PlayerColor color) {
        Set<Position> resultSet = new HashSet<>();
        for (Flower l : getFlowerSet(color)) {
            resultSet.add(l.getFirst());
            resultSet.add(l.getSecond());
            resultSet.add(l.getThird());
        }
        return resultSet;
    }

    // ------------------------------------------------------------

    private Set<Position> getDitchPositionSet(PlayerColor color) {
        Set<Position> resultSet = new HashSet<>();
        for (Ditch b : getDitchSet(color)) {
            resultSet.add(b.getFirst());
            resultSet.add(b.getSecond());
        }
        return resultSet;
    }

    private Set<Flower> getAllDitchBlockedFlowers() {
        Set<Flower> ditchBlocked = new HashSet<>();
        for (Ditch b : getDitchSet(null))
            for (Flower l : getDitchBlockedFlowers(b))
                ditchBlocked.add(l);
        return ditchBlocked;
    }

    private Set<Flower> getDitchBlockedFlowers(Ditch ditch) {
        Position start, end;
        start = ditch.getFirst();
        end = ditch.getSecond();
        int minCol, maxCol, minRow, maxRow;
        minCol = Math.min(start.getColumn(), end.getColumn());
        maxCol = Math.max(start.getColumn(), end.getColumn());
        minRow = Math.min(start.getRow(), end.getRow());
        maxRow = Math.max(start.getRow(), end.getRow());

        Position posA = null, posB = null;

        if (start.getColumn() == end.getColumn()) {
            int col = minCol;
            if (col > 1)
                posA = new Position(col - 1, maxRow);
            posB = new Position(col + 1, minRow);
        } else if (start.getRow() == end.getRow()) {
            int row = minRow;
            posA = new Position(minCol, row + 1);
            if (row > 1)
                posB = new Position(maxCol, row - 1);
        } else {
            posA = new Position(minCol, minRow);
            posB = new Position(maxCol, maxRow);
        }


        Set<Flower> resultFlowers = new HashSet<>();
        if (posA != null)
            resultFlowers.add(new Flower(start, end, posA));
        if (posB != null)
            resultFlowers.add(new Flower(start, end, posB));

        return resultFlowers;
    }

    private Set<Position> getNeighborPositions(Position position) {
        return getNeighborPositions(position, size);
    }

    public Set<Flower> getAllPossibleFlowers() {
        return getAllPossibleFlowers(size);
    }

    public Set<Ditch> getAllPossibleDitches() {
        return getAllPossibleDitches(size);
    }

    private Set<Move> getPossibleMoves() {
        // TODO make it correct!
        Set<Move> moves = new HashSet<>();

        Set<Flower> validFlowers = getAllPossibleFlowers();
        validFlowers.removeAll(getFlowerSet(null));
        validFlowers.removeAll(getAllDitchBlockedFlowers());

        // Create cartesian product
        for (Flower f1 : validFlowers)
            for (Flower f2 : validFlowers)
                if (!f1.equals(f2))
                    moves.add(new Move(f1, f2));

        moves.add(new Move(MoveType.Surrender));

        // Ditch moves
        moves.addAll(getAllPossibleDitches().stream()
                                            .map(Move::new)
                                            .filter(this::isValidMoveFormat)
                                            .filter(this::isValidMove)
                                            .collect(toSet()));

        return moves;
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

            @Override
            public Collection<Flower> getFlowers(PlayerColor color) {
                return new HashSet<>(BoardImpl.this.getFlowerSet(color));
            }

            @Override
            public Collection<Ditch> getDitches(PlayerColor color) {
                return new HashSet<>(BoardImpl.this.getDitchSet(color));
            }

            @Override
            public Collection<Move> getPossibleMoves() {
                return BoardImpl.this.getPossibleMoves();
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

        Set<Flower> whiteFlowers = getFlowerSet(PlayerColor.Red);
        Set<Flower> redFlowers = getFlowerSet(PlayerColor.Blue);

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
                        s += Stream.generate(() -> "__")
                                   .limit(5 - l)
                                   .collect(joining());
                    else {
                        String inner = Stream.generate(() -> "  ")
                                             .limit(5 - l)
                                             .collect(joining());

                        Flower flower = new Flower(new Position(c, r), new Position(c + 1, r), new Position(c, r + 1));
                        if (l == 1) {
                            if (whiteFlowers.contains(flower))
                                inner = "  ++++  ";
                            else if (redFlowers.contains(flower))
                                inner = "  ****  ";
                        } else if (l == 2) {
                            if (whiteFlowers.contains(flower))
                                inner = "  ++  ";
                            else if (redFlowers.contains(flower))
                                inner = "  **  ";
                        }

                        s += inner;
                    }
                    s += "\\";
                    String inner = Stream.generate(() -> "  ")
                                         .limit(l)
                                         .collect(joining());

                    if (c > 1) {
                        Flower flower = new Flower(new Position(c + 1, r), new Position(c, r + 1),
                                new Position(c + 1, r + 1));

                        if (l == 3) {
                            if (whiteFlowers.contains(flower))
                                inner = "  ++  ";
                            else if (redFlowers.contains(flower))
                                inner = "  **  ";
                        } else if (l == 4) {
                            if (whiteFlowers.contains(flower))
                                inner = "  ++++  ";
                            else if (redFlowers.contains(flower))
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
            sb.append(st.pop())
              .append("\n");
        }
        //        st.stream().forEach(s -> sb.append(s).append("\n"));
        return sb.toString();
    }
}
