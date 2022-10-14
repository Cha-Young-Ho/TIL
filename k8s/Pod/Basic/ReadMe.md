# Pod란?

> 컨테이너를 표현하는 k8s api의 최소 단위이다.


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




