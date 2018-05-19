package flowerwarspp.player;

import flowerwarspp.preset.*;

public class InteractivePlayer extends AbstractPlayer {
    private Requestable requestable;

    // ------------------------------------------------------------

    public InteractivePlayer(Requestable requestable) {
        this.requestable = requestable;
    }

    // ------------------------------------------------------------

    @Override
    protected Move provideMove() throws Exception {
        return requestable.request();
    }
}
