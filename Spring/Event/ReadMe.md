# Event
> 오늘은 도메인 개발의 복잡성을 유발하는 의존성을 해결하는 방법 중 한 가지인 이벤트에 대해서 공부를 할 것이다.

# 목차
* Event란?

# 사전 지식

> 먼저 다음의 상황을 살펴보자.

```java
public void 댓글작성(String text){
    
    댓글등록(text));
    댓글이벤트();
    이벤트메일발송();
}
```

위의 내용을 살펴보면 댓글작성 로직에 댓글작성과 상관없는 메소드들이 존재한다. 위와같이 코드를 작성하면 여러가지 문제점이 존재한다.

## 문제점
1. 댓글 등록, 댓글 이벤트, 이벤트 메일 발송이라는 모든 로직이 성공해야 댓글 등록이 된다.

2. 댓글 작성이라는 로직은 세가지의 로직이 걸리는 시간만큼 걸린다.

3. 의존성이 생겨 유지보수에 어려움이 있다.

4. 세 가지의 로직 중에서 1가지라도 실패하면 모든 작업이 취소된다.

이러한 해결방법을 위해서 이벤트라는 소개한다.

# Event란?

> 컴퓨팅에서 이벤트란 프로그램에 의해 감지되고 처리될 수 있는 동작이나 사건을 말한다. 일반적으로 이벤트 기반 시스템은 프로그램에서 처리해야 할 비동기 외부 활동이 있을 때 사용된다.

위의 상황으로 빗대어 표현하면, "댓글 등록"이라는 이벤트가 발생하면 "첫댓글 이벤트", "이벤트 메일발송" 이벤트가 동기 또는 비동기로 실행되는 것이다.

# Event Handler

이벤트 핸들러는 프로그램 내부에서 입력을 받아 처리하는 일종의 콜백 서브루틴이다. 자바에서는 listener라고도 불린다.

# Spring Event

스프링에서는 다음의 순서로 이벤트를 다루게된다.

1. 생성 주체에서 이벤트를 발생하면 이벤트 디스패처에게 전달한다.
2. 이벤트 디스패처가 이벤트 핸들러(리스너)와 연결한다.
3. 이벤트 핸들러(리스너)가 전달받은 데이터를 통해서 구현해놓은 기능을 실행한다.

# Spring에서 사용하기

Spring에서 이벤트를 사용하기 위해선 다음의 의존성이 필요하다.

```gradle
implementation 'org.springframework.boot:spring-boot-starter-web'
```

## Spring에서 이벤트를 구현하는 순서

1. 이벤트 클래스 생성
2. 이벤트 발생 로직 구현
3. 이벤트 핸들러(리스너) 생성


하나씩 살펴보자.

### 이벤트 클래스 생성

```java
@Getter
public class repliedEvent{

    private final Reply reply;

    public repliedEvent(Reply reply){
        this.reply = reply;
    }
}
```

> 이벤트 클래스는 현재 기점으로 이전에 발생한 일이라서, 과거형으로 명명한다.

### 이벤트 발생 로직 구현

이제 이벤트를 발생(publish)시켜야한다.
```java
@Service
@RequiredArgumentConstructor
public class ReplyService{
    // private final ReplyEventService replyEventService;
    // private final EmailSender emailSender;
    private final ReplyRepository replyRepository;
    private final ApplicationEventPublisher eventPublisher;

    public Reply reply(String text, Member member){
        Reply replyEntity = Reply.Builder()
        .text(text)
        .member(member)
        .build();

        reployRepository.save(replyEntity);
        //replyEventService.check(replyEntity);
        //emailSender.send(replyEntity);

        evenPublisher.publishEvent(new RepliedEvent(repliedEvent));

        return replyEntity;
    }

}
```

ApplicationEventPublisher는 Bean으로 등록되어있기 때문에 주입받을 수 있다.

### 이벤트 핸들러(리스너) 작성

이벤트 핸들러를 구현하는 방법은 아주 쉽다.

해당 이벤트 인자를 가지는 메서드를 구현하고 상위에 "@EventListner"를 등록해주면 된다.

```java
@Component
public class ReplyEventHandler{

    @EventListener
    public void check(RepliedEvent repliedEvent){
        //이벤트 확인 및 적용 로직
    }

    @EventListener
    public void send(Reply reply){
        //이메일 보내기 로직
    }
     
}
```


이렇게 할 경우 완벽하게 구현되었다.

# 이벤트 고찰

위와 같이 작성하면 어떤 순서로 실행될까?

댓글 등록 -> 댓글 이벤트 -> 이메일 로직 순서로 돌아간다.

그럼 비동기로 처리를 할 수는 없을까?


## 이벤트 비동기 처리



### 1. @EnableAsync
이벤트를 비동기 처리를 사용할 수 있게 하기 위해서는 메인 application class에 "@EnableAsync"를 추가해주면 된다.

```java
@EnableAsync
@SpringBootApplication
public class EventApplication{
    public static void main(String[] args) {
    SpringApplication.run(EventApplication.class, args);
  }
}
```

### 2. @Async
"@EventListener"를 추가했던 메서드마다 "@Async"를 추가해주면 된다.

아까의 코드에서 "@Async"를 추가해보자.

```java
@Component
public class ReplyEventHandler{

    @Async
    @EventListener
    public void check(RepliedEvent repliedEvent){
        //이벤트 확인 및 적용 로직
    }

    @Async
    @EventListener
    public void send(Reply reply){
        //이메일 보내기 로직
    }
     
}
```

이제 비동기 처리가 된다!














