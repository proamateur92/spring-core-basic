package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class FixDiscountPolicy implements DiscountPolicy{

    private final int discountFixAmount = 1000;
    
    @Override
    public int discount(Member member, int price) {
        
        // Enum은 == 등호로 비교
        if(member.getGrade() == Grade.VIP) {
            return discountFixAmount;
        }

        return 0;
    }
}
