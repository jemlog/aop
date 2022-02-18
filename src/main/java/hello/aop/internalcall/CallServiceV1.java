package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV1 {

    private CallServiceV1 callServiceV1;

    @Autowired
    public void setCallServiceV1(CallServiceV1 callServiceV1) //setter를 통하면 객체 생성 후 setter로 값 주입
    {
        this.callServiceV1  = callServiceV1;
    }



    public void external()
    {
        log.info("call external");
        callServiceV1.internal(); // 내부 메서드 호출
    }

    public void internal()
    {
        log.info("call internal");
    }
}
