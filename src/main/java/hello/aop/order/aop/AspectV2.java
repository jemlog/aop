package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV2 {

    // hello.aop.order 패키지와 하위 패키지
   @Pointcut("execution(* hello.aop.order..*(..))")
   private void allOrder(){} // pointcut signature
                             // 수업 언급 : parameter가 들어가면 그것까지 맞춰줘야 한다.
    @Around("allOrder()") // 포인트컷
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable // 어드바이스
    {
        // joinPoint.getSignature
        log.info("[log] {}",joinPoint.getSignature());
        return joinPoint.proceed();
    }
}
