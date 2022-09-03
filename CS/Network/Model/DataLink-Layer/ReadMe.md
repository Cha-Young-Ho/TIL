# 데이터 링크 계층
2계층인 데이터 링크 계층은 물리 계층으로 송수신되는 정보를 관리하여 안전하게 전달되도록 도와주는 역할을 한다.

데이터 링크 단계에서 사용되는 주소가 있다.

> LAN에서 통신할 때 사용하는 주소는 MAC 주소라고 말한다.

# MAC

* MAC 주소는 16진수로 사용한다.

6C-29-95-04-EB-A1

여기서

OUI : 6C-29-95는 IEEE에서 부여하는 일종의 제조회사 식별 ID이다.

고유번호 : 04-EB-A1는 제조사에서 부여한 고유번호이다.

# 2계층 프로토콜

Ethernet 프로토콜이라고 말한다.

![image](https://user-images.githubusercontent.com/79268661/188266157-47866e94-1eee-4be3-ab61-595e8a96ea83.png)


위의 그림처럼 이루어져 있다.

* Preable : 송신측과 수신측의 전송속도를 맞추기위한 내용

* Destination Address : 목적지 주소 6byte

* Source Address : 출발지 주소 6byte

* Ethernet Type : 상위 계층의 프로토콜 내용을 미리 알려주는 타입 내용(IP인지 ARP인지)






