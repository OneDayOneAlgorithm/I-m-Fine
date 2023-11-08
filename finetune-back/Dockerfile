# 해당 도커파일은 Python 3.8을 기반으로하는 이미지를 생성하며, 다음 작업을 수행합니다:
FROM python:3.9

# 현재 디렉토리의 ./src 디렉토리의 내용을 컨테이너 내부의 /src 디렉토리로 복사합니다.
COPY ./src /src

# 작업 디렉토리를 /src로 설정합니다.
WORKDIR /src

# requirements.txt 파일에 명시된 필수 Python 라이브러리들을 설치합니다.
RUN pip install -r requirements.txt

# 컨테이너가 호스트의 80번 포트에 연결되도록 설정합니다.
EXPOSE 80

# 컨테이너가 시작될 때 실행되는 명령을 정의합니다. 이 경우 uvicorn 서버를 실행하고, main 모듈에서 app 객체를 찾아 호스트 0.0.0.0의 80번 포트에서 서비스를 시작합니다.
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "80"]