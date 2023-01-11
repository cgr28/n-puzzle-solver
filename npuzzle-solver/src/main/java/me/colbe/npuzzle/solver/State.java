
package me.colbe.npuzzle.solver;

import me.colbe.npuzzle.Puzzle;
import me.colbe.npuzzle.Puzzle.Move;

public class State implements Comparable<State> {

    private int g;
    private int h;
    private int f;
    private Puzzle puzzle;
    private State parent;
    private Move move;

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

    public Integer getG() {
        return this.g;
    }

    public Integer getH() {
        return this.h;
    }

    public Integer getF() {
        return this.f;
    }

    public State getParent() {
        return this.parent;
    }

    public Move getMove() {
        return this.move;
    }

    public Puzzle getPuzzle() {
        return this.puzzle;
    }

    @Override
    public int compareTo(State state) {
        return this.getF().compareTo(state.getF());
    }

}
