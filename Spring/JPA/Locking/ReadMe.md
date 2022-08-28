# Locking

> 오늘은 JPA의 트랜잭션 내용인 낙관적 락, 비관적 락에 대해서 공부를 할 것이다. 그리고 JPA에서는 어떤식으로 지원히주는지 알아볼 것이다.

# 목차

* 락이란?
* 낙관적 락이란?
* 비관적 락이란?
* JPA에서의 락

# 락(Lock)이란?
락이라는 개념은 운영체제, 데이터베이스 및 기타 다른곳에서 자주 사용되는 말이다.

여기서는 DB를 기준으로 기록을 해보자 한다.

## 락이 필요한 상황

1. 게시 글이 존재하고, 게시글은 0이라는 조회수를 가지고 있다.
2. A라는 사람과 B라는 사람이 동식에 게시글을 조회한다.
3. A라는 사람이 조회했을 때, DB에 저장되어있는 게시글 조회수를 조회한다. (조회수 0)
4. B라는 사람이 동시에 조회해서, DB에 저장되어있는 게시글 조회수를 조회한다.(조회수 0)
5. A라는 사람이 조회해서 조회수는 가지고온 값 0의 +1인 1의 값이 된다. (update를 하지 않음)
6. B라는 사람이 조회해서 조회수는 가지고온 값 0의 +1인 1의 값이 된다.
7. A 쓰레드에서 DB를 1로 Update한다.
8. B 쓰레드에서 DB를 1로 Update한다.

> 위의 상황을 보면 게시글은 총 2번 조회가 되었다. 근데 조회수는 1이다.

이 때 필요한 것이 락(lock)이다.

## 락이란?
앞서 이야기한 상황을 방지하기 위해서, A라는 사람이 게시 글 조회수에 접근하고 있는 상황(Update까지 끝내기 전)에서는 B라는 사람이 게시 글 조회수에 접근하지 못하도록 막는 것이다.

A라는 사람이 조회수에 접근할 때, 문을 걸어잠갔다고하여 "락"이라고 표현한다.

락에는 종류가 2가지가 존재한다.

1. 낙관적 락
2. 비관적 락

락에 대해서 살펴보자.

# 낙관적 락(Optimistic Lock)이란?
> 데이터 갱신 시, 총돌이 발생하지 않을 것이라는 가정을 두고 진행하는 락 기법이다.

걸어잠그어 접근을 못하게 하기 보다는 충돌을 방지하기 위한 방법이다.

# 비관적 락(Pessimistic Lock)이란?
> 데이터 갱신 시, 충돌이 발생할 것이라는 가정을 두고 진행하는 락 기법이다.

걸어잠그어 접근을 못하게 하는 것이 핵심이다.

# JPA에서의 락
JPA에서는 락에 대해서 많은 지원이 된다. 하나씩 살펴보자.

## JPA 낙관적 락
JPA는 낙관적 락을 "Version"을 이용하여 제공한다.

> Version이라는 속성을 이용하여 접근 자체에 Version을 체크하여 충돌이 되었는지 확인할 수 있다.

다음 그림을 살펴보자.

![image](https://user-images.githubusercontent.com/79268661/187074273-199fd370-a6cd-43e5-a08d-b98b357e47ef.png)


1. Alice의 product 접근은 version 1로 접근되었다.
2. Batch job 접근은 version 1로 접근되었다.
3. Batch job 접근이 Update를 하여 version을 2로 변경하였다.
4. Alice 가 PUT 요청으로 데이터를 변경했지만 version은 1이다.
5. Alice가 요청한 변경은 적용되지 않앗고, "OptimisticLockingException"이 발생했다.

JPA에서는 아주 쉽게 어노테이션을 추가하는 것만으로 "version" 메커니즘을 사용할 수 있다.

> 엔티티에 int, Long 변수를 추가하고 "@Version"을 명시해주면 끝

```java
public Class Entity{
    private Long id;
    private String nickname;

    @Version
    private Long version;
}
```
이렇게 해주면 JPA에서 알아서 Versioning을 지원해준다.

조회 시점과 수정 시점이 다르게 되면 _**ObjectOptimisticLockingFailureException**_이 발생한다.

## 낙관적 락 종류
이러한 낙관적 락에는 여러가지 LockModeType 종류가 있다.

### OPTIMISTIC

* 트랜잭션 시작 시 버전 점검이 수행된다.
* 트랜잭션 종료 시 버전 점검이 수행된다.
* 버전이 다르면 롤백된다.

### OPTIMISTIC_FORCE_INCREMENT
* 버전을 강제로 증가시킨다.
* 매핑되어있는 다른 테이블이 수정되면 버전이 변경된다.

### READ
* OPTIMISTIC 과 동일

### WRITE
* OPTIMISTIC_FORCE_INCREMENT 와 동일

### NONE
* "@Version"이 존재하면 낙관적 락을 사용한다.

## JpaRepository를 이용한 락 종류 적용
다음과 같이 JpaRepository에는 락 종류를 적용할 수 있다.

```java
@Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
Optional<Member> findByIdForUpdate(Long id);
```

### 낙관적 락 고찰

* 낙관적 락과 같은 경우는 1000개의 요청이 존재한다면, 가장 처음의 1개는 적용되고 나머지 999개는 버전 변경이 맞지않아서 롤백된다. 그만큼 자원 소모가 발생한다.

* 모든 작업이 수행되고, commit 하는 시점에 충돌 여부를 알 수 있기 때문에 느리게 될 경우가 존재한다.

## JPA 비관적 락
JPA는 "@Lock" 어노테이션을 통해 비관적 락을 지원한다.

```java
@Lock(LockModeType.PESSIMISTIC_WRITE)
Optional<Member> findByIdForUpdate(Long id);
```

이렇게 할 경우 해당하는 메서드를 사용할 땐, 비관적 락이 진행된다.

## 비관적 락 종류

JPA는 비관적 락에 대해서 여러가지 종류를 지원한다.

### PESIMISTIC_READ
* 다른 트랜잭션에서 읽기만 가능

### PESSIMISTIC_WRITE
* 다른 트랜잭션에서 읽기 및 쓰기 불가능

### PESSIMISTIC_FORCE_INCREMENT
* 다른 트랜잭션에서 읽기 불가능
* 다른 트랜잭션에서 쓰기 불가능
* 버저닝 기능 수행

## 비관적 락 고찰

* 레코드 자체에 락을 걸기 때문에 줄을 서야한다. 그만큼 병목현상이 생길 수 있다.




