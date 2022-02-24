package hello.aop.member.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) // 클래스에 붙이는건 type, 메서드는 method!
@Retention(RetentionPolicy.RUNTIME) // retention은 언제까지 어노테이션이 살아있는지를 물어보는것
public @interface ClassAop{



        }