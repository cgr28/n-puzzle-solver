// TODO: implement A* and IDA*
// make utils and heuristics an extension of solvers so it can use GOAL and GOAL_MAP
// put Searches, Heuristics, and Utils into a Solvers folder
package npuzzle.solver;
import java.io.*;
import java.util.*;

import npuzzle.Puzzle;
import npuzzle.Puzzle.Move;

public class Solvers {
    
    private int[][] goal;
    private Puzzle puzzle;
    private Heuristic heuristic;
    private HashMap<Integer, int[]> goalMap;
    private HashMap<String,Integer> pdb;
    Heuristics searchHeuristic = new Heuristics();
    
    public Solvers(int[][] goal, int[][] initialPuzzle, Heuristic heuristic) {
        this.goal = goal;
        this.puzzle = new Puzzle(initialPuzzle);
        this.heuristic = heuristic;
        this.goalMap = null;
        this.pdb = null;
    }

    public enum Heuristic {
        MANHATTAN_DISTANCE,
        EUCLIDEAN_DISTANCE,
        PATTERN_DATABASE
    }
    public class Heuristics {

    
        public class pdbState {
            int g;
            Puzzle puzzle;
    
            public pdbState(int g, Puzzle puzzle) {
                this.g = g;
                this.puzzle = puzzle;
            }
        }
    
        public int calculateHeuristic(Puzzle puzzle) {
            if (heuristic == Heuristic.MANHATTAN_DISTANCE) {
                if (goalMap == null) {
                    goalMap = generateGoalMap();
                }
                return mdHeuristic(puzzle);
            }
            if (heuristic == Heuristic.EUCLIDEAN_DISTANCE) {
                if (goalMap == null) {
                    goalMap = generateGoalMap();
                }
                return edHeuristic(puzzle);
            }
            if (heuristic == Heuristic.PATTERN_DATABASE) {
                if (pdb == null) {
                    pdb = patternDatabaseEightPuzzle();
                }
                return pdbHeuristic(puzzle);
            }
            return 0;
        }
        
        public int manhattanDistance(int row1, int col1, int row2, int col2) {
            return Math.abs(row1 - row2) + Math.abs(col1 - col2);
        }
    
        // CORRECT BOARD -> 1   2   3
        //                  4   5   6
        //                  8   9   0
        //                  
        public int mdHeuristic(Puzzle puzzle) {
            int total = 0;
            int goalRow;
            int goalCol;
    
            for (int i = 0; i < puzzle.getHeight(); i++) {
                for (int j = 0; j < puzzle.getHeight(); j++) {
                    int key = puzzle.getBoard()[i][j];
                    int[] goal = goalMap.get(key);
                    goalRow = goal[0];
                    goalCol = goal[1];
                    total += manhattanDistance(i, j, goalRow, goalCol);
                }
            }
    
            return total;
    
        }
    
        public int edHeuristic(Puzzle puzzle) {
            int total = 0;
            int goalRow;
            int goalCol;
    
            for (int i = 0; i < puzzle.getHeight(); i++) {
                for (int j = 0; j < puzzle.getHeight(); j++) {
                    int key = puzzle.getBoard()[i][j];
                    int[] goal = goalMap.get(key);
                    goalRow = goal[0];
                    goalCol = goal[1];
                    total += euclideanDistance(i, j, goalRow, goalCol);
                }
            }
    
            return total;
    
        }
    
        public int euclideanDistance(int row1, int col1, int row2, int col2) {
            return (int) Math.sqrt(Math.pow((row1-row2), 2) + Math.pow((col1-col2), 2));
        }
       
        public void createPatternDatabaseEightPuzzle() {
            System.out.println("Beginning creation of 8 puzzle pdb...");
            HashMap<String, Integer> pdb = new HashMap<String, Integer>();
            int[][] BOARD = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
            pdbState state = new pdbState(0, new Puzzle(BOARD));
            Queue<pdbState> queue = new LinkedList<>();
            queue.add(state);
            while (queue.size() > 0) {
                state = queue.poll();
                String hash = state.puzzle.flatten();
                if (pdb.containsKey(hash)) {
                    continue;
                }
                pdb.put(hash, state.g);
                for (Move move: new Move[] {Move.DOWN, Move.LEFT, Move.RIGHT, Move.UP}) {
                    pdbState temp = new pdbState(state.g+1, new Puzzle(deepCopy(state.puzzle.getBoard())));
                    temp.puzzle.moveTile(move);
                    queue.add(temp);
                }
            }
    
            try {
                System.out.println("Writing to file...");
                File fileOne=new File("8-puzzle-pdb");
                FileOutputStream fos=new FileOutputStream(fileOne);
                ObjectOutputStream oos=new ObjectOutputStream(fos);
        
                oos.writeObject(pdb);
                oos.flush();
                oos.close();
                fos.close();
            } catch(Exception e) {System.out.println(e);}
    
        }
    
        public HashMap<String,Integer> patternDatabaseEightPuzzle() {
            HashMap<String,Integer> pdb = new HashMap<>();
            try {
                File toRead = new File("8-puzzle-pdb");
                FileInputStream fis = new FileInputStream(toRead);
                ObjectInputStream ois = new ObjectInputStream(fis);
        
                pdb = (HashMap<String,Integer>)ois.readObject();
                
                ois.close();
                fis.close();
            } catch(Exception e) {System.out.println(e);}
    
            return pdb;
        }
    
        // return pdb heuristic value
        public int pdbHeuristic(Puzzle puzzle) {
            return pdb.get(puzzle.flatten());
        }
    }


