from sqlalchemy.orm import Session

import models
import schemas
from datetime import datetime

def get_log(db: Session, log_id: int):
    return db.query(models.Logs).filter(models.Logs.id == log_id).first()

def get_logs(db: Session, skip: int = 0, limit: int = 100):
    return db.query(models.Logs).offset(skip).limit(limit).all()

def create_log(db: Session, request: schemas.LogRequestDto):
    db_log = models.Logs(input_text=request.input_text, request_time=datetime.now())
    db.add(db_log)
    db.commit()
    db.refresh(db_log)
    return db_log

def update_log(db: Session, log_id: int, update_data: schemas.LogResponseDto):
    # 해당 log_id를 가진 로그 객체를 찾습니다.
    db_log = db.query(models.Logs).filter(models.Logs.id == log_id).first()
    
    # 로그 객체가 존재하지 않으면 None을 반환합니다.
    if db_log is None:
        return None

    db_log.output_text = update_data.output_text
    db_log.is_completed = True
    db_log.complete_time = datetime.now()
    db.commit()
    db.refresh(db_log)
    
    return db_log