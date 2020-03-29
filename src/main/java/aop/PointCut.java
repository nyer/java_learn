package aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Aspect("pertarget(target(aop.Car))")
@Component
@Scope("prototype")
public class PointCut {

    public PointCut() {

        System.out.println("pointCut created, address: " + toString());
    }

    //@Pointcut("execution(* aop..Car.*(..))")
    //@Pointcut("within(aop..*)")
    @Pointcut("this(aop.Car)") // whether proxy depends on proxy method, proxy if it is cglib
    public void pointcut() {

    }

    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {

        System.out.println("before");
    }

    @AfterReturning(pointcut = "pointcut()", returning = "returnObj")
    public void afterReturn(JoinPoint joinPoint, Object returnObj) {

        System.out.println("after return");
    }

    @AfterThrowing(pointcut = "pointcut()", throwing = "th")
    public void afterThrowable(JoinPoint joinPoint, Throwable th) {

        System.out.println("after throwable");
    }

    @After("pointcut()")
    public void after(JoinPoint joinPoint) {

        System.out.println("after");
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        System.out.println("around before");
        Object returnObj = proceedingJoinPoint.proceed();
        System.out.println("around after");
        return returnObj;
    }
}