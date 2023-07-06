# 스프링 핵심 원리 - 기본편

## 목표

> 스프링의 역사를 알아본다. 또한, 스프링의 장점인 객체 지향 프로그래밍과 SOLID 5원칙을 예제 코드를 통해 학습한다.

<br>

### SOLID 5원칙

***SRP 단일 책임 원칙(Single responsivility principle)***

- 한 클래스는 하나의 책임만 가진다.
- 하나의 책임이라는 것은 모호하다.
    - 클 수도 있고 작을 수도 있다.
    - 문맥과 상황에 따라 다르다.
- 중요한 기준은 변경이다. 변경이 있을 때 파급효과가 적으면 단일 책임 원칙을 잘 따른 것
- 예) UI변경, 객체의 생성과 사용을 분리
    
    

***OCP 개방-폐쇄 원칙***

***Open/closed principle***

- 소프트웨어 요소는 확장에는 열려 있으나 변경에는 닫혀 있어야 한다.
- 다형성을 활용해보자
- 인터페이스를 구현한 새로운 클래스를 하나 만들어서 새로운 기능을 구현
- 역할과 구현의 분리를 생각해보자

문제점

- MemberService 클라이언트가 구현 클래스를 직접 선택

```java
MemberRepository m = new MemoryMemberRepository();
MemberRepository m = new JdbcMemberRepository();
```

- 구현 객체를 변경하려면 클라이언트 코드를 변경해야 한다.
- 객체를 생성하고 연관관계를 맺어주는 별도의 조립, 설정자가 필요하다.

***LSP 리스코프 치환 원칙 Liskov substitution principle***

- 프로그램의 객체는 프로그램의 정확성을 깨뜨리지 않고 하위 타입의 인스턴스로 바꿀 수 있어야 한다.
- 다형성에서 하위 클래스는 인터페이스 규약을 다 지켜야 한다는 것.
- 다형성을 지원하기 위한 원칙으로 인터페이스를 구현한 구현체를 믿고 사용하기 위한 원칙.
- 단순히 컴파일에 성공하는 것을 넘어서는 이야기

***ISP 인터페이스 분리 원칙 Interface segregation principle***

- 특정 클라이언트를 위한 인터페이스 여러 개가 범용 인터페이스 하나보다 낫다.
- 자동차 인터페이스 → 운전 인터페이스, 정비 인터페이스로 분리
- 사용자 클라이언트 → 운전자 클라이언트, 정비사 클라이언트로 분리
- 분리하면 정비 인터페이스 자체가 변해도 운전자 클라이언트에 영향을 주지 않음
- 인터페이스가 명확해지고 대체 가능성이 높아진다.

***DIP 의존관계 역전 원칙 Dependency inversion principle*** 

- 추상화에 의존하고 구체화에 의존하면 안된다.
- 구현 클래스가 아닌 인터페이스 의존하라.
- 역할에 의존하게 해야 한다.

정리

- 객체 지향의 핵심은 다형성
- 다형성만으로는 쉽게 부품을 갈아 끼우듯 개발할 수 없다.
- 다형성만으로는 구현 객체를 변경할 때 클라이언트 코드도 함께 변경된다.
- 다형성만으로는 OCP, DIP를 지킬 수 없다.
- 뭔가 더 필요하다.
- **객체 지향 설계와 스프링**
    
    객체 지향 이야기가 나오는 이유
    
    - 스프링은 다형성 + OCP, DIP 가능하도록 지원
        - DI: 의존 관계, 의존성 주입
        - DI 컨테이너 제공
    - 클라이언트 코드의 변경 없이 기능 확장
    - 모듈화로 개발
 
- **정리**
    - 모든 설계에 역할과 구현을 분리하자
    - 자동차, 공연의 예를 떠올려보자.
    - 어플리케이션 설계도 공연을 설계 하듯 배역만 만들고 배우는 언제는 유연히 변경할 수 있도록 만드는 것이 좋은 객체 지향 설계이다.
    - 이상적으로는 모든 설계에 인터페이스를 부여하자.

<br>

