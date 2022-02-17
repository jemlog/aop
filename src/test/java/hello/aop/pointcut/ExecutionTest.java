package hello.aop.pointcut;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

@Slf4j
public class ExecutionTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException
    {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void printMethod()
    {
        // execution(* ..package..Class.)
        // public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
        log.info("helloMethod={}",helloMethod);
    }

    @Test
    void exactMatch()
    {
        // String은 패키지명 생략 가능!   // 접근제어자, 반환타입 선언타입 메서드이름 파라미터
        pointcut.setExpression("execution(public String hello.aop.member.MemberServiceImpl.hello(String))");
        Assertions.assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

    @Test
    void allMatch()
    {
         // 반환 타입 * 메서드 이름 * 파라미터는 빈거(..) 파라미터의 타입과 수 상관없다!
        pointcut.setExpression("execution(* *(..))");
        Assertions.assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatch()
    {
        pointcut.setExpression("execution(* hello(..))");
        Assertions.assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchStar1()
    {
        pointcut.setExpression("execution(* hel*(..))");
        Assertions.assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchStar2()
    {
        pointcut.setExpression("execution(* *el*(..))");
        Assertions.assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchFalse()
    {
        pointcut.setExpression("execution(* nono(..))");
        Assertions.assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isFalse();
    }
    @Test
    void packageExactMatch1()
    {
        pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.hello(..))");
        Assertions.assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageExactMatch2()
    {                                        // pakage는 다 맞아
        pointcut.setExpression("execution(* hello.aop.member.*.*(..))");
        Assertions.assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageExactFalse()
    {                                        // 패키지 정확해야 한다. .은 바로 밑에 ..은 하위 패키지까지 다 포함
        pointcut.setExpression("execution(* hello.aop.*.*(..))");
        Assertions.assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isFalse();
    }

    @Test
    void packageMatchSubPackage1()
    {                                        // 패키지 정확해야 한다. .은 바로 밑에 ..은 하위 패키지까지 다 포함
        pointcut.setExpression("execution(* hello.aop.member..*(..))");
        Assertions.assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageMatchSubPackage2()
    {                                                // aop.. 하위 패키지 포함 * 모든 타입 * 메서드
        pointcut.setExpression("execution(* hello.aop..*.*(..))");
        Assertions.assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeExactMatch()
    {                                                // aop.. 하위 패키지 포함 * 모든 타입 * 메서드
        pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.*(..))");
        Assertions.assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeMatchSuperType()
    {                    // 만약 인터페이스를 타입으로 만든다면? 성공! 부모 타입으로 매칭해도 성공한다!!
        pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))");
        Assertions.assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeMatchInternal() throws NoSuchMethodException
    {                    // 부모 타입에 선언한 메서드만 포인트컷 가능하다! 즉, 인터페이스에 선언한 것만 가능!!!
        pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.*(..))");

        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        Assertions.assertThat(pointcut.matches(internalMethod,MemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeMatchNoSuperTypeMethod() throws NoSuchMethodException
    {                    // 부모 타입에 선언한 메서드만 포인트컷 가능하다! 즉, 인터페이스에 선언한 것만 가능!!!
        pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))");

        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        Assertions.assertThat(pointcut.matches(internalMethod,MemberServiceImpl.class)).isFalse();
    }

    //String 타입의 파라미터 허용
    // (String)
    @Test
    void argsMatch()
    {
        pointcut.setExpression("execution(* *(String))");
        Assertions.assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

    @Test
    void argsMatchNoArgs()
    {
        pointcut.setExpression("execution(* *())");
        Assertions.assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isFalse();
    }

    // 정확히 하나의 파라미터 허용, 모든 타입 허용
    @Test
    void argsMatchStar()
    {
        pointcut.setExpression("execution(* *(*))");
        Assertions.assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

    // 숫자와 무관하게 모든 파라미터, 모든 타입 허용
    @Test
    void argsMatchAll()
    {
        pointcut.setExpression("execution(* *(..))");
        Assertions.assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

    // String 타입으로 시작, 숫자와 무관하게 모든 파라미터, 모든 타입 허용
    @Test
    void argsMatchComplex()
    {
        // ..는 뭐든 숫자 타입 다 상관없다. *은 숫자는 상관있지만 종류는 상관없음!
        pointcut.setExpression("execution(* *(String, ..))");
        Assertions.assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("타켓의 타입에만 직접 적용, 인터페이스를 선정하면 안된다.")
    void withinSuperTypeFalse()
    {
        pointcut.setExpression("within(hello.aop.member.MemberService)");
        Assertions.assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isFalse();
    }




}
