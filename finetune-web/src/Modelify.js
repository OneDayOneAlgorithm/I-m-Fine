import React, { useState, useEffect } from "react";
import axios from "axios";
import { useLocation, useNavigate } from "react-router-dom";

const Modelify = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const model = location.state.data;
  const sliderValues = location.state.sliderValues;
  const inputText = location.state.inputText;
  const text = "CONVERTING...";
  const delay = 500;

  const [data, setData] = useState([]);

  const [currentText, setCurrentText] = useState("");
  const [afterText, setAfterText] = useState("");
  const [currentIndex, setCurrentIndex] = useState(0);

  useEffect(() => {
    if (currentIndex <= text.length) {
      const timeout = setTimeout(() => {
        setCurrentText((prevText) => prevText + text[currentIndex]);
        setCurrentIndex((prevIndex) => prevIndex + 1);
      }, delay);

      return () => clearTimeout(timeout);
    } else {
      setCurrentText("");
      setCurrentIndex(0);
    }
  }, [currentIndex, delay, text]);

  // Call fetchData on component mount
  useEffect(() => {
    // Function to fetch data using Axios
    const fetchData = async () => {
      try {
        let response = null;
        console.log(sliderValues);
        console.log(inputText);
        console.log(model);
        if (model === "GPT") {
          response = await axios.post(
            "https://k9d109.p.ssafy.io/api/gpt-fine-tune",
            {
              input_text: inputText,
              mlp_weight: sliderValues[2],
              attn_weight: sliderValues[1],
              eps_weight: sliderValues[0],
            }
          );
          console.log(response);
          // 가공
          // 줄바꿈 치환
          const replacedNewlines = response.data.result.replace(/\\n/g, '\n');

          // 특수문자 앞의 역슬래시 제거
          const finalText = replacedNewlines.replace(/\\/g, '');

          setAfterText(finalText);
        } else if (model === "LLAMA") {
          response = await axios.post(
            "https://k9d109.p.ssafy.io/api/llama-fine-tune",
            {
              input_text: inputText,
              mlp_weight: sliderValues[1],
              attn_weight: sliderValues[0],
            }
          );
          console.log(response);
          // 가공
          // 줄바꿈 치환
          const replacedNewlines = response.data.result.replace(/\\n/g, '\n');

          // 특수문자 앞의 역슬래시 제거
          const finalText = replacedNewlines.replace(/\\/g, '');
          setAfterText(finalText);
        } else {
          // response = await axios.post("https://k9d109.p.ssafy.io/api/cat");
        }
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchData();
  }, [model, sliderValues, inputText]);

  return (
    <div
      style={{
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      <div
        style={{
          height: "80vh",
          width: "100vw",
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        {/* <h1>{model}</h1>
        <h2>{sliderValues}</h2> */}
        {afterText.length !== 0 ? (
          (console.log(data.result),
            // setAfterText(data.result),
            // data.map((post) => (
            //   <li>{post}</li>
            // )),
            navigate("/Change", {
              state: {
                params: sliderValues,
                model: model,
                prev: inputText,
                aft: afterText,
              },
            })) // navigate('/home', state: {data, data+'a'})
        ) : (
          <div style={{ pointerEvents: "none" }}>
            <img
              className="unselectable"
              src="loading_no_bg.gif"
              alt="로딩바"
              autoPlay
            />
          </div>
        )}
      </div>
      <div
        style={{
          height: "20vh",
          width: "100vw",
          display: "flex",
          justifyContent: "center",
          alignItems: "flex-start",
        }}
      >
        <h1
          style={{ color: "white", fontSize: "5em", fontFamily: "sebangFont" }}
        >
          {currentText}
        </h1>
      </div>
    </div>
  );
};

export default Modelify;
