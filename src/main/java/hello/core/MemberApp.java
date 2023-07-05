package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.order.Order;
import hello.core.order.OrderService;

public class MemberApp {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();

        MemberService memberService = appConfig.memberService();

        Long memberId = 1L;
        Member member = new Member(memberId, "memberA" , Grade.VIP);

        memberService.join(member);
        Member findMember = memberService.findMember(member.getId());

        System.out.println("member = " + member.getName());
        System.out.println("findMember = " + findMember.getName());
    }
}
