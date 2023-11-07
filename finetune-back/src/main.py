from fastapi import FastAPI, APIRouter, Request
from fastapi.staticfiles import StaticFiles
from fastapi.middleware.cors import CORSMiddleware
import requests
from fastapi.responses import HTMLResponse
from fastapi.templating import Jinja2Templates
import os

app = FastAPI() # 인스턴스 생성
app.mount("/static", StaticFiles(directory="static"), name="static")
router = APIRouter()  # APIRouter 인스턴스 생성
colab_url = ""
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
    return templates.TemplateResponse("index.html", {"request": request})

@router.get("/") # get method로 '/'에 해당하는  생성
def root():
    return {'Hello':'World!'} 

# 코랩 주소 업데이트
@router.post("/set-url")
def setUrl(url: str): 
    global colab_url
    colab_url = url
    return colab_url

@router.get("/get-url")
def getUrl():
    return colab_url

#gpt 모델 가중치 변경해서 데이터 반환
@router.post("/gpt")
async def call_colab_function(request: Request):
    json_data = await request.json()
    response = requests.post(colab_url, json=json_data)
    return response.text

app.include_router(router, prefix="/api")  # "/api" 접두사와 함께 router를 app에 포함