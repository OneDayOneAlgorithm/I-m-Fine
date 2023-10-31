from fastapi import FastAPI, APIRouter

app = FastAPI() # 인스턴스 생성
router = APIRouter()  # APIRouter 인스턴스 생성


@router.get("/") # get method로 '/'에 해당하는  생성
def root():
    return {'Hello':'World!'} 

@router.get("/cat")
def cat():
    return "Meow"

@router.get("/dog")
def dog():
    return "Barkbark"

app.include_router(router, prefix="/api")  # "/api" 접두사와 함께 router를 app에 포함