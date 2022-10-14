# k8s namespace란?

> namespace는 "클러스터 하나를 여러 개의 논리 적인 단위로 나눠서 사용"한다는 의미이다.

* 쿠버네티스 클러스터 하나를 여러 팀이나 사용자가 함께 공유
* 용도에 따라 실행해야 하는 앱을 구분할 때 사용

# kubectl 예시

## namespace 생성

```shell
$ kubectl create namespace
-blue API-POD.service.~
-orange API-POD.service.~
-green API-POD.service.~
```

위의 예시로 보았을 때,

blue는 배포, orange는 테스트, green은 개발용 클러스터로 구분하여 컨테이너 pod들을 분리하여 관리할 수 있다.


방법은 총 2가지가 존재한다.

* CLI
* yaml

### CLI

```shell
$ kubectl create namespace blue
$ kubectl get namespaces
```

### yaml

```shell
$ kubectl create namespace green --dry-run -o yaml > green-ns.yaml
$ vim green-ns.yaml
$ kubectl create -f green-ns.yaml
```

## namespace 사용

```shell
$ kubectl create deploy ui --image=nginx --namespace green
```

위의 명령어 뜻은

> 쿠버네티스야~ nginx 컨테이너를 green 공간에 실행해줘~

라는 뜻이다.

## namespace switch

### 쿠버네티스 context 만들기

```shell
$ kubectl config view
```

위 명령어는 컨텍스트를 살펴보는 것이다.

```shell
$ kubectl config set-context blue@kubernetes --cluster=kubernetes --user=kubernetes-admin --namespace=blue
```

위 명령어는 쿠버네티스 "blue" namespace에 blue@kubernetes라는 사용자를 admin 권한으로 생성하는 것이다.

### 쿠버네티스 context 바꾸기

```shell
$ kubectl config use-context blue@kubernetes
```

위 명령어는 현재 사용자를 "blue@kubernetes"로 교환하는 것이다.

### 쿠버네티스 namespace의 pod 삭제

```shell
kubectl delete pods mypod -n <namespace 이름>
```

위 명령어는 해당 namespace의 mypod를 삭제한다.

> namespace를 삭제하면 내부의 pod도 모두 삭제된다.







