import React, { useState, useEffect } from "react";
import "./App.css";
// Bootstrap CSS
import "bootstrap/dist/css/bootstrap.min.css";
// Bootstrap Bundle JS
import "bootstrap/dist/js/bootstrap.bundle.min";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Introduce from "./Introduce";
import Choose from "./Choose";
import Parameter from "./Parameter";
import Modelify from "./Modelify";
import Change from "./Change";
import Send from "./Send";
import Finish from "./Finish";

function HeaderText({ title }) {
  return (
    <div>
      <h2 className="header-text">{title}</h2>
    </div>
  );
}

function App() {
  const [data] = useState(null);
  const title = "모델을 선택하세요.";

  return (
    <div className="div-container" style={{ overflow: "hidden" }}>
      <Router>
        <Routes>
          <Route path="/" element={<Introduce />} />
          <Route
            path="/choose"
            element={
              <>
                <HeaderText title={title} />
                <Choose />
              </>
            }
          />
          <Route path="/parameter" element={<Parameter data={data} />} />
          <Route path="/modelify" element={<Modelify data={data} />} />
          <Route path="/change" element={<Change data={data} />} />
          <Route path="/send" element={<Send data={data} />} />
          <Route path="/finish" element={<Finish data={data} />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
