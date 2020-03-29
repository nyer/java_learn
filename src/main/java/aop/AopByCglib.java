package aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class AopByCglib {

    static class AopCglibProxy implements MethodInterceptor {

        public static Object newProxy(Object object) {

            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(object.getClass());

            AopCglibProxy callBack = new AopCglibProxy();
            enhancer.setCallback(callBack);

            return enhancer.create();
        }

        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

            System.out.printf("before method invoke");
            Object returnObj = methodProxy.invokeSuper(o, objects);
            System.out.printf("after method invoke");
            return returnObj;
        }
    }

    public static void main(String[] args) {

        Car car = (Car) AopCglibProxy.newProxy(new Car());
        car.drive();
    }
}
