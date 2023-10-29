import React from "react";
import { Link, useLocation } from "react-router-dom";
import CustomSlider from "./CustomSlider";

function MySlider() {
  const sliderStyle = {
    marginTop: "3em", // 여기에 마진 값을 설정하세요.
    display: 'flex',
    alignItems: 'center',
  };

  const paramStyle = {
    fontFamily: "paramFont"
  }

  return (
    <div style={sliderStyle}>
      <div style={paramStyle}>pam</div>
      <div>
        <CustomSlider
          valueLabelDisplay="auto"
          aria-label="pretto slider"
          defaultValue={1.0}
          min={0.1}
          max={2.0}
          step={0.05}
        />
      </div>
    </div>
  );
}

const Parameter = () => {
  const location = useLocation();
  const data = location.state.data;

  const containerStyle = {
    display: "flex",
    flex: 3, // 3 부분을 차지하도록 설정
    height: "100vh", // 화면 전체 높이로 설정
    alignItems: "center", // 수평 중앙 정렬
    flexDirection: "column", // 세로 방향으로 나눔
  };

  const topPaneStyle = {
    flex: 1, // 1:2 비율로 나누기 위해 flex 속성 사용
    display: "flex",
    alignItems: "center",
    height: "100%", // 부모 컨테이너의 높이를 100%로 설정합니다.
    // overflow: "auto", // 내용이 넘칠 경우 스크롤 가능하도록 설정
  };

  const bottomPaneStyle = {
    flex: 2, // 1:2 비율로 나누기 위해 flex 속성 사용
    // overflow: "auto", // 내용이 넘칠 경우 스크롤 가능하도록 설정
    flexDirection: "column",
    alignItems: "center", // 수평 중앙 정렬
    marginTop: "20px", // MySlider 사이의 간격 조정
  };

  const headingStyle = {
    color: "white", // 텍스트 색상을 파란색으로 변경
    fontFamily: "hyundaiFont", // 폰트를 Arial로 설정
    fontSize: "4em", // 폰트 크기를 24px로 설정
  };

  const arrowStyle = {
    flex: 1, // 1 부분을 차지하도록 설정
    textAlign: "center",
  };

  return (
    <div style={{ display: "flex", width: "100%", alignItems: "center" }}>
      <div style={arrowStyle}>
        <img
          src="/arrow_img.png"
          alt="좌측 화살표"
          style={{ transform: "scaleX(-1)" }}
        />
      </div>
      <div style={containerStyle}>
        <div style={topPaneStyle}>
          <h1 style={headingStyle}>{data} 파라미터 조정</h1>
          <Link to="/Modelify">{data}</Link>
        </div>
        <div style={bottomPaneStyle}>
          <MySlider />
          <MySlider />
          <MySlider />
        </div>
      </div>
      <div style={arrowStyle}>
        <img src="/arrow_img.png" alt="우측 화살표" />
      </div>
    </div>
  );
};

export default Parameter;
