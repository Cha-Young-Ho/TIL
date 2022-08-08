# 목차

* SELECT
* INSERT
* UPDATE
* DELETE
* GROUP BY
* HAVING


# SELECT

> SQL SELECT문은 하나 또는 그 이상의 테이블에서 데이터를 추출하는 SQL의 데이터 조작 언어(DML) 중 하나이다.

## 사용법

```sql
SELECT ${조회 대상 컬럼명} FROM ${조회하려는 테이블 명}
```
단일 컬럼명을 조회할 수 있다.
또는

```sql
SELECT [컬럼명1, 컬럼명2, ..] FROM ${조회하려는 테이블 명}
```

여러 컬럼명을 조회할 수 있다.
또는

```sql
SELECT * FROM &{조회하려는 테이블 명}
```

으로 모든 컬럼을 조회할 수 있다.


# INSERT
> INSERT 문은 테이블 또는 뷰에 한 개 이상의 행을 추가하는 SQL 데이터 조작 언어(DML) 중 하나이다.

## 사용법

```sql
INSERT INTO ${테이블 또는 뷰 이름} [컬럼1, 컬럼2, ...] VALUES [값1, 값2]
```

순서를 지켜야한다.

# UPDATE

> UPDATE문은 구조화 질의어 중 하나로, 테이블이나 뷰에서 한 개 이상의 행을 바꾼다.

## 사용법
```sql
UPDATE ${테이블 명} SET ${컬럼명}
```

# DELETE
> DELETE문은 구조 질의어 중 하나이다. 테이블 또는 뷰에서 한개 이상의 행을 삭제한다.

## 사용법

```sql
DELETE FROM ${테이블 또는 뷰 이름}
```

# GROUP BY
> GROUP BY는 같은 값을 가진 행끼리 하나의 그룹으로 뭉쳐주는 기능이다.

다음 그림을 살펴보자.


## 사용법

```sql
SELECT COUNT(*) FROM ${테이블 명} 

GROUP BY ${그룹화할 기준 컬럼}
```

이렇게 하면, 기준 칼럼을 전부 뭉치고 뭉쳐진 개수를 출력할 것이다.

# HAVING

> HAVING은 GROUP BY과 같이 집계된 결과에 조건을 걸 때 사용하는 기능이다.

❗️ where절과 매우 유사하지만 where절은 전체 개별 행에 대한 조건을 걸 때 사용한다.

특히나 where -> group by -> having 순서로 동작한다.

## 사용법

```sql
SELECT COUNT(*) FROM ${테이블 명} 

GROUP BY ${그룹화할 기준 컬럼}

HAVING ${조건}
```

# JOIN








