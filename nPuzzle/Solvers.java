package nPuzzle;
import nPuzzle.Puzzle.Move;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.PriorityQueue;

public class Solvers {

    public static class State implements Comparable<State> {
        int g;
        int h;
        int f;
        Puzzle puzzle;
        State parent;
        Move move;

        public State(int g, int h, Puzzle puzzle, State parent, Move move) {
            this.g = g;
            this.h = h;
            this.f = g + h;
            this.puzzle = puzzle;
            this.parent = parent;
            this.move = move;
        }

        public void setParent(State parent) {
            this.parent = parent;
            this.g = parent.g + 1;
            this.f = this.g + this.h;
        }

        public Integer getF() {
            return this.f;
        }

        @Override
        public int compareTo(State state) 
        {
            return this.getF().compareTo( state.getF() );
        }
    }

    public static State bestFirstSearch(Puzzle puzzle) {
        PriorityQueue<State> search = new PriorityQueue<State>();
        Set<Integer> vis = new HashSet<Integer>();
        Integer hash;
        State start = new State(0, Heuristics.overallManhattanDistance(puzzle.board), puzzle, null, null);
        search.add(start);
        
        while (search.size() > 0) {
            State state = search.poll();
            
            if (state.h == 0) {
                return state;
            }

            System.out.println("CHOSEN STATE");
            System.out.println("state.h: " + state.h);
            System.out.println("move: " + state.move);
            System.out.println(state.puzzle.toString());
            System.out.println(state.puzzle.emptyRow + " " + state.puzzle.emptyCol);
            System.out.println();

            vis.add(Arrays.deepHashCode(state.puzzle.board));

            for (State newState: Utils.potentialStates(state, false)) {
                hash = Arrays.deepHashCode(newState.puzzle.board);
                // System.out.println("state.h: " + newState.h);
                // System.out.println("move: " + newState.move);
                // System.out.println(newState.puzzle.toString());
                // System.out.println("hash: " + hash);
                // System.out.println();

                if (vis.contains(hash)) continue;
                search.add(newState);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        int[][] BOARD = {{1, 2, 3},
                         {4, 5, 0}, 
                         {7, 6, 8}};
        Puzzle puzzle = new Puzzle(BOARD);

        // puzzle.shuffle(100);
        Utils.printSteps(Utils.stateStack(Solvers.bestFirstSearch(puzzle)));
    }

}