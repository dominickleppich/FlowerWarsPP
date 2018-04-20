package diavolopp.board;

import diavolopp.preset.*;

import java.util.*;

public class BoardImpl implements Board {
    private final int size;
    private PlayerColor turn;
    private Status status;

    // ------------------------------------------------------------

    public BoardImpl(final int size) {
        this.size = size;

        turn = PlayerColor.White;
        status = Status.Ok;
    }

    // ------------------------------------------------------------

    @Override
    public void make(Move move) {
        // TODO write test for this
        if (status == Status.Illegal)
            throw new IllegalStateException("cannot perform moves on illegal state board");

        if (!isValidMove(move)) {
            status = Status.Illegal;
            return;
        }

        // Change to next player
        nextPlayer();
    }

    // ------------------------------------------------------------

    private boolean isValidMove(Move move) {
        switch (move.getType()) {
            case Land:
                Land[] lands = move.getLands();
                for (Land land : lands)
                    if (!isValidLand(land))
                        return false;
                break;
            case Bridge:
                Bridge bridge = move.getBridge();
                if (!isValidBridge(bridge))
                    return false;
                break;
            default:
                return false;
        }
        return true;
    }

    private boolean isValidLand(Land land) {
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

    private boolean isValidBridge(Bridge bridge) {
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

    // ------------------------------------------------------------

    public static Set<Position> getNeighborPositions(Position position) {
        int c = position.getColumn();
        int r = position.getRow();
        Set<Position> neighbors = new HashSet<>();
        neighbors.add(new Position(c+1, r));
        neighbors.add(new Position(c+1, r-1));
        neighbors.add(new Position(c, r-1));
        neighbors.add(new Position(c-1, r));
        neighbors.add(new Position(c-1, r+1));
        neighbors.add(new Position(c, r+1));
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
}
