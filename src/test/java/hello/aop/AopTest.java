package hello.aop;

import hello.aop.order.OrderRepository;
import hello.aop.order.OrderService;
import hello.aop.order.aop.AspectV1;
import hello.aop.order.aop.AspectV3;
import hello.aop.order.aop.AspectV4Pointcut;
import hello.aop.order.aop.AspectV5Order;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest
@Import({AspectV5Order.LogAspect.class,AspectV5Order.TransactionAspect.class}) // 스프링빈으로 등록 가능
public class AopTest {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    void aopInfo()
    {
        log.info("isAopProxy, orderService={}", AopUtils.isAopProxy(orderService));
        log.info("isAopClass={}",orderService.getClass());
        log.info("isAopProxy, orderRepository={}", AopUtils.isAopProxy(orderRepository));
    }

    @Test
    void success()
    {
        orderService.orderItem("itemA");
    }

    @Test
    void exception()
    {
        Assertions.assertThatThrownBy(()-> orderService.orderItem("ex"))
                .isInstanceOf(IllegalStateException.class);
    }
}