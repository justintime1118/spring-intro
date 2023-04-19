package springroadmap.springintro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springroadmap.springintro.repository.JdbcTemplateMemberRepository;
import springroadmap.springintro.repository.MemberRepository;
import springroadmap.springintro.repository.MemoryMemberRepository;
import springroadmap.springintro.service.MemberService;

import javax.sql.DataSource;

// 컴포넌트 스캔 대신 직접 빈으로 등록하는 방법
// 추후 빈으로 사용하는 객체에 변경사항이 있거나, 컴포넌트를 바꿔 끼게 될 경우에 이 파일만 딱 수정해주면 되므로 간편하다.
@Configuration
public class SpringConfig {

    private DataSource dataSource;

    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
        return new JdbcTemplateMemberRepository(dataSource);
    }
}
