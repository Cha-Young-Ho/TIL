# 목차

* N+1 문제란?
* 해결방법

# N+1 문제란?

다음과 같은 코드가 있다.

Member.java

```java
public class Member {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "KAKAO_ID")
    private Long kakaoId;

    @Column(name = "GENDER")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "NICKNAME")
    private String nickname;


    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Location> locations;

```

Location.java

```java
@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String locationName;

    @Column(name = "LATITUDE")
    private double latitude;

    @Column(name = "LONGITUDE")
    private double longitude;

    @Column(name = "REGION")
    private String region;

    @Column(name = "CITY")
    private String city;

    @Column(name = "TOWN")
    private String town;

    @Column(name = "DETAIL_LOCATION")
    private String detailLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private Member member;

}
```

> Member 객체와 Location 객체는 1:N 관계로 매핑되어있다.

다음의 상황을 보자.

## N + 1 문제가 생길 수 있는 상황

```java

@RequiredArgumentStructure
public class MemberService{

    @Autowired
    private MemberRepository memberRepository;


    public List<Location> findAllLocation(){
        return getLocation(memberRepository.findAll());
    }
    public List<Location> getLocation(List<Member> memberList){
        return memberList.stream()
                .map(v -> v.getLocation())
                .collect(Collectors.toList());
    }
}

```

여기서 findAllLocation 메소드를 호출하게되면 멤버 1명 당 가지고 있는 Location의 개수만큼 select가 발생한다.

멤버가 100명있고, 각각 Location을 10개씩 가지고 있다면

총 조회 쿼리는 100 * 10개가 되어 1000개의 DB 조회가 수행된다.

이 문제가 바로 N + 1 문제이다.

# 해결방법

위의 두 가지 방법이 있다. 먼저 어떠한 것이 존재하는지부터 살펴보자.

> 먼저 N+1을 해결할 수 있는 방법을 알아보자.

* Fetch Join
* EntityGraph

# 또 다른 문제
N+1의 문제를 해결하고나니 또다른 문제들이 발생한다.

> 또 다른 문제가 어떤것이 존재하는지 살펴보고 각각 해결법부터 보자.

## Pagination 문제
위에서 언급되었던 _**"Fetch join"**_과 _**"EntityGraph"**_을 사용하면 Pagination 처리가 불가능하다.

이유는 다음과 같다.
> Fetch나 EntityGraph를 사용하면 JPA는 일단 모든 List 값을 _**SELECT**_ 해서 인메모리에 저장한다. 그리고 application 단에서 필요한 페이지 만큼 반환을 알아서 해준다.

이렇게 될 경우는 사실상.. Pagination의 의미가 사라진다.
페이지 별로 받아서 데이터 효율적으로 관리를 하려는 것이 목적인데 모든 내용을 받아오니 비효율적이다.

그래서 Out of Memory 문제가 생길 수 있다.

<span style="color: red">사실상 Fetch로 pagination을 해결할 수 없다.</span>

### 해결 방법
> 해결방법을 먼저 알아보자면, 다음과 같다.

<span style="color: #2D3748; bacground-color:#fff5b1;">@BatchSize</span>
<span style="color: #2D3748; bacground-color:#fff5b1;">@Fetch(FetchMode.SUBSELECT)</span>

좀 있다 알아보자.

## 2가지 이상의 Join 문제
위에서 언급되었던 _**"Fetch join"**_과 _**"EntityGraph"**_을 사용하면 두 가지 이상의 Join이 필요할 때 한방 쿼리가 안된다.

2가지 이상의 Join을 진행하면 _**MultipleBagFetchException**_ 예외가 발생한다.

이러한 예외가 발생하는 이유는 다음과 같다.

> fetch join은 하나의 collection fetch join에 대해서 인메모리에 모든 값을 저장하고 모든 값을 다 가져오기 때문에 2개 이상이 될 경우 너무나 많은 값이 메모리로 들어오기 때문에 MultipleBagFetchException 예외가 추가로 발생한다.

### 해결 방법
> 해결방법은 다음과 같다.

<span style="color: #2D3748; bacground-color:#fff5b1;">List -> Set</span>
<span style="color: #2D3748; bacground-color:#fff5b1;">@BatchSize</span>

## Fetch Join



