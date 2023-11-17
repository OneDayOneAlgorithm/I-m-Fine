

# 개발환경

- FastAPI

- React

# EC2 환경 설정

## EC2 서버 내 도커 설치

```
$ sudo apt install ca-certificates curl gnupg lsb-release -y
$ sudo mkdir -p /etc/apt/keyrings
$ curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
$ echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
$ sudo apt update
$ sudo apt install docker-ce docker-ce-cli containerd.io docker-compose docker-compose-plugin
```

## 도커 내 젠킨스 이미지 생성

- EC2 내 /home/ubuntu/ 경로에 jenkins-docker dir 생성

- /home/ubuntu/jenkins-docker/ 경로에 Dockerfile과 docker-compose.yml 생성

### Dockerfile

```
FROM jenkins/jenkins:latest

ENV DEBIAN_FRONTEND noninteractive
ENV DEBCONF_NOWARNINGS="yes"

USER root
RUN apt-get -y update && apt-get install -y --no-install-recommends \
    vim \
    apt-utils
RUN apt-get install ca-certificates curl gnupg lsb-release -y
RUN mkdir -p /etc/apt/keyrings
RUN curl -fsSL https://download.docker.com/linux/debian/gpg | gpg --dearmor -o /etc/apt/keyrings/docker.gpg
RUN echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/debian $(lsb_release -cs) stable" | tee /etc/apt/sources.list.d/docker.list > /dev/null
RUN apt-get -y update
RUN apt-get install docker-ce docker-ce-cli containerd.io docker-compose docker-compose-plugin -y
RUN if [ -e /var/run/docker.sock ]; then chown jenkins:jenkins /var/run/docker.sock; fi
RUN usermod -aG docker jenkins
USER jenkins
```

### docker-compose.yml

- /var/run/docker.sock:/var/run/docker.sock 을 통해 Jenkins 컨테이너가 EC2의 Docker 데몬과 상호 작용하여 도커 빌드 및 관리 작업을 수행할 수 있도록 한다.

```
version: "3.1"
services:
  jenkins:
    container_name: jenkins
    image: jenkins
    ports:
      - 50000:8080
      - 50001:50000
    volumes:
      - ~/jenkins:/var/jenkins_home
      - /var/run/docker.sock:/var/run/docker.sock
    restart: always
```

## 젠킨스 이미지 생성 및 컨테이너 실행

- /home/ubuntu/ 경로에 jenkins dir 생성 후 권한 부여
  
  - `jenkins` 디렉토리를 도커 컨테이너 내부의 `/var/jenkins_home` 디렉토리에 마운트하기 위함이다. 이렇게 함으로써 Jenkins 컨테이너에 EC2의 `jenkins` 디렉토리를 Jenkins 데이터 및 설정을 저장시킬 수 있다.

```
$ mkdir -p ~/jenkins
$ sudo chmod 777 ~/jenkins
$ docker build --no-cache -t jenkins . && \
   docker-compose up -d
```

## 젠킨스 비밀번호 확인

- http://k9d109.p.ssafy.io:50000/ 들어가서 비밀번호 입력
- 아래 명령어로 도커 로그를 확인해서 젠킨스 비밀번호 확인

```
$ docker logs jenkins
```

## 젠킨스에 깃랩 인증서 발급

- 젠킨스관리 - 인증 추가 후 아래 정보 입력

- Username : 깃랩 ID

- Password : 깃랩 PW

- ID : github-access-token

## 젠킨스 파이프라인

- Build when a change is pushed to GitLab. GitLab webhook URL: http://k9d109.p.ssafy.io:50000/project/test-pipeline 체크

- Push Events 체크

- Opened Merge Request Events 체크

- Approved Merge Requests (EE-only) 체크

- Comments 체크

- 이후 Gitlab web hook에 http://k9d109.p.ssafy.io:50000/project/test-pipeline 추가

