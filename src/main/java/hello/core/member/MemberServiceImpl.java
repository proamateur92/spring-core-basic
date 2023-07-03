package hello.core.member;

import java.util.Optional;

public class MemberServiceImpl implements MemberService{

    // 구현체와 인터페이스, 둘 모두에게 의존하고 있다. DIP 위반
    // 구현체를 갈아끼우려면 코드를 수정해야 한다. OCP 위반
    private final MemberRepository memberRepository = new MemoryMemberRepository();

    @Override
    public void join(Member member) {
        memberRepository.save((member));
    }

    @Override
    public Member findMember(Long id) {
        return memberRepository.findById(id).orElseGet(Member::new);
    }
}
