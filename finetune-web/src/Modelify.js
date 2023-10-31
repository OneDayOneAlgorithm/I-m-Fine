import React, { useState, useEffect } from 'react';
import axios from "axios";
import {useLocation, useNavigate } from "react-router-dom";

const Modelify = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const model = location.state.data;
  const sliderValues = location.state.sliderValues;
  const text = 'MODELING...';
  const delay = 500;

  const [data, setData] = useState([]);

  const [currentText, setCurrentText] = useState('');
  const [currentIndex, setCurrentIndex] = useState(0);

  useEffect(() => {
    if (currentIndex <= text.length) {
      const timeout = setTimeout(() => {
        setCurrentText(prevText => prevText + text[currentIndex]);
        setCurrentIndex(prevIndex => prevIndex + 1);
      }, delay);
      
      return () => clearTimeout(timeout);
    }else {
      setCurrentText('');
      setCurrentIndex(0);
    }
  }, [currentIndex, delay, text]);

  // Function to fetch data using Axios
  const fetchData = async () => {
    try {
      const response = await axios.get("https://k9d109.p.ssafy.io/api/cat");
      setData(response.data);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  // Call fetchData on component mount
  useEffect(() => {
    fetchData();
  }, []);

  return (
    <div style={{ display: 'flex', flexDirection: 'column', justifyContent: 'center', alignItems: 'center' }}>
      <div style={{ height: '80vh',width: '100vw', display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
        {/* <h1>{model}</h1>
        <h2>{sliderValues}</h2> */}
        {data.length !== 0 ? (
          console.log(data),
          data.map((post) => (
            <li>{post}</li>
          )),
          navigate('/home')
        ) : (
          <div style={{ pointerEvents: 'none' }}>
            <img class='unselectable' src="loading_no_bg.gif" alt="로딩바" autoplay />
          </div>
        )}
      </div>
      <div style={{ height: '20vh', width: '100vw', display: 'flex', justifyContent: 'center', alignItems: 'flex-start' }}>
        <h1 style={{ color: 'white', fontSize: '5em', fontFamily: 'sebangFont' }}>{currentText}</h1>
      </div>
    </div>
  );
};

export default Modelify;