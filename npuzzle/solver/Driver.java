package npuzzle.solver;
import npuzzle.Puzzle;
import npuzzle.solver.Solvers.Heuristic;

public class Driver {
    public static void main(String[] args) {
        int[][] BOARD = {{1, 2, 3, 4},
                         {5, 6, 7, 8} ,
                         {9, 10, 11, 12},
                         {13, 14, 15, 0}
                        };

        // int[][] RALPH_GASSER_BOARD = {{15, 14, 8, 12},
        //                               {10, 11, 9, 13},
        //                               {2, 6, 5, 1},
        //                               {3, 7, 4, 0}
        //                             };

        int[][] GOAL  = {{1, 2, 3, 4},
                        {5, 6, 7, 8} ,
                        {9, 10, 11, 12},
                        {13, 14, 15, 0}};
        
        // int[][] RALPH_GASSER_GOAL  = {{0, 1, 2, 3},
        //                               {4, 5, 6, 7} ,
        //                               {8, 9, 10, 11},
        //                               {12, 13, 14, 15}
        //                             };
        

        Puzzle puzzle = new Puzzle(BOARD);
        Solvers solvers = new Solvers(GOAL, BOARD, Heuristic.MANHATTAN_DISTANCE);
        // Searches.pdb = Heuristics.patternDatabaseEightPuzzle();
        puzzle.shuffle(100);
        solvers.bestFirstSearch();
    }
}
