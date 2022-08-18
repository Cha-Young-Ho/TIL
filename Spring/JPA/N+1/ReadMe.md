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

# Fetch Join - N+1 problem
> fetch는 지연 로딩이 걸려있는 연관관계에서 한번에 같이 즉시로딩을 하도록 도와주는 구문이다.

먼저 Fetch Join을 적용해보자.

```java
@Query("select distinct t from Team t left join fetch t.memberList")
    List<Team> findAllFetch();
```

이렇게 적용을 하고, 다음의 코드를 시행해보자.

```java
@Transactional
    public List<DtoOfGetTeams> getTeamsByFetch(){

        System.out.println("---------------------");
        List<Team> teamList = teamRepository.findAllFetch();
        System.out.println("---------------------");


        return teamList.stream()
                .map(v -> DtoOfGetTeams
                        .builder()
                        .memberList(v.memberList.stream()
                                .map(v2 -> DtoOfgetMember
                                        .builder()
                                        .age(v2.getAge())
                                        .name(v2.getName())
                                        .build())
                                .collect(Collectors.toList()))
                        .name(v.getName())
                        .build())
                .collect(Collectors.toList());
    }
```

이렇게 코드를 수행하면 다음과 같이 나온다.

```sql
---------------------
Hibernate: 
    select
        distinct team0_.id as id1_1_0_,
        memberlist1_.id as id1_0_1_,
        team0_.name as name2_1_0_,
        memberlist1_.age as age2_0_1_,
        memberlist1_.name as name3_0_1_,
        memberlist1_.team_id as team_id4_0_1_,
        memberlist1_.team_id as team_id4_0_0__,
        memberlist1_.id as id1_0_0__ 
    from
        team team0_ 
    left outer join
        member memberlist1_ 
            on team0_.id=memberlist1_.team_id
---------------------
```

모든 내용이 조회되면서 쿼리가 딱 1개만 나온다!

# @EntityGraph - N+1 problem
앞서 이야기한 Fetch Join은 쿼리를 직접 작성하게 되는 하드코딩 단점이 존재한다.
이를 방지하기 위해서는 **@EntityGraph**를 이용할 수 있다.

먼저 코드를 살펴보자.

```java
@EntityGraph(attributePaths = "memberList")
    @Query("select t from Team t")
    List<Team> findAllEntityGraph();
```

다음 코드를 시행하고 쿼리를 살펴보자.

```java
public List<DtoOfGetTeams> getTeamsByEntityGraph(){
        System.out.println("--------------------------------");
        List<Team> teamList = teamRepository.findAllEntityGraph();
        System.out.println("--------------------------------");

        return teamList.stream()
                .map(v -> DtoOfGetTeams
                        .builder()
                        .memberList(v.memberList.stream()
                                .map(v2 -> DtoOfgetMember
                                        .builder()
                                        .age(v2.getAge())
                                        .name(v2.getName())
                                        .build())
                                .collect(Collectors.toList()))
                        .name(v.getName())
                        .build())
                .collect(Collectors.toList());
    }
```

나온 쿼리는 다음과 같다.

```sql
--------------------------------
Hibernate: 
    select
        team0_.id as id1_1_0_,
        memberlist1_.id as id1_0_1_,
        team0_.name as name2_1_0_,
        memberlist1_.age as age2_0_1_,
        memberlist1_.name as name3_0_1_,
        memberlist1_.team_id as team_id4_0_1_,
        memberlist1_.team_id as team_id4_0_0__,
        memberlist1_.id as id1_0_0__ 
    from
        team team0_ 
    left outer join
        member memberlist1_ 
            on team0_.id=memberlist1_.team_id
--------------------------------
```

똑같이 모든 내용이 조회되고, 쿼리는 딱 1개만 나온다!

# ❗️ Pagination 해결하기
위의 두 가지 방법으로는 Pagination 기능을 할 수 없다.

자세히 알아보자면
> fetch join에서 distinct를 쓰는 것은 1개의 연관관계에서 fetch join으로 가져오면 중복된 데이터가 너무나 많아서 실제로 원하는 데이터의 양보다 더 많은 데이터가 나온다.

> 그래서 개발자는 distinct를 직접 추가하여 중복 처리를 지시한다. 또한 paging 처리를 fetch에서 진행해도 데이터가 중복이 될 수 있기 때문에 jpa는 limit, offset을 따로 걸지 않고 인메모리에 일단 다 가지고 온다.

# @BatchSize - Pagination Problem

