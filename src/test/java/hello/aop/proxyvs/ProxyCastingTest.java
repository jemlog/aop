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

        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

        Assertions.assertThrows(ClassCastException.class,()-> {
           MemberServiceImpl castingMemberService =    (MemberServiceImpl) memberServiceProxy;
        });
    }
}
