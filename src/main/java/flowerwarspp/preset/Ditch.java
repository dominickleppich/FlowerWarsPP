package flowerwarspp.preset;

import java.io.*;
import java.util.*;

public class Ditch implements Serializable {
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
            throw new IllegalArgumentException("start cannot be null");
        positions[0] = start;
    }

    public Position getSecond() {
        return positions[1];
    }

    private void setSecond(final Position end) {
        if (end == null)
            throw new IllegalArgumentException("end cannot be null");
        positions[1] = end;
    }

    // ------------------------------------------------------------

    @Override
    public int hashCode() {
        return getFirst().hashCode() * Position.MAX_COLUMN * Position.MAX_ROW
                + getSecond().hashCode();
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
