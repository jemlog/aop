package hello.aop.pointcut;

import hello.aop.member.MemberService;
import hello.aop.member.annotation.MethodAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Slf4j
@Import(ParameterTest.ParameterAspect.class)
public class ParameterTest {

    @Autowired
    MemberService memberService;

    @Test
    void success()
    {
        log.info("memberService Proxy={}",memberService.getClass());
        memberService.hello("helloA");
    }


    @Slf4j
    @Aspect
    static class ParameterAspect
    {
        @Pointcut("execution(* hello.aop.member..*.*(..))")
        private void allMember(){};

        @Around("allMember()")
        public Object logArgs1(ProceedingJoinPoint joinPoint) throws Throwable {
            Object arg1 = joinPoint.getArgs()[0];
            log.info("[logArgs1]{}, arg ={}",joinPoint.getSignature(),arg1);
            return joinPoint.proceed();
        }


        @Around("allMember() && args(arg, ..)") // 파라미터 꺼낼 수 있다!!
        public Object logArgs2(ProceedingJoinPoint joinPoint, Object arg) throws Throwable {
            log.info("[logArgs1]{}, arg ={}",joinPoint.getSignature(),arg);
            return joinPoint.proceed();
        }

        @Before("allMember() && args(arg, ..)") // 파라미터 꺼낼 수 있다!!
        public void logArgs3(String arg) throws Throwable {
            log.info("arg ={}",arg);
        }

        @Before("allMember() && this(obj)")  // this는 스프링 컨테이너에 들어있는 것 (프록시)
        public void thisArgs(JoinPoint joinPoint,MemberService obj)
        {
            log.info("[this]{}, obj={}",joinPoint.getSignature(),obj.getClass());
        }

        @Before("allMember() && target(obj)")  // target은 실제 객체, 프록시 아님!!!
        public void targetArgs(JoinPoint joinPoint,MemberService obj)
        {
            log.info("[target]{}, obj={}",joinPoint.getSignature(),obj.getClass());
        }

        @Before("allMember() && @annotation(annotation)")
        public void atAnnotation(JoinPoint joinPoint, MethodAop annotation)
        {
            log.info("[@annotation]{}, annotationValue={}",joinPoint.getSignature(),annotation.value());
        }
    }
}
