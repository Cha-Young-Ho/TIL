# MySQL 내장함수

### IF

```sql
SELECT IF(1 > 3, 'true', 'false');
```

조건식, 참일 경우 값, 거짓일 경우 값

### IFNULL

```sql
SELECT IFNULL(NULL, 200);
```

IFNULL(수식1, 수식2)

> 수식1이 NULL일 경우 200이 리턴, NULL이 아닐 경우 수식1이 리턴

```sql
SELECT IFNULL(100, 200); 
```

> 위의 식은 100이 리턴

### CASE WHEN THEN END

```sql
SELECT CASE 10
    WHEN 1 THEN 'a'
    WHEN 5 THEN 'e'
    WHEN 10 THEN 'j'
    ELSE '?'
END;
```

switch 문과 흡사하다.

> CASE를 응용하면 아래와 같이 사용할 수 있다.

```sql
explain SELECT
    CAR_ID,
    CASE 
        WHEN 
            TIMESTAMPDIFF(DAY, START_DATE, END_DATE) + 1 >= 30 THEN '장기 대여'
        ELSE 
            '단기 대여' 
    END AS RENT_TYPE
FROM
    CAR_RENTAL_COMPANY_RENTAL_HISTORY
WHERE
    START_DATE >= '2022-09-01'
AND
    START_DATE < '2022-10-01'
ORDER BY
    HISTORY_ID DESC
```

위와 같이 사용하면 리턴되는 컬럼은

> CAR_ID, RENT_TYPE

이 리턴된다.


### FORMAT

```sql
SELECT FORMAT(123.1234, 2); 
```

FORMAT(숫자, 소숫점 자릿수)로 표현된다.

위의 결과는 "123.12"이다.

### ADDDATE

```sql
SELECT ADDDATE('2020-01-01', INTERVAL 31 DAY),
```
ADDDATE(날짜, 차이)로 표현된다.

위의 결과는 31일 뒤의 결과가 나온다.

### SUBDATE

```sql
SELECT SUBDATE('2020-01-01', INTERVAL 31 DAY);
```
SUBDATE(날짜, 차이)로 표현된다.
위의 결과는 31일 전의 결과가 나온다.

### TIMEDIFF

```sql
SELECT DATEDIFF('2020-1-5', '2020-1-1');
```
DATEDIFF(날짜1, 날짜2)로 표현된다.
두 날짜의 차이 날짜가 리턴된다.
```sql
 
SELECT TIMEDIFF('14:30:00', '06:30:00');
```
TIMEDIFF(날짜1 OR 시간1, 날짜2 OR 시간2)
두 날짜 및 시간의 차이가 리턴된다.



        




