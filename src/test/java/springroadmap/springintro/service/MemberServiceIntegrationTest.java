package springroadmap.springintro.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import springroadmap.springintro.domain.Member;
import springroadmap.springintro.repository.MemberRepository;
import springroadmap.springintro.repository.MemoryMemberRepository;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest // 스프링 컨테이너와 테스트를 함께 실행한다. Spring을 띄우는 테스트에는 이 어노테이션을 붙여준다
@Transactional // test에 이 어노테이션을 붙여주면 transaction을 커밋하지 않고 전부 rollback해서 DB에 영향이 없게 해준다. beforeEach, AfterEach 등이 필요없어짐.
class MemberServiceIntegrationTest {

    @Autowired // 테스트는 간편하게 필드주입을 사용해도 무방하다
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;

    @Test
    void join() {
        //given
        Member member = new Member();
        member.setName("Lionel");

        //when
        Long savedId = memberService.join(member);

        //then
        Member memberFound = memberRepository.findById(savedId).get();
        assertEquals(member.getName(), memberFound.getName());
    }

    @Test
    void dupJoin() {
        //given
        Member member1 = new Member();
        member1.setName("Lionel");

        Member member2 = new Member();
        member2.setName("Lionel");

        memberService.join(member1);

        //when & then
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2)); // 예외가 발생해야 함
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}