package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect // aspectJ에서 어노테이션만 차용
public class AspectTesting {

    /*
    interface로 매칭하면 구현 안된 자식 메서드 사용 불가
    class로 매칭하면 구현 and 비구현 모두 매칭
    파라미터 부분에 ()일 경우 -> 파라미터 없을때만 가능
    파라미터 부분에 (*)일 경우 -> 어떤 종류의 파라미터든 가능
    파라미터 부분에 (..)일 경우 -> 어떤 종류, 파라미터 수 상관없이 가능
    파라미터 부분에 (String,..)일 경우 -> 처음은 무조건 String 나와야 하고 그 다음은 제한 x
    파라미터 부분에 (String,*)일 경우 -> 처음은 String 다음은 어떤 타입이든 가능 단 개수는 2개

     */
    @Around("execution(* hello.aop.myTest.MyInterface.*(..)) && args(String)" )
    public Object doTest(ProceedingJoinPoint joinPoint) throws Throwable
    {
        log.info("저는 지금 테스트 중입니다!");
        log.info("method={}",joinPoint.getSignature());

        Object result = joinPoint.proceed();
        log.info("테스트가 끝났습니다.");
        return result;
    }
}
