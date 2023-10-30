import React, { useState } from "react";
import "./App.css";
// Bootstrap CSS
import "bootstrap/dist/css/bootstrap.min.css";
// Bootstrap Bundle JS
import "bootstrap/dist/js/bootstrap.bundle.min";
import AnimatedDiv from "./AnimatedDiv"; // AnimatedDiv 컴포넌트 import
import { BrowserRouter as Router, Route, Routes, Link } from "react-router-dom";
import Parameter from "./Parameter";
import Modelify from "./Modelify";
import Change from "./Change";
import Send from "./Send";
import Finish from "./Finish";

function HeaderText({ title }) {
  return (
    <div>
      <h2 class="header-text">{title}</h2>
    </div>
  );
}

function ModelCard({ img, modelName }) {
  const [isHovered, setIsHovered] = useState(false);

  return (
    <div
      class="card border-primary mb-3"
      style={{
        maxWidth: "14rem",
        height: "24rem",
        boxShadow: isHovered ? "6px 10px 8px 2px rgba(77, 77, 255, 1)" : "none",
        margin: "20px",
        display: "flex", // Flexbox 레이아웃 설정
        flexDirection: "column", // 세로로 배치
        justifyContent: "center", // 수직 중앙 정렬
        alignItems: "center",
      }}
      onMouseEnter={() => setIsHovered(true)}
      onMouseLeave={() => setIsHovered(false)}
    >
      <div
        style={{
          flex: "2",
          // background: "red",
          display: "flex", // Flexbox 레이아웃 설정
          flexDirection: "column", // 세로로 배치
          justifyContent: "center", // 수직 중앙 정렬
        }}
      >
        <img
          src={img}
          className="card-img-top"
          alt={modelName}
          style={{
            maxWidth: "100%",
            maxHeight: "100%",
            height: modelName === "LLAMA" ? "80%" : "100%",
            // height: modelName === "LLAMA" ? "10rem" : "14rem",
            padding: modelName === "LLAMA" ? "20px" : "30px",
          }}
        />
      </div>

      <div
        class="card-body text-primary"
        style={{
          textAlign: "center",
          display: "flex",
          flexDirection: "column", // 세로로 배치
          justifyContent: "center", // 수직 중앙 정렬
          flex: "1",
          // background: "black",
        }}
      >
        <h5 class="card-title">{modelName}</h5>
        <p class="card-text"></p>
      </div>
    </div>
  );
}

function SelectModelPage() {
  const cardContainerStyle = {
    display: "flex",
    justifyContent: "center", // 수평 간격을 동일하게 배치
    maxWidth: "100%", // 윈도우 최대 너비로 설정
    margin: "0 auto", // 가운데 정렬을 위한 마진 설정
  };

  const modelCardStyle = {
    boxSizing: "border-box", // 요소의 크기에 패딩과 테두리를 포함시킵니다.
    margin: "8px", // 간격을 8px로 설정
  };

  return (
    <div style={cardContainerStyle}>
      <AnimatedDiv delay={0}>
        <div style={modelCardStyle}>
          <Link
            style={{ textDecoration: "none" }}
            to={`/Parameter`} state={{ data: "LLAMA"}}
          >
            <ModelCard img="./llama_img.jpg" modelName="LLAMA" />
          </Link>
        </div>
      </AnimatedDiv>
      <AnimatedDiv delay={100}>
        <div style={modelCardStyle}>
          <Link
            style={{ textDecoration: "none" }}
            to={`/Parameter`} state={{ data: "GPT"}}
          >
            <ModelCard img="./gpt_img.png" modelName="GPT" />
          </Link>
        </div>
      </AnimatedDiv>
      <AnimatedDiv delay={200}>
        <div style={modelCardStyle}>
          <Link
            style={{ textDecoration: "none" }}
            to={`/Parameter`} state={{ data: "Stable Diffusion"}}
          >
            <ModelCard
              img="./stablediff_img.png"
              modelName="Stable Diffusion"
            />
          </Link>
        </div>
      </AnimatedDiv>
    </div>
  );
}

function App() {
  const [data] = useState(null);
  const title = "모델을 선택하세요."
  return (
    <div className="div-container" style={{ overflow: "hidden" }}>
      <Router>
        <Routes>
          <Route
            path="/"
            element={
              <>
                <HeaderText title={title} />
                <SelectModelPage />
              </>
            }
          />
          <Route
            path="/parameter"
            element={<Parameter data={data}/>}
          />
          <Route
            path="/modelify"
            element={<Modelify data={data} />}
          />
          <Route
            path="/change"
            element={<Change data={data} />}
          />
          <Route
            path="/send"
            element={<Send data={data} />}
          />
          <Route path="/finish" element={<Finish data={data} />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
