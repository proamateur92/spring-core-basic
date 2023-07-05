package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.order.Order;
import hello.core.order.OrderService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {
    public static void main(String[] args) {
        // @Configuraton으로 등록한 클래스 정보
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
//        AppConfig appConfig = new AppConfig();

        Long memberId = 1L;
        
        // @Bean의 메서드명, @Bean의 반환 타입
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        OrderService orderService = ac.getBean("orderService", OrderService.class);

//        MemberService memberService = appConfig.memberService();
        Member member = new Member();

        member.setId(memberId);
        member.setName("memberA");
        member.setGrade(Grade.VIP);

        memberService.join(member);

//        OrderService orderService = appConfig.orderService();
        Order order = orderService.createOrder(memberId, "itemA", 9876);

        System.out.println("order = " + order);
    }
}
