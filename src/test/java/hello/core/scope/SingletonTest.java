package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonTest {

    @Test
    void compareBean() {
        // 싱글톤에서는 스프링 컨테이서 생성 시점과 동시에 빈 등록과 의존 관계를 맺는다.
        // 싱글톤은 각기 다른 요청에도 같은 빈 객체를 가져다 쓴다.
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class);
        SingletonBean singletonBean1 = ac.getBean(SingletonBean.class);
        SingletonBean singletonBean2 = ac.getBean(SingletonBean.class);

        System.out.println("singletonBean1 = " + singletonBean1);
        System.out.println("singletonBean2 = " + singletonBean2);

        // isSameAs ==
        assertThat(singletonBean1).isSameAs(singletonBean2);
        ac.close();
    }

    @Scope("singleton")
    static class SingletonBean {

        @PostConstruct
        void init() {
            System.out.println("SingletonBean.init");
        }

        @PreDestroy
        void destroy() {
            System.out.println("SingletonBean.destroy");
        }
    }
}
