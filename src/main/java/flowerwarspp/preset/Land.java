package flowerwarspp.preset;

import java.util.*;

public class Land {
    private Position[] positions;

    // ------------------------------------------------------------

    public Land(final Position first, final Position second, final Position
            third) {
        positions = new Position[3];

        setFirst(first);
        setSecond(second);
        setThird(third);

        updateOrder();
    }

    private void updateOrder() {
        Arrays.sort(positions);
    }

    // ------------------------------------------------------------

    public Position getFirst() {
        return positions[0];
    }

    private void setFirst(final Position first) {
        if (first == null)
            throw new IllegalArgumentException("first cannot be null");
        positions[0] = first;
    }

    public Position getSecond() {
        return positions[1];
    }

    private void setSecond(final Position second) {
        if (second == null)
            throw new IllegalArgumentException("second cannot be null");
        positions[1] = second;
    }

    public Position getThird() {
        return positions[2];
    }

    private void setThird(final Position third) {
        if (third == null)
            throw new IllegalArgumentException("third cannot be null");
        positions[2] = third;
    }

    // ------------------------------------------------------------

    @Override
    public int hashCode() {
        return (getFirst().hashCode() * Position.MAX_COLUMN * Position
                .MAX_ROW + getSecond().hashCode()) * Position
                .MAX_COLUMN * Position.MAX_ROW + getThird().hashCode();
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null)
            return false;
        if (!(o instanceof Land))
            return false;
        Land l = (Land) o;
        return Arrays.equals(positions, l.positions);
    }

    @Override
    public String toString() {
        return getFirst().toString() + "+" + getSecond().toString() + "+" +
                getThird().toString();
    }
}
