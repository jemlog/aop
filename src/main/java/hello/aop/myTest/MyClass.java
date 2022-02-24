package hello.aop.myTest;

import org.springframework.stereotype.Component;

@Component
public class MyClass implements MyInterface{


    @Override
    public String run(String username) {
        return username;
    }

    public String stop()
    {
        return "stop";
    }
}