### 스프링 핵심 원리 이해 (1)
- 개발 요구 사항
  - 회원
    - 회원은 아이디, 이름, 등급을 가진다.
    - 등급은 일반회원, vip로 구분된다.
    - 회원가입, 조회 기능을 가진다.
  
    <br>

  - 주문과 할인 정책
    - 주문과 할인 정책
    - 회원은 상품을 주문할 수 있다.
    - 회원 등급에 따라 할인 정책을 적용할 수 있다.
    - 할인 정책은 모든 VIP는 1000원을 할인해주는 고정 금액 할인을 적용하라 (나중에 변경될 수 있다.)
    - 할인 정책은 변경 가능성이 높다. 회사의 기본 할인 정책을 아직 정하지 못했고 오픈 직전까지 고민을 미루고 싶다. 최악의 경우 할인 정책을 적용하지 않을 수도 있다. (미확정)


- 회원 도메인 설계 및 기능 개발
1. 회원 도메인, 등급 enum 생성
2. 회원 Repository Interface 생성 - DB정책이 정해지지 않았으므로
3. 회원 Repository 구현
4. 회원 Service Interface 생성 - 회원가입, 조회 기능
5. 회원 Service 구현
6. test코드 작성

```agsl
// ServiceImpl.java

// 구현체와 인터페이스, 둘 모두에게 의존하고 있다. DIP 위반
// 구현체를 갈아끼우려면 코드를 수정해야 한다. OCP 위반
private final MemberRepository memberRepository = new MemoryMemberRepository();
```

<br>

- 주문과 할인 도메인 설계 및 기능 개발

1. 주문 도메인 생성
2. 할인 인터페이스 생성
3. 할인 구현체 생성
4. 주문 Service 인터페이스 생성 - 회원 등급 별 할인금액 산출
5. 주문 Service 구현체 생성
6. test 코드 작성

<br>

### IoC와 DI

	IoC: 개발자가 구현하는 코드의 흐름은 객체를 생성하고 가져다 쓰며 로직을 실행한다고 하면 IoC를 적용한 코드는 런타임 시점에 외부에서 객체를 생성하고 연결해줌으로써
	클라이언트와 서버의 의존 관계를 맺는 것을 의미한다. 제어의 흐름을 개발자가 가져가는 것은 라이브러리이고 그렇지 않다면 프레임워크라고 할 수 있다.

	DI: 인터페이스와 의존 관계를 맺고 있는 것. 그리고 IoC를 통해 의존 관계를 주입 받는 것을 DI라고 한다.  

<br>

## Section5 싱글톤 컨테이너

<b>싱글톤 컨테이너 적용 전</b>
- 웹 어플리케이션은 보통 여러 고객이 동시에 요청한다.
- 스프링 없는 순수한 DI 컨테이너는 AppConfig에 요청할 때마다 객체를 새로 생성한다.
- 고객 트래픽이 초당 100개라면 100개 객체가 생성되고 소멸 된다. → 메모리 낭비
- 해당 객체는 1개이고 공유 되도록 설계하자. → 싱글톤 패턴

