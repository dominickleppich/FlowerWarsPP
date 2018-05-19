package flowerwarspp.board;

import flowerwarspp.preset.*;
import org.slf4j.*;

import java.util.*;
import java.util.stream.*;

import static java.util.stream.Collectors.*;

public class BoardImpl implements Board {
    private static final Logger logger = LoggerFactory.getLogger(BoardImpl.class);

    private static final int GARDEN_SIZE = 4;

    private final int size;
    private PlayerColor turn;
    private Status status;

    private Set<FlowerBed> redFlowerBeds, blueFlowerBeds;
    private Set<Ditch> redDitches, blueDitches;


    /**
     * This class represents a flower bed. Flower beds can be added new flowers and merged together.
     */
    class FlowerBed {
        private Set<Flower> flowers;
        private Set<Position> positions;
        private Set<Flower> boundingFlowers;

        // ------------------------------------------------------------

        /**
         * Create a new flower bed. Flower beds contain always at least one flower.
         *
         * @param flower initial flower
         */
        FlowerBed(Flower flower) {
            flowers = new HashSet<>();
            positions = new HashSet<>();
            boundingFlowers = new HashSet<>();

            addFlower(flower, false);
        }

        FlowerBed(Set<FlowerBed> flowerBeds) {
            flowers = new HashSet<>();
            positions = new HashSet<>();
            boundingFlowers = new HashSet<>();

            for (FlowerBed fb : flowerBeds) {
                flowers.addAll(fb.getFlowers());
                positions.addAll(fb.positions);
                boundingFlowers.addAll(fb.boundingFlowers);
            }

            boundingFlowers.removeAll(flowers);
        }

        // ------------------------------------------------------------

        /**
         * Get the number of containing flowers
         *
         * @return number of flowers
         */
        int size() {
            return flowers.size();
        }

        Set<Position> getPositions() {
            return new HashSet<>(positions);
        }

        Set<Flower> getBoundingFlowers() {
            return new HashSet<>(boundingFlowers);
        }

        Set<Flower> getFlowers() {
            return new HashSet<>(flowers);
        }

        // ------------------------------------------------------------

        void addFlower(Flower flower) {
            addFlower(flower, true);
        }

        private void addFlower(Flower flower, boolean check) {
            if (check && !boundingFlowers.contains(flower))
                throw new IllegalArgumentException("flower cannot be added to flowerbed");

            flowers.add(flower);

            positions.addAll(getFlowerPositionSet(flower));

            boundingFlowers.remove(flower);

            for (Flower f : getNeighborFlowers(flower))
                if (!flowers.contains(f))
                    boundingFlowers.add(f);
        }
    }

    // ------------------------------------------------------------

    public BoardImpl(final int size) {
        this.size = size;

        turn = PlayerColor.Red;
        status = Status.Ok;

        redFlowerBeds = new HashSet<>();
        blueFlowerBeds = new HashSet<>();
        redDitches = new HashSet<>();
        blueDitches = new HashSet<>();

        logger.debug("New board instantiated");
    }

    // ------------------------------------------------------------
    // * Interface methods *

