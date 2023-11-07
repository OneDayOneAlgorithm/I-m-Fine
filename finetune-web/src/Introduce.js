import React, { useEffect } from "react";
import { motion, useAnimation } from "framer-motion";
import { useInView } from "react-intersection-observer";
import { Link } from "react-router-dom";

const boxVariant = {
  visible: { opacity: 1, scale: 1, transition: { duration: 0.5 } },
  hidden: { opacity: 0, scale: 0 },
};

function Arrow() {
  return (
    <motion.img
      src="/white_arrow.png"
      alt="아래 화살표"
      style={{
        width: "6em",
        height: "10em",
        transform: "rotate(90deg)",
        position: "absolute",
        bottom: "0",
        left: "46%", // 수평 가운데 정렬
      }}
      initial={{ opacity: 1 }}
      animate={{
        opacity: [1, 0, 1],
        transition: { duration: 1.5, repeat: Infinity },
      }}
    />
  );
}

function Prev() {
  return (
    <div
      style={{
        position: "absolute",
        bottom: "50%",
        left: "5%",
      }}
    >
      <Link to="/">
        <img
          src="/arrow_img.png"
          alt="좌측 화살표"
          style={{ transform: "scaleX(-1)", height: "7em" }}
        />
      </Link>
    </div>
  );
}

function Next() {
  return (
    <div
      style={{
        position: "absolute",
        bottom: "50%",
        right: "5%",
      }}
    >
      <Link to="/Modelify">
        <img src="/arrow_img.png" alt="우측 화살표" style={{ height: "7em" }} />
      </Link>
    </div>
  );
}

const Box = ({ main, sub, align }) => {
  const control = useAnimation();
  const [ref, inView] = useInView();

  useEffect(() => {
    if (inView) {
      control.start("visible");
    } else {
      control.start("hidden");
    }
  }, [control, inView]);

  return (
    <motion.div
      className="box"
      ref={ref}
      variants={boxVariant}
      initial="hidden"
      animate={control}
      style={{
        height: "100%",
        width: "100%",
        display: "flex",
        flexDirection: "column",
        justifyContent: align === "0" ? "center" : "flex-end",
        alignItems: "center",
      }}
    >
      <div style={{ fontSize: "4em" }}>{main}</div>
      <div style={{ fontSize: "4em" }}>{sub}</div>
    </motion.div>
  );
};

function Expand({ main, sub, align, src, link }) {
  const content = link ? (
    <Link to={link}>
      <img
        src={src}
        alt="이미지"
        style={{ marginBottom: "15em"}}
      />
    </Link>
  ) : (
    <img
      src={src}
      alt="이미지"
      style={{ marginBottom: "15em", marginTop: "4em", height: "30%" }}
    />
  );

  return (
    <div
      style={{
        height: "105vh",
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      <Box main={main} sub={sub} align={align} />
      {content}
    </div>
  );
}

const arrowStyle = {
  flex: 1, // 1 부분을 차지하도록 설정
  textAlign: "center",
};

const containerStyle = {
  overflowY: "scroll",
  msOverflowStyle: "none", // IE에서 스크롤바를 숨기기 위한 속성
};

function Introduce() {
  const now = new Date();
  const expireDate = new Date(now.getTime() + 24 * 60 * 60 * 1000);
  // 쿠키 확인
  const cookieExists = document.cookie.includes("fine-tune");
  const downloadCookie = () => {
    // 쿠키 다운로드
    document.cookie = `myCookieName='fine-tune'; expires=${expireDate.toUTCString()}; path=/`;
  };

  // if (cookieExists) {
  //   // 쿠키가 있으면 다음 페이지로 이동
  //   window.location.href = "/choose";
  // } else {
  //   downloadCookie();
  // }

  return (
    <div className="Introduce" style={containerStyle}>
      <Arrow />
      <Expand main="환영합니다." align="1" src="/welcome_robot.gif" />
      <Expand main="'파인튜닝'을 아시나요?" align="1" src="/question.gif" />
      <Expand
        main="모델 수치를 변화시켜서"
        sub="결과를 변경시키는 기술입니다!"
        align="1"
        src="/evolution.gif"
      />
      <Expand
        main="모델을 고르고 숫자만 선택하세요."
        align="1"
        src="/select.gif"
      />
      <Expand main="조정은 저희가 할게요 😊" align="1" src="/teraform.gif" />
      <Expand main="지금 시작하기" align="1" src="/start.png" link='./choose' />
    </div>
  );
}

export default Introduce;
