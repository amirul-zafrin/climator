import { BrowserRouter, Routes, Route } from "react-router-dom";
import Dashboard from "./views/Dashboard";
import Landing from "./views/Landing";
import Activation from "./views/Activation";
import ForgotPassword from "./views/ForgotPassword";
import GraphView from "./views/GraphView";

const App = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" exact element={<Landing />} />
        <Route path="/dashboard/:username" element={<Dashboard />} />
        <Route path="/activation" element={<Activation />} />
        <Route path="/resetPassword" element={<ForgotPassword />} />
        <Route path="/graph" element={<GraphView />} />
      </Routes>
    </BrowserRouter>
  );
};

export default App;