    public void setPuzzle(Puzzle initialPuzzle) {
        this.puzzle = initialPuzzle;
    }

    public void setGoal(int[][] goalState) {
        this.goal = goalState;
    }

    public void bestFirstSearch() {
        PriorityQueue<State> open = new PriorityQueue<State>();
        Set<String> closed = new HashSet<String>();
        String hash = puzzle.flatten();

        if (heuristic == Heuristic.PATTERN_DATABASE) {
            pdb = searchHeuristic.patternDatabaseEightPuzzle();
        } else {
            goalMap = generateGoalMap();
        }

        State start = new State(0, searchHeuristic.calculateHeuristic(puzzle), puzzle, null, null);
        open.add(start);
        
        while (open.size() > 0) {
            State state = open.poll();
            
            if (state.getH() == 0) {
                printSteps(stateStack(state));
                return;
            }

            closed.add(state.getPuzzle().flatten());

            for (State newState: potentialStates(state)) {
                hash = newState.getPuzzle().flatten();

                if (closed.contains(hash)) {
                    continue;
                }
                open.add(newState);
            }
        }

        return;
    }

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

    public HashMap<Integer, int[]> generateGoalMap() {

        HashMap<Integer, int[]> GOAL_MAP = new HashMap<Integer, int[]>();
        for (int i = 0; i < goal.length; i++) {
            for (int j = 0; j < goal.length; j++) {
                GOAL_MAP.put(goal[i][j], new int[] {i, j});
            }
        }
        return GOAL_MAP;
    }

    public ArrayList<State>  potentialStates(State state) {
        ArrayList<State> states = new ArrayList<State>();
        Puzzle tempPuzzle;
        State tempState;
        State parent = state;

        for (Move move: new Move[] {Move.DOWN, Move.LEFT, Move.RIGHT, Move.UP}) {
            tempPuzzle = new Puzzle(deepCopy(state.getPuzzle().getBoard()));
            if (tempPuzzle.moveTile(move)) {
                tempState = new State(parent.getG()+1, searchHeuristic.calculateHeuristic(tempPuzzle), tempPuzzle, parent, move);
                states.add(tempState);
            }
        }
        return states;
    }

    

    public Stack<State> stateStack(State state) {
        Stack<State> ret = new Stack<State>();
        while (state != null) {
            ret.push(state);
            state = state.getParent();
        }
        System.out.println("Stack of size " + ret.size() + " created.");
        return ret;
    }

    public void printSteps(Stack<State> stack) {
        State state;
        int count = 1;
        int n = stack.size();
        while(!stack.isEmpty()) {
            state = stack.pop();
            System.out.println("Step " + (count++) + " of " + n);
            System.out.println("Move:" + state.getMove());
            System.out.println(state.getPuzzle().toString());
            System.out.println();
        }
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

    public void aStar() {
        PriorityQueue<State> open = new PriorityQueue<State>();
        Set<String> closed = new HashSet<String>();
        Heuristics searchHeuristic = new Heuristics();
        String hash = puzzle.flatten();

        if (heuristic == Heuristic.PATTERN_DATABASE) {
            pdb = searchHeuristic.patternDatabaseEightPuzzle();
        } else {
            goalMap = generateGoalMap();
        }

        State start = new State(0, searchHeuristic.calculateHeuristic(puzzle), puzzle, null, null);
        open.add(start);

        while (open.size() > 0) {
            State state = open.poll();

            if (state.getH() == 0) {
                printSteps(stateStack(state));
                return;
            }

            closed.add(state.getPuzzle().flatten());

            for (State newState: potentialStates(state)) {
                hash = newState.getPuzzle().flatten();

                if (closed.contains(hash)) {
                    continue;
                }
                open.add(newState);
            }
        }

        return;
    }

    public void idaStar() {
        HashMap<String, Integer> pdb = null;
        Heuristics searchHeuristic = new Heuristics();
        HashMap<Integer, int[]> goalMap = null;

        if (heuristic == Heuristic.PATTERN_DATABASE) {
            pdb = searchHeuristic.patternDatabaseEightPuzzle();
        } else {
            goalMap = generateGoalMap();
        }

        State start = new State(0, searchHeuristic.calculateHeuristic(puzzle), puzzle, null, null);
        int thresh = start.getH();   
        
        while (true) {
            idaStarReturn ret = idaStarSearch(start, thresh, heuristic, goalMap, pdb);
            if (ret.getFound()) {
                printSteps(stateStack(ret.getState()));
                return;
            }
            if (ret.getState().getF() == Integer.MAX_VALUE) {
                return;
            }
            thresh = ret.getState().getF();
        }
    }

    public idaStarReturn idaStarSearch(State state, int thresh, Heuristic heuristic, HashMap<Integer, int[]> goalMap, HashMap<String,Integer> pdb) {
        if (state.getF() > thresh) {
            return new idaStarReturn(false, state);
        }
        if (state.getH() == 0) {
            return new idaStarReturn(true, state);
        }
        State min = new State(0, Integer.MAX_VALUE, puzzle, state, null);
        for (State newState: potentialStates(state)) {
            idaStarReturn ret = idaStarSearch(newState, thresh, heuristic, goalMap, pdb);
            if (ret.getFound()) {
                return ret;
            }
            if (ret.getState().getF() < min.getF()) {
                min = ret.getState();
            }
        }
        return new idaStarReturn(false, min);
    }

    public static class idaStarReturn {
        private boolean found;
        private State state;

        public idaStarReturn(boolean found, State state) {
            this.found = found;
            this.state = state;
        }

        public boolean getFound() {
            return this.found;
        }

        public State getState() {
            return this.state;
        }

    }
}