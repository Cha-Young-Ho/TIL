# NetWork Navigator

* [Data Link Layer](https://github.com/Cha-Young-Ho/TIL/tree/main/CS/Network/Model/DataLink-Layer)
* [Network Layer](https://github.com/Cha-Young-Ho/TIL/tree/main/CS/Network/Model/Network-Layer)
* [Transport Layer](https://github.com/Cha-Young-Ho/TIL/tree/main/CS/Network/Model/Transport-Layer)

# 네트워크란 무엇인가?

> 노드들이 데이터를 공유할 수 있게 하는 디지털 전기 통신망의 하나이다.

* 노드 : 네트워크에 속하 ㄴ컴퓨터 또는 통신 장비를 뜻하는 말

# 인터넷이란?

> 문서, 그림 영상과 같은 여러가지 데이터를 공유하도록 구성된 세사엥서 가장 큰 전세계를 연결하는 네트워크

# 네트워크 분류

* LAN
* WAN
* MAN


## 크기에 따른 분류

* LAN
* WAN

### LAN(Local Area Network)
LAN은 가까운 지역을 하나로 묶은 네트워크

### WAN(Wide Area Network)
WAN은 먼 지역을 하나로 묶은 네트워크

> 여러가지의 LAN을 연결시켜놓은 것이다.

이러한 LAN을 연결하는 형태 또한 여러가지의 분류가 있다.

## 연결 형태에 따른 분류

* Star
* Mesh
* Tree
* 기타

대표적인 Star형과 Mesh형만 알아보도록 하자.

### Star 형

> 중앙 장비에 모든 노드가 연결된 Star 형

Star형은 중앙 장비가 고장이 났을 경우에 나머지 모든 노드들이 인터넷을 사용할 수 없다. 대표적으로 집에 존재하는 공유기를 말할 수 있다.
보통 아주 가까운 수준의 장비들을 연결할 때, Star형을 사용한다.

### Mesh 형

> 여러 노드가 서로 연결된 Mesh 형

어느 한 장비가 고장이 났을 경우에 다른 모든 노드들은 인터넷을 이용할 수 있다.

### 혼합형

실제 인터넷은 Star와 Mesh가 혼합되어 있다.

# 네트워크 통신 방식

* 유니 캐스트 : 같은 네트워크에서 특정한 한 노드와 통신하는 방식

* 멀티 캐스트 : 같은 네트워크에서 특정한 다수의 노드와 통신하는 방식

* 브로드 캐스트 : 같은 네트워크의 모든 노드와 통신하는 방식

# 네트워크 프로토콜

> 프로토콜은 일종의 약속이다.

컴퓨터는 통신이 왔다고해서 어떠한 의도로 통신이 온지 모른다. 그래서 일종의 양식(약속)을 정해두었다.

* Ethernet Protocol : 가까운 곳과 연락할 때

* ICMP IPv4 ARP : 멀리 있는 곳과 연락할 때

* TCP, UDP : 여러가지 프로그램으로 연락할 떄

위의 세 가지 프로토콜읊 포함하여 네트워크 통신이 이루어진다.

다음 그림을 보자.

<img width="333" alt="image" src="https://user-images.githubusercontent.com/79268661/188263206-ec4051a3-cde2-42f4-909a-258a6cad4965.png">


> 위의 그림은 실제 데이터와 통신할 떄 필요한 여러가지의 프로토콜이 겹쳐진다. 실제로 통신을 주고 받을 때 위와 같은 형태로 통신이 된다.

위와 같은 형태를 **패킷**이라고 한다. 패킷은 여러 프로토콜들로 캡슐화한 것을 말한다.

## 네트워크 노드 경로 추적하기

그러면 우리 컴퓨터에서 "구글" 컴퓨터로 가기위한 여정을 살펴보자.

MAC
```bash
traceroute 8.8.8.8
```

Window
```bash
tracert 8.8.8.8
```

위와 같이 입력하면 아래와 같이 나온다.

<img width="480" alt="스크린샷 2022-09-03 오후 5 35 32" src="https://user-images.githubusercontent.com/79268661/188263172-8a534882-cbd5-427c-a4a8-5eb23dacaa23.png">


출력값을 살펴보면 총 9번의 노드를 거쳐서 구글에 도착한 것을 볼 수 있다.

> * * * 로 나온 것은 해당 노드가 통신은 되지만, 자기의 IP 정보를 공개하지 않은 것이다.







