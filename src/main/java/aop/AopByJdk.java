package aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class AopByJdk {

    static class AopJdkProxy implements InvocationHandler {

        private Object object;

        public AopJdkProxy(Object object) {

            this.object = object;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            System.out.println("before method invoke");

            Object returnObj = method.invoke(this.object, args);
            System.out.printf("\nafter method invoke");
            return returnObj;
        }

        public static Object proxy(Class<?> interfaceClazz, Object object) {

            return Proxy.newProxyInstance(interfaceClazz.getClassLoader(), new Class<?> [] {interfaceClazz},
                    new AopJdkProxy(object));
        }
    }

    public static void main(String[] args) {

        Vehicle vehicle = (Vehicle) AopJdkProxy.proxy(Vehicle.class, new Car());
        vehicle.drive();
    }
}