![image](https://github.com/proamateur92/spring-core-basic/assets/68406448/ea6f4189-5ee9-4559-b9d2-1b90b941391d)

<br>

<b>싱글톤 컨테이너 적용 후</b>

- 스프링 컨테이너는 객체 인스턴스를 싱글톤으로 관리한다.
- 스프링 컨테이너는 싱글톤 컨테이너 역할을 한다. 싱글톤 객체를 생성하고 관리하는 기능을 싱글톤 레지스트리라고 한다.
- 스프링 컨테이너 덕분에 싱글톤 패턴의 모든 단점을 해결하며 객체를 싱글톤으로 유지할 수 있다.
    - 싱글톤 패턴을 위한 코드가 필요하지 않아진다.
    - DIP, OCP, 테스트, private 생성자로부터 자유롭게 싱글톤을 사용할 수 있다.

- 스프링 컨테이너 덕분에 고객 요청이 들어올 때마다 객체를 생성하는 것이 아니라 이미 만들어진 객체를 공유해 효율적으로 재사용이 가능하다.
  
![image](https://github.com/proamateur92/spring-core-basic/assets/68406448/86b51f39-0c77-482a-95f8-03252753dd66)

<br>

### 싱글톤 패턴의 주의점

- 싱글톤 패턴, 싱글톤 컨테이너처럼 객체 인스턴스를 하나만 생성해 공유하는 방식은 여러 클라이언트가 하나의 같은 객체 인스턴스를 공유하기 때문에 상태를 유지(stateful)하게 설계하면 안된다.
- 무상태(stateless)로 설계해야 한다.
    - 특정 클라이언트에 의존적인 필드가 있으면 안된다.
    - 특정 클라이언트가 값을 변경할 수 있는 필드 가 있으면 안된다.
    - 가급적 읽기만 가능해야 한다.
    - 필드 대신에 자바에서 공유되지 않는 지역변수, 파라미터, ThreadLocal등을 사용해야 한다.
- 스프링 빈의 필드에 공유 값을 설정하면 큰 장애가 발생할 수 있다.

<br>

### @Configuration과 싱글톤

	@Configuration 이하의 @Bean들은 싱글톤으로 스프링 컨테이너에 등록된다.
 	이미 빈 저장소에 등록된 객체라면 더 이상 등록하지 않고 꺼내 씀으로서 싱글톤이 유지된다.
  	따라서 MemberRepository를 생성자 호출로 가져다 쓰는 MemberServiceImpl이나 OrderServiceImpl 그리고 MemberRepository 클래스 모두 객체의 주소값이 일치함을 확인할 수 있다.

```
@Configuration
public class AppConfig {
    // 모두 같은 memberRepository를 사용하고 있다.
    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public MemberRepository memberRepository() {
	return new MemoryMemberRepository();
    }
    ...
}
```

## Section6 컴포넌트 스캔

<details>
<summary>컴포넌트 스캔과 의존관계 자동 주입 시작하기</summary>
<div markdown="1">

<br>

- 스프링은 설정 정보가 없어도 자동으로 스프링 빈을 등록하는 컴포넌트 스캔 기능을 제공한다.
- 의존관계도 자동으로 주입되는 `@Autowired` 기능도 제공한다.
- `@ComponentScan` 를 붙여 사용하고 `@Component` 가 붙은 클래스를 스프링 빈으로 등록한다.
- `@Autowired`를 붙여 의존 관계를 설정한다. (생성자)

<br>

```
// 빈으로 등록했던 구현체 클래스들에게 @Component를 붙여준다.

// RateDiscountPolicy.java 
@Component
public class RateDiscountPolicy implements DiscountPolicy

// MemoryMemberRepository.java
@Component
public class MemoryMemberRepository implements MemberRepository

// MemberServiceImpl.java
@Component
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

// OrderServiceImpl.java 
@Component
public class OrderServiceImpl implements OrderService{
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
```

<b>@Component</b>
![image](https://github.com/proamateur92/spring-core-basic/assets/68406448/f45f1a8d-8d6d-4fe9-97b5-f44d80528bfb)
- 스프링 빈의 기본 이름은 클래스명을 사용하되 맨 앞글자만 소문자를 사용한다.
- MemberServiceImpl → memberServiceImpl
- 스프링의 빈 이름을 직접 지정하고 싶으면 `@Component(”memberService2”)` 와 같이 작성한다.

<br>

<b>@Autowired 의존관계 자동 주입</b>
![image](https://github.com/proamateur92/spring-core-basic/assets/68406448/9456284a-e4ff-4724-8d0d-36673f879c39)
- 생성자에 `@Autowired` 를 지정하면 스프링 컨테이너가 자동으로 해당 스프링 빈을 찾아서 주입한다.
- 이때 기본 조회 전략은 타입이 같은 빈을 찾아 주입한다.
    - `getBean(MemberRepository.class)`와 동일하다고 이해하면 된다.

<br>

![image](https://github.com/proamateur92/spring-core-basic/assets/68406448/0792b940-ec46-46ea-8d3e-974fee4d3b3f)
생성자에 파라미터가 많아도 전부 자동으로 주입된다.

</div>
</details>

