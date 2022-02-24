package hello.aop.pointcut;

import hello.aop.member.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

public class ArgsTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException
    {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    private AspectJExpressionPointcut pointcut(String expression)
    {
        AspectJExpressionPointcut pointcut= new AspectJExpressionPointcut();
        pointcut.setExpression(expression);
        return pointcut;
    }
    /**
     * execution(* *(java.io.Serializable)): 메서드의 시그니처로 판단 (정적) 즉 무조건 Serializable이 들어와야함
     * args(java.io.Serializable): 런타임에 전달된 인수로 판단 (동적) String이든, Serializable이든 상관없다
     */
}
