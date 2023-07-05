package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.order.Order;
import hello.core.order.OrderService;

public class OrderApp {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();

        Long memberId = 1L;

        MemberService memberService = appConfig.memberService();
        Member member = new Member();

        member.setId(memberId);
        member.setName("memberA");
        member.setGrade(Grade.VIP);

        memberService.join(member);

        OrderService orderService = appConfig.orderService();
        Order order = orderService.createOrder(memberId, "itemA", 9876);

        System.out.println("order = " + order);
    }
}
