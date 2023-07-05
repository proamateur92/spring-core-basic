package hello.core.beanFind;

import com.sun.source.tree.BinaryTree;
import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextSameBeanFindTest {

    ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfing1.class);
    @Test
    @DisplayName("같은 타입의 빈이 두개 이상 등록되어있으면 에러가 발생한다.")
    void findBeanByTypeDuplicate() {
//        Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);
//        System.out.println("beansOfType = " + beansOfType);
//        MemberRepository bean = ac.getBean(MemberRepository.class);
        assertThrows(NoUniqueBeanDefinitionException.class, () -> ac.getBean(MemberRepository.class));
    }

    // 타입 조회 시 같은 타입 둘 이상, 빈 이름 저장하면 된다.
    @Test
    @DisplayName("같은 타입의 빈이 두개 이상 등록되어있으, 빈 이름을 저장하면 된다.")
    void findBeanByTypeSaveName() {
        MemberRepository bean = ac.getBean("memberRepository1", MemberRepository.class);
        org.assertj.core.api.Assertions.assertThat(bean).isInstanceOf(MemberRepository.class);
    }

    @Test
    @DisplayName("모든 타입의 빈 조회하기")
    void findBeanAllSameType() {
        Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);
        for(Object value : beansOfType.values()) {
            System.out.println("value = " + value);
        }
    }

    @Configuration
    static class TestConfing1 {

        @Bean
        MemberRepository memberRepository1() {
            return new MemoryMemberRepository();
        }

        @Bean
        MemberRepository memberRepository2() {
            return new MemoryMemberRepository();
        }
    }
}
