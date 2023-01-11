import React from "react";
import "./Puzzle.scss";

export default function Puzzle({ puzzle, move }) {
  const arrows = new Map([
    ["LEFT", "⬅"],
    ["RIGHT", "➡"],
    ["UP", "⬆"],
    ["DOWN", "⬇"],
    ["null", ""],
  ]);
  return (
    <table className="table shadow-md">
      <tbody>
        {puzzle.map((row, i) => (
          <tr className="row" key={i}>
            {row.map((col, j) => (
              <td key={j}>
                <div className={"col"} id={`${col == 0 && "zero"}`}>
                  {col != 0 ? col : arrows.get(move)}
                </div>
              </td>
            ))}
          </tr>
        ))}
      </tbody>
    </table>
  );
}
