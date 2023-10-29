import React from "react";
import { Link } from "react-router-dom";

const Send = (props) => {
  const { data, setData } = props.location.state; // data와 setData 가져오기

  const handleClick = () => {
    setData({ data }); // 데이터 설정
  };

  return (
    <div>
      <h1>Send</h1>
      <button onClick={handleClick}>Next</button>
      <Link to="/Finish">Go to Page 2</Link>
    </div>
  );
};

export default Send;
