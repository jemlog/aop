package hello.aop.order.aop;

import hello.aop.myTest.MyClass;
import hello.aop.myTest.MyInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Import(AspectTesting.class)
class AspectTestingTest {

    @Autowired
    MyClass myClass;


    @Test
    @DisplayName("내가 만든 에스펙트 테스트")
    public void testing()
    {
        log.info("proxy={}",myClass.getClass());
        String result = myClass.stop();
        System.out.println(result);


    }
}