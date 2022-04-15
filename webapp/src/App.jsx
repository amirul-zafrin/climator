import { BrowserRouter, Routes, Route } from "react-router-dom";
import Dashboard from "./views/Dashboard";
import Landing from "./views/Landing";
import Activation from "./views/Activation";

const App = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" exact element={<Landing />} />
        {/* TODO: Include username or id in path  */}
        <Route path="/dashboard" exact element={<Dashboard />} />
        <Route path="/activation" element={<Activation />} />
      </Routes>
    </BrowserRouter>
  );
};

export default App;
