from fastapi import FastAPI

app = FastAPI() # 인스턴스 생성

@app.get("/") # get method로 '/'에 해당하는  생성
def root():
    return {'Hello':'World!'} 

@app.get("/cat")
def cat():
    return "Meow"

@app.get("/dog")
def dog():
    return "Barkbark"