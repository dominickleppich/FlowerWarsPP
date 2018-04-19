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
}
