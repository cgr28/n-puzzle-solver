package me.colbe.npuzzle;

import static spark.Spark.*;

import java.util.Arrays;
import java.util.Stack;

import me.colbe.npuzzle.solver.Solvers;
import me.colbe.npuzzle.solver.Solvers.Heuristic;
import me.colbe.npuzzle.solver.State;

import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Route;
public class App 
{

    public static int[][] stringToPuzzle(String puzzleString, int n) {
        int index = 0;
        int[][] out = new int[n][n];
        int num = 0;
        String temp = "";

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                temp = "";
                while (puzzleString.charAt(index) != '-') {
                    temp += puzzleString.charAt(index++);
                }
                out[i][j] = Integer.parseInt(temp+"");
                index++;
            }
        }
        return out;
    }

    public static String solutionToJson(Stack<State> stack) {
        String out = "[";

        while (stack.size() > 0) {
            State state = stack.pop();
            String section = "{\"puzzle\": " + arrToString(state.getPuzzle().getBoard()) + ", \"move\": " + "\"" + state.getMove() + "\"},";
            out += section;
        }
        out = out.substring(0, out.length()-1);
        out += "]";
        
        return out;
    }

    public static String arrToString(int[][] arr) {
        String out = "[";
        for (int i = 0; i < arr.length; i++) {
            out += Arrays.toString(arr[i]) + ",";
        }
        out = out.substring(0, out.length()-1);
        out += "]";
        return out;
    }

    public static void main(String[] args) {

        after((Filter) (request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET");
        });

        get("api/solve/:initial_puzzle/:goal_puzzle/:heuristic/:n/:solver", (req, res) -> {
            res.type("application/json");
            int[][] GOAL = stringToPuzzle(req.params(":goal_puzzle"), Integer.parseInt(req.params(":n")));
            int[][] INITIAL = stringToPuzzle(req.params(":initial_puzzle"), Integer.parseInt(req.params(":n")));
            String heuristicParam = req.params(":heuristic");
            String solverParam = req.params(":solver");
            Heuristic heuristic;

            if (heuristicParam == "pdb") {
                heuristic = Heuristic.PATTERN_DATABASE;
            } else if (heuristicParam == "manhattan") {
                heuristic = Heuristic.MANHATTAN_DISTANCE;
            } else {
                heuristic = Heuristic.EUCLIDEAN_DISTANCE;
            }

            Solvers solver = new Solvers(GOAL, INITIAL, heuristic);
            Stack<State> stack;
            
            if (solverParam == "bfs") {
                stack = solver.bestFirstSearch();
            } else if (solverParam == "astar") {
                stack = solver.aStar();
            } else {
                stack = solver.idaStar();
            }
            // solver.printSteps(stack);
            String solution = solutionToJson(stack);
            System.out.println(solution);
            return solution;

        });
        get("/hello", (request, response) -> {
            return "Hello";
        });
        
        // exception(RouteException.class, (e, request, response) -> {
        //     response.status(404);
        //     response.body("Resource not found");
        // });
    }
}
