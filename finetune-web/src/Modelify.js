import React from 'react';
import { Link, useLocation } from "react-router-dom";

const Modelify = () => {
  const location = useLocation();
  const data = location.state.data;
  const sliderValues = location.state.sliderValues;
  
  return (
    <div>
      <h1>{data}</h1>
      <h2>{sliderValues}</h2>
      <Link to="/">
        <h1>ddd</h1>
      </Link>
    </div>
  );
};

export default Modelify;