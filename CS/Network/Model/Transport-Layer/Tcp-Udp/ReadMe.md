# TCP, UDP

# UDP란?

> User DataGram Protocol을 말한다. 일반적으로 오류의 검사와 수정이 필요 없는 프로그램에서 수행하는 것으로 가정한다.

# UDP 프로토콜 구조

![3](https://user-images.githubusercontent.com/79268661/188301135-d2a65c32-92ab-4170-955f-4c672c9ff9bc.png)

* Source Port : 출발지 포트
* Destination Port : 도착지 포트
* Length : udp protocol header + payload 의 길이
* Checksum : 포로토콜의 오류를 체크해주는 필드

# UDP 프로토콜을 사용하는 프로그램

* DNS 서버
* tftpd 프로그램(파일 전송 프로그램)
* RIP 프로토콜

# TCP란?

> Transmission Control Protocol을 말한다. 연결된 컴퓨터에서 실행되는 프로그램 간에 통신을 안정적으로, 순서대로, 에러없이 교환할 수 있게 한다.

# TCP 프로토콜 구조

* Source Port : 출발지 포트
* Destination Port : 도착지 포트
* Sequence Number :
* Acknowledgement Number :
* Offset : header의 길이를 말한다.
* Reserved : 예약되지 않은 필드
* TCP Flags : 해당 메세지가 어떤 것인지 지정해주는 값, 해당 TCP의 기능을 말하는 것
* Window : 얼마만큼 데이터를 보내라는 뜻을 담고 있는 필드
* Checksum :
* Urgent Pointer :
* TCP Options :

## TCP Flags

* U(Urgent) : 긴급 bit, 급한 데이터인 것을 알려주는 것

* A(Ack) : 승인 bit(~해도 된다.)

* P(Push) : 밀어넣기 bit, 버퍼의 크기를 신경쓰지 않고 계속해서 데이터를 보내준다는 뜻

* R(Reset) : 초기화 bit, 문제가 생겨서 연결을 리셋하자는 뜻

* S(Sync) : 동기화 bit, 연결을 시작할 때 무조건 사용하는 값, 서로 연결하면서 계속해서 연결상태를 동기화하기 위한 값

* F(Fin) : 종료 bit, 연결을 종료한다는 값

## TCP 3Way Handshake

TCP를 이용한 통신을 할 때 프로세스와 프로세스를 연결하기 위해 가장 먼저 수행되는 과정

1. 클라이언트가 서버에게 요청 패킷을 보내고

2. 서버가 클라이언트의 요청을 받아들이는 패킷을 보내고

3. 클라이언트는 이를 최종적으로 수락하는 패킷을 보낸다.

## TCP 3Way Handshake 상세보기

## TCP 연결의 시작

![image](https://user-images.githubusercontent.com/79268661/188301196-9409283a-e911-44f2-ad51-27bf22b320f5.png)

1. 사용자의 웹브라우저는 Eth + IPv4 + TCP를 포장하여 웹서버를 향해서 요청을 보낸다.
    * TCP의 Flags는 Seq가 설정되어 보내진다.
    * S : 100
    * A : 0

  > 가장 처음의 Seq와 Ack 값은 요청자의 마음대로 설정한다.

2. 서버는 요청을 확인하고 응답(답장)을 보낸다.
    * S : 2000
    * A : 101

  > 응답자는 요청자가 처음에 요청한 Seq와 Ack에 동기화 해야한다. 여기서 Ack는 요청자의 Seq + 1 값을 설정해준다. 그리고 Seq 값은 랜덤으로 생성한다.

3. 웹브라우저는 답장을 받고 서버의 응답에 답장한다.
      * S : 101
      * A : 2001

  > 요청자는 응답자의 응답을 받고, 응답자가 보낸 Seq + 1을 Ack 값으로 설정해준다. 그리고 Seq 값은 받은 Ack 값을 설정해준다.

  ❗️ 처음에만 Seq 값을 랜덤으로 생성하고, 다음부터는 상대가 보낸 Ack값이 나의 Seq가 된다.

## TCP 데이터 송수신 과정

  실제 데이터를 주고받을 떄는 Sync 값과 Ack 값이 달라진다.

  1. 보낸 쪽에서 다시 보낼 떄는 Seq 번호와 Ack 번호가 그대로다.
      * S : 101
      * A : 2001
      * Data : 100

  2. 받는 쪽에서 Seq 번호는 받은 Ack 번호가 된다.
      * S : 2001
      * A : 201 (보낸 S값 + 데이터 크기)
      * Data : 500

  3. 받는 쪽에서 Ack 번호는 받는 Seq 번호 + 데이터의 크기가 된다.
      * S : 201
      * A : 2501 (보낸 S값 + 데이터 크기)

## TCP 상태

* LISTEN : 클라이언트의 요청을 기다리고 있는 상태
* ESTABLISHED : 연결이 수립된 상태(3way handshake가 끝난 뒤)
* SYN_SENT : 클라이언트가 연결을 요청한 상태(active open)
* SYN_RECEIVED : 연결 요청을 받은 상태


  
