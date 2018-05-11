package flowerwarspp.ui;

import flowerwarspp.preset.*;

public interface UserInterface extends Requestable {
    void setViewer(Viewer viewer);
    void update(Move move);
}
