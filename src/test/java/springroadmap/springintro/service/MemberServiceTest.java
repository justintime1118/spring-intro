package springroadmap.springintro.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import springroadmap.springintro.domain.Member;
import springroadmap.springintro.repository.MemoryMemberRepository;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemoryMemberRepository memberRepository;
    MemberService memberService;

    @BeforeEach
    void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    void afterEach() {
        memberRepository.clear();
    }

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