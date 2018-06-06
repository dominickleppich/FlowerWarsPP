package flowerwarspp;

public class MyBoard implements Board {
    private int size;

    public MyBoard(int size) {
        this.size = size;
    }

    @Override
    public void make(Move move) throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Viewer viewer() {
        return new Viewer() {
            @Override
            public PlayerColor getTurn() {
                return PlayerColor.Red;
            }

            @Override
            public int getSize() {
                return size;
            }

            @Override
            public Status getStatus() {
                return Status.Ok;
            }

            @Override
            public Collection<Flower> getFlowers(PlayerColor color) {
                List<Flower> list = new LinkedList<>();
                if (color == PlayerColor.Red)
                    list.add(new Flower(new Position(2, 2), new Position(3, 2), new Position(2, 3)));
                return list;
            }

            @Override
            public Collection<Ditch> getDitches(PlayerColor color) {
                List<Ditch> list = new LinkedList<>();

                return list;
            }

            @Override
            public Collection<Move> getPossibleMoves() {
                throw new UnsupportedOperationException();
            }

            @Override
            public int getPoints(PlayerColor color) {
                return color == PlayerColor.Red ? 2 : 1;
            }
        };
    }
}
