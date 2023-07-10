package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.assertThat;

public class ProtoTypeSingletonProblemTest {
    /*
    스프링 컨테이너 생성과 함께 싱글톤 빈이 생성되고 의존 관계가 주입된다.
    의존 관계로 프로토 타입 빈을 가지고 있을 때 현재 프로토 타입 인스턴스의 참조 값이 주입된다.
    우리가 원하는건 각 사용자의 요청마다 싱글톤 빈의 의존 관계가 주입된 프로토타입 빈이 생성되는 것이다.
    그러나 싱글톤의 특징으로 인해 각기 다른 요청에도 불구하고 하나의 싱글톤과 프로토타입을 공유하게 된다.
     */

    @Test
    void getBeanCompareValue() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        PrototypeBean bean1 = ac.getBean(PrototypeBean.class);
        bean1.addCount();
        assertThat(bean1.getCount()).isEqualTo(1);

        PrototypeBean bean2 = ac.getBean(PrototypeBean.class);
        bean2.addCount();
        assertThat(bean2.getCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("싱글톤에서 프로토타입 의존 관계 주입")
    void singletonWithPrototype() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        Assertions.assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        Assertions.assertThat(count2).isEqualTo(2);
    }

    @Scope("singleton")
    static class ClientBean {
        private final PrototypeBean prototypeBean; // 생성 시점에 주입

        @Autowired
        public ClientBean(PrototypeBean prototypeBean) {
            this.prototypeBean = prototypeBean;
        }

        public int logic() {
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    @Scope("prototype")
    static class PrototypeBean {
        int count = 0;

        int getCount() {
            return count;
        }

        void addCount() {
            count += 1;
        }
    }
}
