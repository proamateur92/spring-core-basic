package hello.core.order;

import hello.core.AppConfig;
import hello.core.member.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {
    OrderService orderService;
    MemberService memberService;

    @BeforeEach
    void beforeEach() {
        AppConfig appConfig = new AppConfig();

        memberService = appConfig.memberService();
        orderService = appConfig.orderService();
    }

    @Test
    void createOrder() {
        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order newOrder = orderService.createOrder(memberId, "itemA", 12000);
//        Assertions.assertThat(newOrder.calculate()).isEqualTo(newOrder.getItemPrice() - newOrder.getDiscountPrice());
        Assertions.assertThat(newOrder.getDiscountPrice()).isEqualTo(1200);
    }
}
