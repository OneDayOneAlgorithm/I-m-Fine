import React, { useEffect, useState } from 'react';

const AnimatedDiv = ({ children, delay }) => {
  const [show, setShow] = useState(false);

  useEffect(() => {
    const timeout = setTimeout(() => {
      setShow(true);
    }, delay);

    return () => clearTimeout(timeout);
  }, [delay]);

  return (
    <div
      className={show ? 'animated-div' : ''}
      style={{
        opacity: show ? 1 : 0,
        transition: 'opacity 0.5s ease-in-out', // 애니메이션 설정
      }}
    >
      {children}
    </div>
  );
};

export default AnimatedDiv;