package aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
@SpringBootApplication
public class AopBySpring {

    public static void main(String[] args) {

        SpringApplication.run(AopBySpring.class, args);
    }

    @Resource
    private ApplicationContext applicationContext;

    @Test
    public void test() {

        Vehicle vehicle = applicationContext.getBean(Vehicle.class);
        String str = vehicle.drive();
        System.out.println(str);

        vehicle = applicationContext.getBean(Vehicle.class);
        vehicle.drive();
        System.out.println(str);
    }
}
