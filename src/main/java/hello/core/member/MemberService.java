package hello.core.member;

import java.util.Optional;

public interface MemberService {

    void join(Member member);
    Member findMember(Long id);
}
