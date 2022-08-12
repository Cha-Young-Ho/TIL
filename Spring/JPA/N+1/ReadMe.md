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

## Fetch Join



