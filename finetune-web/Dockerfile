# 가져올 이미지를 정의
FROM node:14

# 경로 설정하기
WORKDIR /app

# package.json 워킹 디렉토리에 복사
COPY package.json package-lock.json .

# 명령어 실행 (의존성 설치)
RUN npm install

# 현재 디렉토리의 소스 코드를 도커 컨테이너의 워킹 디렉토리에 복사한다.
COPY . .

# 3000번 포트 노출
EXPOSE 3000

# npm start 스크립트 실행
CMD ["npm", "start"]
