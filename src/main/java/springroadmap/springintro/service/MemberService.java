package springroadmap.springintro.service;

import org.springframework.stereotype.Service;
import springroadmap.springintro.domain.Member;
import springroadmap.springintro.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원가입
    public Long join(Member member) {
        validateDuplicateName(member); // 중복회원 검증
        memberRepository.save(member);

        return member.getId();
    }

    private void validateDuplicateName(Member member) {
        // Optional 객체를 사용하면 이렇게 단순하게 코드를 작성할 수 있다
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    //전체회원조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //ID로 회원조회
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
