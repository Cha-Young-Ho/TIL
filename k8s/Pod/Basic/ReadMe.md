# Pod란?

> 컨테이너를 표현하는 k8s api의 최소 단위이다.

<img width="681" alt="스크린샷 2022-10-14 오후 6 05 45" src="https://user-images.githubusercontent.com/79268661/195815958-abf0349b-77bc-4e55-b3bc-47b9a8cfafab.png">

pod는 위와같이 1개의 컨테이너 혹은 1개 이상의 컨테이너를 포함할 수 있다.

# Pod 생성하기

* [✅ CLI](https://github.com/Cha-Young-Ho/TIL/tree/main/k8s/Namespace)
* [✅ yaml](https://github.com/Cha-Young-Ho/TIL/tree/main/k8s/Namespace)

# Multiple Container Pod 생성하기

* yaml

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

# Multiple Container Pod 실행하기

* yaml

```shell
kubectl create -f pod-multi.yaml
```

# Pod의 Container 진입하기

```shell
kubectl exec <pod name> -c <container name> -it -- /bin/bash
```

위와 같이 명령어를 입력하면 해당 pod의 해당 container에 접속한다.




