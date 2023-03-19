package springroadmap.springintro.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import springroadmap.springintro.domain.Member;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class MemoryMemberRepositoryTest {

    MemberRepository memberRepository = new MemoryMemberRepository();

    @AfterEach
    void clear() {
        memberRepository.clear();
    }

    @Test
    void save() {
        //given
        Member member = new Member();
        member.setName("Messi");

        //when
        Member result = memberRepository.save(member);

        //then
        assertThat(result).isEqualTo(member);
    }

    @Test
    void findByName() {
        //given
        Member member1 = new Member();
        member1.setName("Messi");
        memberRepository.save(member1);

        Member member2 = new Member();
        member2.setName("Mbape");
        memberRepository.save(member2);

        //when
        Member result = memberRepository.findByName("Mbape").get();

        //then
        assertThat(result).isEqualTo(member2);
    }

    @Test
    void findAll() {
        //given
        Member member1 = new Member();
        member1.setName("Messi");
        memberRepository.save(member1);

        Member member2 = new Member();
        member2.setName("Mbape");
        memberRepository.save(member2);

        //when
        List<Member> result = memberRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
    }
}
