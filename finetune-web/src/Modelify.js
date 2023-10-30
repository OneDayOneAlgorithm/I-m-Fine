import React from 'react';
import { Link } from 'react-router-dom';

const Modelify = (props) => {
  const { data, setData } = props.location.state; // data와 setData 가져오기

  const handleClick = () => {
    setData({data}); // 데이터 설정
  };

  return (
    <div>
      <h1>Modelify</h1>
      <button onClick={handleClick}>Next</button>
      <Link to="/Change">Go to Page 2</Link>
    </div>
  );
};

export default Modelify;