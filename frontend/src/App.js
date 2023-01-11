import "./App.css";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Outlet,
} from "react-router-dom";
import Home from "./pages/Home";

function App() {
  return (
    <Router>
      <h1 className="text-center text-xl mt-2 header">n-puzzle solver</h1>
      <Routes>
        <Route path="/" element={<Home />} />
      </Routes>
      <div className="grid justify-items-center header content-start footer">
        <div>
          Made by:{" "}
          <a
            href="https://www.colbe.me"
            target="__blank"
            id="name-link"
            rel="noreferrer"
            className="link text"
          >
            Colbe Roberson
          </a>
        </div>
        <div>
          <a
            href="https://github.com/cgr28/n-puzzle-java"
            target="_blank"
            id="github-link"
            rel="noreferrer"
            className="link text"
          >
            GitHub
          </a>
        </div>
      </div>
    </Router>
  );
}

export default App;
