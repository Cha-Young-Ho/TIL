
# Pod란?

* 컨테이너를 표현하는 k8s api의 최소 단위이다.
* pod에는 하나 또는 여러 개의 컨테이너가 포함될 수 있다.

# Pod 생성하기

파드를 생성할 수 있는 방법은 2가지가 존재한다.

* kubectl run 명령으로 생성
* pod yaml을 이용하여 생성

## kubectl run 명령

명령어는 다음과 같이 구성되어 있다.

```bash
$ kubectl run webserver --image=nginx:1.14
```


## pod yaml을 이용한 생성

yaml 파일은 다음과 같이 구성되어 있다.

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: webserver
spec:
  containers:
  - name: nginx-container
    image: nginx:1.14
    imagePullPolicy: Always
    ports:
    - containerPort: 80
      protocol: TCP
```

위와같이 생성하고 실행 kubectl 명령어를 입력한다.

```bash
$ kubectl create -f pod.yaml
```

## 현재 동작중인 Pod 확인

```bash
$ kubectl get pods
```

위와 같이 입력하면 실행중인 pod들을 확인할 수 있다.
여러가지 옵션을 추가하여 실행정보를 다양한 내용으로 볼 수 있다.

# MultiContainer pod

yaml로 생성하는게 편하다. yaml 로 만들어보자.

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: multipod
spec:
  containers:
  - name: nginx-container
    image: nginx:1.14
    ports:
    - containerPort: 80
  - name: centos-container
    image: centos:7
    command:
  - sleep
  - "10000"
```

yaml 실행

```bash
$ kubectl create -f pod-multi.yaml
```

# Pod 내부 Container 진입

```bash
$ kubectl exec multipod -c nginx-container -it -- /bin/bash
```

위와 같이 입력하면 pod 내부의 container로 진입할 수 있다.

# Pod 동작 Flow

1. 쿠버네티스에게 웹 서버를 실행해달라고 요청한다.

```bash
$ kubectl run webserver --image=nginx:1.14
```

2. 쿠버네티스의 API가 문법이 맞는지 확인한다.

3. node에 대한 정보를 etcd에서 꺼내온다.
4. scheduler를 이용하여 적당한 node를 찾는다.
5. 스케쥴링 중에는 pending 상태라고 한다.
6. 스케쥴링이 완료되면 running 상태가 된다.



