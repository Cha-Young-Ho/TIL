# Count

# 목차
* Count in JPA 및 optimize
* Count vs Exist

# Count in JPA

> JPA를 이용해서 Count를 구하는 여러가지 방법을 알아보자.

## List Size 얻기

사실상 가장 간편한 방법이다.
```java

public void getBoardsCount(){
    List<Board> boardList = boardRepository.finaAll();

    System.Out.Println("boards count = " + boardList.size());
}

```

위에 코드에서 살펴볼 수 있듯이, 모든 BoardList를 조회한 후 얻어낸 객체리스트의 사이즈를 알아보면 된다.

하지만 해당 방법은 너무 비효율적이다.

### 비효율적인 이유

> 우리가 알고싶은 건, 단순한 board의 개수이다.

board의 개수를 알고싶어서 모든 board의 내용을 가지고 온다. 메모리, I/O latency 등등에서 비효율적이다.

또한 다른 문제가 있다.

> boardList 내부에 존재하는 다른 매핑 객체가 존재하고, 해당 개수를 알고싶을 때 문제가 생긴다.

```java
    boardList.get(0).getMemberList().size();
```

위와 같이 작성하면 boardList를 가지고와서 getMemberList()가 호출되는 시점에 지연로딩(즉시로딩일 경우 x)이 이루어진다. 그렇게 되었을 경우에는 한번 더 Select가 날아가 너무나 비효율적이다.

SQL을 다뤄본 사람들은 알겠지만 Query에서 _**Count()**_ 메서드를 제공해준다. 이 방법을 이용해보자.

## JPQL 작성하기

그럼 직접 Query에 Count를 호출해서 Count값을 알아보자.

BoardRepository.java
```java
@Query("SELECT COUNT(*) FROM Room")
int getBoardsCount();
```

BoardSerbice.java
```java
int boardCount = boardRepository.getBoardsCount();
```

이렇게 작성하면 쿼리를 날리는 시점에서 count가 오기 때문에 추가적인 데이터를 메모리에 올릴 필요도 없다.

또한 query에 직접 where절도 삽입하여 member에 대한 count도 지연로딩없이 가지고 올 수 있다.


# 참고
* [✅ 유영모님 글](https://www.popit.kr/jpa-%EC%97%94%ED%84%B0%ED%8B%B0-%EC%B9%B4%EC%9A%B4%ED%8A%B8-%EC%84%B1%EB%8A%A5-%EA%B0%9C%EC%84%A0%ED%95%98%EA%B8%B0/)