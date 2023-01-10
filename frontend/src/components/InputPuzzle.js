import React from "react";
import "./Puzzle.scss";
import "./InputPuzzle.scss";

export default function Puzzle({ n, className }) {
    n = parseInt(n)
    const zeros = (m, n) => [...Array(m)].map(e => Array(n).fill(0));
    const puzzle = zeros(n, n)
    return (
        <table className={`table input-table ${className}`}>
            <tbody>
                {puzzle.map(
                    (row, i) => 
                    (<tr className="row" key={i}>
                            {row.map(
                                (col, j) => 
                                (
                                     <td key={j}>
                                        <input required className="input col input-col" autoComplete={"off"} type={"number"} />
                                        </td>
                                )
                            )}
                        </tr>)
                )}
            </tbody>
        </table>
    )
}