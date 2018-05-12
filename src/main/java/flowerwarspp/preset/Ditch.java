package flowerwarspp.preset;

import java.io.*;
import java.util.*;

public class Ditch implements Serializable, Comparable<Ditch> {
    private Position[] positions;

    // ------------------------------------------------------------

    public Ditch(final Position first, final Position second) {
        positions = new Position[2];

        setFirst(first);
        setSecond(second);

        updateOrder();
    }

    private void updateOrder() {
        Arrays.sort(positions);
    }

    // ------------------------------------------------------------

    public Position getFirst() {
        return positions[0];
    }

    private void setFirst(final Position start) {
        if (start == null)
            throw new IllegalArgumentException("first cannot be null");
        positions[0] = start;
    }

    public Position getSecond() {
        return positions[1];
    }

    private void setSecond(final Position end) {
        if (end == null)
            throw new IllegalArgumentException("second cannot be null");
        positions[1] = end;
    }

    // ------------------------------------------------------------

    @Override
    public int hashCode() {
        return getFirst().hashCode() * Position.COMBINATIONS
                + getSecond().hashCode();
    }

    @Override
    public int compareTo(Ditch ditch) {
        // Due to the unique ordering it is easy to calculate
        if (!getFirst().equals(ditch.getFirst()))
            return getFirst().compareTo(ditch.getFirst());
        else
            return getSecond().getColumn() - ditch.getSecond().getColumn();
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null)
            return false;
        if (!(o instanceof Ditch))
            return false;
        Ditch b = (Ditch) o;
        return Arrays.equals(positions, b.positions);
    }

    @Override
    public String toString() {
        return "{" + getFirst().toString() + "," + getSecond().toString() + "}";
    }
}
