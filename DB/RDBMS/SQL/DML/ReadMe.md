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
![image](https://user-images.githubusercontent.com/79268661/183381183-7fd40016-e3a5-4c98-8cc7-5a4d19822837.png)


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

> JOIN은 두 개의 테이블을 엮어서 원하는 데이터를 추출할 수 있는 기능이다.

JOIN은 4가지의 종류가 있다.

* INNER JOIN
* OUTER JOIN
* CROSS JOIN
* SELF JOIN


## INNER JOIN

INNER JOIN은 두 테이블을 조인할 때, 두 테이블에 모두 지정한 열의 데이터가 존재해야 한다.

가장 많이 사용되는 조인이다.

### 사용법
```sql
SELECT ${열 목록}

FROM ${1번 테이블} INNER JOIN ${2번 테이블}

ON ${조인 조건}

WHERE ${검색 조건}

```

![image](https://user-images.githubusercontent.com/79268661/183385976-676e0277-187e-4c9f-ac5e-4963aa538771.png)


## OUTER JOIN

OUTER JOIN은 두 테이블을 조인할 때, 1개에 테이블에만 데이터가 존재해도 결과를 알 수 있다.

```sql
SELECT ${열 목록}

FROM ${1번 테이블(LEFT)} ${LEFT | RIGHT | FULL} OUTER JOIN ${2번 테이블(RIGHT)}

ON ${조인 조건}

WHERE ${검색 조건}

```

* LEFT : 왼쪽 테이블의 모든 값이 출력
* RIGHT : 오른쪽 테이블의 모든 값이 출력
* FULL : 양쪽 테이블의 모든 값이 출력

![image](https://user-images.githubusercontent.com/79268661/183386024-487af20a-4253-4d48-880f-0acdec8e8e31.png)



## CROSS JOIN

CROSS JOIN은 한쪽 테이블의 모든 행과 다른 쪽 테이블의 모든 행을 조인하는 기능이다.

카티션 곱이라고도 불린다.

```sql
SELECT ${열 목록}

FROM ${1번 테이블} CROSS JOIN ${2번 테이블}

![image](https://user-images.githubusercontent.com/79268661/183386040-65c59f0f-93ff-456d-86bd-bda67128081d.png)
```


## SELF JOIN

SELF JOIN은 자기 자신과 JOIN을 진행한다는 의미이다. 1개의 테이블을 사용한다.

![image](https://user-images.githubusercontent.com/79268661/183386071-6bebbfd6-0810-493d-b326-6a75ca9b686e.png)


