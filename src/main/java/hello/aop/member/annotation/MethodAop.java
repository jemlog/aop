package hello.aop.member.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)  // 동적으로 실행시점에 읽을 수 있다.
public @interface MethodAop {
    String value(); // 이 방식으로 어노테이션에 값을 넣을 수 있다.
}
