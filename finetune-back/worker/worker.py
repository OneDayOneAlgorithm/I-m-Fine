from celery import Celery
from pydantic import BaseModel
from time import sleep
import os
import requests
from fastapi import Request, Depends, HTTPException
from database import SessionLocal, engine
from sqlalchemy.orm import Session
from typing import List
import models
import schemas
import crud

broker_url = os.getenv('CELERY_BROKER_URL', 'pyamqp://guest@localhost//')
# 앱 이름과 broker 설정(heroku상의 rabbitmq url)
app = Celery('tasks', broker=broker_url)

models.Base.metadata.create_all(bind=engine)

class Item(BaseModel):
    text: str

# class GPTRequest(BaseModel):
#     input_text: str
#     mlp_weight: float
#     attn_weight: float
#     eps_weight: float
#     url: str 

# gpt에 요청하는 부분 작성해야함
@app.task(name='tasks.gpt')
def gpt(json_data, colab_url):
    # 질문 기록
    id = create_logs(schemas.LogRequestDto(input_text=json_data["input_text"]))

    # response = requests.post(colab_url, json=json_data)
    response = requests.post(colab_url, json=json_data)
    # print(json_data["input_text"])
    # print(colab_url)

    # 업데이트하는 부분 작성
    print(response.text)
    update_log(id, schemas.LogResponseDto(output_text=response.text))


@app.task(name='tasks.llama')
def llama(json_data, colab_url):
    id = create_logs(schemas.LogRequestDto(input_text=json_data["input_text"]))

    response = requests.post(colab_url + "/llama", json=json_data)
    print(response.text)
    update_log(id, schemas.LogResponseDto(output_text=response.text))

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
# @app.post("/logs", response_model=schemas.Logs)
def create_logs(request: schemas.LogRequestDto):
    # 데이터베이스 세션 생성
    db = SessionLocal()
    try:
        # CRUD 작업 수행
        return crud.create_log(db=db, request=request)
    finally:
        # 세션 닫기
        db.close()

# 로그 업데이트
# @app.put("/logs/{log_id}", response_model=schemas.Logs)
# @app.task(name='tasks.updateLog')
def update_log(log_id: int, update_data: schemas.LogResponseDto):
    # 데이터베이스 세션 생성
    db = SessionLocal()
    try:
        # CRUD 작업 수행
        return crud.update_log(db=db, log_id=log_id, update_data=update_data)
    finally:
        # 세션 닫기
        db.close()

    # # CRUD 함수를 사용하여 로그 업데이트
    # updated_log = 

    # # 업데이트된 로그가 없는 경우 오류 발생
    # if updated_log is None:
    #     raise HTTPException(status_code=404, detail="Log not found")

    # return updated_log

# 모든 로그 받기
# @app.get("/logs", response_model=List[schemas.Logs])
@app.task(name='tasks.getLogs')
def read_logs(skip: int = 0, limit: int = 100):
    # 데이터베이스 세션 생성
    db = SessionLocal()
    try:
        # CRUD 작업 수행
        return crud.get_logs(db, skip=skip, limit=limit)

    finally:
        # 세션 닫기
        db.close()


# # 하나의 로그 받기
# # @app.get("/logs/{log_id}", response_model=schemas.Logs)
# @app.task(name='tasks.getLog')
# def read_log(log_id: int, db: Session = Depends(get_db)):
#     db_log = crud.get_log(db, log_id=log_id)
#     if db_log is None:
#         raise HTTPException(status_code=404, detail="Log not found")
#     return db_log

