package hello.aop.proxyvs;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

public class ProxyCastingTest {

    @Test
    void jdkProxy()
    {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(false);

        // JDK 동적프록시는 인터페이스만 참조 -> 구체 클래스에 대한걸 모르기때문에 다운 캐스팅 불가능이다.
        //CGLIB : 인터페이스 가능, 구체클래스 가능
        // JDK 동적 프록시 : 인터페이스 가능, 구체클래스 불가능!
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

        Assertions.assertThrows(ClassCastException.class,()-> {
           MemberServiceImpl castingMemberService =    (MemberServiceImpl) memberServiceProxy;
        });
    }
}
