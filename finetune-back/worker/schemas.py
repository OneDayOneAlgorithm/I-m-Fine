from pydantic import BaseModel
from datetime import datetime
from typing import Optional

class LogRequestDto(BaseModel):
    input_text: Optional[str] = None

    class Config:
        orm_mode = True

class LogResponseDto(BaseModel):
    output_text: str

    class Config:
        orm_mode = True

class Logs(BaseModel):
    input_text: str
    output_text: Optional[str] = None 
    is_completed: bool 
    request_time: datetime
    complete_time: Optional[datetime] = None