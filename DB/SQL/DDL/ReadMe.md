# 목차

* SELECT
* INSERT
* UPDATE
* DELETE

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




