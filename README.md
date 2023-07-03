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
