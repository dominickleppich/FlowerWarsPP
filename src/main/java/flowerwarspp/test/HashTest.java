package flowerwarspp.test;

import flowerwarspp.preset.*;

import java.util.*;

public class HashTest {
    public static void main(String[] args) {
        List<Move> moves = new LinkedList<>(createAllPossibleMoves());
        Collections.sort(moves);

        Map<Integer, List<Move>> hashMap = new HashMap<>();

        for (Move m : moves) {
            Integer hash = m.hashCode();
            System.out.println(String.format("%20d %s", hash, m.toString()));
            if (hashMap.containsKey(hash))
                hashMap.get(hash).add(m);
            else {
                List<Move> flowerList = new LinkedList<>();
                flowerList.add(m);
                hashMap.put(hash, flowerList);
            }
        }

        System.out.println("\n\n\n");
        for (Map.Entry<Integer, List<Move>> e : hashMap.entrySet()) {
            if (e.getValue().size() > 1) {
                System.out.println("Duplicates for hash: " + e.getKey());
                for (Move m : e.getValue())
                    System.out.println(m);
                System.out.println();
            }
        }
    }

    // ------------------------------------------------------------

    public static Set<Move> createAllPossibleMoves() {
        Set<Move> moves = new HashSet<>();

        // All possible flower moves
        Set<Flower> possibleFlowers = createAllPossibleFlowers();
        for (Flower f1 : possibleFlowers)
            for (Flower f2 : possibleFlowers)
                if (!f1.equals(f2))
                    moves.add(new Move(f1, f2));

        // All possible ditch moves
        for (Ditch d : createAllPossibleDitches())
            moves.add(new Move(d));

        // Add special moves
        moves.add(new Move(MoveType.Surrender));
        moves.add(new Move(MoveType.End));

        return moves;
    }

    public static Set<Ditch> createAllPossibleDitches() {
        Set<Ditch> ditches = new HashSet<>();

        for (int c = 1; c <= Position.MAX_VALUE; c++) {
            for (int r = 1; r < Position.MAX_VALUE; r++) {
                if (c + r <= Position.MAX_VALUE) {
                    ditches.add(new Ditch(new Position(c,r), new Position(c+1, r)));
                    ditches.add(new Ditch(new Position(c, r), new Position(c,
                            r + 1)));
                }
                if (c > 1)
                    ditches.add(new Ditch(new Position(c, r), new Position(c
                            - 1, r + 1)));
            }
        }

        return ditches;
    }

    public static Set<Flower> createAllPossibleFlowers() {
        Set<Flower> flowers = new HashSet<>();

        for (int c = 1; c < Position.MAX_VALUE; c++) {
            for (int r = 1; r < Position.MAX_VALUE; r++) {
                if (c + r <= Position.MAX_VALUE)
                    flowers.add(new Flower(new Position(c, r), new Position(c
                            + 1, r), new Position(c, r + 1)));

                if (c + r < Position.MAX_VALUE) {
                    flowers.add(new Flower(new Position(c + 1, r + 1), new
                            Position(c
                            + 1, r), new Position(c, r + 1)));
                }
            }
        }

        return flowers;
    }
}
