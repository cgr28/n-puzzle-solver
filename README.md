# N-Puzzle Solver
A web app that allows you to get the solution to an n-puzzle using various search algorithms and heuristics.

![Walk-through](https://raw.githubusercontent.com/cgr28/n-puzzle-java/main/imgs/walkthrough.gif)
<br />

## Install and Run App
### Build Project
```bash
cd npuzzle-solver
mvn clean compile assembly:single
```
### Docker
```bash
docker compose up
```
### View
***View at http://localhost:3000***

## Usage
1. Select either an 8, 15, or 24 puzzle.
2. Enter your initial puzzle numbered 1, 2, 3...n, use a 0 to represent the empty space.
3. Select one of three heuristics to use.
4. Select one of three solvers to use.
5. Enter the puzzle numbered 1, 2, 3...n, use a 0 to represent the empty space.