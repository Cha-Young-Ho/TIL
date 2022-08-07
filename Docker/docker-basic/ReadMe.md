
> 오늘은 도커에 대해서 공부를 해보자!

---

# 목차
* 도커란?
* 도커 설치
* 컨테이너란?
* 컨테이너 이미지란?
* 도커 허브란?
* Docker-Compose 란?
* Docker-Compose 이용하기

---

# 도커란?

> 도커는 컨테이너 환경에서 독립적으로 애플리케이션을 실행할 수 있도록 해주는 도구이다.

도커에 대해서는 인터넷에 치면 방대하게 있으니, 나는 예시를 들어서 설명해보겠다.

1. A, B라는 컴퓨터가 있다. A는 개발 컴퓨터이고, B는 고객 컴퓨터이다.

2. 개발자는 A라는 컴퓨터에서 여러가지 환경(자바, MySQL, 기타 등등)을 구축한다.

3. 개발자는 A컴퓨터에서 개발을 한다.

4. 도커를 사용하지 않는 경우에는 개발자가 고객 컴퓨터인 B에 환경을 직접 설치해주고 개발한 내용을 B컴퓨터에서 실행하여야 한다.

5. 도커를 사용하는 경우에는 모든 환경세팅과 기타 실행 내용들을 하나의 파일로 넘겨주면 알아서 촤르륵~ 된다.

## 좀 더 상세히

좀 더 상세히 보자.

기존의 가상 머신과 비슷한 개념인데, 가상머신은 설치되는 박스에 각각의 독자적인 OS를 가지고 있다.

하지만 도커는 Host OS를 같이 공유하고 있으며, Docker Daemon위에서 동작한다.
(자바 JVM이랑 약간 비슷한 느낌?)

그래서 OS라는 별도의 저장공간이 필요없고 가볍다.

---

# 도커 설치

> 그럼 도커를 설치하러 가보자. (필자는 맥이다.)

