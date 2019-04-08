package vm;

import java.lang.reflect.Field;

import org.openjdk.jol.info.ClassLayout;

import sun.misc.Unsafe;

public class FalseSharing implements Runnable {

	public static final int NUM_THREADS = 4;
	public static final long ITERATIONS = 1500L * 1000L * 1000L;
	private final int arrayIndex;

    public static VolatileLong[] longs = new VolatileLong[NUM_THREADS];
	static {
		for (int i = 0; i < longs.length; i++) {
			longs[i] = new VolatileLong();
		}
	}

	public FalseSharing(final int arrayIndex) {
		this.arrayIndex = arrayIndex;
	}

	public static void main(final String[] args) throws Exception {
		final long start = System.nanoTime();
		runTest();
		System.out.println("duration = " + (System.nanoTime() - start));
	}

	private static void runTest() throws Exception {
		
		Thread[] threads = new Thread[NUM_THREADS];

		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(new FalseSharing(i));
		}

		for (Thread t : threads) {
			t.start();
		}

		for (Thread t : threads) {
			t.join();
		}
	}
	
	public void run() {
		//System.out.println(longs[arrayIndex].sumPaddingToPreventOptimisation());
		
		long i = ITERATIONS + 1;
		while (0 != --i) {
			longs[arrayIndex].value = i;
		}
	}
	
	public static class ParentLong {
		
		public volatile long value = 0L; // value占 8byte
	}

	public static final class VolatileLong extends ParentLong { // Object header占12byte，默认开房了coops
	
		//public long pp1, pp2, pp3, pp4, pp5, pp6;
		
		// jdk8开始有这个，加上这个注解后变量单独占一个cacheline，但vm参数加-XX:-RestrictContended才会生效
		//@Contended
		
		// 6 * 8 + 
		public volatile long p1, p2, p3, p4, p5, p6 = 7;
		
	    public long sumPaddingToPreventOptimisation()
	    {
	        return p1 + p2 + p3 + p4 + p5 + p6 ;
	    }
	    
		/*
	    public void printP7Unsafe() {
	    	
	    	System.out.println(UnsafeTest.getUnsafe().getLong(this, UnsafeTest.getFieldOffset(getClass(), "p7")));
	    } */
	}
}
