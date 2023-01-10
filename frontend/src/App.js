import './App.css';
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Outlet
} from "react-router-dom";
import Home from './pages/Home';

function App() {
  return (
    <Router>
      <h1 className="text-center text-xl mt-2 header">n-puzzle solver</h1>
      <Routes>
        <Route path="/" element={<Home />} />
      </Routes>
    </Router>
  );
}

export default App;
