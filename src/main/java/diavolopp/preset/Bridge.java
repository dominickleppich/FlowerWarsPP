package diavolopp.preset;

import java.io.Serializable;

public class Bridge implements Serializable {
    private Position start, end;

    // ------------------------------------------------------------

    public Bridge(final Position start, final Position end) {
        setStart(start);
        setEnd(end);

        updateOrder();
    }

    private void updateOrder() {
        if (getStart().compareTo(getEnd()) > 0) {
            Position tmp = getStart();
            setStart(getEnd());
            setEnd(tmp);
        }
    }

    // ------------------------------------------------------------

    public Position getStart() {
        return start;
    }

    private void setStart(final Position start) {
        if (start == null)
            throw new IllegalArgumentException("start cannot be null");
        this.start = start;
    }

    public Position getEnd() {
        return end;
    }

    private void setEnd(final Position end) {
        if (end == null)
            throw new IllegalArgumentException("end cannot be null");
        this.end = end;
    }

    // ------------------------------------------------------------

    @Override
    public int hashCode() {
        return start.hashCode() * Position.MAX_COLUMN * Position.MAX_ROW + end.hashCode();
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null)
            return false;
        if (!(o instanceof Bridge))
            return false;
        Bridge b = (Bridge) o;
        return getStart().equals(b.getStart()) && getEnd().equals(b.getEnd());
    }

    @Override
    public String toString() {
        return getStart().toString() + "-" + getEnd().toString();
    }
}
