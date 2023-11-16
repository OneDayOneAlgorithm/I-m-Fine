from sqlalchemy import Boolean, Column, Integer, String, DateTime
from datetime import datetime

from database import Base

class Logs(Base):
    __tablename__ = "logs"

    id = Column(Integer, primary_key=True, index=True)
    input_text = Column(String(500))
    output_text = Column(String(500), default = None)    
    is_completed = Column(Boolean, default=False)
    request_time = Column(DateTime, default=None)  # 요청 시간
    complete_time = Column(DateTime, default=None)  # 완료 시간 (수동으로 설정해야 함)