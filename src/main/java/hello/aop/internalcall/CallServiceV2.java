package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV2 {

  //  private final ApplicationContext applicationContext; // 이건 너무 무거워.....
  private final ObjectProvider<CallServiceV2> callServiceV2ObjectProvider;

    public CallServiceV2(ObjectProvider<CallServiceV2> callServiceV2ObjectProvider) {
        this.callServiceV2ObjectProvider = callServiceV2ObjectProvider;
    }


    public void external()
    {
        log.info("call external");
        CallServiceV2 callServiceV2 = callServiceV2ObjectProvider.getObject(); // 타입으로 스프링컨테이너에서 객체 가져온다!
        callServiceV2.internal();
    }

    public void internal()
    {
        log.info("call internal");
    }
}
