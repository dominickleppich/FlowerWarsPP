package flowerwarspp.ui;

import flowerwarspp.preset.*;

public interface Display {
    void setViewer(Viewer viewer);
    void reset();
    void update(Move move);
    void showStatus(Status status);
    void close();
}
