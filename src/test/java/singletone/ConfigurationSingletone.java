package singletone;

import hello.core.AppConfig;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletone {
    @Test
    void configurationDeep() {
       ApplicationContext ac  =  new AnnotationConfigApplicationContext(AppConfig.class);
       // 빈으로 등록
       AppConfig bean = ac.getBean(AppConfig.class);

       System.out.println("bean = " + bean.getClass());
       // bean = class hello.core.AppConfig$$EnhancerBySpringCGLIB$$148dea0f
    }
}
