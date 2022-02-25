package hello.aop.proxyvs;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import hello.aop.proxyvs.code.ProxyDIAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest(properties = {"spring.aop.proxy-target-class=true"}) // JDK 동적 프록시
@Import(ProxyDIAspect.class)
public class ProxyDITest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberServiceImpl memberServiceImpl; // 즉 jdk 동적 프록시로 만들었다면 MemberServiceImpl이 뭔지 모르기 때문에 대입 불가
    // JDK 동적 프록시는 IMPL로 들어오지 못한다! 캐스팅 실패!

    @Test
    void go()
    {
        log.info("memberService class={}",memberService.getClass());
        log.info("memberServiceImpl class={}",memberServiceImpl.getClass());
        memberServiceImpl.hello("hello");
    }
}
