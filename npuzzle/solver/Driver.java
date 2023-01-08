package npuzzle.solver;
// import npuzzle.Puzzle;
import npuzzle.solver.Solvers.Heuristic;

public class Driver {
    public static void main(String[] args) {
        // int[][] BOARD = {{1, 2, 3, 4},
        //                  {5, 6, 7, 8} ,
        //                  {9, 10, 11, 12},
        //                  {13, 14, 15, 0}
        //                 };

        int[][] RALPH_GASSER_BOARD = {{15, 14, 8, 12},
                                      {10, 11, 9, 13},
                                      {2, 6, 5, 1},
                                      {3, 7, 4, 0}
                                    };

        // int[][] GOAL  = {{1, 2, 3, 4},
        //                 {5, 6, 7, 8} ,
        //                 {9, 10, 11, 12},
        //                 {13, 14, 15, 0}};
        
        int[][] RALPH_GASSER_GOAL  = {{0, 1, 2, 3},
                                      {4, 5, 6, 7} ,
                                      {8, 9, 10, 11},
                                      {12, 13, 14, 15}
                                    };
        
        // int[][] COPY_BOARD;

        // Puzzle puzzle = new Puzzle(RALPH_GASSER_BOARD);
        Solvers solvers = new Solvers(RALPH_GASSER_GOAL, RALPH_GASSER_BOARD, Heuristic.MANHATTAN_DISTANCE);
        // Searches.pdb = Heuristics.patternDatabaseEightPuzzle();
        // puzzle.shuffle(75);
        // COPY_BOARD = deepCopy(puzzle.getBoard());
        solvers.idaStar();
        // solvers = new Solvers(GOAL, COPY_BOARD, Heuristic.MANHATTAN_DISTANCE);
        // solvers.aStar();
    }

    // public static int[][] deepCopy(int[][] arr) {
    //     int[][] copy = new int[arr.length][arr[0].length];
    //     for (int i = 0; i < arr.length; i++) {
    //         for (int j = 0; j < arr[0].length; j++) {
    //             copy[i][j] = arr[i][j];
    //         }
    //     }
    //     return copy;
    // }
}
