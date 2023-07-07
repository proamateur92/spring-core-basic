package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

public class AllBeanTest {

    @Test
    void findAllBean() {
        // AutoAppConfig의 컴포넌트 스캔으로 모든 @Component 가 빈으로 등록된다.
        // DiscountService에는 의존 관계 주입으로 byType이 Map과 list에 들어간다.
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);
        DiscountService discountService = ac.getBean(DiscountService.class);

        Member member1 = new Member(1L, "memberA", Grade.VIP);
        int discountPrice1 = discountService.discount(member1, 7761, "rateDiscountPolicy");

        Member member2 = new Member(2L, "memberB", Grade.VIP);
        int discountPrice2 = discountService.discount(member2, 1987, "fixDiscountPolicy");

        Assertions.assertThat(discountPrice1).isEqualTo(776);
        Assertions.assertThat(discountPrice2).isEqualTo(1000);
    }

    static class DiscountService {
        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policyList;

        // Map<bean name, bean type>
        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policyList) {
            this.policyMap = policyMap;
            this.policyList = policyList;
            System.out.println("policyMap = " + policyMap);
            System.out.println("policyList = " + policyList);
        }

        public int discount(Member member, int price, String discountCode) {
            DiscountPolicy discountPolicy = policyMap.get(discountCode);
            return discountPolicy.discount(member, price);
        }
    }
}
