package hello.core.order;

import hello.core.member.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {
    OrderService orderService = new OrderServiceImpl();
    MemberService memberService = new MemberServiceImpl();

    @Test
    void createOrder() {
        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order newOrder = orderService.createOrder(memberId, "itemA", 12000);
//        Assertions.assertThat(newOrder.calculate()).isEqualTo(newOrder.getItemPrice() - newOrder.getDiscountPrice());
        Assertions.assertThat(newOrder.getDiscountPrice()).isEqualTo(1000);
    }
}
