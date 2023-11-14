from celery import Celery
from pydantic import BaseModel
from time import sleep
import os
import requests
from fastapi import Request
from database import SessionLocal, engine
from typing import List
import models

broker_url = os.getenv('CELERY_BROKER_URL', 'pyamqp://guest@localhost//')
# 앱 이름과 broker 설정(heroku상의 rabbitmq url)
app = Celery('tasks', broker=broker_url)

models.Base.metadata.create_all(bind=engine)


class Item(BaseModel):
    text: str

# gpt에 요청하는 부분 작성해야함
@app.task(name='tasks.gpt')
async def gpt(request: Request):
    json_data = await request.json()
    print(json_data)
    # response = requests.post(col)
    # print(text[::-1])
    # return text[::-1]

@app.task(name='tasks.llama')
def reverse(text):
    print("5 left")
    sleep(5)
    print(text[::-1])
    return text[::-1]

####################################################### DB 접근
####################################################### 참고해서 코드 작성
# Dependency
def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

# 로그 등록하기
@app.post("/logs", response_model=schemas.Logs)
def create_user(request: schemas.LogRequestDto, db: Session = Depends(get_db)):
    return crud.create_log(db=db, request=request)

# 모든 로그 받기
@app.get("/logs", response_model=List[schemas.Logs])
def read_logs(skip: int = 0, limit: int = 100, db: Session = Depends(get_db)):
    logs = crud.get_logs(db, skip=skip, limit=limit)
    return logs

# 하나의 로그 받기
@app.get("/logs/{log_id}", response_model=schemas.Logs)
def read_log(log_id: int, db: Session = Depends(get_db)):
    db_log = crud.get_log(db, log_id=log_id)
    if db_log is None:
        raise HTTPException(status_code=404, detail="Log not found")
    return db_log

# 로그 업데이트
@app.put("/logs/{log_id}", response_model=schemas.Logs)
def update_log(log_id: int, update_data: schemas.LogResponseDto, db: Session = Depends(get_db)):
    # CRUD 함수를 사용하여 로그 업데이트
    updated_log = crud.update_log(db=db, log_id=log_id, update_data=update_data)

    # 업데이트된 로그가 없는 경우 오류 발생
    if updated_log is None:
        raise HTTPException(status_code=404, detail="Log not found")

    return updated_log