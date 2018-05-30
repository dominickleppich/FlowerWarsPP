package flowerwarspp;

import java.util.*;
import flowerwarspp.preset.*;

public class MyBoard implements Board {
    private int size;

    public MyBoard(int size) {
        this.size = size;
    }

    @Override
    public void make(Move move) throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Viewer viewer() {
        return new Viewer() {
            @Override
            public PlayerColor getTurn() {
                return PlayerColor.Red;
            }

            @Override
            public int getSize() {
                return size;
            }

            @Override
            public Status getStatus() {
                return Status.Ok;
            }

            @Override
            public Collection<Flower> getFlowers(PlayerColor color) {
                throw new UnsupportedOperationException();
            }

            @Override
            public Collection<Ditch> getDitches(PlayerColor color) {
                throw new UnsupportedOperationException();
            }

            @Override
            public Collection<Move> getPossibleMoves() {
                throw new UnsupportedOperationException();
            }

            @Override
            public int getPoints(PlayerColor color) {
                return 0;
            }
        };
    }
}