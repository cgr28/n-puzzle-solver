package nPuzzle;
import nPuzzle.Solvers.State;
import nPuzzle.Puzzle.Move;

import java.util.ArrayList;
import java.util.Stack;

public class Utils {
    
    public static int[][] generateNPuzzle(int nRow, int nCol) {
        int[][] puzzle = new int[nRow][nCol];
        int count = 1;
        for (int i = 0; i < nRow; i++) {
            for (int j = 0; j < nCol; j++) {
                puzzle[i][j] = count++;
            }
        }
        puzzle[nRow][nCol] = 0;
        return puzzle;
    }

    public static int[][] deepCopy(int[][] arr) {
        int[][] copy = new int[arr.length][arr[0].length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                copy[i][j] = arr[i][j];
            }
        }
        return copy;
    }

    // CHANGE COPY TO DEEPCOPY
    public static ArrayList<State>  potentialStates(State state, boolean setParent) {
        ArrayList<Solvers.State> states = new ArrayList<State>();
        int emptyRow = state.puzzle.emptyRow;
        int emptyCol = state.puzzle.emptyCol;
        int maxRow = state.puzzle.board.length;
        int maxCol = state.puzzle.board[0].length; 
        Puzzle tempPuzzle;
        State tempState;
        State parent = state;
        if (!setParent) {
            parent = null;
        }
        if (emptyRow > 0) {
            tempPuzzle = new Puzzle(deepCopy(state.puzzle.board));
            tempPuzzle.moveTile(Move.DOWN);
            // System.out.println("pot states UP");
            // System.out.println(tempPuzzle.toString());
            // System.out.println();
            tempState = new State(0, Heuristics.overallManhattanDistance(tempPuzzle.board), tempPuzzle, parent, Move.DOWN);
            states.add(tempState);
        }
        if (emptyRow < maxRow-1) {
            tempPuzzle = new Puzzle(deepCopy(state.puzzle.board));
            tempPuzzle.moveTile(Move.UP);
            // System.out.println("pot states DOWN");
            // System.out.println(tempPuzzle.toString());
            // System.out.println();
            tempState = new State(0, Heuristics.overallManhattanDistance(tempPuzzle.board), tempPuzzle, parent, Move.UP);
            states.add(tempState);
        }
        if (emptyCol > 0) {
            tempPuzzle = new Puzzle(deepCopy(state.puzzle.board));
            tempPuzzle.moveTile(Move.RIGHT);
            // System.out.println("pot states LEFT");
            // System.out.println(tempPuzzle.toString());
            // System.out.println();
            tempState = new State(0, Heuristics.overallManhattanDistance(tempPuzzle.board), tempPuzzle, parent, Move.RIGHT);
            states.add(tempState);
        }
        if (emptyCol < maxCol-1) {
            tempPuzzle = new Puzzle(deepCopy(state.puzzle.board));
            tempPuzzle.moveTile(Move.LEFT);
            // System.out.println("pot states RIGHT");
            // System.out.println(tempPuzzle.toString());
            // System.out.println();
            tempState = new Solvers.State(0, Heuristics.overallManhattanDistance(tempPuzzle.board), tempPuzzle, parent, Move.LEFT);
            states.add(tempState);
        }

        return states;
    }

    public static Stack<State> stateStack(State state) {
        Stack<State> ret = new Stack<State>();
        while (state != null) {
            ret.push(state);
            state = state.parent;
        }
        System.out.println("Stack of size " + ret.size() + " created.");
        return ret;
    }

    public static void printSteps(Stack<State> stack) {
        State state;
        while(!stack.isEmpty()) {
            state = stack.pop();
            System.out.println("Move:" + state.move);
            System.out.println(state.puzzle.toString());
            System.out.println();
        }
    }

}
