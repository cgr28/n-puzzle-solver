import React, { useState } from "react";
import Puzzle from '../components/Puzzle';
import InputPuzzle from "../components/InputPuzzle"

export default function Home() {
    const [n, setN] = useState(3);
    const [heuristic, setHeuristic] = useState("manhattan")
    const [solver, setSolver] = useState("idastar")
    const [steps, setSteps] = useState()
    const [step, setStep] = useState(0)
    const [loading, setLoading] = useState(0);
    const [error, setError] = useState();
    
    const handleSubmit = (event) => {
        event.preventDefault();
        let goal = getGoalPuzzle();
        let initial = getInitialPuzzle();
        setLoading(1)
        fetch(`http://localhost:4567/api/solve/${initial}/${goal}/${heuristic}/${n}/${solver}`)
            .then((res) => res.json())
            .then((data) => {
                setError();
                console.log(data)
                setSteps(data);
                setLoading(0)
            })
            .catch((error) => {
                console.log(error);
                setError("Error: check that your puzzle is solvable.")
                setLoading(0)
                return;
            });
    }

    const getGoalPuzzle = () => {
        let out = ""
        let cells = document.querySelectorAll(".goal .input-col");
        cells.forEach((cell) => {
            out += (cell.value + "-");
        })

        return out;
    }
    const getInitialPuzzle = () => {
        let out = ""
        let cells = document.querySelectorAll(".initial .input-col");
        cells.forEach((cell) => {
            out += (cell.value + "-");
        })
        return out;
    }

    const onNChange = (event) => {
        console.log(event.target.value)
        setN(event.target.value)
    }

    const onHeuristicChange = (event) => {
        setHeuristic(event.target.value)
    }
    const onSolverChange = (event) => {
        setSolver(event.target.value)
    }

    const stepBackward = () => {
        if (step > 0) {
            setStep(step - 1);
        }
    }

    const stepForward = () => {
        if (step < steps.length-1) {
            setStep(step + 1);
        }
    }



    return (
        <div className="home container mx-auto mt-4">
            {
                error &&
                <div className="grid justify-items-center text error">{error}</div>
            }
            <div className="flex flex-row">
                <div className="basis-1/2 grid justify-items-center">
                    <div>
                        <form onSubmit={handleSubmit}>
                            <div className="header">Initial Puzzle</div>
                            <InputPuzzle n={n} className="initial" />
                            <div className="mt-2">
                                <label className="header" htmlFor="n-value">Puzzle Size:</label>
                                <br />
                                <select disabled={loading} className="dropdown option" onChange={onNChange} name="n-value" id="n-value">
                                    <option className="option" value="3">8 Puzzle</option>
                                    <option className="option" value="4">15 Puzzle</option>
                                    <option className="option" value="5">24 Puzzle</option>
                                </select>
                            </div>

                            <div className="mt-2">
                                <label className="header" htmlFor="Heuristic">Heuristic:</label>
                                <br />
                                <select disabled={loading} className="dropdown option" onChange={onHeuristicChange} name="heuristic" id="heuristic">
                                    <option className="option" value="manhattan">Manhattan Distance</option>
                                    <option className="option" value="euclidean">Euclidean Distance</option>
                                    {n == "3" && <option className="option" value="pdb">Pattern Database</option>}
                                </select>
                            </div>
                            <div className="mt-2">
                                <label className="header" htmlFor="Heuristic">Solver:</label>
                                <br />
                                <select disabled={loading} className="dropdown option" onChange={onSolverChange} name="heuristic" id="heuristic">
                                    <option className="option" value="idastar">IDA*</option>
                                    <option className="option" value="astar">A*</option>
                                    <option className="option" value="bfs">Best First Search</option>
                                </select>
                            </div>
                            <div className="header mt-2" >Goal Puzzle</div>
                            <InputPuzzle n={n} className="goal" />
                            <input disabled={loading} value={"Solve"} type="submit" className="btn mt-4 px-1" />
                        </form>
                    </div>
                </div>
                <div className="basis-1/2 grid justify-items-center content-start">
                    <div className="header">Instructions</div>
                    <ol type="1" className="list-decimal">
                        <li className="text mt-2">Enter an initial puzzle numbered 1, 2, 3...n, use a 0 to represent the empty space.</li>
                        <li className="text mt-2">Select either an 8, 15, or 24 puzzle.</li>
                        <li className="text mt-2">Select a heuristic to use.</li>
                        <ol type="a" className="ml-6 list-[lower-alpha]">
                            <li className="text mt-2 ml-2"><a href="https://en.wikipedia.org/wiki/Taxicab_geometry" className="link">Manhattan Distance</a>: Uses the distance from the cell's current position to the goal position measured along axes at right angles.</li>
                            <li className="text mt-2 ml-2"><a href="https://en.wikipedia.org/wiki/Euclidean_distance" className="link">Euclidean Distance</a>: Uses the length of a straight line from the cell's current position to the goal position.</li>
                            <li className="text mt-2 ml-2"><a href="https://www.cs.cmu.edu/afs/cs/project/jair/pub/volume22/felner04b.pdf" className="link">Pattern Database (8-Puzzle only)</a>: Best heuristic, uses the most memory.</li>
                        </ol>
                        <li className="text mt-2">Select a solver to use.</li>
                        <ol type="a" className="ml-6 list-[lower-alpha]">
                            <li className="text mt-2 ml-2"><a href="https://en.wikipedia.org/wiki/Iterative_deepening_A*" className="link">IDA*</a>: Finds the optimal solution. Utilizes a heuristic like A*, but has lower memory usage because of it's usage of depth-first search</li>
                            <li className="text mt-2 ml-2"><a href="https://en.wikipedia.org/wiki/A*_search_algorithm" className="link">A*</a>: Finds the optimal solution. Uses a node's cost from the start node and it's heuristic cost to decide where to search next.</li>
                            <li className="text mt-2 ml-2"><a href="https://en.wikipedia.org/wiki/Best-first_search" className="link">Best-First Search</a>: Solution not guaranteed to be optimal. Decides where to search next by choosing the next node with the lowest heuristic cost.</li>
                        </ol>
                        <li className="text mt-2">Enter a goal puzzle numbered 1, 2, 3...n, use a 0 to represent the empty space.</li>
                    </ol>
                </div>
            </div>
            <div className="grid justify-items-center mt-16">
            {loading==1 && <div className="header">Loading...</div>}
            { steps &&
                <div className="">
                    <div className="header">Solution</div>
                    <div className="header">Move: {steps[step].move}</div>
                    <Puzzle puzzle={steps[step].puzzle} move={steps[step].move} />
                    <div className="header">Step {step+1} of {steps.length}</div>
                    <div className="mt-4">
                    <button className={"btn mr-2 px-1"} onClick={stepBackward}>Prev</button>
                    <button className={"btn px-1"} onClick={stepForward}>Next</button>
                    </div>
                </div>
            }

            </div>
        </div>
    )
}