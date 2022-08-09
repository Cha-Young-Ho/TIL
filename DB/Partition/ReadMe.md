
# 목차
* Partition이란?
* 장단점
* 종류
* DDL

# Partition이란?

> 하나의 테이블에 존재하는 데이터를 물리적인 테이블로 나누어 관리하는것을 말한다.


# 장단점

## 장점

1. 특정 DML(Write)와 Query의 성능을 향상시킨다.

2. 물리적인 파티셔닝으로 인해 데이터 훼손 가능성이 줄어들고 데이터 가용성이 향상된다.

3. 분할 partition별로 백업과 복구를 할 수 있다.

4. table의 partition 단위로 Disk I/O을 분산하여 경합을 줄이기 때문에 UPDATE 성능을 향상시킨다.

5. 큰 Table들을 제거하여 관리를 쉽게 해준다.

## 단점

1. Table 간에 JOIN 비용이 증가한다.

2. table과 index를 별도로 파티셔닝할 수 없다.

# 종류

## 수평 분할

수평 분할은 하나의 테이블의 각 행을 다른 테이블에 분산시키는 것이다.

### 개념

* 샤딩과 동일한 개념이다.
* 스키마를 복제한 후 파티션 키(샤드 키)를 기준으로 데이터를 나눈다.
* 데이터의 수가 작아지고, 인덱스 개수도 작아져서 형능 향상으로 이어진다.

### 수평 파티셔닝 생성

파티셔닝은 주로 DDL문을 날릴 때 써넣는다.
```sql
CREATE TABLE Membe(
    id INT NOT NULL,
    nickname VARCHAR(20),
    age INT NOT NULL

    PARTITION BY RANGE (age))(
        PARTITION age1 VALUES LESS THAN (10),
        PARTITION age1 VALUES LESS THAN (30),
        PARTITION age1 VALUES LESS THAN (50),
        PARTITION age1 VALUES LESS THAN (70),
        PARTITION age1 VALUES LESS THAN MAXVALUE),
    )
)
```

###

## 수직 분할

수직 분할은 하나의 테이블에서 일부 열을 뺴내는 형태로 불할한다.

### 개념

* 스키마가 분할 된다. (하나의 엔티티를 2개 이상으로 분리하는 작업)
* 자주 사용하는 컬럼 위주로 데이터를 분리하여 성능을 향상 시킬 수 있다.
* SELECT 시 불필요한 컬럼까지 메모리에 올리지 않아서 한번에 읽어들일 수 있는 Row가 늘어난다.









