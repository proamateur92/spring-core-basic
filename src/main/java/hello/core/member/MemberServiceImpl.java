package hello.core.member;

import hello.core.discount.RateDiscountPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MemberServiceImpl implements MemberService{

    // 구현체와 인터페이스, 둘 모두에게 의존하고 있다. DIP 위반
    // 구현체를 갈아끼우려면 코드를 수정해야 한다. OCP 위반
    private final MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save((member));
    }

    @Override
    public Member findMember(Long id) {
        return memberRepository.findById(id).orElseGet(Member::new);
    }
}
