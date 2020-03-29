package aop;

import org.springframework.stereotype.Component;

@Component
public class Car implements Vehicle {

        public String drive() {

            return "i am driving a car, it's awesome";
        }
}