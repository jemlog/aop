package hello.aop.member;

import hello.aop.member.annotation.ClassAop;
import hello.aop.member.annotation.MethodAop;
import org.springframework.stereotype.Component;

// TODO: 어노테이션 공부하기

@ClassAop
@Component // 스프링 빈 등록되야 스프링 AOP 사용가능
public class MemberServiceImpl implements MemberService{
    @Override
    @MethodAop("test value")
    public String hello(String param) {
        return "ok";
    }

    public String internal(String param)
    {
        return "ok";
    }
}