먼저 BatchSize가 무엇인지 알자.

> BatchSize는 지정해준 크기만큼 모든 값들을 즉시 로딩과 같이 조회를 하도록 도와주는 것이다.

먼저 코드를 살펴보자.

```java
@BatchSize(size = 100)
    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    List<Member> memberList = new ArrayList<>();
```

BatchSize를 걸어주고, DAO에 새로운 메소드를 정의해보자.

```java
Page<Team> findAll(Pageable pageable);
```

그리고 다음의 코드를 실행해보자.

```java
 public List<DtoOfGetTeams> getTeamsByPageble(Pageable pageable){
        System.out.println("--------------------------------");
        Page<Team> teamList = teamRepository.findAll(pageable);
        System.out.println("--------------------------------");

        return teamList.stream()
                .map(v -> DtoOfGetTeams
                        .builder()
                        .memberList(v.memberList.stream()
                                .map(v2 -> DtoOfgetMember
                                        .builder()
                                        .age(v2.getAge())
                                        .name(v2.getName())
                                        .build())
                                .collect(Collectors.toList()))
                        .name(v.getName())
                        .build())
                .collect(Collectors.toList());
    }
```

쿼리를 살펴보면 다음과 같다.

```sql
--------------------------------
Hibernate: 
    select
        team0_.id as id1_1_,
        team0_.name as name2_1_ 
    from
        team team0_ limit ?
--------------------------------
Hibernate: 
    select
        memberlist0_.team_id as team_id4_0_1_,
        memberlist0_.id as id1_0_1_,
        memberlist0_.id as id1_0_0_,
        memberlist0_.age as age2_0_0_,
        memberlist0_.name as name3_0_0_,
        memberlist0_.team_id as team_id4_0_0_ 
    from
        member memberlist0_ 
    where
        memberlist0_.team_id in (
            ?, ?, ?
        )
```

이전과는 다르게 findAll의 쿼리 단 1개만 나가는게 아니다.

추가적으로 1개가 더 생겼다.

이유는 다음과 같다.

> team을 조회하고 메서드에서 필요한 memberList에 접근을 해야한다. 그래서 teamList에 존재하는 모든 id를 이용하여 모든 member를 한번에 조회한다.

member를 조회하는 내용은 다음을 보면 안다.

```sql
    where
        memberlist0_.team_id in (
            ?, ?, ?
        )
```

in 구문을 통해서 정해진 member를 모두 가지고 오는 것을 볼 수 있다.

# @Fetch(FetchMode.SUBSELECT) - Pagination Problem
BatchSize는 개수를 사전에 제한해두었기 때문에 변경할 수없는 애매함이 존재한다.
이 어노테이션은 그런 문제를 해결해준다.

> 이 방법은 비효율적이라고 판단해서 따로 설명하지 않겠습니다.

이 방법은 @BatchSize(size = 무한) 이라고 생각만 하자!

# List -> Set - 2개 이상의 조인 problem
2개 이상의 조인이 필요한 경우는 너무나 많은 데이터를 인메모리에 가지고 오기 때문에 문제가 생긴다고 했다.

그러면 중복을 방지하기 위해서 자료형을 Set으로 바꾸는 방법이 있다.

## ❗️ 주의
* Set은 순서를 보장하지 않기 때문에 데이터의 순서를 보장해야한다면, LinkedHashSet을 이용해야 한다.
* 조금 더 고도화된 자료구조 손해가 생길 수 있다.
* 해당 방법으로 Pagination은 처리할 수 없다.

# @BatchSize - 2개 이상의 조인 problem
이 방법은 다음의 상황 2가지에서 사용하면 좋다.
* List를 사용해야하는 경우
* 2가지 이상의 join과 Pagination을 사용해야하는 경우



# 참고

* [https://velog.io/@jinyoungchoi95/JPA-%EB%AA%A8%EB%93%A0-N1-%EB%B0%9C%EC%83%9D-%EC%BC%80%EC%9D%B4%EC%8A%A4%EA%B3%BC-%ED%95%B4%EA%B2%B0%EC%B1%85](https://velog.io/@jinyoungchoi95/JPA-%EB%AA%A8%EB%93%A0-N1-%EB%B0%9C%EC%83%9D-%EC%BC%80%EC%9D%B4%EC%8A%A4%EA%B3%BC-%ED%95%B4%EA%B2%B0%EC%B1%85)

* [https://jojoldu.tistory.com/457](https://jojoldu.tistory.com/457)










