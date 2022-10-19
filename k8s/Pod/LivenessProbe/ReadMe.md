
# Liveness Probe란?

컨테이너를 진단하는 도구이다.
* Pod가 계속 실행할 수 있음을 보장한다.
* Pod에 문제가 있을 경우, restart를 해준다.

Liveness Probe는 파드를 선언해줄 때, 적어넣는다.

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: nginx-pod
spec:
  containers:
  - name: nginx-container
    image: nginx:1.14
    livenessProbe:
      httpGet:
      path: /
      port: 80
```

위와 같이 livenessProbe를 추가하면, http 프로토콜을 이용하여 80포트로 접속하여 응답이 잘왔을 경우와 잘오지 않았을 경우를 이용하여 컨테이너 건강 상태를 확인할 수 있다.

> 위의 예시는 http를 이용한 건강 상태 체크를 수행한다. 건강 상태 수행은 여러가지 방식이 존재한다.

* httpGet 방식 : http Get을 날려 200 상태코드로 응답이 오지 않으면 컨테이너를 다시 시작한다.

* tcpSocket 방식 : 지정된 포트에 TCP 연결을 시도하고, 연결되지 않으면 컨테이너를 다시 시작한다.

* exec 방식 : exec 명령을 전달하고 명령의 종료코드가 0이 아니면 컨테이너를 다시 시작한다.

> 여러가지 방식으로 상태 체크를 할 수 있는데, 매개변수를 같이 넘겨서 상태 확인을 수행할 수 있다.