    @Override
    public void make(Move move) throws IllegalStateException {
        if (status != Status.Ok)
            throw new IllegalStateException("cannot perform moves on this board anymore");

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

    /**
     * Return a {@link Viewer} for this board.
     *
     * @return a viewer
     */
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

            @Override
            public int getPoints(PlayerColor color) {
                return BoardImpl.this.getPoints(color);
            }
        };
    }

    // ------------------------------------------------------------
    // * Move making helper functions *

    /**
     * Change to next player.
     */
    private void nextPlayer() {
        turn = turn == PlayerColor.Red ? PlayerColor.Blue : PlayerColor.Red;
    }

    /**
     * Set the move on the board. The move will not be checked for validity anymore, this has to be done beforehand.
     *
     * @param move the move to set on the board
     */
    private void setMoveOnBoard(Move move) {
        switch (move.getType()) {
            case Flower:
                setFlowerOnBoard(move.getFirstFlower());
                setFlowerOnBoard(move.getSecondFlower());
                break;
            case Ditch:
                getDitchSet(turn).add(move.getDitch());
                break;
            case Surrender:
            case End:
                // Nothing to be done here
                break;
            default:
                throw new IllegalArgumentException("illegal move type: " + move.getType());
        }
    }

    private void setFlowerOnBoard(Flower flower) {
        // Find touched flower beds
        Set<FlowerBed> neighboringBeds = new HashSet<>();
        for (FlowerBed fb : getFlowerBed(turn))
            if (fb.getBoundingFlowers()
                  .contains(flower))
                neighboringBeds.add(fb);

        // If no flower bed touched, create a new one
        if (neighboringBeds.isEmpty()) {
            getFlowerBed(turn).add(new FlowerBed(flower));
            // .. otherwise fusion all flower beds together
        } else {
            // Remove old separate flower beds
            getFlowerBed(turn).removeAll(neighboringBeds);
            // Create a fusioned flower bed
            FlowerBed newFlowerBed = new FlowerBed(neighboringBeds);
            newFlowerBed.addFlower(flower);
            getFlowerBed(turn).add(newFlowerBed);
        }
    }

    // ------------------------------------------------------------
    // * Move validation helpers *

    /**
     * Checks move format for validity. For moves with {@link MoveType#Flower} and {@link MoveType#Ditch} it is checked,
     * if the given positions describe a valid flower or ditch.
     *
     * @param move the move to check
     * @return true, if the format of the move is valid
     */
    private boolean isValidMoveFormat(Move move) {
        // Check format depending on the move type
        switch (move.getType()) {
            case Flower:
                // Extract both flowers
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
            // The surrender and end move cannot have a broken format
            case Surrender:
            case End:
                return true;
            default:
                throw new IllegalArgumentException("illegal move type: " + move.getType());
        }
        return true;
    }

    /**
     * Checks move for validity. All game rules are checked in this method.
     *
     * @param move the move to check
     * @return true, if the move is valid
     */
    private boolean isValidMove(Move move) {
        // Check rules depending on the move type
        switch (move.getType()) {
            case Flower:
                // Extract both flowers
                Flower[] flowers = new Flower[]{
                        move.getFirstFlower(), move.getSecondFlower()
                };

                // TODO even if two separate flowers are valid, the combination can make it invalid!
                Set<FlowerBed> flowerBeds = getFlowerBed(turn);
                Set<Flower> invalidFlowers = getFlowerSet(null);
                invalidFlowers.addAll(getAllDitchBlockedFlowers());
                Set<Position> gardenPositions = flowerBeds.stream()
                                                          .filter(bed -> bed.size() == GARDEN_SIZE)
                                                          .flatMap(bed -> bed.getPositions()
                                                                             .stream())
                                                          .collect(toSet());

                for (Flower f : flowers)
                    if (!isValidFlower(f, flowerBeds, invalidFlowers, gardenPositions))
                        return false;

                if (!isValidFlowerCombination(flowers[0], flowers[1]))
                    return false;

                break;
            case Ditch:
                if (!isValidDitch(move.getDitch()))
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

    /**
     * Checks if the three positions of the flower describe a valid flower.
     *
     * @param flower the flower to check
     * @return true, if the flower has a valid format
     */
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

    /**
     * Checks if the two positions of the ditch describe a valid ditch.
     *
     * @param ditch the ditch to check
     * @return true, if the ditch has a valid format
     */
    private boolean isValidDitchFormat(Ditch ditch) {
        Position a = ditch.getFirst();
        Position b = ditch.getSecond();
        return getNeighborPositions(a).contains(b);
    }

    /**
     * Checks if a flower is valid (can be set on the board). Garden rules are not fully checked in this method. To fully validate a flower move you need to call {@link #isValidFlowerCombination(Flower, Flower)}.
     *
     * @param flower the flower to check
     * @return true, if the flower is valid
     */
    private boolean isValidFlower(Flower flower, Set<FlowerBed> flowerBeds, Set<Flower> invalidFlowers, Set<Position> gardenBlockedPositions) {
        Set<Position> flowerPositions = getFlowerPositionSet(flower);

        // Flower needs to be on the board
        for (Position p : flowerPositions)
            if (!isPositionOnBoard(p))
                return false;

        // Flower cannot be placed on other flower or on ditch blocked field
        if (invalidFlowers.contains(flower))
            return false;

        // Garden cannot be touched
        for (Position p : flowerPositions) {
            // If flower shares points with any garden, error
            if (gardenBlockedPositions.contains(p))
                return false;
        }

        // If Flower touches other flower bed and arises to garden, no other flower beds or garden can be touched
        Set<FlowerBed> neighboringBeds = new HashSet<>();
        for (FlowerBed fb : flowerBeds)
            if (fb.getBoundingFlowers()
                  .contains(flower))
                neighboringBeds.add(fb);

        if (!neighboringBeds.isEmpty()) {
            FlowerBed newFlowerBed = new FlowerBed(neighboringBeds);
            newFlowerBed.addFlower(flower);

            if (newFlowerBed.size() > GARDEN_SIZE)
                return false;

            if (newFlowerBed.size() == GARDEN_SIZE) {
                Set<Position> gardenPositions = newFlowerBed.getPositions();
                Set<Position> otherPositions = new HashSet<>();
                for (FlowerBed fb : flowerBeds)
                    if (!neighboringBeds.contains(fb))
                        otherPositions.addAll(fb.getPositions());

                for (Position p : gardenPositions)
                    if (otherPositions.contains(p))
                        return false;
            }
        }

        return true;
    }

    /**
     * Checks if a flower combination is valid (can be set together on the board)
     *
     * @param a first flower to check
     * @param b second flower to check
     * @return true, if the two flowers are a valid combination
     */
    private boolean isValidFlowerCombination(Flower a, Flower b) {
        // Garden rules

        Set<FlowerBed> relevantFlowerBeds = new HashSet<>(getFlowerBed(turn));

        Flower[] flowers = new Flower[]{a, b};

        for (Flower f : flowers) {
            // Garden size cannot be exceed

            // Get all neighboring flower beds
            Set<FlowerBed> neighboringBeds = new HashSet<>();
            for (FlowerBed fb : relevantFlowerBeds)
                if (fb.getBoundingFlowers()
                      .contains(f))
                    neighboringBeds.add(fb);
            relevantFlowerBeds.removeAll(neighboringBeds);

            // If no flower bed touched, create a new one
            if (!neighboringBeds.isEmpty()) {
                // Create a fusioned flower bed
                FlowerBed newFlowerBed = new FlowerBed(neighboringBeds);
                newFlowerBed.addFlower(f);
                // If size is exceed, error
                if (newFlowerBed.size() > GARDEN_SIZE)
                    return false;

                // If new flower bed arises to garden and touches other flower beds, error
                if (newFlowerBed.size() == GARDEN_SIZE) {
                    Set<Position> gardenPositions = newFlowerBed.getPositions();
                    Set<Position> otherPositions = new HashSet<>();
                    for (FlowerBed fb : relevantFlowerBeds)
                        otherPositions.addAll(fb.getPositions());
                    gardenPositions.retainAll(otherPositions);
                    if (!gardenPositions.isEmpty())
                        return false;
                }

                // New flower bed can still touch gardens :/
                else {
                    Set<Position> newFlowerBedPositions = newFlowerBed.getPositions();
                    Set<Position> otherPositions = new HashSet<>();
                    for (FlowerBed fb : relevantFlowerBeds)
                        if (fb.size() == GARDEN_SIZE)
                            otherPositions.addAll(fb.getPositions());
                    newFlowerBedPositions.retainAll(otherPositions);
                    if (!newFlowerBedPositions.isEmpty())
                        return false;
                }


                relevantFlowerBeds.add(newFlowerBed);
            } else {
                // New created flower bed cannot touch other gardens as well
                FlowerBed newFlowerBed = new FlowerBed(f);

                Set<Position> newFlowerBedPositions = newFlowerBed.getPositions();
                Set<Position> otherPositions = new HashSet<>();
                for (FlowerBed fb : relevantFlowerBeds)
                    if (fb.size() == GARDEN_SIZE)
                        otherPositions.addAll(fb.getPositions());
                newFlowerBedPositions.retainAll(otherPositions);
                if (!newFlowerBedPositions.isEmpty())
                    return false;

                relevantFlowerBeds.add(newFlowerBed);
            }
        }

        return true;
    }

    /**
     * Checks if a ditch is valid (can be set on the board)
     *
     * @param ditch the ditch to check
     * @return true, if the ditch is valid
     */
    private boolean isValidDitch(Ditch ditch) {
        // Extract both positions
        Position[] positions = new Position[]{ditch.getFirst(), ditch.getSecond()};

        // Ditch cannot be build on blocked positions
        Set<Position> blockedPositionSet = getAllDitchPositionSet(null);
        for (Position p : positions)
            if (blockedPositionSet.contains(p))
                return false;

        // Ditch needs to be build next to own flowers
        Set<Position> flowerPositionSet = getAllFlowerPositionSet(turn);
        for (Position p : positions)
            if (!flowerPositionSet.contains(p))
                return false;

        // Ditch cannot be build next to flowers
        Set<Flower> blockedFlowerIntersection = getDitchBlockedFlowers(ditch);
        blockedFlowerIntersection.retainAll(getFlowerSet(null));
        if (!blockedFlowerIntersection.isEmpty())
            return false;

        return true;
    }

    // ------------------------------------------------------------
    // * Basic board logic functionality *

    /**
     * Checks if a given {@link Position} is located on the board. A position
     * is on the board if and only if {@code column + row <= 2 + size}
     *
     * @param position - {@link Position} to test
     * @return true, if the position is on the board
     */
    private boolean isPositionOnBoard(Position position) {
        return position.getColumn() + position.getRow() <= size + 2;
    }

    private boolean isPositionOnBoard(int column, int row) {
        try {
            return isPositionOnBoard(new Position(column, row));
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

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

    public Set<Flower> getAllPossibleFlowers() {
        return getAllPossibleFlowers(size);
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

    public Set<Ditch> getAllPossibleDitches() {
        return getAllPossibleDitches(size);
    }

    private Set<Flower> getNeighborFlowers(Flower flower) {
        Set<Flower> flowers = new HashSet<>();

        Ditch[] flowerNeighboringDitches = new Ditch[3];
        Position[] flowerPositions = new Position[]{flower.getFirst(), flower.getSecond(), flower.getThird()};

        for (int i = 0; i < 3; i++)
            flowerNeighboringDitches[i] = new Ditch(flowerPositions[i], flowerPositions[(i + 1) % 3]);

        for (Ditch d : flowerNeighboringDitches)
            flowers.addAll(getDitchBlockedFlowers(d));

        flowers.remove(flower);

        return flowers;
    }

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

    private Set<Position> getNeighborPositions(Position position) {
        return getNeighborPositions(position, size);
    }

    private Set<Move> getPossibleMoves() {
        long start = System.currentTimeMillis();
        Set<Move> moves = new HashSet<>();

        Set<FlowerBed> flowerBeds = getFlowerBed(turn);
        Set<Flower> invalidFlowers = getFlowerSet(null);
        invalidFlowers.addAll(getAllDitchBlockedFlowers());
        Set<Position> gardenPositions = flowerBeds.stream()
                                                  .filter(bed -> bed.size() == GARDEN_SIZE)
                                                  .flatMap(bed -> bed.getPositions()
                                                                     .stream())
                                                  .collect(toSet());

        Set<Flower> validFlowers = getAllPossibleFlowers();

        // Remove all flowers which are already invalid
        validFlowers.removeAll(invalidFlowers);

        // Remove non valid flowers
        Set<Flower> nonValidFlowers = new HashSet<>();
        for (Flower f : validFlowers)
            if (!isValidFlower(f, flowerBeds, invalidFlowers, gardenPositions))
                nonValidFlowers.add(f);
        validFlowers.removeAll(nonValidFlowers);

        Set<Move> flowerMoves = new HashSet<>();
        // Create cartesian product
        for (Flower f1 : validFlowers)
            for (Flower f2 : validFlowers)
                if (!f1.equals(f2))
                    flowerMoves.add(new Move(f1, f2));

        int maxDistance = 3;
        Set<Move> invalidFlowerMoves = new HashSet<>();
        for (Move m : flowerMoves) {
            Flower f1 = m.getFirstFlower();
            Flower f2 = m.getSecondFlower();

            // Only check combinations with higher column or row distance than predefined
            if (Math.abs(f1.getFirst()
                           .getColumn() - f2.getFirst()
                                            .getColumn()) <= maxDistance && Math.abs(f1.getFirst()
                                                                                       .getRow() - f2.getFirst()
                                                                                                     .getRow()) <= maxDistance && !isValidFlowerCombination(
                    m.getFirstFlower(), m.getSecondFlower()))
                invalidFlowerMoves.add(m);
        }

        flowerMoves.removeAll(invalidFlowerMoves);
        moves.addAll(flowerMoves);

        moves.add(new Move(MoveType.Surrender));

        // Ditch moves
        moves.addAll(getAllPossibleDitches().stream()
                                            .filter(this::isValidDitch)
                                            .map(Move::new)
                                            .collect(toSet()));

        // TODO test this
        if (validFlowers.size() < 2)
            moves.add(new Move(MoveType.End));

        long stop = System.currentTimeMillis();

        logger.debug(String.format("Possible moves calculated in %.3f s", (double) (stop - start) / 1000.0));

        return moves;
    }

    // ------------------------------------------------------------
    // * Board state getters *

    private Set<FlowerBed> getFlowerBed(PlayerColor color) {
        if (color == null) {
            Set<FlowerBed> resultSet = new HashSet<>();
            resultSet.addAll(redFlowerBeds);
            resultSet.addAll(blueFlowerBeds);
            return resultSet;
        } else if (color == PlayerColor.Red)
            return redFlowerBeds;
        else
            return blueFlowerBeds;
    }

    private Set<Flower> getFlowerSet(PlayerColor color) {
        return getFlowerBed(color).stream()
                                  .flatMap(bed -> bed.getFlowers()
                                                     .stream())
                                  .collect(Collectors.toSet());
    }

    private Set<Ditch> getDitchSet(PlayerColor color) {
        if (color == null) {
            Set<Ditch> resultSet = new HashSet<>();
            resultSet.addAll(redDitches);
            resultSet.addAll(blueDitches);
            return resultSet;
        } else if (color == PlayerColor.Red)
            return redDitches;
        else
            return blueDitches;
    }

    private Set<Position> getFlowerPositionSet(Flower flower) {
        Set<Position> resultSet = new HashSet<>();
        resultSet.add(flower.getFirst());
        resultSet.add(flower.getSecond());
        resultSet.add(flower.getThird());
        return resultSet;
    }

    private Set<Position> getAllFlowerPositionSet(PlayerColor color) {
        Set<Position> resultSet = new HashSet<>();
        for (Flower f : getFlowerSet(color))
            resultSet.addAll(getFlowerPositionSet(f));
        return resultSet;
    }

    private Set<Position> getDitchPositionSet(Ditch ditch) {
        Set<Position> resultSet = new HashSet<>();
        resultSet.add(ditch.getFirst());
        resultSet.add(ditch.getSecond());
        return resultSet;
    }

    private Set<Position> getAllDitchPositionSet(PlayerColor color) {
        Set<Position> resultSet = new HashSet<>();
        for (Ditch d : getDitchSet(color))
            resultSet.addAll(getDitchPositionSet(d));
        return resultSet;
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

    private Set<Flower> getAllDitchBlockedFlowers() {
        Set<Flower> ditchBlocked = new HashSet<>();
        for (Ditch b : getDitchSet(null))
            for (Flower l : getDitchBlockedFlowers(b))
                ditchBlocked.add(l);
        return ditchBlocked;
    }

    private int getPoints(PlayerColor color) {
        // TODO test this
        Set<FlowerBed> ownGardens = getFlowerBed(color).stream()
                                                       .filter(bed -> bed.size() == GARDEN_SIZE)
                                                       .collect(toSet());

        Set<FlowerBed> visitedFlowerBeds = new HashSet<>();

        int score = 0;

        // Iterate over all own gardens and try to find all connected gardens
        while (!ownGardens.isEmpty()) {

            // Take a garden from the garden set
            FlowerBed startGarden = ownGardens.iterator()
                                              .next();

            // CONNECTION LOGIC START

            Set<FlowerBed> connected = new HashSet<>();
            findConnectedFlowerBeds(startGarden, color, connected, visitedFlowerBeds);

            Set<FlowerBed> connectedGardens = connected.stream()
                                                       .filter(bed -> bed.size() == GARDEN_SIZE)
                                                       .collect(toSet());

            score += calculateConnectionScore(connectedGardens.size());

            // CONNECTION LOGIC END

            // Remove all visited flower beds
            ownGardens.removeAll(connected);
        }

        return score;
    }

    private void findConnectedFlowerBeds(FlowerBed flowerBed, PlayerColor color, Set<FlowerBed> resultSet, Set<FlowerBed> visited) {
        // Visit current flower bed
        visited.add(flowerBed);
        resultSet.add(flowerBed);

        Set<Position> flowerBedPositions = flowerBed.getPositions();

        // Search for all ditches starting anywhere on the flower bed
        Set<Ditch> connectionDitches = getDitchSet(color).stream()
                                                         .filter(ditch -> {
                                                             Position p1 = ditch.getFirst();
                                                             Position p2 = ditch.getSecond();
                                                             return flowerBedPositions.contains(
                                                                     p1) || flowerBedPositions.contains(p2);
                                                         })
                                                         .collect(toSet());
        Set<Position> connectionDitchPositions = new HashSet<>();
        for (Ditch d : connectionDitches) {
            connectionDitchPositions.add(d.getFirst());
            connectionDitchPositions.add(d.getSecond());
        }

        // Test all non visited flower beds for a connection
        Set<FlowerBed> availableFlowerBeds = new HashSet<>(getFlowerBed(color));
        availableFlowerBeds.removeAll(visited);

        for (FlowerBed fb : availableFlowerBeds) {
            // Add only flower beds, which are connected through a ditch
            Set<Position> fbPositions = fb.getPositions();
            fbPositions.retainAll(connectionDitchPositions);

            // If a connected flower bed is found, add it and look recursively for other ones
            if (!fbPositions.isEmpty()) {
                findConnectedFlowerBeds(fb, color, resultSet, visited);
            }
        }
    }

    private int calculateConnectionScore(int n) {
        if (n <= 1)
            return 1;
        return n + calculateConnectionScore(n-1);
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