```
pipeline {
    agent any

    stages {
        stage('Clone Repository') {
            steps {
                script {
                    git branch: 'master', credentialsId: 'github-access-token', url: 'https://lab.ssafy.com/s09-final/S09P31D109.git'
                }
            }
        }

        stage('Build & Deploy') {
            steps {
                script{
                    def fastapi = sh(script: "docker ps -a --filter 'name=fastapi' --format '{{.Names}}' | grep fastapi", returnStatus: true)
                    if (fastapi == 0) {
                        sh "docker rm -f fastapi"
                    }

                    def nginx = sh(script: "docker ps -a --filter 'name=nginx' --format '{{.Names}}' | grep nginx", returnStatus: true)
                    if (nginx == 0) {
                        sh "docker rm -f nginx"
                    }

                    def certbot = sh(script: "docker ps -a --filter 'name=certbot' --format '{{.Names}}' | grep certbot", returnStatus: true)
                    if (certbot == 0) {
                        sh "docker rm -f certbot"
                    }

                    def reactapp2 = sh(script: "docker ps -a --filter 'name=reactapp2' --format '{{.Names}}' | grep reactapp2", returnStatus: true)
                    if (reactapp2 == 0) {
                        sh "docker rm -f reactapp2"
                    }

                    sh "whoami"
                    sh "pwd"
                    // 아래 명령어는 처음 이미지를 빌드할 때만 사용한다.
                    // sh "docker-compose up -d --build "    
                }
            }
        }
    }
}
```

## 깃 레포지토리 내 docker-compose.yml 및 Dockerfile 작성

### docker-compose.yml

```
version: '3'

services:

  fastapi:
    container_name: fastapi  # 컨테이너 이름을 지정합니다.
    build:
      context: ./finetune-back
      dockerfile: Dockerfile  # Dockerfile의 경로를 지정합니다. 현재 디렉토리에 있다면 이렇게 지정하면 됩니다.    
    ports: 
      - "8000:80"    
    image: fastapi

  nginx:
    container_name: nginx
    ports:
      - "80:80"
      - "443:443"
    image: nginx:latest
    volumes: 
      - /home/ubuntu/jenkins/workspace/test-pipeline/conf/nginx/conf.d:/etc/nginx/conf.d
      - /home/ubuntu/jenkins/workspace/test-pipeline/conf/certbot/conf:/etc/nginx/ssl
      - /home/ubuntu/jenkins/workspace/test-pipeline/conf/certbot/data:/var/www/certbot


  certbot:
    container_name: certbot
    image: certbot/certbot:latest
    command: certonly --webroot --webroot-path=/var/www/certbot --email leehyunk6310@naver.com --agree-tos --no-eff-email -d k9d109.p.ssafy.io
    volumes:
      - /home/ubuntu/jenkins/workspace/test-pipeline/conf/certbot/conf:/etc/letsencrypt
      - /home/ubuntu/jenkins/workspace/test-pipeline/conf/certbot/logs:/var/log/letsencrypt
      - /home/ubuntu/jenkins/workspace/test-pipeline/conf/certbot/data:/var/www/certbot


  reactapp:
    container_name: reactapp2  # 컨테이너 이름을 지정합니다.
    build:
      context: ./finetune-web
      dockerfile: Dockerfile  # Dockerfile의 경로를 지정합니다. 현재 디렉토리에 있다면 이렇게 지정하면 됩니다.    
    ports: 
      - "3000:3000"    
    image: reactapp
```

### FastAPI DockerFile

```
FROM python:3.8

COPY ./src /src
WORKDIR /src

RUN pip install -r requirements.txt

EXPOSE 80

CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "80"]
```

### React DockerFile

```
# 가져올 이미지를 정의
FROM node:14

# 현재 디렉토리의 소스 코드를 도커 컨테이너의 워킹 디렉토리에 복사한다.
COPY ./src ./app/src
COPY ./public ./app/public

# package.json 워킹 디렉토리에 복사
COPY package.json ./app
COPY package-lock.json ./app

# 경로 설정하기
WORKDIR /app

# 명령어 실행 (의존성 설치)
RUN npm install

# 3000번 포트 노출
EXPOSE 3000

# npm start 스크립트 실행
CMD ["npm", "start"]
```

# 시연 시나리오

## 모델 선택

![](포팅%20메뉴얼/2023-11-13-14-33-20-image.png)

## 선택한 모델의 파라미터를 직접 조정

![](포팅%20메뉴얼/2023-11-13-14-37-37-image.png)

## 모델 생성

![](포팅%20메뉴얼/2023-11-13-14-37-59-image.png)
