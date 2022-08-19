# GroupBy

# 목차
* GroupBy란?
* GroupBy in JPA
* GroupBy + Etc

# GroupBy란?

> 먼저 GroupBy에 대해서 알고가자.

이 글은 JPA를 통해서 GroupBy를 어떻게 사용하는지를 다루는 글이라서 아주 간단하게만 알아볼 것이다!

> Group By는 컬럼의 데이터를 특정 기준으로 그룹화하여 조회하도록 도와주는 sql 명령어이다.

```sql
SELECT <컬럼> FROM <테이블> GROUP BY <특정 기준 컬럼>;
```

위와 같이 명령어를 입력한다.

## 실제 조회되는 테이블

테이블이 다음과 같이 존재한다.
|id|지역|이름|
|:----:|:----:|:----:|
|1|경남|김길동|
|2|서울|이순신|
|3|경남|최둘리|
|4|경북|유짱구|

해당 내용을 그룹화 해보자.

```sql
SELECT count(id) AS count FROM USER_TABLE GROUP BY 지역;
```

결과는 다음과 같이 나온다.

|지역|count|
|:---:|:---:|
|경남|2|
|서울|1|
|경북|1|

# GroupBy in JPA
그럼 위와같은 GROUP BY를 JPA를 이용하면 어떻게 사용해야할까?

방법은 여러가지가 있다.
## 방법
1. JPA 조회 후, stream을 이용하여 그룹화
2. JPQL 생성
3. Native Query 생성
4. QueryDSL 사용

여기서 1번과 3번은 따로 다루지 않을 예정이다.

## JPQL 생성

가장 간단한 방법이 아닐까 생각이 든다.

쿼리를 개발자가 직접 생성하여 조회하는 방법이다.

다음 코드를 보자.

BoardRepository.java

```java
@Query("SELECT Count(id) FROM Board b GROUP BY b.address")
List<DtoOfGroupByAddress> getCountgroupByAddress();
```

이렇게 하면 JPA는 JPQL을 통해서 조회하고, Projection을 통해서 DTO에 결과 값을 전달한다.

## QueryDsl 사용
queryDsl은 group by를 할 수 있는 메소드를 제공한다.

다음 코드를 보자.

```java
public QueryResults<DtoOfGroupByAddress> getCountByQueryDsl(){
    //... 중략 ...
    QBoard qboard = Qboard.board;
    
    QueryResults<DtoOfGroupByAddress> countList =
            qboard.from(qboard)
            .groupBy(qboard.address)
            .select(
                qboard.address,
                qboard.id.count()
            ).fetchResults();
    
    return countList;
}
```


# 참고
* [✅ https://extbrain.tistory.com/56](https://extbrain.tistory.com/56)