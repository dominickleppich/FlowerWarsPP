package diavolopp.preset;

import java.io.*;
import java.util.*;

public class Bridge implements Serializable {
    private Position[] positions;

    // ------------------------------------------------------------

    public Bridge(final Position start, final Position end) {
        positions = new Position[2];

        setStart(start);
        setEnd(end);

        updateOrder();
    }

    private void updateOrder() {
        Arrays.sort(positions);
    }

    // ------------------------------------------------------------

    public Position getStart() {
        return positions[0];
    }

    private void setStart(final Position start) {
        if (start == null)
            throw new IllegalArgumentException("start cannot be null");
        positions[0] = start;
    }

    public Position getEnd() {
        return positions[1];
    }

    private void setEnd(final Position end) {
        if (end == null)
            throw new IllegalArgumentException("end cannot be null");
        positions[1] = end;
    }

    // ------------------------------------------------------------

    @Override
    public int hashCode() {
        return getStart().hashCode() * Position.MAX_COLUMN * Position.MAX_ROW
                + getEnd().hashCode();
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null)
            return false;
        if (!(o instanceof Bridge))
            return false;
        Bridge b = (Bridge) o;
        return Arrays.equals(positions, b.positions);
    }

    @Override
    public String toString() {
        return getStart().toString() + "-" + getEnd().toString();
    }
}
