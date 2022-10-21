> 오늘은 카산드라로 클러스터를 구축해보자.

# 목차

* 카산드라 클러스터 방식
* 도커로 카산드라 준비하기
* 카산드라 데이터 넣어보기
* 데이터 확인하기

---

# 카산드라 클러스터 방식

카산드라는 아래의 그림과 같이 링 방식으로 클러스터를 구성한다.

![](https://velog.velcdn.com/images/jkijki12/post/4557ab21-687a-4881-9807-3a6fe4fe12a6/image.png)

각 링은 노드들로 구성된다.

> 새로운 row를 저장할 때, row를 임의의 hash token으로 변환하여 각 hash에 맞는 노드에 저장이 된다.

## 통신방식

각 노드들은 p2p 방식으로 통신을 한다.

> 카산드라는 기존에 존재하던 프라이머리 - 레플리카 구조를 사용하지 않는다.

카산드라는 p2p 방식으로 각 노드들은 프라이머리의 성격을 가지고있다. 그리하여 프라이머리- 레플리카 구조를 사용하면서 생길 수 있는 장애를 극복할 수 있다.

# 도커로 카산드라 준비하기

> 이번 글에서는 1개의 머신에서 3개의 노드를 운용해볼 것이다.

## Node1

```shell
docker run --name board-node1 
-p 7001:7001 -p 7002:7002 -p 9042:9042 -p 9160:9160 
-e CASSANDRA_CLUSTER_NAME=BoardCluster 
-e CASSANDRA_ENDPOINT_SNITCH=GossipingPropertyFileSnitch 
-e CASSANDRA_DC=datacenter1 
-d cassandra:latest
```

가장먼저 노드1을 준비하였다.

각 프로퍼티를 살펴보자.

* -p : 도커의 포트를 포트포워딩 하는 것이다.

* CASSANDRA_CLUSTER_NAME : 카산드라에 클러스터 이름을 명시하는 것이다.

* CASSANDRA_ENDPOINT_SNITCH : 데이터센터가 어떻게 구성되어있는지, 장비가 설치된 렉이 어떻게 나뉘어져 있는지에 대한 topology를 Cassandra에게 알려주기 위한 옵션이다. 종류는 매우매우 다양하다.

* CASSANDRA_DC : 카산드라 데이터센터 이름을 지정해주는 것이다.

* -d cassandra:latest : 카산드라 최신 버전의 이미지를 백그라운드로 실행하는 것이다.

실행하면 다음과 같이 결과값이 나온다.

![](https://velog.velcdn.com/images/jkijki12/post/b2737867-785a-4908-a355-c1ec8e2bfa4a/image.png)

노드 1개를 실행하였다.

## Node2, 3

다음 노드들은 Node1을 바라보게 만들 예정이다.

Node2

```shell
docker run 
--name board-node2 
-p 17001:7001 -p 17002:7002 -p 19042:9042 -p 19160:9160 
-e CASSANDRA_SEEDS="$(docker inspect --format='{{ .NetworkSettings.IPAddress}}' board-node1)" 
-e CASSANDRA_CLUSTER_NAME=BoardCluster
-e CASSANDRA_ENDPOINT_SNITCH=GossipingPropertyFileSnitch 
-e CASSANDRA_DC=datacenter1 
-d cassandra:latest
```

Node3
```shell
docker run 
--name board-node3 
-p 27001:7001 -p 27002:7002 -p 29042:9042 -p 29160:9160 
-e CASSANDRA_SEEDS="$(docker inspect --format='{{ .NetworkSettings.IPAddress}}' board-node1)" 
-e CASSANDRA_CLUSTER_NAME=BoardCluster 
-e CASSANDRA_ENDPOINT_SNITCH=GossipingPropertyFileSnitch 
-e CASSANDRA_DC=datacenter1 
-d cassandra:latest
```

두 노드를 생성하였다.
위의 명령어를 자세히 보면

**"-e CASSANDRA_SEEDS"** 라는 명령어를 볼 수 있다.

이것은 특정 node에게 등록하면 다른 모든 node에게 이를 알려주는 역할을 하도록하는 node이다.

# 카산드라 데이터 넣어보기

그럼 node1에 keyspace와 table을 지정하고 데이터를 넣어보자!

* node1 카산드라 cqlsh 접속

```shell
sudo docker exec -it board-node1 cqlsh
```

* keyspace 생성

```shell
CREATE KEYSPACE board with 
replication = 
{
'class': 'SimpleStrategy',
'replication_factor':3
};
```

위의 내용들은 keyspace의 분산화 전략에 대해서 입력해주는 것이다.

* table 생성

```shell
create table board.test(
  RollNo int,
  name text,
  dept text,
  primary key(Rollno)
  );
```

pk는 Rollno로 정했다.

> test용이라 대충 만든 것이다. roll이 왜 들어가나 싶을텐데... 참조 사이트 따라 그냥 만든거다.

* 데이터 넣기

```shell
insert into board.test
(
  rollno,
  name,
  dept
)
values
(
  1,
  'testName',
  'testDept'
);
```

데이터는 성공적으로 넣었다.

데이터를 확인해보자.

# 데이터 확인하기

연달아 명령어를 입력한다.

```shell
select * from board.test;
```

결과는 다음과 같다.

![](https://velog.velcdn.com/images/jkijki12/post/04e0fe74-2eb7-4422-a64b-809ce759ecf1/image.png)


그럼 다른 노드에서도 확인해보자.

node2

먼저 이전 노드에서 나가자.

```shell
exit
```

```shell
sudo docker exec -it board-node2 cqlsh
```

```shell
select * from board.test;
```

결과는 다음과 같다.

![](https://velog.velcdn.com/images/jkijki12/post/0475d1c0-0a3b-4faa-9685-58d1371a4bad/image.png)

맞다. node2에는 데이터를 넣어준 적이 없는데, node1과 동기화 처리를 한 것이다.

성공!

# 참고

* [https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=flowerdances&logNo=221213406546](https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=flowerdances&logNo=221213406546)