[✅ 도커 설치 페이지](https://www.docker.com/get-started/)


![](https://velog.velcdn.com/images/jkijki12/post/1e6941fb-ea7b-4d15-9fbd-a4dda4f1d8d2/image.png)

맥 M1 버전으로 다운로드 받는다.

![](https://velog.velcdn.com/images/jkijki12/post/ab666a53-7cd4-4247-9156-821e9433c5f0/image.png)

dmg 파일을 통해서 설치를 해준다.

그리고 설치된 파일을 실행해주자.

![](https://velog.velcdn.com/images/jkijki12/post/677b7b75-a7cc-498e-8e4a-baf33056c000/image.png)

이 단계들을 넘으면 다음과 같이 고래표시가 나온다.

![](https://velog.velcdn.com/images/jkijki12/post/81857f74-79cf-438a-86bb-27f098c16add/image.png)


끝.

버전을 확인해보자.

![](https://velog.velcdn.com/images/jkijki12/post/950a2296-071a-4e94-a565-ffeae63d87fe/image.png)

버전이 나온다. 성공!

---

# 컨테이너란?

가장 먼저 OS의 작동 구조부터 알아야 한다.

![](https://velog.velcdn.com/images/jkijki12/post/772ebeb7-aee5-439e-9132-bfdff1915270/image.png)

이미지 출처 : [https://sdesigner.tistory.com/106](https://sdesigner.tistory.com/106)

위의 그림을 살펴보면 프로세스들이 실행을 하기 위해서 하드웨어 자원을 요청한다.

그래서 Kernel로 System Call을 통해서 요청하고 하드웨어 자원을 할당 받을 수 있다.

## Name Spacing

Kernel을 통해서 하드웨어 자원을 할당 받을 때를 생각해보자.

> A라는 프로세스는 java 1이 필요하다.
B라는 프로세스는 java 2가 필요하다.

이 때 한 하드웨어 공간에 java의 두 버전을 설치하는 것은 문제가 생길 수 있다.

그래서 하드웨어 공간을 나누게 된다.

![](https://velog.velcdn.com/images/jkijki12/post/58f8916b-ddae-4ad1-b789-8a06d6f263fd/image.png)

이미지 출처 : [https://sdesigner.tistory.com/106](https://sdesigner.tistory.com/106)

위의 그림과 같이 프로세스에 대한 공간을 나눠두고, 각 프로세스가 Hard Disk를 read하는 System Call을 커널로 전달할 경우 커널은 해당 프로세스를 확인하고 프로세스에 알맞는 공간에 있는 리소스를 제공해준다.

_**이것을 네임스페이싱 이라고 한다.**_

## 그래서 컨테이너란?

컨테이너는 다음 그림과 같이 OS 동작구조 내에서 수직적인 하나의 전체 running process라고 한다.

![](https://velog.velcdn.com/images/jkijki12/post/ddf91962-6fb2-4860-8f69-b5734dc7139b/image.png)

이미지 출처 : [https://sdesigner.tistory.com/106](https://sdesigner.tistory.com/106)

> 한마디로, 컨테이너는 위와 같은 형태로 프로세스와 해당 프로세스에 대한 모든 설정, 자원들을 모아놓은 형태라고 할 수 있다.

---
# 컨테이너 이미지란?

컨테이너 이미지는 어떠한 프로그램을 실행하기 위해서 필요한 부수적인 내용들을 가지고 있는 File이다.

이미지 파일은 어떻게 생겼는지 보기 전에 컨테이너의 내부 구조를 보자.

![](https://velog.velcdn.com/images/jkijki12/post/f586ce0a-f619-4596-ace6-479327f80816/image.jpeg)

app.js를 실행하기 위한 nodeJS가 설치되어 있는 모습이다. 가장 상위 layer는 app.js를 실행하는 커맨드이다.

이렇게하고 실행하면 도커는 uuid를 할당하고 다음과 같이 image 파일이 생성된다.

![](https://velog.velcdn.com/images/jkijki12/post/1519edb1-d1cb-487e-b644-0956beaad34c/image.jpeg)

위와 같은 이미지 파일이 Docker Daemon위에서 running 하고 있을 때 컨테이너라고 말할 수 있다.

---

# 도커 허브란?

도커 허브는 도커에서 제공하는 기본 이미지 저장소이다. 사람들이 흔하게 쓰는 대표적인 image를 제공한다. (ubuntu, centos, debian, mysql 등등)

또한 나의 image도 공유할 수 있다.

---

# 이미지 실행하기

이미지를 실행해보자. 실행하면 컨테이너가 생성된다.

```shell
docker run -d --name web -p 80:80 nginx:latest
```

위와 같이 명령어를 입력하면 이미지가 실행된다.

## 이미지 실행 옵션

```shell
docker run <옵션> <이미지> <명령> <매개 변수>
```

해당내용은 다음 글에 있습니다!

[✅ 도커 run 옵션](https://wooono.tistory.com/348)

## 도커 파일 만들기

도커를 사용해서 각 프로세스 하나하나는 실행하기가 편해졌다. 하지만 하나의 애플리케이션을 동작시키는 환경을 만들기 위해서는 너무나 많은 이미지를 다운받고 실행하여야 한다.

그래서 도커 파일을 미리 작성하여 이러한 과정을 자동화 시킬 수 있다.

> Docker file은 컨테이너에 설치해야하는 패키지, 소스코드, 명령어, 환경변수 설정 등등을 기록한 파일이다. 이것을 빌드하면 이것을 토대로 자동 빌드된다.

### 도커 파일 작성 요령

해당 내용은 다음 글에 있습니다!

[✅ 도커파일 생성](https://khj93.tistory.com/entry/Docker-Docker-File-%EC%9E%91%EC%84%B1%ED%95%98%EA%B8%B0-%EB%AA%85%EB%A0%B9%EC%96%B4)

---

# Docker-Compose 란?

## Docker-Compose의 존재이유
다음의 상황을 생각해보자.

> Docker를 이용하여 Spring과 mysql을 실행하고 연동하고싶다.

그러면 다음의 명령어를 입력해야한다.

* Docker network 생성
```bash
docker network create <옵션> <네트워크 이름>
```

* MySQL 설치 및 이미지 실행

```bash
docker run -d --network test_net --network-alias mysql -v  /build/DB/mysql:/var/lib/mysql --name mysqlDB -e MYSQL_DATABASE=test_schema -e MYSQL_USER=user01 -e MYSQL_PASSWORD=user01 -e MYSQL_ROOT_PASSWORD=password -p 3306:3306 mysql   
```

* Spring 서버 이미지 빌드

```bash
docker build --tag app/app-test:0.0.2 . 
```

* Spring 서버 이미지 실행
```bash
docker run -d --name app --network test_net -p 8080:8080 test/test:0.0.2
```

> 명령어를 많이 쳐야 하자나?

작은 프로젝트라서 명령어가 작지만, 서비스가 커질 수록 엄청나게 많은 명령어를 입력해야한다. 이러한 문제를 해결하기위하여 Docker-Compose가 존재한다!

## Docker-Compose

> Docker-Compose는 복수의 컨테이너를 실행시키는 툴이다.

yml 기반으로 동작한다.

### Docker-Compose 예시

```yml
version: "3"
services:
  mysqlDB:
    image: mysql
    container_name: mysqlDB
    environment:
      - MYSQL_DATABASE=anonymous_board
      - MYSQL_ROOT_PASSWORD=password
      - MYSQL_USER=user01
      - MYSQL_PASSWORD=user01

    ports:
      - 3306:3306
    networks:
      - test_net

  app:
    build: .
    ports:
      - 8080:8080
    restart: on-failure
    depends_on:
      - mysqlDB
    networks:
      - test_net
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysqlDB:3306/anonymous_board?serverTimezone=UTC&characterEncoding=UTF-8
      SPRING_DATASOURCE_USERNAME: user01
      SPRING_DATASOURCE_PASSWORD: user01

networks:
  test_net:

```

아까 썼던 모든 명령어들을 하나의 파일에 정의할 수 있다.

### Docker-Compose 실행

실행 명령어는 쉽다.

Docker-Compose가 있는 디렉터리로 이동하고, 다음의 명령어를 입력하면 된다.

```bash
docker-compose up
```

그러면 상단부터 차례대로 읽어나가면서 docker 명령어를 실행해준다.
