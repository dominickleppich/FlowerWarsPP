package diavolopp.board;

import diavolopp.preset.*;

public class BoardImpl implements Board {
    private final int size;
    private PlayerColor turn;
    private Status status;

    // ------------------------------------------------------------

    public BoardImpl(final int size) {
        this.size = size;

        turn = PlayerColor.White;
        status = Status.Ok;
    }

    // ------------------------------------------------------------

    private void nextPlayer() {
        if (turn == PlayerColor.White)
            turn = PlayerColor.Red;
        else
            turn = PlayerColor.White;
    }

    // ------------------------------------------------------------

    @Override
    public void make(Move move) {

        // Change to next player
        nextPlayer();
    }

    // ------------------------------------------------------------

    @Override
    public Viewer viewer() {
        return new Viewer() {
            @Override
            public PlayerColor getTurn() {
                return BoardImpl.this.turn;
            }

            @Override
            public int getSize() {
                return BoardImpl.this.size;
            }

            @Override
            public Status getStatus() {
                return BoardImpl.this.status;
            }
        };
    }
}
