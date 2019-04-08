package vm;

import java.lang.reflect.Field;

import org.openjdk.jol.info.ClassLayout;

import sun.misc.Unsafe;

public class UnsafeTest {

	public static void main(String[] args) throws InstantiationException {
		
		Unsafe unsafe = getUnsafe();
		User user = (User) unsafe.allocateInstance(User.class);
		System.out.println(user);
		
		System.out.println(getFieldOffset(user.getClass(), "age"));
	}
	
	public static Long getFieldOffset(Class<?> klass, String fieldName) {
		
		try {
			
			return getUnsafe().objectFieldOffset(klass.getDeclaredField(fieldName));
		} catch(Exception e) {
			
			throw new RuntimeException(e);
		}
	}
	
	public static void printObjectLayout(Object instance) {
		
		System.out.println(ClassLayout.parseInstance(instance).toPrintable());
	}
	
	public static Unsafe getUnsafe() {
		
		try {
			Field field = Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			Unsafe unsafe = (Unsafe) field.get(null);
			return unsafe;
		} catch (Exception e) {
			
			throw new RuntimeException(e);
		}
	}

	static class User {
		
		private String name = "";
		private int age = 0;
		
		public User() {
			
			this.name = "test";
			this.age = 22;
		}
		
		@Override
		public String toString() {
		
			return name + ": " + age;
		}
	}
}
