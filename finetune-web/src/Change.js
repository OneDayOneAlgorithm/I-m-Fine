import React, { useState, useEffect } from "react";
import { Link, useLocation } from "react-router-dom";

function CenteredTextInBox({ org, chg }) {
  const boxStyle = {
    display: 'flex',
    flex: 3,
    flexDirection: "column", // 세로 방향 배치
    justifyContent: 'center',
    paddingLeft: '1em',
    paddingRight: '1em',
    alignItems: 'center',
    width: '100%',
    height: '100%',
    border: '2px dotted #fff',
    borderRadius: '10px',
  };

  const divStyle = {
    color: 'white', 
    flex: 1, display: 
    'flex', 
    flexDirection: 'column', 
    justifyContent: 'center',
    fontFamily: 'bmFont',
    fontSize: '1.2em',
  }

  return (
    <div style={boxStyle}>
      <div style={divStyle}>{org}</div>
      <img
            src="/white_arrow.png"
            alt="아래 화살표"
            style={{ width: '10%', height: '20%', transform: "rotate(90deg)" }}
      />
      <div style={divStyle}>{chg}</div>
    </div>
  );
};

const Parameter = () => {
  const location = useLocation();
  const org = location.state.prev;
  const chg = location.state.aft;
  const model = location.state.model;
  const params = location.state.params;
  
  const [isVisible, setIsVisible] = useState(false);

  useEffect(() => {
    console.log(org);
    setIsVisible(true);
  }, []);

  const containerStyle = {
    display: "flex",
    flex: 4, // 3 부분을 차지하도록 설정
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
    flex: 3,
    width: '90%',
    flexDirection: "column",
    overflow: "auto",
    margin: '2em',
    opacity: isVisible ? 1 : 0, transition: 'opacity 1s'
  };

  const headingStyle = {
    color: "white", // 텍스트 색상을 파란색으로 변경
    fontFamily: "hyundaiFont", // 폰트를 Arial로 설정
    fontSize: "3.5em", // 폰트 크기를 24px로 설정
    marginTop: '1em'
  };

  const arrowStyle = {
    flex: 1, // 1 부분을 차지하도록 설정
    textAlign: "center",
  };

  return (
    <div style={{ display: "flex", width: "100%", alignItems: "center" }}>
      <div style={arrowStyle}>
        <Link to="/Parameter" state={{data: model}}>
          <img
            src="/arrow_img.png"
            alt="좌측 화살표"
            style={{ transform: "scaleX(-1)" }}
          />
        </Link>
      </div>
      <div style={containerStyle}>
        <div style={topPaneStyle}>
          <h1 style={headingStyle}> 출력이 아래와 같이 나옵니다. </h1>
        </div>
        <div class="scroll-hide" style={bottomPaneStyle}>
          <CenteredTextInBox org={org} chg={chg} />
        </div>
      </div>
      <div style={arrowStyle}>
        <Link to="/finish">
          <img src="/arrow_img.png" alt="우측 화살표" />
        </Link>
      </div>
    </div>
  );
};

export default Parameter;
