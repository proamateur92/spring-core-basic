package hello.core.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {

    MemberService memberService = new MemberServiceImpl();
    @Test
    void join() {
        // given
        Member member = new Member(1L, "memberA", Grade.VIP);

        // when
        memberService.join(member);
        Member findMember = memberService.findMember(1L);

        // then
        Assertions.assertThat(member).isEqualTo(findMember);
    }

    @Test
    void findById() {
        // given
        Long id = 1L;

        // when
        Member findMember = memberService.findMember(id);

        // then
        Assertions.assertThat(findMember.getId()).isEqualTo(null);

        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);
        findMember = memberService.findMember(id);

        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }
}
