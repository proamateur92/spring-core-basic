package hello.core.scan;

import hello.core.AutoAppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AutoConfigTest {

    @Test
    @DisplayName("컴포넌트 스캔으로 bean 확인하기")
    void basicScan() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
//        String[] beanDefinitionNames = ac.getBeanDefinitionNames();

//        for(String beanDefinitionName : beanDefinitionNames) {
//            BeanDefinition beanDefinition =  ac.getBeanDefinition(beanDefinitionName);
//
//            if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
//                System.out.println("beanDefinitionName = " + beanDefinitionName + "beanDefinition = " + beanDefinition);
//            }
//        }
//        MemberService memberService = ac.getBean(MemberService.class);
//        Assertions.assertThat(memberService).isInstanceOf(MemberService.class);
        OrderServiceImpl bean = ac.getBean(OrderServiceImpl.class);
    }
}
