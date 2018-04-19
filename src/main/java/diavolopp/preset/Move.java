package diavolopp.preset;

import java.io.*;

public class Move implements Serializable {
    private MoveType type;
    private Land[] lands;
    private Bridge bridge;

    // ------------------------------------------------------------

    public Move(final Land a, final Land b) {
        type = MoveType.Land;

        if (a == null || b == null)
            throw new IllegalArgumentException("lands cannot be null");
        lands = new Land[]{a, b};
    }

    public Move(final Bridge b) {
        type = MoveType.Bridge;

        if (b == null)
            throw new IllegalArgumentException("bridge cannot be null");
        bridge = b;
    }

    // ------------------------------------------------------------

    public MoveType getType() {
        return type;
    }

    public Land[] getLands() {
        if (getType() != MoveType.Land)
            throw new IllegalStateException("cannot be called on type " + type);
        return lands.clone();
    }

    public Bridge getBridge() {
        if (getType() != MoveType.Bridge)
            throw new IllegalStateException("cannot be called on type " + type);
        return bridge;
    }

    // ------------------------------------------------------------

    @Override
    public String toString() {
        switch (getType()) {
            case Land:
                return "{" + lands[0].toString() + ", " + lands[1].toString()
                        + "}";
            case Bridge:
                return bridge.toString();
        }
        // The following should be unreachable code
        throw new IllegalStateException("move is neither a land nor a bridge");
    }
}
