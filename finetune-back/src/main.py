from fastapi import FastAPI, APIRouter, Request
from fastapi.middleware.cors import CORSMiddleware
from fastapi.responses import HTMLResponse
from fastapi.templating import Jinja2Templates
import os


app = FastAPI() # 인스턴스 생성
router = APIRouter()  # APIRouter 인스턴스 생성
templates = Jinja2Templates(directory=os.path.join(os.path.dirname(__file__), "templates"))

origins = [
    # "http://192.168.0.13:3000", # url을 등록해도 되고
    "*" # private 영역에서 사용한다면 *로 모든 접근을 허용할 수 있다.
]

app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True, # cookie 포함 여부를 설정한다. 기본은 False
    allow_methods=["*"],    # 허용할 method를 설정할 수 있으며, 기본값은 'GET'이다.
    allow_headers=["*"],	# 허용할 http header 목록을 설정할 수 있으며 Content-Type, Accept, Accept-Language, Content-Language은 항상 허용된다.
)

@router.get("/micro", response_class=HTMLResponse)
async def micro(request: Request):
    return templates.TemplateResponse("index.html", {"request": request, "message": "Barkbark"})

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